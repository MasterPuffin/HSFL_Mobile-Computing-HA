package com.example.androidstudio.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;


public class SpaceShip extends Moveable {

    private static Bitmap bitmap;
    private Model model;
    private float direction;

    public SpaceShip(float xStart, float yStart, Model model) {
        super(xStart, yStart, 0f, 0f);
        super.init(SpaceShip.bitmap);
        this.model = model;
    }

    public void init(Model model) {
        super.init(SpaceShip.bitmap);
        this.model = model;
    }

    public static void setClassAttributes(Bitmap bitmap) {
        SpaceShip.bitmap = bitmap;
    }

    public void rotate(float diffAngle) {
        direction = (direction + diffAngle + 360f) % 360;
    }

    public void fire() {
        Bullet bullet = new Bullet(x + centerX, y + centerY, direction, 800, 1f);
        model.add(bullet);
    }

    public void move(float bewstarke) {
        super.move();
        xSpeed = (float) Math.cos((double) direction * Math.PI / 180f) * bewstarke;
        ySpeed = (float) Math.sin((double) direction * Math.PI / 180f) * bewstarke;
    }

    @Override
    public void draw(Canvas canvas) {
        //Drehung via Rotationsmatrix
        Matrix mat = new Matrix();
        mat.postRotate(direction, centerX, centerY);
        mat.postTranslate(x, y);
        canvas.drawBitmap(bitmap, mat, null);
    }
}
