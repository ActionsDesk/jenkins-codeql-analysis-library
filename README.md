# Jenkins Shared Library: CodeQL Analysis

This [shared library](https://jenkins.io/doc/book/pipeline/shared-libraries/) allows Jenkins pipelines to initialize and perform CodeQL analysis on a project. There exists a [Jenkins CodeQL plugin](https://plugins.jenkins.io/codeql/), but currently it only supports installing and setting up the [CodeQL CLI](https://codeql.github.com/docs/codeql-cli/) on a Jenkins agent during a build.

## Purpose

At a high level, the purpose of this library is to perform the following:

- Download CodeQL
- Detect languages of the project
- Run dynamic CodeQL init step
- if (language == compiled)
  - Trace the build (set environment variables)
- Run commands in plugin block
- Run finalize and analyze commands on all databases
- Upload results to the project repository
- Track timing/overhead of each step

## References

A list of helpful references:

- [CodeQL CLI Binaries](https://github.com/github/codeql-cli-binaries/releases)
- [Creating CodeQL databases](https://codeql.github.com/docs/codeql-cli/creating-codeql-databases/), including [using indirect build tracing](https://codeql.github.com/docs/codeql-cli/creating-codeql-databases/#using-indirect-build-tracing)

## Sample pipeline

🚧 Add sample pipeline usage here.

## Known issues

A list of known issues is available on the [GitHub issues page of this project][jenkins-codeql-lib-issues].

## Getting Started

This section will contain instructions for using the shared library.

## Contributing

Read and understand our [contribution guidelines][jenkins-codeql-lib-contributing] before opening a pull request.

[jenkins-codeql-lib-issues]: https://github.com/ActionsDesk/jenkins-codeql-analysis-library/issues
[jenkins-codeql-lib-contributing]: .github/CONTRIBUTING.md
