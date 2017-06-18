package application;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MediaBar extends HBox {
	Slider time = new Slider(); // �إ߮ɶ��b
	Slider vol = new Slider();// �إ߭��q�b
	Slider bal = new Slider();// �إ��n�D���Ŷb
	Slider sliderDuration; // ����ɶ�
	Button replayButton = new Button("����");// �إ߭������s
	Button playButton = new Button("||");// �إ߼�����s
	// Button fullButton = new Button("���ù�");// �إߥ��ù����s
	Label volume = new Label("Volume:");// �إ߭��q����
	Label balance = new Label("Balance:");// �إߥ��ż���
	Stage primaryStage;
	MediaPlayer player;// �إ߼���

	public MediaBar(MediaPlayer play) {
		player = play;
		setAlignment(Pos.CENTER); // �ϥ\��bHbox�����
		setPadding(new Insets(5, 10, 5, 10));
		vol.setPrefWidth(100);// �]�m���ﭵ�q�b�e��
		vol.setMinWidth(50);// �̤p���q�b�e��
		vol.setMaxWidth(Region.USE_PREF_SIZE);// �̤j���q�b�e�סA�Y���ŦX�����A��^����e��
		bal.setPrefWidth(60);// �]�m���省�Ŷb�e��
		bal.setMinWidth(30);// �̤p���Ŷb�e��
		bal.setMaxWidth(Region.USE_PREF_SIZE);// �̤j���Ŷb�e�סA�Y���ŦX�����A��^����e��
		vol.setValue(100);// ��l���q�j�p
		bal.setMin(-1.0);// �����n�D�̤p��
		bal.setMax(1.0);// �����n�D�̤j��
		bal.setValue(0.0);// �����n�D��l��
		playButton.setPrefWidth(50);// ������s�e��
		// fullButton.setPrefWidth(55);// ���ù����s�e��
		HBox.setHgrow(time, Priority.ALWAYS);// �u�����t�ɶ��b���Ŷ�
		getChildren().add(replayButton);
		getChildren().add(playButton);
		// getChildren().add(fullButton);
		getChildren().add(time);
		getChildren().add(volume);
		getChildren().add(vol);
		getChildren().add(balance);
		getChildren().add(bal);
		// �[�J�U�ӫ��s�ηưʶb

		playButton.setOnAction(new EventHandler<ActionEvent>() { // �B�zplayButton���s�ƥ�
			public void handle(ActionEvent e) {// ���o���T���`�ɶ�
				Status status = player.getStatus();// ���o��e���񪬺A
				if (status == Status.PLAYING) { // �Y���b����
					if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) {

						player.seek(player.getStartTime());

						player.play();

					} else {

						player.pause();

						playButton.setText(">");
					}
				}
				if (status == Status.PAUSED || status == Status.HALTED || status == Status.STOPPED) { // �Y���A���Ȱ��ο��~�ΰ���

					player.play();

					playButton.setText("||");
				}
			}
		});

		replayButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				final Duration currentduration = Duration.ZERO;// �]�w�ثe���i�׮ɶ����s

				if (player.getStatus() == MediaPlayer.Status.STOPPED) {// ���o���T�ثe�����A

					player.pause();
				}

				player.seek(currentduration); // ���ܭ��T���̫e��

				if (player.getStatus() != MediaPlayer.Status.PLAYING) {// ���o���T�ثe�����A

					if (sliderDuration.isValueChanging())

						return;

					final Duration total = player.getTotalDuration(); // Ū���`����ɶ�

					if (total == null) {

						sliderDuration.setValue(0); // �վ�ưʶb����m

					} else {

						sliderDuration.setValue(currentduration.toMillis() / total.toMillis()); // �վ�ưʶb����m

					}
				}
			}
		});

		player.currentTimeProperty().addListener(new InvalidationListener() {// ��e����i��

			public void invalidated(Observable ov) {

				updateValues();
			}
		});
		time.valueProperty().addListener(new InvalidationListener() { // �B�z�ưʮɶ��b�i�վ㼽��i��

			public void invalidated(Observable ov) {

				if (time.isPressed()) {

					player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100));
				}
			}
		});

		vol.valueProperty().addListener(new InvalidationListener() {

			public void invalidated(Observable ov) { // �B�z�ưʭ��q�b�i�վ㭵�q

				if (vol.isPressed()) {

					player.setVolume(vol.getValue() / 100);
				}
			}
		});

		bal.valueProperty().addListener(new InvalidationListener() {

			public void invalidated(Observable ov) {// �]�w���T���n�D����

				if (bal.isValueChanging()) {
					player.setBalance(bal.getValue() / 10);

				}
			}
		});
	}

	protected void updateValues() {// ��۶i�׽վ�ɶ��b����m

		Platform.runLater(new Runnable() {

			public void run() {

				time.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
			}
		});
	}
}