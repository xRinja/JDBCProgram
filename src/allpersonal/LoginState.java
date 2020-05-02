package allpersonal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LoginState extends JFrame implements GUIState, ActionListener{

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
	
	private void SetupTextFields(Server server){
		textPanel = new TextPanel(server.connection, server.databaseTypes);
		//add(textPanel, BorderLayout.CENTER);
	}
	
	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	@Override
	public void Action(Context context, Server server) {
		SetupButtons();
		SetupTextFields(server);

		context.setState(this);
		this.context = context;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if(clicked == loginButton){
			if(guiState != null){
				setGUIState(guiState);
				remove(loginButton);
				guiState.Action(this.context, null);
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
	
	public String toString(){
		return "Login State";
	}

}
