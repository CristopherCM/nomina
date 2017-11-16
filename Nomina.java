/*****************************************************
Tecnológico de Monterrey CSF
Fundamentos de Programación
Proyecto Final: Sistema de nómina para microempresa
Por:
-Cristopher Cejudo
-Octavio Garduza
-Luis Revilla
*****************************************************/

import java.util.*;
import java.io.*;

public class Nomina //Inicio clase Nomina
{
  public static void main(String[] args) throws IOException
  {
    //Declaracion de variables
    Scanner sc = new Scanner(System.in);
    double salarioMensual, salarioDiario, bonos, sueldo, sueldoDiasFeriados, sueldoHorasExtra, asignaciones, percepciones, deducciones, sueldoNeto, prestamos, deduccionPorIva, deduccionPorIsr;
    int diasTrabajados, horasExtra, feriados, menu, totalEmpleados=0, opcionAModificar;
    String nombreArchivo = "BD-ProyectoProgra.csv";
    String[][] nomina;
    Scanner lectura = new Scanner(System.in);
    int posicionEmpleado;
    String[] empleado = new String[6];
    String nominaEmpleado, nuevoDato, datosLeidos;
    String res = "si";
    boolean salir = false;


    while(res.equalsIgnoreCase("si") ){

      totalEmpleados = 0;
      FileReader lector = new FileReader (nombreArchivo);
      BufferedReader br = new BufferedReader(lector);
      while((datosLeidos = br.readLine()) !=null){
        totalEmpleados++;
      }
      System.out.println(totalEmpleados);
      br.close();
      lector.close();

      nomina = leerNomina(nombreArchivo, totalEmpleados);
      System.out.println();

      System.out.println("##########################");
      System.out.println("# BIENVENIDO A LA NOMINA #");
      System.out.println("##########################");

      System.out.println();
      //Impresion de la nomina
      System.out.println("Numero de Nomina:       Nombre:");
      System.out.println();
      for(int y = 1; y < totalEmpleados; y++){
        System.out.println(imprimirEmpleado(nomina, y));
      }
      System.out.println();
      System.out.println("SELECCIONE EL NUMERO DE LA OPCIÓN QUE DESEE REALIZAR");
      System.out.println(" (1) Buscar Empleado por Nomina ");
      System.out.println(" (2) Agregar Empleado ");
      System.out.println(" (3) Eliminar Empleado ");
      System.out.println(" (4) Salir");
      System.out.println("Opción: ");
      menu = lectura.nextInt();

      switch(menu){
        case 1:
          System.out.println("ESCRIBA LA NOMINA DEL EMPLEADO QUE DESEAS BUSCAR");
          System.out.print("Nomina: ");
          nominaEmpleado = lectura.next();

          posicionEmpleado = encontrarEmpleado(nomina, nominaEmpleado);

          if(posicionEmpleado != -1){

            System.out.println();
            System.out.println("***********************************");
            for(int x = 0; x < nomina[0].length; x++){
              System.out.println(imprimirFichaEmpleado(nomina, posicionEmpleado, x));
            }
            System.out.println("***********************************");
            System.out.println();

            System.out.println("ESCRIBA EL NUMERO DE LA OPCIÓN QUE DESEE REALIZAR");
            System.out.println(" (5) Obtener pago del empleado ");
            System.out.println(" (6) Modificar la información del empleado ");
            menu = lectura.nextInt();

            switch(menu){
              case 5:
                salarioMensual = Double.parseDouble(nomina[posicionEmpleado][3]);

                System.out.println("ESCRIBA LA INFORMACION QUE SE PIDE");

                System.out.println("ASIGNACIONES");

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

                System.out.println("DEDUCCIONES");

                System.out.println("Prestamos ($): ");
                prestamos = lectura.nextDouble();

                deduccionPorIsr = calcularIsr(percepciones);
                deduccionPorIva = calcularIva(percepciones);
                deducciones = calcularDeducciones(prestamos, deduccionPorIva, deduccionPorIsr);
                sueldoNeto = calcularSueldoNeto(percepciones, deducciones);

                System.out.println("************************************");
                System.out.println(" RECIBO DE PAGO DE " + nomina[posicionEmpleado][0].toUpperCase() + " " + nomina[posicionEmpleado][1].toUpperCase());
                System.out.println(" Asignaciones: ");
                System.out.printf("   Bonos: $%.2f%n",bonos);
                System.out.printf("   Sueldo por días feriados: $%.2f%n", sueldoDiasFeriados);
                System.out.printf("   Sueldo por horas extras: $%.2f%n", sueldoHorasExtra );
                System.out.printf("     Percepciones: $%.2f%n", percepciones);
                System.out.println("------------------------------------");
                System.out.println(" Deducciones: ");
                System.out.printf("   Prestamos: $%.2f%n", prestamos);
                System.out.printf("   IVA: $%.2f%n", deduccionPorIva);
                System.out.printf("   ISR: $%.2f%n", deduccionPorIsr);
                System.out.printf("     Total de deducciones: $%.2f%n",deducciones);
                System.out.println("-------------------------------------");
                System.out.printf("Sueldo Neto: $%.2f%n",sueldoNeto);
                System.out.println("*************************************");

                break;
                case 6:
                  System.out.println("ESCRIBA EL NÚMERO DE LA OPCIÓN QUE DESEE");
                  System.out.println("(1) Nombre | (2) Apellido | (3) Cargo | (4) Sueldo | (5) Fecha de Ingreso | (6) Nomina");
                  opcionAModificar = lectura.nextInt() - 1;
                  System.out.println("Escriba el nuevo " + nomina[0][opcionAModificar]);
                  nuevoDato = lectura.next();
                  nomina[posicionEmpleado][opcionAModificar] = nuevoDato;
                  guardarNomina(nombreArchivo, nomina, empleado);
                  break;
                default:
                  System.out.println("Ha ocurrido un error");
            }
            break;
         }
         else{
           System.out.println("La nomina no existe");
           break;
         }

         case 2:
            System.out.println("ESCRIBE LA INFORMACIÓN REQUERIDA: ");
            guardarNomina(nombreArchivo,nomina,empleado);
            nomina = leerNomina(nombreArchivo, totalEmpleados);

            break;
         case 3:
            break;
         case 4:
            salir = true;
            break;
         default:
            System.out.println("Ha ocurrido un error");
      }
      System.out.print("¿Deseas hacer algo de más? (Si | No): ");
      res = sc.nextLine();
    }
  }

  public static String[][] leerNomina(String nombreArchivo, int totalEmpleados) throws IOException
  {
    String datosLeidos;
    String[][] nomina = new String[totalEmpleados][6];
    String[] empleado;

    FileReader lector = new FileReader (nombreArchivo);
    BufferedReader br = new BufferedReader(lector); //lee un bloque sin utilzar el disco

    for(int y = 0; y < totalEmpleados; y++)
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

  public static void guardarNomina(String nombreArchivo, String[][] nomina, String[] empleado) throws IOException{

    Scanner sc = new Scanner(System.in);
    String res;
    String nombre, apll, carg, sueldoB, ingreso, num;
    String datosLeidos = "";
    FileWriter escritor = new FileWriter(nombreArchivo,true);
    PrintWriter pw = new PrintWriter(escritor);

    System.out.printf("%n¿Deseas agregar un nuevo empleado a la nómina? (Si | No): ");
    res = sc.nextLine();

    while (res.equalsIgnoreCase("si")) {

        System.out.print("Nombre: ");
        nombre = sc.nextLine();

        System.out.print("Apellido: ");
        apll = sc.nextLine();

        System.out.print("Cargo: ");
        carg = sc.nextLine();

        System.out.print("Sueldo Base: $");
        sueldoB = sc.nextLine();

        System.out.print("Fecha de Ingreso (dd/mm/aa): ");
        ingreso = sc.nextLine();

        System.out.print("Número de Cuenta de Nómina (4323***): ");
        num = sc.nextLine();

        pw.printf("%n%s,%s,%s,%s,%s,%s",nombre,apll,carg,sueldoB,ingreso,num);

        System.out.print("¿Deseas agregar de nuevo? (Si | No): ");
        res = sc.nextLine();
      }

    escritor.close();
  }

  public static int encontrarEmpleado(String[][] nomina, String nominaEmpleado){
    int posicionEmpleado = -1;
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

  public static double calcularDeducciones(double prestamos, double deduccionPorIva, double deduccionPorIsr){
    double deducciones;
    deducciones = prestamos + deduccionPorIva + deduccionPorIsr;
    return (deducciones);
  }

  public static double calcularIva(double percepciones){
    double deduccionPorIva;
    deduccionPorIva = percepciones * 0.16;
    return (deduccionPorIva);
  }

  public static double calcularIsr(double percepciones){
    double deduccionPorIsr;
    deduccionPorIsr = percepciones * 0.18;
    return (deduccionPorIsr);
  }

  public static double calcularSueldoNeto(double percepciones, double deducciones){
    double sueldoNeto;
    sueldoNeto = percepciones - deducciones;
    return (sueldoNeto);
  }

  public static String imprimirEmpleado(String[][] nomina, int y){
    String nombre, apellido, numeroDeNomina;
    nombre = nomina[y][0];
    apellido = nomina[y][1];
    numeroDeNomina = nomina[y][5];
    return ("     " + numeroDeNomina + "            " + nombre + " " + apellido);
  }

  public static String imprimirFichaEmpleado(String[][] nomina, int posicionEmpleado, int x ){
    String datoEmpleado;
    datoEmpleado = nomina[posicionEmpleado][x];
    return (nomina[0][x] + ": " + datoEmpleado);
  }

  public static int agregarEmpleado(String nombreArchivo, String[][] nomina, String[] empleado, int totalEmpleados) throws IOException{

    String datosLeidos;
    FileWriter escritor = new FileWriter(nombreArchivo, true);
    PrintWriter pw = new PrintWriter(escritor);
    datosLeidos = String.join(",", empleado);
    if(nomina.length == 21){
      pw.println();
      pw.println(datosLeidos);
    }
    else{
      pw.println(datosLeidos);
    }
    escritor.close();
    return(totalEmpleados + 1);

  }

}

