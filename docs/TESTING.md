1. Ensure that this shared library is added to your jenkins server
2. Add the following pipeline to a new job
```groovy
@Library('CodeQL') _

pipeline {
    agent any
    stages {
        stage('CodeQL Scan') {
            steps {
                script {
                    codeqlGetLatestVersion()
                }
            }
        }
    }
}
```
