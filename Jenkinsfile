def gradle;

node {
stage('Echo Test'){
	echo 'Hello World'

}
stage('Build'){
	checkout scm
	bat 'gradle build --info'
}
stage('UnitTests'){
	gradle = load 'jenkins/gradle.groovy'
	gradle.test()
	junit '**/test/*.xml'
}
}