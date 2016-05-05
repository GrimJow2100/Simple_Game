package com.mylibgdxgame.flygame.objects;


import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mylibgdxgame.flygame.loader.ResourseLoader;

public class Fly {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    //Круг для мухи, что бы реализовать столкновение
    private Circle circle;

    private boolean isAlive;
    private float rotation;
    private int width;
    private float height;
    //переменнная которая указывает на начало координат мухи, при старте игры
    private float originalY;

    public Fly(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.originalY = y;

        circle = new Circle();

        isAlive = true;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
    }

    //Метод для того что бы повернуть муху вниз
    public boolean isFalling() {
        return velocity.y > 110;
    }

    //Метод для того что понимать, когда муха должна перестать махать крыльями
    public boolean notFlap() {
        return velocity.y > 70 || !isAlive;
    }

    //Метод для проверки жива ли муха
    //что она не реагировала на клики после смерти
    public void onClick() {
        if (isAlive) {
            velocity.y = -140;
            ResourseLoader.flap.play();
        }
    }

    public void update(float delta) {

        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        //Условие, что бы муха не улетела вверх за пределы экрана
        if (position.y < -13) {
            position.y = -13;
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(delta));

        //Тут прописываем изменения для круга, что бы двигался вместе с мухой
        circle.set(position.x + 9, position.y + 6, 6.5f);

        //Здесь вращаем муху по часовой стрелке, когда она взлетает
        if (velocity.y < 0) {
            //Эфект нормализации, что бы муха поворачивалась с нормальной скоростью
            rotation -= 600 * delta;

            //Ограничение поворота на нужном значении
            if (rotation < -20) {
                rotation = -20;
            }
        }

        //Тот же код для падения мухи
        if (isFalling()) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }
        }
    }

    //Методы который вызывается при смерти мухи
    //обнуляет скорость мухи
    public void die() {
        isAlive = false;
        velocity.y = 0;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;

    }

    public float getRotation() {
        return rotation;
    }

    public int getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Circle getCircle() {
        return circle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    //Метод, который реализует приливание мухи к паутине
    public void cling() {
        acceleration.y = 0;
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }

    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }


}
