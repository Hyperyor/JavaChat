package Servidor;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    
    private static ArrayList<HiloServidor> listadoHilos = new ArrayList<HiloServidor>();
    
    public static void main(String[] args) {
        int puerto = 6000; // puerto
        LlegadaMensaje mensajes = new LlegadaMensaje();
        ListadoClientes nombreClientes = new ListadoClientes();

        try {
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado...escuchando por el puerto "
                                                            + servidor.getLocalPort());
            while (true) {
                Socket cliente = servidor.accept();
                // Esperando a un cliente
                HiloServidor hilo = new HiloServidor(cliente, mensajes, nombreClientes);
                hilo.start();
                mensajes.addObserver(hilo);
                nombreClientes.addObserver(hilo);
                listadoHilos.add(hilo);
            }
        } catch (Exception e) {
            
        }
    }
    
    public static void eliminarHilo(HiloServidor h)
    {
        listadoHilos.remove(h);
    }
}