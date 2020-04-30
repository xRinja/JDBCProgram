package allpersonal;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class addState extends JPanel implements GUIState, ActionListener{

	private JButton saveButton;
	private JButton cancleButton;
	private GUIState guiState;
	private Context context;
	
	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	@Override
	public void Action(Context context, String[] strings) {
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
		context.setState(this);
		this.context = context;
		System.out.println(context.getState().toString());
		
	}

	@Override
	public void Debug(String debug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if(clicked == saveButton) {
			if(guiState != null){
				guiState.Debug("Saved entry");
				setGUIState(guiState);
				guiState.Action(this.context, null);;
				setGUIState(guiState);
			}
		} 
		if(clicked == cancleButton) {
			if(guiState != null){
				guiState.Debug("Saved entry");
				setGUIState(guiState);
			}
		} 
		
	}
	
	public String toString(){
		return "Add State";
	}
}