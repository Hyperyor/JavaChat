package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    private static ArrayList<HiloServidor> listadoHilos = new ArrayList<HiloServidor>();

    public static void main(String[] args) {
        int puerto = 6000; // puerto
        //objeto observable para enviar los mensajes a todos los clientes conectados
        LlegadaMensaje mensajes = new LlegadaMensaje();
        //objeto observable para enviar la lista de los clientes conectados a todos los clientes conectados
        ListadoClientes nombreClientes = new ListadoClientes();

        try {
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado...escuchando por el puerto "
                    + servidor.getLocalPort());
            while (true) {
                //aceptamos conexiones en un bucle infinito
                Socket cliente = servidor.accept();
                //cremos un hilo por cada conexion aceptada
                HiloServidor hilo = new HiloServidor(cliente, mensajes, nombreClientes);
                //iniciamos su ejecucion
                hilo.start();
                //añadimos el hilo a la lista de observadores
                //de los mensajes
                mensajes.addObserver(hilo);
                //añadimos el hilo a la lista de observadores
                //de la lista de nombres
                nombreClientes.addObserver(hilo);
                //añadimos el hilo a un array de hilos interno (sobra)
                listadoHilos.add(hilo);
            }
        } catch (Exception e) {

        }
    }

    //metodo que elimina un hilo de la lista interna de hilos
    public static void eliminarHilo(HiloServidor h) {
        listadoHilos.remove(h);
    }
}
