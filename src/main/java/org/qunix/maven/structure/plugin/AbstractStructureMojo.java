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
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractStructureMojo extends AbstractMojo {
	/**
	 * Location of the file.
	 */
	@Parameter(defaultValue = "${project.basedir}")
	protected File rootDirectory;

	/**
	 * Regex pattern to ignore files.
	 */
	@Parameter(name="ignores")
	protected String[] mIgnores;

	@Parameter
	protected boolean detailed;

	@Parameter
	protected File outputDirectory;

	public void setIgnores(String[] ignores) {
		mIgnores = ignores;
	}
}
