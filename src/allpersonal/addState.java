package allpersonal;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class addState extends JPanel implements GUIState, ActionListener{

	private JButton saveButton;
	private JButton cancleButton;
	private JPanel funcButtons;
	private JPanel textField;
	private JFrame newFrame;
	JLabel columnName;
	JTextField [] columnTextField;
	private GUIState guiState;
	private Context context;
	
	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	@Override
	public void Action(Context context, Server server, String[] dataSetOne, String[] dataSetTwo) {
		System.out.println("Tool bar instantiated");
		cancleButton = new JButton("Cancle"); // Cancel Button.
		saveButton = new JButton("Save"); // Save Button.
		FlowLayout flow = new FlowLayout(); // Flow Layout of Panel.
		setLayout(flow); // Sets layout of panel.
		cancleButton.addActionListener(this); // Adds action listener to button.
		saveButton.addActionListener(this); // Adds action listener to button.
		funcButtons = new JPanel();
		funcButtons.setLayout(flow);
		funcButtons.add(cancleButton);
		funcButtons.add(saveButton);
		System.out.println("Add button");
		validate();
		context.setState(this);
		this.context = context;
		System.out.println(context.getState().toString());
		makeJTextFields(dataSetOne);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if(clicked == saveButton) {
			if(guiState != null){
				setGUIState(guiState);
				guiState.Action(this.context, null, null, null);;
				setGUIState(guiState);
			}
		} 
		if(clicked == cancleButton) {
			if(guiState != null){
				setGUIState(guiState);
			}
		} 
		
	}
	
	private void makeJTextFields(String [] metaDataColumnNames) {
		String[] columnNames = metaDataColumnNames;
		textField = new JPanel();
		textField.setLayout(new java.awt.GridBagLayout());
		columnTextField = new JTextField[columnNames.length];
		for(int i = 0; i < columnNames.length; i++){
			System.out.println(columnNames[i]);
			System.out.println(columnTextField.length);
		}
		//setLayout(new java.awt.GridBagLayout());
		GridBagConstraints brid = new GridBagConstraints();
		brid.insets = new Insets(10,5,35,10);
		int xcounter = 0;
		int ycounter = 0;
			for(int i = 0; i < columnNames.length; i++){
				if(xcounter < 4){
					columnName = new JLabel(columnNames[i]);
					columnName.setVerticalAlignment(JLabel.CENTER);
					brid.fill = GridBagConstraints.HORIZONTAL;
					brid.gridx = xcounter;
					brid.gridy = ycounter;
					textField.add(columnName, brid);
					xcounter++;
					columnTextField[i] = new JTextField(15);
					brid.fill = GridBagConstraints.HORIZONTAL;
					brid.gridx = xcounter;
					brid.gridy = ycounter;
					textField.add(columnTextField[i], brid);
					xcounter++;
					} else {
					xcounter = 0;
					ycounter++;
					columnName = new JLabel(columnNames[i]);
					columnName.setVerticalAlignment(JLabel.CENTER);
					brid.fill = GridBagConstraints.HORIZONTAL;
					brid.gridx = xcounter;
					brid.gridy = ycounter;
					textField.add(columnName, brid);
					xcounter++;
					columnTextField[i] = new JTextField(15);
					brid.fill = GridBagConstraints.HORIZONTAL;
					brid.gridx = xcounter;
					brid.gridy = ycounter;
					textField.add(columnTextField[i], brid);
					xcounter++;
				}
			}
			xcounter = 0;
			ycounter = 0;
	}
	
	
	public JPanel getFuncButtons() {
		return funcButtons;
	}
	
	public JPanel getTextField() {
		return textField;
	}
	
	public String toString(){
		return "Add State";
	}
}
