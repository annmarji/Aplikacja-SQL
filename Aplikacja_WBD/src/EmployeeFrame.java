import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class EmployeeFrame extends JFrame implements ActionListener{
	
	protected JPanel employeePanel;
	protected JPanel childPanel;
	protected LogFrame logFrame;
	
	public EmployeeFrame()
	{
		super("Pracownik");
		Dimension dim = new Dimension(800, 600);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        createPanel();
        
        add(employeePanel);
        
        setVisible(true);
	}

	public void createPanel()
	{
		employeePanel = new JPanel();
		childPanel = new JPanel();
		employeePanel.setLayout(new BorderLayout());
		childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 50));
		
		JLabel employee = new JLabel("Panel Pracownika");
		childPanel.add(employee, SwingConstants.CENTER);
		
		JButton show = new JButton("Wyœwietl swoje dane");
		show.setActionCommand("Show");
		show.addActionListener(this);
		childPanel.add(show);
		
		JButton mod = new JButton("Zmieñ swoje dane");
		mod.setActionCommand("Mod");
		mod.addActionListener(this);
		childPanel.add(mod);
		
		JButton add = new JButton("Poka¿ historiê wynagrodzenia");
		add.setActionCommand("Pay");
		add.addActionListener(this);
		childPanel.add(add);
		
		JButton logout = new JButton("Wyloguj");
		logout.addActionListener(this);
		logout.setActionCommand("Logout");
		childPanel.add(logout);
		
		employeePanel.add(childPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String komenda = e.getActionCommand();
		
		switch(komenda)
		{
			case "Show":
			{
				setVisible(false);
				ShowEmployeeFrame showEmployeeFrame = new ShowEmployeeFrame();
				break;
			}
			
			case "Mod":
			{
				setVisible(false);
				ModEmployeeFrame modEmployeeFrame = new ModEmployeeFrame();
				break;
			}
			
			case "Pay":
			{
				setVisible(false);
				ShowPayEmployee showPayEmployee = new ShowPayEmployee();
				break;
			}
			
			case "Logout":
			{
				setVisible(false);
				logFrame = new LogFrame();
				break;
			}
			
			default:
				break;
		
		}
		
	}
}
