# PixMap
## Choosing a picture from the Gallery

1.) For choosing the image I have simply used Intent with ACTION_PICK. This allows user to select an image from the gallery. <br/>
2.) The image is then send to Image Editing activity. <br/>

## Image Editing Activity
1.) Getting the URI of the image and changing the image to Bitmap.<br/> 
2.) Bitmap library allows easy manipulation of the image. This will come handy in applying various editing features. <br/>
3.) Created an Image View to view the Image. <br/>

## Adding Effect on the Image: 
## FLIPpING THE IMAGE VERTICALLY. 
1.) Here we want to flip the image on the y-axis and keeping the x-axis as it is. Hence, using the Matrix library we can scale the image where y = -y and x = x. <br/>

## FLIPpING THE IMAGE HORIZONTALLY.
1.) Here we want to flip the image on the x-axis and keeping the y-axis as it is. Hence, using the Matrix library we can scale the image where y = y and x = -x. <br/>

## CHANGING THE OPACITY TO 50%
1.) Opacity of any image is nothing but the transparency level of the image which ranges from 0 to 255. Where 0 means completely transparent and 255 means the image is opaque. <br/>
2.) To set opacity to 50% take int opacity = 255/2. <br/>
3.) Change the bitmap to mutable in order edit the same bitmap. <br/>
4.) I have used Canvas library to draw or edit something on the image. To draw something on the image using Canvas, we need 4 basic components, a Bitmap to hold the pixels, a Canvas to write into the bitmap, a drawing primitive (here I have used Bitmap), and a paint (to adjust the colorus and styles for the drawing).<br/>

## ADDING TEXT 
1.) In order to add text on the centre of the image, first I have used the canvas library for the same above stated reasons.<br/> 
2.) Paint library allows formatting of any text. Here I have changed the size and the colour of the text. <br/>
3.) In order to place the text on the centre, I have taken the width and height of the canvas(Picture) and divided by 2.<br/> 
4.) But it is necessary to use Align.CENTER parameter when using setTextAlign(Paint.Align.CENTER) function to align the text right from the centre otherwise the x axis will start from the middle of the image and the image will be shifted to right. <br/>

## ADDING LOGO
1.) The method of adding logo is similar to the adding a text. I have stored the logo in the drawable and converted it into Bitmap. <br/>
