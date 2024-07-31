package Clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

import Logica.Conexion;

public class Tarea {
    //Atributos
    private int Id_Tarea;
    private String nombre;
    private String descripcion;
    private String prioridad;
    private int n_Prioridad;
    private int proyecto;
    private int equipo;
    private int usuario;
    private int creador;
    //Variables
    String query = "";

    //Metodos

    
    public void llenarDatos(int proyecto, int equipo, int creador){
        Scanner scanString = new Scanner(System.in);
        String leerString = "";
        //Llenar cada campo de la Tarea
        System.out.print("Nombre: ");
        this.nombre = scanString.nextLine();
        System.out.print("Descipción: ");
        this.descripcion = scanString.nextLine();
        System.out.println("Prioridad (Baja, Media, Alta): ");
        leerString = scanString.nextLine().toLowerCase();
        if (leerString.equals("baja")) {
            this.prioridad = "Baja";
            this.n_Prioridad = 1;
        } else if(leerString.equals("media")){
            this.prioridad = "Media";
            this.n_Prioridad = 2;
        } else if(leerString.equals("alta")){
            this.prioridad = "Alta";
            this.n_Prioridad = 3;
        } else{
            this.prioridad = "Baja";
            this.n_Prioridad = 1;
        }
        this.proyecto = proyecto;
        this.equipo = equipo;
        this.usuario = 0; //Valor por default desasignado
        this.creador = creador;
    }

    
    public void insertarTarea(int proyecto, int equipo, int creador){
        //Pedir los datos de la Tarea
        llenarDatos(proyecto, equipo, creador);

        //Crea una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();

        //Query para insertar una nueva Tarea
        query = "INSERT INTO tarea(nombre, descripcion, prioridad, n_Prioridad, proyecto, equipo, usuario, creador) " + 
            " VALUES('" + this.nombre + "','" + this.descripcion + "','" + this.prioridad + "'," + this.n_Prioridad + "," + this.proyecto + 
            "," + this.equipo + "," + this.usuario + "," + this.creador + ")";

        try {
            //Se ejecuta la insercion de la Tarea
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            //Crear nueva query que consulta el Id de la tarea recien insertada
            query = "SELECT Id_Tarea FROM tarea ORDER BY Id_Tarea DESC LIMIT 1";
            //Se ejecuta 
            ResultSet rs = stmt.executeQuery(query);
            //Lectura de los datos
            if(rs.next()){
                //Se asigna el Id para crear su Avance
                this.Id_Tarea = rs.getInt("Id_Tarea");
            }
            //Terminar conexion con la DB
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en insertarTarea\n" + e);
        }
    }

    public void verTareas(int equipo){
        //Crear query que obtiene las Tareas del Equipo
        query = "SELECT Id_Tarea, nombre, prioridad, porcentaje FROM avance INNER JOIN tarea ON Id_Tarea = tarea WHERE equipo = " + equipo;
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
            //Ejecutar la consulta de las Tareas
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Imprime tantas Tareas tenga el equipo
                System.out.println("Id: " + rs.getInt("Id_Tarea"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Prioridad: " + rs.getString("prioridad"));
                System.out.println("Avance: " + rs.getDouble("porcentaje") + "%");
                System.out.println("");
                //Aqui se van a ingresar los Id validos
                listaId.add(rs.getInt("Id_Tarea"));
                listaNombre.add(rs.getString("nombre"));
            }
            //Se valida si hay registros en la lista
            if (listaId.size()>0) {
                //Una vez terminado de imprimir, preguntar por el ID al que quiere ingresar
                System.out.print("Id Tarea: ");
                leerInt = scanInt.nextInt();
                //Definir el nombre de la instancia como vacio en caso de no encontrar coincidencia
                this.nombre = "";
                //Ahora se compara si el Id fue valido viendo cada elemento de la lista previamente llenada
                for(int i = 0; i<listaId.size(); i++){
                    //En caso de encontrar coincidencia, asignar los valores a la instancia
                    if (listaId.get(i) == leerInt) {
                        this.Id_Tarea = listaId.get(i);
                        this.nombre = listaNombre.get(i);
                    }
                }
            } else{
                //Si el nombre es null significa que no se encontro ningun Dev
                this.nombre = null;
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en verTareas\n" + e);
        }
    }

    /*Antiguo metodo v3.9
        public void consultarNombreTarea(int Id_Tarea, int equipo){
        this.nombre = "";
        //Crear query para obtener info de la Tarea
        query = "SELECT Id_Tarea, nombre FROM tarea WHERE Id_Tarea = " + Id_Tarea + " AND equipo = " + equipo;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta de la Tarea
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                //Asigna los datos de la Tarea a la instancia
                this.Id_Tarea = rs.getInt("Id_Tarea");
                this.nombre = rs.getString("nombre");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en consultarNombreTarea\n" + e);
        }
        
    }
 */

    public void actualizarInformacion(){
        String leerString = "";
        //Scanners de lectura
        Scanner scanString = new Scanner(System.in);
        //Pedir dato por dato y asignarlo a la clase
        System.out.print("Nombre: ");
        this.nombre = scanString.nextLine();
        System.out.print("Descripción: ");
        this.descripcion = scanString.nextLine();
        System.out.println("Prioridad (Baja, Media, Alta): ");
        leerString = scanString.nextLine().toLowerCase();
        if (leerString.equals("baja")) {
            this.prioridad = "Baja";
            this.n_Prioridad = 1;
        } else if(leerString.equals("media")){
            this.prioridad = "Media";
            this.n_Prioridad = 2;
        } else if(leerString.equals("alta")){
            this.prioridad = "Alta";
            this.n_Prioridad = 3;
        } else{
            this.prioridad = "Baja";
            this.n_Prioridad = 1;
        }

        //Crear query para actualizar los datos de la Tarea
        query = "UPDATE tarea SET nombre = '" + this.nombre + "', descripcion = '" + this.descripcion + "', prioridad = '" + 
            this.prioridad + "', n_Prioridad = " + this.n_Prioridad + " WHERE Id_Tarea = " + this.Id_Tarea;
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

    public void borrarTarea(){
        //Crear query para eliminar la Tarea
        query = "DELETE FROM tarea WHERE Id_Tarea = " + this.Id_Tarea;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la eliminacion del registro
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Tarea eliminada con exito!");
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en borrarTarea\n" + e);
        }
    }

    public void verTareasAsignadas(int desarrollador){
        //Crear query que obtiene las Tareas del Dev
        query = "SELECT Id_Tarea, equipo, t.nombre AS tarea, prioridad, porcentaje, p.nombre AS proyecto FROM proyecto AS p " +
            "INNER JOIN tarea AS t ON p.Id_Proyecto = t.proyecto " + 
            "INNER JOIN avance ON Id_Tarea = tarea " + 
            "WHERE t.usuario = " + desarrollador;
        //Crear una lista que almacenara los id impresos
        List<Integer> listaId = new ArrayList<>();
        List<String> listaNombre = new ArrayList<>();
        List<Integer> listaEquipo = new ArrayList<>();

        //Crear variables para leer el ID
        int leerInt = 0;
        Scanner scanInt = new Scanner(System.in);

        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta de las Tareas
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Imprime tantas tareas tenga el Dev
                System.out.println("ID: " + rs.getInt("Id_Tarea"));
                System.out.println("Proyecto: " + rs.getString("proyecto"));
                System.out.println("Nombre: " + rs.getString("tarea"));
                System.out.println("Prioridad: " + rs.getString("prioridad"));
                System.out.println("Porcentaje: " + rs.getString("porcentaje") + "%");
                System.out.println("");
                //Aqui se van a ingresar los Id validos
                listaId.add(rs.getInt("Id_Tarea"));
                listaNombre.add(rs.getString("tarea"));
                listaEquipo.add(rs.getInt("equipo"));
            }
            //Se valida si hay registros en la lista
            if (listaId.size()>0) {
                //Una vez terminado de imprimir, preguntar por el ID al que quiere ingresar
                System.out.print("Id Tarea: ");
                leerInt = scanInt.nextInt();
                //Definir el nombre de la instancia en blanco en caso de no encontrar coincidencia
                this.nombre = "";
                //Ahora se compara si el Id fue valido viendo cada elemento de la lista previamente llenada
                for(int i = 0; i<listaId.size(); i++){
                    //En caso de encontrar coincidencia, asignar los valores a la instancia
                    if (listaId.get(i) == leerInt) {
                        this.Id_Tarea = listaId.get(i);
                        this.nombre = listaNombre.get(i);
                        this.equipo = listaEquipo.get(i);
                    }
                }
            } else{
                //Si el nombre es null significa que no se encontro ninguna tarea
                this.nombre = null;
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en verTareasAsignadas\n" + e);
        }
    }

    /*Antiguo metodo v3.9
    public void consultarNombreTareaAsignada(int Id_Tarea, int desarrollador){
        this.nombre = "";
        //Crear query para obtener info de la Tarea
        query = "SELECT Id_Tarea, nombre, proyecto, equipo FROM tarea WHERE Id_Tarea = " + Id_Tarea + " AND usuario = " + desarrollador;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta de la Tarea
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                //Asigna los datos de la Tarea a la instancia
                this.Id_Tarea = rs.getInt("Id_Tarea");
                this.nombre = rs.getString("nombre");
                this.proyecto = rs.getInt("proyecto");
                this.equipo = rs.getInt("equipo");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en consultarNombreTareaAsginada\n" + e);
        }
        
    }
 */
    public double marcarAvance(){
        double porcentaje = 0;
        String fecha = new Date(System.currentTimeMillis()).toString();
        //Query para saber el porcentaje de la tarea
        query = "SELECT porcentaje from avance WHERE tarea = " + this.Id_Tarea;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta del porcentaje
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                //Se evalua el porcentaje del avance de la tarea. Si es 0, crear query que la haga 50, si es 50, crear query para completarla
                if(rs.getDouble("porcentaje") == 0){
                    porcentaje = 50;
                    query = "UPDATE avance SET porcentaje = 50, fecha = '" + fecha + "' WHERE tarea = " + this.Id_Tarea;
                    System.out.println("Tarea en su 50%");
                } else{
                    porcentaje = 100;
                    //Ademas de marcar el avance, se debe desasignar la tarea (0)
                    query = "UPDATE avance INNER JOIN tarea ON Id_Tarea = tarea SET usuario = 0, porcentaje = 100, fecha = '" + fecha + "' WHERE tarea = " + this.Id_Tarea;
                    System.out.println("Tarea completada con éxito");
                }
                //Ejecuta la actualizacion
                stmt.executeUpdate(query);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en marcarAvance\n" + e);
        }
        return porcentaje;
    }

    public void dejarComentario(){
        String fecha = new Date(System.currentTimeMillis()).toString();
        Scanner scanString = new Scanner(System.in);
        String comentario = "";
        //Se solicita el comentario
        System.out.print("Comentario: ");
        comentario = scanString.nextLine();
        //Query que actualiza el comentario de un avance
        query = "UPDATE avance SET comentario = '" + comentario + "', fecha = '" + fecha + "' WHERE tarea = " + this.Id_Tarea;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la actualizacion
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            //Desconectarse de la DB
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en dejarComentario\n" + e);
        }
    }

    public void desasignarTarea(){
        //Se crea la variable fecha con la fecha actual para actualizar el avance
        String fecha = new Date(System.currentTimeMillis()).toString();
        //Crear query para actualizar el estado de la tarea a desasignado (usuario = 0)
        query = "UPDATE tarea SET usuario = 0 WHERE Id_Tarea = " + this.Id_Tarea;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la actualizacion de la Tarea
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            //Crear query para regresar el avance al estado inicial
            query = "UPDATE avance SET porcentaje = 0, comentario = '', fecha = '" + fecha + "' WHERE tarea = " + this.Id_Tarea;
            stmt.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en desasignarTarea\n" + e);
        }
    }




    //Constructor
    public Tarea() {
    }

    //Getters n Setters
    public int getId_Tarea() {
        return this.Id_Tarea;
    }

    public void setId_Tarea(int Id_Tarea) {
        this.Id_Tarea = Id_Tarea;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrioridad() {
        return this.prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public int getN_Prioridad() {
        return this.n_Prioridad;
    }

    public void setN_Prioridad(int n_Prioridad) {
        this.n_Prioridad = n_Prioridad;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(int proyecto) {
        this.proyecto = proyecto;
    }

    public int getEquipo() {
        return this.equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

    public int getUsuario() {
        return this.usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getCreador() {
        return this.creador;
    }

    public void setCreador(int creador) {
        this.creador = creador;
    }


}
