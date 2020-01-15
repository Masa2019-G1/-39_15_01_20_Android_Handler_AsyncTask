package com.telran.a15_01_20_cw;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Worker implements Runnable{
    public static final int START = 1;
    public static final int END = 2;
    public static final int PROGRESS = 3;
    private Handler handler;

    public Worker(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(START);
        for (int i = 0; i <= 100; i++) {
            Message msg = handler.obtainMessage(PROGRESS);
            msg.arg1 = i;
            handler.sendMessage(msg);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handler.sendEmptyMessage(END);
    }
}
