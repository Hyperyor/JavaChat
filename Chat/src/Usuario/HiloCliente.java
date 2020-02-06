package Usuario;

import Modelo.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloCliente extends Thread {
    private Socket socket;
    private ObjectOutputStream flujoSalida;
    private ObjectInputStream flujoEntrada;
    private boolean escuchando;
    private Cliente padre;

    public HiloCliente(Cliente c, Socket s) throws IOException {
        this.socket = s;
        padre = c;

        //creamos el flujo de salida
        flujoSalida = new ObjectOutputStream(socket.getOutputStream());
        
        //mandamos los datos del cliente
        flujoSalida.writeObject(padre.getUser());// enviando el objeto
        
        escuchando = true;
    }

    public void run() {
        
        try {
            
            while(escuchando)
            {
                
            }
            
            
            
            System.out.println("Desconexion del usuario");

            flujoEntrada.close();
            flujoSalida.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void desconectar() 
    {
        System.out.println("Desconectamos");
        escuchando = false;
        
        try
        {
            flujoSalida.writeObject("salimos");
        }
        catch(IOException ex)
        {
            
        }
        
    }
}