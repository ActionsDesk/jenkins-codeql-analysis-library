List<String> getLanguages(String CREDID) {
    /**
    *  Gets and returns the list of languages detected in the repository.
    *
    *  https://docs.github.com/en/rest/repos/repos#list-repository-languages
    *
    * @return Lists of the detected compiled and interpreted languages
    */

    println('Begin detect repository language procedure')

    String languagesUrl
    def compiledLanguages = []
    def interpretedLanguages = []

    // Parse out the host and org/repo from the GIT_URL
    String gitHost
    String gitOrgRepo
    (gitHost, gitOrgRepo) = parseGitUrl()

    println('gitHost: ' + gitHost)
    println('gitOrgRepo: ' + gitOrgRepo)

    if (AUTO_DETECT == "0") {

        // TODO: Implement manually providing languages
        println('Manually providing languages')

    } else if (AUTO_DETECT == "1") {

        // Form the languages API URL
        languagesUrl = (gitHost == 'github.com') ? "https://api.github.com/repos/${gitOrgRepo}/languages" : "https://${gitHost}/api/v3/repos/${gitOrgRepo}/languages"
        println('languagesUrl: ' + languagesUrl)
        withCredentials([string(credentialsId: "${CREDID}", variable: 'AUTHTOKEN')]) {
            repoLanguages = sh(script: "curl --request GET '${languagesUrl}' --header 'Accept: application/vnd.github.v3+json' --header 'Authorization: token ${AUTHTOKEN}'", returnStdout: true).toString().trim()
        }

        // Debug: Use this block to test the languages API
        /*languagesUrl = "https://api.github.com/repos/microsoft/vscode/languages"
        println('languagesUrl: ' + languagesUrl)
        repoLanguages = sh(script: "curl --request GET '${languagesUrl}' --header 'Accept: application/vnd.github.v3+json'", returnStdout: true).toString().trim()
        */

        // Proceed after calling the languages API
        println('All repository languages detected:' + repoLanguages)
        def repoLanguagesJSON = new groovy.json.JsonSlurper().parseText(repoLanguages)

        // Check for compiled and interpreted languages in the repo
        String[] codeqlCompiledLanguages = CODEQL_COMPILED_LANGUAGES.split(",")
        String[] codeqlInterpretedLanguages = CODEQL_INTERPRETED_LANGUAGES.split(",")
        repoLanguagesJSON.each {
            //println("jsonslurper: ${it.key}: ${it.value}")
            if (codeqlCompiledLanguages.contains(it.key)) {

                if (it.key == "C++") { // Convert C++ to cpp for init
                    println('CodeQL found compiled language: cpp')
                    compiledLanguages.add('cpp')
                } else if (it.key == "C#") { // Convert C# to csharp for init
                    println('CodeQL found compiled language: csharp')
                    compiledLanguages.add('csharp')
                } else {
                    println('CodeQL found compiled language: ' + it.key)
                    compiledLanguages.add(it.key.toLowerCase())
                }

            } else if (codeqlInterpretedLanguages.contains(it.key)) {
                println('CodeQL found interpreted language: ' + it.key)
                interpretedLanguages.add(it.key.toLowerCase())
            } else {
                println('CodeQL skipping language: ' + it.key)
            }
        }

        // debug
        println('CodeQL-supported compiled languages found: ' + compiledLanguages)
        println('CodeQL-supported interpreted languages found: ' + interpretedLanguages)

    } else {

        println('ERROR: AUTO_DETECT must be 0 or 1')

    }

    return [compiledLanguages, interpretedLanguages]
}

String parseGitUrl() {
    /**
    *  Parses the GIT_URL passed by the Jenkins job into host, org, and repo values.
    *
    * @return The repository's host, org, and repo values
    */

    String gitHost, gitOrgRepo
    if (GIT_URL.contains("@")) {    // SSH URL
        trimmedGitUrl = GIT_URL.split("@")[1]
        //uri = new URI(trimmed)
        gitHost = trimmedGitUrl.split(":")[0]
        gitOrgRepo = trimmedGitUrl.split(":")[1].replace(".git","")

    } else {                        // HTTPS URL
        URI uri = new URI(GIT_URL)
        gitHost = uri.getHost()
        gitOrgRepo = uri.getPath().replace(".git","")
    }

    return [gitHost, gitOrgRepo]
}
