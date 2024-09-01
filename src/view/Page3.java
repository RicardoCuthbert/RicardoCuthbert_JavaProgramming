package view;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import application.Bill;
import application.DataBase_2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Page3 {
	public Stage stage;
	public BorderPane root = new BorderPane();
	public GridPane gp = new GridPane();
	public Scene scene = new Scene(root, 800, 750);
	public TableView<Bill> table = new TableView<Bill>();
	public TableColumn<Bill, String> idcol = new TableColumn<Bill, String>("ID");
	public TableColumn<Bill, String> modelcol = new TableColumn<Bill, String>("Model");
	public TableColumn<Bill, String> brandcol = new TableColumn<Bill, String>("Brand");
	public TableColumn<Bill, String> colorcol = new TableColumn<Bill, String>("Color");
	public TableColumn<Bill, Integer> quantitescol = new TableColumn<Bill, Integer>("Quantites");
	public TableColumn<Bill, Integer> pricecol = new TableColumn<Bill, Integer>("Price");
	public TableColumn<Bill, Integer> totalcol = new TableColumn<Bill, Integer>("Total");
	public TableColumn<Bill, Integer> moneycol = new TableColumn<Bill, Integer>("Money");
	public File struk = new File("struk.txt");
//	public ObservableList<Bill> data = FXCollections.observableArrayList();
	public Button backBtn = new Button("Back");
	public Button printBtn = new Button("Print");
	public ObservableList<Bill> menuList = FXCollections.observableArrayList();
	public HBox buttonBox = new HBox(backBtn, printBtn);
	public ArrayList<String> liststruk = new ArrayList<>();
	DataBase_2 database_2 = DataBase_2.getInstance();

	public Page3(Stage stage) {
		this.stage = stage;
		this.setComponent();
		this.setStyle();
		this.setTableColumns();
		this.handleButton();
		this.populateTable();
	}

	@SuppressWarnings("unchecked")
	public void setComponent() {
		gp.add(buttonBox, 0, 0);

		table.getColumns().addAll(idcol, modelcol, brandcol, colorcol, quantitescol, pricecol, totalcol, moneycol);
		root.setTop(table);
		root.setCenter(gp);
		stage.setScene(scene);
	}

	@SuppressWarnings("deprecation")
	public void setStyle() {
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(15);
		gp.setVgap(15);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		backBtn.setMinWidth(110);
		printBtn.setMinWidth(110);
		stage.setResizable(false);
		buttonBox.setSpacing(10);
	}

	public void setTableColumns() {
		idcol.setCellValueFactory(new PropertyValueFactory<Bill, String>("id"));
		modelcol.setCellValueFactory(new PropertyValueFactory<Bill, String>("model"));
		brandcol.setCellValueFactory(new PropertyValueFactory<Bill, String>("brand"));
		colorcol.setCellValueFactory(new PropertyValueFactory<Bill, String>("color"));
		quantitescol.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("quantites"));
		pricecol.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("price"));
		totalcol.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("total"));
		moneycol.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("money"));
	}

	@SuppressWarnings("static-access")
	public void populateTable() {
		menuList = database_2.getall();
		table.setItems(menuList);
	}

	@SuppressWarnings({ "resource" })
	public void handleButton() {
		backBtn.setOnAction(event -> {
			Page2 page2 = new Page2(stage);
			stage.setScene(page2.scene);
		});

		printBtn.setOnAction(event -> {
			try {
				if (struk.exists()) {
					struk.delete();
				}else {
					try {
						struk.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				FileReader reader = new FileReader(struk);
				int buffer = 0;
				reader = new FileReader(struk);
				buffer = reader.read();
				while (buffer != -1) {
					String name = "";
					while ((char) buffer != '\n') {
						name = name + (char) buffer;
						try {
							buffer = reader.read();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					liststruk.add(name);
					try {
						buffer = reader.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				FileWriter write = new FileWriter(struk);
				int i = 1;
				for (Bill bill : menuList) {
					write.write("~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
					write.write("Struk" + i + "\n");
					write.write("ID: " + bill.getId() + "\n");
					write.write("Model: " + bill.getModel() + "\n");
					write.write("Brand: " + bill.getBrand() + "\n");
					write.write("Color: " + bill.getColor() + "\n");
					write.write("Quantities: " + bill.getQuantites() + "\n");
					write.write("Price: " + bill.getPrice() + "\n");
					write.write("Total: " + (bill.getQuantites() * bill.getPrice()) + "\n");
					write.write("Money: " + bill.getMoney() + "\n");
					write.write("Change: " + (bill.getMoney() - (bill.getQuantites() * bill.getPrice())) + "\n");
					write.write("~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
					i++;
				}
				write.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			alert(AlertType.INFORMATION, "Cool", "Info", "The struk successfully printed");
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
