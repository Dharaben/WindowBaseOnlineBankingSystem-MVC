/******************************************************************************
 *	Program Author: Dhara Patel for CSCI 6810 Java and the Internet	          *
 *	Date:October, 2018													      *
 *******************************************************************************/

import java.awt.*;     //including Java packages used by this program
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.GridBagLayout;
import java.util.Date;
import Com.Patel.*;

class AccountOverviewPanel  extends JPanel implements ActionListener
{
    private String UName,Name;
    private JButton Refresh,ShowMeInterest;
    private JLabel CBalance, SBalance;


    public AccountOverviewPanel(String U_Name,String CustomerName)
    {
        UName=U_Name;
        Name=CustomerName;


        JLabel WelcomeLabel =new JLabel("Welcome! " +CustomerName);
        WelcomeLabel.setFont(new Font("TimesRoman",Font.BOLD,20));

        //GrideBagLayout Gridbg= new GrideBagLayout();
        //setLayout(Gridbg);
        Refresh = new JButton("Refresh");
        Refresh.setFont(new Font("TimesRoman",Font.BOLD,16));
        ShowMeInterest=new JButton ("Show Me Interest");
        ShowMeInterest.setFont(new Font("TimesRoman",Font.BOLD,16));

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 100;
        gbc.weighty = 1;

        // add(welcomeLabel);
        //add(WelcomeLabel, gbc, 0, 0, 1, 1);

        JPanel WelcomePanel = new JPanel();

        WelcomePanel.add(WelcomeLabel);
        WelcomePanel.setAlignmentX(0.5f);
        add(WelcomePanel, gbc, 0, 0, 0, 1);

        JPanel CAPanel = new JPanel();

        JPanel SAPanel = new JPanel();

        //JPanel ButtonPanel=new JPanel();

        CheckingAccount CA = new CheckingAccount();
        CA = CA.getAccountInfo(UName);

        SavingsAccount SA = new SavingsAccount();
        SA = SA.getAccountInfo(UName);

        if(!CA.getCANum().equals(""))
        {
            JLabel CNumberLabel = new JLabel("Account Number: ****");
            CNumberLabel.setFont(new Font("TimesRoman",Font.BOLD,14));
            JLabel CNumber = new JLabel();
            CNumber.setText(CA.getCANum().substring(4));
            JLabel CBalanceLabel = new JLabel("Current Balance: $");
            CBalanceLabel.setFont(new Font("TimesRoman",Font.BOLD,14));
            CBalance = new JLabel();
            CBalance.setText(String.valueOf(CA.getBal()));


            CAPanel.add(CNumberLabel);
            CAPanel.add(CNumber);
            CAPanel.add(CBalanceLabel);
            CAPanel.add(CBalance);
            // add(CAType, gbc, 0, 3, 1, 1);
            // add(CNumberLabel, gbc, 0, 4, 1, 1);
            // add(CNumber, gbc, 1, 4, 1, 1);
            // add(CBalanceLabel, gbc, 0, 5, 1, 1);
            // add(CBalance, gbc, 1, 5, 1, 1);

            TitledBorder title1 = BorderFactory.createTitledBorder("Checking Account");
            CAPanel.setBorder(title1);

            add(CAPanel, gbc, 0, 2, 0, 1);
        }

        if(!SA.getSANum().equals("")){
            JLabel SNumberLabel = new JLabel("Account Number: ****");
            SNumberLabel.setFont(new Font("TimesRoman",Font.BOLD,14));
            JLabel SNumber = new JLabel();
            SNumber.setText(SA.getSANum().substring(4));
            JLabel SBalanceLabel = new JLabel("Current Balance: $");
            SBalanceLabel.setFont(new Font("TimesRoman",Font.BOLD,14));
            SBalance = new JLabel();
            SBalance.setText(String.valueOf(SA.getBal()));

            SAPanel.add(SNumberLabel);
            SAPanel.add(SNumber);
            SAPanel.add(SBalanceLabel);
            SAPanel.add(SBalance);
            //SAPanel.add(Refresh);

            // add(SAType, gbc, 0, 7, 1, 1);
            // add(SNumberLabel, gbc, 0, 8, 1, 1);
            // add(SNumber, gbc, 1, 8, 1, 1);
            // add(SBalanceLabel, gbc, 0, 9, 1, 1);
            // add(SBalance, gbc, 1, 9, 1, 1);

            TitledBorder title2 = BorderFactory.createTitledBorder("Savings Account");
            SAPanel.setBorder(title2);

            add(SAPanel, gbc, 0, 3, 0, 1);
        }
        add(Refresh,gbc,0,4,1,1);
        add(ShowMeInterest,gbc,1,4,1,1);

        Refresh.addActionListener(this);
        ShowMeInterest.addActionListener(this);
    }

    public void add(Component c, GridBagConstraints gbc, int x, int y, int w, int h){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        add(c, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String arg = e.getActionCommand();
        if(arg.equals("Refresh")) {
            CheckingAccount CA = new CheckingAccount();
            CA = CA.getAccountInfo(UName);
            CBalance.setText(String.valueOf(CA.getBal()));

            SavingsAccount SA = new SavingsAccount();
            SA = SA.getAccountInfo(UName);
            SBalance.setText(String.valueOf(SA.getBal()));
        }else if(arg.equals("Show Me Interest")) {
            SavingsAccount SA = new SavingsAccount();
            SA = SA.getAccountInfo(UName);
            float principle = SA.getBal();
            float interest = 0.012f;
            float accruedInterest = principle * interest;

            Date date = new Date();

            String h = String.format("%02d", date.getHours());
            String m = String.format("%02d", date.getMinutes());
            String s = String.format("%02d", date.getSeconds());
            String time = (h + ":" + m + ":" + s);

            Transaction transct = new Transaction(new Date(), accruedInterest,"interest", time, null, SA.getSANum(), UName);
            transct.recordTransaction();
            JOptionPane.showMessageDialog(null, "Interest this month: $" + (String)String.valueOf(accruedInterest), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
