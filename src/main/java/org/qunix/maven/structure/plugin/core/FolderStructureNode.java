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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.qunix.maven.structure.plugin.interfaces.StructureNode;

/**
 * Folder wrapper. Implementation of {@link AbstractStructureNode} for maven Folders goal
 * @author bsarac
 *
 */
public class FolderStructureNode extends FileStructureNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 * @param content
	 * @param detailEnabled
	 * @throws MojoFailureException
	 */
	public FolderStructureNode(File content, boolean detailEnabled) throws MojoFailureException {
		super(content, detailEnabled);
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.core.AbstractStructureNode#isValid(java.lang.String[], java.lang.String[])
	 */
	@Override
	public boolean isValid(String[] ignores, String... params) {
		
		//we need to check additionally if this is a folder
		return super.isValid(ignores) && content.isDirectory();
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.core.FileStructureNode#getChilds()
	 */
	public StructureNode<File>[] getChilds() throws MojoFailureException {

		File[] files = content.listFiles();
		
		if(ArrayUtils.isEmpty(files)){
			return null;
		}
		StructureNode<File>[] childs = new AbstractStructureNode[files.length];
		for (int i = 0; i < files.length; i++) {
			childs[i] = new FolderStructureNode(files[i], detailEnabled);
		}
		return childs;
	}

}
