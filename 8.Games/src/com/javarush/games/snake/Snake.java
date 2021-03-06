package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake{
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y){
        snakeParts.add(new GameObject(x,y));
        snakeParts.add(new GameObject(x+1,y));
        snakeParts.add(new GameObject(x+2,y));
    }

    public void draw(Game game){
        for (int i = 0; i < snakeParts.size(); i++) {
            if(i==0){
                if(isAlive) {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                }else{
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                }
            }else {
                if(isAlive) {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                }else{
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
                }
            }
        }
    }

    public void setDirection(Direction d) {
        if((direction == Direction.LEFT || direction == Direction.RIGHT)
                && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if((direction == Direction.UP || direction == Direction.DOWN)
                && snakeParts.get(0).y == snakeParts.get(1).y){
            return;
        }
        this.direction = d;
    }

    public void move(Apple apple){
        GameObject newHead = createNewHead();
        if(checkCollision(newHead)){
            isAlive = false;
        }else {
            if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH || newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT) {
                isAlive = false;
            } else {
                snakeParts.add(0, newHead);
                if (newHead.x == apple.x && newHead.y == apple.y) {
                    apple.isAlive = false;
                } else {
                    removeTail();
                }
            }
            if (newHead.x == apple.x && newHead.y == apple.y) {
                apple.isAlive = false;
            }
        }
    }

    public GameObject createNewHead(){
        GameObject result;
        switch (direction){
            case UP: result = new GameObject(snakeParts.get(0).x,snakeParts.get(0).y-1); break;
            case DOWN: result = new GameObject(snakeParts.get(0).x,snakeParts.get(0).y+1); break;
            case LEFT: result = new GameObject(snakeParts.get(0).x-1,snakeParts.get(0).y); break;
            case RIGHT: result = new GameObject(snakeParts.get(0).x+1,snakeParts.get(0).y); break;
            default: result = new GameObject(snakeParts.get(0).x-1,snakeParts.get(0).y); break;
        }
        return result;
    }

    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }

    public boolean checkCollision(GameObject go){
        for (GameObject snakePart : snakeParts) {
            if (go.x == snakePart.x && go.y == snakePart.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength(){
        return snakeParts.size();
    }
}
