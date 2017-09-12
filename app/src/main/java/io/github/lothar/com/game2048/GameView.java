package io.github.lothar.com.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.columnCount;
import static android.R.attr.editTextBackground;
import static android.R.attr.y;

/**
 * Created by Lothar on 2017/7/20.
 */

public class GameView extends GridLayout {

    public GameView(Context context,AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    private void initGameView(){
        setColumnCount(4);

        setBackgroundColor(0xffbbada0);
        setOnTouchListener(new OnTouchListener() {

            private float startX,startY,offsetX,offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX()-startX;
                        offsetY = event.getY()-startY;

                        if (Math.abs(offsetX)>Math.abs(offsetY)){
                            if (offsetX<-5){
                                System.out.println("向左滑动");
                                swipeLeft();
                            }else if (offsetX>5){
                                System.out.println("向右滑动");
                                swipeRight();
                            }
                        }else{
                            if (offsetY<-5){
                                System.out.println("向上滑动");
                                swipeUp();
                            }else if(offsetY>5){
                                System.out.println("向下滑动");
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
    //为使用不同手机屏幕，第一次创建时候执行此方法，获取竖立状态下的宽高
    @Override

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w,h)-10)/4;
//        int cardHeight = (Math.min(w,h)-10)/4;
        addCards(cardWidth,cardWidth);
        startGame();
    }
    private void addCards(int cardWidth,int cardHeight){

        Card c;
        for (int y=0;y<4;y++){
            for (int x=0;x<4;x++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y] = c;
            }
        }
    }
    public void startGame(){

        MainActivity.getMainActivity().cleanScore();
        for (int y=0;y<4;y++){

            for (int x=0;x<4;x++){

                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }
    private void addRandomNum(){

        emptyPoints.clear();

        for (int y=0;y<4;y++){
            for (int x=0;x<4;x++){
                //判断每个位置是否没有值，没有在继续往里边添加随机数
                if (cardsMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }else if (cardsMap[x][y].getNum()==2){

                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
//        int s = cardsMap[p.x][p.y].getNum();
//        if (s == 2){
//
//        }
    }

    private void swipeLeft(){

        boolean moRen = false;

        for (int y=0;y<4;y++){

            for (int x=0;x<4;x++){

                for (int x1=x+1;x1<4;x1++){

                    if (cardsMap[x1][y].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x--;
                            moRen = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            moRen = true;
                        }
                        break;
                    }
                }
            }
        }
        if (moRen){
            addRandomNum();
            checkComplete();
        }

    }
    private void swipeRight(){

        boolean moRen = false;

        for (int y=0;y<4;y++){

            for (int x=3;x>=0;x--){

                for (int x1=x-1;x1>=0;x1--){

                    if (cardsMap[x1][y].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x++;
                            moRen = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            moRen = true;
                        }
                        break;
                    }
                }
            }
        }
        if (moRen){
            addRandomNum();
            checkComplete();
        }

    }
    private void swipeUp(){

        boolean moRen = false;

        for (int x=0;x<4;x++){

            for (int y=0;y<4;y++){

                for (int y1=y+1;y1<4;y1++){

                    if (cardsMap[x][y1].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y--;
                            moRen = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            moRen = true;
                        }
                        break;
                    }
                }
            }
        }
        if (moRen){
            addRandomNum();
            checkComplete();
        }

    }
    private void swipeDown(){

        boolean moRen = false;

        for (int x=0;x<4;x++){

            for (int y=3;y>=0;y--){

                for (int y1=y-1;y1>=0;y1--){

                    if (cardsMap[x][y1].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y++;
                            moRen = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            moRen = true;
                        }
                        break;
                    }
                }
            }
        }
        if (moRen){
            addRandomNum();
            checkComplete();
        }

    }
    public void checkComplete(){

        boolean complete = true;
        ALL:

        for (int y=0;y<4;y++){

            for (int x=0;x<4;x++){

                if (cardsMap[x][y].getNum()==0||
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){

                    complete = false;
                    break ALL;

                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重玩", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show().setCancelable(false);
        }
    }
    private Card[][] cardsMap = new Card[4][4];
    //添加随机数之前记录的空链表，来判断数组每个位置是否有数，从而判断是否添加
    private List<Point> emptyPoints = new ArrayList<>();
}
