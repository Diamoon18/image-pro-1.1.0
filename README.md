# image-pro-1.1.0
Update image pro 1.0 \
New functions:
1. Histogram equalization (pow, contrast)
2. Box filter (other)
3. Gauss filter (other)
## Screenshots
![image](https://user-images.githubusercontent.com/72127610/116815946-45220680-ab60-11eb-807d-6923403d037b.png)
![image](https://user-images.githubusercontent.com/72127610/116815951-4b17e780-ab60-11eb-8389-aeeb7306d2b4.png)
![image](https://user-images.githubusercontent.com/72127610/116815953-50753200-ab60-11eb-8fe1-5d829425d484.png)
![image](https://user-images.githubusercontent.com/72127610/116815961-579c4000-ab60-11eb-9496-43fd90182a9a.png)
## Code explanation
### New code in Model
New BoardEnum - Other
```java
public enum BoardEnum {
	MENU,
	LINEAR,
	POW,
	MIXING,
	OTHER
}

```
Board class - new buttons have been added for the Main, Pow and Other.
```java
....
if(e.equals(menu.menuButtons[3])) {
				e.color1 = color_change;
				e.s = "Inne";
				if (menuView.click) {
					state = BoardEnum.OTHER;
					menuView.click = false;
				}
}
... 
if(e.equals(pow.powButtons[4])) {
				e.color1 = color_change;
				if (powView.click_p) {
					if(!powModel.picturePathIsEmpty()) {
						powModel.wyrownanieHistogramu();
					}
					powView.click_p = false;
				}
			}
...

// Other
			if(e.equals(oth.otherButtons[0])) {
				e.color1 = color_change;
				if (otherView.click_o) {
					otherModel.openPicture();
					otherView.click_o = false;
				}
			}
			if(e.equals(oth.otherButtons[1])) {
				e.color1 = color_change;
				if (otherView.click_o) {
					if(!otherModel.picturePathIsEmpty()) {
						otherModel.boxFiltr();
					}
					otherView.click_o = false;
				}
			}
			if(e.equals(oth.otherButtons[2])) {
				e.color1 = color_change;
				if (otherView.click_o) {
					if(!otherModel.picturePathIsEmpty()) {
						otherModel.gaussFiltr();
					}
					otherView.click_o = false;
				}
			}
			if(e.equals(oth.otherButtons[3])) {
				e.color1 = color_change;
				if (otherView.click_o) {
					state = BoardEnum.MENU;
					otherView.click_o = false;
				}
			}
...
```
powModel - new method wyrownanieHistogramu()
1. Compute h - histogram for the image.
```java
		wykresRgb();
```
2. Calculation of the cumulative histogram from the histogram h, for RGB channels
```java
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
...	
```
```java
		plotRGB g = new plotRGB(redd, greenn, bluee);
		double [] n_red = g.getRed();
		double [] n_green = g.getGreen();
		double [] n_blue = g.getBlue();
```
3. Find a new intensity value for each point in the image
```java
		x = (int) ((255/(height*width))*n_red[red]);
		y = (int) ((255/(height*width))*n_green[green]);
		z = (int) ((255/(height*width))*n_blue[blue]);
```
4. Setting new RGB values and filling arrays with values to draw a histogram
```java
		Color newColor = new Color(x, y, z);
		image.setRGB(j,i,newColor.getRGB());
				 
		w_red[newColor.getRed()]++;
		w_green[newColor.getGreen()]++;
		w_blue[newColor.getBlue()]++;
		....
		plotRGB wRGB = new plotRGB(w_red, w_green, w_blue);
		wRGB.drawPlot();
```
otherModel class - Box filtr and Gauss filtr\
Class structure is similar to linearModel.
1.Box filter - blur with a mask 3x3
```java
		static int mask[][] = {{1,1,1}, {1,1,1}, {1,1,1}};
```
Main logic of the boxFilter() method.\
Image convolution filtering mechanism:\
The image brightness values multiplied by the masking factors will be summed up and divided by the sum of the masking factors.\
Checking that it isn't out of range RGB.\
Setting new RGB values and save image.
```java
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
```
gaussFiltr() method - blur with a mask, formed by the Gaussian distribution function in two-dimensional form.
