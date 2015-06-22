package org.qunix.maven.structure.plugin.core;

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

	protected AbstractStructureNode[] childs;
	protected T content;
	protected boolean detailEnabled;

	public AbstractStructureNode(T content, boolean detailEnabled) throws MojoFailureException {
		this.content = content;
		this.childs = getChilds();
		this.detailEnabled = detailEnabled;
	}


	
	
	public String getName() throws MojoFailureException {
		if(!detailEnabled){
			return getNodeName();
		}
		
		return getDetailedName();
	}


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

	public boolean isEmpty() throws MojoFailureException{
		return ArrayUtils.isEmpty(this.childs);
	}

	public abstract String getNodeName();

	public abstract String getDetailedName()  throws MojoFailureException; 
	
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

	public String getHeader() throws MojoFailureException {
		return new StringBuilder(StructureOutput.NEW_LINE.getValue())
		.append(StructureOutput.NEW_LINE.getValue())
		.append("Project structure:")
		.append(StructureOutput.LONG_NEW_LINE.getValue())
		.append(StructureOutput.LONG_TAB.getValue())
		.append(getName()).toString();
	}
}
