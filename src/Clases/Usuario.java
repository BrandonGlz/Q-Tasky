package Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Logica.Conexion;

public class Usuario extends Persona{
    //Atributos
    private int Id_Usuario;
    private String privilegios;
    private String nombre_Usuario;
    private String contraseña;
    private int Id_Creador;
    private int persona;

    //Variables
    String query = "";

    //Crear recursos de DB

    //Metodos
    public void llenarDatos(String privilegios, String nombre, int Id_Persona, String paterno, int creador){
        //Se llenan los datos del Usuario basandose en los de su Persona
        Random ran = new Random();
        int random = ran.nextInt(899) + 100;

        this.privilegios = privilegios;
        this.nombre_Usuario = nombre.toLowerCase() + "." + Id_Persona;
        this.contraseña = paterno.toLowerCase() + "." + random;
        this.Id_Creador = creador;
    }

    public void insertarUsuario(int puesto, int creador){
        //Para crear un Usuario, primero debe existir su Persona
        Persona per = new Persona();
        //Definir la FK persona para Usuario despues de insertar la Persona en la DB
        this.persona = per.insertarPersona(puesto);

        //Admin tiene puesto 1, Dev tiene puesto 2
        if (puesto==1) {
            llenarDatos("Si", per.getNombre(), per.getId_Persona(), per.getApe_Paterno(),creador);
        }
        else{
            llenarDatos("No", per.getNombre(), per.getId_Persona(), per.getApe_Paterno(), creador);
        }

        //Crear query para insertar el Usuario en la DB
        query = "INSERT INTO usuario(privilegios, nombre_Usuario, contraseña, Id_Creador, persona)" +
            "VALUES('" + this.privilegios + "','" + this.nombre_Usuario + "','" + this.contraseña + "'," +
            this.Id_Creador + "," + this.persona + ")";

        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Se ejecuta la insercion de la nueva persona
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            //Solo se ejecuta al crear un Admin
            if (puesto == 1) {
                //Renombrar la Query para traer el Id del usuario recien insertado
                query = "SELECT Id_Usuario FROM usuario ORDER BY Id_Usuario DESC LIMIT 1";
                //Se ejecuta 
                ResultSet rs = stmt.executeQuery(query);
                //Lectura de los datos
                if(rs.next()){
                    //Asignar el valor del Id Usuario al Id Creador
                    query = "UPDATE usuario SET Id_Creador = " + rs.getInt("Id_Usuario") + " WHERE Id_Usuario = " + rs.getInt("Id_Usuario");
                    stmt.executeUpdate(query);
                }
            }
            //Terminar la conexion con la DB
            System.out.println("\nUsuario ingresado con éxito!");
            System.out.println("Usuario: " + this.nombre_Usuario);
            System.out.println("Contraseña: " + this.contraseña);
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en insertarUsuario\n" + e);
        }
    }

    public String iniciarSesion(){
        Scanner scanString = new Scanner(System.in);
        this.privilegios = "null";
        //Pedir el usuario y contraseña
        System.out.print("Usuario: ");
        this.nombre_Usuario = scanString.nextLine();
        System.out.print("Contraseña: ");
        this.contraseña = scanString.nextLine();

        //Crear query que obtiene los datos del usuario si hay coincidencia con el nombre_Usuario
        query = "SELECT Id_Usuario, privilegios, nombre_Usuario, contraseña, Id_Persona FROM persona INNER JOIN usuario ON Id_Persona = persona WHERE nombre_Usuario = '" + this.nombre_Usuario + "'";
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta del usuario
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                //En caso de encontrar el usuario en la DB, comparar la contraseña
                if (this.contraseña.equals(rs.getString("contraseña"))) {
                    //(Si o No) Define que modulo mostrar (Admin o Dev)
                    this.privilegios = rs.getString("privilegios");
                    //Asignar el ID para despues hacer consultas mas facilmente
                    this.Id_Usuario = rs.getInt("Id_Usuario");
                    setId_Persona(rs.getInt("Id_Persona"));
                    System.out.println("Inicio de sesion exitoso!");
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en iniciarSesion\n" + e);
        }
        return this.privilegios;
    }

    public void mostrarInformacion(){
        //Crear query que obtiene los datos del Usuario y su Persona
        query = "SELECT nombre, ape_Paterno, ape_Materno, correo_Electronico, edad, genero, nombre_Usuario, contraseña FROM usuario INNER JOIN persona ON persona = Id_Persona WHERE Id_Usuario = " + this.Id_Usuario;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta del usuario
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Apellido Paterno: " + rs.getString("ape_Paterno"));
                System.out.println("Apellido Materno: " + rs.getString("ape_Materno"));
                System.out.println("Correo Electrónico: " + rs.getString("correo_Electronico"));
                System.out.println("Edad: " + rs.getInt("edad"));
                System.out.println("Genero: " + rs.getString("genero"));
                System.out.println("Usuario: " + rs.getString("nombre_Usuario"));
                System.out.println("Contraseña: " + rs.getString("contraseña"));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en mostrarInformacion\n" + e);
        }
    }

    public void mostrarDesarrolladores(int creador){
        //Crear query para traer todos los Devs del Admin
        query = "SELECT nombre, ape_Paterno, ape_Materno, correo_Electronico, Id_Usuario, nombre_Usuario FROM usuario INNER JOIN persona ON persona = Id_Persona WHERE Id_Creador = " + creador;
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
            //Ejecutar la consulta de los Devs
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Esta condicion hace que no imprima los datos del Admin
                if (rs.getInt("Id_Usuario") != creador) {
                    System.out.println("Id: " + rs.getInt("Id_Usuario"));
                    System.out.println("Nombre: " + rs.getString("nombre") + " " + rs.getString("ape_Paterno") + " " + rs.getString("ape_Materno"));
                    System.out.println("Correo Electrónico: " + rs.getString("correo_Electronico"));
                    System.out.println("Usuario: " + rs.getString("nombre_Usuario"));
                    System.out.println("");
                    //Aqui se van a ingresar los Id validos
                    listaId.add(rs.getInt("Id_Usuario"));
                    listaNombre.add(rs.getString("nombre_Usuario"));
                }
            }

            //Se valida si hay registros en la lista
            if (listaId.size()>0) {
                //Una vez terminado de imprimir, preguntar por el ID al que quiere ingresar
                System.out.print("Id Desarrollador: ");
                leerInt = scanInt.nextInt();
                //Definir el nombre de la instancia como vacio en caso de no encontrar coincidencia
                this.nombre_Usuario = "";
                //Ahora se compara si el Id fue valido viendo cada elemento de la lista antes llenada
                for(int i = 0; i<listaId.size(); i++){
                    //En caso de encontrar coincidencia, asignar los valores a la instancia
                    if (listaId.get(i) == leerInt) {
                        this.Id_Usuario = listaId.get(i);
                        this.nombre_Usuario = listaNombre.get(i);
                    }
                }
            } else{
                //Si el nombre es null significa que no se encontro ningun Dev
                this.nombre_Usuario = null;
            }
            
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en mostrarDesarrolladores\n" + e);
        }

    }

    /*Antiguo metodo v3.9
    public String consultarNombreUsuario(int Id_Usuario, int creador){
        //Define el nombre en blanco en caso de no encontrarlo
        this.nombre_Usuario = "";
        query = "SELECT Id_Usuario, nombre_Usuario FROM usuario WHERE Id_Usuario = " + Id_Usuario + " AND Id_Creador = " + creador;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta del nombre
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                this.nombre_Usuario = rs.getString("nombre_Usuario");
                //Puede que esto cause problemas en un futuro, se hizo con el fin de ahorrar tiempo evitando un INNER JOIN
                //Definir el Id servirá en un futuro para realizar actualizaciones del mismo
                this.Id_Usuario = rs.getInt("Id_Usuario");
                setId_Persona(Id_Usuario);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en consultarNombreUsuario\n" + e);
        }
        return this.nombre_Usuario;
    }
 */
    public void actualizarInformacion(){
        //Scanners de lectura
        Scanner scanString = new Scanner(System.in);
        Scanner scanInt = new Scanner(System.in);
        //Pedir dato por dato y asignarlo a la clase
        System.out.print("Nombre: ");
        setNombre(scanString.nextLine());
        System.out.print("Apellido Paterno: ");
        setApe_Paterno(scanString.nextLine());
        System.out.print("Apellido Materno: ");
        setApe_Materno(scanString.nextLine());
        System.out.print("Correo Electrónico: ");
        setCorreo_Electronico(scanString.nextLine());
        System.out.print("Edad: ");
        setEdad(scanInt.nextInt());
        System.out.print("Genero: ");
        setGenero(scanString.nextLine());

        //Crear query para actualizar los datos del usuario
        query = "UPDATE persona SET nombre = '" + getNombre() + "', ape_Paterno = '" + getApe_Paterno() + "', ape_Materno = '" + 
            getApe_Materno() + "', correo_Electronico = '" + getCorreo_Electronico() + "', edad = " + getEdad() + 
            ", genero = '" + getGenero() + "' WHERE Id_Persona = " + getId_Persona();
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

    public void borrarUsuario(){
        //Crear query para eliminar al Dev
        query = "DELETE FROM usuario WHERE Id_Usuario = " + this.Id_Usuario;
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la eliminacion del registro
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            //Se crea la variable fecha con la fecha actual para actualizar los avances
            String fecha = new Date(System.currentTimeMillis()).toString();
            //Crear query para actualizar el estado de las tareas a desasignado (usuario = 0)
            query = "UPDATE tarea AS t INNER JOIN avance AS a ON t.Id_Tarea = a.tarea SET usuario = 0, porcentaje = 0, fecha = '" + fecha + "' WHERE usuario = " + this.Id_Usuario;
            stmt.executeUpdate(query);
            System.out.println("Desarrollador eliminado con exito!");
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en borrarUsuario\n" + e);
        }
    }

    public void asignarTarea(int creador){
        //Crear una lista que almacenara los id impresos
        List<Integer> listaId = new ArrayList<>();
        //Crear variables para leer el ID
        Scanner scanInt = new Scanner(System.in);
        int leerInt = 0, idTarea = 0;
        //Crear query que consulta todas las tareas del Admin (creador) y que estan desasignadas (0)
        query = "SELECT Id_Tarea, t.nombre AS tarea, prioridad, p.nombre AS proyecto FROM proyecto AS p " +
            "INNER JOIN tarea AS t ON p.Id_Proyecto = t.proyecto " +
            "INNER JOIN avance ON t.Id_Tarea = tarea " + 
            "WHERE creador = " + creador + " AND porcentaje = 0 AND t.usuario = 0";
        //Crear una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Ejecutar la consulta del nombre
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Imprime tantas tareas desasignadas tenga el Admin
                System.out.println("ID: " + rs.getInt("Id_Tarea"));
                System.out.println("Proyecto: " + rs.getString("proyecto"));
                System.out.println("Nombre: " + rs.getString("tarea"));
                System.out.println("Prioridad: " + rs.getString("prioridad"));
                System.out.println("");
                //Aqui se van a ingresar los Id validos
                listaId.add(rs.getInt("Id_Tarea"));
            }
            
            //Se valida si hay registros en la lista
            if (listaId.size()>0) {
                //Una vez terminado de imprimir, preguntar por el ID al que quiere ingresar
                System.out.print("Id Tarea: ");
                leerInt = scanInt.nextInt();
                //Definir el idTarea como 0 en caso de no encontrar coincidencia
                idTarea = 0;
                //Ahora se compara si el Id fue valido viendo cada elemento de la lista previamente llenada
                for(int i = 0; i<listaId.size(); i++){
                    //En caso de encontrar coincidencia, asignar los valores a idTarea
                    if (listaId.get(i) == leerInt) {
                        idTarea = listaId.get(i);
                    }
                }
            } else{
                //Si el id es -1 significa que no se encontro ninguna tarea
                idTarea = -1;
            }
            //Comparar el idTarea
            if (idTarea == -1) {
                System.out.println("No hay Tareas para asignar");
            } else if (idTarea == 0) {
                System.out.println("Tarea inválida");
            } else {
                //Crear query para asignar la tarea al Dev
                query = "UPDATE tarea SET usuario = " + this.Id_Usuario + " WHERE Id_Tarea = " + idTarea + " AND creador = " + creador;
                stmt.executeUpdate(query);
                //Tambien se debe actualizar la fecha del avance
                String fecha = new Date(System.currentTimeMillis()).toString();
                query = "UPDATE avance SET fecha = '" + fecha + "' WHERE tarea = " + idTarea;
                stmt.executeUpdate(query);

                System.out.println("Tarea asignada con éxito!");                
            }
            /*Antiguio metodo v3.9
            //Seleccionar un Id para asignar
            System.out.print("Id Tarea: ");
            leerInt = scanInt.nextInt();
            */
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en asignarTarea\n" + e);
        }
    }



    //Constructores
    public Usuario() {
    }

    public Usuario(int Id_Usuario, String privilegios, String nombre_Usuario, String contraseña, int persona, int Id_Persona, String nombre, String ape_Paterno, String ape_Materno, String correo_Electronico, int edad, String genero, int puesto) {
        super(Id_Persona, nombre, ape_Paterno, ape_Materno, correo_Electronico, edad, genero, puesto);
        this.Id_Usuario = Id_Usuario;
        this.privilegios = privilegios;
        this.nombre_Usuario = nombre_Usuario;
        this.contraseña = contraseña;
        this.persona = persona;
    }

    //Getters n Setters
    public int getId_Usuario() {
        return this.Id_Usuario;
    }

    public void setId_Usuario(int Id_Usuario) {
        this.Id_Usuario = Id_Usuario;
    }

    public String getPrivilegios() {
        return this.privilegios;
    }

    public void setPrivilegios(String privilegios) {
        this.privilegios = privilegios;
    }

    public String getNombre_Usuario() {
        return this.nombre_Usuario;
    }

    public void setNombre_Usuario(String nombre_Usuario) {
        this.nombre_Usuario = nombre_Usuario;
    }

    public String getContraseña() {
        return this.contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getPersona() {
        return this.persona;
    }

    public void setPersona(int persona) {
        this.persona = persona;
    }
    

}
