package com.elmundobinario.lacomidadelcanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Cena extends AppCompatActivity {

    TextView preguntaIngrediente;

    // Base de datos con SharedPreferences:
    SharedPreferences verdurasCenaSharedPref;
    SharedPreferences tipoProteinaSharedPref;
    SharedPreferences pescadosSharedPref;
    SharedPreferences condimentosCenaSharedPref;
    SharedPreferences.Editor verdurasCenaShPrEditor;
    SharedPreferences.Editor tipoProteinaShPrEditor;
    SharedPreferences.Editor pescadosShPrEditor;
    SharedPreferences.Editor condimentoCenaShPrEditor;


    //Aquí cargarán provisionalmente los arrays:
    String ordenDeVerduras[] = {"chukrut", "judías verdes", "cardo", "espárragos verdes", "coles de Bruselas", "apio",
            "hinojo", "borraja", "ajetes (brotes de ajo)", "tallos de acelgas", "aceitunas", "brécol", "grelos"};
    String ordenDeTipoDeProteinas[] = {"huevo", "pescado", "champiñones", "pollo"};
    String ordenDePescados[] = {"arenques", "trucha", "bonito", "sardinas", "jureles", "caballa", "atún", "salmón",
            "boquerones"};
    String ordenDeCondimentosCena[] = {"vino blanco", "albahaca", "levadura de cerveza", "cayena", "orégano",
            "tomillo", "curry", "canela"};

    int verduraPreguntada = 0;
    int tipoProteinaPreguntada = 0;
    int pescadoPreguntado = 0;
    int condimentoCenaPreguntado = 0;

    String verduraElegida;
    String proteinaElegida;
    String condimentoCenaElegido;
    // para mandarla en putExtra con el intent.
    int ordenBloqueAlimento = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cena);

        preguntaIngrediente = findViewById(R.id.ingrediente_cena_textview);

        // inicia objetos SharedPreferences:
        // en este primer objeto las verduras tienen key en indice y en valor el nombre del alimento
        // en tipoProteina es a la inversa...
        verdurasCenaSharedPref = getSharedPreferences("verduras_cena", Context.MODE_PRIVATE);
        tipoProteinaSharedPref = getSharedPreferences("tipo_proteina", Context.MODE_PRIVATE);
        pescadosSharedPref = getSharedPreferences("pescados", Context.MODE_PRIVATE);
        condimentosCenaSharedPref = getSharedPreferences("condimentos_cena", Context.MODE_PRIVATE);

        verdurasCenaShPrEditor = verdurasCenaSharedPref.edit();
        tipoProteinaShPrEditor = tipoProteinaSharedPref.edit();
        pescadosShPrEditor = pescadosSharedPref.edit();
        condimentoCenaShPrEditor = condimentosCenaSharedPref.edit();

        // Carga del array ordenDeVerduras si no es la primera vez... :
        if (verdurasCenaSharedPref.contains("0")) {
            for (int i = 0; i < 13; i++) {
                ordenDeVerduras[i] = verdurasCenaSharedPref.getString(String.valueOf(i), "");
            }
        }
        // Carga del array ordenDeTipoDeProteinas si no es la primera vez... :
        if (tipoProteinaSharedPref.contains("0")) {
            for (int i = 0; i < 4; i++) {
                ordenDeTipoDeProteinas[i] = tipoProteinaSharedPref.getString(String.valueOf(i), "");
            }
        }
        // Carga del array ordenDePescados si no es la primera vez... :
        if (pescadosSharedPref.contains("0")) {
            for (int i = 0; i < 9; i++) {
                ordenDePescados[i] = pescadosSharedPref.getString(String.valueOf(i), "");
            }
        }
        // Carga del array ordenDeCondimentosCena si no es la primera vez... :
        if (condimentosCenaSharedPref.contains(String.valueOf(0))) {
            for (int i = 0; i < 8; i++) {
                ordenDeCondimentosCena[i] = condimentosCenaSharedPref.getString(String.valueOf(i), "");
            }
        }

        // primera vez que muestra ingrediente preguntado. Las demás veces en onClicks...
        if (ordenBloqueAlimento == 1) {
            preguntaIngrediente.setText(ordenDeVerduras[verduraPreguntada] + "?");
        }

    }

    public void clickSi_Cena(View view) {
        // 1 = VERDURAS:
        if (ordenBloqueAlimento == 1) {
            verduraElegida = ordenDeVerduras[verduraPreguntada];
            rotarArrayAlimento();
            ordenBloqueAlimento++;
            if (ordenDeTipoDeProteinas[tipoProteinaPreguntada].equals("pescado")) {
                preguntaIngrediente.setText(ordenDePescados[pescadoPreguntado]);
            } else {
                preguntaIngrediente.setText(ordenDeTipoDeProteinas[tipoProteinaPreguntada]);
            }
            verduraPreguntada = 0;
        }
        // 2 = tipo de PROTEINA:
        else if (ordenBloqueAlimento == 2) {
            if (ordenDeTipoDeProteinas[tipoProteinaPreguntada].equals("pescado")) {
                proteinaElegida = ordenDePescados[pescadoPreguntado]; // este será el String que se
                //muestre en MenuFinal
            } else {
                proteinaElegida = ordenDeTipoDeProteinas[tipoProteinaPreguntada];
            }
            rotarArrayAlimento();
            ordenBloqueAlimento++;
            preguntaIngrediente.setText(ordenDeCondimentosCena[condimentoCenaPreguntado]);
            tipoProteinaPreguntada = 0;
        }
        // 3 = condimento cena:
        else if (ordenBloqueAlimento == 3) {
            condimentoCenaElegido = ordenDeCondimentosCena[condimentoCenaPreguntado];
            rotarArrayAlimento();
            // Aqui ya se tienen todos los alimentos
            // AQUI VA EL DÓDIGO PARA GUARDAR DATOS DE ARRAYS EN ARCHIVOS
            guardarOrdenVerduras();
            guardarOrdenTipoProteinas();
            guardarOrdenPescados();
            guardarOrdenCondimentosCena();
            condimentoCenaPreguntado = 0;
            lanzaActivityMenuFinal();
        }
    }

    public void clickNo_Cena(View view) {
        // si es una VERDURA:
        if (ordenBloqueAlimento == 1) {
            verduraPreguntada++;
            if (verduraPreguntada >= 13) { // ya no hay más verduras, pasa a proteinas...
                ordenBloqueAlimento++;
                if (ordenDeTipoDeProteinas[tipoProteinaPreguntada].equals("pescado")) {
                    if (pescadoPreguntado >= 9) { // ya no hay más pescados en la lista...
                        tipoProteinaPreguntada++; // se pteguntará el siguiente tipo de pescado...
                    } else {
                        preguntaIngrediente.setText(ordenDePescados[pescadoPreguntado] + "?");
                    }
                } else { // solo pregunta el siguiente tipoProteina, y pasa al bloque 2 de proteinas...
                    preguntaIngrediente.setText(ordenDeTipoDeProteinas[tipoProteinaPreguntada] + "?");
                }
                verduraPreguntada = 0;
            } else { // Si aun quedan verduras la preguntara:
                preguntaIngrediente.setText(ordenDeVerduras[verduraPreguntada] + "?");
            }
        }
        // si es un tipo de PROTEINA:
        else if (ordenBloqueAlimento == 2) {
            if (ordenDeTipoDeProteinas[tipoProteinaPreguntada].equals("pescado")) {
                if (pescadoPreguntado >= 9) { // ya no hay más pescados en la lista...
                    tipoProteinaPreguntada++; // se pteguntará el siguiente tipo de pescado...
                } else {
                    preguntaIngrediente.setText(ordenDePescados[pescadoPreguntado] + "?");
                }
            } else {
                if (tipoProteinaPreguntada >= 4) { // Si ya no hay maś tipos de proteinas por preguntar:
                    ordenBloqueAlimento++;
                    preguntaIngrediente.setText(ordenDeCondimentosCena[condimentoCenaPreguntado] + "?");
                } // aun quedan tipos de proteinas por preguntar:
                preguntaIngrediente.setText(ordenDeTipoDeProteinas[tipoProteinaPreguntada] + "?");
            }
        }
        // si es un CONDIMENTO:
        else if (ordenBloqueAlimento == 3) {
            condimentoCenaPreguntado++;
            if (condimentoCenaPreguntado >= 8) { // ya no hay más condimentos
                // Aqui ya no hay más alimentos por preguntar...
                // aqui se guardarán los datos de los arrays a los SharedPreferences:
                guardarOrdenVerduras();
                guardarOrdenTipoProteinas();
                guardarOrdenPescados();
                guardarOrdenCondimentosCena();
                condimentoCenaPreguntado = 0;
                condimentoCenaElegido = "";
                lanzaActivityMenuFinal();
            } else {
                preguntaIngrediente.setText(ordenDeCondimentosCena[condimentoCenaPreguntado] + "?");
            }
        }
    }

    private void rotarArrayAlimento() {
        // ROTAR VERDURAS:
        if (ordenBloqueAlimento == 1) {
            String reordenandoVerduras[] = new String[13];
            reordenandoVerduras[12] = ordenDeVerduras[verduraPreguntada];
            int siguienteVerduraPreguntadaEnIf = 0;
            for (int i = 0; i < 12; i++) {
                if (i == verduraPreguntada)
                    siguienteVerduraPreguntadaEnIf++;// salta el indice vacio
                // del indice que se mueve al final (ultimo indice del array)
                reordenandoVerduras[i] = ordenDeVerduras[siguienteVerduraPreguntadaEnIf];
                siguienteVerduraPreguntadaEnIf++;
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 13; i++) {
                ordenDeVerduras[i] = reordenandoVerduras[i];
            }
        }
        // ROTAR TIPO DE PROTEINAS y pescados...
        else if (ordenBloqueAlimento == 2) {
            String reordenandoTipoDeProteinas[] = new String[4];
            reordenandoTipoDeProteinas[3] = ordenDeTipoDeProteinas[tipoProteinaPreguntada];
            int siguienteTipoProteinaPreguntadaEnIf = 0;
            for (int i = 0; i < 3; i++) {
                if (i == tipoProteinaPreguntada) siguienteTipoProteinaPreguntadaEnIf++;
                reordenandoTipoDeProteinas[i] = ordenDeTipoDeProteinas[siguienteTipoProteinaPreguntadaEnIf];
                siguienteTipoProteinaPreguntadaEnIf++;
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 4; i++) {
                ordenDeTipoDeProteinas[i] = reordenandoTipoDeProteinas[i];
            }
            //aqui dentro tambien se ordenara el array de PESCADOS si es el caso de ser elegido...
            if (ordenDeTipoDeProteinas[tipoProteinaPreguntada].equals("pescado")) {
                String reordenandoPescados[] = new String[9];
                reordenandoPescados[8] = ordenDePescados[pescadoPreguntado];
                int siguientePescadoPreguntadoEnIf = 0;
                for (int i = 0; i < 8; i++) {
                    if (i == pescadoPreguntado) siguientePescadoPreguntadoEnIf++;
                    reordenandoPescados[i] = ordenDePescados[siguientePescadoPreguntadoEnIf];
                    siguientePescadoPreguntadoEnIf++;
                }
                // Pasamos el array de seguridad al array original, actualizando así el array:
                for (int i = 0; i < 9; i++) {
                    ordenDePescados[i] = reordenandoPescados[i];
                }
            }
        }
        // ROTAR CONDIMENTO CENA:
        else {
            String reordenandoCondimentosCena[] = new String[8];
            // se pone en ultimo lugar el condimento elegido:
            reordenandoCondimentosCena[7] = ordenDeCondimentosCena[condimentoCenaPreguntado];
            int siguienteCondimentoCenaPreguntadoEnIf = 0; // (variable para saltar el indice vacio por el
            // indice elegido y puesto el ultimo)
            for (int i = 0; i < 7; i++) {
                if (i == condimentoCenaPreguntado) siguienteCondimentoCenaPreguntadoEnIf++;
                reordenandoCondimentosCena[i] = ordenDeCondimentosCena[siguienteCondimentoCenaPreguntadoEnIf];
                siguienteCondimentoCenaPreguntadoEnIf++;
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 8; i++) {
                ordenDeCondimentosCena[i] = reordenandoCondimentosCena[i];
            }
        }

    }

    private void guardarOrdenVerduras() {
        for (int i = 0; i < 13; i++) {
            verdurasCenaShPrEditor.putString(String.valueOf(i), ordenDeVerduras[i]);
            verdurasCenaShPrEditor.commit();
        }
    }

    private void guardarOrdenTipoProteinas() {
        for (int i = 0; i < 4; i++) {
            tipoProteinaShPrEditor.putString(String.valueOf(i), ordenDeTipoDeProteinas[i]);
            tipoProteinaShPrEditor.commit();
        }
    }

    private void guardarOrdenPescados() {
        for (int i = 0; i < 9; i++) {
            pescadosShPrEditor.putString(String.valueOf(i), ordenDePescados[i]);
            pescadosShPrEditor.commit();
        }
    }

    private void guardarOrdenCondimentosCena() {
        for (int i = 0; i < 8; i++) {
            condimentoCenaShPrEditor.putString(String.valueOf(i), ordenDeCondimentosCena[i]);
            condimentoCenaShPrEditor.commit();
        }
    }

    private void lanzaActivityMenuFinal() {
        Intent intencion = new Intent(this, MenuFinal.class);
        intencion.putExtra("verduraElegidaExtra", verduraElegida);
        intencion.putExtra("alimentoProteinas", proteinaElegida);
        intencion.putExtra("condimentoCena", condimentoCenaElegido);
        startActivity(intencion);
    }

}
