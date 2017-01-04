package jp.techacademy.murai.yusuke.lesson5_taskpractice_timer;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    Timer mainTimer;					//タイマー用
    MainTimerTask mainTimerTask;		//タイマタスククラス
    TextView countText;					//テキストビュー
    Button m_stopButton;                //ボタン
    int count = 0;						//カウント
    Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //タイマーインスタンス生成
        this.mainTimer = new Timer();
        //タスククラスインスタンス生成
        this.mainTimerTask = new MainTimerTask();
        //タイマースケジュール設定＆開始
        this.mainTimer.schedule(mainTimerTask, 0,100);
        //テキストビュー
        this.countText = (TextView)findViewById(R.id.count_text);
        //STOPボタン
        m_stopButton = (Button) findViewById(R.id.stopButton);
        m_stopButton.setTag(0);
        m_stopButton.setOnClickListener(new StopClickListener());

    }


//     *
//     * タイマータスク派生クラス
//     * run()に定周期で処理したい内容を記述
//     *

    public class MainTimerTask extends TimerTask {

        int countMax = 5;

        @Override
        public void run() {
            //ここに定周期で実行したい処理を記述します
            mHandler.post( new Runnable() {
                public void run() {

                    //実行間隔分を加算処理
                    count += 1;
                    //画面にカウントを表示
                    countText.setText(String.valueOf(count));

                    //カウンタをcountMaxで0に戻す
                    if (count == countMax){
                        count = 0;
                    }
                }
            });
        }
    }

    private class StopClickListener implements View.OnClickListener {
        public void onClick(View v){

            switch ((Integer)v.getTag()) {
                case 0:
                    Log.d("javatest", "getTag=0");
                    mainTimer.cancel();
                    m_stopButton.setText("PLAY");
                    m_stopButton.setTag(1);
                    break;
                case 1:
                    Log.d("javatest", "getTag=1");
                    mainTimer = new Timer();        //一度CancelしたTimerは再度newしないと駄目。
                    mainTimerTask = new MainTimerTask();
                    mainTimer.schedule(mainTimerTask, 0,1000);
                    m_stopButton.setText("STOP");
                    m_stopButton.setTag(0);
                    break;
                default:
                    Log.d("javatest", "getTag=0,1以外");
                    break;
            }


        }
    }
}