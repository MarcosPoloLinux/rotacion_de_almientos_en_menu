package com.elmundobinario.lacomidadelcanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void intentAlmuerzo(View view) {
        Intent intencion = new Intent (this, Almuerzo.class);
        startActivity(intencion);
    }

    public void intentCena(View view) {
        Intent intencion = new Intent(this, Cena.class);
    startActivity(intencion);
    }
}