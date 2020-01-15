package com.elmundobinario.lacomidadelcanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Cena extends AppCompatActivity {

    TextView preguntaIngrediente;

    // Base de datos con SharedPreferences:
    SharedPreferences verdurasCenaYTipoProteina;
    SharedPreferences pescados;
    SharedPreferences condimentosCena;
    SharedPreferences.Editor sharedPrefEditorVerdurasCenaYTipoProteina;
    SharedPreferences.Editor sharedPrefEditorPescados;
    SharedPreferences.Editor sharedPrefEditorCondimentosCena;

    //Aquí cargarán provisionalmente los arrays:
    String ordenDeVerduras[] = {"chukrut", "judías verdes", "cardo", "espárragos verdes", "coles de Bruselas", "apio",
            "hinojo", "borraja", "ajetes (brotes de ajo)", "tallos de acelgas", "aceitunas", "brécol", "grelos"};
    String ordenDeTipoDeProteinas[] = {"huevo", "pescado", "champiñones", "pollo"};
    String ordenDePescados[] = {"arenques", "trucha", "bonito", "sardinas", "jureles", "caballa", "atún", "salmón",
            "boquerones"};
    String ordenDeCondimentosCena[] = {"vino blanco", "albahaca", "levadura de cerveza", "cayena", "orégano",
            "tomillo", "curry", "canela"};
    int verduraPreguntada = 0;
    int tipoDeProteinaPreguntada = 0;
    int pescadoPreguntado = 0;
    int condimentoCenaPreguntado = 0;
    String verduraElegida;
    String tipoDeProteinaElegida;
    String pescadoElegido;
    String condimentoCenaElegido;
    int ordenBloqueAlimento = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cena);

        preguntaIngrediente = findViewById(R.id.ingrediente_cena_textview);

        // inicia objetos SharedPreferences:
        verdurasCenaYTipoProteina = getSharedPreferences("verduras_tipoproteinas", Context.MODE_PRIVATE);
        pescados = getSharedPreferences("pescados", Context.MODE_PRIVATE);
        condimentosCena = getSharedPreferences("condimento_cena", Context.MODE_PRIVATE);
        sharedPrefEditorVerdurasCenaYTipoProteina = verdurasCenaYTipoProteina.edit();
        sharedPrefEditorPescados = pescados.edit();
        sharedPrefEditorCondimentosCena = condimentosCena.edit();

        // Carga del array ordenDeVerduras si no es la primera vez... :
        if (verdurasCenaYTipoProteina.contains("0")) {
            for (int i = 0; i < 13; i++) {
                ordenDeVerduras[i] = verdurasCenaYTipoProteina.getString(String.valueOf(i), "");
            }
        }
        // Carga del array ordenDeTipoDeProteinas si no es la primera vez... :
        if (verdurasCenaYTipoProteina.contains("huevo")) {
            for (int i = 0; i < 4; i++) {
                if (verdurasCenaYTipoProteina.getString("huevo", "")
                        == String.valueOf(i)) {
                    ordenDeTipoDeProteinas[i] = "huevo";
                } else if (verdurasCenaYTipoProteina.getString("pescado", "")
                        == String.valueOf(i)) {
                    ordenDeTipoDeProteinas[i] = "pescado";
                } else if (verdurasCenaYTipoProteina.getString("champiñones", "")
                        == String.valueOf(i)) {
                    ordenDeTipoDeProteinas[i] = "champiñones";
                } else if (verdurasCenaYTipoProteina.getString("pollo", "")
                        == String.valueOf(i)) {
                    ordenDeTipoDeProteinas[i] = "pollo";

                }
            }
        }
        // Carga del array ordenDePescados si no es la primera vez... :
        if (pescados.contains("0")) {
            for (int i = 0; i < 9; i++) {
                ordenDePescados[i] = pescados.getString(String.valueOf(i), "");
            }
        }
        // Carga del array ordenDeCondimentosCena si no es la primera vez... :
        if (condimentosCena.contains("0")) {
            for (int i = 0; i < 8; i++) {
                ordenDeCondimentosCena[i] = condimentosCena.getString(String.valueOf(i), "");
            }
        }
        // primera vez que muestra ingrediente preguntado. Las demás veces en onClicks...
        if (ordenBloqueAlimento == 1) {
            preguntaIngrediente.setText(ordenDeVerduras[verduraPreguntada] + "?");
        }

    }

    public void clickSi_Cena(View view) {
    }

    public void clickNo_Cena(View view) {
    }
}
