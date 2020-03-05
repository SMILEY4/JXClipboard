package de.ruegnerlukas.jxclipboard.base.toolbar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ToolActionEvent {


	/**
	 * The id of this event type.
	 */
	public static final String EVENT_ID = "jxclipboard.toolbar.action";

	/**
	 * The name of the tool.
	 */
	private final String toolName;

	/**
	 * If it is a toggleable tool, this flag indicates the new state (otherwise true).
	 */
	private final boolean isSelected;

}
