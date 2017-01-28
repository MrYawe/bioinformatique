# Bioinformatique

## Development

### Set up

#### JDK
This project uses Java 8.

To download the Java 8 JDK on Ubuntu:                                     
`$ sudo apt-get install openjdk-8-jdk`

#### Gradle
##### Linux
This project uses Gradle 3.3.

To download Gradle on Ubuntu:  
`$ curl -s https://get.sdkman.io | bash`  
Open a new terminal and install Gradle:  
`$ sdk install gradle 3.3`

##### Windows
https://docs.gradle.org/current/userguide/installation.html

#### Importing project with Gradle
When opening the project with IntelliJ, you might experience an error "Unregistered Gradle project", just click on the link provided then click OK.

If you can't see the files in IntelliJ, click on "View", "Tool Windows" and "Project".

To open the Gradle Window, click on "View", "Tool Windows" and "Gradle". In this window, you can manually refresh Gradle build or run tasks separately (build, clean, jar...). You can also generate the javadoc of the project.

### Using Gradle

#### Build
From the intellij directory:  
`$ gradle build` or `$ gradle idea` to create the files for IntelliJ only

`$ gradle clean` to clean all builds

`$ gradle build --refresh-dependencies` to force Gradle to download the dependencies

#### Tests
To run the JUnit tests:  
`$ gradle test`

You can find the reports in `build/reports/tests/test/index.html`  
The JUnit tests are also done when you build the whole project.

#### JAR Export
The JAR file is created on build.  

You can find it in `build/libs` and you can run it with `$ java -jar bioinfoCMPSSSW-{version}.jar` where {version} can be replaced with 1.0, 1.1...

To change the main class in the JAR file you juste have to change the attribute `Main-Class` of the task `jar` in `build.gradle` file
