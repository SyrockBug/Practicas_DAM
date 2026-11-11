package ejercicio1;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Ejercicio 1: Trabajo con diferentes tipos de archivos:
 *     a) Archivos de texto: Lectura y escritura
 *     b) Archivos binarios (serialización): Lectura y escritura.
 *
 * @author Medina Fernández Nicolás.
 */
public class Ejercicio1 {


    public static void main(String args[]) {

        //----------------------------------------------
        //          Declaracion de variables 
        //----------------------------------------------
        // Constantes
        final int CANT_SEPARADORES_LINEA = 200;
        
        // Variables de entrada
        Instituto insti = new Instituto();
        Scanner teclado = new Scanner(System.in);
        
        // Variables de salida
        Instituto instiRecuperado = null;
        File fichTextoInsti;
        
        // Variables auxiliares
        String strDatosProf;
        String[] profesInstituto;
        
        //He modificado ligeramente el la forma de obtención de la ruta para que se adapte a cualquier sistema operativo.
        String rutaProfesores = System.getProperty("user.dir") + File.separator + "recursos" + File.separator + "ListadoProfesores.txt";
        String rutaInstituto = System.getProperty("user.dir") + File.separator + "recursos" + File.separator + "Instituto.txt";
        String rutaSerialInstituto = System.getProperty("user.dir") + File.separator + "recursos" + File.separator + "Instituto.bat";

        
        
        //*******************************************************************
        //  PARTE 1: Lectura/Escritura de Profesores en ficheros de texto
        //*******************************************************************
        
        //------------------------------------------------------------------
        //       Lectura del archivo de texto ListadoProfesores.txt
        //------------------------------------------------------------------
        
        // Abrimos el archivo de profesores para la lectura.
        System.out.println("Abriendo archivo de profesores...");

        try(BufferedReader entrada = new BufferedReader(new FileReader(rutaProfesores));){
            
            //Bucle principal que procesará cada linea del fichero con los datos de los profesores almacenados con formato específico.
            while((strDatosProf = entrada.readLine())!=null){
                
                //Declaración de las variables necesarias para procesar cada línea asumiendo un formato predeterminado.
                Profesor profe;
                String nombre, especialidad, departamento;
                LocalDate fechaIncorporacion;
                List<String> listaModulos = new ArrayList<>();
                String[] arrModulos;
                String[] dato = strDatosProf.split(";");

                nombre = dato[0];
                especialidad = dato[1];
                fechaIncorporacion = LocalDate.parse(dato[2]);
                departamento = dato[3];
                
                arrModulos = dato[4].split(",");
                
                // Pasamos el array de los modulos a lista.
                listaModulos.addAll(Arrays.asList(arrModulos));
                
                profe = new Profesor(nombre, especialidad, fechaIncorporacion, listaModulos, departamento);
                insti.add(profe);
            }
            
            //Se cierra el fichero automaticamente haya tenido exito o no el try{} pues es un try-catch con recursos;
            System.out.println("Cerrando archivo de profesores...");

            
        }catch(IOException ex){
            System.out.printf("Error al abrir el archivo: %s\n", ex.getMessage());
        }

        System.out.println();

        //-------------------------------------------------------
        //      Escritura del archivo de texto Instituto.txt
        //-------------------------------------------------------
        
        // Abrimos el archivo de salida para almacenar la información de los profesores.
        System.out.println("Abriendo archivo del instituto...");
        System.out.printf("%s", insti.toString());
        System.out.println();

        //Creamos el archivo donde se almacenarán los datos formateados y lo abrimos.
        fichTextoInsti = new File(rutaInstituto);
        
        //Aunque PrintWriter podria ser suficiente para est atarea prefiero usar BufferedWriter al ser mas general.
        try(BufferedWriter salidaFichTexto = new BufferedWriter(new  FileWriter(rutaInstituto))){
             
            //Impresión de la cabecera del fichero.
            for(int i=0;i<=CANT_SEPARADORES_LINEA;i++){
                salidaFichTexto.write("*");
            }
            salidaFichTexto.newLine();
            
            salidaFichTexto.write("LISTADO DE PROFESORES");
            salidaFichTexto.newLine();
            
            for(int i=0;i<=CANT_SEPARADORES_LINEA;i++){
                salidaFichTexto.write("*");
            }
            salidaFichTexto.newLine();
            
            //Pasamos cada profesor a un array en un vector de arrays pudiendo adaptarse a una cantidad desconocida de profesores si se diera el caso.
            profesInstituto = insti.toString().split("#");
            
            for(int i=0;i<profesInstituto.length;i++){
                //Descartamos posibles fallos del metodo split() al separar la cadena.
                if(!profesInstituto[i].trim().isEmpty()){
                    String[] datos = profesInstituto[i].split(";");
                    
                    //Impresion de cada uno de los datos del profesor.
                    salidaFichTexto.write("NOMBRE DEL PROFESOR: ");
                    salidaFichTexto.write(datos[0]);
                    salidaFichTexto.newLine();

                    salidaFichTexto.write("ESPECIALIDAD: ");
                    salidaFichTexto.write(datos[1]);
                    salidaFichTexto.newLine();

                    salidaFichTexto.write("FECHA DE INCORPORACIÓN: ");
                    salidaFichTexto.write(datos[2]);
                    salidaFichTexto.newLine();

                    salidaFichTexto.write("DEPARTAMENTO: ");
                    salidaFichTexto.write(datos[3]);
                    salidaFichTexto.newLine();

                    salidaFichTexto.write("MÓDULOS: ");
                    salidaFichTexto.write(datos[4]);
                    salidaFichTexto.newLine();

                    for(int j=0;j<=CANT_SEPARADORES_LINEA;j++){
                    salidaFichTexto.write("*");
                    }
                    salidaFichTexto.newLine();
                
                }
            }
            
            //Se cierra el fichero automaticamente haya tenido exito o no el try{} pues es un try-catch con recursos;
            System.out.println("Cerrando archivo del Instituto...");
            
        }catch(IOException ex){
            System.out.printf("Error al abrir el archivo: %s\n", ex.getMessage());
        }
            
        System.out.println();
        System.out.println("Archivos de texto cerrados y procesamiento finalizado");
        System.out.println("-----------------------------------------------------");
        System.out.println();
        
        
        //***********************************************************************
        //  PARTE 2: Persistencia de Objetos mediante Serialización en Java
        //***********************************************************************
        
        //--------------------------------------------------------
        // Escritura del archivo serializable Instituto.dat
        //--------------------------------------------------------
        
        try(ObjectOutputStream serialInstituto = new ObjectOutputStream(new FileOutputStream(rutaSerialInstituto))){
            
            serialInstituto.writeObject(insti);
            System.out.println("Archivo serializable 'Instituto.dat' generado correctamente.");
            System.out.println();
            //Se cierra el stream automaticamente haya tenido exito o no el try{} pues es un try-catch con recursos;
            
        }catch(IOException ex){
            System.out.printf("Error al serializar Instituto: %s\n", ex.getMessage());
        }
        
        
        //--------------------------------------------------------
        // Lectura del archivo serializable Instituto.dat
        //--------------------------------------------------------  
        
        try(ObjectInputStream recuperaInstituto = new ObjectInputStream(new FileInputStream(rutaSerialInstituto))){
            
            instiRecuperado = (Instituto)recuperaInstituto.readObject();
            System.out.println("Archivo serializable leído correctamente.");
            System.out.println();
            //Se cierra el stream automaticamente haya tenido exito o no el try{} pues es un try-catch con recursos;
            
        }catch(FileNotFoundException ex){
            System.out.println("Error al rescatar el objeto 'Instituto'; El archivo noi se encuentra en la ruta especificada.");
        }catch(ClassNotFoundException ex){
            System.out.println("Error al rescatar el objeto 'Instituto'; El sistema no reconoce la clase del objeto a importar");
        }catch(IOException ex){
            System.out.println("Error al rescatar el objeto 'Instituto'; Transferencia de datos fallida.");
        }

        
        //----------------------------------------------
        // Mostrar datos leídos de Instituto.dat 
        //----------------------------------------------
        
        System.out.println("Contenido del Instituto leído desde archivo serializable:");
        System.out.println();
        
        if(instiRecuperado == null){
            System.out.println("El instituto no pudo ser recuperado");
        }else{
            System.out.printf("%s", instiRecuperado.toString());
        }
        
        System.out.println("\nArchivos serializados cerrados y procesamiento finalizado.\n");
        System.out.println("Fin del programa. Cierre la ventana para salir\n");    
        
    }
}
