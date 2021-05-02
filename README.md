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
