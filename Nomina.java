/*****************************************************
Tecnológico de Monterrey CSF
Fundamentos de Programación
Proyecto Final: Sistema de nómina para microempresa
Por:
-Cristopher Cejudo
-Octavio Garduza
-Luis Revilla
*****************************************************/

import java.util.Scanner;
import java.io.*;

public class Nomina //Inicio clase Nomina
{
  public static void main(String[] args) throws IOException
  {
    String nombreArchivo = "Informacion_Empleados.csv";
    String[][] nomina = leerArchivo(nombreArchivo);
    String[] empleado = new String[6];

    for(int y = 0; y < 20; y++)
    {
      for(int x = 0; x < 6; x++){
        System.out.println(nomina[y][x]);
      }
    }

    nomina[0][0] = "Karla";
    guardarNomina(nombreArchivo, nomina);

  }

  public static String[][] leerArchivo(String nombreArchivo) throws IOException
  {
    String datosLeidos;
    String[][] nomina = new String[20][6];
    String[] empleado;

    FileReader lector = new FileReader (nombreArchivo);
    BufferedReader br = new BufferedReader(lector); //lee un bloque sin utilzar el disco

    for(int y = 0; y < 20; y++)
    {
      datosLeidos = br.readLine();
      empleado = datosLeidos.split(",");
      for(int x = 0; x < 6; x++){
        nomina[y][x] = empleado[x];
      }
    }
    lector.close();
    return (nomina);
  }

  public static void guardarNomina(String nombreArchivo, String[][] nomina) throws IOException{

    String[] empleado = new String[6];
    String datosLeidos = "";
    FileWriter escritor = new FileWriter(nombreArchivo);
    PrintWriter pw = new PrintWriter(escritor);

    datosLeidos = String.join(",", empleado);
    pw.println(datosLeidos);

    for(int y = 0; y < 20; y++)
    {
      for(int x = 0; x < 6; x++){
        empleado[x] = nomina[y][x];
      }
      datosLeidos = String.join(",", empleado);
      pw.println(datosLeidos);
    }
    escritor.close();
  }
}
