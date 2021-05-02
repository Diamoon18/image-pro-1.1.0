package model;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class questionFormPowModel{
	
	private static double valueOfPow;
	private static double valueOfCon;
	static JFrame f;
	
	public static void frame() {
		f = new JFrame();
		String input = "1";
		input = JOptionPane.showInputDialog( "Value of pow [0,10]:");
		if(input == null || input.isEmpty() || !input.matches("([0-9]*[.])?[0-9]+")) {
			input = "1";
			JOptionPane.showMessageDialog(f,"Error input, default input 1");
		}
		valueOfPow = Double.parseDouble(input);
		if(valueOfPow < 0 || valueOfPow > 10) {
			valueOfPow = 1;
			JOptionPane.showMessageDialog(f,"Error input, default input 1");
		}
		powModel.potega(valueOfPow);
	}
	
	public static void frame_op() {
		f = new JFrame();
		String input = "1";
		input = JOptionPane.showInputDialog( "Value of pow [0,1]:");
		if(input == null || input.isEmpty() || !input.matches("([0-9]*[.])?[0-9]+")) {
			input = "1";
			JOptionPane.showMessageDialog(f,"Error input, default input 1");
		}
		valueOfPow = Double.parseDouble(input);
		if(valueOfPow < 0 || valueOfPow > 1) {
			valueOfPow = 1;
			JOptionPane.showMessageDialog(f,"Error input, default input 1");
		}
	}
	
	public static void frame_contrast() {
		f = new JFrame();
		String input = "50";
		input = JOptionPane.showInputDialog( "Value of c [-128,127]:");
		if(input == null || input.isEmpty() || !input.matches("^[+-]?([0-9]*[.])?[0-9]+")) {
			input = "50";
			JOptionPane.showMessageDialog(f,"Error input, default input 50");
		}
		valueOfCon = Double.parseDouble(input);
		if(valueOfCon < -128 || valueOfCon > 127) {
			valueOfCon = 50;
			JOptionPane.showMessageDialog(f,"Error input, default input 50");
		}
		powModel.contrast(valueOfCon);
	}
	
	public static double getValueOfPow() {
		return valueOfPow;
	}
}
