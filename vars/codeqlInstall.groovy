def call(String version = 'latest') {
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
