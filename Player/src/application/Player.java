package application;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane {
	Media media;
	MediaPlayer player;
	MediaView view;
	Pane mpane;
	MediaBar bar;

	public Player(String file) {
		mpane = new Pane(); // �إߵ���
		media = new Media(file);
		player = new MediaPlayer(media);
		view = new MediaView(player);
		mpane.getChildren().add(view);
		setCenter(mpane);
		view.fitWidthProperty().bind(Bindings.selectDouble(view.sceneProperty(), "width"));
		view.fitHeightProperty().bind(Bindings.selectDouble(view.sceneProperty(), "height"));
		// �۰ʳ]�w�ѪR��
		view.setPreserveRatio(true); // �����Y�񪺮ɭԡA�v���������
		bar = new MediaBar(player);
		setBottom(bar); // �N�\��]�m�b����
		setStyle("-fx-background-color:#ffe4c4"); // �v���I���C��
		player.play();// ����v��
	}
}