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
    String palabra;
    String palabraCompleta;

    public String getUnCaracterDeUnaPalabra(){
        String aux, caracter = "";
        aux = palabra;
        System.out.println(aux.length());
        if (aux.length() > 0)
            caracter = aux.substring(0,1);
        if (palabra.length()>0)
            palabra = palabra.substring(1, palabra.length());
            //System.out.println("Palabra restante:"+palabra);
           // System.out.println("Caracter obtenido:"+caracter.toUpperCase());
            return caracter;
    }
    public boolean getComparacionDelCaracter(int posFinal, String lexema){
        String numEnum, nomEnumAux2;
        String caracter = getUnCaracterDeUnaPalabra().toUpperCase();

        for (int i = 1; i <= posFinal; i++){
            numEnum = String.valueOf(i);
            nomEnumAux2 = lexema+numEnum;

            Identificadores id = Enum.valueOf(Identificadores.class, nomEnumAux2);
            //System.out.println(getUnCaracterDeUnaPalabra() +"========"+id.getCaracter());

            if (caracter.equals(String.valueOf(id.getCaracter()))){
                System.out.println("Y el caracter es: "+id.getCaracter());
                System.out.println("Y el tipo de Identificador es: "+id.getTipoIdentificador());
                return true;

            }
        }
        return false;
    }
    public void leer_caracter_a_caracter(){
        boolean seEncontro = false;
        //colocar el booleano para el caracter que sea incorrecto :V

        getComparacionDelCaracter(5,"SIMBOLO");
        getComparacionDelCaracter(10,"DIGITO");
        getComparacionDelCaracter(27,"IDENTIFICADOR");

    }

    public void identificarPalabra(){
        int cantCaracteres = palabra.length();
        int primerCaracterId, finalCaracterId;
//        char caracter;
//        boolean isIdentificador;
        boolean primerCaracterEncontrado;
        String palabraId = "";
        int iterador = 0;
        for (int i = 0; i < cantCaracteres; i++) {
            primerCaracterEncontrado = getComparacionDelCaracter(27,"IDENTIFICADOR");
           // System.out.println(primerCaracterEncontrado);

            if (primerCaracterEncontrado == true){
                if (iterador == 0)
                primerCaracterId = palabraCompleta.length()-(palabra.length()+1);
                else
                iterador++;

            }else if (primerCaracterEncontrado == false){
                palabraId = palabraCompleta.substring(0,iterador);
                iterador = 0;
                System.out.println(palabraId+" 'Es un IDENTIFICADOR' ");
            }

        }
    }
    public void identificarNumeroDecimal(){}
    public void identifiicarCaracter(){}

    private class Oyente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            palabra = areaTexto.getText();
//            leer_caracter_a_caracter();
            palabra = areaTexto.getText();
            palabraCompleta = areaTexto.getText();
            identificarPalabra();

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

