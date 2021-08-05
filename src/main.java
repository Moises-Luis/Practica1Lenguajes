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
        setSize(500,500);
        LaminaAnalizador miLamina = new LaminaAnalizador();
        add(miLamina);
        setVisible(true);
    }

}
class LaminaAnalizador extends JPanel{
    JTextArea areaTexto, areaPalabrasListadas;
    JScrollPane laminaTexto, laminaPalabrasListadas;
    JButton boton;
    public LaminaAnalizador(){
        setLayout(new BorderLayout());
        JPanel laminaSuperior = new JPanel();
        JPanel laminaCentral = new JPanel();
        add(laminaCentral, BorderLayout.CENTER);
        add(laminaSuperior, BorderLayout.NORTH);

        areaTexto = new JTextArea(6,18);
        areaTexto.setLineWrap(false);
        laminaTexto = new JScrollPane(areaTexto);
        laminaSuperior.add(laminaTexto);

        boton = new JButton("Verificar");
        boton.addActionListener(new Oyente());
        laminaSuperior.add(boton);

        areaPalabrasListadas = new JTextArea(9,22);
        areaPalabrasListadas.setLineWrap(false);
        laminaPalabrasListadas = new JScrollPane(areaPalabrasListadas);
        laminaCentral.add(laminaPalabrasListadas);
    }

    String cadenaRestante;
    String cadenaCortada;

    public void obtenerPalabra(String cadena){

        int posicion = 0;
        int tamañoCadena = cadena.length();

        for (int i = 0; i<cadena.length(); i++){
            posicion = cadena.indexOf(" ");
            if (posicion != -1){
                cadenaCortada = cadena.substring(0,posicion);
                cadenaRestante = cadena.substring(posicion, tamañoCadena);
                break;
            }else if (posicion == -1){
                cadenaCortada = cadena.substring(0, cadena.length());
                cadenaRestante = "";
            }
        }
    }
    public int obtenerNumPalabras(String cadena){
        String auxCadena, aux2Cadena;
        int posicion = 0;
        int tamañoCadena = cadena.length();
        int contador = 0;


        posicion = cadena.indexOf(" ");
        contador++;
        auxCadena = cadena;
        auxCadena = cadena.substring(posicion,cadena.length());
        auxCadena.trim();

        while (posicion != -1) {
            System.out.println(auxCadena);
            auxCadena = auxCadena.trim();
            posicion = auxCadena.indexOf(" ");
            if (posicion == -1)break;
            contador++;
            aux2Cadena = auxCadena.substring(posicion, auxCadena.length());
            System.out.println(aux2Cadena);
            auxCadena = aux2Cadena;
        }
        return contador+1;
    }

    String palabra;
    String palabraCompleta;

    public String getUnCaracterDeUnaPalabra(){
        String aux, caracter = "";
        aux = palabra;
        //System.out.println(aux.length());
        if (aux.length() > 0)
            caracter = aux.substring(0,1);
        if (palabra.length()>0)
            palabra = palabra.substring(1, palabra.length());
            //System.out.println("Palabra restante:"+palabra);
           // System.out.println("Caracter obtenido:"+caracter.toUpperCase());
            return caracter;
    }
    public Identificadores getComparacionDelCaracter(int posFinal, String lexema, String caracter){
        String numEnum, nomEnumAux2;

        for (int i = 1; i <= posFinal; i++){
            numEnum = String.valueOf(i);
            nomEnumAux2 = lexema+numEnum;

            Identificadores id = Enum.valueOf(Identificadores.class, nomEnumAux2);
            //System.out.println(getUnCaracterDeUnaPalabra() +"========"+id.getCaracter());

            if (caracter.equals(String.valueOf(id.getCaracter()))){
//                System.out.println("el caracter es: "+id.getCaracter());
//                System.out.println("y el tipo de Identificador es: "+id.getTipoIdentificador());
                return id;

            }
        }
        return null;
    }

    public void getPalabraIdentificada(){
        int cantCaracteres = palabra.length();
        Identificadores identificadorAnterior = null, identificador;
        String cadenaCaracteres = "";
        int iterador = 0;
        boolean isId = false, isNumEntero = false,isNumDecimal = false, isSimbolo = false, isError = false;

        boolean primerDigitoEsNumero = false, puntoDespNumero=false;
        boolean yaEntro = false;
        for (int i = 0; i < cantCaracteres; i++) {
            //
            String caracter = getUnCaracterDeUnaPalabra().toUpperCase();

            identificador = getComparacionDelCaracter(27,"IDENTIFICADOR", caracter);

            if (identificador == null){
                identificador = getComparacionDelCaracter(10, "DIGITO", caracter);
                System.out.println("Es un digito");
            }
            if (identificador == null) {
                identificador = getComparacionDelCaracter(5, "SIMBOLO", caracter);
            }

                if (identificador != null && iterador == 0){
                    cadenaCaracteres += identificador.getCaracter();
                    identificadorAnterior = identificador;
                    iterador ++;
                    System.out.println("Si entra al primer if :V");
                    if (identificador.getTipoIdentificador() == "NÚMERO"){
                        primerDigitoEsNumero = true;
                        System.out.println("El primer digito es un número");
                    }

                }else if (identificadorAnterior.getTipoIdentificador() == identificador.getTipoIdentificador()){
                    cadenaCaracteres += identificador.getCaracter();

                    /*
                    if (primerDigitoEsNumero==true && String.valueOf(identificador.getCaracter())== ".") {
                        puntoDespNumero = true;
                    }
                    */

                }else{
                    String expresionCompleta="", nombreDelIdentificador="";

                    if (primerDigitoEsNumero==false && identificadorAnterior.getTipoIdentificador()=="IDENTIFICADOR" && identificador.getTipoIdentificador()=="NÚMERO"){
                        //expresionCompleta = "Identificador:   "+cadenaCaracteres+"\n";
                        cadenaCaracteres += identificador.getCaracter();
                        nombreDelIdentificador = "Identificador";
                        yaEntro=true;

                    }else if (identificador.getTipoIdentificador()=="SIMBOLO" && primerDigitoEsNumero==false){
                        expresionCompleta = "Simbolo/Caracter: "+cadenaCaracteres+"\n";
                        areaPalabrasListadas.append(expresionCompleta);
                        cadenaCaracteres = "";
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior = identificador;
                        puntoDespNumero = false;
                        primerDigitoEsNumero = false;

                    }else if (primerDigitoEsNumero == true || identificadorAnterior.getTipoIdentificador()=="SIMBOLO" && identificador.getTipoIdentificador()=="NÚMERO"){
                        nombreDelIdentificador = "Número Entero: ";
                        cadenaCaracteres += identificador.getCaracter();
                        System.out.println("SIIIIII ENTROOOOOOO");
                        yaEntro = true;

                    }else if (primerDigitoEsNumero == true && String.valueOf(identificadorAnterior.getCaracter()) == "." && identificador.getTipoIdentificador()=="NÚMERO"){
                        nombreDelIdentificador = "Número decimal: ";
                        cadenaCaracteres += identificador.getCaracter();
                        yaEntro = true;

                    }else if (primerDigitoEsNumero == true && String.valueOf(identificadorAnterior.getCaracter()) == "." && identificador.getTipoIdentificador()!= "NÚMERO"){
                        expresionCompleta = "ERROR:   "+cadenaCaracteres+"\n";
                        areaPalabrasListadas.append(expresionCompleta);
                    }else if(yaEntro==true){
                        System.out.println("TTTTTTTTAMBIENN ETNRROOOOO");
                        expresionCompleta = nombreDelIdentificador +cadenaCaracteres+"\n";
                        areaPalabrasListadas.append(expresionCompleta);
                        cadenaCaracteres = "";
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior = identificador;
                        puntoDespNumero = false;
                        primerDigitoEsNumero = false;
                        yaEntro= false;
                    }
                }
        }
        if (identificadorAnterior != null) {
            String expresionCompleta = ">>>>>>>»»»" + ":   " + cadenaCaracteres + "\n";
            areaPalabrasListadas.append(expresionCompleta);
        }
    }


    public void identificarNumeroDecimal(){

    }
    public void identifiicarCaracter(){}

    private class Oyente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String auxCadena="", aux2CadenaResto = "", cadena;
            int posicion = 0;
            cadena = areaTexto.getText().replaceAll("\n", " ");
            do {
                cadena = cadena.trim();
                posicion = cadena.indexOf(" ");
                if (posicion == -1)break;
                auxCadena = cadena.substring(0, posicion);
                System.out.println(auxCadena); //////////////////////////////////////////
                palabra = auxCadena.trim();
                getPalabraIdentificada();

                aux2CadenaResto = cadena.substring(posicion, cadena.length());
                cadena = aux2CadenaResto.trim();

                auxCadena.trim();

            }while (posicion != -1);
            System.out.println(aux2CadenaResto.trim());///////////////////
            palabra = aux2CadenaResto.trim();
            getPalabraIdentificada();

            /*
            while (posicion != -1) {
                System.out.println(auxCadena);
                auxCadena = auxCadena.trim();
                posicion = auxCadena.indexOf(" ");
                if (posicion == -1)break;
                aux2CadenaResto = auxCadena.substring(posicion, auxCadena.length());
                System.out.println(aux2CadenaResto);
                auxCadena = aux2CadenaResto;
            }*/














//            String aux = areaTexto.getText().replaceAll("\n", "");
//            palabra = aux.trim();
//            palabraCompleta = areaTexto.getText();
//            System.out.println(getPalabraIdentificada());

            /*
            String aux = areaTexto.getText().replaceAll("\n"," ");

            obtenerPalabra(aux.trim());
            System.out.println(cadenaCortada);
            obtenerPalabra(cadenaRestante.trim());
            System.out.println(cadenaCortada);
            obtenerPalabra(cadenaRestante.trim());
            System.out.println(cadenaCortada);
            obtenerPalabra(cadenaRestante.trim());
            System.out.println(cadenaCortada);

             */
        }
    }
}

/*
|| primerDigitoEsNumero==true && String.valueOf(identificador.getCaracter())=="."
                || puntoDespNumero== true && identificador.getTipoIdentificador() == "NÚMERO" || primerDigitoEsNumero==false && identificadorAnterior.getTipoIdentificador()=="IDENTIFICADOR"
                 || primerDigitoEsNumero==false && identificador.getTipoIdentificador()=="IDENTIFICADOR" || primerDigitoEsNumero == true && identificador.getTipoIdentificador()=="IDENTIFICADOR"

 */