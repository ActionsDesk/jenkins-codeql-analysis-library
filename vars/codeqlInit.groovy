def call() {
    /**
    * Initializes CodeQL
    */

    // Setup databases and results directories
    sh(script: 'mkdir -p ' + CODEQL_DATABASES, returnStdout: true)
    sh(script: 'mkdir -p ' + CODEQL_RESULTS, returnStdout: true)

}