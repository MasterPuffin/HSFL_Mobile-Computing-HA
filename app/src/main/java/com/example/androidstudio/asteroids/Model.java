package com.example.androidstudio.asteroids;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by androidstudio on 14.01.16.
 */

public class Model {

    // private Resources resources;

    public SpaceShip spaceShip;
    public Bullet bullet;
    public Bullet bull1;
    public Bullet bull2;
    public Asteroid asteroid;

    public static final float ticDurationS = 0.01f;

    // logische screen Werte
    public static final int screenHeight = 1000;  // logische Hoehe, die ist fix !!!, hierauf wird intern gemappt
    public int screenWidth = 0;            // ergibt sich aus dem AspectRatio des aktuellen canvas

    public ArrayList<Bullet> arBullets = new ArrayList<Bullet>();
    public ArrayList<Asteroid> arAsteroids = new ArrayList<Asteroid>();

    public Model() {

        // resources = res;

//        bullet = new Bullet(200f + 55f, 100f + 50f, 0f, 10f, 400, this);
//        bullet.collision(bullet);
//        bull1 = new Bullet(5f, 995f, 0f, 10f, 400, this);
//        bull2 = new Bullet(screenWidth - 5f, 995f, 0f, -10f, 400, this);

    }

    public void init() {
        spaceShip = new SpaceShip(screenWidth/2, screenHeight/2, this);
        asteroid = new Asteroid(500f, 55f, 0f, -50f, this);

    }

    public void add(Bullet bullet) {  // wird von SpaceShip gefuellt -> ## Listener ?
        arBullets.add(bullet);
    }

    public void deleteDead(){
        Iterator<Bullet> it = arBullets.iterator();

        while(it.hasNext()){
            Bullet bullet = it.next();
            if( ! bullet.isAlive ){
                it.remove();
            }
        }
    }
}
