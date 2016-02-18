package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;
    int lastPush;

    SpriteBatch batch;
    TextureRegion up, down, left, right;
    static final float MAX_VELOCITY = 200;
    float x, y, xv, yv;

    @Override
    public void create () {
        batch = new SpriteBatch();
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        left.flip(true, false);
    }

    @Override
    public void render () {
        move();

        TextureRegion direction;
        if(lastPush == 1) {
            direction = up;
        }
        else if(lastPush == 2){
            direction = down;
        }
        else if(lastPush == 3){
            direction = right;
        }
        else{
            direction = left;
        }


        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(direction, x, y, WIDTH, HEIGHT);
        batch.end();
    }
     public void move() {

         Gdx.graphics.getHeight();


         Gdx.graphics.getWidth();





        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY;
            lastPush = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAX_VELOCITY * -1;
            lastPush = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            lastPush = 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -1;
            lastPush = 4;
        }

        y += yv * Gdx.graphics.getDeltaTime();
        x += xv * Gdx.graphics.getDeltaTime();

        yv = decelerate(0.5f, 0.5f);
        xv = decelerate(0.5f, 0.5f);

    }

    float decelerate(float velocity, float deceleration) {
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }

        return velocity;
    }

}
