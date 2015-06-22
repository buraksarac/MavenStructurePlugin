package org.qunix.maven.structure.plugin.core;

import java.io.File;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.qunix.maven.structure.plugin.core.types.StructureType;

public class StructureFactory {

	public static final AbstractStructureNode getStructure(StructureType type, Object parent, boolean detailed) throws MojoFailureException {

		if (type.equals(StructureType.MAVEN_MODULE)) {

			return new ModuleStructureNode((MavenProject) parent, detailed);

		} else if (type.equals(StructureType.FILE)) {
			return new FileStructureNode((File) parent, detailed);
		} else {
			return new FolderStructureNode((File) parent, detailed);
		}

	}

}
