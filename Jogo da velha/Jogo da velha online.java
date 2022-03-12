package jogodavelhaonline;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JogoDaVelhaOnline extends Application {
    
    public static Tabuleiro board = new Tabuleiro();
    public static boolean playing = true;
    
    static FXMLDocumentController f;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = (Parent)loader.load();
        f = loader.getController();
          
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setMaxHeight(520);
        stage.setMaxWidth(500);
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                System.exit(0);
            }
        });                
    }

    public static void main(String[] args) {
        launch(args);
        
    }
    
    public static void jogada(Button quadro, Label label){
        
        int l = Integer.parseInt(quadro.getId().substring(6, 7));
        int c = Integer.parseInt(quadro.getId().substring(7));
        
        System.out.println(l + " " + c);
        
        if(FXMLDocumentController.isServer){
            quadro.setText("X");
        }else{
            quadro.setText("O");
        }
    }
    
    public static void interpretador(String s){
        
        if(s.substring(0, 6).compareTo("Quadro") == 0){
            FXMLDocumentController.vez = true;
            int l = Integer.parseInt(s.substring(6, 7));
            int c = Integer.parseInt(s.substring(7));
            System.out.println("Recebido do server: " + l + " " + c + " " + s);
            f.mudarButton(l, c, s);
            if(FXMLDocumentController.isServer){
                f.mudarLabelpublic("Sua vez jogador 1", false);
            }else{
                f.mudarLabelpublic("Sua vez jogador 2", false);
            }
            
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(JogoDaVelhaOnline.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            playing = !f.checaVitoria();
            if(playing == false){
                f.mudarLabelpublic("", true);
            }
        }else if(s.compareTo("resetar") == 0){
            f.reset();
        }
        
    }
    
}
