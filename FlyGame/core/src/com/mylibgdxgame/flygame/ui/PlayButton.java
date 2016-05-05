package com.mylibgdxgame.flygame.ui;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mylibgdxgame.flygame.loader.ResourseLoader;
//Кнопка для старта игры
public class PlayButton {

    private  float x, y, width, height;

    private TextureRegion buttonUp;
    private TextureRegion buttonDown;

    private Rectangle bounds;

    private boolean isPressed = false;

    public PlayButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;

        bounds = new Rectangle(x, y, width, height);
    }
//метод для отрисовки
    public void draw(SpriteBatch batch) {
        if (isPressed) {
            batch.draw(buttonDown, x, y, width, height);
        } else {
            batch.draw(buttonUp, x, y, width, height);
        }
    }
//Возвращает True при нажатии кнопки
    public boolean isTouchDown(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }
        return false;
    }
//Метод обрабатывается при выходе из нажатого состояния
    public  boolean isTouchUp(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            ResourseLoader.flap.play();
            return true;
        }
        isPressed = false;
        return false;
    }
}
