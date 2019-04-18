/******************************************************************************
 *	Program Author: Dhara Patel for CSCI 6810 Java and the Internet	          *
 *	Date:October, 2018													      *
 *******************************************************************************/

import java.lang.*; //including Java packages used by this program
import java.util.*;
import javax.swing.*;
import Com.Patel.*;

public class TransferControl
{
    String toAccount;
    String fromAccount;
    String UName;
    String Name;
    String Balance;
    String toAccountType, fromAccountType;

    public TransferControl(String ToAccount,String FromAccount,String uName, String name, String balance, String toAcc, String fromAcc)
    {
        toAccount = ToAccount;
        fromAccount = FromAccount;
        UName =  uName;
        Name = name;
        Balance = balance;
        toAccountType = toAcc;
        fromAccountType = fromAcc;
    }

    public void transfer()
    {
//        String fromAccountType = fromAccount.substring(0,8);
//        String toAccountType = toAccount.substring(0,8);

        if (fromAccount.equals("Choose Account Type") || toAccount.equals("Choose Account Type")) {
            JOptionPane.showMessageDialog(null, "Please choose Account type!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }

        if(fromAccountType.equals("Checking") && toAccountType.equals("Savings ")){
//            String fromAccountNumber = fromAccount.substring(11,19);
//            String toAccountNumber = toAccount.substring(10,18);
            CheckingAccount check = new CheckingAccount(fromAccount, Name, UName, Float.parseFloat(Balance));
            Boolean withdrawstatus = check.Withdraw(check.getCANum(), Float.parseFloat(Balance));
            Boolean depositstatus = false;
            if(withdrawstatus)
            {
                SavingsAccount savacc = new SavingsAccount(toAccount, Name, UName, Float.parseFloat(Balance));
                depositstatus = savacc.deposit(savacc.getSANum(), Float.parseFloat(Balance));
            }
            if (withdrawstatus && depositstatus) {
                JOptionPane.showMessageDialog(null, "Successfully Transfer From Checking To Savings!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                Date date = new Date();
                String h = String.format("%02d", date.getHours());
                String m = String.format("%02d", date.getMinutes());
                String s = String.format("%02d", date.getSeconds());
                String time = (h + ":" + m + ":" + s);
                Transaction TA = new Transaction(new Date(), Float.parseFloat(Balance), "Transfer", time, fromAccount,toAccount,UName);
                TA.recordTransaction();
            } else {
                JOptionPane.showMessageDialog(null, "UnSuccessfully Transfer From Checking To Savings!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            }
        }
        if(fromAccountType.equals("Savings ") && toAccountType.equals("Checking")){
//            String fromAccountNumber = fromAccount.substring(10,18);
//            String toAccountNumber = toAccount.substring(11,19);

            SavingsAccount savacc = new SavingsAccount(fromAccount, Name, UName, Float.parseFloat(Balance));
            Boolean withdrawstatus = savacc.Withdraw(savacc.getSANum(), Float.parseFloat(Balance));
            Boolean depositstatus = false;
            if(withdrawstatus){
                CheckingAccount check = new CheckingAccount(toAccount, Name, UName, Float.parseFloat(Balance));
                depositstatus = check.deposit(check.getCANum(), Float.parseFloat(Balance));
            }

            if (withdrawstatus && depositstatus) {
                JOptionPane.showMessageDialog(null, "Successfully Transfer From Savings To Checking!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                CheckingAccount check = new CheckingAccount(toAccount, Name, UName, Float.parseFloat(Balance));
                Date date = new Date();
                String h = String.format("%02d", date.getHours());
                String m = String.format("%02d", date.getMinutes());
                String s = String.format("%02d", date.getSeconds());
                String time = (h + ":" + m + ":" + s);
                Transaction TA = new Transaction(new Date(), Float.parseFloat(Balance), "Transfer", time, fromAccount,toAccount,UName);
                TA.recordTransaction();
            } else {
                JOptionPane.showMessageDialog(null, "UnSuccessfully Transfer From Savings To Checking!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            }
        }
    }
}
