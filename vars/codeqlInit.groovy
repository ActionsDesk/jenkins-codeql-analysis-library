def call() {
    /**
    * Initializes CodeQL
    */

    // Setup databases and results directories
    sh(script: 'mkdir -p ' + CODEQL_DATABASES, returnStdout: true)
    sh(script: 'mkdir -p ' + CODEQL_RESULTS, returnStdout: true)

    // Display info about the repository
    sh 'printenv'
    String githubRepository = getRepoInfo.parseGitUrl()
    String githubRef = ""
    
    println("Github Repository: ${githubRepository}")

}