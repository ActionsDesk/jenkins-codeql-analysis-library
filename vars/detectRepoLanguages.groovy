String[] call() {
    /**
    * Gets and returns the list of languages detected in the repository.
    */

    println('Begin detect repository language procedure')

    // https://docs.github.com/en/rest/repos/repos#list-repository-languages
    
    sh 'printenv'
    //REPO_LANGUAGES_URL = TBD



    GH_CLI_INSTALLED_VERSION = "1.0.0"
    return GH_CLI_INSTALLED_VERSION


    // def GH_CLI_INSTALLED_VERSION = githubCLIInstall()
    // echo "The GitHub CLI installed version is: ${GH_CLI_INSTALLED_VERSION}"
}
