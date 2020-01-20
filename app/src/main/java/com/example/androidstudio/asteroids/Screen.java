package com.example.androidstudio.asteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;


public class Screen extends View {

    Model model;
    public int score;
    // nur zum Test##:

    Paint paint = new Paint();
    Paint pin = new Paint();

    public Screen(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        pin.setStyle(Paint.Style.FILL);
        pin.setColor(Color.WHITE);
    }

    public void setModel(Model m) {
        model = m;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Matrix matCanvas = new Matrix();
        float yFac = (float) getHeight() / 1000f;
        matCanvas.setScale(yFac, yFac);
        canvas.concat(matCanvas);

        pin.setTextSize(100);
        canvas.drawText(Integer.toString(score), 100, 100, pin);
        model.raumschiff.draw(canvas);

        model.asteroid.draw(canvas);
        if (model.asteroid2 != null) {
            model.asteroid2.draw(canvas);
        }
        if (model.asteroid3 != null) {
            model.asteroid3.draw(canvas);
        }
        for (Bullet bullet : model.arBullets) {
            bullet.draw(canvas);
        }
        for (Asteroid asteroid : model.arAsteroids) {
            asteroid.draw(canvas);
        }
    }
}
