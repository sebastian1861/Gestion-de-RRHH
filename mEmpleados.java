package Modelo; 

import Controlador.cConexion; 
import java.awt.HeadlessException; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import javax.swing.JOptionPane; 
import javax.swing.JTable; 
import javax.swing.table.DefaultTableModel; 

//@author Hector Mojica,Cesar Rojas, Sebastian Medina
public class mEmpleados { 

    // insertar empleado nuevo
    public boolean insertar(String cedula, String nombre, String apellido, // define el metodo publico que retorna un booleano y recibe los datos del empleado
            String fecNac, String genero, String telefono, String correo, // continua recibiendo parametros de texto para los datos del empleado
            String direccion, String cargo, String departamento, // continua recibiendo parametros de texto para los datos del empleado
            String fecIngreso, double salarioBase) { // recibe la fecha de ingreso y el salario como decimal

        String sql = "INSERT INTO T_Empleados (Cedula,Nombre,Apellido,Fec_Nacimiento," + // inicia la cadena de texto con la consulta sql para insertar
                     "Genero,Telefono,Correo,Direccion,Cargo,Departamento,Fec_Ingreso," + // continua la cadena de texto de la consulta sql
                     "Salario_Base,Estado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,'Activo')"; // finaliza la consulta sql con los parametros incognitos y estado activo

        Connection con = cConexion.getConexion(); // obtiene la conexion a la base de datos llamando al metodo de la clase cconexion
        if (con == null) return false; // si la conexion es nula retorna falso terminando el metodo

        try (PreparedStatement pst = con.prepareStatement(sql)) { // intenta preparar la consulta sql y asegura que se cierre al terminar
            pst.setString(1, cedula); // asigna el valor de la cedula al primer parametro de la consulta
            pst.setString(2, nombre); // asigna el valor del nombre al segundo parametro de la consulta
            pst.setString(3, apellido); // asigna el valor del apellido al tercer parametro de la consulta
            pst.setString(4, fecNac); // asigna la fecha de nacimiento al cuarto parametro
            pst.setString(5, genero); // asigna el genero al quinto parametro
            pst.setString(6, telefono); // asigna el telefono al sexto parametro
            pst.setString(7, correo); // asigna el correo al septimo parametro
            pst.setString(8, direccion); // asigna la direccion al octavo parametro
            pst.setString(9, cargo); // asigna el cargo al noveno parametro
            pst.setString(10, departamento); // asigna el departamento al decimo parametro
            pst.setString(11, fecIngreso); // asigna la fecha de ingreso al undecimo parametro
            pst.setDouble(12, salarioBase); // asigna el salario base como decimal al duodecimo parametro
            pst.executeUpdate(); // ejecuta la consulta de insercion en la base de datos
            return true; // retorna verdadero si la insercion fue exitosa
        } catch (SQLException ex) { // atrapa cualquier error de tipo sql que ocurra
            if (ex.getMessage().contains("UNIQUE")) { // verifica si el mensaje de error contiene la palabra unique indicando duplicado
                JOptionPane.showMessageDialog(null, // muestra un cuadro de dialogo
                    "Ya existe un empleado con la cédula: " + cedula, // mensaje indicando que la cedula ya existe
                    "Cédula Duplicada", JOptionPane.WARNING_MESSAGE); // titulo del cuadro de dialogo y tipo de mensaje de advertencia
            } else { // si el error no es por duplicado ejecuta este bloque
                JOptionPane.showMessageDialog(null, // muestra un cuadro de dialogo
                    "Error al registrar empleado:\n" + ex.getMessage(), // muestra el mensaje de error tecnico
                    "Error", JOptionPane.ERROR_MESSAGE); // titulo del cuadro de dialogo y tipo de mensaje de error
            }
            return false; // retorna falso porque ocurrio un error al insertar
        }
    }

    // actualizar empleado existente
    public boolean actualizar(int idEmpleado, String cedula, String nombre, // define el metodo para actualizar recibiendo el id y demas datos
            String apellido, String fecNac, String genero, String telefono, // continua recibiendo los parametros de texto
            String correo, String direccion, String cargo, String departamento, // continua recibiendo los parametros de texto
            String fecIngreso, double salarioBase, String estado) { // recibe los ultimos parametros incluyendo el estado

        String sql = "UPDATE T_Empleados SET Cedula=?,Nombre=?,Apellido=?," + // inicia la consulta sql para actualizar los campos
                     "Fec_Nacimiento=?,Genero=?,Telefono=?,Correo=?,Direccion=?," + // continua la consulta sql de actualizacion
                     "Cargo=?,Departamento=?,Fec_Ingreso=?,Salario_Base=?,Estado=? " + // continua asignando los campos a actualizar
                     "WHERE ID_Empleado=?"; // condiciona la actualizacion al id del empleado especifico

        Connection con = cConexion.getConexion(); // obtiene la conexion a la base de datos
        if (con == null) return false; // si no hay conexion retorna falso

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara la consulta sql para ser ejecutada
            pst.setString(1, cedula); // asigna la cedula al parametro uno
            pst.setString(2, nombre); // asigna el nombre al parametro dos
            pst.setString(3, apellido); // asigna el apellido al parametro tres
            pst.setString(4, fecNac); // asigna la fecha de nacimiento al parametro cuatro
            pst.setString(5, genero); // asigna el genero al parametro cinco
            pst.setString(6, telefono); // asigna el telefono al parametro seis
            pst.setString(7, correo); // asigna el correo al parametro siete
            pst.setString(8, direccion); // asigna la direccion al parametro ocho
            pst.setString(9, cargo); // asigna el cargo al parametro nueve
            pst.setString(10, departamento); // asigna el departamento al parametro diez
            pst.setString(11, fecIngreso); // asigna la fecha de ingreso al parametro once
            pst.setDouble(12, salarioBase); // asigna el salario base al parametro doce
            pst.setString(13, estado); // asigna el estado al parametro trece
            pst.setInt(14, idEmpleado); // asigna el id del empleado al parametro catorce para la condicion where
            pst.executeUpdate(); // ejecuta la actualizacion en la base de datos
            return true; // retorna verdadero si se actualizo correctamente
        } catch (SQLException ex) { // atrapa errores de base de datos
            JOptionPane.showMessageDialog(null, // muestra una ventana de mensaje
                "Error al actualizar empleado:\n" + ex.getMessage(), // muestra el detalle del error
                "Error", JOptionPane.ERROR_MESSAGE); // titulo y tipo de mensaje de error
            return false; // retorna falso indicando fallo en la actualizacion
        }
    }

    // baja logica del empleado
    public boolean darDeBaja(int idEmpleado) { // metodo para cambiar el estado a inactivo
        String sql = "UPDATE T_Empleados SET Estado='Inactivo' WHERE ID_Empleado=?"; // consulta sql para desactivar al empleado
        Connection con = cConexion.getConexion(); // obtiene la conexion
        if (con == null) return false; // si falla la conexion retorna falso

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara la consulta sql
            pst.setInt(1, idEmpleado); // asigna el id del empleado al primer parametro
            pst.executeUpdate(); // ejecuta la actualizacion
            return true; // retorna verdadero si tuvo exito
        } catch (SQLException ex) { // atrapa errores de sql
            JOptionPane.showMessageDialog(null, // muestra mensaje visual
                "Error al dar de baja:\n" + ex.getMessage(), // texto con el error especifico
                "Error", JOptionPane.ERROR_MESSAGE); // icono de error en la ventana
            return false; // retorna falso por el error
        }
    }

    // eliminar fisico de la base de datos
    public boolean eliminar(int idEmpleado) { // metodo para borrar definitivamente un registro
        String sql = "DELETE FROM T_Empleados WHERE ID_Empleado=?"; // instruccion sql para borrar el registro
        Connection con = cConexion.getConexion(); // establece la conexion
        if (con == null) return false; // si es nula sale del metodo

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara la instruccion de borrado
            pst.setInt(1, idEmpleado); // inserta el id en la consulta
            pst.executeUpdate(); // ejecuta el borrado
            return true; // retorna confirmacion de exito
        } catch (SQLException ex) { // maneja la excepcion si ocurre
            JOptionPane.showMessageDialog(null, // invoca la ventana de alerta
                "Error al eliminar empleado:\n" + ex.getMessage(), // describe el fallo
                "Error", JOptionPane.ERROR_MESSAGE); // titulo de error
            return false; // devuelve falso al fallar
        }
    }

    // consultar todos los empleados
    public void listar(JTable tabla) { // recibe una tabla visual para llenarla con datos
        String sql = "SELECT ID_Empleado,Cedula,Nombre,Apellido,Cargo,Departamento," + // inicia seleccion de columnas
                     "Telefono,Correo,Salario_Base,Estado FROM T_Empleados ORDER BY Apellido"; // completa columnas y ordena por apellido
        cargarTabla(tabla, sql); // llama al metodo auxiliar privado para llenar la tabla
    }

    // consultar solo empleados activos
    public void listarActivos(JTable tabla) { // recibe tabla para mostrar solo activos
        String sql = "SELECT ID_Empleado,Cedula,Nombre,Apellido,Cargo,Departamento," + // inicia consulta select
                     "Telefono,Correo,Salario_Base,Estado FROM T_Empleados " + // selecciona de la tabla empleados
                     "WHERE Estado='Activo' ORDER BY Apellido"; // filtra por estado activo y ordena
        cargarTabla(tabla, sql); // ejecuta el helper para cargar los datos
    }

    // buscar empleados por coincidencia
    public void buscar(JTable tabla, String criterio) { // metodo de busqueda general
        String sql = "SELECT ID_Empleado,Cedula,Nombre,Apellido,Cargo,Departamento," + // columnas a traer
                     "Telefono,Correo,Salario_Base,Estado FROM T_Empleados " + // tabla origen
                     "WHERE Cedula LIKE ? OR Nombre LIKE ? OR Apellido LIKE ? " + // filtros de coincidencia parcial
                     "OR Cargo LIKE ? OR Departamento LIKE ? ORDER BY Apellido"; // mas filtros y ordenamiento

        Connection con = cConexion.getConexion(); // solicita conexion
        if (con == null) return; // sale si no conecta

        DefaultTableModel modelo = crearModeloTabla(); // crea un modelo de tabla vacio con columnas
        tabla.setModel(modelo); // asigna el modelo vacio a la tabla visual
        String param = "%" + criterio + "%"; // crea el comodin de busqueda para sql

        try (PreparedStatement pst = con.prepareStatement(sql)) { // prepara la busqueda
            for (int i = 1; i <= 5; i++) pst.setString(i, param); // asigna el mismo parametro a los cinco comodines
            try (ResultSet rs = pst.executeQuery()) { // ejecuta y guarda el resultado
                llenarModelo(modelo, rs); // pasa los datos del resultado al modelo de la tabla
            }
        } catch (SQLException ex) { // en caso de error en la base de datos
            JOptionPane.showMessageDialog(null, // muestra popup
                "Error en búsqueda:\n" + ex.getMessage(), // texto de error
                "Error", JOptionPane.ERROR_MESSAGE); // estilo visual de error
        }
    }

    // obtener un empleado especifico por su id
    public String[] obtenerPorId(int idEmpleado) { // retorna un arreglo de textos con los datos
        String sql = "SELECT * FROM T_Empleados WHERE ID_Empleado=?"; // selecciona todos los campos de un id
        Connection con = cConexion.getConexion(); // abre conexion
        if (con == null) return null; // si no hay retorna nulo

        try (PreparedStatement pst = con.prepareStatement(sql)) { // arma la consulta
            pst.setInt(1, idEmpleado); // le pasa el id buscado
            try (ResultSet rs = pst.executeQuery()) { // obtiene la fila resultante
                if (rs.next()) { // si encontro resultados se mueve a la primera fila
                    return new String[]{ // crea y retorna el arreglo con los datos extraidos
                        rs.getString("ID_Empleado"), // extrae id
                        rs.getString("Cedula"), // extrae cedula
                        rs.getString("Nombre"), // extrae nombre
                        rs.getString("Apellido"), // extrae apellido
                        rs.getString("Fec_Nacimiento"), // extrae nacimiento
                        rs.getString("Genero"), // extrae genero
                        rs.getString("Telefono"), // extrae telefono
                        rs.getString("Correo"), // extrae correo
                        rs.getString("Direccion"), // extrae direccion
                        rs.getString("Cargo"), // extrae cargo
                        rs.getString("Departamento"), // extrae departamento
                        rs.getString("Fec_Ingreso"), // extrae ingreso
                        rs.getString("Salario_Base"), // extrae salario
                        rs.getString("Estado") // extrae estado
                    };
                }
            }
        } catch (SQLException ex) { // si falla la lectura
            JOptionPane.showMessageDialog(null, // informa al usuario
                "Error al obtener empleado:\n" + ex.getMessage(), // descripcion del error
                "Error", JOptionPane.ERROR_MESSAGE); // formato de error
        }
        return null; // si no encontro nada retorna nulo
    }

    // metodos de ayuda privados
    private void cargarTabla(JTable tabla, String sql) { // metodo que centraliza la carga de datos
        Connection con = cConexion.getConexion(); // obtiene conexion
        if (con == null) return; // detiene ejecucion si es nula

        DefaultTableModel modelo = crearModeloTabla(); // invoca la creacion de columnas
        tabla.setModel(modelo); // asocia el modelo a la vista

        try (PreparedStatement pst = con.prepareStatement(sql); // prepara la consulta recibida
             ResultSet rs = pst.executeQuery()) { // ejecuta y almacena resultados directamente
            llenarModelo(modelo, rs); // vacia los datos sql en la tabla de java
        } catch (SQLException ex) { // por si explota la consulta
            JOptionPane.showMessageDialog(null, // muestra mensaje
                "Error al listar empleados:\n" + ex.getMessage(), // que paso mal
                "Error", JOptionPane.ERROR_MESSAGE); // asustar al usuario
        }
    }

    private DefaultTableModel crearModeloTabla() { // define la estructura de las tablas
        DefaultTableModel m = new DefaultTableModel() { // instancia el modelo anonimo
            @Override public boolean isCellEditable(int r, int c) { return false; } // bloquea la edicion directa en celdas
        };
        m.addColumn("ID"); // agrega columna id
        m.addColumn("Cédula"); // agrega columna cedula
        m.addColumn("Nombre"); // agrega columna nombre
        m.addColumn("Apellido"); // agrega columna apellido
        m.addColumn("Cargo"); // agrega columna cargo
        m.addColumn("Departamento"); // agrega columna departamento
        m.addColumn("Teléfono"); // agrega columna telefono
        m.addColumn("Correo"); // agrega columna correo
        m.addColumn("Salario Base"); // agrega columna salario
        m.addColumn("Estado"); // agrega columna estado
        return m; // retorna el modelo construido
    }

    private void llenarModelo(DefaultTableModel modelo, ResultSet rs) throws SQLException { // recorre el iterador de base de datos
        while (rs.next()) { // mientras existan filas siguientes
            modelo.addRow(new Object[]{ // anade una fila nueva con un arreglo de objetos
                rs.getInt(1), // id entero
                rs.getString(2), // cedula texto
                rs.getString(3), // nombre texto
                rs.getString(4), // apellido texto
                rs.getString(5), // cargo texto
                rs.getString(6), // departamento texto
                rs.getString(7), // telefono texto
                rs.getString(8), // correo texto
                String.format("$ %.2f", rs.getDouble(9)), // formatea el decimal a moneda
                rs.getString(10) // estado texto
            });
        }
    }

    public void buscarPorCampo(JTable tblEmpleados, String valor, String campo) { // busqueda especifica por columna

    String columna; // variable para mapear el nombre real en la base de datos
    switch (campo) { // evalua el campo recibido de la interfaz
        case "Nombre":   columna = "Nombre";   break; // si es nombre usa nombre
        case "Apellido": columna = "Apellido"; break; // si es apellido usa apellido
        case "Cédula":   columna = "Cedula";   break; // si es cedula quita la tilde para sql
        case "Correo":   columna = "Correo";   break; // si es correo usa correo
        default: // si no coincide ninguna opcion
            JOptionPane.showMessageDialog(null, // muestra error
                "Criterio de búsqueda no reconocido: " + campo, // indica el campo erroneo
                "Error", JOptionPane.ERROR_MESSAGE); // titulo de alerta
            return; // finaliza abruptamente
    }

    DefaultTableModel modelo = new DefaultTableModel(); // crea un modelo en blanco para la tabla
    modelo.addColumn("ID"); // anade cabecera
    modelo.addColumn("Cédula"); 
    modelo.addColumn("Nombre"); 
    modelo.addColumn("Apellido"); 
    modelo.addColumn("Fec. Nacimiento"); 
    modelo.addColumn("Género"); 
    modelo.addColumn("Teléfono"); 
    modelo.addColumn("Correo"); 
    modelo.addColumn("Dirección"); 
    modelo.addColumn("Cargo"); 
    modelo.addColumn("Departamento"); 
    modelo.addColumn("Fec. Ingreso"); 
    modelo.addColumn("Salario");
    modelo.addColumn("Estado"); 

    tblEmpleados.setModel(modelo); // enlaza el modelo a la tabla de la ventana

    String sql = "SELECT * FROM T_Empleados WHERE " + columna + " LIKE ?"; // arma la consulta concatenando la columna
    String patronLike = "%" + valor + "%"; // envuelve el texto en comodines de sql
    String[] datos = new String[14]; // crea un arreglo de catorce posiciones para los datos

    try { // intenta ejecutar la rutina de base de datos
        // asi se usa correctamente tu singleton
        java.sql.PreparedStatement pst = cConexion.getConexion().prepareStatement(sql); // obtiene conexion y prepara instruccion
        pst.setString(1, patronLike); // inserta el comodin en la consulta
        java.sql.ResultSet rs = pst.executeQuery(); // ejecuta peticion y recupera datos

        while (rs.next()) { // itera sobre cada registro encontrado
            datos[0]  = rs.getString(1); // guarda posicion cero
            datos[1]  = rs.getString(2); 
            datos[2]  = rs.getString(3); 
            datos[3]  = rs.getString(4); 
            datos[4]  = rs.getString(5); 
            datos[5]  = rs.getString(6); 
            datos[7]  = rs.getString(8); 
            datos[8]  = rs.getString(9); 
            datos[9]  = rs.getString(10); 
            datos[10] = rs.getString(11); 
            datos[11] = rs.getString(12); 
            datos[12] = rs.getString(13); 
            datos[13] = rs.getString(14); 
            modelo.addRow(datos); 
        }

        tblEmpleados.setModel(modelo); // reasigna el modelo ya lleno por seguridad

        if (modelo.getRowCount() == 0) { // verifica si la tabla quedo vacia
            JOptionPane.showMessageDialog(null, // si no hay resultados avisa
                "No se encontraron empleados con " + campo + ": \"" + valor + "\"", // indica que no hubo suerte
                "Sin resultados", JOptionPane.INFORMATION_MESSAGE); // titulo informativo
        }

    } catch (HeadlessException | SQLException er) { // captura excepciones visuales o de sql
        JOptionPane.showMessageDialog(null, // saca mensaje fatal
            "Error al buscar por " + campo + ": " + er.getMessage(), // detalle del desastre
            "Error de búsqueda", JOptionPane.ERROR_MESSAGE); // cabecera de error
    }
}
}