package Usuario;

import Modelo.Mensaje;
import Modelo.Nombres;
import Modelo.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
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
        flujoEntrada = new ObjectInputStream(socket.getInputStream());
        
        //mandamos los datos del cliente
        flujoSalida.writeObject(padre.getUser());// enviando el objeto
        
        escuchando = true;
    }

    public void run() {
        
        try {
            
            while(escuchando)
            {
                Object obj = flujoEntrada.readObject();
                
                if(obj instanceof String)
                {
                    String men = obj.toString();
                    
                    padre.addMessage(men); //System.out.println(men);
                }
                
                if (obj instanceof ArrayList)
                {
                    //añadir los nombres al jlist

                    ArrayList n = (ArrayList)obj;
                    
//                    for (int i = 0; i < n.getNombres().size(); i++) {
//                        
//                        System.out.println("\n" + n.getNombres().get(i));
//                        
//                    }

                    padre.mostrarUsuarios(n);

                }
                
            }
            
            System.out.println("Desconexion del usuario");

            flujoEntrada.close();
            flujoSalida.close();
            socket.close();
        } catch (Exception e) {
           
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
        catch(Exception ex)
        {
            
        }
        
    }
    
    public synchronized void enviar(String user, String msg) 
    {
        Mensaje j = new Mensaje(user, msg);
        try
        {
            flujoSalida.writeObject(j);
        }
        catch(IOException ex)
        {
            
        }
        
    }
}