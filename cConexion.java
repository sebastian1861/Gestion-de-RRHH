package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


 //@author Hector Mojica,Cesar Rojas, Sebastian Medina
 
public class cConexion {

    // una única instancia de la conexión 
    private static Connection instancia = null;

    // Ruta de la BD relativa al proyecto funciona en cualquier PC
    private static final String RUTA_BD = "Datos/RRHH.db";
    private static final String URL = "jdbc:sqlite:" + RUTA_BD;

    // Constructor privado  no se instancia directamente
    public cConexion() {}

    // Devuelve la conexión única y abierta
     //Si está cerrada o nula, la reabre automáticamente
     
    public static Connection getConexion() {
        try {
            if (instancia == null || instancia.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                instancia = DriverManager.getConnection(URL);

                // Configuración crítica para SQLite
                // WAL evita bloqueos en escrituras concurrentes
                try (Statement st = instancia.createStatement()) {
                    st.execute("PRAGMA journal_mode=WAL;");
                    st.execute("PRAGMA synchronous=NORMAL;");
                    st.execute("PRAGMA foreign_keys=ON;");
                }
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                "Driver SQLite no encontrado.\nVerifica que sqlite-jdbc.jar esté en las librerías.\n" + ex.getMessage(),
                "Error de Driver", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                "No se pudo abrir la base de datos:\n" + ex.getMessage(),
                "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
        return instancia;
    }

    // Cierra la conexión de forma segura llamar al cerrar la app
    public static void cerrarConexion() {
        if (instancia != null) {
            try {
                if (!instancia.isClosed()) {
                    instancia.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            } finally {
                instancia = null;
            }
        }
    }
}
