package view;

import application.DataBase;
import application.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class Page1View {

	public Stage stage;
	public BorderPane root = new BorderPane();
	public GridPane gp = new GridPane();
	public Scene scene = new Scene(root, 800, 750);
	public Label headerlb1 = new Label("Add New Stock");
	public Label modellbl = new Label("Stock Model");
	public Label brandlbl = new Label("Stock Brand");
	public Label colorlbl = new Label("Stock Color");
	public Label pricelbl = new Label("Stock Price");
	public TextField modeltf = new TextField();
	public TextField brandtf = new TextField();
	public TextField colortf = new TextField();
	public TextField pricetf = new TextField();
	public TableView<Stock> table = new TableView<Stock>();
	public TableColumn<Stock, String> idcol = new TableColumn<Stock, String>("ID");
	public TableColumn<Stock, String> modelcol = new TableColumn<Stock, String>("Model");
	public TableColumn<Stock, String> brandcol = new TableColumn<Stock, String>("Brand");
	public TableColumn<Stock, String> colorcol = new TableColumn<Stock, String>("Color");
	public TableColumn<Stock, Integer> pricecol = new TableColumn<Stock, Integer>("Price");
	public Button addBtn = new Button("Add");
	public Button updateBtn = new Button("Update");
	public Button deleteBtn = new Button("Delete");
	public Button buyBtn = new Button("Checkout Here");
	public HBox buttonBox = new HBox(updateBtn, deleteBtn);
	public Stock selected;
	public ObservableList<Stock> menuList = FXCollections.observableArrayList();

	public Page1View(Stage stage) {
		this.stage = stage;
		this.setComponent();
		this.setStyle();
		this.setTableColumns();
		this.setListener();
		DataBase.getInstance();
		this.populateTable();
		this.handleButton();
	}

	@SuppressWarnings("unchecked")
	public void setComponent() {
		gp.add(headerlb1, 0, 0, 2, 1);
		gp.add(modellbl, 0, 1);
		gp.add(modeltf, 1, 1);
		gp.add(brandlbl, 0, 2);
		gp.add(brandtf, 1, 2);
		gp.add(colorlbl, 0, 3);
		gp.add(colortf, 1, 3);
		gp.add(pricelbl, 0, 4);
		gp.add(pricetf, 1, 4);
		gp.add(buttonBox, 0, 5, 3, 1);
		gp.add(addBtn, 0, 6, 3, 1);
		gp.add(buyBtn, 0, 7, 3, 1);

		table.getColumns().addAll(idcol, modelcol, brandcol, colorcol, pricecol);

		root.setTop(table);
		root.setCenter(gp);
		stage.setScene(scene);
	}

	@SuppressWarnings("deprecation")
	public void setStyle() {
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(15);
		gp.setVgap(15);

		GridPane.setHalignment(headerlb1, HPos.CENTER);
		GridPane.setHalignment(buttonBox, HPos.CENTER);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		addBtn.setMinWidth(230);
		updateBtn.setMinWidth(110);
		deleteBtn.setMinWidth(110);
		buttonBox.setSpacing(10);
		stage.setResizable(false);
		buyBtn.setMinWidth(230);
	}

	public void setTableColumns() {
		idcol.setCellValueFactory(new PropertyValueFactory<Stock, String>("id"));
		modelcol.setCellValueFactory(new PropertyValueFactory<Stock, String>("model"));
		brandcol.setCellValueFactory(new PropertyValueFactory<Stock, String>("brand"));
		colorcol.setCellValueFactory(new PropertyValueFactory<Stock, String>("color"));
		pricecol.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("price"));
	}

	public void setListener() {
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {

			if (newValue != null) {
				this.selected = newValue;
				modeltf.setText(newValue.getModel());
				brandtf.setText(newValue.getBrand());
				colortf.setText(newValue.getColor());
				pricetf.setText(String.valueOf(newValue.getPrice()));

			}
		});
	}

	public void populateTable() {
		menuList = DataBase.getall();
		table.setItems(menuList);
		modeltf.clear();
		brandtf.clear();
		colortf.clear();
		pricetf.clear();
	}

	public void handleButton() {
		addBtn.setOnAction(event -> {
			String model = modeltf.getText();
			String brand = brandtf.getText();
			String color = colortf.getText();
			String price = pricetf.getText();
			String id = "000";

			if (menuList.size() == 0) {
				try {
					id = brand.charAt(0) + "001";
				} catch (StringIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			} else {
				int total = menuList.size();
				int lastind = total - 1;
				Stock last = menuList.get(lastind);
				String lastid = last.getId();
				int lastnum = Integer.parseInt(lastid.substring(1));

				int newNum = lastnum + 1;
				String idNum = String.format("%03d", newNum);
				try {
					id = brand.charAt(0) + idNum;
				} catch (StringIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}

			for (Stock stock : menuList) {
				if (stock.getModel().equals(model) && stock.getBrand().equals(brand)
						&& stock.getColor().equals(color)) {
					alert(AlertType.ERROR, "Error", "Validation Error", "Code must be different from existed");
					return;
				}
			}

			if (model.isEmpty() || brand.isEmpty() || color.isEmpty() || price.isEmpty()) {
				alert(AlertType.ERROR, "Error", "Validation Error", "All box must be filled");
				return;
			}

			try {
				Integer.valueOf(price);
			} catch (Exception e) {
				alert(AlertType.ERROR, "Error", "Validation Error", "Price must be numeric");
				return;
			}

			DataBase dataBase = new DataBase();
			DataBase.add(new Stock(id, model, brand, color, Integer.valueOf(price)));
			this.populateTable();
			alert(AlertType.INFORMATION, "Cool", "Info", "The menu succesfully added");
		});

		updateBtn.setOnAction(event -> {
			String model = modeltf.getText();
			String brand = brandtf.getText();
			String color = colortf.getText();
			String price = pricetf.getText();
			String id = "000";
			for (Stock stock : menuList) {
				if (stock.getModel().equals(model) && stock.getBrand().equals(brand)
						&& stock.getColor().equals(color)) {
					id = stock.getId();
				}
			}

			if (model.isEmpty() || brand.isEmpty() || color.isEmpty() || price.isEmpty()) {
				alert(AlertType.ERROR, "Error", "Validation Error", "All box must be filled");
				return;
			}

			DataBase.update(new Stock(id, model, brand, color, Integer.valueOf(price)));
			this.populateTable();
			alert(AlertType.INFORMATION, "Cool", "Info", "The menu succesfully updated");
		});

		deleteBtn.setOnAction(event -> {
			DataBase.delete(selected);
			this.populateTable();
			alert(AlertType.INFORMATION, "Cool", "Info", "The menu succesfully delete");
		});

		buyBtn.setOnAction(event -> {
			Page2 page2 = new Page2(stage);
			stage.setScene(page2.scene);
		});
	}

	public void alert(AlertType alerttype, String title, String header, String content) {
		Alert alert = new Alert(alerttype);
		alert.initOwner(stage);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
