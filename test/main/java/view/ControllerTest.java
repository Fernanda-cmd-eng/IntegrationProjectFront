package main.java.view;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.powermock.api.mockito.PowerMockito;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

@RunWith(Parameterized.class)
public class ControllerTest extends ApplicationTest {

	Controller cc;
	private final String category;
	
    @Parameters    
    public static Collection<String> models() {
    	return Arrays.asList(new String[] {"Cronos", "Ares", "Teste"});
    }        
    
    public ControllerTest(String model) {
    	this.category = model;
    }
	@Before
	public void init() {
		cc = spy(Controller.class);

		cc.tpLine = new TitledPane();
		cc.tpModel = new TitledPane();
		cc.cbbline = new ComboBox<String>();
		cc.listaDeModelos = new TreeView<String>();
	}

	@After
	public void finishTest() {
		cc = null;
	}

	@Test
	public void treeViewIniciaDesativada() {
		PowerMockito.doNothing().when(cc).loadItems();
		cc.initialize(null, null);

		assertTrue(cc.tpModel.isDisable());
	}

	@Test
	public void initializeTest02() {
		PowerMockito.doNothing().when(cc).loadItems();
		cc.initialize(null, null);

		verify(cc).loadItems();
	}

	@Test
	public void verificarSeHa3Filhos() {
		cc.cbbline.getSelectionModel().select("Ares");
		cc.loadItems();
		cc.cbbline.getSelectionModel().select("Cronos");
		assertEquals(3, cc.listaDeModelos.getRoot().getChildren().size());
	}
	
	@Test
	public void verificarSeHa2Filhos() {
		cc.cbbline.getSelectionModel().select("Cronos");
		cc.loadItems();
		cc.cbbline.getSelectionModel().select("Ares");
		assertEquals(2, cc.listaDeModelos.getRoot().getChildren().size());
	}
	
	@Test
	public void TesteModels01() {
		cc.cbbline.getSelectionModel().select("Cronos");
		cc.loadItems();
		cc.cbbline.getSelectionModel().select("Ares");
		
		assertEquals("ARES 8023 15", cc.listaDeModelos.getRoot().getChildren().get(0).getChildren().get(0).getValue());
		
		}
	@Test
	public void TesteModels02() {
		cc.cbbline.getSelectionModel().select("Cronos");
		cc.loadItems();
		cc.cbbline.getSelectionModel().select("Ares");
		
		assertEquals("ARES 7031", cc.listaDeModelos.getRoot().getChildren().get(1).getChildren().get(1).getValue());
		
		}
	
	@Test
	public void TesteModels03() {
		cc.cbbline.getSelectionModel().select("Ares");
		cc.loadItems();
		cc.cbbline.getSelectionModel().select("Cronos");

		assertEquals("Cronos 6001-A", cc.listaDeModelos.getRoot().getChildren().get(0).getChildren().get(0).getValue());
	}
	
	@Test
	public void TesteModels04() {
		cc.cbbline.getSelectionModel().select("Ares");
		cc.loadItems();
		cc.cbbline.getSelectionModel().select("Cronos");

		assertEquals("Cronos 6003-NG", cc.listaDeModelos.getRoot().getChildren().get(1).getChildren().get(1).getValue());
	}
	
	@Test
	public void TesteModels05() {
		cc.cbbline.getSelectionModel().select("Ares");
		cc.loadItems();
		cc.cbbline.getSelectionModel().select("Cronos");

		assertEquals("Cronos 7023L", cc.listaDeModelos.getRoot().getChildren().get(2).getChildren().get(2).getValue());
	}
	
	@Test
	public void testCategory() {
		cc.cbbline.getSelectionModel().select("");
		cc.loadItems();
		cc.cbbline.getSelectionModel().select(category);
		

		for (int i = 0; i < cc.listaDeModelos.getRoot().getChildren().size(); i++) {
			TreeItem<String> filho = cc.listaDeModelos.getRoot().getChildren().get(i);
			assertTrue(filho.getValue().startsWith(category));
		}
	}

}
