String call(String version = 'latest') {
    /**
    * Gets the latest CodeQL bundle version.
    *
    * @param version The version of the CodeQL bundle to get
    * @return The value of latest version of the CodeQL bundle
    */

    println('Get CodeQL Version specified: ' + version)

    latestVersion = sh(script: "curl --silent 'https://api.github.com/repos/github/codeql-action/releases/latest' | grep '\"tag_name\":' | sed -E 's/.*\"([^\"]+)\".*/\1/'", returnStdout: true).toString().trim()

    println('Retrieved the Latest version number: ' + latestVersion)

    return latestVersion
}
