PATH_THIS:=$(realpath $(dir $(lastword ${MAKEFILE_LIST})))
PATH_PROTO:=lib/src/main/proto

.PHONY: patch_proto
patch_proto:
	@echo "Patch proto files ..."
	# add option to proto files
	@for filename in ${PATH_PROTO}/*.proto; do \
  		grep -qF 'java_multiple_files' $$filename || sed -i '/package finazon;/a \\noption java_multiple_files=true;\noption java_package="io.finazon";' $$filename ; \
   	done

.PHONY: generate
generate:
	make patch_proto
	gradle clean build

.PHONY: bump_version
bump_version:
	@[ "${VERSION}" ] || ( echo "VERSION is not set"; exit 1 )
	@sed -i 's/version = [^ ]*/version = '"'"'${VERSION}'"'"'/' ${PATH_THIS}/build.gradle

.PHONY: publish
publish:
	@echo "signing.keyId=${GRPC_JAVA_SIGNING_KEY_ID}\nsigning.password=${GRPC_JAVA_SIGNING_PASSWORD}\nsigning.secretKeyRingFile=${GRPC_JAVA_SIGNING_KEY_FILE}\nmavenCentralUsername=${GRPC_JAVA_MAVEN_USER}\nmavenCentralPassword=${GRPC_JAVA_MAVEN_PASSWORD}" > gradle.properties
	gradle :lib:publishToMavenCentral
