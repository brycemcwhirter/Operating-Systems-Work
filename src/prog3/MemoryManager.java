package prog3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemoryManager {

    static final char ADD = 'a';
    static final char FIRST_FIT = 'f';
    static final char BEST_FIT = 'b';
    static final char WORST_FIT = 'w';
    static final char DISPLAY = 'd';
    static final char QUIT = 'q';
    static final char FREE = 'F';

    public static void main(String[] args) throws IOException {


        if(args.length != 1){
            throw new IllegalArgumentException("Run As: java MemoryManager.java <sizeOfMemInBytess");
        }

        int memorySize = Integer.parseInt(args[0]);
        char option;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        do{
            option = (char) reader.read();

            switch (option){
                case ADD: add(memorySize);
                case DISPLAY: display(memorySize);
                case FREE: free(memorySize);
            }


        }while(option != QUIT);
    }

    private static void free(int memorySize) {
    }

    private static void display(int memorySize) {
    }

    private static void add(int memorySize) {

        //
    }
}
