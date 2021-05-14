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
import model.Meters;
import model.MetersService;

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
		obsCategorias = FXCollections.observableArrayList();
		try {
			MetersService service = new MetersService();
			for (String line : service.getLines())
				obsCategorias.add(line);
		} catch (HibernateException exception) {
			System.out.println("Problem creating session factory");
			exception.printStackTrace();
			throw exception;
		}

		cbbline.setItems(obsCategorias);

		cbbline.getSelectionModel().selectedItemProperty().addListener((sender, oldValue, newValue) -> {
			if (newValue != null) {
				tpModel.setDisable(false);
				try {
					MetersService service = new MetersService();
					List<Meters> meters = service.getByLine(newValue);
					TreeItem<String> root = new TreeItem<String>();
					listaDeModelos.setShowRoot(false);
					listaDeModelos.setRoot(root);

					// Pega todos os Meters de uma linha e agrupa pela categoria
					Map<String, List<Meters>> categories = meters.stream()
							.collect(Collectors.groupingBy(item -> item.getCategory()));
					categories.forEach((key, value) -> {
						TreeItem<String> category = new TreeItem<>(key);
						category.setExpanded(true);
						for (Meters meter : value) {
							TreeItem<String> item = new TreeItem<>(meter.getModel());
							category.getChildren().add(item);
						}
						root.getChildren().add(category);
					});

				} catch (HibernateException exception) {
					exception.printStackTrace();
					throw exception;
				}

			} else
				tpModel.setDisable(true);

		});
	}
}
