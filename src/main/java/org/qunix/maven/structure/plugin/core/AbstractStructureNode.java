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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.qunix.maven.structure.plugin.core.types.StructureOutput;
import org.qunix.maven.structure.plugin.interfaces.StructureNode;

public abstract class AbstractStructureNode<T> implements StructureNode<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected StructureNode<T>[] childs;
	protected T content;
	protected boolean detailEnabled;

	/**
	 * @param content
	 * @param detailEnabled
	 * @throws MojoFailureException
	 */
	public AbstractStructureNode(T content, boolean detailEnabled) throws MojoFailureException {
		this.content = content;
		this.childs = getChilds();
		this.detailEnabled = detailEnabled;
	}


	
	
	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#getName()
	 */
	public String getName() throws MojoFailureException {
		if(!detailEnabled){
			return getNodeName();
		}
		
		return getDetailedName();
	}


	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#hasMoreChilds(int, java.lang.String[])
	 */
	public boolean hasMoreChilds(int index, String[] ignores) throws MojoFailureException {
		if (isEmpty() || this.childs.length - 1 == index) {
			return false;
		}

		for (int i = index + 1; i < this.childs.length; i++) {

			if (this.childs[i].isValid(ignores)) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#isValid(java.lang.String[], java.lang.String[])
	 */
	public boolean isValid(String[] ignores,String... parentName) {
		
		if(!ArrayUtils.isEmpty(parentName) && !StringUtils.isEmpty(this.getParentName())){
			if(!this.getParentName().equalsIgnoreCase(parentName[0])){
				return false;
			}
		}
		
		if (ArrayUtils.isEmpty(ignores)) {
			return true;
		}
		
		

		for (int i = 0; i < ignores.length; i++) {
			if (getNodeName().matches(ignores[i])) {
				return false;
			}
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#isEmpty()
	 */
	public boolean isEmpty() throws MojoFailureException{
		return ArrayUtils.isEmpty(this.childs);
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#getNodeName()
	 */
	public abstract String getNodeName();

	/**
	 * @return
	 * @throws MojoFailureException
	 */
	public abstract String getDetailedName()  throws MojoFailureException; 
	
	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.Printable#getOutput(java.lang.String)
	 */
	public String getOutput(String levelStr) throws MojoFailureException {
		
		StringBuilder sb = new StringBuilder(StructureOutput.NEW_LINE.getValue())
		.append(levelStr)
		.append(StructureOutput.VERTICAL_BAR.getValue())
		.append(StructureOutput.NEW_LINE.getValue())
		.append(levelStr)
		.append(StructureOutput.VERTICAL_BAR.getValue())
		.append(StructureOutput.NEW_LINE.getValue())
		.append(levelStr);
		if(isEmpty()){
			sb.append(StructureOutput.LOW_LINE.getValue());
		}else{
			sb.append(StructureOutput.BACKSLASH_LOWLINE.getValue());
		}
		
		return sb.append(getName())
		.toString();
	}

	/* (non-Javadoc)
	 * @see org.qunix.maven.structure.plugin.interfaces.StructureNode#getHeader()
	 */
	public String getHeader() throws MojoFailureException {
		return new StringBuilder(StructureOutput.NEW_LINE.getValue())
		.append(StructureOutput.NEW_LINE.getValue())
		.append("Project structure:")
		.append(StructureOutput.LONG_NEW_LINE.getValue())
		.append(StructureOutput.LONG_TAB.getValue())
		.append(getName()).toString();
	}
}
