String[] call() {
    /**
    *  Gets and returns the list of languages detected in the repository.
    *
    *  https://docs.github.com/en/rest/repos/repos#list-repository-languages
    *
    */

    println('Begin detect repository language procedure')

    // 
    String server, orgRepo
    if (GIT_URL.contains("@")) {    // SSH URL
        trimmedGitUrl = GIT_URL.split("@")[1]
        //uri = new URI(trimmed)
        server = trimmedGitUrl.split(":")[0]
        orgRepo = trimmedGitUrl.split(":")[1].replace(".git","")

    } else {                        // HTTPS URL
        URI uri = new URI(GIT_URL)
        server = uri.getHost()
        orgRepo = uri.getPath().replace(".git","")
    }

    println('server: ' + server)
    println('orgRepo: ' + orgRepo)
    
    // Temp
    String tmpURL, tmpServer, tmpOrgRepo
    tmpURL = 'https://github.com/github/odst.git'
    URI tmpURI = new URI(tmpURL)
    tmpServer = tmpURI.getHost()
    tmpOrgRepo = tmpURI.getPath().replace(".git","")
    println('tmpServer: ' + tmpServer)
    println('tmpOrgRepo: ' + tmpOrgRepo)

    repoLanguages = "1.0.0"
    return repoLanguages
}
