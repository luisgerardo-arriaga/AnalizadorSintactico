/**
Luis Gerardo Arriaga Gutierrez
Jose Guadalupe Arevaloz Castañeda
Jassiel Emanuel Magaña Lopez
 */
package analizadorsintactico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AnalizadorSintactico {
        
        public static String[] TipoDatos = { "Int", "String", "Float", "Char", "Integer", "Double" };
        
        
        
        public static String[] Reservadas = { "If", "Else", "While", "For", "Break", "Main", "Public", "Void", "Static", "x1", "variable2",
                        "Private", "Import", "Package", "Class", "Int", "String", "Float", "Char", "Integer", "Double" };

        public static String[] Relacionales = { "<", ">", "<=", ">=", };
        public static String NombreArchivo, Archivo1[], Archivo2[][], Archivo3[], ArchivoInf[];
        public static int RenglonesArchivo, ContadorArchivo, RenglonesReglas;
        public static String ArchivoExcel[][], Reglas[];
        public static Stack<String> Pila = new Stack<String>();
        public static Queue<String> Entrada = new LinkedList<>();
        public static boolean EstadoAceptacion = false, EstadoError = false;

        public static void main(String[] args) throws IOException {
                NombreArchivo = "Archivo.txt";
                RenglonesArchivo = ContarRenglones(NombreArchivo);
                Archivo1 = LeerArchivo(NombreArchivo, RenglonesArchivo);
                Archivo2 = Do1x1(Archivo1, RenglonesArchivo);
                Archivo3 = DoPalabras(Archivo1, Archivo2, RenglonesArchivo);
                cleanArchivo(Archivo3, ContadorArchivo);
                ArchivoInf = convertirArreglo(Archivo3, ContadorArchivo);
                ArchivoExcel = LeerArchivoExcel("ArchivoExcel.txt");
                RenglonesReglas = ContarRenglones("Reglas.txt");
                Reglas = LeerArchivo("Reglas.txt", RenglonesReglas);

                for (int i = 0; i < ArchivoInf.length; i++) {
                        if (!ArchivoInf[i].equals("@")) {
                                Entrada.add(ArchivoInf[i]);
                        } else {
                                Entrada.add("23");
                        }
                }

                boolean doPush = true;
                String Salida = "";
                Pila.push("23");
                Pila.push("0");
                System.out.println("Pila\t\t\t\t\tEntrada\t\t\tSalida");
                do {
                        String AuxPila = "";
                        Salida = "";
                        if (!ArchivoExcel[Integer.parseInt(Pila.peek()) + 1][Integer.parseInt(Entrada.peek())]
                                        .equals("V")) {
                                Salida = ArchivoExcel[Integer.parseInt(Pila.peek()) + 1][Integer
                                                .parseInt(Entrada.peek())];
                                if (Salida.toUpperCase().charAt(0) == 'D') {
                                        if (Salida.length() > 2) {
                                                AuxPila = Character.toString(Salida.charAt(1))
                                                                + Character.toString(Salida.charAt(2));
                                        } else {
                                                AuxPila = Character.toString(Salida.charAt(1));
                                        }
                                        doPush = true;
                                }
                                else if (Salida.toUpperCase().charAt(0) == 'R') {
                                        if (Salida.toUpperCase().equals("R0")) {
                                                EstadoAceptacion = true;
                                                break;
                                        }
                                        String Aux, AuxSalida, TipoR = "";
                                        if (Salida.length() > 2) {
                                                AuxSalida = Character.toString(Salida.charAt(1))
                                                                + Character.toString(Salida.charAt(2));
                                        } else {
                                                AuxSalida = Character.toString(Salida.charAt(1));
                                        }
                                        Aux = Reglas[Integer.parseInt(AuxSalida)];
                                        boolean Bandera = false;
                                        for (int i = 0; i < Aux.length(); i++) {
                                                if (Aux.charAt(i) == '=') {
                                                        if (Bandera) {
                                                                break;
                                                        }
                                                        Bandera = true;
                                                } else if (Bandera) {
                                                        TipoR += Aux.charAt(i);
                                                }
                                        }
                                        if (Aux.toUpperCase().contains("DEF")) {
                                                System.out.println(Pila + "\t\t\t\t\t" + Entrada + "\t\t" + Salida);
                                                int ContadorElementos = 0;
                                                for (int i = 0; i < Aux.length(); i++) {
                                                        if (Aux.charAt(i) == '-') {
                                                                ContadorElementos++;
                                                        }
                                                }
                                                if (!Aux.contains("/e")) {
                                                        ContadorElementos++;
                                                }

                                                for (int i = 0; i < (ContadorElementos * 2); i++) {
                                                        Pila.pop();
                                                }

                                                String AuxPosicion = Pila.peek();
                                                Pila.push(String.valueOf(whereIs(TipoR)));
                                                if (!ArchivoExcel[Integer.parseInt(AuxPosicion) + 1][Integer
                                                                .parseInt(Pila.peek())].equals("V")) {
                                                        Pila.push(ArchivoExcel[Integer.parseInt(AuxPosicion)
                                                                        + 1][Integer.parseInt(Pila.peek())]);
                                                } else {
                                                        EstadoError = true;
                                                }

                                                if (!ArchivoExcel[Integer.parseInt(Pila.peek()) + 1][Integer
                                                                .parseInt(Entrada.peek())].equals("V")) {
                                                        Salida = ArchivoExcel[Integer.parseInt(Pila.peek()) + 1][Integer
                                                                        .parseInt(Entrada.peek())];
                                                        doPush = false;
                                                } else {
                                                        EstadoError = true;
                                                }
                                        }
                                        else {
                                                System.out.println(Pila + "\t\t\t\t\t" + Entrada + "\t\t" + Salida);
                                                String AuxPosicion = Pila.peek();
                                                Pila.push(String.valueOf(whereIs(TipoR)));
                                                if (!ArchivoExcel[Integer.parseInt(AuxPosicion) + 1][Integer
                                                                .parseInt(Pila.peek())].equals("V")) {
                                                        Pila.push(ArchivoExcel[Integer.parseInt(AuxPosicion)
                                                                        + 1][Integer.parseInt(Pila.peek())]);
                                                } else {
                                                        EstadoError = true;
                                                }
                                                if (!ArchivoExcel[Integer.parseInt(Pila.peek()) + 1][Integer
                                                                .parseInt(Entrada.peek())].equals("V")) {
                                                        Salida = ArchivoExcel[Integer.parseInt(Pila.peek()) + 1][Integer
                                                                        .parseInt(Entrada.peek())];
                                                        doPush = false;
                                                } else {
                                                        EstadoError = true;
                                                }

                                        }
                                }
                                if (doPush) {
                                        System.out.println(Pila + "\t\t\t\t\t" + Entrada + "\t\t" + Salida);
                                        Pila.push(Entrada.poll());
                                        Pila.push(AuxPila);
                                }
                        } else {
                                EstadoError = true;
                        }

                } while (!EstadoAceptacion && !EstadoError);

                if (EstadoAceptacion && !EstadoError) {
                        System.out.println(Pila + "\t\t\t\t\t" + Entrada + "\t\t" + Salida);
                        System.out.println("\n\nEstado de Aceptacion");
                } else if (!EstadoAceptacion && EstadoError) {
                        System.out.println("Estado de Error");
                }

        } // FIN DE MAIN

        /** MÉTODOS */

        /**
         * Método para buscar la posicion de un Elemento en el Archivo de Excel
         * 
         * @param Palabra Contiene la palabra que se buscará
         * @return retorna la posicion donde se encuentra
         */
        private static int whereIs(String Palabra) {
                int Posicion = 0;
                for (int i = 0; i < 46; i++) {
                        if (ArchivoExcel[0][i].toUpperCase().equals(Palabra.toUpperCase())) {
                                Posicion = i;
                        }
                }

                return Posicion;
        }

        /**
         * Método para validar si es un operador Relacional
         * 
         * @param Palabra contiene la palabra a validar
         * @return retorna un valor booleano para saber si es o no
         */
        private static boolean isRelacionales(String Palabra) {
                boolean Bandera = false;

                for (int i = 0; i < Relacionales.length; i++) {
                        if (Palabra.equals(Relacionales[i])) {
                                Bandera = true;
                                break;
                        }
                }

                return Bandera;
        }

        /**
         * Método para validar si es un número.
         * 
         * @param Palabra Contiene el dato a analizar.
         * @return retorna un valor boleano correspondiente a si es número o no.
         */
        private static boolean isNumber(String Palabra) {
                boolean Bandera = false;

                try {
                        int Numero = Integer.parseInt(Palabra);
                        Bandera = true;
                } catch (NumberFormatException e) {

                }

                return Bandera;
        }

        /**
         * Método para validar si es un Numero de tipo Entero
         * 
         * @param Palabra contiene la sentencia a validar
         * @return retorna un valor boleano correspondiente a si es Entero o no.
         */
        private static boolean isEntero(String Palabra) {
                boolean Bandera = false;
                if (isNumber(Palabra)) {
                        if (!Palabra.contains(".")) {
                                Bandera = true;
                        }
                }

                return Bandera;
        }

        /**
         * Método para validar si es un Numero de tipo Real
         * 
         * @param Palabra contiene la sentencia a validar
         * @return retorna un valor boleano correspondiente a si es Real o no.
         */
        private static boolean isReal(String Palabra) {
                boolean Bandera = false;

                if (isNumber(Palabra)) {
                        if (!isEntero(Palabra)) {
                                Bandera = true;
                        }
                }

                return Bandera;
        }

        /**
         * Método para validar si es una Cadena
         * 
         * @param Palabra contiene la sentencia a validar
         * @return retorna un valor boleano correspondiente a si es una Cadena o no.
         */
        private static boolean isCadena(String Palabra) {
                boolean Bandera = false;

                if (Palabra.length() > 2) {
                        if (Palabra.charAt(0) == 32 && Palabra.charAt(Palabra.length() - 1) == 32) {
                                Bandera = true;
                        }
                }

                return Bandera;
        }

        /**
         * Método para validar si es un tipo de Dato
         * 
         * @param Palabra contiene el Dato a analizar.
         * @return retorna un valor booleano correspondiente a si es o no un Tipo de
         *         Dato.
         */
        private static boolean isTipoDatos(String Palabra) {
                boolean Bandera = false;

                for (int i = 0; i < TipoDatos.length; i++) {
                        if (Palabra.toUpperCase().equals(TipoDatos[i].toUpperCase())) {
                                Bandera = true;
                                break;
                        }
                }

                return Bandera;
        }

        /**
         * Método para validar si es una Palabra Reservda
         * 
         * @param Palabra contiene el dato a analizar.
         * @return retorna un valor booleano correspondiente a si es o no una palabra
         *         Reservada.
         */
        private static boolean isReservada(String Palabra) {
                boolean Bandera = false;

                for (int i = 0; i < Reservadas.length; i++) {
                        if (Palabra.toUpperCase().equals(Reservadas[i].toUpperCase())) {
                                Bandera = true;
                        }
                }

                return Bandera;
        }

        /**
         * Método para validar si se trata de un Identificador
         * 
         * @param Palabra contiene la palabra a validar
         * @return Retorna un valor booleano para saber si e so no un Identificador
         */
        private static boolean isIdentificador(String Palabra) {
                boolean Bandera = false;
                boolean BanderaAux = true;

                for (int i = 0; i < Palabra.length(); i++) {
                        char AuxChar = Palabra.toUpperCase().charAt(i);
                        if ((AuxChar > 64 && AuxChar < 91) || (AuxChar > 47 && AuxChar < 58)) {

                        } else {
                                BanderaAux = false;
                        }
                }

                if (BanderaAux) {
                        if (!isReservada(Palabra.toUpperCase()) && !isNumber(Palabra)) {
                                Bandera = true;
                        }
                }

                return Bandera;
        }

        /**
         * Método para separar Dato por Daro
         * 
         * @param Archivo1         Contiene el archivo leido en renglon por renglon
         * @param Archivo2         Contiene el archivo leido en 1x1
         * @param RenglonesArchivo Contiene el valor int de la cantidad de renglones que
         *                         tiene
         * @return retorna un vector String con las palabras separadas.
         */
        private static String[] DoPalabras(String[] Archivo1, String[][] Archivo2, int RenglonesArchivo) {
                String Palabras[] = new String[RenglonesArchivo * 20];
                ContadorArchivo = 0;

                for (int i = 0; i < RenglonesArchivo; i++) {
                        String Aux = "";
                        for (int j = 0; j < Archivo1[i].length(); j++) {
                                char AuxChar = Archivo2[i][j].toUpperCase().charAt(0);
                                char AuxChar2 = 0;
                                if (j != Archivo1[i].length() - 1) {
                                        AuxChar2 = Archivo2[i][j + 1].toUpperCase().charAt(0);
                                }
                                if (Aux.equals("")) {
                                        if (AuxChar == AuxChar2 && (AuxChar == 124 || AuxChar == 38 || AuxChar == 61)) {
                                                Palabras[ContadorArchivo] = Archivo2[i][j] + Archivo2[i][j + 1];
                                                ContadorArchivo++;
                                                j++;
                                        } else if (AuxChar > 64 && AuxChar < 91 || AuxChar > 47 && AuxChar < 58) {
                                                Aux += Archivo2[i][j];
                                        } else {
                                                Palabras[ContadorArchivo] = Archivo2[i][j];
                                                ContadorArchivo++;
                                        }
                                } else if (!Aux.equals("")) {
                                        if (AuxChar == AuxChar2 && (AuxChar == 124 || AuxChar == 38 || AuxChar == 61)) {
                                                Palabras[ContadorArchivo] = Archivo2[i][j] + Archivo2[i][j + 1];
                                                ContadorArchivo++;
                                                j++;
                                        } else if (AuxChar > 64 && AuxChar < 91 || AuxChar > 47 && AuxChar < 58) {
                                                Aux += Archivo2[i][j];
                                        } else {
                                                Palabras[ContadorArchivo] = Aux;
                                                ContadorArchivo++;
                                                Aux = "";
                                                Palabras[ContadorArchivo] = Archivo2[i][j];
                                                ContadorArchivo++;
                                        }
                                }

                        }
                        Palabras[ContadorArchivo] = "@";
                        ContadorArchivo++;
                }

                return Palabras;
        }

        /**
         * Método para calcular el tamaño m[as grande necesario.
         * 
         * @param Array     Contiene todo el arreglo a calcular
         * @param Renglones Contiene el número de renglones que tiene el arreglo
         * @return retorna el valor del tamaño máximo necesario para los arreglos.
         */
        private static int HighSize(String Array[], int Renglones) {
                int Mayor = Array[0].length();
                for (int i = 0; i < Renglones; i++) {
                        if (Mayor < Array[i].length()) {
                                Mayor = Array[i].length();
                        }
                }
                return Mayor;
        }

        /**
         * Método para contar los renglones que tiene un archivo.
         * 
         * @param NombreArchivo Contiene el nombre del archivo a leer.
         * @return Retorna un valor int que contiene la cantidad de renglones del
         *         archivo leído.
         * @throws IOException Se coloca cuando se leerá algún archivo, en caso de que
         *                     haya algún error.
         */
        private static int ContarRenglones(String NombreArchivo) throws IOException {
                /** VARIABLES */
                String Cadena;
                int Renglones = 0;
                /** OBJETOS */
                FileReader Fr = new FileReader(NombreArchivo);
                BufferedReader Br = new BufferedReader(Fr);

                while ((Cadena = Br.readLine()) != null) {
                        Renglones++;
                }
                Br.close();

                return Renglones;
        }

        /**
         * Método para leer cualquier archivo necesario.
         * 
         * @param NombreArchivo Contiene el nombre del archivo a leer.
         * @param Renglones     Contiene el valor int que indica la cantidad de
         *                      renglones que tiene el archivo a leer.
         * @return retorna un arreglo que contiene el archivo guardado renglón por
         *         renglón.
         * @throws IOException Se coloca cuando se leerá algún archivo, en caso de que
         *                     haya algún error.
         */
        private static String[] LeerArchivo(String NombreArchivo, int Renglones) throws IOException {
                /** VARIABLES */
                String Cadena;
                int Count = 0;
                String Aux[] = new String[Renglones];
                /** OBJETOS */
                FileReader Fr = new FileReader(NombreArchivo);
                BufferedReader Br = new BufferedReader(Fr);

                while ((Cadena = Br.readLine()) != null) {
                        Aux[Count] = Cadena;
                        Count++;
                }
                Br.close();

                return Aux;
        }

        /**
         * Método para Leer el archivo de Excel
         * 
         * @param NombreArchivo Es el Nombre del archivo de Excel que leeremos.
         * @return Retorna un Arreglo con el contenido del Archivo de Excel leido.
         * @throws IOException Se coloca cuando se leerá algún archivo, en caso de que
         *                     haya algún error.
         */
        private static String[][] LeerArchivoExcel(String NombreArchivo) throws IOException {
                /** VARIABLES */
                String Cadena;
                int Count = 0;
                String Aux[] = new String[96];
                /** OBJETOS */
                FileReader Fr = new FileReader(NombreArchivo);
                BufferedReader Br = new BufferedReader(Fr);

                while ((Cadena = Br.readLine()) != null) {
                        Aux[Count] = Cadena;
                        Count++;
                }
                Br.close();

                String Archivo[][] = new String[96][46];
                for (int i = 0; i < 96; i++) {
                        for (int j = 0; j < 46; j++) {
                                Archivo[i][j] = "";
                        }
                }
                int ContadorColumnas = 0;

                for (int i = 0; i < 96; i++) {
                        for (int j = 0; j < Aux[i].length(); j++) {

                                if (Aux[i].charAt(j) == '@') {
                                        ContadorColumnas++;
                                } else if (Aux[i].charAt(j) == '#') {
                                        ContadorColumnas = 0;
                                        break;
                                } else {
                                        Archivo[i][ContadorColumnas] += Aux[i].charAt(j);
                                }
                        }
                }

                return Archivo;
        }

        /**
         * Método para convertir un arreglo de renglón por renglón a caracter por
         * caracter
         * 
         * @param Archivo   Contiene el el archivo guarado renglón por renglón.
         * @param Renglones Contiene el valor int que indica la cantidad de renglones
         *                  del archivo.
         * @return retorna un arreglo guardado caracter por caracter.
         */
        private static String[][] Do1x1(String[] Archivo, int Renglones) {
                String Aux[][] = new String[Renglones][HighSize(Archivo, Renglones)];
                for (int i = 0; i < Renglones; i++) {
                        for (int j = 0; j < Archivo[i].length(); j++) {
                                char x = Archivo[i].charAt(j);
                                if (x == 9) {
                                        x = 32;
                                }
                                Aux[i][j] = Character.toString(x);
                        }
                }

                return Aux;
        }

        /**
         * Método para limpiar el Archivo de basura como espacios, tabuladores, etc.
         * 
         * @param Archivo Contiene el Archivo el Archivo a limpiar.
         * @param Size    Contiene el tamaño del Archivo a limpiar
         * @return Retorna el Archivo ya limpio.
         */
        private static String[] cleanArchivo(String Archivo[], int Size) {
                String ArchivoLimpio[] = new String[Size];

                for (int i = 0; i < Size - 1; i++) {
                        if (Archivo[i].equals("") || Archivo[i].equals(" ") || Archivo[i].equals("\t")) {

                                for (int j = i; j < Size - 1; j++) {
                                        String Aux = Archivo[j];
                                        Archivo[j] = Archivo[j + 1];
                                        Archivo[j + 1] = Aux;
                                }
                                Size--;
                                ContadorArchivo--;

                        }
                }

                return ArchivoLimpio;
        }

        /**
         * Método para Convetir el arreglo de lenguaje de programacion a los valores
         * dados en el .Inf
         * 
         * @param Archivo Contiene el archivo con el codigo en lenguaje de programacion.
         * @param Size    Contiene el tamaño del Archivo
         * @return Retorna el Archivo convertido a los valores del .Inf.
         */
        private static String[] convertirArreglo(String Archivo[], int Size) {
                String ArchivoConvertido[] = new String[Size];
                for (int i = 0; i < ContadorArchivo; i++) {
                        if (isIdentificador(Archivo[i].toUpperCase())) {
                                ArchivoConvertido[i] = "0";
                        } else if (isEntero(Archivo[i].toUpperCase())) {
                                ArchivoConvertido[i] = "1";
                        } else if (isReal(Archivo[i].toUpperCase())) {
                                ArchivoConvertido[i] = "2";
                        } else if (isCadena(Archivo[i].toUpperCase())) {
                                ArchivoConvertido[i] = "3";
                        } else if (isTipoDatos(Archivo[i].toUpperCase())) {
                                ArchivoConvertido[i] = "4";
                        } else if (Archivo[i].toUpperCase().equals("+")) {
                                ArchivoConvertido[i] = "5";
                        } else if (Archivo[i].toUpperCase().equals("*")) {
                                ArchivoConvertido[i] = "6";
                        } else if (isRelacionales(Archivo[i].toUpperCase())) {
                                ArchivoConvertido[i] = "7";
                        } else if (Archivo[i].toUpperCase().equals("||")) {
                                ArchivoConvertido[i] = "8";
                        } else if (Archivo[i].toUpperCase().equals("&&")) {
                                ArchivoConvertido[i] = "9";
                        } else if (Archivo[i].toUpperCase().equals("!")) {
                                ArchivoConvertido[i] = "10";
                        } else if (Archivo[i].toUpperCase().equals("==")) {
                                ArchivoConvertido[i] = "11";
                        } else if (Archivo[i].toUpperCase().equals(";")) {
                                ArchivoConvertido[i] = "12";
                        } else if (Archivo[i].toUpperCase().equals(",")) {
                                ArchivoConvertido[i] = "13";
                        } else if (Archivo[i].toUpperCase().equals("(")) {
                                ArchivoConvertido[i] = "14";
                        } else if (Archivo[i].toUpperCase().equals(")")) {
                                ArchivoConvertido[i] = "15";
                        } else if (Archivo[i].toUpperCase().equals("{")) {
                                ArchivoConvertido[i] = "16";
                        } else if (Archivo[i].toUpperCase().equals("}")) {
                                ArchivoConvertido[i] = "17";
                        } else if (Archivo[i].toUpperCase().equals("=")) {
                                ArchivoConvertido[i] = "19";
                        } else if (Archivo[i].toUpperCase().equals("IF")) {
                                ArchivoConvertido[i] = "19";
                        } else if (Archivo[i].toUpperCase().equals("WHILE")) {
                                ArchivoConvertido[i] = "20";
                        } else if (Archivo[i].toUpperCase().equals("RETURN")) {
                                ArchivoConvertido[i] = "21";
                        } else if (Archivo[i].toUpperCase().equals("ELSE")) {
                                ArchivoConvertido[i] = "22";
                        } else if (Archivo[i].toUpperCase().equals("$")) {
                                ArchivoConvertido[i] = "23";
                        } else if (Archivo[i].toUpperCase().equals("@")) {
                                ArchivoConvertido[i] = "@";
                        }
                }

                return ArchivoConvertido;
        }
}