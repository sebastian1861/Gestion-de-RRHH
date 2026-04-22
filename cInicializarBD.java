package Controlador; 

import java.sql.Connection; 
import java.sql.SQLException; 
import java.sql.Statement; 
import javax.swing.JOptionPane; 

//@author Hector Mojica,Cesar Rojas, Sebastian Medina

public class cInicializarBD { 

    public static void crearTablas() { // metodo publico que se dispara solo
        Connection con = cConexion.getConexion(); // absorbe el puente creado
        if (con == null) return; // si fracaso el puente no construye nada

        try (Statement st = con.createStatement()) { // pide un mensajero puro y en bruto

            // instruccion usuarios 
            st.execute( // ejecuta en el motor un bloque de texto sql
                "CREATE TABLE IF NOT EXISTS T_Usuarios (" + // levanta tabla de login condicional
                "  ID_Usuario  INTEGER PRIMARY KEY AUTOINCREMENT," + // llave primaria autonumerica
                "  Usuario     TEXT NOT NULL UNIQUE," + // usuario unico sin vacios
                "  Password    TEXT NOT NULL," + // contrasena obligatoria
                "  Nombre      TEXT," + // alias humano
                "  Rol         TEXT DEFAULT 'admin'" + // puesto del empleado predefinido
                ");" // fin del create usuarios
            );

            // semilla inicial 
            st.execute( // manda orden de insertado
                "INSERT OR IGNORE INTO T_Usuarios (Usuario, Password, Nombre, Rol) " + // trata de meter esto a la fuerza pero ignora si choca
                "VALUES ('admin', 'admin123', 'Administrador', 'admin');" // los datos de dios del sistema
            );

            // instruccion empleados 
            st.execute( // dispara constructor de tabla central
                "CREATE TABLE IF NOT EXISTS T_Empleados (" + // la tabla mas gorda
                "  ID_Empleado   INTEGER PRIMARY KEY AUTOINCREMENT," + // identificador empleado
                "  Cedula        TEXT NOT NULL UNIQUE," + // el dni que jamas se repite
                "  Nombre        TEXT NOT NULL," + // columna obligatoria
                "  Apellido      TEXT NOT NULL," + // columna obligatoria
                "  Fec_Nacimiento TEXT," + // dia de nacimiento texto
                "  Genero        TEXT," + // m o f
                "  Telefono      TEXT," + // celu
                "  Correo        TEXT," + // email
                "  Direccion     TEXT," + // ubicacion
                "  Cargo         TEXT," + // profesion
                "  Departamento  TEXT," + // gerencia
                "  Fec_Ingreso   TEXT," + // desde cuando es esclavo
                "  Salario_Base  REAL DEFAULT 0," + // lo minimo que cobra con numeros flotantes
                "  Estado        TEXT DEFAULT 'Activo'" + // no ha sido despedido todavia
                ");" // termina create empleados
            );

            // instruccion asistencia 
            st.execute( // manda hacer el reloj checador
                "CREATE TABLE IF NOT EXISTS T_Asistencia (" + // tabla de las madrugadas
                "  ID_Asistencia INTEGER PRIMARY KEY AUTOINCREMENT," + // llave
                "  ID_Empleado   INTEGER NOT NULL," + // quien ficho
                "  Fecha         TEXT NOT NULL," + // cuando ficho
                "  Hora_Entrada  TEXT," + // abriendo ojo
                "  Hora_Salida   TEXT," + // libre
                "  Horas_Trabajadas REAL DEFAULT 0," + // cuantas horas dio su vida
                "  Estado        TEXT DEFAULT 'Presente'," + // vino o lloro
                "  Observacion   TEXT," + // llego borracho nota
                "  FOREIGN KEY (ID_Empleado) REFERENCES T_Empleados(ID_Empleado)" + // amarre total a la tabla matriz
                ");" // finaliza tabla checador
            );

            // instruccion nomina 
            st.execute( // la fabrica del dinero
                "CREATE TABLE IF NOT EXISTS T_Nomina (" + // billetera historica
                "  ID_Nomina     INTEGER PRIMARY KEY AUTOINCREMENT," + // autonumerico dinero
                "  ID_Empleado   INTEGER NOT NULL," + // de quien son los billetes
                "  Periodo       TEXT NOT NULL," + // de que mes es
                "  Salario_Base  REAL DEFAULT 0," + // su tarifa basica numerica
                "  Horas_Trabajadas REAL DEFAULT 0," + // que tanto trabajo ese periodo
                "  Bonificaciones REAL DEFAULT 0," + // premios en plata
                "  Deducciones   REAL DEFAULT 0," + // castigos en plata
                "  Salario_Neto  REAL DEFAULT 0," + // suma y resta final
                "  Fec_Pago      TEXT," + // dia del pago real
                "  Estado        TEXT DEFAULT 'Pendiente'," + // aun no cobrado
                "  Observacion   TEXT," + // comentario anexo
                "  FOREIGN KEY (ID_Empleado) REFERENCES T_Empleados(ID_Empleado)" + // amarre a empleado inquebrantable
                ");" // fin tabla pagos
            );

            // instruccion deudas 
            st.execute( // descuentos
                "CREATE TABLE IF NOT EXISTS T_Deducciones (" + // se la deben
                "  ID_Deduccion  INTEGER PRIMARY KEY AUTOINCREMENT," + // id castigo
                "  ID_Empleado   INTEGER NOT NULL," + // de quien
                "  Tipo          TEXT NOT NULL," + // clasificacion mora
                "  Monto         REAL DEFAULT 0," + // cuanto duele
                "  Descripcion   TEXT," + // por que duele
                "  Fecha         TEXT," + // cuando sucedio
                "  Activo        INTEGER DEFAULT 1," + // sigue descontando si no esta en cero
                "  FOREIGN KEY (ID_Empleado) REFERENCES T_Empleados(ID_Empleado)" + // amarre a la victima
                ");" // cierra ultima tabla
            );

        } catch (SQLException ex) { // si no pudo levantar las paredes de la base
            JOptionPane.showMessageDialog(null, // reporta el desastre
                "Error al inicializar la base de datos:\n" + ex.getMessage(), // tira la descripcion
                "Error BD", JOptionPane.ERROR_MESSAGE); // pantalla grave
        }
    }
}