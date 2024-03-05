<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	
	<artifactId>${sysToolTablesEntity.projectName}</artifactId>
	<groupId>${sysToolTablesEntity.packageName}</groupId>
	<version>${r"${"}dooleen.version}</version>
	<packaging>jar</packaging>
	<name>${sysToolTablesEntity.projectName}</name>
	
	<parent>
		<groupId>com.dooleen</groupId>
		<artifactId>dooleen-common-parent</artifactId>
		<version>1.0.0-SNAPSHOT</version>
		<relativePath>../dooleen-common-parent</relativePath>
	</parent>
	<dependencies>
		 <dependency>
			<groupId>com.dooleen</groupId>
			<artifactId>dooleen-common-core</artifactId>
	        <version>${r"${"}dooleen.version}</version>
			 <exclusions>
				 <exclusion>
					 <artifactId>poi</artifactId>
					 <groupId>org.apache.poi</groupId>
				 </exclusion>
			 </exclusions>
		</dependency>
	</dependencies>

</project>
