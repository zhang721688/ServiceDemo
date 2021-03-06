package com.think.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by think on 2017/11/19.
 */

public class MyIntentService1 extends IntentService {

    private boolean isRunning = true;
    private int count = 0;
    private LocalBroadcastManager mLocalBroadcastManager;
    private IntentFilter intentFilter1;
    private IntentFilter intentFilter2;

    public MyIntentService1() {
        super("MyIntentService1");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        sendServiceStatus("服务启动");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            sendThreadStatus("线程启动", count);
            Thread.sleep(1_000);
            sendServiceStatus("服务运行中...");

            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                sendThreadStatus("线程运行中...", count);
            }
            sendThreadStatus("线程结束", count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendServiceStatus("服务结束");
    }

    // 发送服务状态信息
    private void sendServiceStatus(String status) {
        Intent intent = new Intent(ServiceIntent1Activity.ACTION_TYPE_SERVICE);
        intent.putExtra("status", status);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    // 发送线程状态信息
    private void sendThreadStatus(String status, int progress) {
        Intent intent = new Intent(ServiceIntent1Activity.ACTION_TYPE_THREAD);
        intent.putExtra("status", status);
        intent.putExtra("progress", progress);
        mLocalBroadcastManager.sendBroadcast(intent);
    }
}
