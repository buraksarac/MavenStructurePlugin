package org.qunix.maven.structure.plugin.interfaces;

import org.apache.maven.plugin.MojoFailureException;

public interface Printable {

	public String getOutput(String levelStr)  throws MojoFailureException;
}
