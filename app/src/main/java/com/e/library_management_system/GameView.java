package com.e.library_management_system;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;
//import java.awt.GridLayout;

public class GameView extends GridLayout {
    public GameView(Context context) {
        super(context);
        InitGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        InitGameView();
    }

    private void startGame() {
        GameActivity.getGameActivity().clearScore();

        for(int j = 0; j < 4; j ++) {
            for(int i = 0; i < 4; i ++) {
                cardsMap[i][j].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }
    private void addRandomNum() {
        emptyPoints.clear();
        for(int j = 0; j < 4; j ++){
            for(int i = 0; i < 4; i ++) {
                if(cardsMap[i][j].getNum() <= 0) {
                    emptyPoints.add(new Point(i, j));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }
    private void swipeleft() {
        boolean merge = false;
        for(int y = 0; y < 4; y ++) {
            for(int x = 0; x < 4; x ++) {
                for(int x1 = x + 1; x1 < 4; x1 ++) {
                    if(cardsMap[x1][y].getNum() > 0) {
                        if(cardsMap[x][y].getNum() <= 0 ) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                            x--;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()* 2);
                            cardsMap[x1][y].setNum(0);
                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge == true) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swiperight() {
        boolean merge = false;
        for(int y = 0; y < 4; y ++) {
            for(int x = 3; x >= 0; x --) {
                for(int x1 = x - 1; x1 >= 0; x1 --) {
                    if(cardsMap[x1][y].getNum() > 0) {
                        if(cardsMap[x][y].getNum() <= 0 ) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                            x++;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()* 2);
                            cardsMap[x1][y].setNum(0);
                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge == true) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp() {
        boolean merge = false;
        for(int x = 0; x < 4; x ++) {
            for(int y = 3; y >= 0; y --) {
                for(int y1 = y - 1; y1 >= 0; y1 --) {
                    if(cardsMap[x][y1].getNum() > 0) {
                        if(cardsMap[x][y].getNum() <= 0 ) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            y++;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()* 2);
                            cardsMap[x][y1].setNum(0);
                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge == true) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swiprDown() {
        boolean merge = false;
        for(int x = 0; x < 4; x ++) {
            for(int y = 0; y < 4; y ++) {
                for(int y1 = y + 1; y1 < 4; y1 ++) {
                    if(cardsMap[x][y1].getNum() > 0) {
                        if(cardsMap[x][y].getNum() <= 0 ) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            y--;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()* 2);
                            cardsMap[x][y1].setNum(0);
                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge == true) {
            addRandomNum();
            checkComplete();
        }
    }

    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
    private void InitGameView() {

        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if(Math.abs(offsetX) > Math.abs(offsetY)) {
                            if(offsetX < -5) {
                                //left
                                swipeleft();
                            }else if(offsetX > 5) {
                                //right
                                swiperight();
                            }
                        }else{
                            if(offsetY < -5) {
                                //down
                                swiprDown();
                            }else if(offsetY > 5) {
                                //up
                                swipeUp();
                            }
                        }
                        break;
                    default:
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h) - 10) / 4;
        addCards(cardWidth, cardWidth);
        startGame();
    }
    private void addCards(int x, int y) {
        Card c;
        for(int j = 0; j < 4; j ++) {
            for(int i = 0; i < 4; i ++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, x, y);
                cardsMap[i][j] = c;
            }
        }
    }
    private void checkComplete() {
        boolean complete = true;
        AUTO:
        for(int y = 0; y < 4; y ++) {
            for(int x = 0; x < 4; x ++) {
                if(cardsMap[x][y].getNum()==0 ||
                        (x >0 && cardsMap[x][y].equals(cardsMap[x-1][y])) ||
                        (x <3 && cardsMap[x][y].equals(cardsMap[x+1][y])) ||
                        (y >0 && cardsMap[x][y].equals(cardsMap[x][y-1])) ||
                        (y <3 && cardsMap[x][y].equals(cardsMap[x][y+1]))) {
                    complete = false;
                    break AUTO;
                }
            }
        }
        if(complete){
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startGame();
                }
            }).show();
        }
    }
}
