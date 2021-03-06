package com.mylibgdxgame.flygame.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mylibgdxgame.flygame.loader.ResourseLoader;
import com.mylibgdxgame.flygame.objects.Fly;
import com.mylibgdxgame.flygame.objects.MovHandler;

public class GameWorld {

    private Fly fly;
    private MovHandler movHandler;
    //Прямоугольник земли, для смерти мухи если она будет на земле
    private Rectangle ground;

    private int midPointY;
    private int midPointX;
    //переменная хранит счет
    private int score = 0;
    private  float runTime = 0;

    private GameRender renderer;
    private GameState currentState;
//Создаем переменну енум для создания состояния игры
    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }


    public GameWorld(int midPointY, int midPointX) {
        currentState = GameState.MENU;
        this.midPointX = midPointX;
        this.midPointY = midPointY;

        fly = new Fly(33, midPointY - 5, 17, 12);
        //Прокрутка травы
        movHandler = new MovHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 137, 11);
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;

        }
    }

    private void updateReady(float delta) {
        fly.updateReady(runTime);
        movHandler.updateReady(delta);
    }

    //Метод проверяет, если муха столкнулась с обэктом, то игра останавливается
    public void updateRunning(float delta){
        if (delta > 0.15f) {
            delta = 0.15f;
        }

        fly.update(delta);
        movHandler.update(delta);

        if (movHandler.collides(fly) && fly.isAlive()) {
            movHandler.stop();
            fly.die();
            fly.cling();
            ResourseLoader.fall.play();
            renderer.prepareTransition(255, 255, 255, 0.3f);
            currentState = GameState.GAMEOVER;
            highScore();
        }
        //Реализация падения мухи на землю
        if (Intersector.overlaps(fly.getCircle(), ground)) {
            if (fly.isAlive()) {
                ResourseLoader.dead.play();
                fly.die();
                renderer.prepareTransition(255, 255, 255, 0.3f);
            }
            movHandler.stop();
            fly.cling();
            currentState = GameState.GAMEOVER;

            highScore();
        }

    }

    private void highScore() {
        if (score > ResourseLoader.getHighScore()) {
            ResourseLoader.setHighScore(score);
            currentState = GameState.HIGHSCORE;
        }
    }

    public MovHandler getMovHandler() {
        return movHandler;
    }

    public Fly getFly() {
        return fly;
    }

    public void setRenderer(GameRender renderer) {
        this.renderer = renderer;
    }
//метод доступа к переменной
    public int getScore() {
        return score;
    }
//метод который будет увеличивать счет
    public void addScore(int increment) {
        score += increment;
    }
//Этот метод устанавливае currentState  в Ready
    public void ready() {
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
    }

    public void start() {
        currentState = GameState.RUNNING;
    }
//Этот метод обнуляет все переменные в объекте, которые подвергались изменениям в процессе игры
    public void restart() {
        score = 0;
        fly.onRestart(midPointY - 5);
        movHandler.onRestart();
        ready();
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public int getMidPointY() {
        return midPointY;
    }

    public int getMidPointX() {
        return midPointX;
    }
}
