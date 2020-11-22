package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.jxclipboard.JXClipboardState;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiComponent;
import javafx.geometry.Pos;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.hBox;

public class Toolbar extends SuiComponent<JXClipboardState> {


	/**
	 * The exact height of the toolbar.
	 */
	public static final int TOOLBAR_HEIGHT = 40;


	/**
	 * The id of the injection point for tools
	 */
	public static final String TOOL_INJECTION_POINT = "toolbar.tools";



	public Toolbar() {
		super(state -> hBox()
				.id("toolbar")
				.styleClass("toolbar")
				.anchors(0, null, 0, 0)
				.sizeMax(1000000, TOOLBAR_HEIGHT)
				.sizeMin(0, TOOLBAR_HEIGHT)
				.spacing(5)
				.alignment(Pos.CENTER)
				.itemsInjectable(TOOL_INJECTION_POINT)
		);
	}


}
