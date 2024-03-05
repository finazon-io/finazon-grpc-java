package io.finazon.gradleplugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CodeGeneratorPlugin implements Plugin<Project> {

    static final String TASK_NAME = "createExtGrpcService";

    @Override
    public void apply(Project target) {
        target.getTasks().create(TASK_NAME, GrpcExtensionTask.class);
    }
}