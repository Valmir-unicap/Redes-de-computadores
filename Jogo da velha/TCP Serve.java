package jogodavelhaonline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer extends Thread{

    public PrintWriter out;
    BufferedReader in;
    
    @Override
    public void run() {
        main();
    }

    public void main() {
        try {
            ServerSocket server = new ServerSocket(4899);
            System.out.println("Servidor buscando cliente");
            
            Socket cliente = server.accept();
            System.out.println("Cliente conectado do IP "+cliente.getInetAddress().getHostAddress());
            
            out = new PrintWriter(cliente.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            
            while(true){
                if(in.ready()){
                    JogoDaVelhaOnline.interpretador(getMenssagem());
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void messagem(String s){
        out.println(s);
    }
    
    public String getMenssagem(){
        try {
            if(in.ready()){
                return in.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
}
