package ui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.change_vision.jude.api.inf.AstahAPI;

import parser.Principal;



public class ValidaDialog  extends JDialog{
	
	public ValidaDialog(JFrame frame, boolean modal) throws FileNotFoundException, IOException, 
    		ClassNotFoundException {
		super(frame, modal);
        initComponents();
        this.setTitle("Valida Modelo");
        this.setLocation(new Point(276, 182));
        this.setSize(new Dimension(450, 150));
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
	}
	
	private void initComponents()  {
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton findButton;
        findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean check;
				try {
					Principal p = new Principal();
					check =p.valida();
					
					if(!check) {
					JOptionPane.showMessageDialog(null, "Model with problems!");
					}
					
				}
				
				catch(Exception ex) {
					ex.printStackTrace();
				}
				}
        });
        
        add(findButton, gbc);
        gbc.gridx = 2;
        gbc.gridy++;
	}

}
