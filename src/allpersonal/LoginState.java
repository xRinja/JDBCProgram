package allpersonal;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LoginState extends JPanel implements GUIState, ActionListener{

	// Private Variables
	private JButton loginButton;
	private EntryListener entryListener;
	private GUIState guiState;
	private Context context;
	private Toolbar toolbar;
	private TextPanel textPanel;
	private static final long serialVersionUID = 1L;
	
	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	@Override
	public void Action(Context context, String[] strings) {
		// Setting up buttons
		System.out.println("LoginState Class ON");
		FlowLayout flow = new FlowLayout(); // Flow Layout of Panel.
		setLayout(flow); // Sets layout of panel.
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		add(loginButton);
		validate();
		context.setState(this);
		this.context = context;
		
		// Setting up text fields
		//toolbar.login();
		//textPanel.
	}

	@Override
	public void Debug(String debug) {
		// TODO Auto-generated method stub
		//this.guiState.Debug(debug);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if(clicked == loginButton){
			if(guiState != null){
				guiState.Debug("Logged in");
				setGUIState(guiState);
				remove(loginButton);
				guiState.Action(this.context,null);
				setGUIState(guiState);
				}
			}
	}
	
	public String toString(){
		return "Start State";
	}

}
