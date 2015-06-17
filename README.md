Maven structure plugin developed to print output of project files/modules/folders structure tree in ascii format.

Plugin has 3 goals:

  1.**printAll**: Prints all the files under build path
  
  2.**printFolders**: Prints just folders under build path
  
  3.**printModules**: Prinst all the modules under parent
  
  
  **Usage**: 
   i.e. if you want to print all files you will need to update your pom file as below:
   
   ```
   <project>
  
    <build>
		<plugins>

			<plugin>
				<groupId>org.u-db</groupId>
				<artifactId>structure-maven-plugin</artifactId>
				<version>0.0.1</version>
				<inherited>false</inherited> 
				<executions>
					<execution>
						<phase>compile</phase>
						<goals>
							<goal>
								printAll
							</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<ignores>
						<ignore>\.settings</ignore>
					</ignores>
				</configuration>
			</plugin>
		</plugins>

	</build>
	.....
	</project>
	
	
	
```
	
	
	
then output will look like:

	
```	
Project structure (all files):



				mavenstructuretest
				|
				|__ pom.xml
				|
				|
				\__ src
				:	|
				:	|
				:	\__ test
				:	:	|
				:	:	|
				:	:	\__ resources
				:	:	|
				:	:	|
				:	:	\__ java
				:	|
				:	|
				:	\__ main
				:	 	|
				:	 	|
				:	 	\__ resources
				:	 	|
				:	 	|
				:	 	\__ java
				|
				|
				\__ target
				:	|
				:	|
				:	\__ classes
				|
				|__ .classpath
				|
				|__ .project
	
```
`**<inherited>**` tag tells to plugin dont do execution on modules under this project if there is any. If you dont have a multi module project you can remove this tag.
`**<ignore>**` tag specifies the files needs to be ignored in regex pattern. If you want to list all files you can remove this tag.
	
	
Listing the folders pretty similar:
```
	<build>
		<plugins>

			<plugin>
				<groupId>org.u-db</groupId>
				<artifactId>structure-maven-plugin</artifactId>
				<version>0.0.1</version>
				<executions>
					<execution>
						<phase>compile</phase>
						<goals>
							<goal>
								printFolders
							</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>

	</build>
```


Then output will look like:
```


	mavenstructuretest
				|
				|
				\__ src
				:	|
				:	|
				:	\__ test
				:	:	|
				:	:	|
				:	:	\__ resources
				:	:	|
				:	:	|
				:	:	\__ java
				:	|
				:	|
				:	\__ main
				:	 	|
				:	 	|
				:	 	\__ resources
				:	 	|
				:	 	|
				:	 	\__ java
				|
				|
				\__ target
				:	|
				:	|
				:	\__ classes
				|
				|
				\__ .settings
				
				
```
	
	
And I recommend using 'inherited' tag if you want to list modules under the parent, example pom.xml for modules:
	
```


<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.u_db</groupId>
	<artifactId>test</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>
	<build>
		<plugins>

			<plugin>
				<groupId>org.u-db</groupId>
				<artifactId>structure-maven-plugin</artifactId>
				<version>0.0.1</version>
				<inherited>false</inherited>
				<executions>
					<execution>
						<phase>compile</phase>
						<goals>
							<goal>
								printModules
							</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>

	</build>
	<modules>
		<module>a</module>
		<module>b</module>
		<module>c</module>
	</modules>
</project>
```	
	
And output:

```


	[INFO] --- structure-maven-plugin:0.0.1:printModules (default) @ test ---
[INFO] 

Project structure (all modules):



				test
				|
				|__ a
				|
				|__ b
				|
				|__ d
				|
				|__ e
				|
				|__ f
				|
				|
				\__ c
				 	|
				 	|__ d
				 	|
				 	|__ e
				 	|
				 	|__ f
```


It is my first plugin so you can let me know if you face any issue. 
	
   
