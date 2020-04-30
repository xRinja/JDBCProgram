package allpersonal;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI{

	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI(){
		System.out.println("GUI INITALIZED");
		MainFrame mainFrame = new MainFrame(); // New instance of frame.
	}
}
