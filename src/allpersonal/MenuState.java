package allpersonal;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuState extends JFrame implements GUIState, ActionListener{

	private JButton[] selectionButton;
	private EntryListener entryListener;
	private GUIState guiState;
	private Context context;
	private JPanel buttonPanel;
	private static final long serialVersionUID = 1L;
	
	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		for(int i = 0; i < selectionButton.length; i++){
			if(clicked == selectionButton[i]) {
				if(guiState != null){
					setGUIState(guiState);
					guiState.Action(context, null);
					setGUIState(guiState);
				}
			}
		}
		
	}

	@Override
	public void Action(Context context, Server server) {
		server.setMetaData();
		SetupButtons(server);
		SetupTextFields(server);
		
		context.setState(this);
		this.context = context;
		System.out.println(context.getState().toString());
		
	}
	
	private void SetupButtons(Server server) {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new java.awt.GridBagLayout());
		System.out.println(server.schemaNames.length);
		selectionButton = new JButton[server.schemaNames.length];
		int xcounter = 0;
		int ycounter = 0;
		
		setLayout(new java.awt.GridBagLayout());
		GridBagConstraints brid = new GridBagConstraints();
		brid.insets = new Insets(10,5,35,10);
		
		for(int i = 0; i < server.schemaNames.length; i++){
			if(xcounter < 2){
				System.out.println("Made button out of: " + server.schemaNames[i]);
				selectionButton[i] = new JButton(server.schemaNames[i]);
				brid.fill = GridBagConstraints.HORIZONTAL;
				brid.gridx = xcounter;
				brid.gridy = ycounter;
				selectionButton[i].addActionListener(this);
				buttonPanel.add(selectionButton[i], brid);
				xcounter++;
			} else {
				xcounter = 0;
				ycounter++;
				selectionButton[i] = new JButton(server.schemaNames[i]);
				brid.fill = GridBagConstraints.HORIZONTAL;
				brid.gridx = xcounter;
				brid.gridy = ycounter;
				selectionButton[i].addActionListener(this);
				buttonPanel.add(selectionButton[i], brid);
				xcounter++;
			}
		}
	}
	
	private void SetupTextFields(Server server){

	}
	
	public JPanel getButton() {
		return buttonPanel;
	}
	
	public String toString(){
		return "Menu State";
	}

}
