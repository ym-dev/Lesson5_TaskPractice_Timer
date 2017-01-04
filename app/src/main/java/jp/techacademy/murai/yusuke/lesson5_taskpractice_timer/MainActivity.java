package jp.techacademy.murai.yusuke.lesson5_taskpractice_timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

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
        this.mainTimer.schedule(mainTimerTask, 0,2000);
        //テキストビュー
        this.countText = (TextView)findViewById(R.id.count_text);
        //STOPボタン
        m_stopButton = (Button) findViewById(R.id.stopButton);
        m_stopButton.setOnClickListener(new StopClickListener());

    }


//     *
//     * タイマータスク派生クラス
//     * run()に定周期で処理したい内容を記述
//     *

    public class MainTimerTask extends TimerTask {
        @Override
        public void run() {
            //ここに定周期で実行したい処理を記述します
            mHandler.post( new Runnable() {
                public void run() {

                    //実行間隔分を加算処理
                    count += 1;
                    //画面にカウントを表示
                    countText.setText(String.valueOf(count));
                }
            });
        }
    }

    private class StopClickListener implements View.OnClickListener {
        public void onClick(View v){
            mainTimer.cancel();
        }
    }
}