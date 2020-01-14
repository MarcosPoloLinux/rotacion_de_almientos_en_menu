package com.elmundobinario.lacomidadelcanco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

public class MenuFinal extends AppCompatActivity {

    public String cerealElegido;
    public String legumbreElegida;
    public String hortalizaTuberculoElegido;
    public String condimento_hidratosElegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_final);

        // Cargamos las variables de la actividad Almuerzo de los Extras del Intent:
        cerealElegido = getIntent().getExtras().getString("cerealElegidoExtra");
        legumbreElegida = getIntent().getExtras().getString("legumbreElegidaExtra");
        hortalizaTuberculoElegido = getIntent().getExtras().getString("hortalizaTuberculoElegidoExtra");
        condimento_hidratosElegido = getIntent().getExtras().getString("condimentoHidratosElegidoExtra");

        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //Mantiene pantalla encendida...

        /* para apagar la pantalla probar esto, aun no se si funciona...
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

         */



    }
}
