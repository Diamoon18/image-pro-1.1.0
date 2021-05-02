package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class otherModel {
	 private static String picturePath ="";
	 static BufferedImage image;
	 static int width;
	 static int height;
	 static JFrame f;
	 static int mask[][] = {{1,1,1}, {1,1,1}, {1,1,1}};
	 static double maskGauss[][];
	 int odczylenieGauss;
	 	
	public static void openPicture() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Select an image");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG images", "jpg");
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
        	picturePath = jfc.getSelectedFile().getPath();
            System.out.println(jfc.getSelectedFile().getPath());
        }
	}
	
	public static boolean picturePathIsEmpty() {
		return picturePath.isEmpty();
	}
	
	private static double[][] wypelnGauss(int odchylenie) {
		double[][] tablica = new double[3][3];
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				tablica[x][y] = Math.exp((-(x*x+y*y))/(2*odchylenie*odchylenie))/(2*Math.PI*Math.PI);
			}
		}
		return tablica;
	}
	
	public static void boxFiltr() {
		try {
			 if(!picturePath.isEmpty()) {

				 File input = new File(picturePath);
				 image = ImageIO.read(input);
				 width = image.getWidth();
				 height = image.getHeight();
				 
				 for(int i = 1; i < height-1; i++){
					 for(int j = 1; j < width-1; j++){
						 
						 int x, y, z;
						 x = 0;
						 y = 0;
						 z = 0;
						 
						 for(int k=-1; k<=1; k++) {
							 for(int l=-1; l<=1; l++) {
								 Color c = new Color(image.getRGB(j+k, i+l));
								 int red = (int)(c.getRed());
								 int green = (int)(c.getGreen());
								 int blue = (int)(c.getBlue());

						         x += red * mask[k+1][l+1];
						         y += green * mask[k+1][l+1];
						         z += blue * mask[k+1][l+1];

							 }
						 }
							 
						 x = x/9;
						 y = y/9;
						 z = z/9;
						 
						 if (x>255) {
							 x=255;
						 }else if (x < 0){
							 x=0;
						 }
						 if (y>255) {
							 y=255;
						 }else if (y < 0){
							 y=0;
						 }
						 if (z>255) {
							 z=255;
						 }else if (z < 0){
							 z=0;
						 }
						
						 Color newColor = new Color(x, y, z);
						 image.setRGB(j,i,newColor.getRGB());
					 }
				 }
				 File ouptut = new File(picturePath.replace(".jpg", "_box_filtr.jpg"));
				 ImageIO.write(image, "jpg", ouptut);
				 f = new JFrame();
				 JOptionPane.showMessageDialog(f, "Success!");
			 }
		} catch (Exception e) {}
	}
	
	public static void gaussFiltr() {
		try {
			 if(!picturePath.isEmpty()) {

				 File input = new File(picturePath);
				 image = ImageIO.read(input);
				 width = image.getWidth();
				 height = image.getHeight();
				 
				 maskGauss = wypelnGauss(1);
				 
				 for(int i = 1; i < height-1; i++){
					 for(int j = 1; j < width-1; j++){
						 
						 double x, y, z;
						 x = 0;
						 y = 0;
						 z = 0;
						 
						 for(int k=-1; k<=1; k++) {
							 for(int l=-1; l<=1; l++) {
								 Color c = new Color(image.getRGB(j+k, i+l));
								 int red = (int)(c.getRed());
								 int green = (int)(c.getGreen());
								 int blue = (int)(c.getBlue());

						         x += red * maskGauss[k+1][l+1];
						         y += green * maskGauss[k+1][l+1];
						         z += blue * maskGauss[k+1][l+1];

							 }
						 }
						 
						 System.out.println(x + " " + y + " " + z);
						 x = x;
						 y = y;
						 z = z;
						 
						 if (x>255) {
							 x=255;
						 }else if (x < 0){
							 x=0;
						 }
						 if (y>255) {
							 y=255;
						 }else if (y < 0){
							 y=0;
						 }
						 if (z>255) {
							 z=255;
						 }else if (z < 0){
							 z=0;
						 }
						
						 Color newColor = new Color((int)x, (int)y, (int)z);
						 image.setRGB(j,i,newColor.getRGB());
					 }
				 }
				 File ouptut = new File(picturePath.replace(".jpg", "_gauss_filter19.jpg"));
				 ImageIO.write(image, "jpg", ouptut);
				 f = new JFrame();
				 JOptionPane.showMessageDialog(f, "Success!");
			 }
		} catch (Exception e) {}
		
	}
}
