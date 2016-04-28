package com.anevis.chartgenerator.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

@SuppressWarnings("restriction")
public class DialogUtil {

	static Logger logger = Logger.getLogger(DialogUtil.class.getName());
	
	public static void alert(AlertType type, String title, String msg) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public static void configureFileChooser(FileChooser fileChooser) {
		fileChooser.setTitle("Choose File");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xls", "*.xls"),
				new FileChooser.ExtensionFilter("xlsx", "*.xlsx"));
	}
	
	public static void openFile(File file) {
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(file);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}

}
