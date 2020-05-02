package allpersonal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DatabaseMetaData;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TextPanel extends JPanel implements ActionListener{
	JLabel columnName;
	JTextField [] columnTextField;
	JComboBox<String> serverList;
	
	public TextPanel(String[] jFieldData, String[] dataBaseTypes) {
		LabelMaker(jFieldData, dataBaseTypes);
	}
	private void LabelMaker(String[] metaDataColumnNames, String[] databaseTypes){
		String[] columnNames = metaDataColumnNames;
		columnTextField = new JTextField[columnNames.length];
		for(int i = 0; i < columnNames.length; i++){
			System.out.println(columnNames[i]);
			System.out.println(columnTextField.length);
		}
		setLayout(new java.awt.GridBagLayout());
		GridBagConstraints brid = new GridBagConstraints();
		brid.insets = new Insets(10,5,35,10);
		int xcounter = 0;
		int ycounter = 0;
			for(int i = 0; i < columnNames.length; i++){
				if(xcounter < 4){
					if(columnNames[i].equals("Driver")){
						columnName = new JLabel(columnNames[i]);
						columnName.setVerticalAlignment(JLabel.CENTER);
						brid.fill = GridBagConstraints.HORIZONTAL;
						brid.gridx = xcounter;
						brid.gridy = ycounter;
						add(columnName, brid);
						xcounter++;
						columnTextField[i] = new JTextField(15);
						brid.fill = GridBagConstraints.HORIZONTAL;
						brid.gridx = xcounter;
						brid.gridy = ycounter;
						serverList = new JComboBox<>(databaseTypes);
						serverList.addActionListener(this);
						brid.fill = GridBagConstraints.HORIZONTAL;
						brid.gridx = xcounter;
						brid.gridy = ycounter;
						xcounter++;
						add(serverList, brid);
					} else {
					columnName = new JLabel(columnNames[i]);
					columnName.setVerticalAlignment(JLabel.CENTER);
					brid.fill = GridBagConstraints.HORIZONTAL;
					brid.gridx = xcounter;
					brid.gridy = ycounter;
					add(columnName, brid);
					xcounter++;
					columnTextField[i] = new JTextField(15);
					brid.fill = GridBagConstraints.HORIZONTAL;
					brid.gridx = xcounter;
					brid.gridy = ycounter;
					add(columnTextField[i], brid);
					xcounter++;
					}
				} else {
					xcounter = 0;
					ycounter++;
					columnName = new JLabel(columnNames[i]);
					columnName.setVerticalAlignment(JLabel.CENTER);
					brid.fill = GridBagConstraints.HORIZONTAL;
					brid.gridx = xcounter;
					brid.gridy = ycounter;
					add(columnName, brid);
					xcounter++;
					columnTextField[i] = new JTextField(15);
					brid.fill = GridBagConstraints.HORIZONTAL;
					brid.gridx = xcounter;
					brid.gridy = ycounter;
					add(columnTextField[i], brid);
					xcounter++;
				}
			}
			xcounter = 0;
			ycounter = 0;
			validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> cb = (JComboBox<String>)e.getSource();
		if(cb.getSelectedItem().equals("SQL")){
			columnTextField[0].setText("jdbc:mysql://");
		}
		if(cb.getSelectedItem().equals("ORACLE")){
			columnTextField[0].setText("jdbc:oracle:thin:");
		}
		
	}
}

