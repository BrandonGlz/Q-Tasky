package Clases;

import java.sql.*;
import Logica.Conexion;

public class Tablero {
    //Atributos
    private int Id_Tablero;
    private int estado_No_Iniciado;
    private int estado_Iniciado;
    private int estado_Finalizado;
    private String comentario;
    private int equipo;
    //Variables
    String query = "";


    //Metodos
    public void insertarTablero(int equipo){
        //Crear query para insertar un nuevo Tablero
        query = "INSERT INTO tablero(equipo) VALUES(" + equipo +")";
        //Crea una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Se ejecuta la insercion del nuevo Tablero
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            //Terminar conexion con la DB
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en insertarTablero\n" + e);
        }
    }

    public void calcularEstados(int tablero){
        //Crear queries que cuentan los estados de sus avances
        //Esta query es para el estado No_Iniciado
        query = "SELECT COUNT(Id_Avance) AS n FROM avance WHERE porcentaje = 0 AND tablero = " + tablero;
        //Crea una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Se ejecuta la actualizacion de los estados del Tablero
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //Primero se lee cuantos avances hay en 0
            if (rs.next()) {
                //Aqui se actualiza el tablero con n (n = cuantos avances hay en 0)
                query = "UPDATE tablero SET estado_No_Iniciado = " + rs.getInt("n") + " WHERE Id_Tablero = " + tablero;
                stmt.executeUpdate(query);
            }
            //Se crea una query para los estados iniciado y finalizado
            query = "SELECT COUNT(Id_Avance) AS n FROM avance WHERE porcentaje = 50 AND tablero = " + tablero;
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                query = "UPDATE tablero SET estado_Iniciado = " + rs.getInt("n") + " WHERE Id_Tablero = " + tablero;
                stmt.executeUpdate(query);
            }
            //Finalizado
            query = "SELECT COUNT(Id_Avance) AS n FROM avance WHERE porcentaje = 100 AND tablero = " + tablero;
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                query = "UPDATE tablero SET estado_Finalizado = " + rs.getInt("n") + " WHERE Id_Tablero = " + tablero;
                stmt.executeUpdate(query);
            }
            //Terminar conexion con la DB
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en calcularEstados\n" + e);
        }
    }

    public void calcularAvance(int equipo){
        int proyecto = 0;
        //Query para conseguir el proyecto del equipo
        query = "SELECT proyecto FROM equipo WHERE Id_Equipo = " + equipo;
        //Crea una conexion
        Conexion c = new Conexion();
        //Establece la conexion
        Connection connection = c.conectarDB();
        try {
            //Se ejecuta la insercion del nuevo Tablero
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                proyecto = rs.getInt("proyecto");
            }
            //Una vez sabiendo el proyecto, se puede calcular el avance. En las siguientes variables se guardaran los valores correspondientes
            double totalAvance = 0, iniciado = 0, finalizado = 0, totalTareas = 0;
            //Query que trae los tableros del proyecto
            query = "SELECT estado_No_Iniciado, estado_Iniciado, estado_Finalizado FROM tablero " + 
                "INNER JOIN equipo ON equipo = Id_Equipo WHERE proyecto = " + proyecto;
            rs = stmt.executeQuery(query);
            //Se usa While en vez de If por si son varios registros (tableros)
            while (rs.next()) {
                iniciado = 50 * rs.getInt("estado_Iniciado");
                finalizado = 100 * rs.getInt("estado_Finalizado");
                //Acumulador
                totalAvance = totalAvance + iniciado + finalizado;
                //Contador
                totalTareas = totalTareas + rs.getInt("estado_No_Iniciado")+ rs.getInt("estado_Iniciado")+ rs.getInt("estado_Finalizado");
            }
            //Calcular el promedio de avance
            totalAvance = totalAvance / totalTareas;
            //Crear query que actualice el avance del proyecto
            query = "UPDATE proyecto SET avance = " + totalAvance + " WHERE Id_Proyecto = " + proyecto;
            stmt.executeUpdate(query);
            
            //Terminar conexion con la DB
            connection.close();
        } catch (Exception e) {
            System.out.println("Fallo en calcularAvance\n" + e);
        }

    }




    //Constructor
    public Tablero() {
    }

    //Getters n Setters
    public int getId_Tablero() {
        return this.Id_Tablero;
    }

    public void setId_Tablero(int Id_Tablero) {
        this.Id_Tablero = Id_Tablero;
    }

    public int getEstado_No_Iniciado() {
        return this.estado_No_Iniciado;
    }

    public void setEstado_No_Iniciado(int estado_No_Iniciado) {
        this.estado_No_Iniciado = estado_No_Iniciado;
    }

    public int getEstado_Iniciado() {
        return this.estado_Iniciado;
    }

    public void setEstado_Iniciado(int estado_Iniciado) {
        this.estado_Iniciado = estado_Iniciado;
    }

    public int getEstado_Finalizado() {
        return this.estado_Finalizado;
    }

    public void setEstado_Finalizado(int estado_Finalizado) {
        this.estado_Finalizado = estado_Finalizado;
    }

    public String getComentario() {
        return this.comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getEquipo() {
        return this.equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

}
