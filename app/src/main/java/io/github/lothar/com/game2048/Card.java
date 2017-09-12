package io.github.lothar.com.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import static android.view.Gravity.CENTER;

/**
 * Created by Lothar on 2017/7/20.
 *
 */

public class Card extends FrameLayout {

    public TextView lable;

    public Card(Context context) {
        super(context);
        lable = new TextView(getContext());
        lable.setTextSize(32);
        lable.setBackgroundColor(0x33ffffff);
//        if ( num == 2){
//            lable.setBackgroundColor(0xFFFF33);
//        }else if(num == 4){
//            lable.setBackgroundColor(0x33ffffff);
//        }else if(num == 8){
//            lable.setBackgroundColor(0xFF9900);
//        }
        lable.setGravity(CENTER);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(15,15,0,0);
        addView(lable,lp);
        setNum(0);
    }

    public int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num<=0){
            lable.setText("");
        }else{
            lable.setText(num+"");
        }
    }
    //判断两张卡片数字是够相同方法
    public boolean equals(Card obj) {
        return getNum()== obj.getNum();
    }

}
