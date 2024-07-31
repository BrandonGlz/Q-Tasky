package Clases;

import java.sql.*;
import java.util.Scanner;

import Logica.Conexion;

public class Persona {
    //Atributos
    private int Id_Persona;
    private String nombre;
    private String ape_Paterno;
    private String ape_Materno;
    private String correo_Electronico;
    private int edad;
    private String genero;
    private int puesto;

    //Variables
    String query = "";


    //Metodos
    public void llenarDatos(int puesto){
        Scanner scanString = new Scanner(System.in);
        Scanner scanInt = new Scanner(System.in);

        //Pedir al usuario atributo por atributo de la Persona
        System.out.print("Nombre: ");
        this.nombre = scanString.nextLine();
        System.out.print("Apellido Paterno: ");
        this.ape_Paterno = scanString.nextLine();
        System.out.print("Apellido Materno: ");
        this.ape_Materno = scanString.nextLine();
        System.out.print("Correo Electronico: ");
        this.correo_Electronico = scanString.nextLine();
        System.out.print("Edad: ");
        this.edad = scanInt.nextInt();
        System.out.print("Genero: ");
        this.genero = scanString.nextLine();
        //puesto es 1 si se crea un Admin, 2 si se crea un Dev
        this.puesto = puesto;
    }

    public int insertarPersona(int puesto){
        //Pedir los datos de la Persona
        llenarDatos(puesto);

        //Crea una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();

        //Query para insertar un nueva persona
        query = "INSERT INTO persona(nombre, ape_Paterno, ape_Materno, correo_Electronico, edad, genero, puesto)" +
            "VALUES('" + this.nombre + "','" + this.ape_Paterno + "','" + this.ape_Materno + "','" +
            this.correo_Electronico + "'," + this.edad + ",'" + this.genero + "'," + this.puesto + ")";

        try {
            //Se ejecuta la insercion de la nueva persona
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);

            //Renombrar la Query para traer el Id de la persona recien insertada
            query = "SELECT Id_Persona FROM persona ORDER BY Id_Persona DESC LIMIT 1";
            //Se ejecuta 
            ResultSet rs = stmt.executeQuery(query);
            //Lectura de los datos
            if(rs.next()){
                //Asignar el Id de la DB al objeto Persona
                this.Id_Persona = rs.getInt("Id_Persona");
            }
            //Terminar conexion con la DB
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en la conexion\n" + e);
        }
        //Regresa el Id para asignarselo a la FK "persona" en Usuario
        return this.Id_Persona;
    }


    
    //Constructores
    public Persona() {
    }

    public Persona(int Id_Persona, String nombre, String ape_Paterno, String ape_Materno, String correo_Electronico, int edad, String genero, int puesto) {
        this.Id_Persona = Id_Persona;
        this.nombre = nombre;
        this.ape_Paterno = ape_Paterno;
        this.ape_Materno = ape_Materno;
        this.correo_Electronico = correo_Electronico;
        this.edad = edad;
        this.genero = genero;
        this.puesto = puesto;
    }

    //Getters n Setters
    public int getId_Persona() {
        return this.Id_Persona;
    }

    public void setId_Persona(int Id_Persona) {
        this.Id_Persona = Id_Persona;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe_Paterno() {
        return this.ape_Paterno;
    }

    public void setApe_Paterno(String ape_Paterno) {
        this.ape_Paterno = ape_Paterno;
    }

    public String getApe_Materno() {
        return this.ape_Materno;
    }

    public void setApe_Materno(String ape_Materno) {
        this.ape_Materno = ape_Materno;
    }

    public String getCorreo_Electronico() {
        return this.correo_Electronico;
    }

    public void setCorreo_Electronico(String correo_Electronico) {
        this.correo_Electronico = correo_Electronico;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getPuesto() {
        return this.puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

}
