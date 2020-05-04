package allpersonal;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableSelectState extends JFrame implements GUIState, ActionListener{

	private JButton[] selectionButton;
	private GUIState guiState;
	private Context context;
	private JList<String> jList;
	private JTable jTable;
	private JPanel tableList;
	private JPanel tableFill;
	private JScrollPane jScrollPane;
	private static final long serialVersionUID = 1L;

	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	@Override
	public void Action(Context context, String[] dataSetOne, String[] dataSetTwo) {
		//JTable table = new JTable();
		MakeListSelect(dataSetOne);
		
		
		// Context
		context.setState(this);
		this.context = context;
		System.out.println(context.getState().toString());
	}
	
	private void MakeListSelect(String[] tables) {
		jList = new JList<String>(tables);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//jList.setLayoutOrientation(JList.VERTICAL_WRAP);
		//jList.setSize(250,250);
		jList.setFixedCellHeight(25);
		jList.setFixedCellWidth(75);
		jList.setVisibleRowCount(100);
		jList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				for(int i = 0; i < tables.length; i++) {
					if(jList.getSelectedValue().equalsIgnoreCase(tables[i])) {
						System.out.println(tables[i]);
					}
				}
				
			}
			
		});
		tableList = new JPanel();
		jScrollPane = new JScrollPane(jList);
		for(int i = 0; i < tables.length; i++) {
			System.out.println("Tables are: " + tables[i]);
		}
		tableList.add(jScrollPane);
	}
	
	private void MakeTableArea(String[] dataSetOne, String[] dataSetTwo) {
		jTable = new JTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		System.out.println("Games button pressed");
		for(int i = 0; i < selectionButton.length; i++){
			if(clicked == selectionButton[i]) {
				System.out.println("Yelp");
				if(guiState != null){
					setGUIState(guiState);
					guiState.Action(this.context, null, null);
					setGUIState(guiState);
				}
			}
		}
		
	}
	
	public JPanel getTableList() {
		return tableList;
	}
	
	public String toString(){
		return "Table State";
	}

}
