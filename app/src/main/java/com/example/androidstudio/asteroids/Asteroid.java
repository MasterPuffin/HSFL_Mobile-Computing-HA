package com.example.androidstudio.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Asteroid extends Moveable{

    private static Bitmap bitmap;

    public Asteroid(float xStart, float yStart, float direction, float speed, Model model) {
        super(xStart, yStart, direction, speed, Asteroid.bitmap);
    }

    public static void setClassAttributes(Bitmap bitmap) {
        Asteroid.bitmap = bitmap;
    }


//    public void move() {
//        super.move();
//    }

//    @Override
//    public void draw (Canvas canvas){
//        if ( isAlive ) {
//            canvas.drawRect(x, y, x + smallWidth * size, y + smallWidth * size, paint);
//        }
//    }
//
//    Bitmap bmpOriginal = BitmapFactory.decodeResource(this.getResources(), R.drawable.image2);
//    Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
//    Canvas tempCanvas = new Canvas(bmResult);
//    tempCanvas.rotate(90, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
//    tempCanvas.drawBitmap(bmpOriginal, 0, 0, null);


}
