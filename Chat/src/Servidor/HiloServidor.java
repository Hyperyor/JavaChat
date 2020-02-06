package Servidor;

import Modelo.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloServidor extends Thread {
    private Socket socket;
    private DataOutputStream fsalida;
    private ObjectInputStream fentrada;
    private boolean escuchando;

    public HiloServidor(Socket s) throws IOException {
        this.socket = s;
        // se crean los flujos de entrada y salida
        //fsalida = new DataOutputStream(s.getOutputStream());
        fentrada = new ObjectInputStream(socket.getInputStream());
        escuchando = true;
    }

    public void run() {
        String cadena = "";
        try {
            
            while(escuchando)
            {
                Object obj = fentrada.readObject();
            
                if(obj instanceof User)
                {
                    User datosUsuario = (User)obj;

                    System.out.println(datosUsuario.getNombre());
                }
                else
                {
                    if(obj instanceof String)
                    {
                        escuchando = false;
                    }
                }
            }
            
            
//            while (!cadena.trim().equals("*")) {
//                //System.out.println("Comunico con socket " + socket.toString());
//                cadena = fentrada.readUTF();// obtengo la cadena
//                
//                System.out.println(cadena);
//                //fsalida.writeUTF(cadena.toUpperCase());// envio en mayúsculas
//            }
            System.out.println("Fin de comunicación con socket " + socket.toString());
            //fsalida.close();
            fentrada.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}