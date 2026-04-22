package Modelo; 


import Controlador.cConexion; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import javax.swing.JOptionPane; 
import javax.swing.JTable; 
import javax.swing.table.DefaultTableModel; 
//@author Hector Mojica,Cesar Rojas, Sebastian Medina

public class mAsistencia { // declara la clase para manejar la asistencia

    // registrar entrada
    public boolean registrarEntrada(int idEmpleado, String fecha, String horaEntrada) { // metodo para guardar la entrada de un empleado
        // verificar que no haya entrada ya registrada hoy
        String check = "SELECT ID_Asistencia FROM T_Asistencia WHERE ID_Empleado=? AND Fecha=?"; // consulta sql para buscar si ya entro hoy
        Connection con = cConexion.getConexion(); // obtiene la conexion a la base de datos
        if (con == null) return false; // si no hay conexion retorna falso y sale

        try (PreparedStatement pCheck = con.prepareStatement(check)) { // prepara la consulta de verificacion
            pCheck.setInt(1, idEmpleado); // asigna el id del empleado al primer parametro
            pCheck.setString(2, fecha); // asigna la fecha al segundo parametro
            try (ResultSet rs = pCheck.executeQuery()) { // ejecuta la consulta y guarda el resultado
                if (rs.next()) { // si encuentra un registro significa que ya entro
                    JOptionPane.showMessageDialog(null, // muestra mensaje en pantalla
                        "Este empleado ya tiene entrada registrada para hoy.", // texto del mensaje
                        "Aviso", JOptionPane.WARNING_MESSAGE); // titulo y tipo de advertencia
                    return false; // retorna falso porque ya existe la entrada
                }
            }
        } catch (SQLException ex) { // captura si hay un error en la base de datos
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra el error en pantalla
            return false; // retorna falso por el error
        }

        String sql = "INSERT INTO T_Asistencia (ID_Empleado,Fecha,Hora_Entrada,Estado) VALUES (?,?,?,'Presente')"; // consulta sql para insertar la entrada
        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara la consulta de insercion
            pst.setInt(1, idEmpleado); // asigna el id del empleado al primer parametro
            pst.setString(2, fecha); // asigna la fecha al segundo parametro
            pst.setString(3, horaEntrada); // asigna la hora de entrada al tercer parametro
            pst.executeUpdate(); // ejecuta la insercion en la base de datos
            return true; // retorna verdadero indicando exito
        } catch (SQLException ex) { // captura error de insercion
            JOptionPane.showMessageDialog(null, "Error al registrar entrada:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra error en pantalla
            return false; // retorna falso por el error
        }
    }

    // registrar salida
    public boolean registrarSalida(int idEmpleado, String fecha, String horaSalida) { // metodo para guardar la salida y calcular horas
        // calcular horas trabajadas
        String sqlSelect = "SELECT Hora_Entrada FROM T_Asistencia WHERE ID_Empleado=? AND Fecha=?"; // consulta para obtener la hora de entrada de hoy
        Connection con = cConexion.getConexion(); // obtiene conexion a la base de datos
        if (con == null) return false; // si no hay conexion sale retornando falso

        double horasTrabajadas = 0; // inicializa la variable de horas trabajadas en cero
        try (PreparedStatement pst = con.prepareStatement(sqlSelect)) { // prepara la consulta de busqueda
            pst.setInt(1, idEmpleado); // asigna el id del empleado
            pst.setString(2, fecha); // asigna la fecha
            try (ResultSet rs = pst.executeQuery()) { // ejecuta y guarda el resultado
                if (rs.next()) { // si encuentra la entrada
                    String[] partsEntrada = rs.getString("Hora_Entrada").split(":"); // divide la hora de entrada en horas y minutos
                    String[] partsSalida = horaSalida.split(":"); // divide la hora de salida en horas y minutos
                    int minEntrada = Integer.parseInt(partsEntrada[0]) * 60 + Integer.parseInt(partsEntrada[1]); // convierte la entrada a minutos totales
                    int minSalida = Integer.parseInt(partsSalida[0]) * 60 + Integer.parseInt(partsSalida[1]); // convierte la salida a minutos totales
                    horasTrabajadas = (minSalida - minEntrada) / 60.0; // calcula la diferencia en horas decimales
                    if (horasTrabajadas < 0) horasTrabajadas = 0; // si da negativo lo deja en cero
                } else { // si no encontro entrada
                    JOptionPane.showMessageDialog(null, "No hay entrada registrada para este empleado hoy.", "Aviso", JOptionPane.WARNING_MESSAGE); // muestra advertencia
                    return false; // retorna falso porque no puede registrar salida sin entrada
                }
            }
        } catch (Exception ex) { // captura errores de calculo o base de datos
            JOptionPane.showMessageDialog(null, "Error al calcular horas:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra el error
            return false; // retorna falso por el error
        }

        String sql = "UPDATE T_Asistencia SET Hora_Salida=?, Horas_Trabajadas=? WHERE ID_Empleado=? AND Fecha=?"; // consulta para actualizar el registro con la salida
        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara la consulta de actualizacion
            pst.setString(1, horaSalida); // asigna la hora de salida
            pst.setDouble(2, horasTrabajadas); // asigna las horas calculadas
            pst.setInt(3, idEmpleado); // asigna el id del empleado
            pst.setString(4, fecha); // asigna la fecha
            pst.executeUpdate(); // ejecuta la actualizacion en la base de datos
            return true; // retorna verdadero por exito
        } catch (SQLException ex) { // captura errores de actualizacion
            JOptionPane.showMessageDialog(null, "Error al registrar salida:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra error
            return false; // retorna falso
        }
    }

    // registrar ausencia
    public boolean registrarAusencia(int idEmpleado, String fecha, String motivo) { // metodo para guardar una falta
        String sql = "INSERT INTO T_Asistencia (ID_Empleado,Fecha,Estado,Observacion,Horas_Trabajadas) VALUES (?,?,'Ausente',?,0)"; // consulta para insertar la falta
        Connection con = cConexion.getConexion(); // obtiene conexion
        if (con == null) return false; // si falla la conexion retorna falso

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara la consulta de insercion
            pst.setInt(1, idEmpleado); // asigna id del empleado
            pst.setString(2, fecha); // asigna fecha de la falta
            pst.setString(3, motivo); // asigna el motivo de la falta
            pst.executeUpdate(); // ejecuta la insercion
            return true; // retorna verdadero si guardo bien
        } catch (SQLException ex) { // captura errores sql
            JOptionPane.showMessageDialog(null, "Error al registrar ausencia:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra error
            return false; // retorna falso
        }
    }

    // listar por empleado
    public void listarPorEmpleado(JTable tabla, int idEmpleado) { // metodo para llenar la tabla visual con datos de un empleado especifico
        String sql = "SELECT a.ID_Asistencia, e.Nombre||' '||e.Apellido, a.Fecha, " + // inicia consulta select uniendo tablas
                     "a.Hora_Entrada, a.Hora_Salida, a.Horas_Trabajadas, a.Estado, a.Observacion " + // selecciona las columnas a mostrar
                     "FROM T_Asistencia a JOIN T_Empleados e ON a.ID_Empleado=e.ID_Empleado " + // une tabla asistencia con empleados
                     "WHERE a.ID_Empleado=? ORDER BY a.Fecha DESC"; // filtra por id y ordena por fecha descendente

        Connection con = cConexion.getConexion(); // obtiene conexion
        if (con == null) return; // sale si no hay conexion

        DefaultTableModel modelo = crearModeloTabla(); // crea una estructura vacia para la tabla visual
        tabla.setModel(modelo); // asigna la estructura vacia a la tabla grafica

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara la consulta de lectura
            pst.setInt(1, idEmpleado); // asigna el id a buscar
            try (ResultSet rs = pst.executeQuery()) { // ejecuta y guarda resultados
                llenarModelo(modelo, rs); // llama al metodo ayudante para volcar los datos en la tabla
            }
        } catch (SQLException ex) { // captura errores
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra error en pantalla
        }
    }

    // listar por rango de fechas
    public void listarPorFecha(JTable tabla, String fechaInicio, String fechaFin) { // metodo para buscar asistencias entre dos fechas
        String sql = "SELECT a.ID_Asistencia, e.Nombre||' '||e.Apellido, a.Fecha, " + // inicia select
                     "a.Hora_Entrada, a.Hora_Salida, a.Horas_Trabajadas, a.Estado, a.Observacion " + // define columnas
                     "FROM T_Asistencia a JOIN T_Empleados e ON a.ID_Empleado=e.ID_Empleado " + // hace el join
                     "WHERE a.Fecha BETWEEN ? AND ? ORDER BY a.Fecha DESC, e.Apellido"; // filtra por rango de fechas y ordena

        Connection con = cConexion.getConexion(); // conecta a base de datos
        if (con == null) return; // aborta si no conecta

        DefaultTableModel modelo = crearModeloTabla(); // crea esqueleto de tabla
        tabla.setModel(modelo); // lo aplica a la vista

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara consulta
            pst.setString(1, fechaInicio); // asigna fecha desde
            pst.setString(2, fechaFin); // asigna fecha hasta
            try (ResultSet rs = pst.executeQuery()) { // ejecuta busqueda
                llenarModelo(modelo, rs); // rellena la grilla con los datos encontrados
            }
        } catch (SQLException ex) { // captura fallos sql
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra popup de error
        }
    }

    // reporte de ausencias
    public void reporteAusencias(JTable tabla, String fechaInicio, String fechaFin) { // metodo para contar faltas por empleado en un periodo
        String sql = "SELECT e.Cedula, e.Nombre||' '||e.Apellido AS Empleado, " + // select de cedula y nombre completo
                     "COUNT(a.ID_Asistencia) AS TotalAusencias, e.Departamento, e.Cargo " + // cuenta la cantidad de faltas y trae dpto y cargo
                     "FROM T_Asistencia a JOIN T_Empleados e ON a.ID_Empleado=e.ID_Empleado " + // une tablas
                     "WHERE a.Estado='Ausente' AND a.Fecha BETWEEN ? AND ? " + // filtra solo ausentes en el rango de fechas
                     "GROUP BY a.ID_Empleado ORDER BY TotalAusencias DESC"; // agrupa por persona y ordena del que mas falto al que menos

        Connection con = cConexion.getConexion(); // se conecta
        if (con == null) return; // sale si falla conexion

        DefaultTableModel modelo = new DefaultTableModel() { // crea modelo de tabla personalizado in situ
            @Override public boolean isCellEditable(int r, int c) { return false; } // bloquea edicion de celdas
        };
        modelo.addColumn("Cédula"); // anade columna cedula
        modelo.addColumn("Empleado"); // anade columna empleado
        modelo.addColumn("Total Ausencias"); // anade columna total
        modelo.addColumn("Departamento"); // anade columna departamento
        modelo.addColumn("Cargo"); // anade columna cargo
        tabla.setModel(modelo); // aplica columnas a la tabla visual

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara consulta de reporte
            pst.setString(1, fechaInicio); // inyecta fecha inicio
            pst.setString(2, fechaFin); // inyecta fecha fin
            try (ResultSet rs = pst.executeQuery()) { // ejecuta reporte
                while (rs.next()) { // itera sobre cada fila de resultado
                    modelo.addRow(new Object[]{ // agrega una fila a la tabla grafica
                        rs.getString(1), rs.getString(2), // mete cedula y nombre
                        rs.getInt(3), rs.getString(4), rs.getString(5) // mete conteo departamento y cargo
                    });
                }
            }
        } catch (SQLException ex) { // agarra errores de base de datos
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra mensaje
        }
    }

    // listar todo
    public void listarTodo(JTable tabla) { // metodo para mostrar las ultimas doscientas asistencias
        String sql = "SELECT a.ID_Asistencia, e.Nombre||' '||e.Apellido, a.Fecha, " + // select de datos basicos
                     "a.Hora_Entrada, a.Hora_Salida, a.Horas_Trabajadas, a.Estado, a.Observacion " + // columnas a extraer
                     "FROM T_Asistencia a JOIN T_Empleados e ON a.ID_Empleado=e.ID_Empleado " + // join de tablas
                     "ORDER BY a.Fecha DESC LIMIT 200"; // ordena por fecha y limita a doscientos registros

        Connection con = cConexion.getConexion(); // conecta
        if (con == null) return; // si falla sale

        DefaultTableModel modelo = crearModeloTabla(); // crea columnas base
        tabla.setModel(modelo); // setea la tabla

        try (PreparedStatement pst = con.prepareStatement(sql); // prepara query
             ResultSet rs = pst.executeQuery()) { // la ejecuta inmediatamente por no tener parametros
            llenarModelo(modelo, rs); // manda los datos a la grilla
        } catch (SQLException ex) { // en caso de error
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // muestra error
        }
    }

    // helpers
    private DefaultTableModel crearModeloTabla() { // metodo privado para generar la estructura de columnas de las tablas
        DefaultTableModel m = new DefaultTableModel() { // instancia el modelo
            @Override public boolean isCellEditable(int r, int c) { return false; } // anula la edicion manual de celdas
        };
        m.addColumn("ID"); // crea columna id
        m.addColumn("Empleado"); // crea columna empleado
        m.addColumn("Fecha"); // crea columna fecha
        m.addColumn("Hora Entrada"); // crea columna hora entrada
        m.addColumn("Hora Salida"); // crea columna hora salida
        m.addColumn("Horas Trab."); // crea columna horas trabajadas
        m.addColumn("Estado"); // crea columna estado
        m.addColumn("Observación"); // crea columna observacion
        return m; // devuelve el modelo configurado
    }

    private void llenarModelo(DefaultTableModel modelo, ResultSet rs) throws SQLException { // metodo para iterar resultados y meterlos al modelo visual
        while (rs.next()) { // bucle mientras haya registros en la base de datos
            modelo.addRow(new Object[]{ // agrega fila al modelo
                rs.getInt(1), rs.getString(2), rs.getString(3), // lee id empleado y fecha
                rs.getString(4) == null ? "--" : rs.getString(4), // lee hora entrada o pone guiones si es nulo
                rs.getString(5) == null ? "--" : rs.getString(5), // lee hora salida o pone guiones si es nulo
                String.format("%.1f h", rs.getDouble(6)), // formatea las horas trabajadas con un decimal
                rs.getString(7), rs.getString(8) // lee estado y observacion
            });
        }
    }
}