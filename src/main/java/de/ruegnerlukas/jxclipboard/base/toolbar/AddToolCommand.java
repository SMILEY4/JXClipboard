package de.ruegnerlukas.jxclipboard.base.toolbar;

import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddToolCommand extends Publishable {


	/**
	 * The name of the tool.
	 */
	private final String toolName;

	/**
	 * Whether the tool is toggleable.
	 */
	private boolean toggle;

}
