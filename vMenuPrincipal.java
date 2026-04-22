package Vista;

import Controlador.cConexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//@author Hector Mojica,Cesar Rojas, Sebastian Medina
public class vMenuPrincipal extends javax.swing.JFrame { 

    private String usuarioActual; // Variable para guardar el nombre del usuario que acaba de iniciar sesión

    //  Componentes 
    // Declaración de los elementos visuales que tendrá nuestro menú
    private javax.swing.JLabel lblBienvenido; // Etiqueta para mostrar el mensaje de "Sesión: [Usuario]"
    private javax.swing.JLabel lblTitulo; // Etiqueta para el título de la ventana
    private javax.swing.JButton btnEmpleados; // Botón para abrir el módulo de empleados
    private javax.swing.JButton btnAsistencia; // Botón para abrir el módulo de asistencia
    private javax.swing.JButton btnNomina; // Botón para abrir el módulo de nómina
    private javax.swing.JButton btnSalir; // Botón para cerrar sesión y salir
    private javax.swing.JPanel panelMenu; // Panel principal (fondo) de la ventana
    private javax.swing.JPanel panelBotones; // Panel interior donde irán acomodados los botones
    private javax.swing.JLabel lblVersion; // Etiqueta pequeñita para mostrar la versión del sistema

    // Constructor
    public vMenuPrincipal(String usuarioActual) { // Recibe como parámetro el nombre del usuario desde vLogin
        this.usuarioActual = usuarioActual; // Guarda el nombre de usuario recibido en la variable de esta clase
        initComponents(); // Llama al método que "dibuja" la ventana
    }

    // Método que inicializa y configura todos los componentes visuales
    private void initComponents() {
        panelMenu    = new javax.swing.JPanel(); // Crea el panel principal
        panelBotones = new javax.swing.JPanel(); // Crea el panel secundario para los botones
        lblTitulo    = new javax.swing.JLabel(); // Crea la etiqueta del título
        lblBienvenido = new javax.swing.JLabel(); // Crea la etiqueta de bienvenida
        btnEmpleados  = new javax.swing.JButton(); // Crea el botón de empleados
        btnAsistencia = new javax.swing.JButton(); // Crea el botón de asistencia
        btnNomina     = new javax.swing.JButton(); // Crea el botón de nómina
        btnSalir      = new javax.swing.JButton(); // Crea el botón de salir
        lblVersion    = new javax.swing.JLabel(); // Crea la etiqueta de versión

        // Configuración básica de la ventana
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // Evita que la ventana se cierre de golpe al darle a la 'X'. Nosotros controlaremos el cierre más abajo.
        setTitle("Sistema de Gestión de Recursos Humanos"); // Texto de la barra superior de la ventana
        setPreferredSize(new Dimension(600, 550)); // Tamaño de la ventana: 600px de ancho por 550px de alto
        setResizable(false); // Bloquea la ventana para que no se pueda maximizar ni cambiar de tamaño

        //  Panel principal azul 
        panelMenu.setBackground(new Color(30, 60, 114)); // Le da un color azul corporativo al panel de fondo
        panelMenu.setLayout(new BorderLayout(10, 10)); // Usa diseño de bordes con una separación de 10 píxeles entre sus áreas

        // Header (Encabezado)
        JPanel panelHeader = new JPanel(); // Crea un sub-panel temporal solo para la parte superior
        panelHeader.setOpaque(false); // Lo hace transparente para que se vea el fondo azul oscuro de panelMenu
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS)); // Apila los elementos verticalmente (de arriba hacia abajo)
        panelHeader.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20)); // Márgenes internos (arriba, izquierda, abajo, derecha)

        JLabel lblIconHeader = new JLabel("🏢"); // Crea un emoji de edificio de oficinas
        lblIconHeader.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40)); // Usa fuente de emojis a tamaño 40
        lblIconHeader.setForeground(Color.WHITE); // Color de texto blanco
        lblIconHeader.setAlignmentX(Component.CENTER_ALIGNMENT); // Lo centra horizontalmente en el panel

        lblTitulo.setText("Recursos Humanos"); // Define el texto del título principal
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Fuente grande y en negrita
        lblTitulo.setForeground(Color.WHITE); // Texto blanco
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Lo centra

        lblBienvenido.setText("Sesión: " + usuarioActual); // Une el texto fijo con la variable que tiene el nombre del usuario
        lblBienvenido.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Fuente normal
        lblBienvenido.setForeground(new Color(180, 200, 255)); // Azul clarito
        lblBienvenido.setAlignmentX(Component.CENTER_ALIGNMENT); // Lo centra

        // Agregamos los elementos al encabezado en orden (de arriba a abajo)
        panelHeader.add(lblIconHeader); 
        panelHeader.add(Box.createVerticalStrut(8)); // Empuja el siguiente elemento 8 píxeles hacia abajo
        panelHeader.add(lblTitulo);
        panelHeader.add(Box.createVerticalStrut(4)); // Empuja el siguiente elemento 4 píxeles hacia abajo
        panelHeader.add(lblBienvenido);

        //  Panel de botones blanco 
        panelBotones.setBackground(Color.WHITE); // Pinta el panel de los botones de blanco
        panelBotones.setLayout(new GridBagLayout()); // Usa el diseño de cuadrícula flexible
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60)); // Le da márgenes internos grandes
        GridBagConstraints gbc = new GridBagConstraints(); // Objeto que define las "reglas" de la cuadrícula
        gbc.fill = GridBagConstraints.HORIZONTAL; // Los botones se estirarán a lo ancho
        gbc.weightx = 1.0; // Ocuparán todo el ancho disponible de su columna
        gbc.insets = new Insets(8, 0, 8, 0); // Margen de 8 píxeles arriba y abajo para cada botón

        // Botón Empleados
        btnEmpleados.setText(" Módulo de Empleados"); // Texto del botón
        estilizarBoton(btnEmpleados, new Color(30, 60, 114)); // Llama al método de abajo para darle diseño y un color azul
        gbc.gridx = 0; gbc.gridy = 0; // Lo pone en la columna 0, fila 0
        panelBotones.add(btnEmpleados, gbc); // Lo añade al panel

        // Botón Asistencia
        btnAsistencia.setText(" Módulo de Asistencia"); // Texto del botón
        estilizarBoton(btnAsistencia, new Color(39, 110, 58)); // Llama al método de diseño y le da un color verde
        gbc.gridy = 1; // Lo baja a la fila 1
        panelBotones.add(btnAsistencia, gbc); // Lo añade al panel

        // Botón Nómina
        btnNomina.setText(" Módulo de Nómina"); // Texto del botón
        estilizarBoton(btnNomina, new Color(150, 80, 0)); // Llama al método de diseño y le da un color naranja/marrón
        gbc.gridy = 2; // Lo baja a la fila 2
        panelBotones.add(btnNomina, gbc); // Lo añade al panel

        // Separador (línea horizontal)
        JSeparator sep = new JSeparator(); // Crea una línea divisoria visual
        sep.setForeground(new Color(220, 220, 220)); // Le da un color gris claro
        gbc.gridy = 3; // Fila 3
        gbc.insets = new Insets(10, 0, 10, 0); // Le da más separación para alejarlo de los botones de arriba y abajo
        panelBotones.add(sep, gbc); // Lo añade

        // Botón Salir
        btnSalir.setText(" Cerrar Sesión"); // Texto del botón
        estilizarBoton(btnSalir, new Color(180, 30, 30)); // Le da un diseño de color rojo
        gbc.gridy = 4; // Fila 4
        gbc.insets = new Insets(8, 0, 8, 0); // Regresa los márgenes a la normalidad
        panelBotones.add(btnSalir, gbc); // Lo añade

        // Versión del sistema
        lblVersion.setText("v1.0 — Sistema RRHH con SQLite"); // Texto informativo
        lblVersion.setFont(new Font("Segoe UI", Font.ITALIC, 10)); // Letra chiquita y en cursiva
        lblVersion.setForeground(new Color(160, 160, 160)); // Letra gris claro
        lblVersion.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto de la etiqueta
        gbc.gridy = 5; // Fila 5 (al fondo)
        panelBotones.add(lblVersion, gbc); // Lo añade

        //  Ensamblar 
        panelMenu.add(panelHeader, BorderLayout.NORTH); // Pega el encabezado azul en la parte superior (Norte)
        panelMenu.add(panelBotones, BorderLayout.CENTER); // Pega el panel blanco de botones en el resto de la ventana (Centro)
        getContentPane().add(panelMenu); // Mete todo esto dentro de la ventana real
        pack(); // Ajusta los tamaños visuales internos automáticamente
        setLocationRelativeTo(null); // Centra la ventana en tu monitor al abrirse

        //  Eventos (Qué pasa cuando haces clic) 
        
        btnEmpleados.addActionListener(evt -> { // Si haces clic en Empleados...
            new vEmpleados().setVisible(true); // Crea la ventana de empleados y la muestra
        });

        btnAsistencia.addActionListener(evt -> { // Si haces clic en Asistencia...
            new vAsistencia().setVisible(true); // Crea la ventana de asistencia y la muestra
        });

        btnNomina.addActionListener(evt -> { // Si haces clic en Nómina...
            new vNomina().setVisible(true); // Crea la ventana de nómina y la muestra
        });

        btnSalir.addActionListener(evt -> { // Si haces clic en "Cerrar Sesión"...
            // Abre una ventanita de pregunta (Sí/No)
            int conf = JOptionPane.showConfirmDialog(this,
                "¿Deseas cerrar sesión y salir del sistema?",
                "Confirmar Salida", JOptionPane.YES_NO_OPTION);
            
            if (conf == JOptionPane.YES_OPTION) { // Si el usuario presionó "Sí"
                cConexion.cerrarConexion(); // Llama a tu controlador para desconectar la base de datos de forma segura
                System.exit(0); // Cierra completamente la aplicación
            }
        });

        // Evento especial: ¿Qué pasa si el usuario presiona la 'X' roja de la ventana de Windows/Mac?
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                btnSalir.doClick(); // En lugar de cerrarse de golpe, simula que hicieron clic en el botón "Cerrar Sesión" (así sale el mensaje de confirmación)
            }
        });
    }

    // Método "Helper" o auxiliar: Sirve para no repetir el mismo código de diseño por cada botón
    // Recibe el botón que quieres modificar y el color que le quieres dar
    private void estilizarBoton(JButton btn, Color color) {
        btn.setBackground(color); // Pinta el fondo del botón con el color recibido
        btn.setForeground(Color.BLACK); // Pinta el texto de negro (Ojo: quizá quieras cambiarlo a WHITE si tus fondos son oscuros)
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Letra negrita tamaño 14
        btn.setPreferredSize(new Dimension(380, 50)); // Define un tamaño fijo para que todos los botones sean iguales
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16)); // Le quita el borde 3D por defecto de Java
        btn.setFocusPainted(false); // Quita el recuadro punteado feo cuando seleccionas el botón
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a la "manito"

        // Efecto hover (Cambio de color al pasar el mouse por encima)
        Color colorHover = color.darker(); // Calcula un tono más oscuro del color original
        btn.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseEntered(MouseEvent e) { 
                btn.setBackground(colorHover); // Cuando el mouse entra al botón, lo oscurece
            }
            @Override 
            public void mouseExited(MouseEvent e)  { 
                btn.setBackground(color); // Cuando el mouse sale, le regresa su color original
            }
        });
    }
}
