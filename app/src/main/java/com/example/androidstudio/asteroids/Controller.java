package com.example.androidstudio.asteroids;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class Controller extends Activity {

    private static final String TAG = "hsflController";


    static MediaPlayer spaceshipExplosion;
    static MediaPlayer asteroidExplosion;



    Context context =this;

    //Gyro Stuff
    //Kalibiert den Gyrosensor bei Appstart so dass die momentane Ausrochtung 0 beträgt
    float gyroNull = 0;
    //Rotationsparameter der an die Model Klasse übergeben wird
    float rotationAngle = 1f;
    //Legt die Rotationsgeschwindigkeit fest
    final static int rotationSensitivity = 20;
    public boolean gedrueckt;
    public float bewstaerke;

    private Screen screen;
//    private double neuerAsteroid =0;
    public Model model;

    private CountDownTimer timer = new CountDownTimer(Long.MAX_VALUE, (int)(1000.0*Model.ticDurationS)) {

        public void onTick(long millisUntilFinished) {
            model.deleteDead();

            for (Bullet bullet: model.arBullets)
            {
                bullet.move();
                // Bullet trifft einen Asteroid
                if(bullet.collision(model.asteroid)){
                    Log.v(TAG, "Bullet collides with Asteroid");
                     asteroidExplosion = MediaPlayer.create(context, R.raw.explosion_asteroid);
                    asteroidExplosion.start();
                    screen.score++;
                    model.spawnAsteroid(screen.score);

                    //Bullet remove?
                }
                if (model.asteroid2!=null){
                if(bullet.collision(model.asteroid2)){
                Log.v(TAG, "Bullet collides with Asteroid");
                asteroidExplosion = MediaPlayer.create(context, R.raw.explosion_asteroid);
                asteroidExplosion.start();
                screen.score++;
                model.spawnAsteroid2(screen.score);}
                //Bullet remove?
            }
                if (model.asteroid3!=null){
                    if(bullet.collision(model.asteroid3)){
                        Log.v(TAG, "Bullet collides with Asteroid");
                        asteroidExplosion = MediaPlayer.create(context, R.raw.explosion_asteroid);
                        asteroidExplosion.start();
                        screen.score++;
                        model.spawnAsteroid3(screen.score);}
                    //Bullet remove?
                }
            }
            for (Asteroid ast: model.arAsteroids)
            {
                    ast.move();
            }
            if(model.raumschiff.collision(model.asteroid)) {
                Log.v(TAG, "Asteroid collides with Spaceship");
                spaceshipExplosion = MediaPlayer.create(context, R.raw.explosion);
                spaceshipExplosion.start();
                model.asteroid2 = null;
                model.asteroid3 = null;
                model.init();
                screen.score = 0;
            }



            /*neuerAsteroid=neuerAsteroid+0.01;
            if(neuerAsteroid>=5){
                model.newAsteroid();
                neuerAsteroid=0;
            }
            System.out.println(neuerAsteroid);*/
            if(gedrueckt){
                if(bewstaerke<20)
                    bewstaerke =bewstaerke+0.5f;}
            if (bewstaerke>0){
                bewstaerke = bewstaerke-0.15f;
            if(bewstaerke<0){
                bewstaerke=0;
            }
            }
            if (model.asteroid2!=null){
                model.asteroid2.move();
                if(model.raumschiff.collision(model.asteroid2)) {
                    Log.v(TAG, "Asteroid collides with Spaceship");
                    spaceshipExplosion = MediaPlayer.create(context, R.raw.explosion);
                    spaceshipExplosion.start();
                    model.asteroid2 = null;
                    model.asteroid3 = null;
                    model.init();
                    screen.score = 0;
                }
            }
            if (model.asteroid3!=null){
                model.asteroid3.move();
                if(model.raumschiff.collision(model.asteroid3)) {
                    Log.v(TAG, "Asteroid collides with Spaceship");
                    spaceshipExplosion = MediaPlayer.create(context, R.raw.explosion);
                    spaceshipExplosion.start();
                    model.asteroid2 = null;
                    model.asteroid3 = null;
                    model.init();
                    screen.score = 0;
                }
            }
            model.raumschiff.move(bewstaerke);
            model.asteroid.move();
            model.raumschiff.rotate(rotationAngle);




            screen.invalidate();

        }

        public void onFinish() {

        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MediaPlayer laserShot = MediaPlayer.create(this, R.raw.lasershot);
        final MediaPlayer rocketSound = MediaPlayer.create(this, R.raw.rocketsound);
        final MediaPlayer backgroundMusic = MediaPlayer.create(this, R.raw.background);

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f, 0.3f);
        backgroundMusic.start();

        model = new Model();

        screen = new Screen( getApplicationContext() );
        screen.setModel(model);

        setContentView(screen);     // mal nicht aus den Ressourcen generiert

        //OnTouchListener, für schiessen bzw beschleunigen
        screen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(TAG, "onTouch()");
                Log.d(TAG,"Height:" + view.getHeight() + "Width:" + view.getWidth());
                Log.d(TAG,"X-Position" + motionEvent.getX() + "Y-Position" + motionEvent.getY());
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(motionEvent.getX() < (view.getWidth()/2)) {
                        gedrueckt = true;
                        rocketSound.start();
                        rocketSound.setVolume(0.25f, 0.25f);
                        rocketSound.setLooping(true);
                        SpaceShip.setClassAttributes(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship_flame_32_25));


                    } else {

                        model.raumschiff.fire();
                        laserShot.start();
                    }
                    return true;
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    Log.d(TAG, "onTouch: UPUPUP");
                    gedrueckt = false;
                    rocketSound.pause();
                    SpaceShip.setClassAttributes(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship_32_25));
                    return true;
                }
                return false;
            }
        });
        //Gyro Sensor
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        // Create a listener
        SensorEventListener rvListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);

                // Remap coordinate system
                float[] remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_X,
                        SensorManager.AXIS_Z,
                        remappedRotationMatrix);

                // Convert to orientations
                float[] orientations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix, orientations);

                for(int i = 0; i < 3; i++) {
                    orientations[i] = (float)(Math.toDegrees(orientations[i]));
                }

                //Rotationsparameter entspricht der Z Achse
                float rotation = orientations[2];

                //Setzt den Kalibierungswert beim ersten Aufruf
                if (gyroNull == 0) {
                    gyroNull = rotation;
                }
                //Setzt den Rotierungsparameter --> Multiplizier mit -1 damit die Drehung richtig rum erfolgt
                rotationAngle = ((gyroNull - rotation)/rotationSensitivity)*(-1);

                Log.d("dbg",Float.toString(rotation));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        // Register it
        sensorManager.registerListener(rvListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {  // View ist aufgebaut
        if (model.width == 0) {   // also noch nichts initialisiert

            model.width = (int) ((Model.height /(float)screen.getHeight())*(float)screen.getWidth());
            Log.v(TAG, "timer.onTick(): phys. height: " + screen.getHeight() + "  phys. width: " + screen.getWidth());
            Log.v(TAG, "timer.onTick(): log. height: " + Model.height + "  log. width: " + model.width);

            // Initialisierung der Klassenattribute
            Moveable.setClassAttributes(Model.ticDurationS, model.width, Model.height);

            // Achtung: beim Laden wird u.U. die Bitmap umskaliert -> fixen ##

            SpaceShip.setClassAttributes(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship_32_25));

            Bullet.setClassAttributes(BitmapFactory.decodeResource(getResources(), R.drawable.bullet5_5));

            Asteroid.setClassAttributes(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid25_32));


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

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
