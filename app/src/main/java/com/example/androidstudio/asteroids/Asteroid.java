package com.example.androidstudio.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Asteroid extends Moveable {

    private static Bitmap bitmap;

    public Asteroid(float xStart, float yStart, float direction, float speed, Model model) {
        super(xStart, yStart, direction, speed, Asteroid.bitmap);
    }

    public static void setClassAttributes(Bitmap bitmap) {
        Asteroid.bitmap = bitmap;
    }
}
