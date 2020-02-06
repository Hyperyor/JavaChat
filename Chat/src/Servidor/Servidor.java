package Servidor;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 6000; // puerto
        
        ArrayList<HiloServidor> listadoHilos = new ArrayList<HiloServidor>();
        
        try {
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado...escuchando por el puerto "
                                                            + servidor.getLocalPort());
            while (true) {
                Socket cliente = servidor.accept();
                // Esperando a un cliente
                HiloServidor hilo = new HiloServidor(cliente);
                hilo.start();
                
                listadoHilos.add(hilo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}