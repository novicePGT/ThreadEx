package com.example.threadex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar seek1, seek2;
    Button btn1;
    TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seek1 = (SeekBar) findViewById(R.id.seek1);
        seek2 = (SeekBar) findViewById(R.id.seek2);
        btn1 = (Button) findViewById(R.id.btn1);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread() {
                    // 여러 작업을 동시에 수행하기 위해 스레드 사용
                    public void run(){
                        for (int i=seek1.getProgress(); i<100; i+=2) {
                            runOnUiThread(new Runnable() {
                                /* 스레드 내부에서 텍스트뷰의 글자를 변경하면 강제로 종료되는 오류가 발생하는데
                                이러한 문제를 해결하려면 runOnUiThread({})를 사용하여 위젯을 변경하려는 부분을
                                넣으면 해결된다. */
                                public void run() {
                                    seek1.setProgress(seek1.getProgress() + 2);
                                    tv1.setText("1번 진행률 : "+seek1.getProgress() + "%");
                                    // 각 프로그레스바의 초깃값으로 프로그레스 시작
                                }
                            }); SystemClock.sleep(100); // 0.1초씩 멈춤
                        }
                    }
                }.start();

                new Thread() {
                    // 여러 작업을 동시에 수행하기 위해 스레드 사용
                    public void run(){
                        for (int i=seek2.getProgress(); i<100; i++) {
                            runOnUiThread(new Runnable() {
                                // 위젯을 변경할 때는 Ui스레드를 사용하는 것이 더욱 안정적이다.
                                public void run() {
                                    seek2.setProgress(seek2.getProgress() + 1);
                                    tv2.setText("2번 진행률 : "+seek2.getProgress() + "%");
                                }
                            }); SystemClock.sleep(100);
                        }
                    }
                }.start();
            }
        });
    }
}