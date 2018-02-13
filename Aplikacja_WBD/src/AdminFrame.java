import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class AdminFrame extends JFrame implements ActionListener{
	
	protected JPanel adminPanel;
	protected JPanel childPanel;
	protected LogFrame logFrame;
	
	public AdminFrame()
	{
		super("Administrator");
		Dimension dim = new Dimension(800, 600);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        createPanel();
        
        add(adminPanel);
        
        setVisible(true);
	}

	public void createPanel()
	{
		adminPanel = new JPanel();
		childPanel = new JPanel();
		adminPanel.setLayout(new BorderLayout());
		childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 50));
		
		JLabel admin = new JLabel("Panel Administratora");
		childPanel.add(admin, SwingConstants.CENTER);
		
		JButton show = new JButton("Wyœwietl");
		show.setActionCommand("Show");
		show.addActionListener(this);
		childPanel.add(show);
		
		JButton mod = new JButton("Zmieñ");
		mod.setActionCommand("Mod");
		mod.addActionListener(this);
		childPanel.add(mod);
		
		JButton add = new JButton("Dodaj");
		add.setActionCommand("Add");
		add.addActionListener(this);
		childPanel.add(add);
		
		JButton delete = new JButton("Usuñ");
		delete.addActionListener(this);
		delete.setActionCommand("Delete");
		childPanel.add(delete);
		
		JButton logout = new JButton("Wyloguj");
		logout.addActionListener(this);
		logout.setActionCommand("Logout");
		childPanel.add(logout);
		
		adminPanel.add(childPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String komenda = e.getActionCommand();
		
		switch(komenda)
		{
			case "Show":
			{
				setVisible(false);
				ShowAdminFrame showAdminFrame = new ShowAdminFrame();
				break;
			}
			
			case "Mod":
			{
				setVisible(false);
				ModAdminFrame modAdminFrame = new ModAdminFrame();
				break;
			}
			
			case "Add":
			{
				setVisible(false);
				AddAdminFrame addAdminFrame = new AddAdminFrame();
				break;
			}
			
			case "Delete":
			{
				setVisible(false);
				DeleteAdminFrame deleteAdminFrame = new DeleteAdminFrame();
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
