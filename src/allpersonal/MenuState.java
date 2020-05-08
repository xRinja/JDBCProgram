package allpersonal;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuState extends JPanel implements GUIState, ActionListener{

	private JButton[] selectionButton;
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
		String[] tempString = new String[1];
		tempString[0] = clicked.getText();
		for(int i = 0; i < selectionButton.length; i++){
			if(clicked == selectionButton[i]) {
				if(guiState != null){
					setGUIState(guiState);
					guiState.Action(context, null, tempString, null);
					setGUIState(guiState);
				}
			}
		}
		
	}

	@Override
	public void Action(Context context, Server server, String[] dataSetOne, String[] dataSetTwo) {
		SetupButtons(dataSetOne);
		
		context.setState(this);
		this.context = context;
		System.out.println(context.getState().toString());
		
	}
	
	private void SetupButtons(String[] dataSetOne) {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new java.awt.GridBagLayout());
		System.out.println(dataSetOne.length);
		selectionButton = new JButton[dataSetOne.length];
		int xcounter = 0;
		int ycounter = 0;
		
		setLayout(new java.awt.GridBagLayout());
		GridBagConstraints brid = new GridBagConstraints();
		brid.insets = new Insets(10,5,35,10);
		
		for(int i = 0; i < dataSetOne.length; i++){
			if(xcounter < 2){
				System.out.println("Made button out of: " + dataSetOne[i]);
				selectionButton[i] = new JButton(dataSetOne[i]);
				brid.fill = GridBagConstraints.HORIZONTAL;
				brid.gridx = xcounter;
				brid.gridy = ycounter;
				selectionButton[i].addActionListener(this);
				buttonPanel.add(selectionButton[i], brid);
				xcounter++;
			} else {
				xcounter = 0;
				ycounter++;
				selectionButton[i] = new JButton(dataSetOne[i]);
				brid.fill = GridBagConstraints.HORIZONTAL;
				brid.gridx = xcounter;
				brid.gridy = ycounter;
				selectionButton[i].addActionListener(this);
				buttonPanel.add(selectionButton[i], brid);
				xcounter++;
			}
		}
	}
	
	public JPanel getButton() {
		return buttonPanel;
	}
	
	public String toString(){
		return "Menu State";
	}

}
