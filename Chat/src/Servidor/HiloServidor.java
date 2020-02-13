package Servidor;

import Modelo.Mensaje;
import Modelo.Nombres;
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

public class HiloServidor extends Thread implements Observer{
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
        escuchando = true;
    }

    public void run() {
        
        try {
            
            while(escuchando)
            {
                Object obj = fentrada.readObject();
            
                if(obj instanceof User)
                {
                    datosUsuario = (User)obj;

                    System.out.println(datosUsuario.getNombre());

                    rebote.nuevoMensaje(" ********** Servidor dice: "+ "\n" + 
                            " se ha conectado " + datosUsuario.getNombre() + " *********** \n");
                    
                    listado.anadirUsusario(datosUsuario.getNombre());
                }
                else
                {
                    //si envian un string, quiere decir que debemos finalizar
                    //la ejecucion del hilo
                    if(obj instanceof String)
                    {
                        escuchando = false;
                        
                        listado.borrarUsuario(datosUsuario.getNombre());
                    }
                    else
                    {
                        if(obj instanceof Mensaje)
                        {
                            Mensaje msg = (Mensaje)obj;
                            rebote.nuevoMensaje(msg.getNombreUsuario() + " dice: "+ "\n" + 
                                    " " + msg.getMensaje() + " \n");
                        }
                    }
                }
               
            }
            
            eliminar();
            
        } catch (Exception e) {
            
        } 
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        try
        {
            
        
            if(arg instanceof String)
            {
                mensaje = arg.toString();
                try {
                    fsalida.writeObject(mensaje);// enviando el objeto
                } catch (IOException ex) {
                    Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                //Nombres nombres = (Nombres)arg;
                ArrayList lista1 = (ArrayList) arg;
                ArrayList lista = new ArrayList(lista1);
    //            System.out.println("\nDatos que recibe el cliente " + datosUsuario.getNombre());
    //            for (int i = 0; i < nombres.getNombres().size(); i++) {
    //                System.out.println("\n" + nombres.getNombres().get(i));
    //            }

                try {
                    fsalida.writeObject(lista);// enviando el objeto
                } catch (IOException ex) {
                    Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        catch(Exception ex)
        {
            
        }
    }
        
    private void eliminar() throws IOException
    {
        System.out.println("Fin de comunicación con socket " + socket.toString());
        fsalida.close();
        fentrada.close();
        socket.close();
        rebote.deleteObserver(this);
        listado.deleteObserver(this);

        rebote.nuevoMensaje(" ********** Servidor dice: "+ "\n" + 
                        " se ha desconectado " + datosUsuario.getNombre() + " *********** \n");

        Servidor.eliminarHilo(this);
    }
                    
             
}