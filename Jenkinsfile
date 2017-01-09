node {
stage (‘Build’){
	checkout scm
	bat ‘./gradlew stage --info’
}
stage (‘Test’){
	bat ‘./gradlew check --info
	junit ‘**/test-results/*.xml’
}
}
