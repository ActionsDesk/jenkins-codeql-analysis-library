String call(String version) {
    /**
    * Installs the requested CodeQL version
    *
    * @param version The version of the CodeQL bundle to install
    * @return The latest version of the CodeQL bundle
    */

    println('Version requested to install: ' + version)

    CODEQL_BIN_PATH="/codeql"
    CODEQL_CLI_ARCHIVE="/codeql/codeql.tar.gz"
    CODEQL_CLI_PATH="$CODEQL_BIN_PATH/bin/codeql"

    
}
