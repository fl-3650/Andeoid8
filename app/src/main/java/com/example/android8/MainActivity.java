package com.example.android8;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private TextView textView;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        runTask1();
        runTask2();
    }

    private void runTask1() {
        Thread thread1 = new Thread(() -> {

            for (int i = 0; i < 5; i++){
                textView.setText(i + " Task1");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.getCause();
                }
            }
        });
        thread1.start();
    }
    
    private void runTask2() {
        Thread thread2 = new Thread(() -> {
            for (int i = 5; i >= 0; i--){
                textView2.setText(i + " Task2");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.getCause();
                }
            }
        });
        thread2.start();
    }
}

