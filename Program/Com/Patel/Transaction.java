/******************************************************************************
 *	Program Author: Dhara Patel for CSCI 6810 Java and the Internet	          *
 *	Date:September, 2018													  *
 *******************************************************************************/
package Com.Patel;
import Com.Patel.*;
import java.util.*;
import java.lang.*; //including Java packages used by this program
import java.sql.*;
import java.util.UUID;

public class Transaction
{
	//Instance Variables
	private UUID TransactionNumber;
	private java.util.Date TransactionDate;
	private float TransactionAmount;
	private String TransactionType, FromAccount, ToAccount, CustomerID, TransactionTime;

	/*constructors*/
	public Transaction(java.util.Date T_Date, float Amt, String T_Type,String T_Time, String From_Acc, String To_Acc, String Cust_ID) {
		TransactionDate = T_Date;
		TransactionAmount = Amt;
		TransactionTime= T_Time;
		TransactionType = T_Type;
		FromAccount = From_Acc;
		ToAccount = To_Acc;
		CustomerID = Cust_ID;
	}
	public Transaction(UUID T_Id, java.util.Date T_Date, float Amt, String T_Type,String T_Time, String From_Acc, String To_Acc, String Cust_ID) {
		TransactionNumber = T_Id;
		TransactionDate = T_Date;
		TransactionAmount = Amt;
		TransactionTime= T_Time;
		TransactionType = T_Type;
		FromAccount = From_Acc;
		ToAccount = To_Acc;
		CustomerID = Cust_ID;
	}
	//public Transaction() {
	//}

	public boolean recordTransaction()
	{
		System.out.println("("+TransactionDate+") ("+TransactionType+") ("+CustomerID+")");
		boolean done = !TransactionDate.equals("") && !TransactionType.equals("") && !CustomerID.equals("");
		try {
			if (done)
			{
				DBConnection ToDB = new DBConnection(); //Have a connection to the DB
				Connection DBConn = ToDB.openConn();
				Statement Stmt = DBConn.createStatement();
				if (done)
				{
					// Unique ID generation
					TransactionNumber =  UUID.randomUUID();
					//converting java date to sql date
					java.sql.Date SQ_TransactionDate = new java.sql.Date(TransactionDate.getTime());
					System.out.println(TransactionNumber+ "','"+SQ_TransactionDate+"','"+TransactionAmount+"','"+TransactionType+"','"+TransactionTime+"','"+FromAccount+"','"+ToAccount+"','"+CustomerID);
					String SQL_Command = "INSERT INTO Transactions(TransactionNumber, TransactionDate, TransactionAmount, TransactionType, TransactionTime, FromAccount, ToAccount, CustomerID) VALUES ('"+TransactionNumber+ "','"+SQ_TransactionDate+"','"+TransactionAmount+"','"+TransactionType+"','"+TransactionTime+"','"+FromAccount+"','"+ToAccount+"','"+CustomerID+"')";
					Stmt.executeUpdate(SQL_Command);
				}
				Stmt.close();
				ToDB.closeConn();
			}
		}
		catch(SQLException e)
		{
			done = false;
			System.out.println("SQLException: " + e);
			while (e != null)
			{   System.out.println("SQLState: " + e.getSQLState());
				System.out.println("Message: " + e.getMessage());
				System.out.println("Vendor: " + e.getErrorCode());
				e = e.getNextException();
				System.out.println("");
			}
		}
		catch (Exception e)
		{         done = false;
			System.out.println("Exception: " + e);
			e.printStackTrace ();
		}
		return done;
	}


/*	public boolean searchTransaction(String TransactionNumber)
	{

		//System.out.println("("+TransactionDate+") ("+TransactionType+") ("+CustomerID+")");
		boolean done = !TransactionNumber.equals("") && !CustomerID.equals("");
		try {
			if (done)
			{
				DBConnection ToDB = new DBConnection(); //Have a connection to the DB
				Connection DBConn = ToDB.openConn();
				Statement Stmt = DBConn.createStatement();
				String SQL_Command = "SELECT TransactionNumber FROM Transactions WHERE TransactionNumber ='"+TransactionNumber+"'"; //SQL query command for Balance
				ResultSet Rslt = Stmt.executeQuery(SQL_Command);
				while (Rslt.next())
				if (done)
				{
					// Unique ID generation
					TransactionNumber = String.valueOf(UUID.randomUUID());
					//converting java date to sql date
					java.sql.Date SQ_TransactionDate = new java.sql.Date(TransactionDate.getTime());
					System.out.println(TransactionNumber+ "','"+SQ_TransactionDate+"','"+TransactionAmount+"','"+TransactionType+"','"+TransactionTime+"','"+FromAccount+"','"+ToAccount+"','"+CustomerID);
					//SQL_Command = "INSERT INTO Transactions(TransactionNumber, TransactionDate, TransactionAmount, TransactionType, TransactionTime, FromAccount, ToAccount, CustomerID) VALUES ('"+TransactionNumber+ "','"+SQ_TransactionDate+"','"+TransactionAmount+"','"+TransactionType+"','"+TransactionTime+"','"+FromAccount+"','"+ToAccount+"','"+CustomerID+"')";
					Stmt.executeUpdate(SQL_Command);
				}
				Stmt.close();
				ToDB.closeConn();
			}
		}
	catch(SQLException e)
	{
		done = false;
		System.out.println("SQLException: " + e);
		while (e != null)
		{
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("Message: " + e.getMessage());
			System.out.println("Vendor: " + e.getErrorCode());
			e = e.getNextException();
			System.out.println("");
		}
	}
	catch (Exception e)
	{
		done = false;
		System.out.println("Exception: " + e);
		e.printStackTrace ();
	}
	return done;
	}*/

	public List<Transaction> getTransactionList(String Cust_ID){
		List <Transaction> TransactionList = new ArrayList<Transaction>();
		boolean done = false;
		try {

			DBConnection ToDB = new DBConnection(); //Have a connection to the DB
			Connection DBConn = ToDB.openConn();
			Statement Stmt = DBConn.createStatement();
			String SQL_Command = "SELECT * FROM Transactions WHERE CustomerID ='"+Cust_ID+"';"; //SQL query command
			ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
			while (Rslt.next()) {
				UUID T_Id = UUID.fromString(Rslt.getString("TransactionNumber"));
				float Amt = Float.parseFloat(Rslt.getString("TransactionAmount"));
				//formating string to date

				java.sql.Date SQ_T_Date = Rslt.getDate("TransactionDate");
				java.util.Date T_Date = new java.util.Date(SQ_T_Date.getTime());
				String T_Type = Rslt.getString("TransactionType");
				String T_Time =Rslt.getString("TransactionTime");
				String From_Acc =  Rslt.getString("FromAccount");
				String To_Acc = Rslt.getString("ToAccount");
				Cust_ID = Rslt.getString("CustomerID");
				Transaction Transaction = new Transaction(T_Id, T_Date, Amt, T_Type,T_Time ,From_Acc,To_Acc, Cust_ID); // Creating a new object
				TransactionList.add(Transaction);
			}

			Stmt.close();
			ToDB.closeConn();
			done=true;
		}

		catch(java.sql.SQLException e)
		{
			done = false;
			System.out.println("SQLException: " + e);
			while (e != null)
			{
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("Message: " + e.getMessage());
				System.out.println("Vendor: " + e.getErrorCode());
				e = e.getNextException();
				System.out.println("");
			}
		}
		catch (java.lang.Exception e)
		{
			done = false;
			System.out.println("Exception: " + e);
			e.printStackTrace ();
		}
		return TransactionList;
	}

}