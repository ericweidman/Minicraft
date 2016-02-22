package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;


public class Minicraft extends ApplicationAdapter {
    static final float MAX_VELOCITY = 350;
    static final float ZOMBIE_MAX_VELOCITY = 200;
    final int WIDTH = 100;
    final int HEIGHT = 100;
    SpriteBatch batch;
    TextureRegion up, down, tree, tree2, grass, cactus, cactus2, upFlip;
    TextureRegion zombieUp, zombieDown;
    Animation walk, zombieWalk;
    float x, y, xv, yv, time, zombieX = 150, zombieY = 150;
    float zombieYv, zombieXv;
    int randomX, randomY, randomMinusY, randomMinusX;
    int randomCactusX, randomCactusY, randomCactusMinusX, randomCactusMinusY;
    boolean faceRight = true, zombieFaceRight = true;


    @Override
    public void create() {
        batch = new SpriteBatch();
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        TextureRegion[][] bigGrid = TextureRegion.split(tiles, 16, 8);
        TextureRegion[][] grassRegion = TextureRegion.split(tiles, 8, 8);
        walk = new Animation(.7f, grid[6][2], grid[6][3]);
        zombieWalk = new Animation(.3f, grid[6][6], grid[6][7]);
        cactus = bigGrid[1][1];
        cactus2 = bigGrid[2][1];
        grass = grassRegion[0][0];
        zombieUp = grid[6][5];
        zombieDown = grid[6][4];
        down = grid[6][0];
        up = grid[6][1];
        tree = bigGrid[1][0];
        tree2 = bigGrid[2][0];
        randomY = (int) Math.ceil(Math.random() * Gdx.graphics.getHeight());
        randomX = (int) Math.ceil(Math.random() * Gdx.graphics.getHeight());
        randomCactusY = (int) Math.ceil(Math.random() * Gdx.graphics.getHeight() + Math.random() * 101);
        randomCactusY = (int) Math.ceil(Math.random() * Gdx.graphics.getHeight() + Math.random() * 101);
        randomCactusMinusY = randomCactusY - 85;
        randomCactusMinusX = randomCactusX;
        randomMinusY = randomY - 85;
        randomMinusX = randomX;


    }


    @Override
    public void render() {
        move();
        zombieMove();
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
        float oldZombieX = zombieX;
        float oldZombieY = zombieY;
        y += yv * Gdx.graphics.getDeltaTime();
        x += xv * Gdx.graphics.getDeltaTime();
        zombieY += zombieYv * Gdx.graphics.getDeltaTime();
        zombieX += zombieXv * Gdx.graphics.getDeltaTime();

        if (x < -20 || x > Gdx.graphics.getWidth() - 90) {
            x = oldX;
        }
        if (y < -5 || y > Gdx.graphics.getHeight() - 85) {
            y = oldY;
        }

        TextureRegion img;
        if (yv > 0) {
            img = up;
        } else if (yv < 0) {
            img = down;
        } else if (xv > 0) {
            img = walk.getKeyFrame(time, true);
        } else if (xv < 0) {
            img = walk.getKeyFrame(time, true);
        } else {
            img = down;
        }

        if (zombieX < -20 || zombieX > Gdx.graphics.getWidth() - 90) {
            zombieX = oldZombieX;
        }
        if (zombieY < -5 || zombieY > Gdx.graphics.getHeight() - 85) {
            zombieY = oldZombieY;
        }

        TextureRegion zombieImg;
        if (zombieYv > 0) {
            zombieImg = zombieUp;
        } else if (zombieYv < 0) {
            zombieImg = zombieDown;
        } else if (zombieXv > 0) {
            zombieImg = zombieWalk.getKeyFrame(time, true);
        } else {
            zombieImg = zombieWalk.getKeyFrame(time, true);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        TiledDrawable grassGrid = new TiledDrawable(grass);
        grassGrid.draw(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(cactus2, randomCactusMinusX, randomCactusMinusY, 110, 85);
        batch.draw(cactus, randomCactusX, randomCactusY, 110, 85);
        batch.draw(tree2, randomMinusX, randomMinusY, 110, 85);
        batch.draw(tree, randomX, randomY, 110, 85);

        if (zombieFaceRight) {
            batch.draw(zombieImg, zombieX, zombieY, WIDTH, HEIGHT);
        } else {
            batch.draw(zombieImg, zombieX + 100, zombieY, WIDTH * -1, HEIGHT);
        }

        if (faceRight) {
            batch.draw(img, x, y, WIDTH, HEIGHT);
        } else {
            batch.draw(img, x + 100, y, WIDTH * -1, HEIGHT);
        }

        batch.end();

    }

    public void zombieMove() {

        //if (Math.random() * 100 >= 0 && Math.random() * 100 <= 25) {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            zombieYv = ZOMBIE_MAX_VELOCITY;

        } //else if (Math.random() * 100 >= 26 && Math.random() * 100 <= 50) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            zombieYv = ZOMBIE_MAX_VELOCITY * -1;

        } //else if (Math.random() * 100 >= 51 && Math.random() * 100 <= 75) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            zombieXv = ZOMBIE_MAX_VELOCITY;
            zombieFaceRight = true;

        } //else {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            zombieXv = ZOMBIE_MAX_VELOCITY * -1;
            zombieFaceRight = false;

        }
    }
}


