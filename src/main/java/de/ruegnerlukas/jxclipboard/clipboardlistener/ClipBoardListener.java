package de.ruegnerlukas.jxclipboard.clipboardlistener;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import lombok.extern.slf4j.Slf4j;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

@Slf4j
public class ClipBoardListener extends Thread implements ClipboardOwner {


	/**
	 * How many milliseconds to wait after loosing ownership of the clipboard
	 */
	private static final long OWNERSHIP_COOLDOWN = 250;


	/**
	 * The system clipboard.
	 */
	private final Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	/**
	 * The callback executed when the clipboard was changed
	 */
	private final Callback<String> clipboardCallback;




	/**
	 * @param clipboardCallback the callback executed when the clipboard was changed
	 */
	public ClipBoardListener(final Callback<String> clipboardCallback) {
		this.clipboardCallback = clipboardCallback;
	}




	@Override
	public void run() {
		Transferable trans = systemClipboard.getContents(this);
		takeOwnership(trans);
	}




	@Override
	public void lostOwnership(final Clipboard clipboard, final Transferable transferable) {
		try {
			ClipBoardListener.sleep(OWNERSHIP_COOLDOWN);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		Transferable contents = systemClipboard.getContents(this);
		try {
			processClipboard(contents);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		takeOwnership(contents);
	}




	/**
	 * Take ownership of the clipboard
	 */
	private void takeOwnership(final Transferable transferable) {
		systemClipboard.setContents(transferable, this);
	}




	/**
	 * The clipboard has changed and a new clipboard event is triggered.
	 */
	public void processClipboard(final Transferable transferable) {
		try {
			if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				String clipboardContent = (String) transferable.getTransferData(DataFlavor.stringFlavor);
				clipboardCallback.execute(clipboardContent);
			}
		} catch (Exception ignored) {
		}
	}

}