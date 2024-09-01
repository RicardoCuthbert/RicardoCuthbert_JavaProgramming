package application;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Stock {

	private String id;
	private String model;
	private String brand;
	private String color;
	private int price;
	public ObservableList<Stock> menuList = FXCollections.observableArrayList();
	@SuppressWarnings("unused")
	private static Map<String, Integer> brandIdCounter = new HashMap<>();
	// alt shift s

	@SuppressWarnings("unused")
	public Stock(String id, String model, String brand, String color, int price) {
		super();
		this.id = id;
		this.model = model;
		this.brand = brand;
		this.color = color;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
