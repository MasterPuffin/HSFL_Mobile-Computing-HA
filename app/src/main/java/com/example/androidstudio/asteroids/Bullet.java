package com.example.androidstudio.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;


public class Bullet extends Moveable{

    private static final String TAG = "fhflBullet";
    private static Bitmap bitmap;

    private int timeToLiveTics;

    public Bullet(float xStart, float yStart, float direction, float speed, float timeToLiveS) {
        super(xStart - Bullet.bitmap.getWidth()/2, yStart - Bullet.bitmap.getHeight()/2, direction, speed, Bullet.bitmap);

        this.timeToLiveTics = (int) (timeToLiveS/Model.ticDurationS);
    }

    public static void setClassAttributes(Bitmap bitmap) {
        Bullet.bitmap = bitmap;
     }

    @Override
    public void move() {
        super.move();

        timeToLiveTics = timeToLiveTics - 1;

        if (timeToLiveTics == 0) {
            isAlive = false;
        }
    }

 //   @Override
//    public void draw (Canvas canvas){
//        canvas.drawBitmap(bitmap, x-centerX, y-centerY, null);
//    }

}
