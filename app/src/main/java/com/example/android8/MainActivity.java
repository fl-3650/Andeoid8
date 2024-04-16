package com.example.android8;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private Handler handler;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        // Looper - запускает цикл обработки сообщений
        // и стартует его в главном потоке - это вызов статического метода getMainLooper()

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                // здесь мы будем ждать сообщения из другого потока
                int n = msg.getData().getInt("key");
                tv.setText("N: "+ n);
                if (n==49)
                    btn.setEnabled(true);
            }
        };
        Button btn = findViewById(R.id.button1);
        btn.setOnClickListener(v -> {
            btn.setEnabled(false);
            //Создаем поток с помощью интерфейса Runnable
            //Код для нового потока. Этот поток завершится, когда метод вернёт управление.
            new Thread(this::doSlow).start();
        });
    }
    public void doSlow() {
        for(int i=0;i<50;i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("key",i);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }
}

