package view;

import application.Bill;
import application.DataBase;
import application.DataBase_2;
import application.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Page2 {
	public Stage stage;
	public BorderPane root = new BorderPane();
	public GridPane gp = new GridPane();
	public Scene scene = new Scene(root, 800, 750);
	public Label headerlb1 = new Label("CheckOut");
	public Label modellbl = new Label("Stock Model");
	public Label brandlbl = new Label("Stock Brand");
	public Label colorlbl = new Label("Stock Color");
	public Label totallbl = new Label("Quantities");
	public Label urmoneylbl = new Label("Your Money");
	public TextField modeltf = new TextField();
	public TextField brandtf = new TextField();
	public TextField colortf = new TextField();
	public TextField totaltf = new TextField();
	public TextField urmoneytf = new TextField();
	public TableView<Stock> table = new TableView<Stock>();
	public TableColumn<Stock, String> idcol = new TableColumn<Stock, String>("ID");
	public TableColumn<Stock, String> modelcol = new TableColumn<Stock, String>("Model");
	public TableColumn<Stock, String> brandcol = new TableColumn<Stock, String>("Brand");
	public TableColumn<Stock, String> colorcol = new TableColumn<Stock, String>("Color");
	public TableColumn<Stock, Integer> pricecol = new TableColumn<Stock, Integer>("Price");
	public Button backBtn = new Button("Back");
	public Button historyBtn = new Button("History");
	public Button buyBtn = new Button("Buy");
	public HBox buttonBox = new HBox(backBtn, historyBtn);
	public Stock selected;
	public ObservableList<Stock> menuList = FXCollections.observableArrayList();
	int price;
	int total;

	
	public Page2(Stage stage) {
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
		gp.add(totallbl, 0, 4);
		gp.add(totaltf, 1, 4);
		gp.add(urmoneylbl, 0, 5);
		gp.add(urmoneytf, 1, 5);
		gp.add(buttonBox, 0, 6, 3, 1);
		gp.add(buyBtn, 0, 7, 3, 1);
		
		table.getColumns().addAll(idcol, modelcol ,brandcol, colorcol, pricecol);
		
		root.setTop(table);
		root.setCenter(gp);
		stage.setScene(scene);
	}
	
	@SuppressWarnings({ "deprecation" })
	public void setStyle() {
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(15);
		gp.setVgap(15);
		
		GridPane.setHalignment(headerlb1, HPos.CENTER);
		GridPane.setHalignment(buttonBox, HPos.CENTER);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		backBtn.setMinWidth(110);
		historyBtn.setMinWidth(110);
		buyBtn.setMinWidth(230);
		buttonBox.setSpacing(10);
		stage.setResizable(false);
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
				totaltf.setText("1");
				price = newValue.getPrice();
			}
		});
	}
	
	public void populateTable() {
		menuList = DataBase.getall();
		table.setItems(menuList);
		modeltf.clear();
		brandtf.clear();
		colortf.clear();
		totaltf.clear();
	}
	
	@SuppressWarnings({ "unused", "resource" })
	public void handleButton() {
		buyBtn.setOnAction(event -> {
			String model = modeltf.getText();
			String brand = brandtf.getText();
			String color = colortf.getText();
			String quantites = totaltf.getText();
			String money = urmoneytf.getText();
			total = price * Integer.parseInt(quantites);
			
			if (model.isEmpty() || brand.isEmpty() || color.isEmpty() || quantites.isEmpty() || money.isEmpty()) {
				alert(AlertType.ERROR, "Error", "Validation Error", "All box must be filled");
				return;
			}
			
			if (Integer.parseInt(money) < total) {
				alert(AlertType.ERROR, "Error", "Insufficient Balance", "Please insert more money :)");
				return;
			}

			try {
				Integer.valueOf(total);
			} catch (Exception e) {
				alert(AlertType.ERROR, "Error", "Validation Error", "Price must be numeric");
				return;
			}

			DataBase_2 dataBase = new DataBase_2();
			DataBase_2.add(new Bill(model, brand, color, Integer.valueOf(quantites), price, total, Integer.valueOf(money)));
			this.populateTable();
			alert(AlertType.INFORMATION, "Cool", "Info", "The menu succesfully added");
		});

		backBtn.setOnAction(event -> {
			Page1View page1 = new Page1View(stage);
			stage.setScene(page1.scene);
		});

		historyBtn.setOnAction(event -> {
			Page3 page3 = new Page3(stage);
			stage.setScene(page3.scene);
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

