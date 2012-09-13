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

	private File lastDirectory;

	@FXML
	protected Pane content;

	@FXML
	protected TextField txtDirectory;

	@FXML
	protected Text statusLine;

	@FXML
	protected void onChooseDirectory(ActionEvent event) {
		DirectoryChooser fc = new DirectoryChooser();
		fc.setTitle("Choose directory");
		if (lastDirectory != null)
			fc.setInitialDirectory(lastDirectory);
		File directory = fc.showDialog(content.getScene().getWindow());
		if (directory == null) {
			clear();
			return;
		}
		lastDirectory = directory;
		txtDirectory.setText(directory.getAbsolutePath());
		update(directory.toPath());
	}

	protected void clear() {
		txtDirectory.setText("");
		statusLine.setText("");
	}

	@FXML
	abstract protected void onSave(ActionEvent event);

	abstract protected void update(Path dir);
}
