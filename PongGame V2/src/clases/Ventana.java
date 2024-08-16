package clases;

import java.awt.Toolkit;
import javax.swing.JFrame;

public class Ventana extends JFrame implements Temporizador.TemporizadorListener {
      
   private final int ANCHO=800, ALTO=500;
  
   private TableroJuego lamina;
   
   private Hilo hilo;
   
   private Temporizador temporizador;

   
   
    public Ventana (){
        
        setTitle("PONG");
        setSize(ANCHO, ALTO);
        setLocationRelativeTo(null);
        setResizable(false);
        
        lamina = new TableroJuego();
        add(lamina);
        addKeyListener(new EventoTeclado());
        
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        hilo = new Hilo(lamina);
        hilo.start();
        
        temporizador = new Temporizador(this);
        temporizador.start();
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/PONG.png")));
     
    }
    
    @Override
    public void onTiempoActualizado(int tiempoRestante) {
        
    }

    @Override
    public void onTiempoFinalizado() {
        
    }
    
}
