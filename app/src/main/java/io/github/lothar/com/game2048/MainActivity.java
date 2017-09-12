package io.github.lothar.com.game2048;

import android.content.Context;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.Key;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Lothar
 *
 */
public class MainActivity extends AppCompatActivity {

    private Button reGame;
    private TextView Score;
    private TextView Best;
    private GameView gameView;
    public static MainActivity mainActivity = null;
    private int score = 0;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
    public MainActivity(){
        mainActivity = this;    //mainActivity一旦被构建，就给这个静态变量赋值了，外界就可以访问了
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Score = (TextView)findViewById(R.id.Score);
        Best = (TextView)findViewById(R.id.Best);
        reGame = (Button)findViewById(R.id.reGame);
        gameView = (GameView)findViewById(R.id.gameView);
        reGame.setOnClickListener(new ButtonOnclickListener());
    }
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();   //获取当前系统时间
                if (secondTime - firstTime >2000){
                    Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    class ButtonOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.reGame:
                    reGame();
                    break;

            }
        }
    }
    public void reGame(){
        score = 0;
        Score.setText("0"+"");
        gameView.startGame();
    }
    public void cleanScore(){
        score = 0;
        showScore();
    }
    public void showScore(){
        Score.setText(score+"");
        Best.setText(score+"");
    }
    public void addScore(int s){
        score+=s;
        showScore();
    }
}
