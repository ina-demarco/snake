node {
stage('Echo Test'){
	echo 'Hello World'

}
stage('Build'){
	bat 'gradle clean --info'
	checkout scm
	bat 'gradle build --info'
}
stage('UnitTests'){
	bat 'gradle check --info'
	junit '**/test/*Test.xml'
}
}