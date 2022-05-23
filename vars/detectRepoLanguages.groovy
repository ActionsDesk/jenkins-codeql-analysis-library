String[] call() {
    /**
    *  Gets and returns the list of languages detected in the repository.
    *
    *  https://docs.github.com/en/rest/repos/repos#list-repository-languages
    */

    println('Begin detect repository language procedure')

    // Parse GIT_URL so we can call the languages API
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

    println('gitHost: ' + gitHost)
    println('gitOrgRepo: ' + gitOrgRepo)

    // Form the languages API URL
    String languagesUrl = (gitHost == 'github.com') ? "https://api.github.com/repos/${gitOrgRepo}/languages" : "https://${gitHost}/api/v3/repos/${gitOrgRepo}/languages"
    println('languagesUrl: ' + languagesUrl)

    // Call the languages API
    repoLanguages = sh(script: "curl --request GET 'https://api.github.com/repos/microsoft/vscode/languages' --header 'Accept: application/vnd.github.v3+json'", returnStdout: true).toString().trim()
    println('Repository languages detected:' + repoLanguages)
    def repoLanguagesJSON = new groovy.json.JsonSlurper().parseText(repoLanguages)

    String[] compiledLanguagesArray = CODEQL_COMPILED_LANGUAGES.split("|")
    for (String language in compiledLanguagesArray) {
        println("debug language: " + language)
    }

    repoLanguagesJSON.each {
        println("jsonslurper: ${it.key}: ${it.value}")
    }

    // Return the result
    repoLanguagesTBD = "1.0.0"
    return repoLanguagesTBD
}
