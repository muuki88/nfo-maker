package de.mukis.nfo.maker;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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

import javax.xml.bind.JAXB;

import de.mukis.nfo.maker.model.MusicVideo;
import de.mukis.nfo.maker.scraper.MusicVideoScraper;
import de.mukis.nfo.maker.scraper.Scraper;
import de.mukis.nfo.maker.ui.IMusicVideoItem;
import de.mukis.nfo.maker.ui.IMusicVideoItem.Video;

public class MusicvideosController extends ScrapController {

	private MusicVideo video;
	private IMusicVideoItem.Artist artist;

	@FXML
	private TextField txtArtist;

	@FXML
	private TextField txtGenre;

	@FXML
	private TreeView<IMusicVideoItem> artistTree;

	/* ========= Details View ====== */

	@FXML
	private TextField txtVideoTitle;

	@FXML
	private TextField txtVideoAlbum;

	/* =============================== */

	protected void onSave(ActionEvent event) {
		for (TreeItem<IMusicVideoItem> artistItem : artistTree.getRoot().getChildren()) {
			for (TreeItem<IMusicVideoItem> videoItem : artistItem.getChildren()) {
				IMusicVideoItem.Video video = (IMusicVideoItem.Video) videoItem.getValue();
				Path videoFile = video.getFile();
				Path nfoFile = videoFile.getParent().resolve(videoFile.getFileName().toString().replaceAll("\\.\\w{3,4}$", ".nfo"));
				try (OutputStream out = Files.newOutputStream(nfoFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE,
						StandardOpenOption.TRUNCATE_EXISTING)) {
					JAXB.marshal(video.getVideo(), out);
				} catch (IOException e) {
					// TODO exception handling
					e.printStackTrace();
				}
			}
		}
		statusLine.setText("Successfully written");
	}

	@FXML
	protected void onFileSelected(MouseEvent event) {
		TreeItem<IMusicVideoItem> item = artistTree.getSelectionModel().getSelectedItem();
		if (item == null)
			return;

		if (item.getValue() instanceof IMusicVideoItem.Video) {
			// unbind
			unbindVideo();
			unbindArtist();

			// bind
			bindVideo((IMusicVideoItem.Video) item.getValue());
			bindArtist(((IMusicVideoItem.Video) item.getValue()).getArtist());
		} else if (item.getValue() instanceof IMusicVideoItem.Artist) {
			unbindArtist();
			unbindVideo();
			bindArtist((IMusicVideoItem.Artist) item.getValue());
		}
	}

	@Override
	protected void clear() {
		unbindArtist();
		unbindVideo();
		artistTree.setRoot(null);
		txtVideoAlbum.textProperty().unbind();
		txtVideoTitle.textProperty().unbind();
		txtArtist.textProperty().unbind();
		txtVideoAlbum.setText("");
		txtVideoTitle.setText("");
		txtArtist.setText("");
		super.clear();
	}

	@Override
	protected void update(Path dir) {
		unbindArtist();
		unbindVideo();
		clear();
		txtDirectory.setText(dir.toString());
		try {
			List<MusicVideo> videos = Scraper.findList(dir, new MusicVideoScraper(), MusicVideo.class);

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
								// TODO no checks
								Video video = artist.getVideos().values().iterator().next();
								textProperty().bind(video.getVideo().artistProperty());
							} else if (item instanceof IMusicVideoItem.Video) {
								IMusicVideoItem.Video video = (IMusicVideoItem.Video) item;
								textProperty().bind(video.getVideo().titleProperty());
							}
					}
				};
			}
		});
	}

	private void bindVideo(IMusicVideoItem.Video videoItem) {
		video = videoItem.getVideo();
		txtVideoTitle.textProperty().bindBidirectional(video.titleProperty());
		txtVideoAlbum.textProperty().bindBidirectional(video.albumProperty());
	}

	private void bindArtist(IMusicVideoItem.Artist artistItem) {
		artist = artistItem;
		for (IMusicVideoItem.Video video : this.artist.getVideos().values()) {
			txtArtist.textProperty().bindBidirectional(video.getVideo().artistProperty());
		}
	}

	private void unbindVideo() {
		if (video != null) {
			video.titleProperty().unbindBidirectional(txtVideoTitle.textProperty());
			video.albumProperty().unbindBidirectional(txtVideoAlbum.textProperty());
		}
	}

	private void unbindArtist() {
		if (this.artist != null) {
			for (IMusicVideoItem.Video video : artist.getVideos().values()) {
				txtArtist.textProperty().unbindBidirectional(video.getVideo().artistProperty());
			}
		}
	}
}
