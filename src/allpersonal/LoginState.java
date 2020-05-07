package allpersonal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginState extends JPanel implements GUIState, ActionListener{

	// Private Variables
	private JButton loginButton;
	private GUIState guiState;
	private Context context;
	private TextPanel textPanel;
	private JPanel buttonPanel;
	private static final long serialVersionUID = 1L;
	
	public LoginState() {
		System.out.println("LoginState Class ON");
	}
	
	private void SetupButtons() {
		buttonPanel = new JPanel();
		//FlowLayout flow = new FlowLayout(); // Flow Layout of Panel.
		//setLayout(flow); // Sets layout of panel.
		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		buttonPanel.add(loginButton);
		//add(buttonPanel, BorderLayout.SOUTH);
		validate();
	}
	
	private void SetupTextFields(String[] connectionParamters, String[] dataBaseTypes){
		textPanel = new TextPanel(connectionParamters, dataBaseTypes);
		try {
			BufferedReader loadFile = new BufferedReader(new FileReader("LoginSave.txt"));
			for(int i = 0; i < textPanel.columnTextField.length; i++) {
				textPanel.columnTextField[i].setText(loadFile.readLine());
			}
			loadFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	@Override
	public void Action(Context context, Server server, String[] dataSetOne, String[] dataSetTwo) {
		SetupButtons();
		SetupTextFields(dataSetOne, dataSetTwo);

		context.setState(this);
		this.context = context;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if(clicked == loginButton){
			if(guiState != null){
				// TextField into String
				String[] loginData = new String[textPanel.columnTextField.length];
				for(int i = 0; i < textPanel.columnTextField.length; i++) {
				loginData[i] = textPanel.columnTextField[i].getText().toString();
				System.out.println("Saved data was" + loginData[i]);
				}
				
				// Save Login Info
				try {
				FileWriter saveFile = new FileWriter("LoginSave.txt");
				for(int i = 0; i < textPanel.columnTextField.length; i++) {
				saveFile.write(textPanel.columnTextField[i].getText().toString() +"\n");
				}
				saveFile.close();
				} catch(Exception e1){
					
				}
				
				setGUIState(guiState);
				guiState.Action(this.context, null, loginData, null);
				setGUIState(guiState);
				}
			}
	}
	
	public JPanel getButton() {
		return buttonPanel;
	}
	
	public JPanel getTextField() {
		return textPanel;
	}
	
	public JTextField[] getJTextColumns() {
		return textPanel.columnTextField;
	}
	
	public String toString(){
		return "Login State";
	}

}
