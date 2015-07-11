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
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.qunix.maven.structure.plugin.core.types.StructureOutput;
import org.qunix.maven.structure.plugin.core.types.StructureType;
import org.qunix.maven.structure.plugin.interfaces.StructureNode;

/**
 * Builder class to generate ascii tree from {@link AbstractStructureNode} 
 * 
 * @author bsarac
 *
 */
public class StructureBuilder {

	/**
	 * Collects user/env. parameters then instantiate wrapper {@link AbstractStructureNode} object and calls a recursive to put outputs together
	 * 
	 * @param root root node
	 * @param type {@link StructureType} (from maven goal)
	 * @param detailed does user wants details output
	 * @param logger default logger instance for mojo
	 * @param ignores list of regexpatterns to ignore
	 * @param outputFile does user wants to write output into file? If null then it will be printed into screen
	 * @throws MojoFailureException
	 */
	public void build(Object root, StructureType type, boolean detailed, Log logger, String[] ignores, File... outputFile)
			throws MojoFailureException {

		//null check
		if (root == null) {
			throw new MojoFailureException("Structure plugin couldnt recognize root!");
		}

		//get wrapped node so we can process
		StructureNode<?> parent = StructureFactory.getStructure(type, root, detailed);
		
		//get header (project structure...)
		StringBuilder outputStr = new StringBuilder(parent.getHeader());

		//call recursive if root has childs
		buildChilds(parent, StructureOutput.LONG_TAB.getValue(), outputStr, ignores);

		//some more space
		outputStr.append(StructureOutput.LONG_NEW_LINE.getValue());

		//if outputfile not specified put into screen
		if (ArrayUtils.isEmpty(outputFile) || outputFile[0] == null) {
			logger.info(outputStr.toString());
		} else {

			//if output file specified write into file
			//TODO carry into or call from utility class
			try {
				FileWriter fw = new FileWriter(outputFile[0]);
				fw.write(outputStr.toString());
				fw.flush();
				fw.close();
			} catch (IOException e) {
				logger.error(e);
				throw new MojoFailureException("Structure plugin couldnt write output to file!");

			}

		}
	}

	/**
	 * 
	 * recursive method to build output from nodes
	 * @param parent parent node
	 * @param lvlStr indentation
	 * @param output each recursive will refer to same {@link StringBuilder} instance
	 * @param ignores list of regex to ignore
	 * @return final output
	 * @throws MojoFailureException
	 */
	private StringBuilder buildChilds(StructureNode<?> parent, String lvlStr, StringBuilder output, String[] ignores)
			throws MojoFailureException {

		//get childs
		StructureNode<?>[] childs = (AbstractStructureNode[]) parent.getChilds();

		//check if there is any 
		if (ArrayUtils.isEmpty(childs)) {
			return output;
		}
		
		//iterate over childs
		for (int i = 0; i < childs.length; i++) {
			
			//shortcut
			StructureNode<?> child = childs[i];

			//this method does regex validation all goals and parent check just for modules
			if (!child.isValid(ignores, parent.getNodeName())) {
				continue;
			}

			//collect builtin string
			output.append(child.getOutput(lvlStr));

			//if child also has childs then inception:)
			if (!child.isEmpty()) {
				//this is for make up to print colons character on same level folders
				boolean hasMoreChilds = parent.hasMoreChilds(i, ignores);

				
				String separator = hasMoreChilds ? StructureOutput.COLON.getValue() : StructureOutput.SPACE.getValue();

				//call itself
				output = buildChilds(child, lvlStr + separator + StructureOutput.TAB.getValue(), output, ignores);
			}

		}

		return output;

	}

}
