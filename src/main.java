import javax.swing.*;
import java.awt.*;

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
        laminaSuperior.add(boton);

        areaPalabrasListadas = new JTextArea(9,22);
        areaPalabrasListadas.setLineWrap(false);
        laminaPalabrasListadas = new JScrollPane(areaPalabrasListadas);
        laminaCentral.add(laminaPalabrasListadas);
    }
}

enum Identificadores{
    SIMBOLO1('[',"SIMBOLO"),
    SIMBOLO2(']',"SIMBOLO"),
    SIMBOLO3(';',"SIMBOLO"),
    SIMBOLO4(',',"SIMBOLO"),
    SIMBOLO5('.',"SIMBOLO"),
    DIGITO('0',"NÚMERO"),
    DIGITO1('1',"NÚMERO"),
    DIGITO2('2',"NÚMERO"),
    DIGITO3('3',"NÚMERO"),
    DIGITO4('4',"NÚMERO"),
    DIGITO5('5',"NÚMERO"),
    DIGITO6('6',"NÚMERO"),
    DIGITO7('7',"NÚMERO"),
    DIGITO8('8',"NÚMERO"),
    DIGITO9('9',"NÚMERO");

    private Identificadores(char caracter, String tipoIdentificador){
        this.caracter = caracter;
        this.tipoIdentificador = tipoIdentificador;
    }

    public char getCaracter(){
        return caracter;
    }
    public String getTipoIdentificador(){
        return tipoIdentificador;
    }
    char caracter;
    String tipoIdentificador;
}