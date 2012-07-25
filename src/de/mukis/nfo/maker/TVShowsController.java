package de.mukis.nfo.maker;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

import javax.xml.bind.JAXB;

import de.mukis.nfo.maker.model.Episodedetails;
import de.mukis.nfo.maker.model.TVShow;
import de.mukis.nfo.maker.scraper.TVShowScraper;
import de.mukis.nfo.maker.ui.ITVShowItem;
import de.mukis.nfo.maker.ui.ITVShowItem.Episode;
import de.mukis.nfo.maker.ui.ITVShowItem.Season;
import de.mukis.nfo.maker.ui.ITVShowItem.Show;

public class TVShowsController implements Initializable {

	private TVShow tvshow = new TVShow();
	private Episodedetails episode;

	@FXML
	private Pane tvContent;

	@FXML
	private TextField txtDirectory;

	@FXML
	private TextField tvShowTitle;

	@FXML
	private TreeView<ITVShowItem> seasonTree;

	@FXML
	private TextField txtEpisodeTitle;

	@FXML
	protected void onChooseDirectory(ActionEvent event) {
		DirectoryChooser fc = new DirectoryChooser();
		File directory = fc.showDialog(tvContent.getScene().getWindow());
		if (directory == null) {
			txtDirectory.setText("");
		} else {
			txtDirectory.setText(directory.getAbsolutePath());
		}
		Path p = Paths.get(directory.getAbsolutePath());
		try {
			TVShowScraper scraper = new TVShowScraper(p);
			Show show = scraper.findShow();

			TreeItem<ITVShowItem> root = new TreeItem<>((ITVShowItem) show);
			for (Season season : show.getSeasons().values()) {
				TreeItem<ITVShowItem> seasonItem = new TreeItem<>((ITVShowItem)season);
				for (Episode episode : season.getEpisodes().values()) {
					TreeItem<ITVShowItem> episodeItem = new TreeItem<>((ITVShowItem) episode);
					seasonItem.getChildren().add(episodeItem);
				}
				root.getChildren().add(seasonItem);
			}

			seasonTree.setRoot(root);
			tvShowTitle.setText(show.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	protected void onSave(ActionEvent event) {
		JAXB.marshal(tvshow, System.out);
		JAXB.marshal(episode, System.out);
	}

	@FXML
	protected void onFileSelected(MouseEvent event) {
		System.out.println("Selected: " + seasonTree.getSelectionModel().getSelectedItem());
		TreeItem<ITVShowItem> item = seasonTree.getSelectionModel().getSelectedItem();
		if(item != null && item.getValue() instanceof Episode) {
			//unbind
			if(episode != null) {
				episode.titleProperty().unbindBidirectional(txtEpisodeTitle.textProperty());
			}

			//bind
			episode = ((Episode)item.getValue()).getDetails();
			txtEpisodeTitle.textProperty().bindBidirectional(episode.titleProperty());
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tvShowTitle.textProperty().bindBidirectional(tvshow.titleProperty());
		
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
								//nothing
								
							} else if (item instanceof Season) {
								Season season = (Season) item;
								setText("Season " + season.getSeason());
							} else if (item instanceof Episode) {
								Episode episode = (Episode) item;
								//setText(episode.getDetails().getEpisode() + " - " + episode.getDetails().getTitle());
								textProperty().bind(Bindings.concat(episode.getDetails().episodeProperty(), " - ", episode.getDetails().titleProperty()));
							}
						}
					}
				};
			}
		});

	}
}
