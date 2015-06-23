Maven structure plugin developed to print output of project files/modules/folders structure tree in ascii format.

Plugin has 3 goals:

  1.**files**: Prints all the files under build path
  
  2.**folders**: Prints just folders under build path
  
  3.**modules**: Prinst all the modules under parent
  

    
  **Usage**: You can run from command line: `mvn structure:folders` or `mvn structure:files` or if you have multimodule project then `mvn structure:modules`
   
   
   For continous integration you will need to update your pom file as below:
   
   ```
   <project>
  
   <build>
		<plugins>
	
			<plugin>
				<groupId>org.qunix</groupId>
				<artifactId>structure-maven-plugin</artifactId>
				<version>0.0.2</version>
				<inherited>false</inherited>
				<executions>
					<execution>
						<phase>compile</phase>
						<goals>
							<goal>
								files
							</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<ignores>
						<ignore>target.*</ignore>
						<ignore>\.settings.*</ignore>
						<ignore>\.project.*</ignore>
						<ignore>\.classpath.*</ignore>
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

     Project structure:



				mavenstructuretest
				|
				|
				|__ pom.xml
				|
				|
				\__ src
				 	|
				 	|
				 	\__ test
				 	:	|
				 	:	|
				 	:	|__ resources
				 	:	|
				 	:	|
				 	:	|__ java
				 	|
				 	|
				 	\__ main
				 	 	|
				 	 	|
				 	 	|__ resources
				 	 	|
				 	 	|
				 	 	|__ java
	
```
`<inherited>` tag tells to plugin dont do execution on modules under this project if there is any. If you dont have a multi module project you can remove this tag.
`<ignore>` tag specifies the files needs to be ignored in regex pattern. If you want to list all files you can remove this tag.
	
	
Listing the folders pretty similar:

		<plugin>
				<groupId>org.qunix</groupId>
				<artifactId>structure-maven-plugin</artifactId>
				<version>0.0.2</version>
				<executions>
					<execution>
						<phase>compile</phase>
						<goals>
							<goal>
								folders
							</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<ignores>
						<ignore>target.*</ignore>
						<ignore>\.settings.*</ignore>
						<ignore>\.project.*</ignore>
						<ignore>\.classpath.*</ignore>
					</ignores>
				</configuration>
			</plugin>
```
```
Then output will look like:

```


	mavenstructuretest
				|
				|
				\__ src
				 	|
				 	|
				 	\__ test
				 	:	|
				 	:	|
				 	:	|__ resources
				 	:	|
				 	:	|
				 	:	|__ java
				 	|
				 	|
				 	\__ main
				 	 	|
				 	 	|
				 	 	|__ resources
				 	 	|
				 	 	|
				 	 	|__ java
				
				
```
	
	

 
   And I recommend using `<inherited>false</inherited>` tag if you want to list modules under the parent, example pom.xml for modules:
	
```



			<plugin>
				<groupId>org.qunix</groupId>
				<artifactId>structure-maven-plugin</artifactId>
				<version>0.0.1</version>
				<inherited>false</inherited>
				<executions>
					<execution>
						<phase>compile</phase>
						<goals>
							<goal>
								modules
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






				test
				|
				|__ a
				|
				|__ b
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


 You can also print output into a file by using `<outputDirectory>` tag in`<configuration>` (i.e. output.txt) so output will be saved into project root directory/output.txt. If you want to print details then set `<detailed>true</detailed>`. If you have enabled details then node names will be as following:
 
   **Modules**:group id:artifact id: version: packaging
   **Files**: File name + hidden +   size +  last modified
   **Folders**: Folder name + hidden + quantity of files + last modified  
   
   
   
*Detailed modules:*
   
```

		com.u_db : test (0.0.1-SNAPSHOT) pom
						|
						|
						|__ com.u_db : a (0.0.1-SNAPSHOT) jar
						|
						|
						|__ com.u_db : b (0.0.1-SNAPSHOT) jar
						|
						|
						\__ com.u_db : c (0.0.1-SNAPSHOT) pom
						 	|
						 	|
						 	|__ com.u_db : d (0.0.1-SNAPSHOT) jar
						 	|
						 	|
						 	|__ com.u_db : e (0.0.1-SNAPSHOT) jar
						 	|
						 	|
						 	|__ com.u_db : f (0.0.1-SNAPSHOT) jar

```

*Detailed folders:*

```

			 [ mavenstructuretest ]   6 file Tue Jun 23 00:58:19 CEST 2015
							|
							|
							\__  [ src ]   2 file Wed Jun 17 01:58:24 CEST 2015
							 	|
							 	|
							 	\__  [ test ]   2 file Wed Jun 17 01:58:24 CEST 2015
							 	:	|
							 	:	|
							 	:	|__  [ resources ]   0 file Wed Jun 17 01:58:24 CEST 2015
							 	:	|
							 	:	|
							 	:	|__  [ java ]   0 file Wed Jun 17 01:58:24 CEST 2015
							 	|
							 	|
							 	\__  [ main ]   2 file Wed Jun 17 01:58:24 CEST 2015
							 	 	|
							 	 	|
							 	 	|__  [ resources ]   0 file Wed Jun 17 01:58:24 CEST 2015
							 	 	|
							 	 	|
							 	 	|__  [ java ]   0 file Wed Jun 17 01:58:24 CEST 2015



```

*Detailed files:*

```


				 [ mavenstructuretest ]   6 file Tue Jun 23 00:57:05 CEST 2015
								|
								|
								|__  [ pom.xml ]   962 byte Tue Jun 23 00:56:56 CEST 2015
								|
								|
								\__  [ src ]   2 file Wed Jun 17 01:58:24 CEST 2015
								 	|
								 	|
								 	\__  [ test ]   2 file Wed Jun 17 01:58:24 CEST 2015
								 	:	|
								 	:	|
								 	:	|__  [ resources ]   0 file Wed Jun 17 01:58:24 CEST 2015
								 	:	|
								 	:	|
								 	:	|__  [ java ]   0 file Wed Jun 17 01:58:24 CEST 2015
								 	|
								 	|
								 	\__  [ main ]   2 file Wed Jun 17 01:58:24 CEST 2015
								 	 	|
								 	 	|
								 	 	|__  [ resources ]   0 file Wed Jun 17 01:58:24 CEST 2015
								 	 	|
								 	 	|
								 	 	|__  [ java ]   0 file Wed Jun 17 01:58:24 CEST 2015
								 	 	
								 	 	


```
It is my first plugin so you can let me know if you face any issue. 
	
   
