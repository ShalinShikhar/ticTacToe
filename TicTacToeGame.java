import model.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {

    Deque<Player>players;
    Board gameBoard;
    Player winner;


    public void initializeGame()
    {
        players=new LinkedList<>();
        PlayingPieceX crossPeice=new PlayingPieceX();
        Player player1=new Player("Player1",crossPeice);
        PlayingPieceO noughtsPiece=new PlayingPieceO();
        Player player2=new Player("Player2",noughtsPiece);

        players.add(player1);
        players.add(player2);

        gameBoard=new Board(3);

    }
    public GameStatus startGame()
    {
        boolean noWinner=true;
        while(noWinner)
        {
            Player currentPlayer=players.removeFirst();
            gameBoard.printBoard();
            List<Pair> freeSpaces=gameBoard.getFreeCells();

            if(freeSpaces.isEmpty())
            {
                noWinner=false;
                continue;
            }
            System.out.println("Player : "+currentPlayer+"- Please enter [row , columnn");
            Scanner inputScanner=new Scanner(System.in);
            String s=inputScanner.nextLine();
            String[] values=s.split(",");
            int inputRow=Integer.valueOf(values[0]);
            int inputCol=Integer.valueOf(values[1]);

            boolean validMove=gameBoard.addPiece(inputRow,inputCol,currentPlayer.playingPiece);
            if(!validMove)
            {
                System.out.println("Incorrect position chosen,try again");
                players.addFirst(currentPlayer);
                continue;
            }
            players.addLast(currentPlayer);

            boolean isWinner=checkForWinner(inputRow,inputCol,currentPlayer.playingPiece.pieceType);
            if(isWinner)
            {
                gameBoard.printBoard();
                winner=currentPlayer;
                return GameStatus.WIN;
            }
        }
        return GameStatus.DRAW;
    }

    public boolean checkForWinner(int row,int col,PieceType pieceType)
    {
       boolean rowMatch=false;
       boolean colMatch=false;
       boolean diagonalMatch=false;
       boolean antiDiagonalMatch=false;

       for(int i=0;i<gameBoard.size;i++)
       {
           if(gameBoard.board[row][i]==null || gameBoard.board[row][i].pieceType!=pieceType)
           {
               rowMatch=false;
               break;
           }
       }
       for(int i=0;i<gameBoard.size;i++)
       {
           if(gameBoard.board[i][col]==null || gameBoard.board[i][col].pieceType!=pieceType)
           {
               colMatch=false;
               break;
           }
       }

        // Check Diagonally
        for (int i = 0, j = 0; i < gameBoard.size; i++, j++) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                diagonalMatch = false;
                break;
            }
        }

        // Check Anti-Diagonally
        for (int i = 0, j = gameBoard.size - 1; i < gameBoard.size; i++, j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                antiDiagonalMatch = false;
                break;
            }
        }
        return rowMatch || colMatch || diagonalMatch || antiDiagonalMatch;

    }
}
