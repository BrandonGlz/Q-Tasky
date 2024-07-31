package Clases;

import java.sql.*;

import Logica.Conexion;

public class Avance {
    //Atributos
    private int Id_Avance;
    private double porcentaje;
    private String fecha;
    private String comentario;
    private int tarea;
    private int tablero;
    //Variables
    String query = "";

    //Metodos
    public void insertarAvance(int tarea, int tablero){
        this.fecha = new Date(System.currentTimeMillis()).toString();
        //Query para insertar avance cuando se crea una nueva tarea
        query = "INSERT INTO avance(porcentaje, fecha, tarea, tablero) " + 
            "VALUES(0,'"+ this.fecha +"'," + tarea + "," + tablero+ ")";
        //Crea una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Se ejecuta la insercion del nuevo Avance
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            //Terminar conexion con la DB
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en insertarAvance\n" + e);
        }
    }


    //Constructor
    public Avance() {
    }

    //Getters n Setters
    public int getId_Avance() {
        return this.Id_Avance;
    }

    public void setId_Avance(int Id_Avance) {
        this.Id_Avance = Id_Avance;
    }

    public double getPorcentaje() {
        return this.porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return this.comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getTarea() {
        return this.tarea;
    }

    public void setTarea(int tarea) {
        this.tarea = tarea;
    }

    public int getTablero() {
        return this.tablero;
    }

    public void setTablero(int tablero) {
        this.tablero = tablero;
    }

}
