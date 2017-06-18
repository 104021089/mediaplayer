package application;

import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//�w�� JavaFx http://download.eclipse.org/efxclipse/updates-released/0.9.0/site
public class Main extends Application {
	Player player;
	FileChooser fileChooser;

	public void start(Stage primaryStage) throws MalformedURLException {
		primaryStage.setTitle("²��ֳt���v������");
		MenuBar menu = new MenuBar(); // �إ߿��C
		Menu file = new Menu("���"); // �إ߿��
		MenuItem open = new MenuItem("�}��"); // �إߤl��檺���D
		menu.getMenus().add(file);
		file.getItems().add(open);
		// �[�Jfile��open
		fileChooser = new FileChooser();
		File filestart = fileChooser.showOpenDialog(primaryStage);
		player = new Player(filestart.toURI().toURL().toExternalForm()); // ����v�����|
		player.setTop(menu);// �N���]�m�b���W��
		Scene scene = new Scene(player, 1080, 720, Color.BLACK);
		primaryStage.setScene(scene);
		// primaryStage.setFullScreen(true); //�]�w���ù�
		primaryStage.show();

		open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				player.player.pause();// ����Ȱ�
				File fileopen = fileChooser.showOpenDialog(primaryStage); // ��ܤ@�ӷs���}���ɮ׵���
				if (file != null) {
					try {
						player = new Player(fileopen.toURI().toURL().toExternalForm());
						player.setTop(menu);// �N�s�������]�m���b���W��
						Scene scene = new Scene(player, 1080, 720, Color.BLACK);
						primaryStage.setScene(scene);
					} catch (MalformedURLException e1) { // �ߥX�榡�����T���v��
						e1.printStackTrace(); // �bconsole��ܲ��`�H���b�{�Ǥ��X������m�έ�]
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		Application.launch(args); // �ҰʿW�ߪ����ε{��
	}
}