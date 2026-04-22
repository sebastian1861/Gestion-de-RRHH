package Vista;

import Modelo.mAsistencia;
import Modelo.mEmpleados;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
//@author Hector Mojica,Cesar Rojas, Sebastian Medina

public class vAsistencia extends javax.swing.JFrame { // declara la ventana grafica de asistencia

    // panel de registro
    private javax.swing.JPanel     panelRegistro; // contendra los campos para ingresar datos
    private javax.swing.JLabel     lblIdEmp; // etiqueta id empleado
    private javax.swing.JTextField txtIdEmpleado; // caja de texto para el id
    private javax.swing.JLabel     lblNombreEmp; // etiqueta nombre empleado
    private javax.swing.JTextField txtNombreEmp; // caja de texto de solo lectura para el nombre
    private javax.swing.JLabel     lblFecha; // etiqueta fecha
    private javax.swing.JTextField txtFecha; // caja de texto para la fecha
    private javax.swing.JLabel     lblHoraEntrada; // etiqueta hora entrada
    private javax.swing.JTextField txtHoraEntrada; // caja de texto para hora entrada
    private javax.swing.JLabel     lblHoraSalida; // etiqueta hora salida
    private javax.swing.JTextField txtHoraSalida; // caja de texto para hora salida
    private javax.swing.JLabel     lblMotivo; // etiqueta para motivo
    private javax.swing.JTextField txtMotivo; // caja de texto para el motivo de ausencia

    // botones de accion
    private javax.swing.JButton    btnEntrada; // boton para marcar entrada
    private javax.swing.JButton    btnSalida; // boton para marcar salida
    private javax.swing.JButton    btnAusencia; // boton para registrar falta
    private javax.swing.JButton    btnUsarHoraActual; // boton para llenar las horas con la actual del sistema
    private javax.swing.JButton    btnBuscarEmp; // boton para buscar emp por id
    private javax.swing.JButton    btnBuscarNombre; // boton para buscar emp por texto

    // panel de consulta y reporte
    private javax.swing.JPanel     panelConsulta; // contendra los filtros para buscar
    private javax.swing.JTextField txtFechaInicio; // fecha desde para reporte
    private javax.swing.JTextField txtFechaFin; // fecha hasta para reporte
    private javax.swing.JTextField txtIdEmpConsulta; // id a buscar en el reporte
    private javax.swing.JButton    btnConsultarEmp; // boton para buscar por id en reportes
    private javax.swing.JButton    btnConsultarFecha; // boton para buscar por fechas
    private javax.swing.JButton    btnReporteAusencias; // boton para mostrar faltas
    private javax.swing.JButton    btnVerTodo; // boton para limpiar filtros
    private javax.swing.JTable     tblAsistencia; // grilla principal de asistencia
    private javax.swing.JScrollPane scrollTabla; // barra de desplazamiento para la grilla

    // tabs
    private javax.swing.JTabbedPane tabPanel; // panel con pestanias

    private final mAsistencia modelo = new mAsistencia(); // instancia el modelo de datos de asistencia
    private final mEmpleados  modeloEmp = new mEmpleados(); // instancia el modelo de datos de empleados

    public vAsistencia() { // constructor de la ventana
        initComponents(); // llama al creador de componentes
        modelo.listarTodo(tblAsistencia); // llena la tabla principal al iniciar
    }

    private void initComponents() { // creador y ensamblador de la interfaz grafica
        tabPanel = new JTabbedPane(); // inicializa el gestor de pestanias

        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // permite cerrar solo esta ventana sin apagar el programa
        setTitle(" Módulo de Asistencia"); // titulo en la barra
        setPreferredSize(new Dimension(1100, 640)); // tamano fijo de la ventana

        // panel titulo
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT)); // crea franja superior
        panelTitulo.setBackground(new Color(39, 110, 58)); // pinta verde oscuro
        JLabel lblTit = new JLabel("  Control de Asistencia"); // texto principal
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 18)); // fuente grande
        lblTit.setForeground(Color.BLACK); // letra negra
        panelTitulo.add(lblTit); // inserta titulo en la franja
        setLayout(new BorderLayout()); // usa layout de bordes
        add(panelTitulo, BorderLayout.NORTH); // ubica la franja arriba del todo

        // primera pestania
        JPanel tabRegistro = new JPanel(new BorderLayout(10, 10)); // panel para el tab de registro
        tabRegistro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // margenes internas
        tabRegistro.setBackground(new Color(245, 247, 250)); // fondo gris claro

        panelRegistro = new JPanel(new GridBagLayout()); // panel lateral para botones y textos
        panelRegistro.setBackground(Color.WHITE); // fondo blanco
        panelRegistro.setBorder(BorderFactory.createTitledBorder( // recuadro decorativo
            BorderFactory.createLineBorder(new Color(200, 200, 200)), // borde gris
            "Registro de Entrada / Salida / Ausencia", 0, 0, // titulo del recuadro
            new Font("Segoe UI", Font.BOLD, 13), new Color(39, 110, 58))); // estilo y color verde

        GridBagConstraints gbc = new GridBagConstraints(); // controlador de posiciones de la grilla
        gbc.fill = GridBagConstraints.HORIZONTAL; // hace que los elementos se estiren horizontalmente
        gbc.insets = new Insets(6, 10, 6, 10); // espacio entre componentes
        gbc.weightx = 1.0; // distribucion proporcional

        txtIdEmpleado  = new JTextField(); // inicializa campo id
        txtNombreEmp   = new JTextField(); // inicializa campo nombre
        txtFecha       = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_DATE)); // inicializa campo fecha con hoy
        txtHoraEntrada = new JTextField(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))); // inicializa entrada prellenada
        txtHoraSalida  = new JTextField(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))); // inicializa salida prellenada
        txtMotivo      = new JTextField(); // inicializa campo motivo

        txtNombreEmp.setEditable(false); // bloquea tipeo en nombre
        txtNombreEmp.setBackground(new Color(230, 230, 230)); // le da color grisecito

        btnBuscarEmp     = new JButton(" Buscar por ID"); // inicializa boton
        btnBuscarNombre  = new JButton(" Buscar por Nombre"); // inicializa boton
        btnUsarHoraActual = new JButton(" Hora Actual"); // inicializa boton
        btnEntrada       = new JButton(" Registrar ENTRADA"); // inicializa boton
        btnSalida        = new JButton(" Registrar SALIDA"); // inicializa boton
        btnAusencia      = new JButton(" Registrar AUSENCIA"); // inicializa boton

        Object[][] campos = { // crea matriz con los textos y las cajas para inyectarlas con bucle
            {"ID Empleado:", txtIdEmpleado}, // fila cero
            {"Empleado:", txtNombreEmp}, // fila uno
            {"Fecha (YYYY-MM-DD):", txtFecha}, // fila dos
            {"Hora Entrada (HH:mm):", txtHoraEntrada}, // fila tres
            {"Hora Salida (HH:mm):", txtHoraSalida}, // fila cuatro
            {"Motivo Ausencia:", txtMotivo} // fila cinco
        };

        for (int i = 0; i < campos.length; i++) { // recorre la matriz
            JLabel lbl = new JLabel(campos[i][0].toString()); // extrae el texto
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12)); // le da estilo
            gbc.gridx = 0; gbc.gridy = i; gbc.gridwidth = 1; // ubica en la columna izquierda
            panelRegistro.add(lbl, gbc); // mete el label al panel
            gbc.gridx = 1; // cambia a columna derecha
            JComponent comp = (JComponent) campos[i][1]; // extrae la caja de texto
            comp.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // le da estilo
            comp.setPreferredSize(new Dimension(250, 30)); // fija tamano de caja
            panelRegistro.add(comp, gbc); // mete la caja al panel
        }

        // boton buscar empleado id
        gbc.gridx = 0; gbc.gridy = campos.length; gbc.gridwidth = 2; // lo centra ocupando dos columnas abajo
        gbc.insets = new Insets(8, 10, 4, 10); // retoca margenes
        estilizarBtn(btnBuscarEmp, new Color(30, 60, 114)); // lo pinta azul oscuro
        panelRegistro.add(btnBuscarEmp, gbc); // inyecta al panel

        gbc.gridy++; // baja de renglon
        estilizarBtn(btnBuscarNombre, new Color(0, 100, 140)); // pinta celeste oscuro
        panelRegistro.add(btnBuscarNombre, gbc); // inyecta al panel

        gbc.gridy++; // baja de renglon
        estilizarBtn(btnUsarHoraActual, new Color(80, 80, 80)); // pinta gris
        panelRegistro.add(btnUsarHoraActual, gbc); // inyecta al panel

        gbc.gridy++; // baja de renglon
        estilizarBtn(btnEntrada, new Color(39, 110, 58)); // pinta verde
        panelRegistro.add(btnEntrada, gbc); // inyecta al panel

        gbc.gridy++; // baja de renglon
        estilizarBtn(btnSalida, new Color(30, 60, 114)); // pinta azul
        panelRegistro.add(btnSalida, gbc); // inyecta al panel

        gbc.gridy++; // baja de renglon
        estilizarBtn(btnAusencia, new Color(150, 30, 30)); // pinta rojo oscuro
        panelRegistro.add(btnAusencia, gbc); // inyecta al panel

        // texto informacion inferior
        JLabel lblInfo = new JLabel("<html><i>Usuario por defecto: admin / admin123<br>" + // texto en formato html
            "Fecha actual cargada automáticamente</i></html>"); // texto descriptivo
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 10)); // letrita cursiva chica
        lblInfo.setForeground(new Color(120, 120, 120)); // tono gris
        gbc.gridy++; // baja de renglon
        panelRegistro.add(lblInfo, gbc); // inyecta texto

        tabRegistro.add(new JScrollPane(panelRegistro), BorderLayout.WEST); // manda todo el formulario al lado izquierdo del tab

        // tabla central derecha
        tblAsistencia = new JTable(); // crea la grilla visual
        scrollTabla   = new JScrollPane(tblAsistencia); // le pone barrita desplazadora
        estilizarTabla(tblAsistencia); // le aplica css de java
        tabRegistro.add(scrollTabla, BorderLayout.CENTER); // la encaja en el espacio sobrante

        tabPanel.addTab(" Registro", tabRegistro); // inyecta la pestania completa al gestor

        // segunda pestania
        JPanel tabConsultas = new JPanel(new BorderLayout(10, 10)); // panel principal de consultas
        tabConsultas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // margenes
        tabConsultas.setBackground(new Color(245, 247, 250)); // gris claro

        panelConsulta = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8)); // panel de filtros arriba
        panelConsulta.setBackground(Color.WHITE); // blanco
        panelConsulta.setBorder(BorderFactory.createTitledBorder( // cuadrito lindo
            BorderFactory.createLineBorder(new Color(200, 200, 200)), // raya gris
            "Filtros de Consulta", 0, 0, // titulo filtro
            new Font("Segoe UI", Font.BOLD, 13), new Color(39, 110, 58))); // letrita verde

        txtFechaInicio   = new JTextField(LocalDate.now().withDayOfMonth(1).toString(), 12); // arranca con el dia 1 del mes actual
        txtFechaFin      = new JTextField(LocalDate.now().toString(), 12); // arranca con el dia de hoy
        txtIdEmpConsulta = new JTextField(8); // caja chica para id
        btnConsultarEmp  = new JButton("Por Empleado"); // boton
        btnConsultarFecha = new JButton("Por Fechas"); // boton
        btnReporteAusencias = new JButton("📊 Reporte Ausencias"); // boton con emoji
        btnVerTodo       = new JButton("Ver Todo"); // boton

        panelConsulta.add(new JLabel("ID Empleado:")); // texto
        panelConsulta.add(txtIdEmpConsulta); // cajita
        panelConsulta.add(btnConsultarEmp); // boton emp
        panelConsulta.add(new JSeparator(JSeparator.VERTICAL)); // raya separadora
        panelConsulta.add(new JLabel("Desde:")); // texto
        panelConsulta.add(txtFechaInicio); // caja
        panelConsulta.add(new JLabel("Hasta:")); // texto
        panelConsulta.add(txtFechaFin); // caja
        panelConsulta.add(btnConsultarFecha); // boton fecha
        panelConsulta.add(btnReporteAusencias); // boton reporte
        panelConsulta.add(btnVerTodo); // boton reset

        for (JButton b : new JButton[]{btnConsultarEmp, btnConsultarFecha}) { // bucle para pintar dos botones
            estilizarBtn(b, new Color(39, 110, 58)); // verde
        }
        estilizarBtn(btnReporteAusencias, new Color(150, 80, 0)); // naranja oscuro
        estilizarBtn(btnVerTodo, new Color(80, 80, 80)); // gris

        JTable tblConsulta = new JTable(); // tabla vacia para este tab
        estilizarTabla(tblConsulta); // embellecedor

        tabConsultas.add(panelConsulta, BorderLayout.NORTH); // pone los filtros arriba
        tabConsultas.add(new JScrollPane(tblConsulta), BorderLayout.CENTER); // pone la tabla abajo de los filtros

        tabPanel.addTab("🔍 Consultas y Reportes", tabConsultas); // inyecta el tab al gestor

        add(tabPanel, BorderLayout.CENTER); // pone las pestanias al medio de la ventana principal
        pack(); // ajusta todo automaticamente
        setLocationRelativeTo(null); // la centra en el monitor

        // eventos click
        btnBuscarEmp.addActionListener(e -> { // al apretar buscar por id
            String idStr = txtIdEmpleado.getText().trim(); // extrae texto quitando espacios
            if (idStr.isEmpty()) { // si no escribio nada
                JOptionPane.showMessageDialog(null, "Ingrese el ID del empleado.", "Aviso", JOptionPane.WARNING_MESSAGE); // regania
                return; // se va
            }
            try { // intenta convertir
                int id = Integer.parseInt(idStr); // lo pasa a numero
                mEmpleados modelo = new mEmpleados(); // crea controlador local de empleados
                String[] emp = modelo.obtenerPorId(id); // pide datos al modelo
                if (emp == null) { // si no lo halla
                    JOptionPane.showMessageDialog(null, // lanza error
                            "No existe un empleado con ID: " + id + "\nVerifique en el módulo Empleados.", // texto informativo
                            "Empleado no encontrado", JOptionPane.ERROR_MESSAGE); // error grave
                    txtNombreEmp.setText("");  // limpia la cajita
                    return; // se va
                }
                txtNombreEmp.setText(emp[2] + " " + emp[3]); // escribe nombre y apellido en la cajita gris
            } catch (NumberFormatException ex) { // si escribio letras
                JOptionPane.showMessageDialog(null, "El ID debe ser un número entero.", "Error", JOptionPane.WARNING_MESSAGE); // avisa
            }
        });

        btnBuscarNombre.addActionListener(e -> { // al apretar buscar por nombre
            // levanta ventanita flotante
            JTextField txtBusqueda = new JTextField(20); // caja de tipear
            JTable tblResultados = new JTable(); // grilla chica
            tblResultados.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // letra base
            tblResultados.setRowHeight(24); // altura
            tblResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12)); // negrita en columnas
            tblResultados.getTableHeader().setBackground(new Color(30, 60, 114)); // azul
            tblResultados.getTableHeader().setForeground(Color.WHITE); // letra blanca
            tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // permite agarrar de a una fila

            JButton btnBuscar = new JButton("Buscar"); // boton flotante
            estilizarBtn(btnBuscar, new Color(30, 60, 114)); // azulito

            JPanel panelBusq = new JPanel(new BorderLayout(8, 8)); // panel flotante
            JPanel panelTop  = new JPanel(new FlowLayout(FlowLayout.LEFT)); // arriba flotante
            panelTop.add(new JLabel("Nombre / Apellido / Cédula:")); // textito
            panelTop.add(txtBusqueda); // caja buscar
            panelTop.add(btnBuscar); // boton buscar
            panelBusq.add(panelTop, BorderLayout.NORTH); // une
            panelBusq.add(new JScrollPane(tblResultados), BorderLayout.CENTER); // une grilla
            panelBusq.setPreferredSize(new Dimension(620, 300)); // estira ventanita

            btnBuscar.addActionListener(ev -> { // cuando apretas buscar adentro de la ventanita
                String criterio = txtBusqueda.getText().trim(); // toma el texto
                if (!criterio.isEmpty()) { // si hay algo
                    modeloEmp.buscar(tblResultados, criterio); // llama al modelo para que llene la grilla chica
                }
            });

            int result = JOptionPane.showConfirmDialog( // muestra la ventanita parando el programa principal
                this, panelBusq, "Buscar Empleado", // parametros de visualizacion
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE); // estilo de confirmacion

            if (result == JOptionPane.OK_OPTION) { // si apreto aceptar
                int fila = tblResultados.getSelectedRow(); // mira que fila toco
                if (fila == -1) { // si no toco nada
                    JOptionPane.showMessageDialog(this, // le dice que le erro
                        "Selecciona un empleado de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE); // advertencia
                    return; // aborta
                }
                // saca datos de la grilla
                int idSeleccionado = (int) tblResultados.getModel().getValueAt(fila, 0); // agarra id
                String nombre      = tblResultados.getModel().getValueAt(fila, 2).toString(); // agarra nombre
                String apellido    = tblResultados.getModel().getValueAt(fila, 3).toString(); // agarra apellido
                txtIdEmpleado.setText(String.valueOf(idSeleccionado)); // escupe id al formulario principal
                txtNombreEmp.setText(nombre + " " + apellido); // escupe nombres
            }
        });

        btnUsarHoraActual.addActionListener(e -> { // boton de acceso rapido de hora
            String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")); // toma hora windows
            txtHoraEntrada.setText(hora); // rellena
            txtHoraSalida.setText(hora); // rellena
        });

        btnEntrada.addActionListener(e -> { // al dar click en boton entrada
            try { // previene crasheos
                int id = Integer.parseInt(txtIdEmpleado.getText().trim()); // saca id numerico
                if (modelo.registrarEntrada(id, txtFecha.getText().trim(), txtHoraEntrada.getText().trim())) { // invoca al modelo mandando textos
                    JOptionPane.showMessageDialog(this, " Entrada registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE); // cartelito feliz
                    modelo.listarTodo(tblAsistencia); // recarga tabla
                }
            } catch (NumberFormatException ex) { // si no hay un numero valido de id
                JOptionPane.showMessageDialog(this, "ID de empleado inválido.", "Error", JOptionPane.ERROR_MESSAGE); // te detiene
            }
        });

        btnSalida.addActionListener(e -> { // al dar click en boton salida
            try { // asegura
                int id = Integer.parseInt(txtIdEmpleado.getText().trim()); // toma id
                if (modelo.registrarSalida(id, txtFecha.getText().trim(), txtHoraSalida.getText().trim())) { // manda orden a base
                    JOptionPane.showMessageDialog(this, " Salida registrada. Horas calculadas.", "Éxito", JOptionPane.INFORMATION_MESSAGE); // cartel ok
                    modelo.listarTodo(tblAsistencia); // recarga vista
                }
            } catch (NumberFormatException ex) { // por si fallas el tipeo
                JOptionPane.showMessageDialog(this, "ID de empleado inválido.", "Error", JOptionPane.ERROR_MESSAGE); // alerta fatal
            }
        });

        btnAusencia.addActionListener(e -> { // boton rojo
            try { // protege bloque
                int id = Integer.parseInt(txtIdEmpleado.getText().trim()); // id
                if (modelo.registrarAusencia(id, txtFecha.getText().trim(), txtMotivo.getText().trim())) { // manda a guardar falta
                    JOptionPane.showMessageDialog(this, " Ausencia registrada.", "Registrado", JOptionPane.INFORMATION_MESSAGE); // canta victoria
                    modelo.listarTodo(tblAsistencia); // actualiza
                }
            } catch (NumberFormatException ex) { // falla num
                JOptionPane.showMessageDialog(this, "ID de empleado inválido.", "Error", JOptionPane.ERROR_MESSAGE); // cartel malo
            }
        });

        btnConsultarEmp.addActionListener(e -> { // busca una persona en pestaña dos
            try { // blinda ejecucion
                int id = Integer.parseInt(txtIdEmpConsulta.getText().trim()); // parsea id
                modelo.listarPorEmpleado(tblConsulta, id); // le dice al modelo que vuelque datos en la grilla dos
            } catch (NumberFormatException ex) { // sino
                JOptionPane.showMessageDialog(this, "ID de empleado inválido.", "Error", JOptionPane.ERROR_MESSAGE); // frena en seco
            }
        });

        btnConsultarFecha.addActionListener(e -> // flechita lambda para boton entre fechas
            modelo.listarPorFecha(tblConsulta, txtFechaInicio.getText().trim(), txtFechaFin.getText().trim())); // dispara la query

        btnReporteAusencias.addActionListener(e -> // boton naranja
            modelo.reporteAusencias(tblConsulta, txtFechaInicio.getText().trim(), txtFechaFin.getText().trim())); // dispara query especial de faltas

        btnVerTodo.addActionListener(e -> modelo.listarTodo(tblConsulta)); // resetea tabla mostrandola completa de nuevo
    }

    private void estilizarBtn(JButton btn, Color color) { // metodo helper visual
        btn.setBackground(color); // aplica tinte
        btn.setForeground(Color.BLACK); // letra oscura
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12)); // fuente
        btn.setFocusPainted(false); // quita cuadrado punteado molesto
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // manito en el mouse
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12)); // rellenos interiores
    }

    private void estilizarTabla(JTable tabla) { // utilitario para las grillas
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // tamano letra items
        tabla.setRowHeight(24); // gordura de fila
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // no multipick
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12)); // encabezados gordos
        tabla.getTableHeader().setBackground(new Color(39, 110, 58)); // verde oscuro al tope
        tabla.getTableHeader().setForeground(Color.BLACK); // letra negra
        tabla.setAutoCreateRowSorter(true); // habilita ordenar dandole click al titulo de columna
    }
}