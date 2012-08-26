package de.mukis.nfo.maker;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import de.mukis.nfo.maker.model.MusicVideo;
import de.mukis.nfo.maker.scraper.MusicVideoScraper;
import de.mukis.nfo.maker.scraper.Scraper;
import de.mukis.nfo.maker.ui.IMusicVideoItem;

public class MusicvideosController extends ScrapController {

	private MusicVideo video;

	@FXML
	private TextField txtArtist;

	@FXML
	private TextField txtGenre;

	@FXML
	private TreeView<IMusicVideoItem> artistTree;

	/* ========= Details View ====== */

	@FXML
	private TextField txtVideoTitle;

	/* =============================== */

	@FXML
	protected void onSave(ActionEvent event) {
		System.out.println("MusicvideosController.onSave()");
	}

	@FXML
	protected void onFileSelected(MouseEvent event) {
		TreeItem<IMusicVideoItem> item = artistTree.getSelectionModel().getSelectedItem();
		if (item == null)
			return;

		if (item.getValue() instanceof IMusicVideoItem.Video) {
			// unbind
			if (video != null) {
				video.titleProperty().unbindBidirectional(txtVideoTitle.textProperty());
			}

			// bind
			video = ((IMusicVideoItem.Video) item.getValue()).getVideo();
			txtVideoTitle.textProperty().bindBidirectional(video.titleProperty());
		} else if (item.getValue() instanceof IMusicVideoItem.Artist) {

		}
	}

	@Override
	protected void clear() {
		System.out.println("MusicvideosController.clear()");
	}

	@Override
	protected void update(Path dir) {
		try {
			List<MusicVideo> videos = Scraper.findList(dir, new MusicVideoScraper(), MusicVideo.class);
			System.out.println(videos);

			IMusicVideoItem.MusicVideoItem root = new IMusicVideoItem.MusicVideoItem();
			TreeItem<IMusicVideoItem> rootItem = new TreeItem<>((IMusicVideoItem) root);
			for (MusicVideo video : videos) {
				IMusicVideoItem.Artist artist = root.get(video.getArtist());
				new IMusicVideoItem.Video(video.getFile(), video, artist);
			}
			for (IMusicVideoItem.Artist artist : root.getArtists().values()) {
				TreeItem<IMusicVideoItem> artistItem = new TreeItem<>((IMusicVideoItem) artist);
				for (IMusicVideoItem.Video video : artist.getVideos().values()) {
					artistItem.getChildren().add(new TreeItem<>((IMusicVideoItem) video));
				}
				rootItem.getChildren().add(artistItem);
			}
			artistTree.setRoot(rootItem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		artistTree.setShowRoot(false);
		artistTree.setCellFactory(new Callback<TreeView<IMusicVideoItem>, TreeCell<IMusicVideoItem>>() {
			@Override
			public TreeCell<IMusicVideoItem> call(TreeView<IMusicVideoItem> p) {
				return new TreeCell<IMusicVideoItem>() {

					@Override
					protected void updateItem(IMusicVideoItem item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null)

							if (item instanceof IMusicVideoItem.Artist) {
								IMusicVideoItem.Artist artist = (IMusicVideoItem.Artist) item;
								setText(artist.getName());
							} else if (item instanceof IMusicVideoItem.Video) {
								IMusicVideoItem.Video video = (IMusicVideoItem.Video) item;
								textProperty().bind(video.getVideo().titleProperty());
							}
					}
				};
			}
		});
	}
}
