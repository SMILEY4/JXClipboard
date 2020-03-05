package de.ruegnerlukas.jxclipboard.base.toolbar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddToolCommand {


	/**
	 * The id of this command type
	 */
	public static final String COMMAND_ID = "jxclipboard.toolbar.addtool";


	/**
	 * The name of the tool.
	 */
	public final String toolName;

	/**
	 * Whether the tool is toggleable.
	 */
	boolean toggle;

}
