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
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * File wrapper. Implementation of {@link AbstractStructureNode} for maven File goal
 * @author ubuntu
 *
 */
public class FileStructureNode extends AbstractStructureNode<File> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Default constructor to inform super
	 * @param content
	 * @param detailEnabled
	 * @throws MojoFailureException
	 */
	public FileStructureNode(File content, boolean detailEnabled) throws MojoFailureException {
		super(content, detailEnabled);
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#getChilds()
	 */
	public AbstractStructureNode[] getChilds() throws MojoFailureException {
		
		//
		File[] files = content.listFiles();
		
		//check if there is any child
		if(ArrayUtils.isEmpty(files)){
			return null;
		}
		
		//create local variable to return
		AbstractStructureNode[] childs = new AbstractStructureNode[files.length];
		
		//iterate over
		for (int i = 0; i < files.length; i++) {
			childs[i] = new FileStructureNode(files[i], detailEnabled);
		}
		
		return childs;
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.core.AbstractStructureNode#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return content.getName();
	}
	
	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.core.AbstractStructureNode#getDetailedName()
	 */
	@Override
	public String getDetailedName() throws MojoFailureException {
		StringBuilder sb = new StringBuilder(" [ "  + getNodeName() + " ] ");
		sb.append(" ").append(content.isHidden() ? "hidden" : StringUtils.EMPTY)
				.append(" ").append(content.isDirectory() ? ((isEmpty() ? 0 : this.childs.length) + " file") : content.length() + " byte").append(" ")
				.append(new Date(content.lastModified()).toString());

		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#getParentName()
	 */
	public String getParentName() {
		return null;
	}


	

}
