package org.qunix.maven.structure.plugin.core;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoFailureException;

public class FileStructureNode extends AbstractStructureNode<File> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileStructureNode(File content, boolean detailEnabled) throws MojoFailureException {
		super(content, detailEnabled);
	}

	public AbstractStructureNode[] getChilds() throws MojoFailureException {
		
		File[] files = content.listFiles();
		if(ArrayUtils.isEmpty(files)){
			return null;
		}
		AbstractStructureNode[] childs = new AbstractStructureNode[files.length];
		for (int i = 0; i < files.length; i++) {
			childs[i] = new FileStructureNode(files[i], detailEnabled);
		}
		return childs;
	}

	@Override
	public String getNodeName() {
		return content.getName();
	}
	
	@Override
	public String getDetailedName() throws MojoFailureException {
		StringBuilder sb = new StringBuilder(" [ "  + getNodeName() + " ] ");
		sb.append(" ").append(content.isHidden() ? "hidden" : StringUtils.EMPTY)
				.append(" ").append(content.isDirectory() ? ((isEmpty() ? 0 : this.childs.length) + " file") : content.length() + " byte").append(" ")
				.append(new Date(content.lastModified()).toString());

		return sb.toString();
	}

	public String getParentName() {
		return null;
	}


	

}
