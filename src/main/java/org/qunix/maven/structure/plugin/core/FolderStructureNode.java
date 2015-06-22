package org.qunix.maven.structure.plugin.core;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.maven.plugin.MojoFailureException;

public class FolderStructureNode extends FileStructureNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FolderStructureNode(File content, boolean detailEnabled) throws MojoFailureException {
		super(content, detailEnabled);
	}

	@Override
	public boolean isValid(String[] ignores, String... params) {
		return super.isValid(ignores) && content.isDirectory();
	}

	public AbstractStructureNode[] getChilds() throws MojoFailureException {

		File[] files = content.listFiles();
		
		if(ArrayUtils.isEmpty(files)){
			return null;
		}
		AbstractStructureNode[] childs = new AbstractStructureNode[files.length];
		for (int i = 0; i < files.length; i++) {
			childs[i] = new FolderStructureNode(files[i], detailEnabled);
		}
		return childs;
	}

}
