package com.example.threads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.example.threads.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    static String TextToView = "Ночь, улица, фонарь, аптека,\n" +
            "Бессмысленный и тусклый свет.\n" +
            "Живи еще хоть четверть века -\n" +
            "Все будет так. Исхода нет.\n" +
            "Умрешь - начнешь опять сначала\n" +
            "И повторится все, как встарь:\n" +
            "Ночь, ледяная рябь канала,\n" +
            "Аптека, улица, фонарь.";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                char[] chars = (char[]) msg.obj;
                String str = String.valueOf(chars);
                binding.TV.setText(str);
            }
        };
        binding.butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThread myThread = new MyThread(TextToView);
                myThread.start();
            }
        });
    }

    class MyThread extends Thread {
        private char[] texttoview;
        private String text;

        public MyThread(String text) {
            this.text = text;
            this.texttoview = new char[text.length()];
        }

        @Override
        public void run() {
            super.run();
            char[] textchars = text.toCharArray();
            for (int i = 0; i < textchars.length; i++) {
                char ch = textchars[i];
                texttoview[i] = ch;
                Message msg = new Message();
                msg.obj = texttoview;
                handler.sendMessage(msg);
                try {
                    if (ch == '.' || ch == '!' || ch == '?') {
                        Thread.sleep(1000);
                    } else {
                        Thread.sleep(200);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}