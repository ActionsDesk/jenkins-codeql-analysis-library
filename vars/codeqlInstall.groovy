String call(String version) {
    /**
    * Installs the requested CodeQL version
    *
    * @param version The version of the CodeQL bundle to download
    */

    println('Begin CodeQL Install procedure')

    println('Version requested to install: ' + version)
    
    os = System.properties['os.name'].toLowerCase()

    // https://github.com/github/codeql-action/releases
    switch (os) {
        case 'linux':
            bundleName = 'codeql-linux64.zip'
            break
        case 'macos':
            bundleName = 'codeql-osx64.zip'
            break
        case 'windows':
            bundleName = 'codeql-win64.zip'
            break
        default:
            bundleName = 'codeql.zip'
    }

    CODEQL_CLI_RELEASE_URL = 'https://github.com/github/codeql-cli-binaries/releases/download/' + version + '/' + bundleName
    CODEQL_CLI_PATH = File.createTempDir()
    CODEQL_BIN_PATH = CODEQL_CLI_PATH
    CODEQL_CLI_ARCHIVE = bundleName

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
    println(sh(script: 'file ' + CODEQL_CLI_ARCHIVE, returnStdout: true))
    sh(script: 'unzip ' + CODEQL_CLI_ARCHIVE + ' -d ' + CODEQL_BIN_PATH, returnStdout: true)
    println(sh(script: 'ls -al; ls -al ' + CODEQL_BIN_PATH, returnStdout: true))
    sh(script: 'mv ' + CODEQL_BIN_PATH + '/codeql/codeql ' + CODEQL_BIN_PATH + '/codeql/bin', returnStdout: true)

    // Create bin symlink
    //println('Create bin symlink...')
    //sh(script: 'ln -s ' + CODEQL_CLI_PATH + ' /usr/bin/codeql', returnStdout: true)

    // Display downloaded version
    println('The workspace is: ' + WORKSPACE)
    env.PATH = env.PATH + ':' + CODEQL_BIN_PATH + '/bin'
    println('ENV: ' + env.PATH)
    println(sh(script: CODEQL_BIN_PATH + '/bin', returnStdout: true))
    CODEQL_INSTALLED_VERSION = sh(script: 'codeql --version', returnStdout: true)
    //sh(script: CODEQL_CLI_PATH + ' --version', returnStdout: true)
    println('CodeQL CLI Version: ' + CODEQL_INSTALLED_VERSION)

    // Cleaning up image
    println('Cleaning up image...')
    sh(script: 'rm -f ' + CODEQL_CLI_ARCHIVE, returnStdout: true)

    return CODEQL_INSTALLED_VERSION
}
