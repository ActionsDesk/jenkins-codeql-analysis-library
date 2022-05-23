String call() {
    /**
    * Downloads the GitHub CLI. Useful for detecting repository languages.
    */

    println('Begin GitHub CLI Install procedure')

    OS_NAME = sh(script: 'cat /etc/os-release | grep -i "^NAME"', returnStdout: true).split('=')[1].trim()


    println("Operating system is: " + OS_NAME)

    // https://cli.github.com/manual/installation
    /*
    switch (os) {
        case 'linux':
            bundleName = 'codeql-bundle-linux64.tar.gz'
            break
        case 'macos':
            sh(script: 'brew install gh', returnStdout: true)
            break
        case 'windows':
            sh(script: 'choco install gh', returnStdout: true)
            break
        default:
            println('Could not detect OS while installing GitHub CLI.')
    }
    */


    GH_CLI_INSTALLED_VERSION = "1.0.0"
    return GH_CLI_INSTALLED_VERSION

}
