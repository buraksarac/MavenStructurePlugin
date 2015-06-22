package org.qunix.maven.structure.plugin.interfaces;

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

import java.io.Serializable;

import org.apache.maven.plugin.MojoFailureException;
import org.qunix.maven.structure.plugin.core.AbstractStructureNode;

public interface StructureNode<N> extends Printable, Serializable {

	/**
	 * Returns childs of element if any exist
	 * @return list of {@link AbstractStructureNode}
	 * @throws MojoFailureException
	 */
	public abstract AbstractStructureNode[] getChilds()  throws MojoFailureException;
	
	/**
	 * Utility method for below case:</br>
	 * While you are iterating you know that you know there is more files but you are not sure if those files needs to be ignored.
	 * Then this utility class can help by given index and the ignore list
	 * @param index current index of iteration so the rest will be handled
	 * @param ignores regex patterns
	 * @return {@link Boolean}
	 * @throws MojoFailureException
	 */
	public abstract boolean hasMoreChilds(int index, String[] ignores)  throws MojoFailureException;

	/**
	 * Regex validation for single node
	 * 
	 * @param ignores regex patterns
	 * @param parentName this is for modules to validate if they are not printed multiple time referring to their parent
	 * @return {@link Boolean}
	 */
	public abstract boolean isValid(String[] ignores, String... parentName);
	
	/**
	 * @return false if there is no child
	 * @throws MojoFailureException
	 */
	public abstract boolean isEmpty()  throws MojoFailureException;
	
	/**
	 * Returns file.name or artifactId for maven project
	 * 
	 * @return {@link String}
	 */
	public abstract String getNodeName();
	
	/**
	 * 
	 * if detailed output is not asked then it will call {@link StructureNode#getNodeName()} else it will return full details
	 * @return
	 * @throws MojoFailureException
	 */
	public abstract String getName()  throws MojoFailureException;
	
	/**
	 * 
	 * Header of output 
	 * @return {@link String}
	 * @throws MojoFailureException
	 */
	public abstract String getHeader()  throws MojoFailureException;
	
	/**
	 * 
	 * this is for modules to validate if they are not printed multiple time referring to their parent, files always return null
	 * @return
	 */
	public String getParentName();
}
