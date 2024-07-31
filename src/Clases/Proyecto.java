package Clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.*;

import Logica.Conexion;

public class Proyecto {
    //Atributos
    private int Id_Proyecto;
    private String nombre;
    private int n_Equipos;
    private double avance;
    private String fecha_Inicio;
    private String fecha_Limite;
    private String descripcion_Proyecto;
    private int usuario;
    //Variables
    String query = "";

    //Metodos
    public void llenarDatos(int creador){
        Scanner scanString = new Scanner(System.in);

        //Llenar cada campo del Proyecto
        System.out.print("Nombre: ");
        this.nombre = scanString.nextLine();
        //Valores ingresados por default
        this.n_Equipos = 0;
        this.avance = 0;
        this.fecha_Inicio = new Date(System.currentTimeMillis()).toString();
        //Solicitar la fecha Límite
        System.out.print("Fecha Límite (yyyy-mm-dd): ");
        this.fecha_Limite = scanString.nextLine();
        System.out.print("Descripción: ");
        this.descripcion_Proyecto = scanString.nextLine();
        //Definir quién creó el proyecto
        this.usuario = creador;
    }

    public void insertarProyecto(int creador){
        //Pedir los datos del Proyecto
        llenarDatos(creador);

        //Crea una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();

        //Query para insertar un nueva persona
        query = "INSERT INTO proyecto(nombre, n_Equipos, avance, fecha_Inicio, fecha_Limite, descripcion_Proyecto, usuario)" +
            "VALUES('" + this.nombre + "'," + this.n_Equipos + "," + this.avance + ",'" +
            this.fecha_Inicio + "','" + this.fecha_Limite + "','" + this.descripcion_Proyecto + "'," + this.usuario + ")";

        try {
            //Se ejecuta la insercion del nuevo Proyecto
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            //Terminar conexion con la DB
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en la conexion\n" + e);
        }
    }

    public void verProyecto(int creador){
        //Crear query que obtiene los proyectos creados por el Admin
        query = "SELECT Id_Proyecto, nombre, avance, fecha_Inicio, fecha_Limite FROM proyecto WHERE usuario = " + creador;
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
            //Ejecutar la consulta del usuario
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Imprime tantos proyectos tenga el Admin
                System.out.println("Id: " + rs.getInt("Id_Proyecto"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("avance: " + rs.getInt("avance") + "%");
                System.out.println("Fecha Inicio: " + rs.getString("fecha_Inicio"));
                System.out.println("Fecha Límite: " + rs.getString("fecha_Limite"));
                System.out.println("");
                //Aqui se van a ingresar los Id validos
                listaId.add(rs.getInt("Id_Proyecto"));
                listaNombre.add(rs.getString("nombre"));
            }
            //Se valida si hay registros en la lista
            if (listaId.size()>0) {
                //Una vez terminado de imprimir, preguntar por el ID al que quiere ingresar
                System.out.print("Id Proyecto: ");
                leerInt = scanInt.nextInt();
                //Definir el nombre de la instancia como vacio en caso de no encontrar coincidencia
                this.nombre = "";
                //Ahora se compara si el Id fue valido viendo cada elemento de la lista previamente llenada
                for(int i = 0; i<listaId.size(); i++){
                    //En caso de encontrar coincidencia, asignar los valores a la instancia
                    if (listaId.get(i) == leerInt) {
                        this.Id_Proyecto = listaId.get(i);
                        this.nombre = listaNombre.get(i);
                    }
                }
            } else{
                //Si el nombre es null significa que no se encontro ningun Dev
                this.nombre = null;
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en verProyecto\n" + e);
        }
    }

    /*Antiguo metodo v3.9
    public void consultarNombreProyecto(int Id_Proyecto, int usuario){
        this.nombre = "";
        //Crear query para obtener info del Proyecto
        query = "SELECT Id_Proyecto, nombre FROM proyecto WHERE Id_Proyecto = " + Id_Proyecto + " AND usuario = " + usuario;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta del usuario
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                //Asigna los datos del Proyecto a la instancia
                this.Id_Proyecto = rs.getInt("Id_Proyecto");
                this.nombre = rs.getString("nombre");
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
        this.nombre = scanString.nextLine();
        System.out.print("Descripción: ");
        this.descripcion_Proyecto = scanString.nextLine();
        System.out.print("Fecha límite (yyyy-mm-dd): ");
        this.fecha_Limite = scanString.nextLine();

        //Crear query para actualizar los datos del proyecto
        query = "UPDATE proyecto SET nombre = '" + this.nombre + "', fecha_Limite = '" + this.fecha_Limite + "', descripcion_Proyecto = '" + 
            this.descripcion_Proyecto + "' WHERE Id_Proyecto = " + this.Id_Proyecto;
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

    public void borrarProyecto(){
        //Crear query para eliminar el Proyecto
        query = "DELETE FROM proyecto WHERE Id_Proyecto = " + this.Id_Proyecto;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la eliminacion del registro
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Proyecto eliminado con exito!");
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en borrarUsuario\n" + e);
        }
    }

    public void calcularEquipos(){
        //Crear query que cuenta los equipos en un proyecto
        query = "SELECT COUNT(Id_Equipo) AS n FROM equipo WHERE proyecto = " + this.Id_Proyecto;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar el conteo de equipos
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                //Asigna los datos del Equipo a la instancia
                this.n_Equipos = rs.getInt("n");
            }
            //Crear query que actualice n_Equipos en Proyecto
            query = "UPDATE proyecto SET n_Equipos = " + this.n_Equipos + " WHERE Id_Proyecto = " + this.Id_Proyecto;
            stmt.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en borrarEquipo\n" + e);
        }

    }




    //Constructores
    public Proyecto() {
    }

    //Getters n Setters
    public int getId_Proyecto() {
        return this.Id_Proyecto;
    }

    public void setId_Proyecto(int Id_Proyecto) {
        this.Id_Proyecto = Id_Proyecto;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getN_Equipos() {
        return this.n_Equipos;
    }

    public void setN_Equipos(int n_Equipos) {
        this.n_Equipos = n_Equipos;
    }

    public double getAvance() {
        return this.avance;
    }

    public void setAvance(double avance) {
        this.avance = avance;
    }

    public String getFecha_Inicio() {
        return this.fecha_Inicio;
    }

    public void setFecha_Inicio(String fecha_Inicio) {
        this.fecha_Inicio = fecha_Inicio;
    }

    public String getFecha_Limite() {
        return this.fecha_Limite;
    }

    public void setFecha_Limite(String fecha_Limite) {
        this.fecha_Limite = fecha_Limite;
    }

    public String getDescripcion_Proyecto() {
        return this.descripcion_Proyecto;
    }

    public void setDescripcion_Proyecto(String descripcion_Proyecto) {
        this.descripcion_Proyecto = descripcion_Proyecto;
    }

    public int getUsuario() {
        return this.usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

}
