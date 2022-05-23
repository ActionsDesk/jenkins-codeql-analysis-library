def install(String version) {
    /**
    * Installs the specified CodeQL bundle version
    *
    * @param version The version of the CodeQL bundle to install
    */

    // If we specify 'latest', then go get the latest release
    if (version == 'latest') {
        println('Version specified: ' + version)

    // If we don't specify 'latest', check which version we need to get
    } else {
        // TODO: get a specific version
        println('Need to implement: Get specific version. Version specified: ' + version)
    }
}

String latestVersion() {
    /**
    * Gets the latest version of the CodeQL bundle
    *
    * @return The latest version of the CodeQL bundle
    */

    String response = httpRequest(
        customHeaders: [],
        httpMode: 'GET',
        responseHandle: 'NONE',
        url: getBundleUrl())

    println 'response: ' + response

    return 'test'
}

String getBundleUrl() {
    /**
    * Gets the URL of the CodeQL bundle
    *
    * @return The URL of the CodeQL bundle
    */

    String codeql = libraryResource 'com.github.codeql'

    return codeql.bundleUrl
}
