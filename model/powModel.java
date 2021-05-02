package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class powModel {
	 private static String picturePath ="";
	 static BufferedImage image;
	 static double width;
	 static double height;
	 static JFrame f;
	 
	 static double [] redd = new double[256];
	 static double [] greenn = new double[256];
	 static double [] bluee = new double[256];
	 	
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
	
	public static void potega(double valueOfB) {
		try {
			 if(!picturePath.isEmpty()) {
				 File input = new File(picturePath);
				 image = ImageIO.read(input);
				 width = image.getWidth();
				 height = image.getHeight();
				 
				 
				 for(int i = 0; i < height; i++){
					 for(int j = 0; j < width; j++){
						 Color c = new Color(image.getRGB(j, i));
						 double red = (int)(c.getRed());
						 double green = (int)(c.getGreen());
						 double blue = (int)(c.getBlue());
						 
						 double potega = valueOfB;
						 double x, y, z;
						 x = 0;
						 y = 0;
						 z = 0;
						 
						 x = (255 * Math.pow(red/255, potega));
						 y = (255 * Math.pow(green/255, potega));
						 z = (255 * Math.pow(blue/255, potega));
						
						
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
				 File ouptut = new File(picturePath.replace(".jpg", "_potega.jpg"));
				 ImageIO.write(image, "jpg", ouptut);
			 }
		} catch (Exception e) {}
		f = new JFrame();
		JOptionPane.showMessageDialog(f, "Success!");
	}
	
	public static void contrast(double valueOfCon) {
		
		 File input = new File(picturePath);
		 try {
			image = ImageIO.read(input);
		 }catch (IOException e1) {
			e1.printStackTrace();
		 }
		 width = image.getWidth();
		 height = image.getHeight();

		 for(int i = 0; i < height; i++){
			 for(int j = 0; j < width; j++){
				 Color c = new Color(image.getRGB(j, i));
				 double red = (int)(c.getRed());
				 double green = (int)(c.getGreen());
				 double blue = (int)(c.getBlue());
	
				 double delta_c = valueOfCon;
				 double x, y, z;
				 x = 0;
				 y = 0;
				 z = 0;
				 
				 if(delta_c <= 127 && delta_c >= 0) {
					 x = (127/(127-delta_c))*(red-delta_c);
					 y = (127/(127-delta_c))*(green-delta_c);
					 z = (127/(127-delta_c))*(blue-delta_c);
				 }
				 
				 if(delta_c >= -128 && delta_c < 0) {
					 x = ((127+delta_c)/127)*red-delta_c;
					 y = ((127+delta_c)/127)*green-delta_c;
					 z = ((127+delta_c)/127)*blue-delta_c;
				 }
				 
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
		 File ouptut = new File(picturePath.replace(".jpg", "_contrast.jpg"));
		 try {
			ImageIO.write(image, "jpg", ouptut);
		 }catch (IOException e) {
			e.printStackTrace();
		 }
		 f = new JFrame();
		 JOptionPane.showMessageDialog(f, "Success!");
	 }
	
	private static void wykresRgb() {
		 File input = new File(picturePath);
		 try {
			image = ImageIO.read(input);
		 }catch (IOException e1) {
			e1.printStackTrace();
		 }
		 width = image.getWidth();
		 height = image.getHeight();

		 for(int i = 0; i < height; i++){
			 for(int j = 0; j < width; j++){
				 Color c = new Color(image.getRGB(j, i));
				 int red = (int)(c.getRed());
				 int green = (int)(c.getGreen());
				 int blue = (int)(c.getBlue());
	
				 redd[red]++;
				 greenn[green]++;
				 bluee[blue]++;
			 }
		 }
	}
	
	public static void rgbPlot() {
		wykresRgb();
		plotRGB pR = new plotRGB(redd, greenn, bluee);
		pR.drawPlot();
	}
	
	public static void wyrownanieHistogramu() {
		wykresRgb();
		plotRGB g = new plotRGB(redd, greenn, bluee);
		
		double [] n_red = g.getRed();
		double [] n_green = g.getGreen();
		double [] n_blue = g.getBlue();
		
		double [] w_red = new double[256];
		double [] w_green = new double[256];
		double [] w_blue = new double[256];
		
		for(int i = 0; i < height; i++){
			 for(int j = 0; j < width; j++){
				 Color c1 = new Color(image.getRGB(j, i));
				 int red = (int)(c1.getRed());
				 int green = (int)(c1.getGreen());
				 int blue = (int)(c1.getBlue());
				 
				 int x, y, z;
				 x = 0;
				 y = 0;
				 z = 0;
				 
				 x = (int) ((255/(height*width))*n_red[red]);
				 y = (int) ((255/(height*width))*n_green[green]);
				 z = (int) ((255/(height*width))*n_blue[blue]);
				 
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
				 
				 w_red[newColor.getRed()]++;
				 w_green[newColor.getGreen()]++;
				 w_blue[newColor.getBlue()]++;
			 }
		 }
		 File ouptut = new File(picturePath.replace(".jpg", "_wyrownanieHistogramu.jpg"));
		 try {
			ImageIO.write(image, "jpg", ouptut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 plotRGB wRGB = new plotRGB(w_red, w_green, w_blue);
		 wRGB.drawPlot();
	}
	
	public static boolean picturePathIsEmpty() {
		return picturePath.isEmpty();
	}
	
	public static String getPicturePath() {
		return picturePath;
	}

}
