package com.example.intentservicevebroadcastreceiverkullanimi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textSayac;

    // Yeni BroadcastRecevier nesnesi üretiyoruz. İstersek kendi custom Class ımızı oluşturup,
    // BroadcastReceiver den extend edebilir, ve o classın nesnesini dinleyici olarak kullanabilirdik.
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) { //Main Thread

            Log.i("tag_flow", "MainActivity.BroadcastReceiver onReceive çalıştı");
            Log.i("tag_process","process = "+intent.getIntExtra("process",0));

            // MainThread de çalıştığı için doğrudan UI güncellenebilmektedir.
            textSayac.setText("" + intent.getIntExtra("process", 0));

        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSayac = findViewById(R.id.textSayac);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void StartMyIntentService(View view) {

        //Servisden veri alabilmek için BroadcastReceiver kullanıldı.
        // ResultReceiver da kullanılabilirdi, örnek için bakınız= Github halil9393/JobIntentServiceVeResultReceiverKullanimi repository

        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onResume() {
        super.onResume();

        // BroadcastReceiver a kayıt yaptırıyoruz, action isminden gelen yayınları dinlemeye başlıyoruz..
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("my.custom.action.name");
        registerReceiver(broadcastReceiver, intentFilter);


        /*
        * Aşağıdaki durumu şu şekilde kullanabiliriz;
        *
        *   Uygulamamız kapalıyken dinleme yapamadığımız için Androidin yayınladığı Broadcast leri dinleyemiyoruz.
        *   Ama uygulamamız açılırken, Androidin özel Action Namelerini dinleme yaparsak eğer; en son yayınlanan veriyi alabiliriz.
        *   Örnek: Uygulamamız açıldığında  en son Batarya hakkındaki Broadcasti aşağıdaki gibi yakalayabiliriz..
        *
        * */

//        IntentFilter intentFilter = new IntentFilter();
//        Intent intentGelen = registerReceiver(null,intentFilter);
//        int durum = intentGelen.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
//        if(durum==1) Toast.makeText(this, "durum 1", Toast.LENGTH_SHORT).show();
//        else if(durum==2) Toast.makeText(this, "durum 2", Toast.LENGTH_SHORT).show();
//        else if(durum==3) Toast.makeText(this, "durum 3", Toast.LENGTH_SHORT).show();
//        else if(durum==4) Toast.makeText(this, "durum 4", Toast.LENGTH_SHORT).show();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onPause() {
        super.onPause();

        // Yayına olan kaydımızı koparmakda bizim vazifemiz, aksi halde arka planda hata verecektir.
        unregisterReceiver(broadcastReceiver);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

}