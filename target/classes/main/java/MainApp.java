package main.java;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.view.Controller;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Projeto Integração");

		initInterface();

	}

	public void initInterface() {
		try {
			// Carrega o rootlayout do arquivo fxml.
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(MainApp.class.getResource("/main/java/view/Interface.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// Mostra a scene (cena) contendo o root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			// Dá ao controlador acesso à the main app.
			Controller controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}

}