package com.example.androidstudio.asteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * der verfuegbare Screen wird auf eine Hoehe von 1000 gemappt. Die Breite ergibt sich dann
 *   entsprechend.
 *
 */

public class Screen extends View {

    Model model;

    // nur zum Test##:
    Paint paint = new Paint();

    public Screen(Context context) {

        super(context);
        setBackgroundColor(Color.BLACK);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);

     }

    public void setModel(Model m) {
        model = m;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        // Skalierung auf 1000 virtuelle Pixel Hoehe
        Matrix matCanvas = new Matrix();
        float yFac = (float)getHeight()/1000f;
        matCanvas.setScale(yFac, yFac);
        canvas.concat(matCanvas);

        model.spaceShip.draw(canvas);
        // model.bullet.draw(canvas);
        // model.bull1.draw(canvas);
        // model.bull2.draw(canvas);

        // ## zur Zeit gibts noch keine Asteroids-Bitmap deswegen
        //      erscheint auch hier ein SpaceShip
        model.asteroid.draw(canvas);

        for (Bullet bullet: model.arBullets)
        {
            bullet.draw(canvas);
        }

        // ##test
        canvas.drawLine(0, 0, model.screenWidth - 1, model.screenHeight - 1, paint);
    }
}
