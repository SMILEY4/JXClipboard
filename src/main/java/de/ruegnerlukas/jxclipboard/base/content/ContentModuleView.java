package de.ruegnerlukas.jxclipboard.base.content;

import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ContentModuleView implements ModuleView {


	@Override
	public void initialize(final Pane pane) {
		Button btn = new Button("Content Button");
		Anchors.setAnchors(btn, 200, null, 0, 0);
		pane.getChildren().add(btn);
	}


}
