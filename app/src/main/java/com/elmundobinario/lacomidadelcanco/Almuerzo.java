package com.elmundobinario.lacomidadelcanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Almuerzo extends AppCompatActivity {

    TextView preguntaIngrediente;

    // base de datos con SharedPreferences:
    SharedPreferences cerealesSharedPreferences;
    SharedPreferences legumbresSharedPreferences;
    SharedPreferences hortalizasTuberculosSharedPreferences;
    SharedPreferences condimentoHidratosSharedPreferences;
    SharedPreferences.Editor sharedPrefEditorCereales;
    SharedPreferences.Editor sharedPrefEditorLegumbres;
    SharedPreferences.Editor sharedPrefEditorHortalizasTuberculos;
    SharedPreferences.Editor sharedPrefEditorCondimentoHidratos;

    //Aquí cargarán provisionalmente los arrays:
    String cereales[] = {"arroz integral", "pasta integral", "trigo sarraceno",
            "quinoa", "amaranto", "arroz blanco", "maiz", "mijo"};
    String legumbres[] = {"soja verde en grano", "azuki", "lentejas", "alubias oscuras", "habas",
            "altramuces", "garbanzos", "guisantes", "cacahuetes"};
    String hortalizas_tuberculos[] = {"coliflor", "alcachofas", "calabacín", "remolacha", "nabo",
            "puerro", "pepino", "pimiento de cualquier color", "berenjena", "batata", "patata", "rábano",
            "chirivía", "calabaza"};
    String condimento_hidratos[] = {"laurel", "hierbabuena", "comino (de bote)",
            "pimentón dulce (de bote)", "cúrcuma (de bote)", "jengibre", "mejorana"};
    int cerealPreguntado = 0;
    int legumbrePreguntada = 0;
    int hortaliza_tuberculoPreguntado = 0;
    int condimento_hidratosPreguntado = 0;
    String cerealElegido;
    String legumbreElegida;
    String hortalizaTuberculoElegido;
    String condimento_hidratosElegido;
    int ordenBloqueAlimento = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almuerzo);

        preguntaIngrediente = findViewById(R.id.ingrediente_almuerzo_textview);

        cerealesSharedPreferences = getSharedPreferences("cereales", Context.MODE_PRIVATE);
        legumbresSharedPreferences = getSharedPreferences("legumbres", Context.MODE_PRIVATE);
        hortalizasTuberculosSharedPreferences = getSharedPreferences("hortalizas_tuberculos", Context.MODE_PRIVATE);
        condimentoHidratosSharedPreferences = getSharedPreferences("condimento_hidratos", Context.MODE_PRIVATE);
        sharedPrefEditorCereales = cerealesSharedPreferences.edit();
        sharedPrefEditorLegumbres = legumbresSharedPreferences.edit();
        sharedPrefEditorHortalizasTuberculos = hortalizasTuberculosSharedPreferences.edit();
        sharedPrefEditorCondimentoHidratos = condimentoHidratosSharedPreferences.edit();

        //Si no es la primera vez, existirá el fichero CEREALES y habrá que cargar el array desde SharedPreferences
        if (cerealesSharedPreferences.contains(String.valueOf(0))) {
            for (int i = 0; i < 8; i++) {
                cereales[i] = cerealesSharedPreferences.getString(String.valueOf(i), "");
            }

        } //fin de la carga del array cereales

        //Si no es la primera vez, existirá el fichero LEGUMBRES y habrá que cargar el array desde SharedPreferences
        if (legumbresSharedPreferences.contains(String.valueOf(0))) {
            for (int i = 0; i < 9; i++) {
                legumbres[i] = legumbresSharedPreferences.getString(String.valueOf(i), "");
            }
        } //fin de la carga del array legumbres

        //Si no es la primera vez, existirá el fichero HORTALIZAS_TUBERCULOS y habrá que cargar el array desde SharedPreferences:
        if (hortalizasTuberculosSharedPreferences.contains(String.valueOf(0))) {
            for (int i = 0; i < 14; i++) {
                hortalizas_tuberculos[i] = hortalizasTuberculosSharedPreferences.getString(String.valueOf(i), "");
            } //fin de la carga del array hortalizas_tuberculos
        }
        //Si no es la primera vez, existirá el fichero CONDIMENTOS_HIDRATOS y habrá que cargar el array desde SharedPreferences:
        if (condimentoHidratosSharedPreferences.contains(String.valueOf(0))) {
            for (int i = 0; i < 7; i++) {
                condimento_hidratos[i] = condimentoHidratosSharedPreferences.getString(String.valueOf(i), "");
            } //fin de la carga del array condimentos_hidratos.
        }
        // primera vez que muestra ingrediente preguntado. Las demás veces en onClicks...
        if (ordenBloqueAlimento == 1) {
            preguntaIngrediente.setText(cereales[cerealPreguntado] + "?");
        }

    }

    public void clickSi_Almuerzo(View view) {
        // 1 = cereales
        if (ordenBloqueAlimento == 1) {
            cerealElegido = cereales[cerealPreguntado];
            rotarArrayAlimento();
            ordenBloqueAlimento++;
            preguntaIngrediente.setText(legumbres[legumbrePreguntada] + "?");
            cerealPreguntado = 0;
        }
        // 2 = legumbres:
        else if (ordenBloqueAlimento == 2) {
            legumbreElegida = legumbres[legumbrePreguntada];
            rotarArrayAlimento();
            ordenBloqueAlimento++;
            preguntaIngrediente.setText(hortalizas_tuberculos[hortaliza_tuberculoPreguntado] + "?");
            legumbrePreguntada = 0;
        }
        // 3 = hortalizas_tuberculos:
        else if (ordenBloqueAlimento == 3) {
            hortalizaTuberculoElegido = hortalizas_tuberculos[hortaliza_tuberculoPreguntado];
            rotarArrayAlimento();
            ordenBloqueAlimento++;
            preguntaIngrediente.setText(condimento_hidratos[condimento_hidratosPreguntado] + "?");
        }
        // 4 = condimento_hidratos:
        else if (ordenBloqueAlimento == 4) {
            condimento_hidratosElegido = condimento_hidratos[condimento_hidratosPreguntado];
            rotarArrayAlimento();
            // Aqui ya se tienen todos los alimentos
            // AQUI VA EL DÓDIGO PARA GUARDAR DATOS DE ARRAYS EN ARCHIVOS
            guardarArrayCereales();
            guardarArrayLegumbres();
            guardarArrayHortalizaTuberculo();
            guardarArrayCondimentoHidratos();
            condimento_hidratosPreguntado = 0;
            lanzaActivityMenuFinal();
        }

    }

    public void clickNo_Almuerzo(View view) {
        if (ordenBloqueAlimento == 1) { // si es un cereal:
            cerealPreguntado++;        // pasa a preguntar el siguiente cereal
            if (cerealPreguntado >= 8) {
                ordenBloqueAlimento++; // ya no hay más cereales: pasa a legumbres...
                preguntaIngrediente.setText(legumbres[legumbrePreguntada] + "?");
                cerealPreguntado = 0;
                cerealElegido = "";
            } else {
                preguntaIngrediente.setText(cereales[cerealPreguntado] + "?");
            }
        }
        if (ordenBloqueAlimento == 2) { // si es una legumbre:
            legumbrePreguntada++;        // pasa a preguntar la siguiente legumbre:
            if (legumbrePreguntada >= 9) {
                ordenBloqueAlimento++; // ya no hay más cereales: pasa a legumbres...
                preguntaIngrediente.setText(hortalizas_tuberculos[hortaliza_tuberculoPreguntado] + "?");
                legumbrePreguntada = 0;
                legumbreElegida = "";
            } else {
                preguntaIngrediente.setText(legumbres[legumbrePreguntada] + "?");
            }

        }
        if (ordenBloqueAlimento == 3) { // si es una hortaliza:
            hortaliza_tuberculoPreguntado++; // pasa a preguntar la siguiente hortaliza:
            if (hortaliza_tuberculoPreguntado >= 14) {
                ordenBloqueAlimento++; // ya no hay más cereales: pasa a legumbres...
                preguntaIngrediente.setText(condimento_hidratos[condimento_hidratosPreguntado] + "?");
                hortaliza_tuberculoPreguntado = 0;
                hortalizaTuberculoElegido = "";
            } else {
                preguntaIngrediente.setText(hortalizas_tuberculos[hortaliza_tuberculoPreguntado] + "?");
            }

        }
        if (ordenBloqueAlimento == 4) { // si es un condimento:
            condimento_hidratosPreguntado++; // pasa a preguntar el siguiente condimento:
            if (condimento_hidratosPreguntado >= 7) {
                // Aqui ya se tienen todos los alimentos
                // AQUI VA EL DÓDIGO PARA GUARDAR DATOS DE ARRAYS EN ARCHIVOS
                guardarArrayCereales();
                guardarArrayLegumbres();
                guardarArrayHortalizaTuberculo();
                guardarArrayCondimentoHidratos();
                condimento_hidratosPreguntado = 0;
                condimento_hidratosElegido = "";
                lanzaActivityMenuFinal();
            } else {
                preguntaIngrediente.setText(condimento_hidratos[condimento_hidratosPreguntado] + "?");
            }

        }
    }

    private void rotarArrayAlimento() {
        // ROTAR CEREALES:
        if (ordenBloqueAlimento == 1) {
            String reordenandoCereales[] = new String[8];
            reordenandoCereales[7] = cereales[cerealPreguntado];
            int siguienteCerealPreguntadoEnIf = 0;
            for (int i = 0; i < 7; i++) {
                if (i == cerealPreguntado) siguienteCerealPreguntadoEnIf++; // salta el indice vacio
                // del indice que se mueve al final (ultimo indice del array)
                reordenandoCereales[i] = cereales[siguienteCerealPreguntadoEnIf];
                siguienteCerealPreguntadoEnIf++;
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 8; i++) {
                cereales[i] = reordenandoCereales[i];
            }
        }
        //ROTAR LEGUMBRES:
        else if (ordenBloqueAlimento == 2) {
            String reordenandoLegumbres[] = new String[9];
            reordenandoLegumbres[8] = legumbres[legumbrePreguntada];
            int siguienteLegumbrePreguntadaEnIf = 0;
            for (int i = 0; i < 8; i++) {
                if (i == legumbrePreguntada) siguienteLegumbrePreguntadaEnIf++;
                reordenandoLegumbres[i] = legumbres[siguienteLegumbrePreguntadaEnIf];
                siguienteLegumbrePreguntadaEnIf++;
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 9; i++) {
                legumbres[i] = reordenandoLegumbres[i];
            }

        }
        //ROTAR HORTALIZAS_TUBERCULOS:
        else if (ordenBloqueAlimento == 3) {
            String reordenandoHortalizasTuberculos[] = new String[14];
            reordenandoHortalizasTuberculos[13] = hortalizas_tuberculos[hortaliza_tuberculoPreguntado];
            int siguienteHortalizaPreguntadaEnIf = 0;
            for (int i = 0; i < 13; i++) {
                if (i == hortaliza_tuberculoPreguntado) siguienteHortalizaPreguntadaEnIf++;
                reordenandoHortalizasTuberculos[i] = hortalizas_tuberculos[siguienteHortalizaPreguntadaEnIf];
                siguienteHortalizaPreguntadaEnIf++;
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 14; i++) {
                hortalizas_tuberculos[i] = reordenandoHortalizasTuberculos[i];
            }

        }
        //ROTAR CONDIMENTO_HIDRATOS:
        else if (ordenBloqueAlimento == 4) {
            String reordenandoCondimento_Hidratos[] = new String[7];
            reordenandoCondimento_Hidratos[6] = condimento_hidratos[condimento_hidratosPreguntado];
            int siguienteCondimentoPreguntadoEnIf = 0;
            for (int i = 0; i < 6; i++) {
                if (i == condimento_hidratosPreguntado) siguienteCondimentoPreguntadoEnIf++;
                reordenandoCondimento_Hidratos[i] = condimento_hidratos[siguienteCondimentoPreguntadoEnIf];
                siguienteCondimentoPreguntadoEnIf++;
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 7; i++) {
                condimento_hidratos[i] = reordenandoCondimento_Hidratos[i];
            }

        }

    }

    private void guardarArrayCereales() {
        for (int i = 0; i < 8; i++) {
            sharedPrefEditorCereales.putString(String.valueOf(i), cereales[i]);
            sharedPrefEditorCereales.commit();
        }
    }

    private void guardarArrayLegumbres() {
        for (int i = 0; i < 9; i++) {
            sharedPrefEditorLegumbres.putString(String.valueOf(i), legumbres[i]);
            sharedPrefEditorLegumbres.commit();
        }
    }

    private void guardarArrayHortalizaTuberculo() {
        for (int i = 0; i < 14; i++) {
            sharedPrefEditorHortalizasTuberculos.putString(String.valueOf(i), hortalizas_tuberculos[i]);
            sharedPrefEditorHortalizasTuberculos.commit();
        }
    }

    private void guardarArrayCondimentoHidratos() {
        for (int i = 0; i < 7; i++) {
            sharedPrefEditorCondimentoHidratos.putString(String.valueOf(i), condimento_hidratos[i]);
            sharedPrefEditorCondimentoHidratos.commit();
        }
    }

    private void lanzaActivityMenuFinal() {
        Intent intencion = new Intent(this, MenuFinal.class);
        intencion.putExtra("cerealElegidoExtra", cerealElegido);
        intencion.putExtra("legumbreElegidaExtra", legumbreElegida);
        intencion.putExtra("hortalizaTuberculoElegidoExtra", hortalizaTuberculoElegido);
        intencion.putExtra("condimentoHidratosElegidoExtra", condimento_hidratosElegido);
        startActivity(intencion);
    }

}
