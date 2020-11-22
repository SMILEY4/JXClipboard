package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.jxclipboard.JXClipboardState;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.stream.IntStream;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.button;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.hBox;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.label;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.scrollPane;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.vBox;

@SuppressWarnings ("CheckStyle")
public class ContentArea extends SuiComponent<JXClipboardState> {


	public ContentArea() {
		super(state ->
				scrollPane()
						.id("content-area-scroll")
						.styleClass("content-area-scroll")
						.showScrollbars(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.AS_NEEDED)
						.anchors(Toolbar.TOOLBAR_HEIGHT, 0, 0, 0)
						.fitToWidth()
						.item(
								vBox()
										.id("content-area")
										.padding(5, 0, 0, 0)
										.styleClass("content-area")
										.spacing(5)
										.items(IntStream.range(0, state.getSavedEntries().size())
												.mapToObj(index -> Pair.of(index, state.getSavedEntries().get(index)))
												.map(pair -> buildEntry(pair.getLeft(), pair.getRight()))
										)
						)

		);
	}

// content, index, int -> rem "index" -> content, int


	private static NodeFactory buildEntry(final int index, final String content) {
		return hBox()
				.id("entry-" + content)
				.styleClass("entry")
				.spacing(5)
				.sizeMax(10000, 40)
				.alignment(Pos.CENTER_LEFT)
				.items(
						label()
								.id("label-content")
								.sizePreferred(100000, 40)
								.textContent(content),
						button()
								.id("btn-copy")
								.sizeMin(40, 40)
								.sizeMax(40, 40)
								.styleClass("btn-copy")
								.graphic(new FontIcon(), 0)
								.eventAction(".", e -> {
									StringSelection selection = new StringSelection(content);
									Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
									clipboard.setContents(selection, selection);
								}),
						button()
								.id("btn-remove")
								.sizeMin(40, 40)
								.sizeMax(40, 40)
								.styleClass("btn-remove")
								.graphic(new FontIcon(), 0)
								.eventAction(".", e -> {
									System.out.println("remove: " + content);
									final JXClipboardState state = new Provider<>(JXClipboardState.class).get();
									state.update(JXClipboardState.class, s -> s.removeEntry(content));
								})
				);
	}


}
