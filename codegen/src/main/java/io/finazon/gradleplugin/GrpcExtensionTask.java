package io.finazon.gradleplugin;

import com.google.common.base.CaseFormat;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.protobuf.gradle.ProtobufExtension;
import com.google.protobuf.gradle.tasks.ProtoSourceSet;
import com.hubspot.jinjava.Jinjava;
import com.squareup.wire.schema.Location;
import com.squareup.wire.schema.ProtoFile;
import com.squareup.wire.schema.Schema;
import com.squareup.wire.schema.SchemaLoader;
import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GrpcExtensionTask extends DefaultTask {

    private final Pattern classPattern = Pattern.compile("public final class (\\w+)Grpc \\{", Pattern.CASE_INSENSITIVE);

    @TaskAction
    public void createExtensionGrpcService() throws IOException {
        ProtobufExtension extension = this.getProject().getExtensions().findByType(ProtobufExtension.class);
        if (extension == null) {
            throw new IOException("Not found protobuf extension");
        }
        Path generated = Paths.get(extension.getGeneratedFilesBaseDir());
        Files
            .walk(generated)
            .filter(Files::isRegularFile)
            .forEach(this::fixJavaGeneratedFile);

        Path result = Files
            .walk(generated)
            .filter(Files::isDirectory)
            .filter(f -> f.endsWith("finazon"))
            .findFirst()
            .get();

        Jinjava jinjava = new Jinjava();
        String template = Resources.toString(Resources.getResource("templates/class.tpl"), Charsets.UTF_8);

        SchemaLoader loader = new SchemaLoader(FileSystems.getDefault());
        List<Location> sourcePath = ((NamedDomainObjectContainer<ProtoSourceSet>) extension.getProperty("sourceSets"))
            .findByName("main")
            .getProto()
            .getSourceDirectories()
            .getFiles()
            .stream()
            .map(p -> Location.get(p.toString()))
            .collect(Collectors.toList());
        List<Location> protoPath = Collections.emptyList();
        loader.initRoots(sourcePath, protoPath);
        Schema schema = loader.loadSchema();
        List<ProtoFile> protoFiles = schema.getProtoFiles();
        protoFiles.stream()
            .sorted(Comparator.comparing(protoFile -> protoFile.getLocation().getPath()))
            .forEach(protoFile -> {
                if (protoFile.getServices().isEmpty()) {
                    return;
                }
                protoFile.getServices().forEach(service -> {
                    Map<String, Object> context = Maps.newHashMap();
                    context.put("package", protoFile.javaPackage());
                    context.put("name", service.name());
                    List<Map<String, Object>> rpcs = service.rpcs()
                        .stream()
                        .map(rpcItem -> {
                            Map<String, Object> rpc = Maps.newHashMap();
                            rpc.put("requestType", rpcItem.getRequestType().getSimpleName());
                            rpc.put("responseType", rpcItem.getResponseType().getSimpleName());
                            rpc.put("name", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, rpcItem.getName()));
                            rpc.put("documentation", rpcItem.getDocumentation());
                            return rpc;
                        })
                        .collect(Collectors.toList());
                    context.put("rpc", rpcs);
                    try {
                        Files.write(Paths.get(result.toString(), service.name() + ".java"), jinjava.render(template, context).getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
    }

    private void fixJavaGeneratedFile(Path f) {
        try {
            String result = new String(Files.readAllBytes(f));
            Matcher matcher = classPattern.matcher(result);
            if (matcher.find()) {
                result = matcher.replaceFirst("final class $1Grpc \\{");
                Files.write(f, result.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
