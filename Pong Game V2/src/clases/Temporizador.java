package clases;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Temporizador {

    private Timer timer;
    static int tiempoRestante;
    private TemporizadorListener listener;

    public interface TemporizadorListener {
        void onTiempoActualizado(int tiempoRestante);
        void onTiempoFinalizado();
    }

    public Temporizador(TemporizadorListener listener) {
    	int tiempoDelPartido=10000;
    	
        this.tiempoRestante = tiempoDelPartido / 1000; // tiempo en segundos
        this.listener = listener;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                if (listener != null) {
                    listener.onTiempoActualizado(tiempoRestante);
                }
                if (tiempoRestante <= 0) {
                    timer.stop();
                    if (listener != null) {
                        listener.onTiempoFinalizado();
                    }
                }
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void detener() {
        timer.stop();
    }

    public static int getTiempoRestante() {
        return tiempoRestante;
    }
    
}
