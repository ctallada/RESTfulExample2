def CONTAINER_NAME = "ositest"
	def CONTAINER_TAG = "latest"
	def DOCKER_HUB_USER = "chetant"
	def HTTP_PORT = "8099"

	node {
		CONTAINER_ID = ''
	CONTAINER_ID = sh(
			script: "docker ps | grep restappapiimage:latest | awk '{print \$1}'",
			returnStdout: true).trim()
		echo "container id: ${CONTAINER_ID}" 

		stage('Checkout') {
		checkout scm
	}

	stage('Build') {
		//sh "mvn clean install"
	}

	stage('Image Build') {
		imageBuild(CONTAINER_NAME, CONTAINER_TAG)
	}

	stage('Image run') {
		imageRun(CONTAINER_NAME, CONTAINER_TAG, CONTAINER_ID)
	}

}

def imageBuild(containerName, tag) {

	sh "docker build -t restappapiimage:$tag  -t $containerName --pull --no-cache ."
	echo "Image build complete"
}

def imageRun(containerName, tag, container_id) {
	if (container_id != '') {
		sh "docker stop $container_id"
	}
	sh "docker run -v /var/lib/jenkins/workspace/RestAssured/target/RESTfulExample2.war:/usr/local/tomcat/webapps/RESTfulExample2.war -i -d -p 8580:8580 restappapiimage:$tag"
	
	CONTAINER_ID_NEW = sh(
			script: "docker ps | grep restappapiimage:latest | awk '{print \$1}'",
returnStdout: true).trim()
	echo "container id new: ${CONTAINER_ID_NEW}"
	
	log_location = sh(
			script: "docker inspect --format='{{.LogPath}}' ${CONTAINER_ID_NEW}",
returnStdout: true).trim()
	echo "log location: ${log_location}"
	//sh "chown -R jenkins:jenkins ${log_location}"
	sh "whoami"
	//sh "sudo chown -R jenkins:jenkins '/var/lib/docker/containers/'"
	sh "echo osicpl@1 | sudo -S chmod -R 777 ${log_location}"
	//echo "permission done"
	var sucess_count = sh(
			script:"echo osicpl@1 | sudo -S grep -c 'INFO' ${log_location}",
returnStdout: true).trim()
	echo "hiii"
	if(sucess_count != 0){
		echo "in if"
		echo "sucess count : ${sucess_count}"
	}else {
		echo "in else"
		echo "sucess count : ${sucess_count}"
	}
	
	 
	echo "Image build complete"
}
