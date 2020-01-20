package com.example.androidstudio.asteroids;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static android.content.ContentValues.TAG;


public class Model {

    public SpaceShip raumschiff;
    public Asteroid asteroid;
    public Asteroid asteroid2;
    public Asteroid asteroid3;

    private static final String serialisationFile = "Persistenz4.dat";
    private Activity myActivity;
    public Integer ticCounter = 0;

    Random rdm = new Random();

    public static final float ticDurationS = 0.01f;
    public static final int height = 1000;
    public int width = 0;

    public boolean initialised = false;

    public ArrayList<Bullet> arBullets = new ArrayList<Bullet>();
    public ArrayList<Asteroid> arAsteroids = new ArrayList<Asteroid>();

    public Model(Activity act) {
        myActivity = act;
    }

    public void init() {
        raumschiff = new SpaceShip(width / 2, height / 2, this);
        float rdmbreite = rdm.nextInt(width);
        float rdmhoehe = rdm.nextInt(height);
        while (Math.abs(rdmbreite - raumschiff.x) < 20) {
            rdmbreite = rdm.nextInt(width);
        }
        while (Math.abs(rdmhoehe - raumschiff.y) < 20) {
            rdmhoehe = rdm.nextInt(height);
        }
        asteroid = new Asteroid(rdmbreite, rdmhoehe, rdm.nextInt(360), -10, this);
        initialised = true;

        load();
    }

    public void spawnAsteroid(int score) {

        float rdmbreite = rdm.nextInt(width);
        float rdmhoehe = rdm.nextInt(height);
        while (Math.abs(rdmbreite - raumschiff.x) < 20) {
            rdmbreite = rdm.nextInt(width);
        }
        while (Math.abs(rdmhoehe - raumschiff.y) < 20) {
            rdmhoehe = rdm.nextInt(height);
        }

        asteroid = new Asteroid(rdmbreite, rdmhoehe, rdm.nextInt(360), -score * 20, this);

        if (score == 5) {
            asteroid2 = new Asteroid(100, rdmhoehe, rdm.nextInt(360), -score * 20, this);
        }
        if (score == 10) {
            asteroid3 = new Asteroid(100, rdmhoehe, rdm.nextInt(360), -score * 20, this);
        }
    }

    public void spawnAsteroid2(int score) {
        float rdmbreite = rdm.nextInt(width);
        float rdmhoehe = rdm.nextInt(height);
        while (Math.abs(rdmbreite - raumschiff.x) < 20) {
            rdmbreite = rdm.nextInt(width);
        }
        while (Math.abs(rdmhoehe - raumschiff.y) < 20) {
            rdmhoehe = rdm.nextInt(height);
        }

        asteroid2 = new Asteroid(rdmbreite, rdmhoehe, rdm.nextInt(360), -score * 20, this);

        if (score == 10) {
            asteroid3 = new Asteroid(100, rdmhoehe, rdm.nextInt(360), -score * 20, this);
        }
    }

    public void spawnAsteroid3(int score) {
        float rdmbreite = rdm.nextInt(width);
        float rdmhoehe = rdm.nextInt(height);
        while (Math.abs(rdmbreite - raumschiff.x) < 20) {
            rdmbreite = rdm.nextInt(width);
        }
        while (Math.abs(rdmhoehe - raumschiff.y) < 20) {
            rdmhoehe = rdm.nextInt(height);
        }

        asteroid3 = new Asteroid(rdmbreite, rdmhoehe, rdm.nextInt(360), -score * 20, this);
    }


    public void add(Bullet bullet) {  // wird von SpaceShip gefuellt -> ## Listener ?
        arBullets.add(bullet);
    }

    public void deleteDead() {
        Iterator<Bullet> it = arBullets.iterator();

        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (!bullet.isAlive) {
                it.remove();
            }
        }
    }

    public void save() {
        Log.d(TAG, "save()");

        FileOutputStream foStream = null;

        try
        {
            // hier wird via Klasse Context ein FileOutputStream im privaten Bereich geliefert
            foStream =  myActivity.openFileOutput(serialisationFile, Context.MODE_PRIVATE);

            ObjectOutputStream o = new ObjectOutputStream( foStream );
            o.writeObject(ticCounter);
            o.writeObject(asteroid);
            o.writeObject(raumschiff);
            o.writeObject(arBullets);

            Log.d(TAG,"save(): serialisierte Objekte geschrieben");
        }
        catch ( IOException e ) { Log.d(TAG,"IOException: " + e.toString()); }
        // hier weitere spezielle Exceptions 'catchen' (z.B. Formatkonvertierungen
        finally { try { foStream.close(); }             // stream koennte noch geoeffnet sein
        catch ( Exception e ) {  Log.d(TAG,"Exception: " + e.toString());  } } // und letztlich
        // alle Exceptions abfangen
    }
    private void load() {
        Log.d(TAG, "load()");
        InputStream fiStream = null;
        try
        {
            fiStream = myActivity.openFileInput(serialisationFile);
            ObjectInputStream o = new ObjectInputStream( fiStream );

            ticCounter = (Integer) o.readObject();
            asteroid = (Asteroid) o.readObject();
            asteroid.init();
            raumschiff = (SpaceShip) o.readObject();
            raumschiff.init(this);
            arBullets = (ArrayList<Bullet>) o.readObject();
            for (Bullet bullet: arBullets)
            {
                bullet.init();
            }

            Log.d(TAG, "load(): serialisierte Objekte geladen");
        }
        catch ( IOException e ) {  Log.d(TAG,"IOException: " + e.toString());  }
        catch ( ClassNotFoundException e ) { Log.d(TAG,"ClassNotFoundException: " + e.toString());  }
        finally { try { fiStream.close(); }
        catch ( Exception e ) { Log.d(TAG,"Exception: " + e.toString());  } }
    }
}
