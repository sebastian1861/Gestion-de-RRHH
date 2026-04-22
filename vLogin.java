package Vista;

import Controlador.cInicializarBD;
import Modelo.mUsuarios;
import javax.swing.*;
import java.awt.*;
//@author Hector Mojica,Cesar Rojas, Sebastian Medina

public class vLogin extends javax.swing.JFrame { // Declara la clase vLogin que hereda de JFrame (es decir, es una ventana)

    //  Variables de formulario 
    // Se declaran todos los componentes visuales que tendrá la ventana
    private javax.swing.JLabel lblTitulo; // Etiqueta para el título principal
    private javax.swing.JLabel lblSubtitulo; // Etiqueta para el subtítulo
    private javax.swing.JLabel lblUsuario; // Etiqueta que dirá "Usuario"
    private javax.swing.JLabel lblPassword; // Etiqueta que dirá "Contraseña"
    private javax.swing.JTextField txtUsuario; // Cajita de texto normal para que el usuario escriba su nombre
    private javax.swing.JPasswordField txtPassword; // Cajita de texto especial para contraseñas (oculta los caracteres con asteriscos/puntos)
    private javax.swing.JButton btnIngresar; // Botón para iniciar sesión
    private javax.swing.JButton btnCancelar; // Botón para cerrar la aplicación
    private javax.swing.JLabel lblIcono; // Etiqueta que se usará para mostrar un emoji decorativo
    private javax.swing.JPanel panelMain; // Panel de fondo que cubrirá toda la ventana
    private javax.swing.JPanel panelForm; // Panel blanco donde irán las cajas de texto y botones

    // Constructor
    public vLogin() { // Este método se ejecuta automáticamente cuando se crea la ventana
        initComponents(); // Llama al método que dibuja e inicializa todos los componentes visuales
        // Inicializar BD al abrir la app
        cInicializarBD.crearTablas(); // Llama al controlador para verificar/crear la base de datos y sus tablas al arrancar el programa
    }

    // Inicialización de componentes 
    private void initComponents() { // Método donde se configuran tamaños, colores y posiciones
        panelMain    = new javax.swing.JPanel(); // Crea el panel principal
        panelForm    = new javax.swing.JPanel(); // Crea el panel del formulario
        lblIcono     = new javax.swing.JLabel(); // Crea la etiqueta del icono
        lblTitulo    = new javax.swing.JLabel(); // Crea la etiqueta del título
        lblSubtitulo = new javax.swing.JLabel(); // Crea la etiqueta del subtítulo
        lblUsuario   = new javax.swing.JLabel(); // Crea la etiqueta "Usuario"
        lblPassword  = new javax.swing.JLabel(); // Crea la etiqueta "Contraseña"
        txtUsuario   = new javax.swing.JTextField(); // Crea la caja para el usuario
        txtPassword  = new javax.swing.JPasswordField(); // Crea la caja para la contraseña
        btnIngresar  = new javax.swing.JButton(); // Crea el botón ingresar
        btnCancelar  = new javax.swing.JButton(); // Crea el botón cancelar

        //  Ventana principal 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE); // Si se cierra esta ventana, se apaga todo el programa
        setTitle("Sistema RRHH - Inicio de Sesion"); // Pone el texto en la barra superior de la ventana
        setResizable(false); // Bloquea la ventana para que el usuario no pueda cambiarle el tamaño
        setPreferredSize(new java.awt.Dimension(420, 480)); // Define el tamaño inicial de la ventana (ancho x alto)

        // Panel principal con gradiente 
        panelMain.setBackground(new java.awt.Color(30, 60, 114)); // Le da un color azul oscuro al panel de fondo
        panelMain.setLayout(new java.awt.BorderLayout()); // Usa un diseño de bordes (Norte, Sur, Este, Oeste, Centro)

        //  Encabezado 
        JPanel panelHeader = new JPanel(); // Crea un sub-panel para la parte de arriba
        panelHeader.setOpaque(false); // Lo hace transparente para que se vea el azul oscuro de panelMain
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS)); // Apila los elementos de arriba hacia abajo (eje Y)
        panelHeader.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20)); // Le da márgenes internos (arriba, izq, abajo, der)

        lblIcono.setText("👥"); // Le pone un emoji de personas como texto
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48)); // Usa una fuente que soporta emojis a tamaño 48
        lblIcono.setForeground(Color.WHITE); // Pone el color en blanco
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra el icono horizontalmente

        lblTitulo.setText("Sistema Recursos Humanos"); // Texto del título
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Fuente grande y en negrita
        lblTitulo.setForeground(Color.WHITE); // Letra blanca
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Lo centra

        lblSubtitulo.setText("Gestión de Recursos Humanos"); // Texto del subtítulo
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Fuente más pequeña
        lblSubtitulo.setForeground(new Color(180, 200, 255)); // Letra color azul clarito
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Lo centra

        panelHeader.add(lblIcono); // Mete el icono al encabezado
        panelHeader.add(Box.createVerticalStrut(8)); // Añade un espacio vacío de 8 pixeles hacia abajo
        panelHeader.add(lblTitulo); // Mete el título
        panelHeader.add(Box.createVerticalStrut(4)); // Añade otro espacio vacío de 4 pixeles
        panelHeader.add(lblSubtitulo); // Mete el subtítulo

        //  Panel del formulario 
        panelForm.setBackground(Color.WHITE); // Pinta el panel del formulario de blanco
        panelForm.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Le da márgenes internos grandes
        panelForm.setLayout(new GridBagLayout()); // Usa un diseño de cuadrícula flexible (excelente para formularios)
        GridBagConstraints gbc = new GridBagConstraints(); // Objeto para controlar en qué celda de la cuadrícula va cada cosa
        gbc.insets = new Insets(8, 0, 8, 0); // Define una separación de 8 pixeles arriba y abajo de cada componente
        gbc.fill = GridBagConstraints.HORIZONTAL; // Hace que los componentes se estiren a lo ancho
        gbc.weightx = 1.0; // Le dice que tome todo el ancho disponible

        // Usuario
        lblUsuario.setText("Usuario"); // Pone texto a la etiqueta
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Negrita
        lblUsuario.setForeground(new Color(60, 60, 60)); // Gris oscuro
        gbc.gridx = 0; gbc.gridy = 0; // Columna 0, Fila 0
        panelForm.add(lblUsuario, gbc); // Agrega la etiqueta al panel

        txtUsuario.setPreferredSize(new Dimension(280, 36)); // Tamaño fijo para la caja de texto
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Fuente normal
        txtUsuario.setBorder(BorderFactory.createCompoundBorder( // Borde doble:
            BorderFactory.createLineBorder(new Color(200, 200, 200)), // Una línea gris exterior
            BorderFactory.createEmptyBorder(4, 10, 4, 10))); // Y un espacio interior para que el texto no pegue al borde
        gbc.gridy = 1; // Baja a la Fila 1
        panelForm.add(txtUsuario, gbc); // Agrega la caja

        // contraseña
        lblPassword.setText("Contraseña"); // Texto etiqueta
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Negrita
        lblPassword.setForeground(new Color(60, 60, 60)); // Gris oscuro
        gbc.gridy = 2; // Fila 2
        panelForm.add(lblPassword, gbc); // Agrega la etiqueta

        txtPassword.setPreferredSize(new Dimension(280, 36)); // Mismo tamaño que la caja de usuario
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Fuente
        txtPassword.setBorder(BorderFactory.createCompoundBorder( // Mismo borde doble que la otra caja
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        gbc.gridy = 3; // Fila 3
        panelForm.add(txtPassword, gbc); // Agrega la caja de contraseña

        // Botón Ingresar
        btnIngresar.setText("INGRESAR"); // Texto del botón
        btnIngresar.setBackground(new Color(30, 60, 114)); // Fondo azul oscuro
        btnIngresar.setForeground(Color.BLACK); // Texto negro
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Negrita
        btnIngresar.setPreferredSize(new Dimension(280, 40)); // Altura de 40px para que se vea robusto
        btnIngresar.setBorder(BorderFactory.createEmptyBorder()); // Le quita bordes feos
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Pone la manito cuando pasas el mouse
        btnIngresar.setFocusPainted(false); // Quita el recuadrito punteado al hacerle clic
        gbc.gridy = 4; // Fila 4
        gbc.insets = new Insets(20, 0, 6, 0); // Le da más separación por arriba (20px) para alejarlo de las cajas
        panelForm.add(btnIngresar, gbc); // Agrega el botón

        // Botón Cancelar
        btnCancelar.setText("Cancelar"); // Texto del botón secundario
        btnCancelar.setBackground(Color.BLACK); // Debería ser de fondo transparente pero aquí dice negro
        btnCancelar.setForeground(new Color(120, 120, 120)); // Texto gris
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Texto normal
        btnCancelar.setBorder(BorderFactory.createEmptyBorder()); // Sin bordes
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de manito
        btnCancelar.setFocusPainted(false); // Sin cuadrito de foco
        gbc.gridy = 5; // Fila 5
        gbc.insets = new Insets(0, 0, 0, 0); // Quita las separaciones para que pegue un poco al botón anterior
        panelForm.add(btnCancelar, gbc); // Agrega el botón cancelar

        // Texto ayuda
        JLabel lblAyuda = new JLabel("Usuario: grupolenguaje / lenguaje2026"); // Mensaje de recordatorio
        lblAyuda.setFont(new Font("Segoe UI", Font.ITALIC, 10)); // Cursiva y chiquito
        lblAyuda.setForeground(new Color(160, 160, 160)); // Gris claro
        lblAyuda.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        gbc.gridy = 6; // Fila 6
        gbc.insets = new Insets(12, 0, 0, 0); // Lo separa un poco hacia abajo
        panelForm.add(lblAyuda, gbc); // Lo agrega al panel

        //  Ensamblar 
        panelMain.add(panelHeader, BorderLayout.NORTH); // Pega el encabezado azul en la parte superior del panel principal
        panelMain.add(panelForm, BorderLayout.CENTER); // Pega el formulario blanco en el centro (ocupando el resto del espacio)

        getContentPane().add(panelMain); // Mete todo ese super panel dentro de la ventana real
        pack(); // Le dice a Java que ajuste los tamaños visuales de todo para que encaje
        setLocationRelativeTo(null); // Centra la ventana en la pantalla del monitor

        //  Eventos 
        btnIngresar.addActionListener(evt -> btnIngresarActionPerformed(evt)); // Si hacen clic en Ingresar, llama al método de login
        btnCancelar.addActionListener(evt -> System.exit(0)); // Si hacen clic en Cancelar, cierra el programa (el 0 significa cierre sin error)
        txtPassword.addActionListener(evt -> btnIngresarActionPerformed(evt)); // Si aprietan "Enter" estando en la contraseña, intenta hacer login
        txtUsuario.addActionListener(evt -> txtPassword.requestFocus()); // Si aprietan "Enter" en el usuario, salta a la caja de contraseña
    }

    //  Acción de Login 
    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) { // Lógica que ocurre al intentar ingresar
        String usuario  = txtUsuario.getText().trim(); // Saca el texto del usuario y le corta los espacios en blanco sobrantes
        String password = new String(txtPassword.getPassword()).trim(); // Saca la contraseña (getPassword devuelve un arreglo de char, por eso se convierte a String)

        if (usuario.isEmpty() || password.isEmpty()) { // Revisa si dejaron alguna caja vacía
            JOptionPane.showMessageDialog(this, // Muestra ventanita de alerta
                "Por favor ingresa usuario y contraseña.", // Mensaje
                "Campos vacíos", JOptionPane.WARNING_MESSAGE); // Título y tipo de alerta (Amarilla)
            return; // Corta la ejecución aquí para que no siga procesando
        }

        mUsuarios modelo = new mUsuarios(); // Instancia la clase del modelo que se conecta a la Base de Datos
        String nombreUsuario = modelo.autenticar(usuario, password); // Envía credenciales. Si está bien devuelve el nombre, si está mal devuelve null

        if (nombreUsuario != null) { // Si sí trajo un nombre (las credenciales son correctas)
            JOptionPane.showMessageDialog(this, // Muestra ventanita de éxito
                "¡Bienvenido, " + nombreUsuario + "!", // Saluda
                "Acceso Correcto", JOptionPane.INFORMATION_MESSAGE); // Título y tipo (Azul)
            
            // Abrir menú principal
            vMenuPrincipal menu = new vMenuPrincipal(nombreUsuario); // Crea la ventana del menú principal del sistema
            menu.setVisible(true); // La hace visible en pantalla
            this.dispose(); // Cierra y destruye la ventana de Login actual (para no dejarla de fondo)
        } else { // Si devolvió null (las credenciales son incorrectas)
            JOptionPane.showMessageDialog(this, // Ventanita de error
                "Usuario o contraseña incorrectos.\nIntenta de nuevo.", // Mensaje de rechazo
                "Acceso Denegado", JOptionPane.ERROR_MESSAGE); // Título y tipo de icono (Rojo)
            txtPassword.setText(""); // Borra lo que estaba en la contraseña para que vuelvan a teclear
            txtPassword.requestFocus(); // Pone el cursor parpadeando directo en la caja de contraseña
        }
    }

    //  Main de la aplicación 
    public static void main(String[] args) { // Punto de arranque del programa
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Intenta que los botones y cajas se vean como el sistema operativo (Windows/Mac) y no como el Java viejo de los 90s
        } catch (Exception ex) { /* usar look por defecto */ } // Si falla, que use el diseño feíto por defecto, no importa

        java.awt.EventQueue.invokeLater(() -> new vLogin().setVisible(true)); // Arranca la ventana de manera segura para la memoria (crea el hilo visual)
    }
}
