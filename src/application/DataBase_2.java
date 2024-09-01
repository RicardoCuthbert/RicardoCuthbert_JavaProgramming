package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("unused")
public class DataBase_2 {
	
	private final String URL = "jdbc:mysql://localhost:3306/bncc_bootcamp";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	
	private static DataBase_2 instance;
	private static Connection con;
	
	public DataBase_2() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	public static DataBase_2 getInstance() {
		if (instance == null) {
			instance = new DataBase_2();
		}
		return instance;
	}
	
	public PreparedStatement preparestatement(String query) throws SQLException {
		return con.prepareStatement(query);
	}
	
	public static ObservableList<Bill> getall(){
		String query = "SELECT * FROM history";
		ObservableList<Bill> menulist = FXCollections.observableArrayList();
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				menulist.add(new Bill(rs.getString("model"), rs.getString("brand"), rs.getString("color"), rs.getInt("quantites"), rs.getInt("price"), rs.getInt("total"), rs.getInt("money")));
 			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menulist;
	}
	
	public static void add(Bill menu) {
		String query = "INSERT INTO history (id, model, brand, color, quantites, price, total, money) VALUES (?, ? , ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, menu.getId());
			ps.setString(2, menu.getModel());
			ps.setString(3, menu.getBrand());
			ps.setString(4, menu.getColor());
			ps.setInt(5, menu.getQuantites());
			ps.setInt(6, menu.getPrice());
			ps.setInt(7, menu.getTotal());
			ps.setInt(8, menu.getMoney());

			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
