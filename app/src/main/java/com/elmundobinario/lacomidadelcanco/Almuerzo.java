package com.elmundobinario.lacomidadelcanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Almuerzo extends AppCompatActivity {

    TextView preguntaIngrediente;
    //File cereales_txt = new File("cereales.txt");
    //File legumbres_txt = new File("legumbres.txt");
    //File hortalizas_tuberculos_txt = new File("hortalizas_tuberculos.txt");
    //File condimento_hidratos_txt = new File("condimento_hidratos.txt");
    // ahora con SharedPreferences:
    SharedPreferences cerealesSharedPreferences = getSharedPreferences("cereales", Context.MODE_PRIVATE);
    SharedPreferences legumbresSharedPreferences = getSharedPreferences("legumbres", Context.MODE_PRIVATE);
    SharedPreferences hortalizas_tuberculosSharedPreferences = getSharedPreferences("hortalizas_tuberculos", Context.MODE_PRIVATE);
    SharedPreferences condimento_hidratosSharedPreferences = getSharedPreferences("condimento_hidratos", Context.MODE_PRIVATE);

    Button respuestaSi;
    Button respuestaNo;
    //Aquí cargará provisionalmente el array de cereales:
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
    String hortalia_tuberculoElegido;
    String condimento_hidratosElegido;
    int ordenBloqueAlimento = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almuerzo);

        preguntaIngrediente = findViewById(R.id.ingrediente);

        //Si no es la primera vez, existirá el fichero CEREALES y habrá que cargar el array desde fichero:
        if (cereales_txt.exists()) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("cereales.txt")));
                for (int i = 0; i < 8; i++) {
                    cereales[i] = br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } //fin de la carga del array cereales

        //Si no es la primera vez, existirá el fichero LEGUMBRES y habrá que cargar el array desde fichero:
        if (legumbres_txt.exists()) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("legumbres.txt")));
                for (int i = 0; i < 9; i++) {
                    legumbres[i] = br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } //fin de la carga del array legumbres

        //Si no es la primera vez, existirá el fichero HORTALIZAS_TUBERCULOS y habrá que cargar el array desde fichero:
        if (hortalizas_tuberculos_txt.exists()) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("hortalizas_tuberculos.txt")));
                for (int i = 0; i < 14; i++) {
                    hortalizas_tuberculos[i] = br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } //fin de la carga del array hortalizas_tuberculos

        //Si no es la primera vez, existirá el fichero CONDIMENTOS_HIDRATOS y habrá que cargar el array desde fichero:
        if (condimento_hidratos_txt.exists()) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("condimento_hidratos.txt")));
                for (int i = 0; i < 7; i++) {
                    condimento_hidratos[i] = br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } //fin de la carga del array condimentos_hidratos.

        // primera vez que muestra ingrediente preguntado. Las demás veces en onClicks...
        if (ordenBloqueAlimento == 1) {
            preguntaIngrediente.setText(cereales[cerealPreguntado] + "?");
        }

    }

    public void clickSi(View view) {
        // 1 = cereales
        if (ordenBloqueAlimento == 1) {
            cerealElegido = cereales[cerealPreguntado];
            RotarArrayAlimento();
            ordenBloqueAlimento++;
            preguntaIngrediente.setText(legumbres[legumbrePreguntada] + "?");
            cerealPreguntado = 0;
        }
        // 2 = legumbres:
        else if (ordenBloqueAlimento == 2) {
            legumbreElegida = legumbres[legumbrePreguntada];
            RotarArrayAlimento();
            ordenBloqueAlimento++;
            preguntaIngrediente.setText(hortalizas_tuberculos[hortaliza_tuberculoPreguntado] + "?");
            legumbrePreguntada = 0;
        }
        // 3 = hortalizas_tuberculos:
        else if (ordenBloqueAlimento == 3) {
            hortalia_tuberculoElegido = hortalizas_tuberculos[hortaliza_tuberculoPreguntado];
            RotarArrayAlimento();
            ordenBloqueAlimento++;
            preguntaIngrediente.setText(condimento_hidratos[condimento_hidratosPreguntado] + "?");
        }
        // 4 = condimento_hidratos:
        else if (ordenBloqueAlimento == 4) {
            condimento_hidratosElegido = condimento_hidratos[condimento_hidratosPreguntado];
            RotarArrayAlimento();
            // Aqui ya se tienen todos los alimentos
            // AQUI VA EL DÓDIGO PARA GUARDAR DATOS DE ARRAYS EN ARCHIVOS
            guardarArrayCereales();
            guardarArrayLegumbres();
            guardarArrayHortalizaTuberculo();
            guardarArrayCondimentoHidratos();
            condimento_hidratosPreguntado = 0;

        }
    }

    public void clickNo(View view) {
    }

    private void RotarArrayAlimento() {
        // ROTAR CEREALES:
        if (ordenBloqueAlimento == 1) {
            String reordenandoCereales[] = new String[8];
            reordenandoCereales[7] = cereales[cerealPreguntado];
            if (cerealPreguntado == 0) {
                for (int i = 0; i == 7; i++) {
                    reordenandoCereales[i] = cereales[i + 1];
                }
            } else {
                for (int i = 0; i < cerealPreguntado; i++) {
                    reordenandoCereales[i] = cereales[i - 1];
                }
                for (int i = cerealPreguntado; i == 7; i++) {
                    reordenandoCereales[i] = cereales[i + 1];
                }
            }
            for (int i = 0; i < 8; i++) {
                cereales[i] = reordenandoCereales[i];
            }

        }
        //ROTAR LEGUMBRES:
        else if (ordenBloqueAlimento == 2) {
            String reordenandoLegumbres[] = new String[9];
            reordenandoLegumbres[8] = legumbres[legumbrePreguntada];
            if (legumbrePreguntada == 0) {
                for (int i = 0; i == 8; i++) {
                    reordenandoLegumbres[i] = legumbres[i + 1];
                }
            } else {
                for (int i = 0; i < legumbrePreguntada; i++) {
                    reordenandoLegumbres[i] = legumbres[i - 1];
                }
                //aqui la 'i' llega hasta un indice menos del total, donde está la elegida en reordenando:
                for (int i = legumbrePreguntada; i == 8; i++) {
                    reordenandoLegumbres[i] = legumbres[i + 1];
                }
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 9; i++) {
                legumbres[i] = reordenandoLegumbres[i];
            }

        }
        //ROTAR HORTALIZAS_TUBERCULOS:
        else if (ordenBloqueAlimento == 3) {
            String reordenandoHortalizas_Tuberculos[] = new String[14];
            reordenandoHortalizas_Tuberculos[13] = hortalizas_tuberculos[hortaliza_tuberculoPreguntado];
            if (hortaliza_tuberculoPreguntado == 0) {
                for (int i = 0; i == 13; i++) {
                    reordenandoHortalizas_Tuberculos[i] = hortalizas_tuberculos[i + 1];
                }
            } else {
                for (int i = 0; i < hortaliza_tuberculoPreguntado; i++) {
                    reordenandoHortalizas_Tuberculos[i] = hortalizas_tuberculos[i - 1];
                }
                //aqui la 'i' llega hasta un indice menos del total, donde está la elegida en reordenando:
                for (int i = hortaliza_tuberculoPreguntado; i == 13; i++) {
                    reordenandoHortalizas_Tuberculos[i] = hortalizas_tuberculos[i + 1];
                }
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 14; i++) {
                hortalizas_tuberculos[i] = reordenandoHortalizas_Tuberculos[i];
            }

        }
        //ROTAR CONDIMENTO_HIDRATOS:
        else if (ordenBloqueAlimento == 4) {
            String reordenandoCondimento_Hidratos[] = new String[7];
            reordenandoCondimento_Hidratos[6] = condimento_hidratos[condimento_hidratosPreguntado];
            if (condimento_hidratosPreguntado == 0) {
                for (int i = 0; i == 6; i++) {
                    reordenandoCondimento_Hidratos[i] = condimento_hidratos[i + 1];
                }
            } else {
                for (int i = 0; i < condimento_hidratosPreguntado; i++) {
                    reordenandoCondimento_Hidratos[i] = condimento_hidratos[i - 1];
                }
                //aqui la 'i' llega hasta un indice menos del total, donde está la elegida en reordenando:
                for (int i = condimento_hidratosPreguntado; i == 6; i++) {
                    reordenandoCondimento_Hidratos[i] = condimento_hidratos[i + 1];
                }
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 7; i++) {
                condimento_hidratos[i] = reordenandoCondimento_Hidratos[i];
            }

        }

    }

    private void guardarArrayCereales() {
        try {
            if (!cereales_txt.exists()) {
                cereales_txt.createNewFile();
            }
            PrintWriter bw = new PrintWriter(new BufferedWriter(new FileWriter("cereales.txt")));
            for (int i = 0; i < 8; i++) {
                bw.write(cereales[i]);
            }
            bw.close();
        } catch (IOException e) {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
    }

    private void guardarArrayLegumbres() {
    }

    private void guardarArrayHortalizaTuberculo() {
    }

    private void guardarArrayCondimentoHidratos() {
    }
}
