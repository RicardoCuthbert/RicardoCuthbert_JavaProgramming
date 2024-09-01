package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("unused")
public class Bill {

	private String id;
	private String model;
	private String brand;
	private String color;
	private int quantites;
	private int price;
	private int total;
	private ObservableList<Stock> menuList = FXCollections.observableArrayList();
	private int money;

	public Bill(String model, String brand, String color, int quantites, int price, int total, int money) {
		super();
		String id;
		if (menuList.size()==0) {
			this.id = brand.charAt(0) + "001";
		}else {
			int tot = menuList.size();
			int lastind = tot-1;
			Stock last = menuList.get(lastind);
			String lastid = last.getId();
			int lastnum = Integer.parseInt(lastid.substring(1));
			
			int newNum = lastnum+1;
			String idNum = String.format("%03d", newNum);
			this.id = brand.charAt(0) + idNum;
		}
		this.model = model;
		this.brand = brand;
		this.color = color;
		this.quantites = quantites;
		this.price = price;
		this.total = total;
		this.money = money;
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

	public int getQuantites() {
		return quantites;
	}

	public void setQuantities(int quantities) {
		this.quantites = quantities;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
