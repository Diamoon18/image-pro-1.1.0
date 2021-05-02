package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


import view.imagesLoad;

public class plotRGB extends JFrame {
	
	private double [] red   = new double[256];
	private double [] green = new double[256];
	private double [] blue  = new double[256];
	
	public plotRGB(double[] red, double[] green, double[] blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	private double[] plotKomulacyjny(double [] rgb) {
		double [] tablica = new double[256];
	    tablica[0] = rgb[0];
	    for (int i = 1; i < rgb.length; i++) {
	    	tablica[i] = rgb[i] + tablica[i-1];
	    }
		return tablica;
	}
	
	public double[] getRed() {
		return plotKomulacyjny(red);
	}
	
	public double[] getGreen() {
		return plotKomulacyjny(green);
	}
	
	public double[] getBlue() {
		return plotKomulacyjny(blue);
	}
	
	public void drawPlot() {
		JPanel jcp = new JPanel(new BorderLayout());
	    setContentPane(jcp);
	    setTitle("RGB graph");
	    setIconImage(imagesLoad.icon.getImage());
	    jcp.add(new DrawingComponent(red, green, blue), BorderLayout.CENTER); 
	    setSize(1500, 1500);
	    setLocationRelativeTo(null);
	    setVisible(true);
	}
	
}

class DrawingComponent extends JPanel {
	
	private double [] red   = new double[256];
	private double [] green = new double[256];
	private double [] blue  = new double[256];
	
	public DrawingComponent(double[] red, double[] green, double[] blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
    @Override
    protected void paintComponent(Graphics gh) {       
    	
      Graphics2D drp = (Graphics2D)gh;
      drp.setColor(Color.black);
      drp.fillRect(0, 0, 1500, 1500);
      
      drp.setColor(Color.white);
      drp.drawLine(0, 0, 300, 0);
      drp.drawLine(0, 0, 0, 1500);
      for(int i = 0; i < red.length; i++) {
    	  drp.setColor(Color.red);
    	  drp.drawLine(i, 0, i, (int) red[i]);
      }
      
      drp.setColor(Color.white);
      drp.drawLine(320, 0, 620, 0);
      drp.drawLine(320, 0, 320, 1500);
      for(int i = 0; i < green.length; i++) {
    	  drp.setColor(Color.green);
    	  drp.drawLine(320+i, 0, 320+i, (int) green[i]);
      }
      
      drp.setColor(Color.white);
      drp.drawLine(630, 0, 930, 0);
      drp.drawLine(630, 0, 630, 1500);
      for(int i = 0; i < blue.length; i++) {
    	  drp.setColor(Color.blue);
    	  drp.drawLine(630+i, 0, 630+i, (int) blue[i]);
      }
    }
}