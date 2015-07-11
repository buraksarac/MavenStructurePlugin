package org.qunix.maven.structure.plugin.core;

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

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.qunix.maven.structure.plugin.core.types.StructureType;
import org.qunix.maven.structure.plugin.interfaces.StructureNode;

/**
 * Factory class to return right instance by the given type
 * 
 * @author bsarac
 *
 */
public class StructureFactory {

	/**
	 * 
	 * Returns correct instance by the given type
	 * @param type {@link StructureType}
	 * @param parent Root object which needs to be wrapped
	 * @param detailed does user ask details?
	 * @return Wrapped {@link AbstractStructureNode} object
	 * @throws MojoFailureException
	 */
	@SuppressWarnings("rawtypes")
	public static final StructureNode getStructure(StructureType type, Object parent, boolean detailed) throws MojoFailureException {

		if (type.equals(StructureType.MAVEN_MODULE)) {

			//return module if the goal is modules
			return new ModuleStructureNode((MavenProject) parent, detailed);

		} else if (type.equals(StructureType.FILE)) {
			//return files if the goal is files
			return new FileStructureNode((File) parent, detailed);
			
		} else {
			
			//return folders if the goal is folders
			return new FolderStructureNode((File) parent, detailed);
		}

	}

}
