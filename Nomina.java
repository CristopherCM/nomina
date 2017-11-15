/*****************************************************
Tecnológico de Monterrey CSF
Fundamentos de Programación
Proyecto Final: Sistema de nómina para microempresa
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
    String nombreArchivo = "BD-ProyectoProgra.csv";
    String[][] nomina = leerArchivo(nombreArchivo);
    String[] empleado = new String[6];

    for(int y = 0; y < 20; y++)
    {
      for(int x = 0; x < 6; x++){
        System.out.print(nomina[y][x]+"\t\t");
      }
      System.out.println();
    }
    System.out.println();
    //nomina[0][0] = "Karla"
    guardarNomina(nombreArchivo, nomina);
    System.out.println("Hola!!!!");
    System.out.println(nomina[19][5]);
  }

  //leerArchivo guarda los datos de Excel en una matriz
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
//guardarNomina permite agregar empleados a la nómina
  public static void guardarNomina(String nombreArchivo, String[][] nomina) throws IOException{

    String[] empleado = new String[6];
    String datosLeidos = "";
    FileWriter escritor = new FileWriter(nombreArchivo,true);
    PrintWriter pw = new PrintWriter(escritor);

    datosLeidos = String.join(",", empleado);
    //pw.println(datosLeidos);

    for(int y = 0; y < 20; y++)
    {
      for(int x = 0; x < 6; x++){
        empleado[x] = nomina[y][x];
      }
      datosLeidos = String.join(",", empleado);
      //pw.println(datosLeidos);
    }
    escritor.close();
  }
//salario saca el total y desglose del salario del empleado
  public static void salario(String[][] nomina){
    Scanner sc = new Scanner(System.in);
    int diasTrab = 0;
    int nomEmp = 0;
    int j = 0;

    System.out.print("Ingrese el número de nómina del trabajador: ");
    nomEmp = sc.nextInt();

    for (int y = 0; y < nomina.length; y++) {
      if (nomina[y][5] == nomEmp) {
        y = j;
      }
    }

  }
}
