package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedCommand;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.List;

public class ContentModuleView implements ModuleView {


	@Override
	public void initialize(final Pane pane) {
		Button btn = new Button("Content Button");
		Anchors.setAnchors(btn, 200, null, 0, 0);
		pane.getChildren().add(btn);
	}




	@Override
	public List<ExposedEvent> getExposedEvents() {
		return null;
	}




	@Override
	public List<ExposedCommand> getExposedCommands() {
		return null;
	}

}
