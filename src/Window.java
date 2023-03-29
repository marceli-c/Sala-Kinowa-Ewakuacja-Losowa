import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.Timer;

public class Window extends JFrame implements ActionListener{



    Coordinates[] coordinate;
    int direction=3,direction2=3;
    int[][] board;
    Rectangles[][] recArr;
    Scanner skaner;
    int boardSize;
    int scale;
    Timer timer;
    int numberOfAnts;
    recDraw recDraw;
    Mesh mesh;
    // STABILNA POZYCJA - {17,18} {20,22}

    Window(int windowSize, int boardSize){
        this.scale=windowSize/boardSize;

        Board board=new Board(100);
        this.board=board.getBoard();

        System.out.println("Podaj liczbe mrowek");
        skaner=new Scanner(System.in);
        //int numberOfAnts=skaner.nextInt();            //JEZELI CHCEMY Z PALCA
        int numberOfAnts=101;
        this.numberOfAnts=numberOfAnts;
        coordinate=new Coordinates[numberOfAnts];
        //createAnts(numberOfAnts);
        createLevel();

        createWindow(windowSize);


        this.boardSize=boardSize;


        recArr=new Rectangles[boardSize][boardSize];



        callRecArr(board.getBoard());

        timerInitialize(32);

        recDraw=new recDraw(boardSize,board,recArr);
        mesh= new Mesh(scale);

        this.add(mesh);
        this.add(recDraw);

    }
    private void createAnts(int numberOfAnts) {
        Random random=new Random();
        //int x=0,y=0;
        for(int i=0;i<numberOfAnts;i++) {
            //System.out.println("Podaj x:");
           // x=skaner.nextInt();
            //System.out.println("Podaj y:");
            //y=skaner.nextInt();
            this.coordinate[i]=new Coordinates(random.nextInt(10,45),random.nextInt(10,45));
        }
    }





    private void createWindow(int Windowsize) {

        //this.setLocationRelativeTo(this.getParent());
        //this.setResizable(false);

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setSize(Windowsize,Windowsize);

        this.setVisible(true);

        mesh=new Mesh(scale);

        this.add(mesh,0);
        //System.out.println("BOUNDSSSSSSSSSSSSSSSSSSSSSSSSSS"+mesh.getBounds()+" "+mesh.isVisible()+" "+mesh.isOpaque());
        this.pack();
    }









    private void callRecArr(int[][] board) {
        int h=0;
        for(int i=0;i<boardSize;i++) {
            int k=0;
            for(int j=0;j<boardSize;j++) {
                k+=scale;
                recArr[i][j]=new Rectangles(this.getWidth()/boardSize,false);
                //recArr[i][j].setBounds(k+200, h+200, scale, scale);
                recArr[i][j].setRect(k, h, scale, scale);
            }
            h+=scale;
        }
        implementBoardState(board);

    }


    private void implementBoardState(int[][] board) {
        //int h=0;
        for(int i=0;i<boardSize;i++) {
            //int k=0;
            for(int j=0;j<boardSize;j++) {
                if(board[i][j]==1) recArr[i][j].setState(true);
                else recArr[i][j].setState(false);


            }
        }

    }

    public void showStates() {
        for(int i=0;i<boardSize;i++) {
            for(int j=0;j<boardSize;j++) {
                System.out.print(recArr[i][j].getState()+" ");
                System.out.print(recArr[i][j].getBounds()+" ");
            }
            System.out.println();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        implementBoardState(board);

        this.repaint();
        for(int i=0;i<numberOfAnts;i++) {
            move(coordinate[i],board);
        }

        recDraw.repaint();

    }

    private void timerInitialize(int delay) {
        timer=new Timer(delay,this);
        timer.start();

    }

    /*private static void moveLeft(Coordinates coordinates) {

        switch (coordinates.getDirection()) {
            case 1: coordinates.setDirecrtion(3);								// 1-GÓRA
                coordinates.changeCoordinates(-1,0);
                break;
            case 2: coordinates.setDirecrtion(4);								// 2-DÓL
                coordinates.changeCoordinates(1, 0);
                break;
            case 3: coordinates.setDirecrtion(2);								// 3-LEWO
                coordinates.changeCoordinates(0, 1);
                break;
            case 4: coordinates.setDirecrtion(1);								// 4-PRAWO
                coordinates.changeCoordinates(0,-1);
                break;
        }

    }
    private static void moveRight(Coordinates coordinates) {

        switch (coordinates.getDirection()) {
            case 1: coordinates.setDirecrtion(4);								// 1-GÓRA
                coordinates.changeCoordinates(1,0);
                break;
            case 2: coordinates.setDirecrtion(3);								// 2-DÓL
                coordinates.changeCoordinates(-1, 0);
                break;
            case 3: coordinates.setDirecrtion(1);								// 3-LEWO
                coordinates.changeCoordinates(0, -1);
                break;
            case 4: coordinates.setDirecrtion(2);								// 4-PRAWO
                coordinates.changeCoordinates(0,1);
                break;
        }

    }*/
    private static void moveRight(Coordinates coordinates){
        coordinates.changeCoordinates(1,0);
    }
    private static void moveLeft(Coordinates coordinates){
        coordinates.changeCoordinates(-1,0);
    }
    private static void moveDown(Coordinates coordinates){
        coordinates.changeCoordinates(0,-1);
    }
    private static void moveUp(Coordinates coordinates){
        coordinates.changeCoordinates(0,1);
    }

    private static void move(Coordinates coordinate, int[][] board) {

        int random=getRandom();                                 // 0-w lewo, 1-w prawo, 2-gora, 3-dol
        int retry=0;
        boolean cannotPass=true;
        /*if( coordinate.getCoordinates()[1][1]- 1 <0 ||
                coordinate.getCoordinates()[1][1]+ 1 >65 ||
                coordinate.getCoordinates()[0][0]- 1 <0 ||
                coordinate.getCoordinates()[0][0]+ 1 >65) {
            changeBoard(coordinate,board,1);
            return;
        }*/
        if(
                coordinate.getCoordinates()[0][0]- 1 <5 ||
                coordinate.getCoordinates()[0][0]+ 1 >33) {
            changeBoard(coordinate,board,1);
            return;
        }



        while(cannotPass) {
            if (
                    (random == 0 && board[(coordinate.getCoordinates()[0][0]) ][coordinate.getCoordinates()[1][1]- 1] == 1) ||
                    (random == 1 && board[(coordinate.getCoordinates()[0][0])][coordinate.getCoordinates()[1][1]+1] == 1) ||
                    (random == 2 && board[(coordinate.getCoordinates()[0][0]+1)][(coordinate.getCoordinates()[1][1]) ] == 1) ||
                    (random == 3 && board[(coordinate.getCoordinates()[0][0]-1)][(coordinate.getCoordinates()[1][1]) ] == 1)



            ) {
                random = getRandom();
                System.out.println("changing random "+random);

                if(retry++>15) return;
            }

            else {
                //System.out.println("hej" + random);
                cannotPass=false;
            }

        }

        changeBoard(coordinate,board,1);

        switch(random){
            case 0:moveLeft(coordinate);
            break;
            case 1:moveRight(coordinate);
            break;
            case 2:moveUp(coordinate);
            break;
            case 3:moveDown(coordinate);
        }
        changeBoard(coordinate,board,0);
    }
    private static int getRandom(){
        Random random=new Random();
        return random.nextInt(0,4);
    }

    private static void changeBoard(Coordinates coordinate,int[][] board, int value) {
        switch(value) {
            case 1:board[	coordinate.getCoordinates()[0][0] 	] [	coordinate.getCoordinates()[1][1]	] =0;
                break;
            case 0:board[	coordinate.getCoordinates()[0][0]	] [	coordinate.getCoordinates()[1][1]	] =1;
                break;
        }
    }
    private void createLevel(){
        createWalls();
        siedzenia(15,0);
        siedzenia(20,20);
        siedzenia(25,40);
        siedzenia(30,60);
        siedzenia(35,80);


        ekran(8);
        ekran(7);
        ekran(6);
    }
    private void siedzenia(int x, int k){
        for(int i=9;i<=29;i++){
            board[i][x]=1;
            this.coordinate[k]=new Coordinates(x-1,i);
            k++;
        }
    }
    private void ekran(int x){
        for(int i=9;i<=29;i++){
            board[i][x]=1;

        }
    }
    private void createWalls(){
        for(int i=5;i<=40;i++){

            board[5][i]=1;
            board[33][i]=1;

        }
        for(int i=5;i<=33;i++){
            board[i][40]=1;
            board[i][5]=1;
        }
        board[5][25]=0;
        board[5][24]=0;
        board[5][23]=0;
        board[5][22]=0;

        board[33][25]=0;
        board[33][24]=0;
        board[33][23]=0;
        board[33][22]=0;

    }

}
