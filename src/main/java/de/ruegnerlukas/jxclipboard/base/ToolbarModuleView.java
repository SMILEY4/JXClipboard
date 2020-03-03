package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedCommand;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.List;

public class ToolbarModuleView implements ModuleView {


	@Override
	public void initialize(final Pane pane) {
		Button btn = new Button("A");
		btn.setPrefSize(30, 30);
		btn.setMaxSize(30, 30);
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
