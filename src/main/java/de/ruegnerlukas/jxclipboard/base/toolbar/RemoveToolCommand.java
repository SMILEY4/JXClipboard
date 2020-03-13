package de.ruegnerlukas.jxclipboard.base.toolbar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RemoveToolCommand {


	/**
	 * The id of this command type
	 */
	public static final String COMMAND_ID = "jxclipboard.toolbar.removetool";


	/**
	 * The name of the tool.
	 */
	private final String toolName;


}
