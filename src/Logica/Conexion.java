package Logica;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public String hostDB = "localhost"; //Cambiar a "192.168.10.20" al usar REDES
    public String db = "gestion_de_tareas"; //Nombre de la base de datos
    public String urlDB = "jdbc:mysql://" + hostDB + ":3306/" + db; //Ruta completa para hacer la conexion
    public String usuarioDB = "root"; //Cambiar a "cisco" al usar REDES
    public String contraseñaDB = ""; //Cambiar a "class" al usar  REDES

    public Conexion(){
    }

    public Connection conectarDB(){
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(urlDB, usuarioDB, contraseñaDB);
        } catch (Exception e) {
            System.out.println("Fallo en la conexion\n" + e);
        }
        return conexion;
    }
}
