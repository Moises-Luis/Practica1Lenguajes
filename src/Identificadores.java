public enum Identificadores {
        SIMBOLO1('[',"SIMBOLO"),
        SIMBOLO2(']',"SIMBOLO"),
        SIMBOLO3(';',"SIMBOLO"),
        SIMBOLO4(',',"SIMBOLO"),
        SIMBOLO5('.',"SIMBOLO"),
        SIMBOLO6('{',"SIMBOLO"),
        SIMBOLO7('}',"SIMBOLO"),
        DIGITO1('0',"NÚMERO"),
        DIGITO2('1',"NÚMERO"),
        DIGITO3('2',"NÚMERO"),
        DIGITO4('3',"NÚMERO"),
        DIGITO5('4',"NÚMERO"),
        DIGITO6('5',"NÚMERO"),
        DIGITO7('6',"NÚMERO"),
        DIGITO8('7',"NÚMERO"),
        DIGITO9('8',"NÚMERO"),
        DIGITO10('9',"NÚMERO"),
        IDENTIFICADOR1('A',"IDENTIFICADOR"),
        IDENTIFICADOR2('B',"IDENTIFICADOR"),
        IDENTIFICADOR3('C',"IDENTIFICADOR"),
        IDENTIFICADOR4('D',"IDENTIFICADOR"),
        IDENTIFICADOR5('E',"IDENTIFICADOR"),
        IDENTIFICADOR6('F',"IDENTIFICADOR"),
        IDENTIFICADOR7('G',"IDENTIFICADOR"),
        IDENTIFICADOR8('H',"IDENTIFICADOR"),
        IDENTIFICADOR9('I',"IDENTIFICADOR"),
        IDENTIFICADOR10('J',"IDENTIFICADOR"),
        IDENTIFICADOR11('K',"IDENTIFICADOR"),
        IDENTIFICADOR12('L',"IDENTIFICADOR"),
        IDENTIFICADOR13('M',"IDENTIFICADOR"),
        IDENTIFICADOR14('N',"IDENTIFICADOR"),
        IDENTIFICADOR15('Ñ',"IDENTIFICADOR"),
        IDENTIFICADOR16('O',"IDENTIFICADOR"),
        IDENTIFICADOR17('P',"IDENTIFICADOR"),
        IDENTIFICADOR18('Q',"IDENTIFICADOR"),
        IDENTIFICADOR19('R',"IDENTIFICADOR"),
        IDENTIFICADOR20('S',"IDENTIFICADOR"),
        IDENTIFICADOR21('T',"IDENTIFICADOR"),
        IDENTIFICADOR22('U',"IDENTIFICADOR"),
        IDENTIFICADOR23('V',"IDENTIFICADOR"),
        IDENTIFICADOR24('W',"IDENTIFICADOR"),
        IDENTIFICADOR25('X',"IDENTIFICADOR"),
        IDENTIFICADOR26('Y',"IDENTIFICADOR"),
        IDENTIFICADOR27('Z',"IDENTIFICADOR");

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
