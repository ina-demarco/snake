node {
stage('Echo Test'){
	echo 'Hello World'

}
stage('Build'){
	checkout scm
	bat 'gradle build --info'
}
}