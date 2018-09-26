package app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientdbConnect {

	public static Connection getConnect() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/patientdb";
			String username = "root";
			String password = "1234";
			Class.forName(driver);

			java.sql.Connection con = DriverManager.getConnection(url, username, password);

			System.out.println("connected");
			return con;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	public static Connection con = getConnect();
	
	public static void createDB() throws Exception {
		try {

			PreparedStatement create = con.prepareStatement("CREATE DATABASE IF NOT EXISTS patientdb");
			create.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void createTable() throws Exception {
		try {

			PreparedStatement create = con.prepareStatement(
					"create table if not exists patient (id int AUTO_INCREMENT, nom varchar(100) not null,prenom varchar(100) not null,dateprisCharge timestamp default now() ,prisechargeNum int ,assurer varchar (200),assurNum int ,seanceNumber int , prix float ,primary key(id));");
			create.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void createTableDepense() throws Exception {
		try {

			PreparedStatement create = con.prepareStatement(
					"create table if not exists depense (id int AUTO_INCREMENT, nomProduit varchar(100) not null,prix  float not null, dateprisCharge timestamp default now(), primary key(id) );");
			create.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void createTableKine() throws Exception {
		try {

			PreparedStatement create = con.prepareStatement(
					"create table if not exists profile (id int AUTO_INCREMENT, nom varchar(200) not null,code  varchar(200),rip varchar(200),mf varchar(200),cnss varchar(200),primary key(id));");
			create.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}


	public static ResultSet select(String table) throws SQLException {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from " + table);

		// st.close();
		return rs;
	}

	public static void insert() throws SQLException {
		

		// the mysql insert statement
		String query = " insert into patient (nom,prenom,prisechargeNum,assurer,assurNum,seanceNumber,prix)"
				+ " values (?,?,?,?,?,?,?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, "Darney");
		preparedStmt.setString(2, "Didou");
		preparedStmt.setInt(3, 45);
		preparedStmt.setString(4, "hytyyy");
		preparedStmt.setInt(5, 45);
		preparedStmt.setInt(6, 45);
		preparedStmt.setFloat(7, (float) 11.5);
		preparedStmt.execute();

	}

}
