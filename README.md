# Bioinformatique

## Development

### Set up

#### JDK
This project uses Java 8.

To download the Java 8 JDK on Ubuntu:                                     
`$ sudo apt-get install openjdk-8-jdk`

#### Gradle
This project uses Gradle 3.3.

To download Gradle on Ubuntu:  
`$ curl -s https://get.sdkman.io | bash`  
Open a new terminal and install Gradle:  
`$ sdk install gradle 3.3`

### Using Gradle

#### Build
From the intellij directory:  
`$ gradle build` or `$ gradle idea` to create the files for IntelliJ only

`$ gradle clean` to clean all builds

`$ gradle build --refresh-dependencies` to force Gradle to download the dependencies

#### JAR Export
The JAR file is created on build.  

You can find it in build/libs and you can run it with `$ java -jar bioinfoCMPSSSW-{version}.jar` where {version} can be replaced with 1.0, 1.1...

To change the main class in the JAR file you juste have to change the attribute `Main-Class` of the task `jar` in `build.gradle` file
