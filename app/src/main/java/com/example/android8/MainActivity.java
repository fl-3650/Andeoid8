package com.example.android8;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        runTask1();
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
            mainHandler.post(this::runTask2);
        });
        thread1.start();
    }
    
    private void runTask2() {
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++){
                textView.setText(i + " Task2");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.getCause();
                }
            }
           mainHandler.post(this::runTask3);
        });
        thread2.start();
    }

    private void runTask3() {
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 5; i++){
                textView.setText(i + " Task3");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.getCause();
                }
            }
        });
        thread3.start();
    }
}

