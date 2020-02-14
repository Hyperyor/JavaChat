package Servidor;

import Modelo.Mensaje;
import Modelo.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;

public class HiloServidor extends Thread implements Observer {

    private Socket socket;
    private ObjectOutputStream fsalida;
    private ObjectInputStream fentrada;
    private boolean escuchando;

    private String mensaje = "";

    private LlegadaMensaje rebote;
    private ListadoClientes listado;

    private User datosUsuario;

    public HiloServidor(Socket s, LlegadaMensaje men, ListadoClientes nombres) throws IOException {
        this.socket = s;
        rebote = men;
        listado = nombres;
        // se crean los flujos de entrada y salida
        //fsalida = new DataOutputStream(s.getOutputStream());
        fentrada = new ObjectInputStream(socket.getInputStream());
        fsalida = new ObjectOutputStream(socket.getOutputStream());
        //bandera para el bucle infinito
        escuchando = true;
    }

    public void run() {

        try {

            //escucha constantemente
            while (escuchando) {
                //recibe un objeto
                Object obj = fentrada.readObject();

                //si recibe una instancia de usuario quiere decir que se acaba
                //de conectar un cliente
                if (obj instanceof User) {
                    datosUsuario = (User) obj;

                    System.out.println(datosUsuario.getNombre());
                    //le decimos al objeto observable de mensajes que
                    //muestre un mensaje "del servidor" indicando
                    //que se ha conectado un nuevo usuario
                    rebote.nuevoMensaje(" ********** Servidor dice: " + "\n"
                            + " se ha conectado " + datosUsuario.getNombre() + " *********** \n");
                    //le decimos al objeto observable de usuarios conectados que
                    //actualice la lista introduciendo el nombre del nuevo
                    listado.anadirUsusario(datosUsuario.getNombre());
                } else {
                    //si envian un string, quiere decir que debemos finalizar
                    //la ejecucion del hilo
                    if (obj instanceof String) {
                        escuchando = false; //bajamos la bandera
                        //eliminamos al usuario de la lista de usuarios conectados
                        listado.borrarUsuario(datosUsuario.getNombre());
                    } else {
                        //si el objeto es una instancia de mensaje
                        if (obj instanceof Mensaje) {
                            Mensaje msg = (Mensaje) obj;
                            //le decimos al objeto observable de mensajes que
                            //muestre un mensaje del usuario "X" (la clase
                            //mensaje tiene dos parametros: nombre de usuario
                            //que envia ese mensaje y el mensaje como tal)
                            rebote.nuevoMensaje(msg.getNombreUsuario() + " dice: " + "\n"
                                    + " " + msg.getMensaje() + " \n");
                        }
                    }
                }

            }

            //al salir del bucle desconectamos
            eliminar();

        } catch (Exception e) {

        }
    }

    //metodo que se llamara como resultado de una notificacion
    //por parte de los objetos observables
    @Override
    public synchronized void update(Observable o, Object arg) {
        try {

            //si nos pasan un String, quiere decir que alguien ha mandado
            //un mensaje en la conversacion
            if (arg instanceof String) {
                mensaje = arg.toString();
                try {
                    //se lo mandamos al cliente
                    fsalida.writeObject(mensaje);// enviando el objeto
                } catch (IOException ex) {
                    Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //si no es un String se trata de una actualizacion en el listado
                //de usuarios conectados, por lo que se lo mandamos al cliente

                ArrayList lista1 = (ArrayList) arg; //esta variable me cae mal
                ArrayList lista = new ArrayList(lista1);

                try {
                    //le enviamos el arraylist de usuarios conectados al cliente
                    fsalida.writeObject(lista);// enviando el objeto
                } catch (IOException ex) {
                    Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {

        }
    }

    private void eliminar() throws IOException {
        System.out.println("Fin de comunicación con socket " + socket.toString());
        //cerramos flujos
        fsalida.close();
        fentrada.close();
        //cerramos socket
        socket.close();
        //eliminamos el hilo actual de los observadores
        rebote.deleteObserver(this);
        listado.deleteObserver(this);

        //le indicamos al objeto observable de mensajes que muestre
        //un mensaje "del servidor" indicando que un usuario se ha desconectado
        rebote.nuevoMensaje(" ********** Servidor dice: " + "\n"
                + " se ha desconectado " + datosUsuario.getNombre() + " *********** \n");

        //eliminamos el hilo actual de la lista de hilos del servidor
        Servidor.eliminarHilo(this);
    }

}
