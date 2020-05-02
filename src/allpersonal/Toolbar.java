package allpersonal;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener{
	// Variables
	private JButton saveButton;
	private JButton cancleButton;
	private JButton loginButton;
	private JButton searchButton;
	private JButton addButton;
	private JButton modifyButton;
	private JButton[] selectionButton;
	
	private EntryListener entryListener;
	
	public Toolbar(){
	
		System.out.println("Tool bar instantiated");
		cancleButton = new JButton("Cancle"); // Cancel Button.
		saveButton = new JButton("Save"); // Save Button.
		loginButton = new JButton("Login");
		FlowLayout flow = new FlowLayout(); // Flow Layout of Panel.
		setLayout(flow); // Sets layout of panel.
		
		loginButton.addActionListener(this);
		cancleButton.addActionListener(this); // Adds action listener to button.
		saveButton.addActionListener(this); // Adds action listener to button.
		
		
		// Adding components to panel
		//add(cancleButton);
		//add(saveButton);
		add(loginButton);
		
	
	}
	
	public void login(){
		System.out.println("Tool bar instantiated");
		FlowLayout flow = new FlowLayout(); // Flow Layout of Panel.
		setLayout(flow); // Sets layout of panel.
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		add(loginButton);
		validate();
	}
	
	public void AddEntry(){
		System.out.println("Tool bar instantiated");
		cancleButton = new JButton("Cancle"); // Cancel Button.
		saveButton = new JButton("Save"); // Save Button.
		FlowLayout flow = new FlowLayout(); // Flow Layout of Panel.
		setLayout(flow); // Sets layout of panel.
		cancleButton.addActionListener(this); // Adds action listener to button.
		saveButton.addActionListener(this); // Adds action listener to button.
		add(cancleButton);
		add(saveButton);
		System.out.println("Add button");
		validate();
	}
	
	public void Menu(String[] menu){
		System.out.println(menu.length);
		selectionButton = new JButton[menu.length];
		int xcounter = 0;
		int ycounter = 0;
		
		setLayout(new java.awt.GridBagLayout());
		GridBagConstraints brid = new GridBagConstraints();
		brid.insets = new Insets(10,5,35,10);
		
		for(int i = 0; i < menu.length; i++){
			if(xcounter < 2){
				System.out.println("Made button out of: " + menu[i]);
				selectionButton[i] = new JButton(menu[i]);
				brid.fill = GridBagConstraints.HORIZONTAL;
				brid.gridx = xcounter;
				brid.gridy = ycounter;
				selectionButton[i].addActionListener(this);
				add(selectionButton[i], brid);
				xcounter++;
			} else {
				xcounter = 0;
				ycounter++;
				
				selectionButton[i] = new JButton(menu[i]);
				brid.fill = GridBagConstraints.HORIZONTAL;
				brid.gridx = xcounter;
				brid.gridy = ycounter;
				selectionButton[i].addActionListener(this);
				add(selectionButton[i], brid);
				xcounter++;
			}
		}
		validate();
	}
	
	public void setEntryListener(EntryListener entryListener) {
		this.entryListener = entryListener;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if(clicked == saveButton) {
			if(entryListener != null){
				entryListener.debug("Saved entry");
				setEntryListener(entryListener);
				entryListener.saveOrCancle(1);
				setEntryListener(entryListener);
			}
		} 
		if(clicked == cancleButton) {
			if(entryListener != null){
				entryListener.debug("Canceled entry");
				setEntryListener(entryListener);
			}
		} 
		if(clicked == loginButton){
			if(entryListener != null){
				entryListener.debug("Logged in");
				setEntryListener(entryListener);
				remove(loginButton);
				entryListener.saveOrCancle(0);
				setEntryListener(entryListener);
			}
		}
		for(int i = 0; i < selectionButton.length; i++){
			if(clicked == selectionButton[i]) {
				if(entryListener != null){
					entryListener.debug("Clicked on: " + selectionButton[i].getText());
					setEntryListener(entryListener);
					entryListener.saveOrCancle(2);
					setEntryListener(entryListener);
				}
			}
		}
	}
}
