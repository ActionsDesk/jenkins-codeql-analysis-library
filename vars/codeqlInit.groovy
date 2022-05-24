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
    println("GitHub Ref: ${GIT_COMMIT}")

    // Get repo languages
    def compiledLanguages = []
    def interpretedLanguages = []

    (compiledLanguages, interpretedLanguages) = getRepoInfo.getLanguages("${GITHUB_CREDENTIAL_ID}")

    // Warn if there were no languages detected
    if (compiledLanguages.isEmpty() && interpretedLanguages.isEmpty()) {
        println("WARNING: No languages detected")
    }


    String codeqlGitHubRepository = gitOrgRepo.replace("/", "_")

    // Work on detected compiled languages
    if (!compiledLanguages.isEmpty()) {

        for (String language in compiledLanguages) {
            println("Init compiled language: ${language}")
            String codeqlDatabase = getCodeqlDatabase(language, codeqlGitHubRepository)
            deleteOldDatabase(codeqlDatabase)

            codeqlCreate = codeqlCreateCommand(language)

            // TODO: Add build command support here, if needed

            // If CODEQL_TRACING = "1", then swap to using indirect build tracing
            // https://codeql.github.com/docs/codeql-cli/creating-codeql-databases/#using-indirect-build-tracing
            if (CODEQL_TRACING == "1") {
                codeqlCreate = "codeql database init --begin-tracing --language=${language} --source-root=. ${codeqlDatabase}"

            // Otherwise, only append the database
            } else {
                codeqlCreate = "${codeqlCreate} ${codeqlDatabase}"
            }

            // Check our command
            println("CodeQL create command: ${codeqlCreate}")

            // Create the database
            sh(script: "${codeqlCreate}", returnStdout: true)

            // If CODEQL_TRACING = "1", then start tracing
            if (CODEQL_TRACING == "1") {

                println("CodeQL -> Starting tracing")
                //sh(script: "source ${codeqlDatabase}/temp/tracingEnvironment/start-tracing.sh", returnStdout: true)

            // Otherwise, run autobuild
            } else {

                String codeqlAutoBuildPath = getCodeqlAutoBuildPath(language)
                println("Running CodeQL auto-build :: ${codeqlAutoBuildPath}")
                sh(script: "${codeqlAutoBuildPath}", returnStdout: true)
            }
        }

    }

    // Work on detected interpreted languages
    if (!interpretedLanguages.isEmpty()) {

        for (String language in interpretedLanguages) {
            println("Init interpreted language: ${language}")
            String codeqlDatabase = getCodeqlDatabase(language, codeqlGitHubRepository)
            deleteOldDatabase(codeqlDatabase)

            codeqlCreate = codeqlCreateCommand(language)
            println("CodeQL create command: ${codeqlCreate}")

            // TODO
        }

    }
}

String getCodeqlDatabase(String language, String codeqlGitHubRepository) {
    /**
    * Gets the CodeQL database for the given language and repo
    */

    String codeqlDatabase = "${CODEQL_DATABASES}/${language}-${codeqlGitHubRepository}"
    return codeqlDatabase
}

def deleteOldDatabase(String codeqlDatabase) {
    /**
    * Deletes the old database
    */

    sh(script: "rm -rf ${codeqlDatabase}", returnStdout: true)
}

String codeqlCreateCommand(String language) {
    /**
    * Generates the the CodeQL create command for the given language
    */

    String command = "codeql database create --language=${language}"
    return command
}

String getCodeqlAutoBuildPath(String language) {
    /**
    * Gets the CodeQL auto-build path for the given language
    */

    String codeqlAutoBuildPath = "${CODEQL_BIN_PATH}/${language}/tools/autobuild.sh"
    return codeqlAutoBuildPath
}
