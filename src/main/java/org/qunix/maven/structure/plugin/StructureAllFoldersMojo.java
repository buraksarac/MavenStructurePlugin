package org.qunix.maven.structure.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.qunix.maven.structure.plugin.core.StructureBuilder;
import org.qunix.maven.structure.plugin.core.types.StructureType;

/**
 * Goal to print all folders
 *
 * @goal folders
 * 
 * @phase process-sources
 */

public class StructureAllFoldersMojo extends AbstractMojo {
	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.basedir}"
	 * 
	 */
	private File rootDirectory;

	/**
	 * Regex pattern to ignore files.
	 * 
	 * @parameter alias="ignores"
	 * 
	 */

	private String[] mIgnores;

	/**
	 * @parameter
	 */
	private boolean detailed;

	/**
	 * @parameter
	 */
	private File outputDirectory;

	public void execute() throws MojoExecutionException, MojoFailureException {

		StructureBuilder builder = new StructureBuilder();

		builder.build(rootDirectory, StructureType.DIRECTORY, detailed, getLog(), mIgnores, outputDirectory);

	}

	/**
	 * This list will be injected by maven
	 * 
	 * @param ignores
	 */
	public void setIgnores(String[] ignores) {
		mIgnores = ignores;
	}
}
