package de.ruegnerlukas.jxclipboard.base.toolbar;

import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ToolActionEvent extends Publishable {


	/**
	 * The name of the tool.
	 */
	private final String toolName;

	/**
	 * If it is a toggleable tool, this flag indicates the new state (otherwise true).
	 */
	private final boolean isSelected;

}
