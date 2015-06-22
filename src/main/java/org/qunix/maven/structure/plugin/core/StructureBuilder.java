package org.qunix.maven.structure.plugin.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.qunix.maven.structure.plugin.core.types.StructureOutput;
import org.qunix.maven.structure.plugin.core.types.StructureType;

public class StructureBuilder<T> {

	public void build(Object root, StructureType type, boolean detailed, Log logger, String[] ignores, File... outputFile)
			throws MojoFailureException {

		if (root == null) {
			throw new MojoFailureException("Structure plugin couldnt recognize root!");
		}

		AbstractStructureNode parent = StructureFactory.getStructure(type, root, detailed);
		StringBuilder outputStr = new StringBuilder(parent.getHeader());

		buildChilds(parent, StructureOutput.LONG_TAB.getValue(), outputStr, ignores);

		outputStr.append(StructureOutput.LONG_NEW_LINE.getValue());

		if (ArrayUtils.isEmpty(outputFile) || outputFile[0] == null) {
			logger.info(outputStr.toString());
		} else {

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

	private StringBuilder buildChilds(AbstractStructureNode parent, String lvlStr, StringBuilder output, String[] ignores)
			throws MojoFailureException {

		AbstractStructureNode[] childs = (AbstractStructureNode[]) parent.getChilds();

		if (ArrayUtils.isEmpty(childs)) {
			return output;
		}
		for (int i = 0; i < childs.length; i++) {
			AbstractStructureNode child = childs[i];

			if (!child.isValid(ignores, parent.getNodeName())) {
				continue;
			}

			output.append(child.getOutput(lvlStr));

			if (!child.isEmpty()) {
				boolean hasMoreChilds = parent.hasMoreChilds(i, ignores);

				String separator = hasMoreChilds ? StructureOutput.COLON.getValue() : StructureOutput.SPACE.getValue();

				output = buildChilds(child, lvlStr + separator + StructureOutput.TAB.getValue(), output, ignores);
			}

		}

		return output;

	}

}
