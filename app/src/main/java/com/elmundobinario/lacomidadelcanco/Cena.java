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
    String pescadoElegido = "";
    String condimentoCenaElegido;
    String alimentoEspecificoElegidoDeProteinas;
    int ordenBloqueAlimento = 1;
    String confirmaEleccionTipoProteina;

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
        // 1 = verduras:
        if (ordenBloqueAlimento == 1) {
            verduraElegida = ordenDeVerduras[verduraPreguntada];
            rotarArrayAlimento();
            ordenBloqueAlimento++;
            preguntaEleccionTipoDeProteina();
            verduraPreguntada = 0;
        }
        // 2 = tipo de proteina:
        else if (ordenBloqueAlimento == 2) {
            if (confirmaEleccionTipoProteina == "huevo") {
                tipoDeProteinaElegida = "huevo";
                rotarArrayAlimento();
                ordenBloqueAlimento++;
                preguntaIngrediente.setText(ordenDeCondimentosCena[condimentoCenaPreguntado] + "?");
                tipoDeProteinaPreguntada = 0;
            } else if (confirmaEleccionTipoProteina == "pescado") {
                pescadoElegido = ordenDePescados[pescadoPreguntado];
                tipoDeProteinaElegida = "pescado";
                rotarArrayAlimento(); // la siguiente vez tocaria champiñones...etc
                ordenBloqueAlimento++;
                preguntaIngrediente.setText(ordenDeCondimentosCena[condimentoCenaPreguntado] + "?");
                tipoDeProteinaPreguntada = 0;
            } else if (confirmaEleccionTipoProteina == "champiñones") {
                tipoDeProteinaElegida = "champiñones";
                rotarArrayAlimento();
                ordenBloqueAlimento++;
                preguntaIngrediente.setText(ordenDeCondimentosCena[condimentoCenaPreguntado] + "?");
                tipoDeProteinaPreguntada = 0;
            } else if (confirmaEleccionTipoProteina == "pollo") {
                tipoDeProteinaElegida = "pollo";
                rotarArrayAlimento();
                ordenBloqueAlimento++;
                preguntaIngrediente.setText(ordenDeCondimentosCena[condimentoCenaPreguntado] + "?");
                tipoDeProteinaPreguntada = 0;
            }

        }
        // 3 = condimento cena:
        else if (ordenBloqueAlimento == 3) {
            condimentoCenaElegido = ordenDeCondimentosCena[condimentoCenaPreguntado];
            rotarArrayAlimento();
            // Aqui ya se tienen todos los alimentos
            // AQUI VA EL DÓDIGO PARA GUARDAR DATOS DE ARRAYS EN ARCHIVOS
            guardarOrdenDeVerduras();
            guardarOrdenDeTipoDeProteinas();
            guardarOrdenDePescados();
            guardarOrdenDeCondimentosCena();
            condimentoCenaPreguntado = 0;
            lanzaActivityMenuFinal();
        }
    }

    public void clickNo_Cena(View view) {
        if (ordenBloqueAlimento == 1) { // si es una verdura:
            verduraPreguntada++;
            if (verduraPreguntada >= 13) { // ya no hay más verduras, pasa a proteinas...
                ordenBloqueAlimento++;
                preguntaEleccionTipoDeProteina();
                verduraPreguntada = 0;
            } else {
                preguntaIngrediente.setText(ordenDeVerduras[verduraPreguntada] + "?");
            }
        } else if (ordenBloqueAlimento == 2) { // si es proteina:
            if (confirmaEleccionTipoProteina != "pescado") {
                tipoDeProteinaPreguntada++;
            } else {
                pescadoPreguntado++;
                if (pescadoPreguntado >= 9) { // ya no hay más pescados en la lista...
                    tipoDeProteinaPreguntada++;
                } else {
                    preguntaIngrediente.setText(ordenDePescados[pescadoPreguntado] + "?");
                }
            }
            if (tipoDeProteinaPreguntada >= 4) { // ya no hay más proteinas... pasa a condimentos:
                ordenBloqueAlimento++;
                preguntaIngrediente.setText(ordenDeCondimentosCena[condimentoCenaPreguntado] + "?");
                tipoDeProteinaPreguntada = 0;
                tipoDeProteinaElegida = "";
            } else { // si aun quedan tipos de proteinas diferentes de pescado por preguntar:
                preguntaIngrediente.setText(ordenDeTipoDeProteinas[tipoDeProteinaPreguntada] + "?");
            }
        } else if (ordenBloqueAlimento == 3) { // si es un condimento:
            condimentoCenaPreguntado++;
            if (condimentoCenaPreguntado >= 8) { // ya no hay más condimentos
                // Aqui ya no hay más alimentos por preguntar...
                // aqui se guardarán los datos de los arrays a los SharedPreferences:
                guardarOrdenDeVerduras();
                guardarOrdenDeTipoDeProteinas();
                guardarOrdenDePescados();
                guardarOrdenDeCondimentosCena();
                condimentoCenaPreguntado = 0;
                condimentoCenaElegido = "";
                lanzaActivityMenuFinal();
            } else {
                preguntaIngrediente.setText(ordenDeCondimentosCena[condimentoCenaPreguntado] + "?");
            }
        }
    }

    private void preguntaEleccionTipoDeProteina() {
        // aqui habrá que elegir el tipo que toca y preguntarlo en el TextView...
        if (ordenDeTipoDeProteinas[0] == "huevo") {
            preguntaIngrediente.setText("huevo?");
            confirmaEleccionTipoProteina = "huevo";
        } else if (ordenDeTipoDeProteinas[0] == "pescado") {
            preguntaIngrediente.setText(ordenDePescados[0] + "?");
            confirmaEleccionTipoProteina = "pescado"; // aqui en clickSi hay que llamar a un metodo
            //para elegir el pescado...
        } else if (ordenDeTipoDeProteinas[0] == "champiñones") {
            preguntaIngrediente.setText("champiñones?");
            confirmaEleccionTipoProteina = "champiñones";
        } else if (ordenDeTipoDeProteinas[0] == "pollo") {
            preguntaIngrediente.setText("pollo?");
            confirmaEleccionTipoProteina = "pollo";
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
            reordenandoTipoDeProteinas[3] = ordenDeTipoDeProteinas[tipoDeProteinaPreguntada];
            int siguienteTipoDeProteinaPreguntadaEnIf = 0;
            for (int i = 0; i < 3; i++) {
                if (i == tipoDeProteinaPreguntada) siguienteTipoDeProteinaPreguntadaEnIf++;
                reordenandoTipoDeProteinas[i] = ordenDeTipoDeProteinas[siguienteTipoDeProteinaPreguntadaEnIf];
                siguienteTipoDeProteinaPreguntadaEnIf++;
            }
            // Pasamos el array de seguridad al array original, actualizando así el array:
            for (int i = 0; i < 4; i++) {
                ordenDeTipoDeProteinas[i] = reordenandoTipoDeProteinas[i];
            }
            //aqui dentro tambien se ordenara el array de PESCADOS si es el caso de ser elegido...
            if (confirmaEleccionTipoProteina == "pescado") {
                String reordenandoPescados[] = new String[9];
                reordenandoPescados[8] = ordenDePescados[pescadoPreguntado];
                int siguientePescadoPreguntadoEnIf = 0;
                for (int i = 0; i < 8; i++) {
                    if (i == pescadoPreguntado) siguientePescadoPreguntadoEnIf++;
                    reordenandoPescados[i] = ordenDePescados[siguientePescadoPreguntadoEnIf];
                    siguientePescadoPreguntadoEnIf++;
                }
            }
        }
        // ROTAR CONDIMENTO CENA:
        else if (ordenBloqueAlimento == 3) {
            String reordenandoCondimentosCena[] = new String[8];
            reordenandoCondimentosCena[7] = ordenDeCondimentosCena[condimentoCenaPreguntado];
            int siguienteCondimentoCenaPreguntadoEnIf = 0;
            for (int i = 0; i < 7; i++) {
                if (i == condimentoCenaPreguntado) siguienteCondimentoCenaPreguntadoEnIf++;
                reordenandoCondimentosCena[i] = ordenDeCondimentosCena[siguienteCondimentoCenaPreguntadoEnIf];
                siguienteCondimentoCenaPreguntadoEnIf++;
            }
        }

    }

    private void guardarOrdenDeVerduras() {
        for (int i = 0; i < 13; i++) {
            sharedPrefEditorVerdurasCenaYTipoProteina.putString(String.valueOf(i), ordenDeVerduras[i]);
            sharedPrefEditorVerdurasCenaYTipoProteina.commit();
        }
    }

    private void guardarOrdenDeTipoDeProteinas() { // OJO aqui la key es el nombre y el valor es el indice de array
        for (int i = 0; i < 4; i++) {
            if (ordenDeTipoDeProteinas[i] == "huevo") {
                sharedPrefEditorVerdurasCenaYTipoProteina.putString("huevo", String.valueOf(i));
                sharedPrefEditorVerdurasCenaYTipoProteina.commit();
            } else if (ordenDeTipoDeProteinas[i] == "pescado") {
                sharedPrefEditorVerdurasCenaYTipoProteina.putString("pescado", String.valueOf(i));
                sharedPrefEditorVerdurasCenaYTipoProteina.commit();
            } else if (ordenDeTipoDeProteinas[i] == "champiñones") {
                sharedPrefEditorVerdurasCenaYTipoProteina.putString("champiñones", String.valueOf(i));
                sharedPrefEditorVerdurasCenaYTipoProteina.commit();
            } else { // ya solo queda el pollo, es 1 de 4... por eso no es else if.
                sharedPrefEditorVerdurasCenaYTipoProteina.putString("pollo", String.valueOf(i));
                sharedPrefEditorVerdurasCenaYTipoProteina.commit();
            }
        }
    }

    private void guardarOrdenDePescados() {
        for (int i = 0; i < 9; i++) {
            sharedPrefEditorPescados.putString(String.valueOf(i), ordenDePescados[i]);
            sharedPrefEditorPescados.commit();
        }
    }

    private void guardarOrdenDeCondimentosCena() {
        for (int i = 0; i < 8; i++) {
            sharedPrefEditorCondimentosCena.putString(String.valueOf(i), ordenDeCondimentosCena[i]);
            sharedPrefEditorCondimentosCena.commit();
        }
    }

    private void lanzaActivityMenuFinal() {
        if (pescadoElegido != "") { // necesario para saber si fue pescado o cualquier otro alimento...
            alimentoEspecificoElegidoDeProteinas = pescadoElegido;
        } else {
            alimentoEspecificoElegidoDeProteinas=tipoDeProteinaElegida;
        }
        Intent intencion = new Intent(this, MenuFinal.class);
        intencion.putExtra("verduraElegidaExtra",verduraElegida);
        intencion.putExtra("alimentoProteinas",alimentoEspecificoElegidoDeProteinas);
        intencion.putExtra("condimentoCena",condimentoCenaElegido);
    }

}
