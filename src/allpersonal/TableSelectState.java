package allpersonal;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TableSelectState extends JPanel implements GUIState, ActionListener{

	private JButton[] selectionButton;
	private EntryListener entryListener;
	private GUIState guiState;
	private Context context;
	private static final long serialVersionUID = 1L;

	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	@Override
	public void Action(Context context, String[] menu) {
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
		System.out.println("Games button pressed");
		for(int i = 0; i < selectionButton.length; i++){
			if(clicked == selectionButton[i]) {
				System.out.println("Yelp");
				if(guiState != null){
					guiState.Debug("Clicked on: " + selectionButton[i].getText());
					setGUIState(guiState);
					guiState.Action(this.context, null);
					setGUIState(guiState);
				}
			}
		}
		
	}
	
	public String toString(){
		return "Table State";
	}

}
