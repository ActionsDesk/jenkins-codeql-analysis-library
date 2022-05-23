String call(String version = 'latest') {
    /**
    * Gets the latest CodeQL bundle version.
    *
    * @param version The version of the CodeQL bundle to get
    * @return The value of latest version of the CodeQL bundle
    */
    
    releasesUrl = 'https://api.github.com/repos/github/codeql-action/releases/latest'

    latestVersion = sh(script: "curl --silent '${releasesUrl}' | grep '\"tag_name\":'", returnStdout: true).toString().trim()
    latestVersion = latestVersion.split('\"')[3].trim()

    println('Retrieved the latest CodeQL bundle version number: ' + latestVersion)

    return latestVersion
}
