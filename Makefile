PATH_THIS:=$(realpath $(dir $(lastword ${MAKEFILE_LIST})))

.PHONY: generate
generate:
	gradle clean build

.PHONY: bump_version
bump_version:
	@[ "${VERSION}" ] || ( echo "VERSION is not set"; exit 1 )
	@sed -i 's/version = [^ ]*/version = '"'"'${VERSION}'"'"'/' ${PATH_THIS}/build.gradle

.PHONY: publish
publish:
	gradle :lib:publish