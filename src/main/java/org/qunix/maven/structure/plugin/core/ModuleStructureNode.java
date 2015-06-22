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

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * Module wrapper. Implementation of {@link AbstractStructureNode} for maven Modules goal
 * @author ubuntu
 *
 */
public class ModuleStructureNode extends AbstractStructureNode<MavenProject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 * 
	 * @param content
	 * @param detailEnabled
	 * @throws MojoFailureException
	 */
	public ModuleStructureNode(MavenProject content, boolean detailEnabled) throws MojoFailureException {
		super(content, detailEnabled);
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#getChilds()
	 */
	public AbstractStructureNode[] getChilds() throws MojoFailureException {
		List<MavenProject> projects = content.getCollectedProjects();
		if(CollectionUtils.isEmpty(projects)){
			return null;
		}
		AbstractStructureNode[] childs = new AbstractStructureNode[projects.size()];
		
		for (int i = 0; i < projects.size(); i++) {
			childs[i] = new ModuleStructureNode(projects.get(i), detailEnabled);
		}
		
		return childs;
	}


	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.core.AbstractStructureNode#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return content.getArtifactId();
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.core.AbstractStructureNode#getDetailedName()
	 */
	@Override
	public String getDetailedName() {
		StringBuilder sb = new StringBuilder(content.getGroupId());
		sb.append(" : ").append(content.getArtifactId());
		sb.append(" (").append(content.getVersion()).append(") ");
		sb.append(content.getPackaging());
		
		return sb.toString();

	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#getParentName()
	 */
	public String getParentName() {
		return content.getParent().getArtifactId();
	}

}
