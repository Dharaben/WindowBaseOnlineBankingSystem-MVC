/******************************************************************************
 *	Program Author: Dr. Yongming Tang for CSCI 6810 Java and the Internet	  *
 *	Date: February, 2014													  *
 *******************************************************************************/

import java.lang.*; //including Java packages used by this program
import javax.swing.*;
import java.util.*;
import Com.Patel.*;

public class OpenBankAccountControl
{
    public OpenBankAccountControl(String AcountType, String  AcountNumber, String  Name, String  UName, float  Balance)
    {
        //Use CheckingAccount object to invoke method openAcct()
        Transaction tr;
        if (AcountType.equals("Checking"))
        {
            CheckingAccount CA = new CheckingAccount(AcountNumber, Name, UName, Balance);
            if (CA.openAcct())
            {
                Date date = new Date();
                String h = String.format("%02d", date.getHours());
                String m = String.format("%02d", date.getMinutes());
                String s = String.format("%02d", date.getSeconds());
                String time = (h + ":" + m + ":" + s);
                tr = new Transaction(date, Balance, "Deposit", time, "", AcountNumber, UName);
                if(tr.recordTransaction())
                {
                    //System.out.println("successful!");
                    JOptionPane.showMessageDialog(null, "Opening a Checking Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    //System.out.println("fail!");
                    JOptionPane.showMessageDialog(null, "Opening a Checking Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if(AcountType.equals("Savings"))
        {

            SavingsAccount SA = new SavingsAccount(AcountNumber, Name, UName, Balance);
            if (SA.openAcct())
            {
                Date date = new Date();
                String h = String.format("%02d", date.getHours());
                String m = String.format("%02d", date.getMinutes());
                String s = String.format("%02d", date.getSeconds());
                String time = (h + ":" + m + ":" + s);
                tr = new Transaction(date, Balance, "Deposit", time, "", AcountNumber, UName); // Add TransactionTime
                if(tr.recordTransaction())
                {
                    System.out.println("successful!");
                    JOptionPane.showMessageDialog(null, "Opening a Savings Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else
                //System.out.println("fail!");
                JOptionPane.showMessageDialog(null, "Opening a Savings Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}