package com.example.androidstudio.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.util.Log;


public class Moveable extends Drawable {


    private static float stTicDurationS;
    private static int stScreenWidth;
    private static int stScreenHeight;

    private Bitmap bitmap;
    protected float centerX, centerY;

    protected float x = 0f;
    protected float y = 0f;
    public float xSpeed = 0f;
    public float ySpeed = 0f;
    protected boolean isAlive = true;

    protected Paint paint = new Paint();

    public Moveable(float xStart, float yStart, float direction, float speed, Bitmap bitmap) {

        x = xStart;
        y = yStart;

        this.bitmap = bitmap;

        float pixelPerTimeTic = speed * stTicDurationS;

        xSpeed = (float) Math.cos((double) direction * Math.PI / 180f) * pixelPerTimeTic;
        ySpeed = (float) Math.sin((double) direction * Math.PI / 180f) * pixelPerTimeTic;

        centerX = bitmap.getWidth() / 2;
        centerY = bitmap.getHeight() / 2;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
    }

    public static void setClassAttributes(float ticDurationS, int screenWidth, int screenHeight) {
        stTicDurationS = ticDurationS;
        stScreenWidth = screenWidth;
        stScreenHeight = screenHeight;
    }

    public void move() {
        x = (x + xSpeed + stScreenWidth) % stScreenWidth;
        y = (y + ySpeed + stScreenHeight) % stScreenHeight;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean collision(Moveable moveable) {
        RectF rec, rec2;
        rec = moveable.getRect();
        rec2 = getRect();
        boolean intersec = rec.intersect(rec2);
        if (intersec) {
            return true;
        } else {
            return false;
        }
    }

    public RectF getRect() {
        RectF rec = new RectF(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
        return rec;
    }

    @Override
    public void setAlpha(int a) {
    }

    @Override
    public void setColorFilter(ColorFilter a) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
