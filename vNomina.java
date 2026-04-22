package Vista;

import Modelo.mEmpleados;
import Modelo.mNomina;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
//@author Hector Mojica,Cesar Rojas, Sebastian Medina

public class vNomina extends javax.swing.JFrame { // Clase que crea la ventana de Nómina, hereda de JFrame

    //  Variables de la Pestaña 1: Generar Nómina 
    private javax.swing.JTextField txtIdEmpleado; // Caja para escribir el ID del empleado
    private javax.swing.JTextField txtNombreEmpleado; // Caja (bloqueada) para mostrar el nombre
    private javax.swing.JTextField txtPeriodo; // Caja para el mes de la nómina
    private javax.swing.JTextField txtSalarioBase; // Caja para el salario base mensual
    private javax.swing.JTextField txtFechaInicio; // Fecha de inicio del cálculo
    private javax.swing.JTextField txtFechaFin; // Fecha de fin del cálculo
    private javax.swing.JTextField txtHorasTrabajadas; // Caja (bloqueada) que muestra las horas calculadas
    private javax.swing.JTextField txtBonificaciones; // Caja para ingresar dinero extra manual
    private javax.swing.JTextField txtDeducciones; // Caja (bloqueada) con el total de descuentos
    private javax.swing.JTextField txtSalarioNeto; // Caja (bloqueada) con el total final a pagar
    private javax.swing.JTextField txtFechaPago; // Fecha en la que se realiza el pago
    private javax.swing.JTextArea  txtObservacion; // Área de texto grande para notas o comentarios
    
    // Botones de acción de la pestaña 1
    private javax.swing.JButton    btnBuscarEmp; 
    private javax.swing.JButton    btnBuscarNombre;
    private javax.swing.JButton    btnCalcularHoras;
    private javax.swing.JButton    btnCargarDeducciones;
    private javax.swing.JButton    btnCalcularNeto;
    private javax.swing.JButton    btnGenerarNomina;
    private javax.swing.JButton    btnLimpiar;

    //  Variables de la Pestaña 2: Historial 
    private javax.swing.JTable     tblHistorial; // Tabla pequeña de vista rápida (va en la pestaña 1)
    private javax.swing.JTable     tblHistorial2; // Tabla grande que ocupa toda la pestaña 2
    private javax.swing.JButton    btnVerHistorial; // Botón para ver absolutamente todas las nóminas
    private javax.swing.JTextField txtIdEmpHistorial; // Caja para filtrar historial por ID
    private javax.swing.JButton    btnHistorialEmp; // Botón para aplicar el filtro por ID

    //  Variables de la Pestaña 3: Deducciones 
    private javax.swing.JTextField txtIdEmpDeduccion; // ID del empleado para asignarle el descuento
    private javax.swing.JComboBox<String> cmbTipoDeduccion; // Menú desplegable con tipos (Préstamo, Salud, etc.)
    private javax.swing.JTextField txtMontoDeduccion; // Cuánto se le va a descontar
    private javax.swing.JTextField txtDescDeduccion; // Descripción o motivo del descuento
    private javax.swing.JTextField txtFechaDeduccion; // Fecha del descuento
    private javax.swing.JButton    btnGuardarDeduccion; // Botón para guardar en BD
    private javax.swing.JButton    btnCargarDeduccTable; // Botón para ver los descuentos actuales de un empleado
    private javax.swing.JButton    btnDesactivarDeduccion; // Botón para "apagar" un descuento recurrente
    private javax.swing.JTable     tblDeducciones; // Tabla que muestra los descuentos

    // Panel con Pestañas
    private javax.swing.JTabbedPane tabPanel; // El contenedor especial que crea las "pestañitas" superiores

    // Instancias de los modelos (Conexión con la lógica de negocio y Base de Datos)
    private final mNomina   modelo   = new mNomina();
    private final mEmpleados modeloEmp = new mEmpleados();

    // Constructor
    public vNomina() {
        initComponents(); // Carga todo el diseño visual al abrir la ventana
    }

    private void initComponents() {
        tabPanel = new JTabbedPane(); // Crea el sistema de pestañas

        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Si se cierra, SOLO se cierra esta ventana, no toda la aplicación
        setTitle(" Módulo de Nómina"); // Título superior de la ventana
        setPreferredSize(new Dimension(1150, 660)); // Tamaño grande para que quepan todos los formularios
        setLayout(new BorderLayout()); // Distribución de bordes (Norte, Sur, Este, Oeste, Centro)

        //  Panel del título (Parte superior) 
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel alineado a la izquierda
        panelTitulo.setBackground(new Color(150, 80, 0)); // Color naranja oscuro / café
        JLabel lblTit = new JLabel("  Gestión de Nómina"); // Texto del título
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTit.setForeground(Color.WHITE);
        panelTitulo.add(lblTit); // Agrega el texto al panel
        add(panelTitulo, BorderLayout.NORTH); // Coloca el panel en la parte más alta de la ventana

        // ==========================================
        //  TAB 1: Pestaña Generar Nómina
        // ==========================================
        JPanel tabGenerar = new JPanel(new BorderLayout(10, 10)); // Panel base de la pestaña 1
        tabGenerar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes
        tabGenerar.setBackground(new Color(245, 247, 250)); // Gris muy claro

        //  Panel izquierdo: Formulario de la nómina 
        JPanel panelForm = new JPanel(new GridBagLayout()); // Cuadrícula para alinear etiquetas y cajas
        panelForm.setBackground(Color.WHITE );
        panelForm.setBorder(BorderFactory.createTitledBorder( // Le pone un marco con el título "Cálculo de Nómina"
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Cálculo de Nómina", 0, 0,
            new Font("Segoe UI", Font.BOLD, 13), new Color(150, 80, 0)));

        GridBagConstraints gbc = new GridBagConstraints(); // Reglas de posición para el GridBagLayout
        gbc.fill = GridBagConstraints.HORIZONTAL; // Estirar a lo ancho
        gbc.insets = new Insets(5, 10, 5, 10); // Márgenes entre componentes
        gbc.weightx = 1.0;

        // Inicializando las cajitas de texto de la Pestaña 1
        txtIdEmpleado      = new JTextField();
        txtNombreEmpleado  = new JTextField(); txtNombreEmpleado.setEditable(false); txtNombreEmpleado.setBackground(new Color(230, 230, 230)); // Bloqueado y gris
        txtPeriodo         = new JTextField("2024-01"); // Valor por defecto sugerido
        txtSalarioBase     = new JTextField("0.00");
        // Obtiene la fecha actual, la pone en el día 1 del mes y la convierte a texto
        txtFechaInicio     = new JTextField(LocalDate.now().withDayOfMonth(1).format(java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        // Obtiene la fecha de hoy
        txtFechaFin        = new JTextField(LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        txtHorasTrabajadas = new JTextField("0.0"); txtHorasTrabajadas.setEditable(false); txtHorasTrabajadas.setBackground(new Color(230, 230, 230));
        txtBonificaciones  = new JTextField("0.00");
        txtDeducciones     = new JTextField("0.00"); txtDeducciones.setEditable(false); txtDeducciones.setBackground(new Color(230, 230, 230));
        txtSalarioNeto     = new JTextField("0.00"); txtSalarioNeto.setEditable(false); txtSalarioNeto.setBackground(new Color(220, 255, 220)); // Bloqueado y verde claro
        txtFechaPago       = new JTextField(LocalDate.now().toString());
        
        txtObservacion     = new JTextArea(3, 20); // Área de texto de 3 filas
        txtObservacion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtObservacion.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        txtObservacion.setLineWrap(true); // Hace que el texto baje de línea automáticamente
        txtObservacion.setWrapStyleWord(true); // Evita cortar palabras a la mitad

        // Botones
        btnBuscarEmp         = new JButton("Buscar por ID");
        btnBuscarNombre      = new JButton("Buscar por Nombre");
        btnCalcularHoras     = new JButton("Calcular horas del período");
        btnCargarDeducciones = new JButton("Cargar deducciones activas");
        btnCalcularNeto      = new JButton("Calcular Salario Neto");
        btnGenerarNomina     = new JButton("Generar y Guardar Nómina");
        btnLimpiar           = new JButton("Limpiar");

        // Arreglos temporales para crear el formulario en un bucle y ahorrar muchas líneas de código
        String[] etiquetas = {
            "ID Empleado:", "Nombre Empleado:", "Período (YYYY-MM):", "Salario Base $:",
            "Fecha Inicio (YYYY/MM/DD):", "Fecha Fin (YYYY/MM/DD):", "Horas Trabajadas:",
            "Bonificaciones $:", "Deducciones $:", "SALARIO NETO $:", "Fecha de Pago:", "Observación:"
        };

        JComponent[] comps = {
            txtIdEmpleado, txtNombreEmpleado, txtPeriodo, txtSalarioBase,
            txtFechaInicio, txtFechaFin, txtHorasTrabajadas, txtBonificaciones,
            txtDeducciones, txtSalarioNeto, txtFechaPago,
            new JScrollPane(txtObservacion) // Al área de texto se le pone un Scroll por si escriben mucho
        };

        // Bucle que recorre las etiquetas y componentes para pegarlos en el formulario uno debajo de otro
        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            if (etiquetas[i].startsWith("SALARIO")) { // Si es la etiqueta final del salario
                lbl.setForeground(new Color(0, 120, 0)); // La pone en color verde oscuro
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            }
            gbc.gridx = 0; gbc.gridy = i * 2; // Fila par para las etiquetas
            panelForm.add(lbl, gbc);

            gbc.gridy = i * 2 + 1; // Fila impar para las cajas de texto
            comps[i].setFont(new Font("Segoe UI", Font.PLAIN, 12));
            
            // Fija tamaños dependiendo si es una caja de texto o el área de notas grande
            if (comps[i] instanceof JTextField) {
                ((JTextField) comps[i]).setPreferredSize(new Dimension(290, 28));
            } else {
                comps[i].setPreferredSize(new Dimension(290, 70));
            }
            panelForm.add(comps[i], gbc);
        }

        // Bucle para acomodar los botones debajo del formulario
        int baseRow = etiquetas.length * 2; // Fila donde terminaron las cajas
        for (JButton btn : new JButton[]{btnBuscarEmp, btnBuscarNombre, btnCalcularHoras, btnCargarDeducciones,
                                          btnCalcularNeto, btnGenerarNomina, btnLimpiar}) {
            gbc.gridx = 0; gbc.gridy = baseRow++;
            gbc.insets = new Insets(4, 10, 4, 10);
            panelForm.add(btn, gbc);
        }

        // Truco visual: Una caja invisible expansible al final para empujar todo hacia arriba
        gbc.gridy = baseRow;
        gbc.weighty = 1.0;
        panelForm.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;

        // Pinta cada botón con colores distintos usando el método auxiliar del final
        estilizarBtn(btnBuscarEmp,         new Color(30, 60, 114)); // Azul
        estilizarBtn(btnBuscarNombre,       new Color(0, 100, 140)); // Azul claro
        estilizarBtn(btnCalcularHoras,      new Color(80, 120, 80)); // Verde opaco
        estilizarBtn(btnCargarDeducciones,  new Color(100, 60, 140)); // Morado
        estilizarBtn(btnCalcularNeto,       new Color(150, 80, 0)); // Naranja
        estilizarBtn(btnGenerarNomina,      new Color(0, 130, 0)); // Verde brillante (Guardar)
        estilizarBtn(btnLimpiar,            new Color(80, 80, 80)); // Gris

        // Se envuelve todo el formulario en un Scroll vertical por si la pantalla del monitor es muy pequeña
        JScrollPane scrollForm = new JScrollPane(panelForm);
        scrollForm.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollForm.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollForm.setPreferredSize(new Dimension(340, 0)); 
        scrollForm.getVerticalScrollBar().setUnitIncrement(16); // Hace que la rueda del mouse baje más rápido

        // Panel derecho: Tabla historial rápido 
        tblHistorial = new JTable(); // Inicializa la tabla
        estilizarTabla(tblHistorial, new Color(150, 80, 0)); // Pinta el encabezado de naranja

        JPanel panelDerHistorial = new JPanel(new BorderLayout(5, 5));
        panelDerHistorial.setBackground(Color.WHITE);
        panelDerHistorial.setBorder(BorderFactory.createTitledBorder("Vista rápida del historial"));
        modelo.listarHistorial(tblHistorial); // Consulta la BD y llena la tabla con las últimas nóminas
        panelDerHistorial.add(new JScrollPane(tblHistorial), BorderLayout.CENTER);

        // Agrega el formulario a la izquierda y la tabla a la derecha en la Pestaña 1
        tabGenerar.add(scrollForm, BorderLayout.WEST);
        tabGenerar.add(panelDerHistorial, BorderLayout.CENTER);
        tabPanel.addTab(" Generar Nómina", tabGenerar); // Mete la Pestaña 1 al TabPanel general


        // ==========================================
        //  TAB 2: Pestaña Historial de Nóminas
        // ==========================================
        JPanel tabHistorial = new JPanel(new BorderLayout(10, 10)); // Panel base
        tabHistorial.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Barra superior de la pestaña 2 con filtros
        JPanel barraHistorial = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barraHistorial.setBackground(Color.WHITE);
        txtIdEmpHistorial = new JTextField(8);
        btnHistorialEmp   = new JButton("Por Empleado");
        btnVerHistorial   = new JButton("Ver Todo");
        estilizarBtn(btnHistorialEmp, new Color(150, 80, 0));
        estilizarBtn(btnVerHistorial, new Color(80, 80, 80));
        barraHistorial.add(new JLabel("ID Empleado:"));
        barraHistorial.add(txtIdEmpHistorial);
        barraHistorial.add(btnHistorialEmp);
        barraHistorial.add(btnVerHistorial);
        barraHistorial.setBorder(BorderFactory.createTitledBorder("Filtrar Historial de Nóminas"));

        tblHistorial2 = new JTable(); // Segunda tabla (ocupará todo el espacio)
        estilizarTabla(tblHistorial2, new Color(150, 80, 0));
        modelo.listarHistorial(tblHistorial2); // Llena la tabla completa desde BD

        tabHistorial.add(barraHistorial, BorderLayout.NORTH);
        tabHistorial.add(new JScrollPane(tblHistorial2), BorderLayout.CENTER);
        tabPanel.addTab(" Historial de Nóminas", tabHistorial); // Mete la Pestaña 2 al TabPanel


        // ==========================================
        //  TAB 3: Pestaña Gestión de Deducciones
        // ==========================================
        JPanel tabDeducciones = new JPanel(new BorderLayout(10, 10));
        tabDeducciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabDeducciones.setBackground(new Color(245, 247, 250));

        // Formulario izquierdo para crear Deducciones
        JPanel panelDeducForm = new JPanel(new GridBagLayout());
        panelDeducForm.setBackground(Color.WHITE);
        panelDeducForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Nueva Deducción", 0, 0,
            new Font("Segoe UI", Font.BOLD, 13), new Color(100, 60, 140))); // Título en morado

        txtIdEmpDeduccion    = new JTextField();
        cmbTipoDeduccion     = new JComboBox<>(new String[]{"Ausencia", "Préstamo", "Seguro Social", "ARL", "Otro"}); // Menú de opciones
        txtMontoDeduccion    = new JTextField("0.00");
        txtDescDeduccion     = new JTextField();
        txtFechaDeduccion    = new JTextField(LocalDate.now().toString());
        btnGuardarDeduccion  = new JButton("Guardar Deducción");
        btnCargarDeduccTable = new JButton("Ver Deducciones del Empleado");
        btnDesactivarDeduccion = new JButton("Desactivar Seleccionada");

        // Bucle similar al de la pestaña 1 para armar el formulario rápidamente
        String[] etiquetasDed = {"ID Empleado:", "Tipo:", "Monto $:", "Descripción:", "Fecha:"};
        JComponent[] compsDed = {txtIdEmpDeduccion, cmbTipoDeduccion, txtMontoDeduccion, txtDescDeduccion, txtFechaDeduccion};

        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.fill = GridBagConstraints.HORIZONTAL;
        gbcD.weightx = 1.0;
        gbcD.insets = new Insets(5, 10, 5, 10);

        for (int i = 0; i < etiquetasDed.length; i++) {
            JLabel lbl = new JLabel(etiquetasDed[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            gbcD.gridx = 0; gbcD.gridy = i * 2;
            panelDeducForm.add(lbl, gbcD);

            gbcD.gridy = i * 2 + 1;
            compsDed[i].setPreferredSize(new Dimension(260, 28));
            compsDed[i].setFont(new Font("Segoe UI", Font.PLAIN, 12));
            panelDeducForm.add(compsDed[i], gbcD);
        }

        int br = etiquetasDed.length * 2;
        for (JButton b : new JButton[]{btnGuardarDeduccion, btnCargarDeduccTable, btnDesactivarDeduccion}) {
            gbcD.gridx = 0; gbcD.gridy = br++;
            gbcD.insets = new Insets(6, 10, 6, 10);
            panelDeducForm.add(b, gbcD);
        }

        // Fila expansora al final
        gbcD.gridy = br;
        gbcD.weighty = 1.0;
        panelDeducForm.add(Box.createVerticalGlue(), gbcD);

        estilizarBtn(btnGuardarDeduccion,    new Color(0, 130, 0));
        estilizarBtn(btnCargarDeduccTable,   new Color(100, 60, 140));
        estilizarBtn(btnDesactivarDeduccion, new Color(150, 30, 30)); // Rojo para desactivar

        // Scroll para el formulario izquierdo
        JScrollPane scrollDeducForm = new JScrollPane(panelDeducForm);
        scrollDeducForm.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDeducForm.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDeducForm.setPreferredSize(new Dimension(320, 0));
        scrollDeducForm.getVerticalScrollBar().setUnitIncrement(16);

        // Tabla derecha para ver deducciones de un empleado
        tblDeducciones = new JTable();
        estilizarTabla(tblDeducciones, new Color(100, 60, 140)); // Encabezado morado

        tabDeducciones.add(scrollDeducForm, BorderLayout.WEST);
        tabDeducciones.add(new JScrollPane(tblDeducciones), BorderLayout.CENTER);
        tabPanel.addTab(" Deducciones", tabDeducciones); // Mete la Pestaña 3

        //  Finalizar el armado de la ventana principal 
        add(tabPanel, BorderLayout.CENTER); // Agrega el gran bloque de pestañas a la ventana
        pack(); // Ajusta los componentes
        setLocationRelativeTo(null); // Centra la ventana

        // ==========================================
        //  Eventos (Programación de qué hace cada botón)
        // ==========================================
        
        // Pestaña 1: Botón Buscar por ID
        btnBuscarEmp.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdEmpleado.getText().trim()); // Lee el ID
                String[] datos = modeloEmp.obtenerPorId(id); // Busca al empleado en la BD usando el modelo
                if (datos != null) { // Si sí existe
                    txtNombreEmpleado.setText(datos[2] + " " + datos[3]); // Concatena Nombre + Apellido
                    txtSalarioBase.setText(datos[12]); // Trae el salario base guardado en su contrato
                } else JOptionPane.showMessageDialog(this, "Empleado no encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Pestaña 1: Botón Buscar por Nombre (Abre una sub-ventana flotante)
        btnBuscarNombre.addActionListener(e -> {
            JTextField txtBusqueda = new JTextField(20);
            JTable tblResultados = new JTable(); // Crea una tablita temporal
            tblResultados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            tblResultados.setRowHeight(24);
            tblResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            tblResultados.getTableHeader().setBackground(new Color(30, 60, 114));
            tblResultados.getTableHeader().setForeground(Color.WHITE);
            tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JButton btnBuscar = new JButton("Buscar");
            estilizarBtn(btnBuscar, new Color(30, 60, 114));

            JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelTop.add(new JLabel("Nombre / Apellido / Cédula:"));
            panelTop.add(txtBusqueda);
            panelTop.add(btnBuscar);

            JPanel panelBusq = new JPanel(new BorderLayout(8, 8));
            panelBusq.add(panelTop, BorderLayout.NORTH);
            panelBusq.add(new JScrollPane(tblResultados), BorderLayout.CENTER);
            panelBusq.setPreferredSize(new Dimension(620, 300));

            // Si dan clic en buscar en la subventana...
            btnBuscar.addActionListener(ev -> {
                String criterio = txtBusqueda.getText().trim();
                if (!criterio.isEmpty()) modeloEmp.buscar(tblResultados, criterio); // Filtra en la tabla
            });

            // También busca si dan "Enter" en la cajita
            txtBusqueda.addActionListener(ev -> {
                String criterio = txtBusqueda.getText().trim();
                if (!criterio.isEmpty()) modeloEmp.buscar(tblResultados, criterio);
            });

            // Lanza la sub-ventana flotante como un diálogo y espera a que el usuario le de OK
            int result = JOptionPane.showConfirmDialog(
                this, panelBusq, "Buscar Empleado",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) { // Si dieron OK
                int fila = tblResultados.getSelectedRow(); // Revisa qué fila seleccionaron
                if (fila == -1) {
                    JOptionPane.showMessageDialog(this,
                        "Selecciona un empleado de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Saca los datos de esa fila
                int idSel      = (int)    tblResultados.getModel().getValueAt(fila, 0); 
                String nombre  = tblResultados.getModel().getValueAt(fila, 2).toString();
                String apellido= tblResultados.getModel().getValueAt(fila, 3).toString();
                Object salObj  = tblResultados.getModel().getValueAt(fila, 8); // Columna 8 es Salario_Base

                // Los pasa al formulario principal
                txtIdEmpleado.setText(String.valueOf(idSel));
                txtNombreEmpleado.setText(nombre + " " + apellido);
                if (salObj != null) txtSalarioBase.setText(salObj.toString().replace(",", "."));
            }
        });

        // Pestaña 1: Calcular Horas
        btnCalcularHoras.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdEmpleado.getText().trim());
                String inicio = txtFechaInicio.getText().trim();
                String fin    = txtFechaFin.getText().trim();

                // Va a la BD a revisar las entradas y salidas de ese empleado en ese rango de fechas
                double horas = modelo.calcularHorasPeriodo(id, inicio, fin);

                txtHorasTrabajadas.setText(String.format("%.1f", horas)); // Lo pone en pantalla
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error al calcular horas. Verifica fechas y ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Pestaña 1: Cargar Deducciones (Traer de la BD)
        btnCargarDeducciones.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdEmpleado.getText().trim());
                double ded = modelo.calcularDeducciones(id); // Suma todos los descuentos activos de ese empleado
                txtDeducciones.setText(String.format(java.util.Locale.US, "%.2f", ded));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Pestaña 1: Calcular Salario Neto
        btnCalcularNeto.addActionListener(e -> {
            try {
                // Lee las cajas y convierte de texto a número decimal (cambia comas por puntos por compatibilidad)
                double base  = Double.parseDouble(txtSalarioBase.getText().trim().replace(",", "."));
                double bonif = Double.parseDouble(txtBonificaciones.getText().trim().replace(",", "."));
                double deduc = Double.parseDouble(txtDeducciones.getText().trim().replace(",", "."));

                // Ecuación principal de la nómina
                double neto  = base + bonif - deduc;

                // Math.max(neto, 0) asegura que el salario nunca dé negativo (ej: si le descuentan más de lo que gana)
                txtSalarioNeto.setText(String.format(java.util.Locale.US, "%.2f", Math.max(neto, 0)));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error: revisa que todos los campos sean números válidos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Pestaña 1: Botón Guardar Nómina Final
        btnGenerarNomina.addActionListener(e -> {
            try {
                int id     = Integer.parseInt(txtIdEmpleado.getText().trim());
                double base  = Double.parseDouble(txtSalarioBase.getText().trim().replace(",", "."));
                double horas = Double.parseDouble(txtHorasTrabajadas.getText().trim().replace(",", "."));
                double bonif = Double.parseDouble(txtBonificaciones.getText().trim().replace(",", "."));
                double deduc = Double.parseDouble(txtDeducciones.getText().trim().replace(",", "."));
                
                int conf = JOptionPane.showConfirmDialog(this,
                    "¿Generar nómina para período " + txtPeriodo.getText() + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
                
                if (conf == JOptionPane.YES_OPTION) {
                    // Manda todo al modelo para hacer el INSERT en la Base de Datos
                    if (modelo.generarNomina(id, txtPeriodo.getText().trim(), base, horas, bonif, deduc,
                            txtFechaPago.getText().trim(), txtObservacion.getText().trim())) {
                        JOptionPane.showMessageDialog(this,
                            "Nómina generada exitosamente. Salario neto: $" + txtSalarioNeto.getText(),
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        modelo.listarHistorial(tblHistorial); // Actualiza la tablita derecha al instante
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Revisa los campos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Pestaña 1: Limpiar Cajas
        btnLimpiar.addActionListener(e -> {
            for (JTextField tf : new JTextField[]{txtIdEmpleado, txtNombreEmpleado, txtHorasTrabajadas, txtDeducciones, txtSalarioNeto})
                tf.setText("");
            txtSalarioBase.setText("0.00");
            txtBonificaciones.setText("0.00");
            txtObservacion.setText("");
        });

        // Pestaña 2: Botón Ver Todo el Historial
        btnVerHistorial.addActionListener(e -> modelo.listarHistorial(tblHistorial2));

        // Pestaña 2: Botón Filtrar por ID Empleado
        btnHistorialEmp.addActionListener(e -> {
            try {
                modelo.listarPorEmpleado(tblHistorial2, Integer.parseInt(txtIdEmpHistorial.getText().trim()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Pestaña 3: Guardar nueva deducción
        btnGuardarDeduccion.addActionListener(e -> {
            try {
                int id      = Integer.parseInt(txtIdEmpDeduccion.getText().trim());
                double monto = Double.parseDouble(txtMontoDeduccion.getText().trim());
                // Llama al modelo para insertar la deducción en BD
                if (modelo.insertarDeduccion(id, cmbTipoDeduccion.getSelectedItem().toString(),
                        monto, txtDescDeduccion.getText().trim(), txtFechaDeduccion.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Deducción guardada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    modelo.listarDeducciones(tblDeducciones, id); // Refresca la tabla de la derecha
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID o monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Pestaña 3: Mostrar deducciones de un empleado específico en la tabla
        btnCargarDeduccTable.addActionListener(e -> {
            try {
                modelo.listarDeducciones(tblDeducciones, Integer.parseInt(txtIdEmpDeduccion.getText().trim()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Pestaña 3: Desactivar una deducción (Para que ya no se descuente el próximo mes)
        btnDesactivarDeduccion.addActionListener(e -> {
            int fila = tblDeducciones.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una deducción.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idDed = (int) tblDeducciones.getModel().getValueAt(fila, 0); // Toma el ID interno de esa deducción
            if (modelo.desactivarDeduccion(idDed)) { // Hace un UPDATE en la BD poniendo estado=inactivo
                JOptionPane.showMessageDialog(this, "Deducción desactivada.", "OK", JOptionPane.INFORMATION_MESSAGE);
                try {
                    // Refresca la tabla para que se quite
                    modelo.listarDeducciones(tblDeducciones, Integer.parseInt(txtIdEmpDeduccion.getText().trim()));
                } catch (Exception ex) { /* ignorar */ }
            }
        });
    }

    //  Métodos auxiliares para no repetir diseño de botones y tablas  
    private void estilizarBtn(JButton btn, Color color) {
        btn.setBackground(color); // Pinta el fondo
        btn.setForeground(Color.BLACK);  // Pinta la letra (Podrías cambiar a WHITE si te parece que contrasta mejor con fondos oscuros)
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false); // Quita el marco punteado de selección
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Pone la manito
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12)); // Aumenta tamaño interno
    }

    private void estilizarTabla(JTable tabla, Color headerColor) {
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(24); // Hace las filas más anchitas
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo deja seleccionar de a 1 fila
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(headerColor); // El color que le pases por parámetro
        tabla.getTableHeader().setForeground(Color.BLACK); // De nuevo, si ves que no se lee bien con negro, cámbialo a Color.WHITE
        tabla.setAutoCreateRowSorter(true); // ¡Magia! Permite dar clic a las columnas para ordenar de la A a la Z
    }
}
