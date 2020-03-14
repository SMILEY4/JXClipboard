open module JXClipboard {
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive org.slf4j;
	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.annotation;
	requires transitive SimpleApplication;
	requires static lombok;
	requires java.datatransfer;
	requires java.desktop;
}