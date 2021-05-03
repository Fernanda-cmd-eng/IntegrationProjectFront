package main.java.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import main.java.MainApp;
import model.Category;
import model.Model;
import javafx.fxml.Initializable;

public class Controller implements Initializable {

	@FXML
	public TitledPane tpLine;

	@FXML
	public ComboBox<String> cbbline;

	public ObservableList<String> obsCategorias; // Uma lista observavel de itens

	@FXML
	public TitledPane tpModel;

	@FXML
	public TreeView<String> listaDeModelos; // objetos de vários tipos

	public MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tpModel.setDisable(true); // Inicia com o painel de baixo desabilitado
		loadItems();
	}

	private void loadItems() {
		//setando itens do combobox
		obsCategorias = FXCollections.observableArrayList();
		obsCategorias.add("Ares");
		obsCategorias.add("Cronos");
		cbbline.setItems(obsCategorias);

		//Manipule a mudança do item selecionado da combobox
		cbbline.getSelectionModel().selectedItemProperty().addListener((sender, oldValue, newValue) -> {
			if (newValue != null) {
				tpModel.setDisable(false);

				TreeItem<String> root = new TreeItem<>("root");
				listaDeModelos.setRoot(root);
				listaDeModelos.setShowRoot(false);
				switch (newValue) {
				case "Ares": {
					TreeItem<String> aresTB = new TreeItem<>(Category.AresTb.getName());
					aresTB.setExpanded(true);

					aresTB.getChildren().add(new TreeItem<>(Model.ARES7021.getName()));
					aresTB.getChildren().add(new TreeItem<>(Model.ARES7031.getName()));
					aresTB.getChildren().add(new TreeItem<>(Model.ARES7023.getName()));
					root.getChildren().add(aresTB);

					TreeItem<String> aresThs = new TreeItem<>(Category.AresThs.getName());
					aresThs.setExpanded(true);

					aresThs.getChildren().add(new TreeItem<>(Model.ARES802315.getName()));
					aresThs.getChildren().add(new TreeItem<>(Model.ARES8023200.getName()));
					aresThs.getChildren().add(new TreeItem<>(Model.ARES802325.getName()));
					root.getChildren().add(aresThs);

				}
					break;

				case "Cronos":

					TreeItem<String> CronosL = new TreeItem<>(Category.CronosL.getName());
					CronosL.setExpanded(true);

					CronosL.getChildren().add(new TreeItem<>(Model.Cronos6021L.getName()));
					CronosL.getChildren().add(new TreeItem<>(Model.Cronos6021L.getName()));
					CronosL.getChildren().add(new TreeItem<>(Model.Cronos7023L.getName()));
					root.getChildren().add(CronosL);

					TreeItem<String> CronosNg = new TreeItem<>(Category.CronosNg.getName());
					CronosNg.setExpanded(true);

					CronosNg.getChildren().add(new TreeItem<>(Model.Cronos6001NG.getName()));
					CronosNg.getChildren().add(new TreeItem<>(Model.Cronos6003NG.getName()));
					CronosNg.getChildren().add(new TreeItem<>(Model.Cronos6021NG.getName()));
					CronosNg.getChildren().add(new TreeItem<>(Model.Cronos6031NG.getName()));
					CronosNg.getChildren().add(new TreeItem<>(Model.Cronos7021NG.getName()));
					CronosNg.getChildren().add(new TreeItem<>(Model.Cronos7023NG.getName()));
					root.getChildren().add(CronosNg);

					TreeItem<String> CronosOld = new TreeItem<>(Category.CronosOld.getName());
					CronosOld.setExpanded(true);

					CronosOld.getChildren().add(new TreeItem<>(Model.Cronos6001A.getName()));
					CronosOld.getChildren().add(new TreeItem<>(Model.Cronos6003.getName()));
					CronosOld.getChildren().add(new TreeItem<>(Model.Cronos7023.getName()));

					root.getChildren().add(CronosOld);

					break;
				}

			}
		});
	}
}

