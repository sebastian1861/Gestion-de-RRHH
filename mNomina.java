package Modelo;

import Controlador.cConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//@author Hector Mojica,Cesar Rojas, Sebastian Medina

public class mNomina { // inicio de la clase que gestiona la nomina

    // calcular y guardar nomina
    public boolean generarNomina(int idEmpleado, String periodo, // metodo para insertar un registro de pago recibiendo id y periodo
            double salarioBase, double horasTrabajadas, // recibe valores numericos del pago
            double bonificaciones, double deducciones, String fechaPago, String obs) { // recibe bonos descuentos fecha y observaciones

        double salarioNeto = salarioBase + bonificaciones - deducciones; // formula matematica basica del salario
        if (salarioNeto < 0) salarioNeto = 0; // proteccion para evitar que el empleado quede con deuda negativa

        String sql = "INSERT INTO T_Nomina (ID_Empleado,Periodo,Salario_Base,Horas_Trabajadas," + // primera parte de la consulta de guardado
                     "Bonificaciones,Deducciones,Salario_Neto,Fec_Pago,Estado,Observacion) " + // segunda parte listando las columnas
                     "VALUES (?,?,?,?,?,?,?,?,'Pagado',?)"; // tercera parte con los parametros dinamicos y estado pagado por defecto

        Connection con = cConexion.getConexion(); // se conecta al archivo sqlite
        if (con == null) return false; // si se rompe la conexion cancela la operacion

        try (PreparedStatement pst = con.prepareStatement(sql)) { // arma la instruccion sql
            pst.setInt(1, idEmpleado); // coloca el id en el primer hueco
            pst.setString(2, periodo); // coloca el texto del periodo en el segundo
            pst.setDouble(3, salarioBase); // inserta salario base
            pst.setDouble(4, horasTrabajadas); // inserta las horas
            pst.setDouble(5, bonificaciones); // inserta plata extra
            pst.setDouble(6, deducciones); // inserta descuentos
            pst.setDouble(7, salarioNeto); // inserta el dinero real a pagar
            pst.setString(8, fechaPago); // inserta cuando se pago
            pst.setString(9, obs); // inserta algun comentario final
            pst.executeUpdate(); // dispara la consulta hacia la base de datos
            return true; // reporta operacion completa exitosamente
        } catch (SQLException ex) { // en caso de falla sql
            JOptionPane.showMessageDialog(null, "Error al generar nómina:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra error al usuario
            return false; // reporta fallo
        }
    }

    // calcular horas trabajadas en un periodo especifico
public double calcularHorasPeriodo(int idEmpleado, String fechaInicio, String fechaFin) { // funcion que devuelve decimales de horas

    try { // intenta hacer la conversion y calculo
        // convertir a formato correcto de bd
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // define el formato en el que viene el texto
        DateTimeFormatter db    = DateTimeFormatter.ISO_LOCAL_DATE; // define formato estandar de base de datos anio mes dia

        String inicio = LocalDate.parse(fechaInicio, input).format(db); // convierte el texto inicial al formato de la db
        String fin    = LocalDate.parse(fechaFin, input).format(db); // convierte el texto final al formato de la db

        String sql = "SELECT IFNULL(SUM(Horas_Trabajadas),0) FROM T_Asistencia " + // consulta sumando horas y previniendo nulos
                     "WHERE ID_Empleado=? AND Fecha BETWEEN ? AND ?"; // filtrando por id y rango de fechas

        Connection con = cConexion.getConexion(); // pide enlace a base de datos
        if (con == null) return 0; // si no hay retorna cero horas

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara la suma sql
            pst.setInt(1, idEmpleado); // pasa el id al filtro
            pst.setString(2, inicio); // pasa fecha de arranque
            pst.setString(3, fin); // pasa fecha de corte

            ResultSet rs = pst.executeQuery(); // ejecuta la suma
            if (rs.next()) return rs.getDouble(1); // si hay un resultado devuelve ese numero decimal
        }

    } catch (Exception e) { // captura errores de parseo de fechas
        JOptionPane.showMessageDialog(null, // lanza notificacion
            "Error en formato de fecha. Usa YYYY/MM/DD", // explica como deberia escribirse
            "Error", JOptionPane.ERROR_MESSAGE); // aspecto de error
    }

    return 0; // devuelve cero por defecto si algo salio mal
}
    // calcular deducciones activas de un empleado especifico
    public double calcularDeducciones(int idEmpleado) { // metodo que suma dinero a descontar
    String sql = "SELECT IFNULL(SUM(Monto),0) FROM T_Deducciones WHERE ID_Empleado=? AND Activo=1"; // selecciona descuentos vigentes sumados

    Connection con = cConexion.getConexion(); // activa conexion a db
    if (con == null) return 0; // corta si no existe conexion

    try (PreparedStatement pst = con.prepareStatement(sql)) { // arma sentencia
        pst.setInt(1, idEmpleado); // manda el empleado a consultar

        ResultSet rs = pst.executeQuery(); // trae el total sumado
        if (rs.next()) return rs.getDouble(1); // extrae la primera columna como decimal

    } catch (SQLException ex) { // por si falla la base
        JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage()); // muestra mensajito simple
    }

    return 0; // valor salvavidas si falla
}

    // listar historial general de nomina de todos
    public void listarHistorial(JTable tabla) { // rellena una tabla con pagos pasados
        String sql = "SELECT n.ID_Nomina, e.Nombre||' '||e.Apellido, n.Periodo, " + // selecciona id une nombre y apellido pide periodo
                     "n.Salario_Base, n.Horas_Trabajadas, n.Bonificaciones, " + // pide los detalles numericos del pago
                     "n.Deducciones, n.Salario_Neto, n.Fec_Pago, n.Estado " + // pide totales y fechas
                     "FROM T_Nomina n JOIN T_Empleados e ON n.ID_Empleado=e.ID_Empleado " + // mezcla tabla nomina con tabla empleados buscando correspondencia
                     "ORDER BY n.Fec_Pago DESC, e.Apellido"; // ordena desde el pago mas reciente y luego alfabeticamente

        Connection con = cConexion.getConexion(); // arranca conexion
        if (con == null) return; // sale de emergencia si es nula

        DefaultTableModel modelo = crearModeloHistorial(); // instancia estructura de tabla
        tabla.setModel(modelo); // pega la estructura vacia a la tabla grafica

        try (PreparedStatement pst = con.prepareStatement(sql); // alista la union
             ResultSet rs = pst.executeQuery()) { // procesa la busqueda gigante
            while (rs.next()) { // recorre todas las nominas existentes
                modelo.addRow(new Object[]{ // mete datos en filas
                    rs.getInt(1), rs.getString(2), rs.getString(3), // id empleado periodo
                    String.format("$ %.2f", rs.getDouble(4)), // base en dolares
                    String.format("%.1f h", rs.getDouble(5)), // horas con letra h
                    String.format("$ %.2f", rs.getDouble(6)), // bonos en dolares
                    String.format("$ %.2f", rs.getDouble(7)), // descuentos dolares
                    String.format("$ %.2f", rs.getDouble(8)), // neto en dolares
                    rs.getString(9), rs.getString(10) // fecha y estado
                });
            }
        } catch (SQLException ex) { // de fallar
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // saca cartel
        }
    }

    // listar nomina filtrando unicamente por un empleado
    public void listarPorEmpleado(JTable tabla, int idEmpleado) { // metodo similar al anterior pero acotado
        String sql = "SELECT n.ID_Nomina, e.Nombre||' '||e.Apellido, n.Periodo, " + // id y concatenacion de nombres
                     "n.Salario_Base, n.Horas_Trabajadas, n.Bonificaciones, " + // valores del pago
                     "n.Deducciones, n.Salario_Neto, n.Fec_Pago, n.Estado " + // totales pagados
                     "FROM T_Nomina n JOIN T_Empleados e ON n.ID_Empleado=e.ID_Empleado " + // junta nominas con dueños
                     "WHERE n.ID_Empleado=? ORDER BY n.Fec_Pago DESC"; // restringe al id pasado por parametro y ordena

        Connection con = cConexion.getConexion(); // toma conexion
        if (con == null) return; // aborta si hay problemas de conexion

        DefaultTableModel modelo = crearModeloHistorial(); // crea modelo de grilla
        tabla.setModel(modelo); // actualiza vista

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara statement
            pst.setInt(1, idEmpleado); // inyecta id al filtro where
            try (ResultSet rs = pst.executeQuery()) { // ejecuta
                while (rs.next()) { // escanea resultados del empleado
                    modelo.addRow(new Object[]{ // crea el renglon
                        rs.getInt(1), rs.getString(2), rs.getString(3), // datos primarios
                        String.format("$ %.2f", rs.getDouble(4)), // plata con formato
                        String.format("%.1f h", rs.getDouble(5)), // horas textuales
                        String.format("$ %.2f", rs.getDouble(6)), // platita bono
                        String.format("$ %.2f", rs.getDouble(7)), // platita descuento
                        String.format("$ %.2f", rs.getDouble(8)), // platita final
                        rs.getString(9), rs.getString(10) // resto texto
                    });
                }
            }
        } catch (SQLException ex) { // al tropezar
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // grita error
        }
    }

    // guardar una deduccion nueva
    public boolean insertarDeduccion(int idEmpleado, String tipo, double monto, String descripcion, String fecha) { // pasa datos de descuento
        String sql = "INSERT INTO T_Deducciones (ID_Empleado,Tipo,Monto,Descripcion,Fecha,Activo) VALUES (?,?,?,?,?,1)"; // sql inyectando activo verdadero
        Connection con = cConexion.getConexion(); // trae enlace
        if (con == null) return false; // si no lo logra se va

        try (PreparedStatement pst = con.prepareStatement(sql)) { // compone instruccion
            pst.setInt(1, idEmpleado); // setea aquien
            pst.setString(2, tipo); // setea de que
            pst.setDouble(3, monto); // setea cuanto
            pst.setString(4, descripcion); // setea porque
            pst.setString(5, fecha); // setea cuando
            pst.executeUpdate(); // escribe base
            return true; // salio bien
        } catch (SQLException ex) { // si hubo lio
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // llora en pantalla
            return false; // manda bandera falsa
        }
    }

    // listar todas las deducciones de un empleado
    public void listarDeducciones(JTable tabla, int idEmpleado) { // puebla un cuadro con deudas
        String sql = "SELECT d.ID_Deduccion, e.Nombre||' '||e.Apellido, d.Tipo, " + // campos de deduccion y persona
                     "d.Monto, d.Descripcion, d.Fecha, " + // campos descriptivos
                     "CASE d.Activo WHEN 1 THEN 'Activa' ELSE 'Inactiva' END " + // traduce el numero del estado a una palabra entendible
                     "FROM T_Deducciones d JOIN T_Empleados e ON d.ID_Empleado=e.ID_Empleado " + // une tablas para tener el nombre
                     "WHERE d.ID_Empleado=? ORDER BY d.Fecha DESC"; // solo de un fulano ordenado por fecha

        Connection con = cConexion.getConexion(); // agarra conexion local
        if (con == null) return; // si no hay se esfuma

        DefaultTableModel modelo = crearModeloDeducciones(); // crea molde
        tabla.setModel(modelo); // asimila molde

        try (PreparedStatement pst = con.prepareStatement(sql)) { // arma la string sql
            pst.setInt(1, idEmpleado); // inyecta al fulano
            try (ResultSet rs = pst.executeQuery()) { // recopila deducciones
                while (rs.next()) { // mientras existan deudas
                    modelo.addRow(new Object[]{ // agrupa la data del renglon
                        rs.getInt(1), rs.getString(2), rs.getString(3), // id empleado tipo
                        rs.getDouble(4), // el numero pelado
                        rs.getString(5), rs.getString(6), rs.getString(7) // explicacion fecha estado
                    });
                }
            }
        } catch (SQLException ex) { // si da fallo
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // notifica a interfaz
        }
    }

    // apagar una deduccion para que no se cobre mas
    public boolean desactivarDeduccion(int idDeduccion) { // recibe que descuento matar
        String sql = "UPDATE T_Deducciones SET Activo=0 WHERE ID_Deduccion=?"; // hace un update pasandolo a cero
        Connection con = cConexion.getConexion(); // asegura la via de db
        if (con == null) return false; // si esta caida no hace nada

        try (PreparedStatement pst = con.prepareStatement(sql)) { // organiza peticion
            pst.setInt(1, idDeduccion); // cual va a desactivar
            pst.executeUpdate(); // la ejecuta sin piedad
            return true; // finaliza ok
        } catch (SQLException ex) { // excepcion sql
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // ventana de terror
            return false; // devuelve falso
        }
    }

    // utilidades para construir tablas
    private DefaultTableModel crearModeloHistorial() { // crea el molde de pagos
        DefaultTableModel m = new DefaultTableModel() { // hace clase abstracta rapida
            @Override public boolean isCellEditable(int r, int c) { return false; } // candado contra escritura del usuario
        };
        m.addColumn("ID"); // header
        m.addColumn("Empleado"); // header
        m.addColumn("Período"); // header
        m.addColumn("Salario Base"); // header
        m.addColumn("Horas Trab."); // header
        m.addColumn("Bonificaciones"); // header
        m.addColumn("Deducciones"); // header
        m.addColumn("Salario Neto"); // header
        m.addColumn("Fecha Pago"); // header
        m.addColumn("Estado"); // header
        return m; // escupe el molde
    }

    private DefaultTableModel crearModeloDeducciones() { // crea el molde de deudas
        DefaultTableModel m = new DefaultTableModel() { // clase al vuelo
            @Override public boolean isCellEditable(int r, int c) { return false; } // prohibe teclear encima
        };
        m.addColumn("ID"); // titulillo
        m.addColumn("Empleado"); // titulillo
        m.addColumn("Tipo"); // titulillo
        m.addColumn("Monto"); // titulillo
        m.addColumn("Descripción"); // titulillo
        m.addColumn("Fecha"); // titulillo
        m.addColumn("Estado"); // titulillo
        return m; // entrega el modelo
    }
}

    