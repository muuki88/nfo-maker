package de.mukis.nfo.maker;

import java.io.File;
import java.nio.file.Path;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

abstract public class ScrapController implements Initializable {

	@FXML
	protected Pane content;

	@FXML
	protected TextField txtDirectory;

	@FXML
	protected Text statusLine;

	@FXML
	protected void onChooseDirectory(ActionEvent event) {
		DirectoryChooser fc = new DirectoryChooser();
		File directory = fc.showDialog(content.getScene().getWindow());
		if (directory == null) {
			clear();
			return;
		}
		update(directory.toPath());
	}

	abstract protected void clear();

	abstract protected void update(Path dir);
}
