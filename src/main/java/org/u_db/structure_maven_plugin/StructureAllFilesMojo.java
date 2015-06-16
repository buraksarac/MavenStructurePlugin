package org.u_db.structure_maven_plugin;

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
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/**
 * Goal which touches a timestamp file.
 *
 * @goal printAll
 * 
 * @phase process-sources
 */

public class StructureAllFilesMojo extends AbstractMojo {
	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.basedir}"
	 * 
	 */
	private File outputDirectory;

	/**
	 * Regex pattern to ignore files.
	 * 
	 * @parameter alias="ignores"
	 * 
	 */
	private String[] mIgnores;

	// bunch of string constants
	private static final String NEW_LINE = "\n";
	private static final String LONG_NEW_LINE = "\n\n\n\n";
	private static final String TAB = "\t";
	private static final String LONG_TAB = "\t\t\t\t";
	private static final String VERTICAL_CONNECTOR = "|";
	private static final String HORIZONTAL_CONNECTOR = "\\__ ";
	private static final String HEADER = "Project structure (all files):";
	private static final String NOT_SCANNED = "Maven structure plugin couldnt scan filesystem!";
	private static final String DOESNT_EXIST = "Specified build path doesnt exist: ";

	public void execute() throws MojoExecutionException {

		// project base directory injected
		File f = outputDirectory;

		// Start building output
		StringBuilder sb = new StringBuilder(NEW_LINE);
		sb.append(NEW_LINE).append(HEADER);
		sb.append(LONG_NEW_LINE).append(LONG_TAB).append(f.getName());
		if (f.exists()) {
			try {
				// scan subdirectories
				checkFiles(outputDirectory, LONG_TAB, getLog(), sb);

				sb.append(LONG_NEW_LINE);
			} catch (IOException e) {
				// throw exception if any
				throw new MojoExecutionException(NOT_SCANNED, e);
			}

		} else {

			throw new MojoExecutionException(DOESNT_EXIST + f.getAbsolutePath());
		}

		// finally print output
		getLog().info(sb.toString());

	}

	private StringBuilder checkFiles(File file, String lvl, Log log, StringBuilder str) throws IOException {

		// get list of files
		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {

			// check if any file needs to be ignored
			if (hasRegexMatch(files[i])) {
				continue;
			}

			// if it is directory we need to scan also its files by recursive
			if (files[i].isDirectory()) {

				// build output
				str.append(NEW_LINE).append(lvl).append(VERTICAL_CONNECTOR).append(NEW_LINE).append(lvl).append(VERTICAL_CONNECTOR)
						.append(NEW_LINE).append(lvl).append(HORIZONTAL_CONNECTOR).append(files[i].getName());

				// this is for makeup
				boolean hasMoreDirChilds = hasMoreSiblings(i, files);

				String separator = hasMoreDirChilds ? ":" : " ";

				// get what we found from subdirectories
				str = checkFiles(files[i], lvl + separator + TAB, log, str);

			} else {
				// if this is a file simple build new line
				str.append(NEW_LINE).append(lvl).append(VERTICAL_CONNECTOR).append(NEW_LINE).append(lvl).append(VERTICAL_CONNECTOR)
						.append("__ ").append(files[i].getName());

			}
		}

		return str;

	}

	/**
	 * Returns true if there is any other file after specified index
	 * 
	 * @param index
	 * @param files
	 * @return
	 */
	private final boolean hasMoreSiblings(int index, File[] files) {

		if (files.length < 1 || files.length - 1 == index) {
			return false;
		}

		for (int i = index + 1; i < files.length; i++) {
			// check if needs to be ignored
			if (!hasRegexMatch(files[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * Checks if user has specified ignore list
	 * 
	 * @param file file to check
	 * @return true if there is a match
	 */
	private final boolean hasRegexMatch(File file) {
		//check if list is injected
		if (mIgnores == null) {
			return false;
		}

		//check if any file is matching else return immediately
		for (int i = 0; i < mIgnores.length; i++) {
			if (file.getName().matches(mIgnores[i])) {
				return true;
			}
		}

		return false;
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
