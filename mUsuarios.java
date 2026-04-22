package Modelo;

import Controlador.cConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

//@author Hector Mojica,Cesar Rojas, Sebastian Medina
public class mUsuarios { // declara clase publica musuarios

    // verifica credenciales y devuelve nombre
    
    public String autenticar(String usuario, String password) { // pide logeo y clave
        String sql = "SELECT Nombre FROM T_Usuarios WHERE Usuario=? AND Password=?"; // formula select simple validando ambos campos
        Connection con = cConexion.getConexion(); // toma la carretera a sqlite
        if (con == null) return null; // si hay corte de via devuelve nulo

        try (PreparedStatement pst = con.prepareStatement(sql)) { // arma la valija con la instruccion
            pst.setString(1, usuario); // mete user
            pst.setString(2, password); // mete clave
            try (ResultSet rs = pst.executeQuery()) { // pregunta al guardia de la base
                if (rs.next()) { // si el guardia lo encuentra
                    return rs.getString("Nombre"); // devuelve como se llama el fulano
                }
            }
        } catch (SQLException ex) { // si el guardia le dispara por error
            JOptionPane.showMessageDialog(null, // muestra mensaje
                "Error al autenticar:\n" + ex.getMessage(), // dice que salio mal
                "Error", JOptionPane.ERROR_MESSAGE); // estilo alerta
        }
        return null; // si no lo encontro no devuelve nombre
    }

    // actualiza la contrasena en la base de datos
     
    public boolean cambiarPassword(String usuario, String passActual, String passNueva) { // pide quien eres tu clave vieja y tu clave nueva
        if (autenticar(usuario, passActual) == null) { // revisa si el metodo de arriba te aprueba la identidad
            JOptionPane.showMessageDialog(null, // si te reprueba
                "La contraseña actual es incorrecta.", // te lo dice en la cara
                "Error", JOptionPane.WARNING_MESSAGE); // un mensajito naranja
            return false; // y te cancela la operacion
        }
        String sql = "UPDATE T_Usuarios SET Password=? WHERE Usuario=?"; // instruccion sql para machacar clave vieja
        Connection con = cConexion.getConexion(); // pide puente
        if (con == null) return false; // sin puente no hay paso

        try (PreparedStatement pst = con.prepareStatement(sql)) { // alista herramienta
            pst.setString(1, passNueva); // pone lo nuevo primero
            pst.setString(2, usuario); // le dice de quien es
            pst.executeUpdate(); // escribe sobre piedra
            return true; // canta victoria
        } catch (SQLException ex) { // en caso de terremoto
            JOptionPane.showMessageDialog(null, // avisa a la gente
                "Error al cambiar contraseña:\n" + ex.getMessage(), // lee que paso
                "Error", JOptionPane.ERROR_MESSAGE); // caja rojita
            return false; // llora derrota
        }
    }
}
