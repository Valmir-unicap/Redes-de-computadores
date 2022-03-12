package jogodavelhaonline;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Label labelVictory;
    
    @FXML
    private Button Quadro00;
    @FXML
    private Button Quadro01;
    @FXML
    private Button Quadro02;
    @FXML
    private Button Quadro10;
    @FXML
    private Button Quadro11;
    @FXML
    private Button Quadro12;
    @FXML
    private Button Quadro20;
    @FXML
    private Button Quadro21;
    @FXML
    private Button Quadro22;
    
    
    private Button[][] Quadros = new Button[3][3];
    
    static boolean isServer = false;
    public static boolean vez = true;
    TCPServer tcpServer = new TCPServer();
    TCPCliente tcpClient = new TCPCliente();
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        JogoDaVelhaOnline.playing = !checaVitoria();
        if(JogoDaVelhaOnline.playing){
            if(vez){
                Button b = (Button)event.getSource();
                System.out.println(b.getId() + " click");
                if(b.getText().compareTo(" ") == 0){
                    JogoDaVelhaOnline.jogada(b, label);
                    JogoDaVelhaOnline.playing = !checaVitoria();
                    if(isServer){
                        mudarLabelprivate("Vez de jogador 2");
                        tcpServer.messagem(b.getId());
                    }else{
                        mudarLabelprivate("Vez de jogador 1");
                        tcpClient.messagem(b.getId());
                    }
                    
                    if(JogoDaVelhaOnline.playing == false){
                        labelVictory.setText("Você ganhou!");
                    }
                    
                    vez = false; 
                }
            }
        }
        
        
        if(false){
            Button b = (Button)event.getSource();
            System.out.println(b.getId() + " click");
        }
    }
    
    @FXML
    private void initServer(){
        if(!isServer){
            vez = true;
            isServer = true;
            tcpServer.start();
            mudarLabelprivate("Vez de " + (vez == true? "jogador 1":"jogador 2"));
        }
    }
    
    @FXML
    private void connect(){
        if(!isServer){
            vez = false;
            tcpClient.start();
            mudarLabelprivate("Vez de " + (vez == false? "jogador 1":"jogador 2"));
        }
    }
    
    @FXML
    private void serverReset(){
        for(int i = 0; i < Quadros.length; i++){
            for(int j = 0; j < Quadros.length; j++){
                Quadros[i][j].setText(" ");
            }
        }
        JogoDaVelhaOnline.playing = true;
        labelVictory.setText("");
        
        if(isServer==false){
            tcpServer.messagem("reset");
        }else{
            tcpClient.messagem("reset");
        }
    }
    
    public void reset(){
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                for(int i = 0; i < Quadros.length; i++){
                    for(int j = 0; j < Quadros.length; j++){
                        Quadros[i][j].setText(" ");
                    }
                }
                JogoDaVelhaOnline.playing = true;
                labelVictory.setText("");
            }
        });
    }
    
    private void mudarLabelprivate(String s){
        label.setText(s);
    }
    
    public void mudarLabelpublic(String s, boolean victory){
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                if(!victory){
                    label.setText(s);
                }else{
                    labelVictory.setText("Você perdeu.");
                }
            }
        });
    }
    
    public void mudarButton(int l, int c, String s){
        if(JogoDaVelhaOnline.playing){
            Platform.runLater(new Runnable(){

                @Override
                public void run() {
                    if(!isServer){
                        Quadros[l][c].setText("X");
                    }else{
                        Quadros[l][c].setText("O");
                    }
                }
            });
        }
    }
    
    public boolean checaVitoria(){
        char aux;
        int i,j;
        
        //Coluna
        for(j=0 ; j < 3 ; j++){
            aux = Quadros[0][j].getText().charAt(0);
            
            if(aux != 0 && aux == Quadros[1][j].getText().charAt(0) && aux == Quadros[2][j].getText().charAt(0)){
                if(this.Quadros[0][j].getText().charAt(0) != ' '){
                    return true;
                }
            }
        }
        
        //Linha
        for(i=0 ; i < 3 ; i++){
            aux = Quadros[i][0].getText().charAt(0);
            
            if(aux != 0 && aux == Quadros[i][1].getText().charAt(0) && aux == Quadros[i][2].getText().charAt(0)){
                if(this.Quadros[i][0].getText().charAt(0) != ' '){
                    return true;
                }
            }
        }
        
        //Diagonal
        if(Quadros[0][0].getText().charAt(0) != 0 &&  Quadros[0][0].getText().charAt(0) == Quadros[1][1].getText().charAt(0) && Quadros[1][1].getText().charAt(0) == Quadros[2][2].getText().charAt(0)){
            if(this.Quadros[1][1].getText().charAt(0) != ' '){
                return true;
            }
            
        }else if(Quadros[0][2].getText().charAt(0) != 0 && Quadros[0][2].getText().charAt(0) == Quadros[1][1].getText().charAt(0) && Quadros[1][1].getText().charAt(0) == Quadros[2][0].getText().charAt(0)){
            if(this.Quadros[1][1].getText().charAt(0) != ' '){
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Quadros[0][0] = Quadro00;
        Quadros[0][1] = Quadro01;
        Quadros[0][2] = Quadro02;
        Quadros[1][0] = Quadro10;
        Quadros[1][1] = Quadro11;
        Quadros[1][2] = Quadro12;
        Quadros[2][0] = Quadro20;
        Quadros[2][1] = Quadro21;
        Quadros[2][2] = Quadro22;
    }    
    
}
