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
        boolean esNumDecimal = false;

        boolean ahoraEsLetra = false;
        for (int i = 0; i < cantCaracteres; i++) {
            //
            String caracter = getUnCaracterDeUnaPalabra().toUpperCase();

            identificador = getComparacionDelCaracter(27, "IDENTIFICADOR", caracter);

            if (identificador == null) {
                identificador = getComparacionDelCaracter(10, "DIGITO", caracter);
            }
            if (identificador == null) {
                identificador = getComparacionDelCaracter(5, "SIMBOLO", caracter);

            }

                if (identificador != null && iterador == 0) {

                    cadenaCaracteres += identificador.getCaracter();
                    identificadorAnterior = identificador;
                    iterador++;

                    if (identificador.getTipoIdentificador() == "NÚMERO") primerDigitoEsNumero = true;
                    if (identificador.getTipoIdentificador() == "IDENTIFICADOR") primerDigitoEsID = true;


                } else if (identificador != null && identificadorAnterior.getTipoIdentificador() == identificador.getTipoIdentificador()) {
                    cadenaCaracteres += identificador.getCaracter();

                } else if(identificador== null){
                    areaPalabrasListadas.append("Error caracter inválido: "+caracter +"\n");
                }
                else {
                    if (esNumDecimal){
                        areaPalabrasListadas.append("NÚMERO DECIMAL: " + cadenaCaracteres+"\n");
                        System.out.println(">>>>>>>>>>>>  NUMERO DECIMAL  <<<<<<<<<<<<");
                        System.out.println(">>>>>>>>>>>>  1  <<<<<<<<<<<<");
                        cadenaCaracteres = "";
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior = identificador;
                        primerDigitoEsNumero = false;
                    }
                    else if (identificador.getTipoIdentificador()=="IDENTIFICADOR"){
                        ahoraEsLetra = true;
                        areaPalabrasListadas.append(identificadorAnterior.getTipoIdentificador() + ": " + cadenaCaracteres+"\n");
                        System.out.println(">>>>>>>>>>>>  "+identificadorAnterior.getTipoIdentificador()+"  <<<<<<<<<<<<");
                        System.out.println(">>>>>>>>>>>>  2  <<<<<<<<<<<<");
                        cadenaCaracteres = "";
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior = identificador;
                        primerDigitoEsNumero = false;

                    }else if(ahoraEsLetra==true && identificadorAnterior.getTipoIdentificador()== "IDENTIFICADOR" && identificador.getTipoIdentificador() == "NÚMERO" ||
                    identificadorAnterior.getTipoIdentificador() == "NÚMERO" && ahoraEsLetra== true){
                        cadenaCaracteres += identificador.getCaracter();
                        if (identificador.getTipoIdentificador()!= "NÚMERO") ahoraEsLetra = false;

                    }else if(primerDigitoEsID && identificadorAnterior.getTipoIdentificador()=="IDENTIFICADOR" && identificador.getTipoIdentificador()=="NÚMERO"){
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior= identificador;
                        nombreIdentificadorDePaso = "IDENTIFICADOR: ";
                        pasoAca = true;
                    }
                    else if(identificador.getCaracter()=='.'  && identificadorAnterior.getTipoIdentificador()=="NÚMERO" && primerDigitoEsNumero==true){
                        numYPuntoAntes = true;
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior = identificador;
                    }else if(numYPuntoAntes== true && primerDigitoEsNumero==true && identificadorAnterior.getCaracter()== '.' && identificador.getTipoIdentificador()=="NÚMERO"){
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior = identificador;
                        //nombreIdentificadorDePaso = "NÚMERO _DECIMAL: ";
                        numYPuntoAntes = false;
                        esNumDecimal = true;
                        //pasoAca = true;
                    }else if (identificador.getTipoIdentificador() == "SIMBOLO"){
                        areaPalabrasListadas.append(identificadorAnterior.getTipoIdentificador() + ": " + cadenaCaracteres+"\n");
                        System.out.println(">>>>>>>>>>>>  "+identificadorAnterior.getTipoIdentificador()+"  <<<<<<<<<<<<");
                        System.out.println(">>>>>>>>>>>>  3  <<<<<<<<<<<<");
                        cadenaCaracteres = "";
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior = identificador;
                    }
                    else{
                        if (pasoAca==true) {
                            areaPalabrasListadas.append(nombreIdentificadorDePaso + ": " + cadenaCaracteres+"\n");
                            System.out.println(">>>>>>>>>>>>  "+nombreIdentificadorDePaso+"  <<<<<<<<<<<<");
                            System.out.println(">>>>>>>>>>>>  4  <<<<<<<<<<<<");
                            pasoAca =false;
                        }
                        else areaPalabrasListadas.append(identificadorAnterior.getTipoIdentificador() + ": " + cadenaCaracteres+"\n");
                        System.out.println(">>>>>>>>>>>>  "+identificadorAnterior.getTipoIdentificador()+"  <<<<<<<<<<<<");
                        System.out.println(">>>>>>>>>>>>  5  <<<<<<<<<<<<");
                        cadenaCaracteres = "";
                        cadenaCaracteres += identificador.getCaracter();
                        identificadorAnterior = identificador;
                        primerDigitoEsNumero = false;
                    }
                }
        }
            if (identificadorAnterior != null) {
                if (pasoAca==true) {
                    areaPalabrasListadas.append(nombreIdentificadorDePaso + ": " + cadenaCaracteres + "\n");
                    System.out.println(">>>>>>>>>>>>  "+nombreIdentificadorDePaso+"  <<<<<<<<<<<<");
                    System.out.println(">>>>>>>>>>>>  6  <<<<<<<<<<<<");
                    pasoAca=false;
                }
                else areaPalabrasListadas.append(identificadorAnterior.getTipoIdentificador() + ": " + cadenaCaracteres+"\n");
                System.out.println(">>>>>>>>>>>>  "+identificadorAnterior.getTipoIdentificador()+"  <<<<<<<<<<<<");
                System.out.println(">>>>>>>>>>>>  7  <<<<<<<<<<<<");
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