def CONTAINER_NAME = "ositest"
	def CONTAINER_TAG = "latest"
	def DOCKER_HUB_USER = "chetant"
	def HTTP_PORT = "8099"

	node {
	CONTAINER_ID = sh(
			script: "docker ps | grep restappapiimage:latest | awk '{print \$1}'",
			returnStdout: true).trim()
		echo "container id: ${CONTAINER_ID}"

		stage('Checkout') {
		checkout scm
	}

	stage('Build') {
		sh "mvn clean install"
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
	
	sucess_count = sh(
			script: " sudo grep -c 'org.apache.catalina.startup.Catalina.start Server startup' ${log_location}",
returnStdout: true).trim()
	echo "sucess count : ${sucess_count}"
	 
	echo "Image build complete"
}
