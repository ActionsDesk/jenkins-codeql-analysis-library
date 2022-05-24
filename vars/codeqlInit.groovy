def call() {
    /**
    * Initializes CodeQL
    */

    // Setup databases and results directories
    sh(script: 'mkdir -p ' + CODEQL_DATABASES, returnStdout: true)
    sh(script: 'mkdir -p ' + CODEQL_RESULTS, returnStdout: true)

    // Display info about the repository
    sh 'printenv'
    String gitHost
    String gitOrgRepo

    (gitHost, gitOrgRepo) = getRepoInfo.parseGitUrl()

    println("GitHub Repository URL: ${GIT_URL}")
    println("Github Ref: ${GIT_COMMIT}")




}
