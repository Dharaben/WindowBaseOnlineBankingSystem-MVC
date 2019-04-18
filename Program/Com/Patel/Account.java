/******************************************************************************
 *	Program Author: Dr. Yongming Tang for CSCI 6810 Java and the Internet	  *
 *	Date: September, 2012													  *
 *******************************************************************************/
package Com.Patel;
import Com.Patel.*;
import java.lang.*; //including Java packages used by this program
import java.sql.*;
import java.util.Vector;


public class Account {
	private String Username, Password, Password1, Name;

	public Account(String UN, String PassW, String PassW1, String NM) {
		Username = UN;
		Password = PassW;
		Password1 = PassW1;
		Name = NM;
	}

	public Account(String UN, String PassW) {
		Username = UN;
		Password = PassW;
	}
	public Account(String UN)
	{
		Username=UN;
	}
	public Account()
	{
		}

	public boolean signUp() {
		boolean done = !Username.equals("") && !Password.equals("") && !Password1.equals("") && Password.equals(Password1);
		try {
			if (done) {
				DBConnection ToDB = new DBConnection(); //Have a connection to the DB
				Connection DBConn = ToDB.openConn();
				Statement Stmt = DBConn.createStatement();
				String SQL_Command = "SELECT Username FROM Account WHERE Username ='" + Username + "'"; //SQL query command
				ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
				done = done && !Rslt.next();
				if (done) {
					SQL_Command = "INSERT INTO Account(Username, Password, Name) VALUES ('" + Username + "','" + Password + "','" + Name + "')"; //Save the username, password and Name
					Stmt.executeUpdate(SQL_Command);
				}
				Stmt.close();
				ToDB.closeConn();
			}
		} catch (SQLException e) {
			done = false;
			System.out.println("SQLException: " + e);
			while (e != null) {
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("Message: " + e.getMessage());
				System.out.println("Vendor: " + e.getErrorCode());
				e = e.getNextException();
				System.out.println("");
			}
		} catch (Exception e) {
			done = false;
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		return done;
	}

	public boolean changePassword(String NewPassword) {    //5
		boolean done = false;
		try {        //20
			DBConnection ToDB = new DBConnection(); //Have a connection to the DB
			Connection DBConn = ToDB.openConn();
			Statement Stmt = DBConn.createStatement();
			String SQL_Command = "SELECT * FROM Account WHERE Username ='" + Username + "'AND Password ='" + Password + "'"; //SQL query command
			ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
			if (Rslt.next()) {
				SQL_Command = "UPDATE Account SET Password='" + NewPassword + "' WHERE Username ='" + Username + "'"; //Save the username, password and Name
				Stmt.executeUpdate(SQL_Command);
				Stmt.close();
				ToDB.closeConn();
				done = true;
			}
		} catch (SQLException e)        //5
		{
			done = false;
			System.out.println("SQLException: " + e);
			while (e != null) {
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("Message: " + e.getMessage());
				System.out.println("Vendor: " + e.getErrorCode());
				e = e.getNextException();
				System.out.println("");
			}
		} catch (Exception e) {
			done = false;
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		return done;
	}

	public String signIn() {
		String Name = "";
		try {

			DBConnection ToDB = new DBConnection(); //Have a connection to the DB
			Connection DBConn = ToDB.openConn();
			Statement Stmt = DBConn.createStatement();
			String SQL_Command = "SELECT Name FROM Account WHERE Username ='" + Username + "'AND Password ='" + Password + "'"; //SQL query command
			ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.

			if (Rslt.next()) {
				Name = Rslt.getString(1);
				Stmt.close();
				ToDB.closeConn();

			}

		} catch (SQLException e) {

			System.out.println("SQLException: " + e);
			while (e != null) {
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("Message: " + e.getMessage());
				System.out.println("Vendor: " + e.getErrorCode());
				e = e.getNextException();
				System.out.println("");
			}
		} catch (Exception e) {

			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		return Name;
	}

	public Vector[] Inquire(String str1, String str2)
	{
		Vector[] vectors = new Vector[2];
		Vector columnNames = new Vector();
		Vector data = new Vector();

		try {
			DBConnection ToDB = new DBConnection(); // Have a connection to the
			Connection DBConn = ToDB.openConn();
			Statement Stmt = DBConn.createStatement();

			String SQL_Command = "SELECT * FROM Transactions WHERE TransactionDate BETWEEN '"+str2+"' AND '"+str1+"' AND CustomerID ='"+Username+"'";
			//String SQL_Command = "SELECT * FROM Transactions WHERE TransactionDate BETWEEN '2018-10-22' AND '2018-10-24'";
			System.out.println("query: "+SQL_Command);
			ResultSet rs = Stmt.executeQuery(SQL_Command);
			if(null == rs)
				System.out.println("result set is null");
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				// System.out.println(metaData.getColumnName(i));
				// System.out.println(metaData.getColumnTypeName(i));
				columnNames.addElement(metaData.getColumnName(i));
			}

			while (rs.next()) {
				Vector row = new Vector(columns);
				// System.out.println(columns);
				for (int i = 1; i <= columns; i++) {
					// System.out.println(i);
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
			Stmt.close();
			ToDB.closeConn();
		}

		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException: " + e);
			while (e != null) {
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("Message: " + e.getMessage());
				System.out.println("Vendor: " + e.getErrorCode());
				e = e.getNextException();
				System.out.println("");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		vectors[0] = columnNames;
		vectors[1] = data;
		return vectors;
		// return data;
	}
}


