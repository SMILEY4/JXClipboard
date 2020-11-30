open module JXClipboard {
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
//	requires transitive org.slf4j;
	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.annotation;
	requires transitive SimpleApplication;
	requires transitive org.kordamp.iconli.core;
	requires transitive org.kordamp.ikonli.fontawesome;
	requires transitive org.kordamp.ikonli.javafx;
	requires static lombok;
	requires java.datatransfer;
	requires java.desktop;
}