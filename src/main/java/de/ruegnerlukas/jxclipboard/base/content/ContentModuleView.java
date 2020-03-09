package de.ruegnerlukas.jxclipboard.base.content;

import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ContentModuleView implements ModuleView {

	@FXML
	public VBox boxContent;

	@Override
	public void initialize(final Pane pane) {
		Button btn = new Button("Content Button");
		Anchors.setAnchors(btn, 200, null, 0, 0);
		boxContent.getChildren().add(btn);
	}



	/*
	 * add entry command:
	 * - node: content
	 * 		the node to display in the content area list as a new entry
	 * - removable: boolean
	 * 		whether the entry should have a button to remove it
	 * - priority: int
	 * 		higher -> entry shows up on top
	 * 		lower -> entry at bottom
	 * 		equal priority -> newer entry on top
	 *
	 * remove entry command
	 * -> force remove the entry from the list
	 *
	 * remove entry event
	 * -> when an entry was removed from the list
	 * - byUser: boolean
	 * 		whether the entry was removed by the user (via the button)
	 *
	 *
	 *
	 */

}
