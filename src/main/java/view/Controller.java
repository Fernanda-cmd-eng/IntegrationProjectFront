package main.java.view;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import org.hibernate.HibernateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import main.java.MainApp;
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

	private List<MetersView> meters;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tpModel.setDisable(true); // Inicia com o painel de baixo desabilitado

		loadItems();
	}

	private void loadItems() {
		obsCategorias = FXCollections.observableArrayList();
		try {
			meters = MeterRequester.getMeters("http://localhost:8080/api/meters");
			for (MetersView m : meters) {
				if (!obsCategorias.contains(m.getLine()))
					obsCategorias.add(m.getLine());
			}

		} catch (HibernateException exception) {
			System.out.println("Problem creating session factory");
			exception.printStackTrace();
			throw exception;
		}

		cbbline.setItems(obsCategorias);

		cbbline.getSelectionModel().selectedItemProperty().addListener((sender, oldValue, newValue) -> {
			if (newValue != null) {
				tpModel.setDisable(false);

				TreeItem<String> root = new TreeItem<String>();
				listaDeModelos.setShowRoot(false);
				listaDeModelos.setRoot(root);

				Map<String, List<MetersView>> categories = meters.stream()
						.filter(item -> item.getLine().equals(newValue))
						.collect(Collectors.groupingBy(item -> item.getCategory()));
				categories.forEach((key, value) -> {
					TreeItem<String> category = new TreeItem<>(key);
					category.setExpanded(true);
					for (MetersView meter : value) {
						TreeItem<String> item = new TreeItem<>(meter.getModel());
						category.getChildren().add(item);
					}
					root.getChildren().add(category);
				});

			} else
				tpModel.setDisable(true);

		});
	}
}
//aaaaaaa
//gggg