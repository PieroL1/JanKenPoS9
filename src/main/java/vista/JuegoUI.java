package vista;

import modelo.Jugada;
import modelo.Resultado;
import viewmodel.JuegoViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class JuegoUI extends JFrame implements Observer, ActionListener {
    private JuegoViewModel viewModel;
    private JLabel lblMensaje;
    private JLabel lblPuntuacion;
    private JLabel lblTemporizador;
    private JButton btnPiedra, btnPapel, btnTijeras;
    private JButton btnReiniciar, btnHistorial;
    private JToggleButton btnModoJuego;
    private JToggleButton btnDificultadIA;
    private JPanel panelJuego;
    private JPanel panelControles;
    private JPanel panelMensajes;
    private SelectorTemaUI selectorTema;
    private Timer timerUI;
    private AnimacionManager animacionManager;
    
    // Temas de colores
    private Color[] colorFondo = {Color.WHITE, new Color(50, 50, 50), new Color(230, 230, 255)};
    private Color[] colorTexto = {Color.BLACK, Color.WHITE, new Color(0, 0, 100)};
    private Color[] colorPanel = {new Color(240, 240, 240), new Color(70, 70, 70), new Color(200, 220, 255)};
    private Color[] colorBoton = {new Color(220, 220, 220), new Color(100, 100, 100), new Color(180, 200, 255)};
    
    public JuegoUI() {
        viewModel = new JuegoViewModel();
        viewModel.addObserver(this);
        
        configurarVentana();
        inicializarComponentes();
        
        // Iniciar timer para actualizar UI
        timerUI = new Timer(100, e -> actualizarUI());
        timerUI.start();
    }
    
    private void configurarVentana() {
        setTitle("Jan Ken Po - Juego de Piedra, Papel o Tijeras");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Crear paneles principales
        panelJuego = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (animacionManager != null) {
                    animacionManager.dibujarAnimacion(g);
                }
            }
        };
        panelControles = new JPanel();
        panelMensajes = new JPanel();
        
        // Configurar layouts
        panelJuego.setLayout(new GridLayout(1, 3, 10, 10));
        panelControles.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
        
        // Añadir paneles al frame
        add(panelJuego, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);
        add(panelMensajes, BorderLayout.NORTH);
        
        // Inicializar animaciones
        animacionManager = new AnimacionManager(panelJuego);
    }
    
    private void inicializarComponentes() {
        // Panel de mensaje y puntuación
        lblMensaje = new JLabel(viewModel.getMensajeResultado(), JLabel.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblPuntuacion = new JLabel("Jugador 1: 0 | Jugador 2/IA: 0 | Empates: 0", JLabel.CENTER);
        lblTemporizador = new JLabel("Tiempo: --", JLabel.CENTER);
        
        panelMensajes.add(lblMensaje);
        panelMensajes.add(Box.createRigidArea(new Dimension(0, 5)));
        panelMensajes.add(lblPuntuacion);
        panelMensajes.add(Box.createRigidArea(new Dimension(0, 5)));
        panelMensajes.add(lblTemporizador);
        
        // Configurar botones de juego
        btnPiedra = crearBotonJugada("Piedra", Jugada.PIEDRA);
        btnPapel = crearBotonJugada("Papel", Jugada.PAPEL);
        btnTijeras = crearBotonJugada("Tijeras", Jugada.TIJERAS);
        
        panelJuego.add(btnPiedra);
        panelJuego.add(btnPapel);
        panelJuego.add(btnTijeras);
        
        // Configurar botones de control
        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setActionCommand("reiniciar");
        btnReiniciar.addActionListener(this);
        
        btnHistorial = new JButton("Historial");
        btnHistorial.setActionCommand("historial");
        btnHistorial.addActionListener(this);
        
        btnModoJuego = new JToggleButton("Modo: IA");
        btnModoJuego.setActionCommand("modoJuego");
        btnModoJuego.addActionListener(this);
        
        btnDificultadIA = new JToggleButton("Dificultad: Fácil");
        btnDificultadIA.setActionCommand("dificultad");
        btnDificultadIA.addActionListener(this);
        
        // Selector de tema
        selectorTema = new SelectorTemaUI(this);
        
        panelControles.add(btnReiniciar);
        panelControles.add(btnHistorial);
        panelControles.add(btnModoJuego);
        panelControles.add(btnDificultadIA);
        panelControles.add(btnDificultadIA);
        panelControles.add(selectorTema);
        
        aplicarTema(0); // Tema clásico por defecto
    }
    
    private JButton crearBotonJugada(String texto, Jugada jugada) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setPreferredSize(new Dimension(120, 80));
        boton.setActionCommand("jugada_" + jugada.name());
        boton.addActionListener(this);
        
        // Añadir icono
        try {
            String rutaIcono = "/imagenes/" + jugada.name().toLowerCase() + ".png";
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            // Escalar icono si es necesario
            Image img = resizeImage(icono.getImage(), 150, 150);

            boton.setIcon(new ImageIcon(img));
            boton.setVerticalTextPosition(SwingConstants.BOTTOM);
            boton.setHorizontalTextPosition(SwingConstants.CENTER);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono para " + jugada);
        }
        
        return boton;
    }
    
    private Image resizeImage(Image img, int width, int height) {
    BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = resizedImage.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2d.drawImage(img, 0, 0, width, height, null);
    g2d.dispose();
    return resizedImage;
}

    
    private void actualizarUI() {
        // Actualizar temporizador si hay partida en curso
        if (viewModel.partidaEnCurso()) {
            long tiempo = viewModel.getTiempoRestante();
            lblTemporizador.setText("Tiempo: " + tiempo + "s");
            
            if (viewModel.tiempoAgotado()) {
                // Si se agotó el tiempo, cancelar la jugada
                viewModel.reiniciarJuego();
                lblMensaje.setText("¡Tiempo agotado! La ronda se canceló.");
            }
        } else {
            lblTemporizador.setText("Tiempo: --");
        }
        
        // Actualizar estado de los botones según el modo de juego
        btnDificultadIA.setEnabled(!viewModel.esModoMultijugador());
    }
    
    @Override
    public void update(Observable o, Object arg) {
        // Actualizar interfaz con datos del ViewModel
        lblMensaje.setText(viewModel.getMensajeResultado());
        
        String puntuacion = "Jugador 1: " + viewModel.getVictoriasJugador1() + " | ";
        puntuacion += viewModel.esModoMultijugador() ? "Jugador 2: " : "IA: ";
        puntuacion += viewModel.getVictoriasJugador2() + " | Empates: " + viewModel.getEmpates();
        lblPuntuacion.setText(puntuacion);
        
        // Actualizar botones según el turno en modo multijugador
        if (viewModel.esModoMultijugador() && viewModel.esTurnoJugador2()) {
            lblMensaje.setText("Jugador 2, elige tu jugada");
        }
        
        // Actualizar texto de botones de modo
        btnModoJuego.setText("Modo: " + (viewModel.esModoMultijugador() ? "Multijugador" : "IA"));
        btnDificultadIA.setText("Dificultad: " + (viewModel.getDificultadIA() ? "Difícil" : "Fácil"));
        
        // Reproducir animación y sonido según el mensaje de resultado
        if (viewModel.getMensajeResultado().contains("gana")) {
            boolean jugador1Gana = viewModel.getMensajeResultado().contains("Jugador 1 gana");
            if ((jugador1Gana && !viewModel.esTurnoJugador2()) || 
                (!jugador1Gana && viewModel.esTurnoJugador2())) {
                animacionManager.iniciarAnimacion("victoria");
                SonidoManager.reproducirSonido(SonidoManager.TipoSonido.VICTORIA);
            } else {
                animacionManager.iniciarAnimacion("derrota");
                SonidoManager.reproducirSonido(SonidoManager.TipoSonido.DERROTA);
            }
        } else if (viewModel.getMensajeResultado().contains("Empate")) {
            animacionManager.iniciarAnimacion("empate");
            SonidoManager.reproducirSonido(SonidoManager.TipoSonido.EMPATE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if (comando.startsWith("jugada_")) {
            // Procesar jugada
            String jugadaStr = comando.substring(7); // Quitar "jugada_"
            Jugada jugada = Jugada.valueOf(jugadaStr);
            
            // Reproducir sonido de selección
            SonidoManager.reproducirSonido(SonidoManager.TipoSonido.SELECCION);
            animacionManager.iniciarAnimacion("seleccion");
            
            // Si no hay partida en curso, iniciarla
            if (!viewModel.partidaEnCurso()) {
                viewModel.iniciarPartida();
            }
            
            // Procesar la jugada
            viewModel.seleccionarJugada(jugada);
            
        } else if (comando.equals("reiniciar")) {
            viewModel.reiniciarJuego();
            
        } else if (comando.equals("historial")) {
            HistorialUI historialUI = new HistorialUI(this, viewModel.getHistorialPartidas());
            historialUI.setVisible(true);
            
        } else if (comando.equals("modoJuego")) {
            viewModel.setModoMultijugador(btnModoJuego.isSelected());
            
        } else if (comando.equals("dificultad")) {
            viewModel.setDificultadIA(btnDificultadIA.isSelected());
            
        } else if (comando.equals("cambiarTema")) {
            int indice = selectorTema.getTemaSeleccionado().equals("Clásico") ? 0 : 
                         selectorTema.getTemaSeleccionado().equals("Oscuro") ? 1 : 2;
            aplicarTema(indice);
        }
    }
    
    private void aplicarTema(int indice) {
        // Aplicar colores según el tema seleccionado
        Color fondo = colorFondo[indice];
        Color texto = colorTexto[indice];
        Color panelColor = colorPanel[indice];
        Color botonColor = colorBoton[indice];
        
        // Aplicar a la ventana principal
        getContentPane().setBackground(fondo);
        
        // Aplicar a paneles
        panelJuego.setBackground(panelColor);
        panelControles.setBackground(panelColor);
        panelMensajes.setBackground(panelColor);
        selectorTema.setBackground(panelColor);
        
        // Aplicar a etiquetas
        lblMensaje.setForeground(texto);
        lblPuntuacion.setForeground(texto);
        lblTemporizador.setForeground(texto);
        
        // Aplicar a botones de juego
        btnPiedra.setBackground(botonColor);
        btnPapel.setBackground(botonColor);
        btnTijeras.setBackground(botonColor);
        
        // Aplicar a botones de control
        btnReiniciar.setBackground(botonColor);
        btnHistorial.setBackground(botonColor);
        btnModoJuego.setBackground(botonColor);
        btnDificultadIA.setBackground(botonColor);
        
        // Actualizar UI
        SwingUtilities.updateComponentTreeUI(this);
    }
}
