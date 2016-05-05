package com.mylibgdxgame.flygame.objects;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;
//Этот класс расширяет класс Moving
public class Web extends Moving{

    private Random r;

//Прямогульники для паутин и пауков
    private Rectangle spider, webUp, webDown;
//Константа для указания просвета между прямоугольниками
    public static final int GAP = 45;
    private boolean isScored = false;

    private float groundY;

    //Конструктор, который вызывает конструктор родительского класса
    public Web(float x, float y, int width, int height, float movSpeed, float groundY) {
        super(x, y, width, height, movSpeed);
        r = new Random();
        spider = new Rectangle();
        webUp = new Rectangle();
        webDown = new Rectangle();
        this.groundY = groundY;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        //Устанавливаем координаты препятсвий
        webUp.set(position.x, position.y, width, height);
        webDown.set(position.x, position.y + height + GAP, width, groundY - (position.y + height + GAP));
        spider.set(position.x - (24 - width) / 2, position.y + height - 11, 24, 11);
    }

    // В этом методы мы меняем значие высоты на ранломное из указанного диапазона
    @Override
    public void reset(float newX) {
        super.reset(newX);
        //Добавление 15px нужно для того, что бы объекты не сильно вылетали за экран
        height = r.nextInt(90) + 15;
        isScored = false;
    }

    //Реализация логики столкновения
    public boolean collides(Fly fly) {
        if (position.x < fly.getX() + fly.getWidth()) {
            return (Intersector.overlaps(fly.getCircle(), webUp)
                    || Intersector.overlaps(fly.getCircle(), webDown)
                    || Intersector.overlaps(fly.getCircle(), spider));
        }
        return  false;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }

    public void onRestart(float x, float movSpeed) {
        velocity.x = movSpeed;
        reset(x);
    }

}
