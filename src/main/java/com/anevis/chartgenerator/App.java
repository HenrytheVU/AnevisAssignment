package com.anevis.chartgenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.anevis.chartgenerator.exporter.ExporterPieChart;
import com.anevis.chartgenerator.gui.DialogUtil;
import com.anevis.chartgenerator.importer.ImporterXls;
import com.anevis.chartgenerator.model.dao.PieChartDao;
import com.anevis.chartgenerator.model.entity.PieChartEntity;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public final class App extends Application {

	Logger logger = Logger.getLogger(App.class.getName());

	private List<PieChartEntity> entityList = new ArrayList<PieChartEntity>();
	private ImporterXls importer;
	private ExporterPieChart exporter;
	private PieChartDao pieChartDao;
	private File selectedFile;

	@Override
	public void start(final Stage stage) {
		stage.setTitle("Pie Chart Generator");

		final FileChooser fileChooser = new FileChooser();
		final Button openButton = new Button("Open a pie-chart file (.xls)");
		final Button generateButton = new Button("Generate Chart");
		final Button saveButton = new Button("Save to DB");
		TextField selectedFileTextField = new TextField();
		selectedFileTextField.setEditable(false);
		selectedFileTextField.setMinWidth(600);
		selectedFileTextField.setText("No file selected...");

		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				DialogUtil.configureFileChooser(fileChooser);
				selectedFile = fileChooser.showOpenDialog(stage);
				if (selectedFile != null) {
					importer = new ImporterXls();
					importer.importFile(selectedFile);
					entityList = importer.getEntityList();
					selectedFileTextField.setText(selectedFile.getPath());
				}
			}
		});

		generateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if (selectedFile != null) {
					logger.info("Generating File");
					exporter = new ExporterPieChart();
					String exportedFileName = selectedFile.getName().replace(".xls", ".pdf");
					String exportedFilePath = selectedFile.getParent() + File.separator + exportedFileName;
					exporter.exportPdf(entityList, exportedFilePath);
					String title = "PDF SUCCESSFULLY EXPORTED";
					String msg = "PDF exported to: " + exportedFilePath;
					DialogUtil.alert(AlertType.INFORMATION, title, msg);
					DialogUtil.openFile(new File(exportedFilePath));
				} else {
					String title = "NO INPUT FILE!";
					String msg = "Please select a proper pie-chart .xls file";
					DialogUtil.alert(AlertType.ERROR, title, msg);
				}
			}
		});

		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!entityList.isEmpty()) {
					pieChartDao = new PieChartDao();
					pieChartDao.saveAll(entityList);
					String title = "SAVED";
					String msg = "All data are persisted in the database";
					DialogUtil.alert(AlertType.INFORMATION, title, msg);
				} else {
					String title = "NO INPUT FILE!";
					String msg = "Nothing to save! Please import a pie-chart .xls file first";
					DialogUtil.alert(AlertType.ERROR, title, msg);
				}
			}
		});

		final GridPane inputGridPane = new GridPane();

		GridPane.setConstraints(openButton, 0, 1);
		GridPane.setConstraints(generateButton, 0, 2);
		GridPane.setConstraints(saveButton, 0, 3);
		GridPane.setConstraints(selectedFileTextField, 0, 4);
		inputGridPane.setHgap(6);
		inputGridPane.setVgap(6);
		inputGridPane.getChildren().addAll(openButton, generateButton, saveButton, selectedFileTextField);

		final Pane rootGroup = new VBox(12);
		rootGroup.getChildren().addAll(inputGridPane);
		rootGroup.setPadding(new Insets(12, 12, 12, 12));

		stage.setScene(new Scene(rootGroup));
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
