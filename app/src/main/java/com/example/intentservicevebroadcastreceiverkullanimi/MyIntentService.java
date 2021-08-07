package com.example.intentservicevebroadcastreceiverkullanimi;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Uzun sürecek işlemler için kullanılabilir, WorkerThread de çalışıyor, Async olarak.
 * DezAvantajı şu ki; arka plan işlemi tamamlanmadan iptal edilirse, öldürülürse;
 * otomatik olarak tekrar BAŞLATILMIYOR !!! Verilen görev önemliyse, tamamlanması şart ise
 * JobIntentService kullanın.
 *
 *      bakınız= Github halil9393/JobIntentServiceVeResultReceiverKullanimi repository
 *
 * */

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyCustomThreadName");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate() {// Main Thread
        Log.i("tag_flow", "MyIntentService onCreate " + Thread.currentThread().getName() + " thread üzerinden cağrıldı");
        super.onCreate();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {// WorkerThread
        Log.i("tag_flow", "MyIntentService onHandleIntent " + Thread.currentThread().getName() + " thread üzerinden cağrıldı");

        for (int i = 0; i < 100; i++) {

            Intent intent1 = new Intent("my.custom.action.name");
            intent1.putExtra("process",i);
            sendBroadcast(intent1);

            SystemClock.sleep(2000);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onDestroy() {// Main Thread
        Log.i("tag_flow", "MyIntentService onDestroy " + Thread.currentThread().getName() + " thread üzerinden cağrıldı");
        super.onDestroy();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

}

