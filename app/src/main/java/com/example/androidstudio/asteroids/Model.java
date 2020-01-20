package com.example.androidstudio.asteroids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Model {

    public SpaceShip raumschiff;
    public Asteroid asteroid;
    public Asteroid asteroid2;
    public Asteroid asteroid3;

    Random rdm = new Random();

    public static final float ticDurationS = 0.01f;
    public static final int height = 1000;
    public int width = 0;

    public ArrayList<Bullet> arBullets = new ArrayList<Bullet>();
    public ArrayList<Asteroid> arAsteroids = new ArrayList<Asteroid>();

    public Model() {
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
}
