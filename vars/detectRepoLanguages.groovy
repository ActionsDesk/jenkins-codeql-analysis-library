String[] call() {
    /**
    * Gets and returns the list of languages detected in the repository.
    */

    println('Begin detect repository language procedure')

    // https://docs.github.com/en/rest/repos/repos#list-repository-languages
    URI uri
    String server, orgRepo
    if (GIT_URL.contains("@")) {    // SSH
        trimmedGitUrl = GIT_URL.split("@")[1]
        //uri = new URI(trimmed)
        server = trimmedGitUrl.split(":")[0]
        orgRepo = trimmedGitUrl.split(":")[1].split(".")[0]

        sh 'printenv'
        
    } else {                        // HTTPS
        println("HTTPS URL")
        uri = new URI(GIT_URL)
    }



    GH_CLI_INSTALLED_VERSION = "1.0.0"
    return GH_CLI_INSTALLED_VERSION


    // def GH_CLI_INSTALLED_VERSION = githubCLIInstall()
    // echo "The GitHub CLI installed version is: ${GH_CLI_INSTALLED_VERSION}"
}
