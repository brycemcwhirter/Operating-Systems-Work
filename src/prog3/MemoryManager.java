package prog3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MemoryManager {

    static final char ADD = 'a';
    static final char FIRST_FIT = 'f';
    static final char BEST_FIT = 'b';
    static final char WORST_FIT = 'w';
    static final char DISPLAY = 'd';
    static final char QUIT = 'q';
    static final char FREE = 'F';

    public static void main(String[] args) {


        if(args.length != 1){
            throw new IllegalArgumentException("Run As: java MemoryManager.java <sizeOfMemInBytes>");
        }

        int memorySize = Integer.parseInt(args[0]);
        char option;
        Scanner input = new Scanner(System.in);


        do{
            System.out.println("Memory Manager");
            System.out.println("What would you like to do?\n");
            System.out.println("Add an Item: a");
            System.out.println("Free an Item: f");
            System.out.println("Display Memory: d");
            System.out.println("Quit: q");
            System.out.print("=> ");
            option = input.next().charAt(0);



            switch (option){
                case ADD: {
                    System.out.print("Item ID (int): ");
                    int id = input.nextInt();
                    System.out.print("Item Size (int): ");
                    int size = input.nextInt();
                    System.out.print("How to store?\nBest Fit: b\nFirst Fit: f\nWorst Fit: w\n=>");
                    char fitOption = input.next().charAt(0);
                    add(memorySize,id, size, fitOption);
                    System.out.print("\n\n\n");

                }
                case DISPLAY: display(memorySize);
                case FREE: free(memorySize);
            }


        }while(option != QUIT);

        System.out.println("Quitting. GoodBye");
    }



    private static void add(int memorySize, int id, int size, char fitOption) {

        switch(fitOption){
            case FIRST_FIT:{

            }
            case BEST_FIT:{

            }
            case WORST_FIT:{

            }
        }
    }




    private static void free(int memorySize) {
        //
    }




    private static void display(int memorySize) {
        //
    }


}
