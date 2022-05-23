def call(String version, String os = 'linux') {
    /**
    * Downloads the requested CodeQL version
    *
    * @param version The version of the CodeQL bundle to download
    */

    println('Version requested to download: ' + version)

    CODEQL_BIN_PATH = './codeql'
    CODEQL_CLI_ARCHIVE = './codeql/codeql.tar.gz'
    CODEQL_CLI_PATH = CODEQL_BIN_PATH + '/bin/codeql'

    // https://github.com/github/codeql-action/releases
    switch (os) {
        case 'linux':
            bundleName = 'codeql-bundle-linux64.tar.gz'
            break
        case 'macos':
            bundleName = 'codeql-bundle-macos64.tar.gz'
            break
        case 'windows':
            bundleName = 'codeql-bundle-win64.zip'
            break
        default:
            bundleName = 'codeql-bundle.tar.gz'
    }

    CODEQL_CLI_RELEASE_URL = 'https://github.com/github/codeql-action/releases/' + version + '/download/' + bundleName

    // Check the values
    println('CodeQL URL: ' + CODEQL_CLI_RELEASE_URL)
    println('CodeQL CLI Path: ' + CODEQL_CLI_PATH)

    // Create directory
    println('Creating CodeQL bin directory: ' + CODEQL_BIN_PATH)
    sh(script: 'mkdir -p ' + CODEQL_BIN_PATH, returnStdout: true)

    // Download archive
    println('Downloading archive...')
    sh(script: 'curl -o ' + CODEQL_CLI_ARCHIVE + ' -L ' + CODEQL_CLI_RELEASE_URL, returnStdout: true)

    // Extract archive
    println('Extracting archive...')
    sh(script: 'tar -xvzf ' + CODEQL_CLI_ARCHIVE + ' -C ' + CODEQL_BIN_PATH, returnStdout: true)
    sh(script: 'mv ./codeql/codeql ./codeql/bin', returnStdout: true)

    // Create bin symlink
    //println('Create bin symlink...')
    //sh(script: 'ln -s ' + CODEQL_CLI_PATH + ' /usr/bin/codeql', returnStdout: true)

    // Display downloaded version
    println('The workspace is: ' + WORKSPACE)
    env.PATH = env.PATH + ':' + CODEQL_BIN_PATH + '/bin'
    CODEQL_INSTALLED_VERSION = sh(script: 'codeql --version', returnStdout: true)
    //sh(script: CODEQL_CLI_PATH + ' --version', returnStdout: true)
    println('CodeQL CLI Version: ' + CODEQL_INSTALLED_VERSION)

    // Cleaning up image
    println('Cleaning up image...')
    sh(script: 'rm -f ' + CODEQL_CLI_ARCHIVE, returnStdout: true)
}
