package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;


public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;

    SpriteBatch batch;
    TextureRegion up, down, tree, tree2, grass, cactus, cactus2;
    Animation walk;
    static final float MAX_VELOCITY = 350;
    float x, y, xv, yv, time;
    int randomX, randomY, randomMinusY, randomMinusX;
    int randomCactusX, randomCactusY, randomCactusMinusX, randomCactusMinusY;
    boolean faceRight = true;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        TextureRegion[][] treeGrid = TextureRegion.split(tiles, 16, 8);
        TextureRegion[][] treeGrid2 = TextureRegion.split(tiles, 16, 8);
        TextureRegion[][] cactusGrid = TextureRegion.split(tiles, 16, 8);
        TextureRegion[][] cactusGrid2 = TextureRegion.split(tiles, 16, 8);
        TextureRegion[][] grassRegion = TextureRegion.split(tiles, 8, 8);
        walk = new Animation(.67f, grid[6][2], grid[6][3]);
        cactus = cactusGrid[1][1];
        cactus2 = cactusGrid2[2][1];
        grass = grassRegion[0][0];
        down = grid[6][0];
        up = grid[6][1];
        tree = treeGrid[1][0];
        tree2 = treeGrid2[2][0];
        randomY = (int) Math.ceil(Math.random() * Gdx.graphics.getHeight());
        randomX = (int) Math.ceil(Math.random() * Gdx.graphics.getHeight());
        randomCactusY = (int) Math.ceil(Math.random() * Gdx.graphics.getHeight() + Math.random()*101);
        randomCactusY = (int) Math.ceil(Math.random()* Gdx.graphics.getHeight() + Math.random()*101);
        randomCactusMinusY = randomCactusY -85;
        randomCactusMinusX = randomCactusX;
        randomMinusY = randomY - 85;
        randomMinusX = randomX;

    }
    @Override
    public void render() {
        move();
        draw();

    }

    float decelerate(float velocity) {
        float deceleration = 0.7f;
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }

    void move() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAX_VELOCITY * -1;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            faceRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -1;
            faceRight = false;

        }

        yv = decelerate(yv);
        xv = decelerate(xv);
    }

    public void draw() {
        time += Gdx.graphics.getDeltaTime();

        float oldX = x;
        float oldY = y;
        y += yv * Gdx.graphics.getDeltaTime();
        x += xv * Gdx.graphics.getDeltaTime();

        if (x < -20 || x > (Gdx.graphics.getWidth() - 90)) {
            x = oldX;
        }
        if (y < -5 || y > (Gdx.graphics.getHeight() - 85)) {
            y = oldY;
        }

        TextureRegion img;
        if (yv > 0){
            img = up;
        }
        else if(yv < 0){
            img = down;
        }
        else if(xv > 0) {
            img = walk.getKeyFrame(time, true);
        }
        else if(xv < 0){
            img = walk.getKeyFrame(time, true);
        }
        else{
            img = down;
        }
        batch.begin();
        TiledDrawable grassGrid = new TiledDrawable(grass);
        grassGrid.draw(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(cactus2, randomCactusMinusX, randomCactusMinusY, 110, 85);
        batch.draw(cactus, randomCactusX, randomCactusY, 110, 85);
        batch.draw(tree2, randomMinusX, randomMinusY, 110, 85);
        batch.draw(tree, randomX, randomY, 110, 85);

        if(faceRight){
            batch.draw(img, x, y, WIDTH, HEIGHT);
        }
        else{
            batch.draw(img, x + 100 , y, WIDTH *-1, HEIGHT);
        }

        batch.end();

    }
}

