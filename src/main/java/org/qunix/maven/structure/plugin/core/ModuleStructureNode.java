package org.qunix.maven.structure.plugin.core;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

public class ModuleStructureNode extends AbstractStructureNode<MavenProject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModuleStructureNode(MavenProject content, boolean detailEnabled) throws MojoFailureException {
		super(content, detailEnabled);
	}

	public AbstractStructureNode[] getChilds() throws MojoFailureException {
		List<MavenProject> projects = content.getCollectedProjects();
		if(CollectionUtils.isEmpty(projects)){
			return null;
		}
		AbstractStructureNode[] childs = new AbstractStructureNode[projects.size()];
		
		for (int i = 0; i < projects.size(); i++) {
			childs[i] = new ModuleStructureNode(projects.get(i), detailEnabled);
		}
		
		return childs;
	}


	@Override
	public String getNodeName() {
		return content.getArtifactId();
	}

	@Override
	public String getDetailedName() {
		StringBuilder sb = new StringBuilder(content.getGroupId());
		sb.append(" : ").append(content.getArtifactId());
		sb.append(" (").append(content.getVersion()).append(") ");
		sb.append(content.getPackaging());
		
		return sb.toString();

	}

	public String getParentName() {
		return content.getParent().getArtifactId();
	}

}
