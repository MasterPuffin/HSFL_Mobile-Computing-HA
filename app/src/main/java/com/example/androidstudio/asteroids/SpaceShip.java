package com.example.androidstudio.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;



public class SpaceShip extends Moveable{

    private static final String TAG = "hsflSpaceShip";

    private static Bitmap bitmap;

    private Model model;

    private float direction;  // in degree

    public SpaceShip(float xStart, float yStart, Model model) {
        super(xStart, yStart, 0f, 0f, SpaceShip.bitmap);

        this.model = model;
    }

    public static void setClassAttributes(Bitmap bitmap) {

        SpaceShip.bitmap = bitmap;

        Log.i(TAG, "setClassAttributes(): " + bitmap.getWidth() + "  " + bitmap.getHeight() );
    }

    public void rotate(float diffAngle) {
        direction = (direction + diffAngle + 360f) % 360;
    }

    public void fire(){
        Bullet bullet = new Bullet(x + centerX, y + centerY, direction, 200, 3);
        model.add(bullet);
    }

    // ## deprecated
//        bmRotatedSpaceShip.eraseColor(Color.TRANSPARENT);
//
//        Canvas tempCanvas = new Canvas(bmRotatedSpaceShip);
//        tempCanvas.rotate(direction, bmSpaceShip.getWidth()/2, bmSpaceShip.getHeight()/2);
//        tempCanvas.drawBitmap(bmSpaceShip, 0, 0, null);
//    }

    public void move() {
        super.move();
    }

    @Override
    public void draw (Canvas canvas){

            // Drehung via Rotationsmatrix
            Matrix mat = new Matrix();
            mat.postRotate(direction, centerX, centerY);
            mat.postTranslate(x, y);
            canvas.drawBitmap(bitmap, mat, null);
    }
}
