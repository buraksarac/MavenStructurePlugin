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

import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

/**
 * Goal which touches a timestamp file.
 *
 * @goal printModules
 * 
 * @phase process-sources
 */

public class StructureAllModules extends AbstractMojo {

	/**
	 * Regex pattern to ignore files.
	 * 
	 * @parameter alias="ignores"
	 * 
	 */
	private String[] mIgnores;

	/**
	 * @component
	 */
	private MavenProject project;

	private static final String NEW_LINE = "\n";
	private static final String LONG_NEW_LINE = "\n\n\n\n";
	private static final String TAB = "\t";
	private static final String LONG_TAB = "\t\t\t\t";
	private static final String VERTICAL_CONNECTOR = "|";
	private static final String HORIZONTAL_CONNECTOR = "\\__ ";

	public void execute() throws MojoExecutionException {

		project.getModules();

		StringBuilder sb = new StringBuilder(NEW_LINE);
		sb.append(NEW_LINE).append("Project structure (all modules):");
		sb.append(LONG_NEW_LINE).append(LONG_TAB).append(project.getArtifactId());
		try {
			checkModules(project, LONG_TAB, getLog(), sb);

			sb.append(LONG_NEW_LINE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException("Maven structure plugin couldnt scan filesystem!", e);
		}

		getLog().info(sb.toString());

	}

	private StringBuilder checkModules(MavenProject project, String lvl, Log log, StringBuilder str) throws IOException {

		List<MavenProject> modules = project.getCollectedProjects();

		for (int i = 0; i < modules.size(); i++) {

			if (hasRegexMatch(modules.get(i))) {
				continue;
			}
			
			if (!modules.get(i).getParent().getArtifactId()
					.equalsIgnoreCase(project.getArtifactId())) {
				continue;
			}
			
			if (hasModules(modules.get(i))) {

				str.append(NEW_LINE).append(lvl).append(VERTICAL_CONNECTOR).append(NEW_LINE).append(lvl).append(VERTICAL_CONNECTOR)
						.append(NEW_LINE).append(lvl).append(HORIZONTAL_CONNECTOR).append(modules.get(i).getArtifactId());

				boolean hasMoreDirChilds = hasMoreFiles(i, modules);

				String separator = hasMoreDirChilds ? ":" : " ";
				str = checkModules(modules.get(i), lvl + separator + TAB, log, str);

			} else {
				str.append(NEW_LINE).append(lvl).append(VERTICAL_CONNECTOR).append(NEW_LINE).append(lvl).append(VERTICAL_CONNECTOR)
						.append("__ ").append(modules.get(i).getArtifactId());

			}
		}

		return str;

	}

	private final boolean hasModules(MavenProject project) {
		return project.getCollectedProjects() != null && project.getCollectedProjects().size() > 0;
	}

	private final boolean hasMoreFiles(int index, List<MavenProject> modules) {

		if (modules.size() < 1 || modules.size() - 1 == index) {
			return false;
		}

		for (int i = index + 1; i < modules.size(); i++) {
			if (!hasRegexMatch(modules.get(i))) {
				return true;
			}
		}

		return false;
	}

	private final boolean hasRegexMatch(MavenProject project) {
		if (mIgnores == null) {
			return false;
		}

		for (int i = 0; i < mIgnores.length; i++) {
			if (project.getArtifactId().matches(mIgnores[i])) {
				return true;
			}
		}

		return false;
	}

	public void setIgnores(String[] ignores) {
		mIgnores = ignores;
	}
}
