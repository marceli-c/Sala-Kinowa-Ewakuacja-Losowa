import java.util.Scanner;

public class Main{




    static Window okno;

    public static void main(String[] args) {



        System.out.println("POCZĄTEK");

        Scanner skaner=new Scanner(System.in);
        //System.out.println("Podaj rozmiar planszy");
        //int boardSize=skaner.nextInt();


        okno=new Window(1000,45);			// WIELKOŚĆ EKRANU, WIELKOŚĆ TABLICY -//-
        System.out.println("KONIEC");


    }
}