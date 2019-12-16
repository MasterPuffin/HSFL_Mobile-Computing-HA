package com.example.androidstudio.asteroids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Model {



    public SpaceShip raumschiff;
    public Asteroid asteroid;
    Random rdm = new Random();

    public static final float ticDurationS = 0.01f;


    public static final int height = 1000;
    public int width = 0;

    public ArrayList<Bullet> arBullets = new ArrayList<Bullet>();
    public ArrayList<Asteroid> arAsteroids = new ArrayList<Asteroid>();

    public Model() {

    }

    public void init() {
        raumschiff = new SpaceShip(width /2, height /2, this);
        asteroid = new Asteroid(500f, 55f, -90f, -50f, this);

    }

    public void spawnAsteroid(){
        asteroid=new Asteroid(rdm.nextInt(width), rdm.nextInt(height), height /4, -90f, this);

    }
    /*public void newAsteroid(){
            Asteroid as = new Asteroid(rdm.nextInt(width), rdm.nextInt(height), height /4, -90f, this);
            arAsteroids.add(as);
            as.move();
            System.out.println("ok");

        ;}*/

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
