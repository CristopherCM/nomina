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
    double salarioMensual, salarioDiario, bonos, sueldo, sueldoDiasFeriados, sueldoHorasExtra, asignaciones, percepciones, deducciones, sueldoNeto;
    int diasTrabajados, horasExtra, feriados;
    String nombreArchivo = "Informacion_Empleados.csv";
    String[][] nomina = leerArchivo(nombreArchivo);
    Scanner lectura = new Scanner(System.in);
    int posicionEmpleado;
    String[] empleado = new String[6];

    System.out.println("Numero de Nomina:       Nombre:");
    for(int y = 0; y < nomina.length; y++){
      System.out.println(imprimirEmpleado(nomina, y));
    }
    leerArchivo(nombreArchivo);
    guardarNomina(nombreArchivo, nomina);

    System.out.println("ESCRIBE LA NOMINA DEL EMPLEADO QUE DESEAS BUSCAR");
    posicionEmpleado = encontrarEmpleado(nomina);
    salarioMensual = Double.parseDouble(nomina[posicionEmpleado][3]);

    System.out.println("ESCRIBE LA INFORMACION QUE SE PIDE");

    System.out.println("Días Trabajados: ");
    diasTrabajados = lectura.nextInt();

    System.out.println("Bonos: ");
    bonos = lectura.nextDouble();

    System.out.println("Días Feriados: ");
    feriados = lectura.nextInt();

    System.out.println("Horas Extra: ");
    horasExtra = lectura.nextInt();

    salarioDiario = calcularSalarioDiario(salarioMensual);
    sueldo = calcularSueldo(salarioDiario, diasTrabajados);
    sueldoDiasFeriados = calcularSueldoDiasFeriados(salarioDiario, feriados);
    sueldoHorasExtra = calcularSueldoHorasExtra(salarioDiario, horasExtra);
    asignaciones = calcularAsignaciones(sueldoDiasFeriados, sueldoHorasExtra, bonos);
    percepciones = calcularPercepciones(sueldo, asignaciones);

    System.out.println(percepciones);
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

  //Este metodo busca al empleado en el arreglo nomina y regresa la posicion en la se encuentra.
  public static int encontrarEmpleado(String[][] nomina){
    Scanner lectura = new Scanner(System.in);
    String nominaEmpleado;
    int posicionEmpleado = -1;

    System.out.print("Ingrese el número de nómina del trabajador: ");
    nominaEmpleado = lectura.next();

    for (int y = 0; y < nomina.length; y++) {
      if (nomina[y][5].equals(nominaEmpleado)){
          posicionEmpleado = y;
          break;
      }
    }
    return (posicionEmpleado);
  }

  public static double calcularPercepciones(double sueldo, double asignaciones){
    double percepciones;
    percepciones = sueldo + asignaciones;
    return (percepciones);
  }

  public static double calcularSalarioDiario(double salarioMensual){
    double salarioDiario;
    salarioDiario = salarioMensual / 30;
    return salarioDiario;
  }

  public static double calcularSueldo(double salarioDiario, int diasTrabajados){
    double sueldo;
    sueldo = salarioDiario * diasTrabajados;
    return (sueldo);
  }

  public static double calcularSueldoDiasFeriados(double salarioDiario, int feriados){
    double sueldoDiasFeriados;
    sueldoDiasFeriados = salarioDiario * (double)feriados;
    return (sueldoDiasFeriados);
  }

  public static double calcularSueldoHorasExtra(double salarioDiario, int horasExtra){
    double sueldoHorasExtra;
    sueldoHorasExtra = (salarioDiario / 8) * horasExtra;
    return (sueldoHorasExtra);
  }

  public static double calcularAsignaciones(double salarioDiasFeriados, double salarioHorasExtra, double bonos){
    double totalDeAsignaciones;
    totalDeAsignaciones = salarioDiasFeriados + salarioHorasExtra + bonos;
    return (totalDeAsignaciones);
  }

  //Este metodo regresa las deducciones del empleado
  public static double calcularDeducciones(double percepciones){
    Scanner lectura = new Scanner(System.in);
    double deducciones, iva, isr, prestamos;

    System.out.println("Prestamos: ");

    prestamos = lectura.nextInt();
    iva = percepciones * 0.16;
    isr = percepciones * 0.18;

    deducciones = prestamos + iva + isr;

    return (deducciones);
  }

  //Este metodo resta las deducciones a las percepciones del empleado
  public static double calcularSueldoNeto(double percepciones, double deducciones){
    double sueldoNeto;
    sueldoNeto = percepciones - deducciones;
    return (sueldoNeto);
  }

  //Este metodo regres el nombre, el apellido y el numero de nomina del empleado en un string
  public static String imprimirEmpleado(String[][] nomina, int y){
    String nombre, apellido, numeroDeNomina;
    nombre = nomina[y][0];
    apellido = nomina[y][1];
    numeroDeNomina = nomina[y][5];
    return ("       " +numeroDeNomina + "                " + nombre + " " + apellido);
  }



}
