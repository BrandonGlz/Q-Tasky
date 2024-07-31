package Clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.*;

import Logica.Conexion;

public class Equipo {
    //Atributos
    private int Id_Equipo;
    private String nombre_Equipo;
    private int proyecto;
    //Variables
    String query = "";


    //Metodos
    public void llenarDatos(int proyecto){
        Scanner scanString = new Scanner(System.in);

        //Llenar cada campo del Equipo
        System.out.print("Nombre: ");
        this.nombre_Equipo = scanString.nextLine();
        this.proyecto = proyecto;
    }

    
    public void insertarEquipo(int proyecto){
        //Pedir los datos del Equipo
        llenarDatos(proyecto);

        //Crea una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();

        //Query para insertar un nueva persona
        query = "INSERT INTO equipo(nombre_Equipo, proyecto) VALUES('" + this.nombre_Equipo + "'," + this.proyecto + ")";

        try {
            //Se ejecuta la insercion del nuevo Equipo
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            //Crear nueva query que consulta el Id del equipo recien insertado
            query = "SELECT Id_Equipo FROM equipo ORDER BY Id_Equipo DESC LIMIT 1";
            //Se ejecuta 
            ResultSet rs = stmt.executeQuery(query);
            //Lectura de los datos
            if(rs.next()){
                //Se asigna el Id para crear su Tablero
                this.Id_Equipo = rs.getInt("Id_Equipo");
            }
            //Terminar conexion con la DB
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en insertarEquipo\n" + e);
        }

    }

    public void verEquipos(int proyecto){
        //Crear query que obtiene los Equipos del proyecto
        query = "SELECT Id_Equipo, nombre_Equipo FROM equipo WHERE proyecto = " + proyecto;
        //Crear una lista que almacenara los id impresos
        List<Integer> listaId = new ArrayList<>();
        List<String> listaNombre = new ArrayList<>();
        //Crear variables para leer el ID
        int leerInt = 0;
        Scanner scanInt = new Scanner(System.in);

        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta de los Equipos
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Imprime tantos Equipos tenga el Proyecto
                System.out.println("Id: " + rs.getInt("Id_Equipo"));
                System.out.println("Nombre: " + rs.getString("nombre_Equipo"));
                System.out.println("");
                //Aqui se van a ingresar los Id validos
                listaId.add(rs.getInt("Id_Equipo"));
                listaNombre.add(rs.getString("nombre_Equipo"));
            }
            //Se valida si hay registros en la lista
            if (listaId.size()>0) {
                //Una vez terminado de imprimir, preguntar por el ID al que quiere ingresar
                System.out.print("Id Equipo: ");
                leerInt = scanInt.nextInt();
                //Definir el nombre de la instancia como vacio en caso de no encontrar coincidencia
                this.nombre_Equipo = "";
                //Ahora se compara si el Id fue valido viendo cada elemento de la lista previamente llenada
                for(int i = 0; i<listaId.size(); i++){
                    //En caso de encontrar coincidencia, asignar los valores a la instancia
                    if (listaId.get(i) == leerInt) {
                        this.Id_Equipo = listaId.get(i);
                        this.nombre_Equipo = listaNombre.get(i);
                    }
                }
            } else{
                //Si el nombre es null significa que no se encontro ningun Dev
                this.nombre_Equipo = null;
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en verEquipo\n" + e);
        }
    }

    /*Antiguo metodo v3.9
    public void consultarNombreEquipo(int Id_Equipo, int proyecto){
        this.nombre_Equipo = "";
        //Crear query para obtener info del Equipo
        query = "SELECT Id_Equipo, nombre_Equipo FROM equipo WHERE Id_Equipo = " + Id_Equipo + " AND proyecto = " + proyecto;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta del Equipo
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                //Asigna los datos del Equipo a la instancia
                this.Id_Equipo = rs.getInt("Id_Equipo");
                this.nombre_Equipo = rs.getString("nombre_Equipo");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en consultarNombreProyecto\n" + e);
        }
        
    }
 */
    public void actualizarInformacion(){
        //Scanners de lectura
        Scanner scanString = new Scanner(System.in);
        Scanner scanInt = new Scanner(System.in);
        //Pedir dato por dato y asignarlo a la clase
        System.out.print("Nombre: ");
        this.nombre_Equipo = scanString.nextLine();

        //Crear query para actualizar los datos del Equipo
        query = "UPDATE equipo SET nombre_Equipo = '" + this.nombre_Equipo + "' WHERE Id_Equipo = " + this.Id_Equipo;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la actualizacion de datos
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Datos actualizados con exito!");
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en actualizarInformacion\n" + e);
        }
    }

    public void borrarEquipo(){
        //Crear query para eliminar el Equipo
        query = "DELETE FROM equipo WHERE Id_Equipo = " + this.Id_Equipo;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la eliminacion del registro
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Equipo eliminado con exito!");
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en borrarEquipo\n" + e);
        }
    }

    


    

    //Constructores
    public Equipo() {
    }

    //Getters n Setters
    public int getId_Equipo() {
        return this.Id_Equipo;
    }

    public void setId_Equipo(int Id_Equipo) {
        this.Id_Equipo = Id_Equipo;
    }

    public String getNombre_Equipo() {
        return this.nombre_Equipo;
    }

    public void setNombre_Equipo(String nombre_Equipo) {
        this.nombre_Equipo = nombre_Equipo;
    }

    public int getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(int proyecto) {
        this.proyecto = proyecto;
    }
}
