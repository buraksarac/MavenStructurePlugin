package org.qunix.maven.structure.plugin.core.types;

public enum StructureOutput {

	
	SPACE(" "),
	NEW_LINE("\n"),
	COLON(":"),
	VERTICAL_BAR("|"),
	LOW_LINE("|__ "),
	BACKSLASH_LOWLINE("\\__ "),
	TAB("\t"),
	LONG_NEW_LINE("\n\n\n\n"),
	LONG_TAB("\t\t\t\t");

	private String value;

	private StructureOutput(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
