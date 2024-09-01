package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;


@SuppressWarnings("unused")
public class DataBase {
	private final String URL = "jdbc:mysql://localhost:3306/bncc_bootcamp";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	
	private static DataBase instance;
	private static Connection con;
	String id;
	public DataBase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DataBase getInstance() {
		if (instance == null) {
			instance = new DataBase();
		}
		return instance;
	}
	
	public PreparedStatement preparestatement(String query) throws SQLException {
		return con.prepareStatement(query);
	}
	
	public static ObservableList<Stock> getall(){
		String query = "SELECT * FROM menu";
		ObservableList<Stock> menulist = FXCollections.observableArrayList();
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				menulist.add(new Stock(rs.getString("id"), rs.getString("model"), rs.getString("brand"), rs.getString("color"), rs.getInt("price")));
 			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menulist;
	}

	
	public static void add(Stock menu) {
		String query = "INSERT INTO menu (id, model, brand, color, price) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, menu.getId());
			ps.setString(2, menu.getModel());
			ps.setString(3, menu.getBrand());
			ps.setString(4, menu.getColor());
			ps.setInt(5, menu.getPrice());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void update(Stock menu) {
		String query = "UPDATE menu SET model = ?, brand = ?, color = ?, price = ? WHERE id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(2, menu.getModel());
			ps.setString(3, menu.getBrand());
			ps.setString(4, menu.getColor());
			ps.setInt(5, menu.getPrice());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void delete(Stock menu) {
		String query = "DELETE FROM menu WHERE id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, menu.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
