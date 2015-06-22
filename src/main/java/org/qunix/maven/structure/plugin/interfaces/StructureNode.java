package org.qunix.maven.structure.plugin.interfaces;

import java.io.Serializable;

import org.apache.maven.plugin.MojoFailureException;
import org.qunix.maven.structure.plugin.core.AbstractStructureNode;

public interface StructureNode<N> extends Printable, Serializable {

	public abstract AbstractStructureNode[] getChilds()  throws MojoFailureException;
	
	public abstract boolean hasMoreChilds(int index, String[] ignores)  throws MojoFailureException;

	public abstract boolean isValid(String[] ignores, String... parentName);
	
	public abstract boolean isEmpty()  throws MojoFailureException;
	
	public abstract String getNodeName();
	
	public abstract String getName()  throws MojoFailureException;
	
	public abstract String getHeader()  throws MojoFailureException;
	
	public String getParentName();
}
