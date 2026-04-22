package Vista;

import Modelo.mEmpleados;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

//@author Hector Mojica,Cesar Rojas, Sebastian Medina
public class vEmpleados extends javax.swing.JFrame { // declara la clase principal de la ventana que hereda de jframe

    //  Componentes 
    private javax.swing.JPanel           panelFormulario; // declara el panel que contendra las cajas de texto y etiquetas
    private javax.swing.JLabel           lblIdEmpleado; // declara la etiqueta para el texto de id
    private javax.swing.JTextField       txtIdEmpleado; // declara la caja de texto para el id
    private javax.swing.JLabel           lblCedula; // declara la etiqueta para el texto de cedula
    private javax.swing.JTextField       txtCedula; // declara la caja de texto para la cedula
    private javax.swing.JLabel           lblNombre; // declara la etiqueta para el texto de nombre
    private javax.swing.JTextField       txtNombre; // declara la caja de texto para el nombre
    private javax.swing.JLabel           lblApellido; // declara la etiqueta para el texto de apellido
    private javax.swing.JTextField       txtApellido; // declara la caja de texto para el apellido
    private javax.swing.JLabel           lblFecNac; // declara la etiqueta para la fecha de nacimiento
    private javax.swing.JTextField       txtFecNac; // declara la caja de texto para la fecha de nacimiento
    private javax.swing.JLabel           lblGenero; // declara la etiqueta para el genero
    private javax.swing.JComboBox<String> cmbGenero; // declara una lista desplegable para seleccionar el genero
    private javax.swing.JLabel           lblTelefono; // declara la etiqueta para el telefono
    private javax.swing.JTextField       txtTelefono; // declara la caja de texto para el telefono
    private javax.swing.JLabel           lblCorreo; // declara la etiqueta para el correo
    private javax.swing.JTextField       txtCorreo; // declara la caja de texto para el correo
    private javax.swing.JLabel           lblDireccion; // declara la etiqueta para la direccion
    private javax.swing.JTextField       txtDireccion; // declara la caja de texto para la direccion
    private javax.swing.JLabel           lblCargo; // declara la etiqueta para el cargo
    private javax.swing.JTextField       txtCargo; // declara la caja de texto para el cargo
    private javax.swing.JLabel           lblDepartamento; // declara la etiqueta para el departamento
    private javax.swing.JTextField       txtDepartamento; // declara la caja de texto para el departamento
    private javax.swing.JLabel           lblFecIngreso; // declara la etiqueta para la fecha de ingreso
    private javax.swing.JTextField       txtFecIngreso; // declara la caja de texto para la fecha de ingreso
    private javax.swing.JLabel           lblSalario; // declara la etiqueta para el salario
    private javax.swing.JTextField       txtSalario; // declara la caja de texto para el salario
    private javax.swing.JLabel           lblEstado; // declara la etiqueta para el estado del empleado
    private javax.swing.JComboBox<String> cmbEstado; // declara una lista desplegable para el estado activo/inactivo

    private javax.swing.JPanel           panelBotones; // declara el panel que contendra los botones de accion
    private javax.swing.JButton          btnNuevo; // declara el boton para limpiar y crear nuevo
    private javax.swing.JButton          btnGuardar; // declara el boton para guardar en base de datos
    private javax.swing.JButton          btnModificar; // declara el boton para actualizar datos
    private javax.swing.JButton          btnDarBaja; // declara el boton para inactivar empleado
    private javax.swing.JButton          btnEliminar; // declara el boton para borrar definitivamente
    private javax.swing.JButton          btnLimpiar; // declara el boton para borrar el texto de las cajas

    private javax.swing.JPanel           panelTabla; // declara el panel que contendra la tabla de visualizacion
    private javax.swing.JScrollPane      scrollTabla; // declara una barra de desplazamiento para la tabla
    private javax.swing.JTable           tblEmpleados; // declara la tabla grafica para mostrar datos
    private javax.swing.JTextField       txtBuscar; // declara la caja de texto para buscar empleados
    private javax.swing.JButton          btnBuscar; // declara el boton que ejecuta la busqueda
    private javax.swing.JButton          btnMostrarTodos; // declara el boton para quitar los filtros de busqueda
    private javax.swing.JCheckBox        chkSoloActivos; // declara una casilla de verificacion para filtrar solo activos
    private javax.swing.JComboBox<String> cmbCriterioBusqueda; // declara lista desplegable para elegir por que campo buscar

    //  Constantes de fecha 
    private static final DateTimeFormatter FMT_DISPLAY  = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // define el formato de fecha que ve el usuario
    private static final DateTimeFormatter FMT_BD       = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // define el formato de fecha que usa la base de datos
    private static final String            PH_FECHA     = "yyyy/MM/dd"; // define el texto fantasma (placeholder) para las fechas
    private static final Color             COLOR_PH     = new Color(150, 150, 150); // define el color gris para el texto fantasma
    private static final Color             COLOR_TEXTO  = new Color(0, 0, 0); // define el color negro para el texto normal

    //  Estado interno 
    private int idSeleccionado = -1; // variable para guardar el id del empleado clickeado en la tabla, inicializa en -1 (ninguno)
    private final mEmpleados modelo = new mEmpleados(); // crea una instancia constante del modelo de base de datos de empleados

    // Constructor 
    public vEmpleados() { // constructor que se ejecuta al crear la ventana
        initComponents(); // llama al metodo que construye y ubica todos los botones y cajas
        modelo.listarActivos(tblEmpleados); // llena la tabla inicialmente solo con empleados activos
    }

    //  Inicialización de componentes 
    private void initComponents() { // metodo principal que dibuja la interfaz

        // Crear paneles
        panelFormulario = new JPanel(); // instancia el panel de formulario
        panelBotones    = new JPanel(); // instancia el panel de botones
        panelTabla      = new JPanel(); // instancia el panel de la tabla

        // Crear campos de formulario
        lblIdEmpleado   = new JLabel("ID:"); // crea la etiqueta id
        txtIdEmpleado   = new JTextField(); // crea la caja de texto id
        lblCedula       = new JLabel("Cédula *:"); // crea la etiqueta cedula marcandola obligatoria
        txtCedula       = new JTextField(); // crea la caja de texto cedula
        lblNombre       = new JLabel("Nombre *:"); // crea la etiqueta nombre marcandola obligatoria
        txtNombre       = new JTextField(); // crea la caja de texto nombre
        lblApellido     = new JLabel("Apellido *:"); // crea la etiqueta apellido marcandola obligatoria
        txtApellido     = new JTextField(); // crea la caja de texto apellido
        lblFecNac       = new JLabel("Fecha de Nacimiento:"); // crea etiqueta fecha nacimiento
        txtFecNac       = new JTextField(PH_FECHA); // crea caja fecha nacimiento con el texto fantasma inicial
        lblGenero       = new JLabel("Género:"); // crea etiqueta genero
        cmbGenero       = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"}); // crea la lista de genero con opciones precargadas
        lblTelefono     = new JLabel("Teléfono:"); // crea etiqueta telefono
        txtTelefono     = new JTextField(); // crea caja telefono
        lblCorreo       = new JLabel("Correo:"); // crea etiqueta correo
        txtCorreo       = new JTextField(); // crea caja correo
        lblDireccion    = new JLabel("Dirección:"); // crea etiqueta direccion
        txtDireccion    = new JTextField(); // crea caja direccion
        lblCargo        = new JLabel("Cargo:"); // crea etiqueta cargo
        txtCargo        = new JTextField(); // crea caja cargo
        lblDepartamento = new JLabel("Departamento:"); // crea etiqueta departamento
        txtDepartamento = new JTextField(); // crea caja departamento
        lblFecIngreso   = new JLabel("Fecha de Ingreso:"); // crea etiqueta fecha de ingreso
        txtFecIngreso   = new JTextField(PH_FECHA); // crea caja fecha ingreso con texto fantasma inicial
        lblSalario      = new JLabel("Salario Base $:"); // crea etiqueta salario
        txtSalario      = new JTextField("0.00"); // crea caja salario con valor por defecto
        lblEstado       = new JLabel("Estado:"); // crea etiqueta estado
        cmbEstado       = new JComboBox<>(new String[]{"Activo", "Inactivo"}); // crea la lista desplegable de estado

        // Crear botones
        btnNuevo      = new JButton("Nuevo"); // crea el boton nuevo
        btnGuardar    = new JButton("Guardar"); // crea el boton guardar
        btnModificar  = new JButton("Modificar"); // crea el boton modificar
        btnDarBaja    = new JButton("Dar de Baja"); // crea el boton dar de baja
        btnEliminar   = new JButton("Eliminar"); // crea el boton eliminar
        btnLimpiar    = new JButton("Limpiar"); // crea el boton limpiar

        // Crear componentes de tabla y búsqueda
        tblEmpleados         = new JTable(); // inicializa la tabla visual
        scrollTabla          = new JScrollPane(tblEmpleados); // le pone una barra de scroll a la tabla
        txtBuscar            = new JTextField(); // inicializa la caja de busqueda
        btnBuscar            = new JButton("Buscar"); // inicializa el boton buscar
        btnMostrarTodos      = new JButton("Ver Todos"); // inicializa boton para ver todos
        chkSoloActivos       = new JCheckBox("Solo activos", true); // inicializa el checkbox marcado por defecto
        cmbCriterioBusqueda  = new JComboBox<>(new String[]{"Nombre", "Apellido", "Cédula", "Correo"}); // crea combo con opciones de filtro de busqueda

        // Placeholder de fecha: txtFecNac 
        txtFecNac.setForeground(COLOR_PH); // le pone color gris inicial al texto fantasma
        txtFecNac.setToolTipText("Formato: yyyy/MM/dd  Ej: 1990/04/24"); // le pone un mensajito flotante de ayuda
        txtFecNac.addFocusListener(new FocusAdapter() { // le añade un evento para cuando la caja recibe clics (foco)
            @Override public void focusGained(FocusEvent e) { // metodo que se dispara al hacer clic en la caja
                if (txtFecNac.getText().equals(PH_FECHA)) { // si el texto es igual al fantasma
                    txtFecNac.setText(""); // vacia la caja para que el usuario escriba
                    txtFecNac.setForeground(COLOR_TEXTO); // le devuelve el color negro normal
                }
            }
            @Override public void focusLost(FocusEvent e) { // metodo que se dispara al salir de la caja
                if (txtFecNac.getText().trim().isEmpty()) { // si la caja se quedo vacia
                    txtFecNac.setText(PH_FECHA); // le vuelve a poner el texto fantasma
                    txtFecNac.setForeground(COLOR_PH); // le vuelve a poner color gris
                }
            }
        });

        //  Placeholder de fecha: txtFecIngreso 
        txtFecIngreso.setForeground(COLOR_PH); // pone color gris inicial al texto fantasma de fecha de ingreso
        txtFecIngreso.setToolTipText("Formato: Ej: 1990/04/24"); // le añade el globo de ayuda
        txtFecIngreso.addFocusListener(new FocusAdapter() { // escucha eventos de foco para la fecha de ingreso
            @Override public void focusGained(FocusEvent e) { // al entrar a la caja
                if (txtFecIngreso.getText().equals(PH_FECHA)) { // si tiene el texto fantasma
                    txtFecIngreso.setText(""); // lo borra
                    txtFecIngreso.setForeground(COLOR_TEXTO); // le pone color de escritura normal
                }
            }
            @Override public void focusLost(FocusEvent e) { // al salir de la caja
                if (txtFecIngreso.getText().trim().isEmpty()) { // si el usuario no escribio nada
                    txtFecIngreso.setText(PH_FECHA); // repone el texto fantasma
                    txtFecIngreso.setForeground(COLOR_PH); // le devuelve el color gris
                }
            }
        });

        //  Ventana 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // configura que al cerrar esta ventana no se cierre todo el programa
        setTitle(" Módulo de Empleados"); // le pone el titulo a la ventana
        setPreferredSize(new Dimension(1200, 680)); // define las dimensiones por defecto de la ventana
        setLayout(new BorderLayout(5, 5)); // configura el esquema de diseño usando bordes con un margen de 5px

        //  Panel título 
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT)); // crea un panel superior con alineacion a la izquierda
        panelTitulo.setBackground(new Color(30, 60, 114)); // le asigna un color de fondo azul oscuro
        JLabel lblTit = new JLabel("   Gestión de Empleados"); // crea la etiqueta principal del titulo
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 18)); // le pone una fuente grande y en negrita
        lblTit.setForeground(Color.WHITE); // le cambia el color a las letras a blanco
        panelTitulo.add(lblTit); // añade el titulo al panel superior
        add(panelTitulo, BorderLayout.NORTH); // ubica este panel en la parte norte (arriba) de la ventana

        //  Panel formulario 
        panelFormulario.setBackground(new Color(245, 247, 250)); // le da un color gris muy clarito de fondo al formulario
        panelFormulario.setBorder(BorderFactory.createTitledBorder( // le crea un borde con titulo incorporado
            BorderFactory.createLineBorder(new Color(200, 200, 200)), // el borde es de linea gris
            "Datos del Empleado", 0, 0, // el titulo es "Datos del empleado"
            new Font("Segoe UI", Font.BOLD, 12), new Color(30, 60, 114))); // le da fuente al titulo y color azul
        panelFormulario.setPreferredSize(new Dimension(300, 850)); // le asigna dimensiones base al formulario
        panelFormulario.setLayout(new GridBagLayout()); // usa layout de cuadricula flexible para los elementos

        GridBagConstraints gbc = new GridBagConstraints(); // crea un objeto que controla la posicion en la cuadricula
        gbc.fill    = GridBagConstraints.HORIZONTAL; // dice que los campos deben expandirse a lo ancho
        gbc.insets  = new Insets(3, 8, 3, 8); // le asigna margenes internos (arriba, izquierda, abajo, derecha)
        gbc.weightx = 1.0; // dice que debe ocupar todo el ancho posible en su celda

        Object[][] campos = { // crea una matriz asociando cada etiqueta con su respectiva caja
            {lblIdEmpleado,   txtIdEmpleado}, // fila id
            {lblCedula,       txtCedula}, // fila cedula
            {lblNombre,       txtNombre}, // fila nombre
            {lblApellido,     txtApellido}, // fila apellido
            {lblFecNac,       txtFecNac}, // fila nacimiento
            {lblGenero,       cmbGenero}, // fila genero
            {lblTelefono,     txtTelefono}, // fila telefono
            {lblCorreo,       txtCorreo}, // fila correo
            {lblDireccion,    txtDireccion}, // fila direccion
            {lblCargo,        txtCargo}, // fila cargo
            {lblDepartamento, txtDepartamento}, // fila depto
            {lblFecIngreso,   txtFecIngreso}, // fila ingreso
            {lblSalario,      txtSalario}, // fila salario
            {lblEstado,       cmbEstado} // fila estado
        };

        for (int i = 0; i < campos.length; i++) { // bucle para agregar dinamicamente todas las etiquetas y cajas
            gbc.gridx = 0; // le dice que se posicione en la primera columna
            gbc.gridy = i * 2; // le asigna la fila correspondiente a la etiqueta (multiplica por dos para dejar espacio abajo)
            JLabel lbl = (JLabel) campos[i][0]; // extrae la etiqueta de la matriz
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11)); // le asigna fuente a la etiqueta
            panelFormulario.add(lbl, gbc); // añade la etiqueta al panel

            gbc.gridy = i * 2 + 1; // baja a la siguiente fila de la cuadricula
            JComponent comp = (JComponent) campos[i][1]; // extrae la caja o lista de la matriz
            comp.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // le da fuente a la caja
            if (comp instanceof JTextField) // si el componente es una caja de texto comun...
                ((JTextField) comp).setPreferredSize(new Dimension(280, 28)); // le fija un tamaño por defecto
            panelFormulario.add(comp, gbc); // añade la caja debajo de la etiqueta
        }

        // ID no editable
        txtIdEmpleado.setEditable(false); // bloquea la caja del ID para que el usuario no la pueda escribir a mano
        txtIdEmpleado.setBackground(new Color(230, 230, 230)); // la pone en color grisecito para que se entienda que esta inhabilitada

        //  Panel botones 
        panelBotones.setBackground(new Color(245, 247, 250)); // le da fondo claro al area de botones
        panelBotones.setLayout(new GridLayout(3, 2, 6, 6)); // organiza los botones en una matriz de 3 filas x 2 columnas con espacio de 6px
        panelBotones.setBorder(BorderFactory.createCompoundBorder( // crea un borde compuesto para este panel
            BorderFactory.createTitledBorder( // el primer borde es uno titulado
                BorderFactory.createLineBorder(new Color(200, 200, 200)), // con linea gris
                "Acciones", 0, 0, // de titulo "Acciones"
                new Font("Segoe UI", Font.BOLD, 12), new Color(30, 60, 114)), // con fuente del titulo
            BorderFactory.createEmptyBorder(8, 8, 8, 8))); // y el segundo borde interno es un espacio vacio de 8px por lado

        estilizarBtn(btnNuevo,     new Color(40, 120, 40),  "Nuevo registro"); // llama a metodo ayudante para pintar boton nuevo de verde
        estilizarBtn(btnGuardar,   new Color(30, 60, 114),  "Guardar nuevo"); // pinta boton guardar de azul
        estilizarBtn(btnModificar, new Color(180, 100, 0),  "Actualizar seleccionado"); // pinta boton modificar de naranja
        estilizarBtn(btnDarBaja,   new Color(140, 0, 0),    "Baja lógica (inactivar)"); // pinta boton dar baja de rojo oscuro
        estilizarBtn(btnEliminar,  new Color(80, 0, 0),     "Eliminar permanentemente"); // pinta boton eliminar de rojo muy oscuro
        estilizarBtn(btnLimpiar,   new Color(80, 80, 80),   "Limpiar formulario"); // pinta boton limpiar de gris

        panelBotones.add(btnNuevo); // agrega el boton nuevo al panel de botones
        panelBotones.add(btnGuardar); // agrega el boton guardar
        panelBotones.add(btnModificar); // agrega el boton modificar
        panelBotones.add(btnDarBaja); // agrega el boton dar de baja
        panelBotones.add(btnEliminar); // agrega el boton eliminar
        panelBotones.add(btnLimpiar); // agrega el boton limpiar

        //  Panel izquierdo = formulario + botones 
        JPanel panelIzq = new JPanel(new BorderLayout(0, 5)); // crea un panel mayor para agrupar formulario arriba y botones abajo
        panelIzq.setBackground(new Color(245, 247, 250)); // le da el mismo fondo claro

        JScrollPane scrollForm = new JScrollPane(panelFormulario, // mete el formulario dentro de una barra deslizante
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, // solo muestra scroll vertical si hace falta (pantallas chicas)
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // jamas muestra scroll horizontal
        scrollForm.setBorder(null); // le quita el borde feo por defecto al scroll
        scrollForm.getVerticalScrollBar().setUnitIncrement(16); // hace que el scroll baje mas rapido con la rueda del raton

        panelIzq.add(scrollForm, BorderLayout.CENTER); // pone el formulario scrolleable en el centro
        panelIzq.add(panelBotones, BorderLayout.SOUTH); // pone los botones pegados abajo
        panelIzq.setMinimumSize(new Dimension(330, 0)); // forza tamaño minimo a 330px de ancho
        panelIzq.setMaximumSize(new Dimension(330, Integer.MAX_VALUE)); // no deja que el panel se estire mas de 330px
        panelIzq.setPreferredSize(new Dimension(330, 0)); // tamaño inicial a 330px
        add(panelIzq, BorderLayout.WEST); // ancla todo este bloque en el lado izquierdo de la ventana

        //  Panel tabla (derecho) 
        panelTabla.setLayout(new BorderLayout(5, 5)); // define el layout de la zona derecha
        panelTabla.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // le da un respiro de 5px a los bordes

        // Barra de búsqueda con criterio seleccionable
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5)); // panel superior para cajita de busqueda
        panelBusqueda.setBackground(Color.WHITE); // le da fondo blanco

        cmbCriterioBusqueda.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // fuente para combo de criterio
        cmbCriterioBusqueda.setPreferredSize(new Dimension(110, 28)); // tamaño para combo de criterio
        cmbCriterioBusqueda.setToolTipText("Selecciona el campo por el que deseas buscar"); // ayuda flotante

        txtBuscar.setPreferredSize(new Dimension(220, 28)); // tamaño para la cajita de escribir busqueda
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // fuente para cajita
        txtBuscar.setToolTipText("Escribe el valor a buscar"); // ayuda flotante

        btnBuscar.setBackground(new Color(30, 60, 114)); // azul oscuro para boton de buscar
        btnBuscar.setForeground(Color.BLACK); // letra negra
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12)); // negrita para boton buscar
        btnBuscar.setFocusPainted(false); // quita el recuadrito punteado feo al hacerle clic

        btnMostrarTodos.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // fuente para boton de mostrar todos

        panelBusqueda.add(new JLabel("Buscar por:")); // etiqueta de indicacion
        panelBusqueda.add(cmbCriterioBusqueda); // agrega la lista de criterios al buscador
        panelBusqueda.add(txtBuscar); // agrega la caja
        panelBusqueda.add(btnBuscar); // agrega el boton buscar
        panelBusqueda.add(btnMostrarTodos); // agrega el boton ver todos
        panelBusqueda.add(chkSoloActivos); // agrega el chulito de solo activos
        panelTabla.add(panelBusqueda, BorderLayout.NORTH); // ubica este panel arriba del area de tabla

        // Tabla
        tblEmpleados.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // fuente general de las filas
        tblEmpleados.setRowHeight(24); // hace las filas mas altas (24px) para mejor lectura
        tblEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // fuerza a que el usuario solo pueda elegir una fila a la vez
        tblEmpleados.setAutoCreateRowSorter(true); // permite clickear el titulo de la columna para ordenar alfabeticamente
        tblEmpleados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12)); // fuente en negrita para las cabeceras
        tblEmpleados.getTableHeader().setBackground(new Color(30, 60, 114)); // color azul de fondo en las cabeceras
        tblEmpleados.getTableHeader().setForeground(Color.BLACK); // color de letra negro en cabeceras
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // recuadro gris sutil alrededor de la tabla
        panelTabla.add(scrollTabla, BorderLayout.CENTER); // ubica la tabla en el centro del area derecha

        // Pie
        JLabel lblPie = new JLabel("  ℹ  Haz clic en una fila para seleccionar un empleado"); // crea mensajito informativo inferior
        lblPie.setFont(new Font("Segoe UI", Font.ITALIC, 11)); // le pone letra cursiva y pequeña
        lblPie.setForeground(new Color(100, 100, 100)); // le pone color gris
        panelTabla.add(lblPie, BorderLayout.SOUTH); // lo ubica abajito de la tabla

        add(panelTabla, BorderLayout.CENTER); // pega todo el panel derecho (con la tabla y busquedas) en el centro de la ventana

        pack(); // le dice a java que recalcule internamente el tamaño para que todo quepa
        setLocationRelativeTo(null); // hace que la ventana aparezca justo en el medio de la pantalla al abrir

        //  Eventos 
        btnNuevo.addActionListener(e -> limpiarFormulario()); // asocia el clic del boton nuevo a vaciar las cajas
        btnGuardar.addActionListener(e -> guardarEmpleado()); // asocia el clic del boton guardar al metodo de crear en db
        btnModificar.addActionListener(e -> modificarEmpleado()); // asocia boton modificar a su logica respectiva
        btnDarBaja.addActionListener(e -> darDeBaja()); // asocia boton baja a metodo dar de baja
        btnEliminar.addActionListener(e -> eliminarEmpleado()); // asocia boton eliminar a borrar de la base de datos
        btnLimpiar.addActionListener(e -> limpiarFormulario()); // boton limpiar resetea cajas

        btnBuscar.addActionListener(e -> buscarEmpleado()); // asocia boton buscar al metodo que hace el select filtrado
        txtBuscar.addActionListener(e -> buscarEmpleado()); // permite que al presionar 'Enter' en la caja, tambien busque

        btnMostrarTodos.addActionListener(e -> { // evento del boton mostrar todos
            txtBuscar.setText(""); // borra lo que se estaba buscando
            if (chkSoloActivos.isSelected()) modelo.listarActivos(tblEmpleados); // si la casilla de activos esta marcada, lista activos
            else modelo.listar(tblEmpleados); // sino, lista absolutamente todo
        });

        chkSoloActivos.addActionListener(e -> { // evento de clickear el chulito de solo activos
            if (chkSoloActivos.isSelected()) modelo.listarActivos(tblEmpleados); // cambia la tabla para reflejar solo activos
            else modelo.listar(tblEmpleados); // o reflejar todos, segun corresponda
        });

        tblEmpleados.getSelectionModel().addListSelectionListener(e -> { // escucha cuando el usuario hace clic sobre las filas de la tabla
            if (!e.getValueIsAdjusting()) cargarDatosDeTabla(); // si el evento de click ya termino, llama al metodo para mandar esos datos al formulario
        });
    }

    //  Convertir fecha de pantalla (dd/MM/yyyy) a BD (yyyy-MM-dd) 
    private String fechaDisplayABD(String fecha) { // metodo para transformar la fecha que mete el usuario al formato que la bd entiende
        String f = fecha.trim(); // borra espacios en blanco basura al principio y final
        if (f.isEmpty() || f.equals(PH_FECHA)) return ""; // si esta vacio o tiene el texto fantasma, devuelve cadena vacia
        try {
            return LocalDate.parse(f, FMT_DISPLAY).format(FMT_BD); // toma el string del usuario, lo convierte a fecha y le cambia el formato al estandar
        } catch (DateTimeParseException ex) { // si el usuario metio una fecha absurda (ej: 34/15/2000) entra aca
            JOptionPane.showMessageDialog(this, // lanza ventanita emergente de error
                "Fecha inválida: \"" + f + "\"\nUse el formato DD/MM/YYYY   Ej: 25/03/1990", // explica el error
                "Error de fecha", JOptionPane.WARNING_MESSAGE); // titulo y advertencia
            return null; // null = error, detener operación
        }
    }

    //  Convertir fecha de BD (yyyy-MM-dd) a pantalla (dd/MM/yyyy) 
    private String fechaBDADisplay(String fecha) { // metodo inverso: toma la fecha de la bd y la pone bonita para el usuario
        if (fecha == null || fecha.trim().isEmpty()) return PH_FECHA; // si no hay fecha, devuelve el texto fantasma
        try {
            return LocalDate.parse(fecha.trim(), FMT_BD).format(FMT_DISPLAY); // agarra la de la bd, y la devuelve en yyyy/MM/dd
        } catch (DateTimeParseException ex) { // si falla la lectura...
            return fecha; // si viene en otro formato loco por algun bug antiguo, la muestra tal cual para evitar colapso
        }
    }

    //  Guardar nuevo empleado 
    private void guardarEmpleado() { // logica enlazada al boton guardar
        if (!validarCamposObligatorios()) return; // revisa si los obligatorios estan llenos, sino aborta

        double salario = 0; // variable para guardar la platita
        try {
            salario = Double.parseDouble(txtSalario.getText().trim()); // intenta transformar el texto escrito en numero con decimales
        } catch (NumberFormatException ex) { // si el tipo metio letras (ej: "mil dolares")
            JOptionPane.showMessageDialog(this, // salta error visual
                "El salario debe ser un número válido.", "Error", JOptionPane.WARNING_MESSAGE); // avisa la advertencia
            return; // aborta ejecucion
        }

        String fechaNac     = fechaDisplayABD(txtFecNac.getText()); // convierte la fecha de nacimiento usando el metodo de arriba
        if (fechaNac == null) return; // si la conversion falló (null), se corta aca

        String fechaIngreso = fechaDisplayABD(txtFecIngreso.getText()); // lo mismo con fecha de ingreso
        if (fechaIngreso == null) return; // si falla se sale

        boolean ok = modelo.insertar( // manda llamar al metodo de insertar del modelo pasando todo por parametros
            txtCedula.getText().trim(), // saca el texto de la cedula
            txtNombre.getText().trim(), // saca nombre
            txtApellido.getText().trim(), // saca apellido
            fechaNac, // manda la fecha formateada
            cmbGenero.getSelectedItem().toString(), // extrae la opcion del genero
            txtTelefono.getText().trim(), // manda tel
            txtCorreo.getText().trim(), // manda email
            txtDireccion.getText().trim(), // manda direcc
            txtCargo.getText().trim(), // manda cargo
            txtDepartamento.getText().trim(), // manda depto
            fechaIngreso, // manda la fecha convertida
            salario); // manda el double que transformamos antes

        if (ok) { // si la insercion en la base de datos fue positiva...
            JOptionPane.showMessageDialog(this, // saca mensaje de exito
                "✅ Empleado registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE); // todo chido
            limpiarFormulario(); // vacia las cajas de texto solitas
            modelo.listarActivos(tblEmpleados); // refresca la tabla para que el usuario recien ingresado aparezca ahi
        }
    }

    //  Modificar empleado existente 
    private void modificarEmpleado() { // evento cuando apretan el boton modificar
        if (idSeleccionado == -1) { // revisa si el usuario no tiene ninguna fila cliqueada de la tabla
            JOptionPane.showMessageDialog(this, // le lanza alerta
                "Selecciona un empleado de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE); // diciendo que pique alguno primero
            return; // aborta la mision
        }
        if (!validarCamposObligatorios()) return; // revalida cedula nombre etc como en el de guardar

        double salario = 0; // mismo proceso de validacion de salario que guardar
        try {
            salario = Double.parseDouble(txtSalario.getText().trim()); // vuelve la caja un double
        } catch (NumberFormatException ex) { // si hay letras revienta
            JOptionPane.showMessageDialog(this,
                "El salario debe ser un número válido.", "Error", JOptionPane.WARNING_MESSAGE);
            return; // se sale
        }

        String fechaNac     = fechaDisplayABD(txtFecNac.getText()); // formatea fecnac
        if (fechaNac == null) return; // cancela si error

        String fechaIngreso = fechaDisplayABD(txtFecIngreso.getText()); // formatea fcing
        if (fechaIngreso == null) return; // cancela si error

        int conf = JOptionPane.showConfirmDialog(this, // salta un mensajito preguntando "estas seguro man?"
            "¿Actualizar datos del empleado?", "Confirmar", JOptionPane.YES_NO_OPTION); // muestra botones de SI / NO
        if (conf != JOptionPane.YES_OPTION) return; // si pica que NO o lo cierra, aborta ejecucion

        boolean ok = modelo.actualizar( // llama a actualizar del modelo de bd
            idSeleccionado, // ojo que aca le manda el ID por delante para el WHERE del SQL
            txtCedula.getText().trim(), // el resto de datos todos iguales...
            txtNombre.getText().trim(),
            txtApellido.getText().trim(),
            fechaNac,
            cmbGenero.getSelectedItem().toString(),
            txtTelefono.getText().trim(),
            txtCorreo.getText().trim(),
            txtDireccion.getText().trim(),
            txtCargo.getText().trim(),
            txtDepartamento.getText().trim(),
            fechaIngreso,
            salario,
            cmbEstado.getSelectedItem().toString()); // y tambien extrae y actualiza si sigue activo o inactivo

        if (ok) { // si la query resulto bien
            JOptionPane.showMessageDialog(this, // mensaje de todo bonito
                "✅ Empleado actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            modelo.listar(tblEmpleados); // recarga la tabla de empleados para ver reflejados los cambios al instante
        }
    }

    //  Dar de baja (baja lógica) 
    private void darDeBaja() { // metodo del boton "Dar baja"
        if (idSeleccionado == -1) { // valida otra vez que tengan seleccionado un usuario
            JOptionPane.showMessageDialog(this,
                "Selecciona un empleado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // si no eligio sale
        }
        int conf = JOptionPane.showConfirmDialog(this, // manda el mensajito intimidante
            "¿Dar de baja (inactivar) a este empleado?\nSus datos NO serán eliminados.", // aclara que es logico, no fisico
            "Confirmar Baja", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE); // pregunta de si/no
        if (conf != JOptionPane.YES_OPTION) return; // cancela si no le pico SI

        if (modelo.darDeBaja(idSeleccionado)) { // si se logra inactivar en la BD...
            JOptionPane.showMessageDialog(this, // le sale mensaje q lo lograron
                "Empleado dado de baja.", "Baja Registrada", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario(); // vacia las cajas pq el tipo ya esta inactivo
            modelo.listar(tblEmpleados); // recarga la tabla
        }
    }

    //  Eliminar permanentemente 
    private void eliminarEmpleado() { // evento de boton eliminar
        if (idSeleccionado == -1) { // valida seleccion previa en tabla
            JOptionPane.showMessageDialog(this,
                "Selecciona un empleado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // aborta si hay -1
        }
        int conf = JOptionPane.showConfirmDialog(this, // manda otro mensajito mas severo aun
            "⚠️ ¿Eliminar PERMANENTEMENTE este empleado?\nEsta acción no se puede deshacer.", // advierte peligro inminente de la accion
            "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE); // muestra la equis roja
        if (conf != JOptionPane.YES_OPTION) return; // si la persona se echa para atras, salimos del metodo

        if (modelo.eliminar(idSeleccionado)) { // llama al delete fisico del modelo
            JOptionPane.showMessageDialog(this, // si jalo bien...
                "Empleado eliminado.", "Eliminado", JOptionPane.INFORMATION_MESSAGE); // pum, borrado. mensaje ok
            limpiarFormulario(); // limpia las caquitas de texto 
            modelo.listar(tblEmpleados); // repinta la tabla (ya deberia verse con un vato menos)
        }
    }

    //  Buscar por criterio seleccionado 
    private void buscarEmpleado() { // funcion unida al boton buscar o al apretar enter
        String valor = txtBuscar.getText().trim(); // extrae lo que teclearon y quita espacios raros
        if (valor.isEmpty()) { // si apretaron buscar sin escribir nada de nada
            if (chkSoloActivos.isSelected()) modelo.listarActivos(tblEmpleados); // vuelve a mostrar activos
            else modelo.listar(tblEmpleados); // o vuelve a mostrar todos (basicamente reset)
            return; // aborta la busqueda
        }
        String campo = cmbCriterioBusqueda.getSelectedItem().toString(); // saca que selecciono de la listita (nombre? cedula? etc)
        modelo.buscarPorCampo(tblEmpleados, valor, campo); // manda al modelo la peticion para que ejecute el query like en base al campo elegido
    }

    //  Cargar datos de la fila seleccionada 
    private void cargarDatosDeTabla() { // metodo que se ejecuta cada vez que el usuario hace un clic a una fila de la tabla
        int fila = tblEmpleados.getSelectedRow(); // averigua que numero de renglon clickeamos (empezando de cero)
        if (fila == -1) return; // si hubo un des-seleccionar o un bug, se cancela

        idSeleccionado = (int) tblEmpleados.getModel().getValueAt(fila, 0); // la columna 0 es el ID siempre, lo agarramos para el WHERE de las otras sentencias
        String[] datos = modelo.obtenerPorId(idSeleccionado); // pide a base de datos traigame la info completa de este compa
        if (datos == null) return; // si la db trono y manda nulo ps nos salimos

        txtIdEmpleado.setText(datos[0]); // pone el id en la cajita inhabilitada
        txtCedula.setText(datos[1]); // rellena cedula
        txtNombre.setText(datos[2]); // rellena nombre
        txtApellido.setText(datos[3]); // rellena apellido

        // Fechas: convertir de yyyy-MM-dd a dd/MM/yyyy para mostrar
        String fnac = fechaBDADisplay(datos[4]); // formatea la fecha del indice 4
        txtFecNac.setText(fnac); // mete la fecha ya linda al textfield
        txtFecNac.setForeground(fnac.equals(PH_FECHA) ? COLOR_PH : COLOR_TEXTO); // si vino vacio, vuelve gris el placeholder, si tiene fecha real, negro

        cmbGenero.setSelectedItem(datos[5] == null ? "Masculino" : datos[5]); // setea combobox genero, si esta null le mete masculino por descarte
        txtTelefono.setText(datos[6]  == null ? "" : datos[6]); // rellena telefono protegiendo contra nulos 
        txtCorreo.setText(datos[7]    == null ? "" : datos[7]); // rellena correo protegiendo
        txtDireccion.setText(datos[8] == null ? "" : datos[8]); // rellena direccion
        txtCargo.setText(datos[9]     == null ? "" : datos[9]); // rellena cargo
        txtDepartamento.setText(datos[10] == null ? "" : datos[10]); // rellena depto

        String fing = fechaBDADisplay(datos[11]); // toma fecha ingreso y le mete formatico lindo
        txtFecIngreso.setText(fing); // la chanta al field
        txtFecIngreso.setForeground(fing.equals(PH_FECHA) ? COLOR_PH : COLOR_TEXTO); // colorea a conveniencia igual que fec nac

        txtSalario.setText(datos[12] == null ? "0.00" : datos[12]); // jala el salario protegiendolo por cero de defecto
        cmbEstado.setSelectedItem(datos[13] == null ? "Activo" : datos[13]); // mete activo o inactivo en el cmbox
    }

    //  Limpiar formulario 
    private void limpiarFormulario() { // la escobita, vacia todo visualmente y resetea id
        idSeleccionado = -1; // le quita seleccion del motor interno
        txtIdEmpleado.setText(""); // blanco
        txtCedula.setText(""); // blanco
        txtNombre.setText(""); // blanco
        txtApellido.setText(""); // blanco

        txtFecNac.setText(PH_FECHA); // devuelve a formato fantasma
        txtFecNac.setForeground(COLOR_PH); // le da color gris al formato fantasma

        cmbGenero.setSelectedIndex(0); // devuelve index del combo genero a la opcion nro 1 (masculino)
        txtTelefono.setText(""); // borra telefono
        txtCorreo.setText(""); // borra correo
        txtDireccion.setText(""); // borra direccion
        txtCargo.setText(""); // borra cargo
        txtDepartamento.setText(""); // borra departamento

        txtFecIngreso.setText(PH_FECHA); // lo devuelve a yyyy/MM/dd placeholder
        txtFecIngreso.setForeground(COLOR_PH); // gris clarito

        txtSalario.setText("0.00"); // lo vuelve a inicializar en 0.00 como antes
        cmbEstado.setSelectedIndex(0); // pone "activo" por defecto

        tblEmpleados.clearSelection(); // despinta cualquier fila que haya quedado sombreada en la tabla derecha
        txtCedula.requestFocus(); // mueve el cursor tildando sobre "Cedula" para que el usuario teclee rapidin
    }

    //  Validar campos obligatorios 
    private boolean validarCamposObligatorios() { // chequeador de validacion pre-guardado o update
        if (txtCedula.getText().trim().isEmpty()  || // revisa que cedula este llena y...
            txtNombre.getText().trim().isEmpty()   || // nombre... y ...
            txtApellido.getText().trim().isEmpty()) { // apellido
            JOptionPane.showMessageDialog(this, // si alguno de esos tres principales falta...
                "Cédula, Nombre y Apellido son obligatorios.", // el mensaje de error de que no se puede ser tan menso
                "Campos requeridos", JOptionPane.WARNING_MESSAGE); // titulo y advertencia popup
            return false; // devuelve falso que significa "aborte operacion, falto llenar algo"
        }
        return true; // en cambio si esos tres estam, devuelva verdadero y permita el paso al registro bd
    }

    //  Estilizar botones 
    private void estilizarBtn(JButton btn, Color color, String tooltip) { // una macro para pintar botones facil en initcomponents
        btn.setBackground(color); // recibe el color y se lo clava de fondo al boton
        btn.setForeground(Color.BLACK); // fuerza siempre letra negrita (podes pasarlo a blanco si los fondos son muy oscuros compa)
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11)); // le asgina la fuente segoe semichica a la vez
        btn.setFocusPainted(false); // desactiva contorno punteado
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // que el ratoncito se vuelva una manito al pararse encima
        btn.setToolTipText(tooltip); // le mete un globo de texto tipo ayuda por encima
        btn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10)); // le da aire entre el texto del boton y el marco superior/inferior
    }
}