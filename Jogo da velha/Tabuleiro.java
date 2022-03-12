package jogodavelhaonline;

import java.io.Serializable;

public class Tabuleiro implements Serializable{
    private char[][] board;
    private int vez;
    private int numDeJogadas;
    
    public Tabuleiro(){
        this.board = new char[3][3];
        limparTabuleiro();
        this.vez = 0;
        this.numDeJogadas = 9;
    }
    
    public int getVez(){
        return this.vez;
    }
    
    public int getNumDeJogadas(){
        return this.numDeJogadas;
    }
      
    private void limparTabuleiro(){
        for(int linha = 0; linha < this.board.length; linha++){
            for(int coluna = 0; coluna < this.board.length; coluna++){
                this.board[linha][coluna] = ' ';
            }
        }
    }
    
    public void reset(){
        this.numDeJogadas = 9;
        limparTabuleiro();
    }
    
    public char getBoardPosition(int l, int c){
        return this.board[l][c];
    }

    public boolean play(int linha, int coluna){
        char simb[] = {'X', 'O'};
        
        if(this.numDeJogadas > 0){
            if((linha >= 0 && linha <= 2) && (coluna >= 0 && coluna <= 2)){
                if(this.board[linha][coluna] == ' '){
                    this.board[linha][coluna] = simb[this.vez];
                    this.numDeJogadas--;

                    this.vez = (this.vez + 1)%2;

                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean checkVictory(){
        return checkColun() || checkLine() || checkDiag();
    }
    
    private boolean checkColun(){
        int coluna;
        for(coluna = 0; coluna < this.board.length; coluna++){
            if(this.board[1][coluna] == this.board[0][coluna] && this.board[2][coluna] == this.board[0][coluna]){
                if(this.board[0][coluna] != ' '){
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean checkLine(){
        int line;
        for(line = 0; line < this.board.length; line++){
            if(this.board[line][1] == this.board[line][0] && this.board[line][2] == this.board[line][0]){
                if(this.board[line][0] != ' '){
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean checkDiag(){
        if(this.board[1][1] == this.board[2][2] && this.board[0][0] == this.board[2][2]){
            if(this.board[2][2] != ' '){
                return true;
            }
        }
        if(this.board[0][2] == this.board[2][2] && this.board[2][0] == this.board[2][2]){
            if(this.board[2][2] != ' '){
                return true;
            }
        }
        return false;
    }
    
    public String showBoard(){
        return "     1      2      3   \n\n"
             + "1  | "+ board[0][0] +" | | "+ board[0][1] +" | | "+ board[0][2] +" | \n"
             + "    ---   ---   --- \n"
             + "2  | "+ board[1][0] +" | | "+ board[1][1] +" | | "+ board[1][2] +" | \n"
             + "    ---   ---   --- \n"
             + "3  | "+ board[2][0] +" | | "+ board[2][1] +" | | "+ board[2][2] +" |";
    }
    
}
