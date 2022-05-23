String call() {
    /**
    * Gets the current linux distribution
    */
    OS_NAME = os.toLowerCase();

    switch (os) {
        case 'linux':
            OS_NAME = sh(script: 'cat /etc/os-release | grep -i \"^NAME\"', returnStdout: true).split('=')[1].replace("\"", "").trim()
            break
        case 'macos':
            // Todo: add macos support
            break
        case 'windows':
            // Todo: add windows support
            break
        default:
            println("Operating system is: " + OS_NAME)
    }

    return OS_NAME
}