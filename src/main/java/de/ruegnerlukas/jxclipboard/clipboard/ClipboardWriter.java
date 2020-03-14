package de.ruegnerlukas.jxclipboard.clipboard;

public interface ClipboardWriter {


	/**
	 * Writes the contents to the clipboard.
	 *
	 * @param content the content to write
	 */
	void write(String content);

}
