import jdk.swing.interop.SwingInterOpUtils;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class main {
    public static void main(String args[]){
        MarcoAnalizador miMarco = new MarcoAnalizador();
        miMarco.setLocationRelativeTo(null);
        miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class MarcoAnalizador extends JFrame{
    public MarcoAnalizador(){
        setSize(500,350);
        LaminaAnalizador miLamina = new LaminaAnalizador();
        add(miLamina);
        setVisible(true);
    }

}
class LaminaAnalizador extends JPanel {
    JTextArea areaTexto, areaPalabrasListadas;
    JScrollPane laminaTexto, laminaPalabrasListadas;
    JButton boton;


    public LaminaAnalizador() {
        setLayout(new BorderLayout());
        JPanel laminaSuperior = new JPanel();
        JPanel laminaCentral = new JPanel();
        add(laminaCentral, BorderLayout.CENTER);
        add(laminaSuperior, BorderLayout.NORTH);

        areaTexto = new JTextArea(9, 22);
        areaTexto.setLineWrap(false);
        laminaTexto = new JScrollPane(areaTexto);
        laminaSuperior.add(laminaTexto);

        boton = new JButton("Verificar");
        boton.addActionListener(new Oyente());
        laminaSuperior.add(boton);



        areaPalabrasListadas = new JTextArea(9, 22);
        areaPalabrasListadas.setLineWrap(false);
        laminaPalabrasListadas = new JScrollPane(areaPalabrasListadas);
        laminaCentral.add(laminaPalabrasListadas);
    }

    String cadenaRestante;
    String cadenaCortada;

    public void obtenerPalabra(String cadena) {

        int posicion = 0;
        int tamañoCadena = cadena.length();

        for (int i = 0; i < cadena.length(); i++) {
            posicion = cadena.indexOf(" ");
            if (posicion != -1) {
                cadenaCortada = cadena.substring(0, posicion);
                cadenaRestante = cadena.substring(posicion, tamañoCadena);
                break;
            } else if (posicion == -1) {
                cadenaCortada = cadena.substring(0, cadena.length());
                cadenaRestante = "";
            }
        }
    }

    public int obtenerNumPalabras(String cadena) {
        String auxCadena, aux2Cadena;
        int posicion = 0;
        int tamañoCadena = cadena.length();
        int contador = 0;


        posicion = cadena.indexOf(" ");
        contador++;
        auxCadena = cadena;
        auxCadena = cadena.substring(posicion, cadena.length());
        auxCadena.trim();

        while (posicion != -1) {
            System.out.println(auxCadena);
            auxCadena = auxCadena.trim();
            posicion = auxCadena.indexOf(" ");
            if (posicion == -1) break;
            contador++;
            aux2Cadena = auxCadena.substring(posicion, auxCadena.length());
            System.out.println(aux2Cadena);
            auxCadena = aux2Cadena;
        }
        return contador + 1;
    }

    String palabra;
    String palabraCompleta;

    public String getUnCaracterDeUnaPalabra() {
        String aux, caracter = "";
        aux = palabra;
        if (aux.length() > 0)
            caracter = aux.substring(0, 1);
        if (palabra.length() > 0)
            palabra = palabra.substring(1, palabra.length());
        return caracter;
    }

    public Identificadores getComparacionDelCaracter(int posFinal, String lexema, String caracter) {
        String numEnum, nomEnumAux2;

        for (int i = 1; i <= posFinal; i++) {
            numEnum = String.valueOf(i);
            nomEnumAux2 = lexema + numEnum;

            Identificadores id = Enum.valueOf(Identificadores.class, nomEnumAux2);
            if (caracter.equals(String.valueOf(id.getCaracter()))) {
                return id;
            }
        }
        return null;
    }

    public void getPalabraIdentificada() {
        int cantCaracteres = palabra.length();
        Identificadores identificadorAnterior = null, identificador;
        String cadenaCaracteres = "";
        int iterador = 0;
        boolean isId = false, isNumEntero = false, isNumDecimal = false, isSimbolo = false, isError = false;

        boolean primerDigitoEsNumero = false, primerDigitoEsID = false, primerDigitoEsSimbolo= false;
        boolean pasoAca = false;
        String nombreIdentificadorDePaso = "";
        boolean numYPuntoAntes= false;
        boolean esDecimal = false;
        boolean imprimirDecimal =false;
        int cambio = 0;
        boolean esEntero =false;
        boolean esSimbolo=false;
        int iteradorTemporal = 0;
        boolean yaPaso = false;
        boolean ahoraEsLetra = false;
        for (int i = 0; i < cantCaracteres; i++) {
            yaPaso=false;
            //
            String caracter = getUnCaracterDeUnaPalabra().toUpperCase();
            System.out.println(caracter);

            identificador = getComparacionDelCaracter(27, "IDENTIFICADOR", caracter);

            if (identificador == null) {
                identificador = getComparacionDelCaracter(10, "DIGITO", caracter);
            }
            if (identificador == null) {
                identificador = getComparacionDelCaracter(5, "SIMBOLO", caracter);
            }
                if (identificador != null && iterador == 0) { //Con esto sabre si es un digito o un identificador
                    cadenaCaracteres += identificador.getCaracter();
                    identificadorAnterior = identificador;
                    System.out.println("caracter trasladado al anterior: "+identificadorAnterior.getCaracter());
                    iterador++;

                    if (identificador.getTipoIdentificador() == "NÚMERO") primerDigitoEsNumero = true;
                    if (identificador.getTipoIdentificador() == "IDENTIFICADOR") primerDigitoEsID = true;
                    System.out.println("--->--->--->--->--->--->--->--->"+ primerDigitoEsID);

                } else if (identificador != null && identificadorAnterior.getTipoIdentificador() == identificador.getTipoIdentificador()) {
                    //concatenamos si el caracter anterior es igual al actual
                    cadenaCaracteres += identificador.getCaracter();

                } else if(identificador== null){
                    if (identificadorAnterior!=null){
                        if (identificadorAnterior.getTipoIdentificador() == "NÚMERO") {
                            if (nombreIdentificadorDePaso!="")
                            areaPalabrasListadas.append(nombreIdentificadorDePaso+"   " + cadenaCaracteres + '\n');
                            areaPalabrasListadas.append("NÚMERO_ENTERO: " + cadenaCaracteres + '\n');
                        }
                        if (identificadorAnterior.getTipoIdentificador() == "SIMBOLO") {
                            if (nombreIdentificadorDePaso!="")
                            areaPalabrasListadas.append(nombreIdentificadorDePaso +"   "+ cadenaCaracteres + '\n');
                            areaPalabrasListadas.append("SIMBOLO: " + cadenaCaracteres + '\n');
                        }
                        if (identificadorAnterior.getTipoIdentificador() == "IDENTIFICADOR") {
                            if (nombreIdentificadorDePaso!="")
                            areaPalabrasListadas.append(nombreIdentificadorDePaso +"   "+ cadenaCaracteres + '\n');
                            areaPalabrasListadas.append("IDENTIFICADOR: " + cadenaCaracteres + '\n');
                        }
                    }
                    areaPalabrasListadas.append("Error caracter inválido: "+caracter +"\n");
                    identificadorAnterior=identificador;
                    cadenaCaracteres="";
                    iterador=0;
                } else {
                    iteradorTemporal++;
                    System.out.println("veces que entra :V : "+iteradorTemporal);

                    //verificación para el numero decimal

                    if (primerDigitoEsNumero && identificadorAnterior.getTipoIdentificador()=="NÚMERO" && identificador.getCaracter()=='.'){
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior=identificador;
                        esDecimal=true;
                        System.out.println("ahora es true");
                    }
                    if (primerDigitoEsNumero && identificadorAnterior.getCaracter()=='.'){
                        if (identificador.getTipoIdentificador()=="NÚMERO"){
                            cadenaCaracteres+=identificador.getCaracter();
                            identificadorAnterior=identificador;
                            esDecimal = true;
                            nombreIdentificadorDePaso="NÚMERO_DECIMAL: ";
                        }else if (identificador.getCaracter()!='.'){
                            System.out.println("Entro al error no se porque razon");
                            areaPalabrasListadas.append("Error: "+ cadenaCaracteres+"\n");
                            cadenaCaracteres= String.valueOf(identificador.getCaracter());
                            identificadorAnterior=identificador;
                            esDecimal=false;
                            iterador=0;
                            yaPaso=true;
                        }
                    }else if(primerDigitoEsNumero && identificadorAnterior.getCaracter()=='.'){
                        esDecimal=false;
                        System.out.println("ahora si es falso");
                    }
                    if (esDecimal){// 45.5--2    45.555--2
                        cambio++;
                        if (cambio==3){
                            imprimirDecimal = true;
                        }
                    }

                    if (esDecimal==true && imprimirDecimal){
                        areaPalabrasListadas.append("NÚMERO_DECIMAL: inicio:  "+ cadenaCaracteres+"\n");
                        cadenaCaracteres = String.valueOf(identificador.getCaracter());
                        identificadorAnterior=identificador;
                        esDecimal=false;
                        imprimirDecimal=false;
                        cambio=0;
                        yaPaso=true;
                        iterador=0;
                        System.out.println(cadenaCaracteres);

                    }
                    //verificación para el numero entero
                    if (primerDigitoEsNumero && identificadorAnterior.getTipoIdentificador()=="NÚMERO" && esDecimal==false && yaPaso==false){
                        areaPalabrasListadas.append("NÚMERO_ENTERO: "+ cadenaCaracteres+"\n");
                        System.out.println(identificador.getCaracter());
                        System.out.println("estamos en numeros enteros");
                        cadenaCaracteres = String.valueOf(identificador.getCaracter());
                        identificadorAnterior=identificador;
                        primerDigitoEsNumero=false;
                        primerDigitoEsID=true;

                        //iterador=0;
                        yaPaso = true;
                    }
                    //verificacion LETRAS
                    //    asdf456  abc123def123

                    if (primerDigitoEsID && identificadorAnterior.getTipoIdentificador()=="IDENTIFICADOR" && yaPaso==false || yaPaso==false && primerDigitoEsID && identificadorAnterior.getTipoIdentificador()=="NÚMERO"){
                        if (identificador.getTipoIdentificador()=="IDENTIFICADOR" || identificador.getTipoIdentificador()=="NÚMERO"){
                            cadenaCaracteres += identificador.getCaracter();
                            identificadorAnterior=identificador;
                            nombreIdentificadorDePaso="IDENTIFICADOR: ";
                        }else if(identificador.getTipoIdentificador()=="SIMBOLO"){
                            areaPalabrasListadas.append("IDENTIFICADOR: "+ cadenaCaracteres+"\n");
                            cadenaCaracteres = String.valueOf(identificador.getCaracter());
                            identificadorAnterior=identificador;
                            //iterador=0;
                            yaPaso= true;
                            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>WTF>>>>  "+identificador.getCaracter());
                            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>WTF>>>>  "+cadenaCaracteres);
                        }
                    }

                    //verificacion para los SIMBOLOS

                    if (identificadorAnterior.getTipoIdentificador()=="SIMBOLO" && esDecimal == false && yaPaso==false){
                        areaPalabrasListadas.append("SIMBOLO(S): "+ cadenaCaracteres+"\n");
                        cadenaCaracteres = String.valueOf(identificador.getCaracter());
                        identificadorAnterior=identificador;
                        System.out.println("dentro de simbolo(s) "+ cadenaCaracteres);
                        nombreIdentificadorDePaso="SIMBOLO(S): ";
                       // iterador=0;
                        yaPaso=true;
                    }else if (identificador.getTipoIdentificador()=="SIMBOLO" && esDecimal == false && yaPaso==false){
                        areaPalabrasListadas.append("SIMBOLO(S): "+ cadenaCaracteres+"\n");
                        cadenaCaracteres = "";
                        identificadorAnterior=identificador;
                        nombreIdentificadorDePaso="SIMBOLOS(S): ";
                        //iterador=0;
                        yaPaso=true;
                    }
                }
            if(identificador==null){
                System.out.println("EEEEEEEEERRRRRRRRRRRRRROOOOOOOOOOOOOOOORRRRRRRR");
            }else if (identificadorAnterior != null && i==cantCaracteres-1) {
                System.out.println("identificadores: iniciial= "+identificador.getCaracter() +" anterior= "+identificadorAnterior.getCaracter());
                //verificación para el numero entero
                if (primerDigitoEsID == false && primerDigitoEsNumero && identificadorAnterior.getTipoIdentificador()=="NÚMERO" && esDecimal==false ){
                    areaPalabrasListadas.append("NÚMERO_ENTERO del final :V : "+ cadenaCaracteres+"\n");
                    cadenaCaracteres = String.valueOf(identificador.getCaracter());
                    identificadorAnterior=identificador;
                   // nombreIdentificadorDePaso="NÚMERO_ENTERO: ";
                    iterador=0;
                }else if (primerDigitoEsNumero && esDecimal==true){
                    areaPalabrasListadas.append("NÚMERO_DECIMAL: inicio:  "+ cadenaCaracteres+"\n");
                    cadenaCaracteres = String.valueOf(identificador.getCaracter());
                    identificadorAnterior=identificador;
                    esDecimal=false;
                    imprimirDecimal=false;
                    cambio=0;
                    iterador=0;
                   // nombreIdentificadorDePaso="NÚMERO_DECIMAL: ";
                } else if(primerDigitoEsID && identificadorAnterior.getTipoIdentificador()=="NÚMERO" || primerDigitoEsID && identificadorAnterior.getTipoIdentificador()=="IDENTIFICADOR"){
                    System.out.println("finaaaaaaaaaaaaaallllllllll::::::::::::::::   "+identificador.getCaracter()+" anterior: "+identificadorAnterior.getCaracter());
                         areaPalabrasListadas.append("IDENTIFICADOR final: "+ cadenaCaracteres+"\n");
                         cadenaCaracteres = String.valueOf(identificador.getCaracter());
                         identificadorAnterior=identificador;
                         iterador=0;
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  "+identificador.getCaracter());
                    //nombreIdentificadorDePaso="IDENTIFICADOR: ";
                     }
                else if (identificador.getTipoIdentificador()=="SIMBOLO" && esDecimal==false){
                    areaPalabrasListadas.append("SIMBOLO(S) final: "+ cadenaCaracteres+"\n");
                    cadenaCaracteres = "";
                    identificadorAnterior=identificador;
                    iterador=0;
                    //nombreIdentificadorDePaso="SIMBOLO(S): ";
                    // adf,456
                }
/*                if (pasoAca==true) {
                    areaPalabrasListadas.append(nombreIdentificadorDePaso + ": " + cadenaCaracteres + "\n");
                    System.out.println(">>>>>>>>>>>>  "+nombreIdentificadorDePaso+"  <<<<<<<<<<<<");
                    System.out.println(">>>>>>>>>>>>  6  <<<<<<<<<<<<");
                    pasoAca=false;
                }*/
            }
        }

    }
        private class Oyente implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                areaPalabrasListadas.setText("");
                String auxCadena = "", aux2CadenaResto = "", cadena;
                int posicion = 0;
                cadena = areaTexto.getText().replaceAll("\n", " ");
                do {
                    cadena = cadena.trim();
                    posicion = cadena.indexOf(" ");
                    //if (posicion == -1) break;
                    auxCadena = cadena.substring(0, posicion+1);
                    System.out.println(auxCadena);
                    palabra = auxCadena.trim();
                    getPalabraIdentificada();

                    aux2CadenaResto = cadena.substring(posicion+1, cadena.length());
                    cadena = aux2CadenaResto.trim();

                    auxCadena.trim();
                    //if (posicion == -1) break;
                } while (posicion != -1);
                System.out.println(aux2CadenaResto.trim());
                palabra = aux2CadenaResto.trim();
                getPalabraIdentificada();

        }
    }
}