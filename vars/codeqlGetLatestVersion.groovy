String call() {
    /**
    * Gets the latest CodeQL bundle version number.
    *
    * @return The value of latest version of the CodeQL bundle
    */
    
    latestVersion = pwsh(returnStdout: true, script: Invoke-RestMethod -Method 'Get' -Uri 'https://api.github.com/repos/github/codeql-action/releases/latest' -ContentType 'application/json')
                         
    
    

//     releasesUrl = 'https://api.github.com/repos/github/codeql-action/releases/latest'

//     latestVersion = sh(script: "curl --silent '${releasesUrl}' | grep '\"tag_name\":'", returnStdout: true).toString().trim()
//     latestVersion = latestVersion.split('\"')[3].trim()

//     println('Retrieved the latest CodeQL bundle version number: ' + latestVersion)

    return latestVersion
}
