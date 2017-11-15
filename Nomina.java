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
    String nombreArchivo = "Informacion_Empleados.csv";
    String[][] nomina = leerArchivo(nombreArchivo);
    String[] empleado = new String[6];

    System.out.println(calcularPercepciones(nomina, encontrarEmpleado(nomina)));
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
  }

  //Este metodo busca al empleado en el arreglo nomina y regresa la posicion en la se encuentra.
  public static int encontrarEmpleado(String[][] nomina){
    Scanner lectura = new Scanner(System.in);
    String nominaEmpleado;
    int posicionEmpleado = 0;

    System.out.print("Ingrese el número de nómina del trabajador: ");
    nominaEmpleado = lectura.next();

    for (int y = 0; y < nomina.length; y++) {
      if (nomina[y][5].equals(nominaEmpleado)) {
          posicionEmpleado = y;
      }
    }
    return (posicionEmpleado);
  }

  public static double calcularPercepciones(String[][] nomina, int posicionEmpleado){
    Scanner lectura = new Scanner(System.in);
    double percepciones = 0, sueldo = 0, asignaciones = 0, salarioDiario = 0, bonos = 0, diasTrabajados = 0, feriados = 0, horasExtra = 0;

    System.out.println("Escribe la información que se pide: ");

    System.out.println("Dias Trabajados: ");
    diasTrabajados = lectura.nextInt();

    System.out.println("Bonos: ");
    bonos = lectura.nextInt();

    System.out.println("Feriados: ");
    feriados = lectura.nextInt();

    System.out.println("Horas Extra: ");
    horasExtra = lectura.nextInt();

    salarioDiario = Double.parseDouble(nomina[posicionEmpleado][3]) / 30;

    System.out.println(nomina[posicionEmpleado][3]);
    sueldo = salarioDiario * diasTrabajados;

    feriados = salarioDiario * feriados;
    horasExtra = (salarioDiario / 8) * horasExtra;

    asignaciones = feriados + horasExtra + bonos;
    percepciones = sueldo + asignaciones;

    return (percepciones);
  }

}
