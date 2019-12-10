package com.example.androidstudio.asteroids;

/**
 * Kleine Demo fuer eine Spiele-Konzept.
 *  An MVC-Pattern angelehnt.
 *
 *  !!!!!! Die App ist im Entwurfssatdium und zeigt erstmal nur die
 *  prinzipielle Klassenstruktur und das Timing.   !!!!!!!
 *
 * Android-Techniken:
 *  - onShowWindow-Ersatz: onWindowFocusChanged()
 *
 * Bem.:
 *   die sinnvollen RotateDrawable koennen erst ab API-Level 21 eingesetzt werden.
 *   logische ScreenHeight ist auf 1000 festgelegt, log. Width ergibt sich daraus
 *
 * History:
 *  03.05.2016  tas     Start
 *  05.12.2016  tas     Kommentierung ergaenzt
 *  22.09.2017  tas     auf API25 umgestellt
 *
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;

public class Controller extends Activity {

    private static final String TAG = "hsflController";

    private Screen screen;

    public Model model;

    private CountDownTimer timer = new CountDownTimer(Long.MAX_VALUE, (int)(1000.0*Model.ticDurationS)) {
        public void onTick(long millisUntilFinished) {
            // Log.v("TAG", "timer.onTick()");

            model.deleteDead();

            // Beispiele fuer Bewegungen, dies wird spaeter von Sensoren
            //      gesteuert
            model.spaceShip.move();
            model.spaceShip.rotate(2);

            model.spaceShip.fire();

            // model.spaceShip.collision(model.arAsteroids);

            for (Bullet bullet: model.arBullets)
            {
                bullet.move();
            }

            model.asteroid.move();

            if( model.asteroid.collision(model.spaceShip) ) {
                Log.v(TAG, "collision() ------------------------------------------------------ ");
            }

            screen.invalidate();

//            model.bullet.move();
//            model.bull1.move();
//            model.bull2.move();
        }

        public void onFinish() {
            //Log.v(TAG, "timer.onFinish()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new Model();

        screen = new Screen( getApplicationContext() );
        screen.setModel(model);

        setContentView(screen);     // mal nicht aus den Ressourcen generiert
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {  // View ist aufgebaut
        if (model.screenWidth == 0) {   // also noch nichts initialisiert

            model.screenWidth = (int) ((Model.screenHeight/(float)screen.getHeight())*(float)screen.getWidth());
            Log.v(TAG, "timer.onTick(): phys. height: " + screen.getHeight() + "  phys. width: " + screen.getWidth());
            Log.v(TAG, "timer.onTick(): log. height: " + Model.screenHeight + "  log. width: " + model.screenWidth);

            // Initialisierung der Klassenattribute
            Moveable.setClassAttributes(Model.ticDurationS, model.screenWidth, Model.screenHeight);

            // Achtung: beim Laden wird u.U. die Bitmap umskaliert -> fixen ##

            // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship25_32);
            Bitmap bitmap = getBitmapFromVectorDrawable(this, R.drawable.spaceship_32_25dp);
            SpaceShip.setClassAttributes(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bullet5_5);
            Bullet.setClassAttributes(bitmap);
            //## Bitmap fuer Asteroid fehlt noch
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship25_32);  //##
            Asteroid.setClassAttributes(bitmap);

            // jetzt kann es losgehen
            model.init();
            timer.start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // aus stackoverflow
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
