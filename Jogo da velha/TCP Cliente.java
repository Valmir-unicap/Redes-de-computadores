package jogodavelhaonline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPCliente extends Thread{
    
    public PrintWriter out;
    BufferedReader in;
    
    @Override
    public void run() {
        main();
    }
    
    public void main() {
        Scanner scan = new Scanner(System.in);
        try {           
            //String host = InetAddress.getLocalHost().getHostAddress();
            //System.out.println(host);
            InetAddress ip = InetAddress.getByName("26.76.169.77");
            System.out.println(ip);
            Socket servidor = new Socket(ip,4899);
            System.out.println("Conectado com sucesso!");
            
            out = new PrintWriter(servidor.getOutputStream(), true);
            
            in = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            
            while(true){
                if(in.ready()){
                    JogoDaVelhaOnline.interpretador(getMenssagem());
                    System.out.print("");
                }
            }
        } catch (IOException ex) {
            System.out.println("Aguarde!");
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
