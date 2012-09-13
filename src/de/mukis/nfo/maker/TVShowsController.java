package de.mukis.nfo.maker;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;

import javax.xml.bind.JAXB;

import de.mukis.nfo.maker.model.Episodedetails;
import de.mukis.nfo.maker.scraper.TVShowScraper;
import de.mukis.nfo.maker.ui.ITVShowItem;
import de.mukis.nfo.maker.ui.ITVShowItem.Episode;
import de.mukis.nfo.maker.ui.ITVShowItem.Season;
import de.mukis.nfo.maker.ui.ITVShowItem.Show;

public class TVShowsController implements Initializable {

	private Episodedetails episode;
	private Show show;
	private static final ObservableList<Number> DEFAULT_EPISODES = FXCollections.observableArrayList(new Number[] { 1, 2, 3, 4, 5, 6, 7, 8,
			9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 });

	@FXML
	private Pane tvContent;

	@FXML
	private TextField txtDirectory;

	@FXML
	private TextField txtTvShowTitle;

	@FXML
	private Text statusLine;

	/* ========= Details View ====== */

	@FXML
	private TreeView<ITVShowItem> seasonTree;

	@FXML
	private TextField txtEpisodeTitle;

	@FXML
	private ComboBox<Number> choiceEpisodeNumber;

	@FXML
	private ComboBox<Number> choiceSeasonNumber;

	/* =============================== */

	@FXML
	protected void onChooseDirectory(ActionEvent event) {
		DirectoryChooser fc = new DirectoryChooser();
		File directory = fc.showDialog(tvContent.getScene().getWindow());

		// clear
		if (directory == null) {
			txtDirectory.setText("");
			txtTvShowTitle.setText("");
			seasonTree.setRoot(null);
			return;
		}
		txtDirectory.setText(directory.getAbsolutePath());
		Path p = Paths.get(directory.getAbsolutePath());
		try {
			TVShowScraper scraper = new TVShowScraper(p);
			show = scraper.findShow();

			TreeItem<ITVShowItem> root = new TreeItem<>((ITVShowItem) show);
			for (Season season : show.getSeasons().values()) {
				TreeItem<ITVShowItem> seasonItem = new TreeItem<>((ITVShowItem) season);
				for (Episode episode : season.getEpisodes().values()) {
					TreeItem<ITVShowItem> episodeItem = new TreeItem<>((ITVShowItem) episode);
					seasonItem.getChildren().add(episodeItem);
				}
				root.getChildren().add(seasonItem);
			}

			seasonTree.setRoot(root);
			if (show != null) {
				show.getTvShow().titleProperty().unbindBidirectional(txtTvShowTitle.textProperty());
			}
			txtTvShowTitle.textProperty().bindBidirectional(show.getTvShow().titleProperty());

			// Seasonlist
			ObservableList<Number> seaonsList = FXCollections.observableArrayList(show.getSeasons().keySet());
			choiceSeasonNumber.setItems(seaonsList);
			choiceEpisodeNumber.setItems(DEFAULT_EPISODES);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	protected void onSave(ActionEvent event) {
		try (OutputStream outShow = Files.newOutputStream(show.getPath().resolve("tvshow.nfo"))) {
			JAXB.marshal(show.getTvShow(), outShow);

			for (Season season : show.getSeasons().values()) {
				for (Episode episode : season.getEpisodes().values()) {
					String episodeNFO = episode.getPath().getFileName().toString().replaceAll("\\.\\w{3}$", ".nfo");
					Path episodeNFOPath = episode.getPath().getParent().resolve(episodeNFO);
					try (OutputStream outEpisode = Files.newOutputStream(episodeNFOPath)) {
						JAXB.marshal(episode.getDetails(), outEpisode);
					}

				}
			}
		} catch (IOException e) {
			// TODO exception handling
			e.printStackTrace();
		}
		statusLine.setText("Successfully created all files");
	}

	@FXML
	protected void onFileSelected(MouseEvent event) {
		TreeItem<ITVShowItem> item = seasonTree.getSelectionModel().getSelectedItem();
		if (item != null && item.getValue() instanceof Episode) {
			// unbind
			if (episode != null) {
				episode.titleProperty().unbindBidirectional(txtEpisodeTitle.textProperty());
				episode.seasonProperty().unbindBidirectional(choiceSeasonNumber.valueProperty());
				episode.episodeProperty().unbindBidirectional(choiceEpisodeNumber.valueProperty());
			}

			// bind
			episode = ((Episode) item.getValue()).getDetails();
			txtEpisodeTitle.textProperty().bindBidirectional(episode.titleProperty());
			choiceSeasonNumber.valueProperty().bindBidirectional(episode.seasonProperty());
			choiceEpisodeNumber.valueProperty().bindBidirectional(episode.episodeProperty());
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		seasonTree.setShowRoot(false);
		seasonTree.setCellFactory(new Callback<TreeView<ITVShowItem>, TreeCell<ITVShowItem>>() {
			@Override
			public TreeCell<ITVShowItem> call(TreeView<ITVShowItem> p) {
				return new TreeCell<ITVShowItem>() {

					@Override
					protected void updateItem(ITVShowItem item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							if (item instanceof Show) {
								// nothing

							} else if (item instanceof Season) {
								Season season = (Season) item;
								setText("Season " + season.getSeason());
							} else if (item instanceof Episode) {
								Episode episode = (Episode) item;
								// setText(episode.getDetails().getEpisode() +
								// " - " + episode.getDetails().getTitle());
								textProperty()
										.bind(Bindings.concat(episode.getDetails().episodeProperty(), " - ", episode.getDetails()
												.titleProperty()));
							}
						}
					}
				};
			}
		});

		choiceSeasonNumber.setConverter(new StringConverter<Number>() {
			@Override
			public String toString(Number object) {
				return object.toString();
			}

			@Override
			public Number fromString(String string) {
				try {
					return new Integer(string);
				} catch (NumberFormatException e) {
					return choiceSeasonNumber.getSelectionModel().getSelectedItem();
				}
			}
		});

		choiceEpisodeNumber.setConverter(new StringConverter<Number>() {
			@Override
			public String toString(Number object) {
				return object.toString();
			}

			@Override
			public Number fromString(String string) {
				try {
					return new Integer(string);
				} catch (NumberFormatException e) {
					return choiceEpisodeNumber.getSelectionModel().getSelectedItem();
				}
			}
		});

	}
}
