package prog3;

import java.util.*;

public class MemoryManager {

    static final char ADD = 'a';
    static final char FIRST_FIT = 'f';
    static final char BEST_FIT = 'b';
    static final char WORST_FIT = 'w';
    static final char DISPLAY = 'd';
    static final char QUIT = 'q';
    static final char FREE = 'f';

    static final String FREE_SPACE = "Free";

    static Integer MEMORY_SIZE;


    public static void main(String[] args) {


        if(args.length != 1){
            throw new IllegalArgumentException("Run As: java MemoryManager.java <sizeOfMemInBytes>");
        }

        MEMORY_SIZE = Integer.parseInt(args[0]);
        TreeMap<Integer, String> theMemory = new TreeMap<>();

        for(int i = 0; i < MEMORY_SIZE; i++){
            theMemory.put(i, FREE_SPACE);
        }


        char option;
        Scanner input = new Scanner(System.in);

        System.out.println("Memory Manager");
        System.out.println("What would you like to do?\n");
        System.out.println("Add an Item: a");
        System.out.println("Free an Item: f");
        System.out.println("Display Memory: d");
        System.out.println("Quit: q");

        try {

            do {
                System.out.print("=> ");
                option = input.next().charAt(0);


                switch (option) {
                    case ADD -> {
                        System.out.print("Item ID (int): ");
                        int id = input.nextInt();
                        System.out.print("Item Size (int): ");
                        int size = input.nextInt();
                        System.out.print("How to store?\nBest Fit: b\nFirst Fit: f\nWorst Fit: w\n=>");
                        char fitOption = input.next().charAt(0);
                        add(theMemory, id, size, fitOption);
                        break;

                    }
                    case DISPLAY -> {
                        display(theMemory);
                        break;
                    }
                    case FREE -> {
                        System.out.print("Item ID to free from Memory: ");
                        int id = input.nextInt();
                        String itemName = "item " + id;
                        theMemory = free(theMemory, itemName);
                        break;
                    }
                    default -> {
                        System.out.println("invalid option");
                    }
                }


            } while (option != QUIT);
        }
        catch (InputMismatchException e){
            System.out.println("Invalid value. Restarting Process");
        }

        System.out.println("Quitting. GoodBye");
    }









    /**
     * This method adds a new process to memory
     * @param theMemory the memory
     * @param id the id of the process to be added
     * @param size the size of the process
     * @param fitOption the option describing the fit option
     */
    private static void add(TreeMap<Integer, String> theMemory, int id, int size, char fitOption) {

        String itemName = "item "+id;


        switch(fitOption){
            case FIRST_FIT:{
                theMemory = firstFit(theMemory, size, itemName);
                break;
            }
            case BEST_FIT:{
                theMemory = bestFit(theMemory, size, itemName);
                break;
            }
            case WORST_FIT:{
                theMemory = worstFit(theMemory, size, itemName);
                break;
            }
            default:
                System.out.println("Invalid Fit Option");
        }
    }


    /**
     * This method returns the holes present in memory
     * @param theMemory the memory
     * @return A TreeMap that lists the starting index of holes & sizes
     */
    public static TreeMap<Integer, Integer> getHoles(TreeMap<Integer, String> theMemory){

        // Iterator for Each Memory Segment
        Iterator<Map.Entry<Integer, String>> iterator = theMemory.entrySet().iterator();


        // The Entry for each memory segment
        Map.Entry<Integer, String> entry;


        // A tracker for the current hole
        int holeTracker = 0;


        // The set of holes in memory
        TreeMap<Integer, Integer> holesInMemory = new TreeMap<>();


        do {

            entry = iterator.next();

            if(entry.getValue().equals(FREE_SPACE)){

                // Calculate the size of the hole
                holeTracker++;

            }

            // If hole tracker is zero,
            // then we're currently on
            // a process


            // If the hole tracker has a value
            // other than 0. Meaning, we have reached
            // the end of the hole in memory
            if(holeTracker != 0 && !entry.getValue().equals(FREE_SPACE)){

                // Placing the starting Index & the size of the hole
                holesInMemory.put(entry.getKey() - holeTracker, holeTracker);

                // Reset Hole Tracker
                holeTracker = 0;


            }


            // Case where the entire memory is
            // empty.
            if(holeTracker == theMemory.size()){
                holesInMemory.put(0, theMemory.size());
                break;
            }


            // Case where we are currently iterating
            // on the last memory spot and we have a hole
            // we are on
            if(holeTracker != 0 && !iterator.hasNext()){
                holesInMemory.put(entry.getKey() - holeTracker + 1, holeTracker);
                break;
            }


        }while(iterator.hasNext());


        return holesInMemory;
    }


















    static TreeMap<Integer, String> firstFit(TreeMap<Integer, String> theMemory, int processSize, String itemName) {

        TreeMap<Integer, Integer> holesInMemory = getHoles(theMemory);

        if(processSize < MEMORY_SIZE && 0 != holesInMemory.size()){

            // Get the first entry in holes
            Map.Entry<Integer, Integer> startingEntryPoint = holesInMemory.firstEntry();
            int startingNDX = startingEntryPoint.getKey();


            // place the process there
            for(int i = startingNDX; i < processSize + startingNDX; i++){
                theMemory.put(i, itemName);
            }

        }
        else{
            System.out.println("Process doesn't fit in memory");
        }





        return theMemory;
    }
















    static TreeMap<Integer, String> bestFit(TreeMap<Integer, String> theMemory, int processSize, String itemName){

        TreeMap<Integer, Integer> holesInMemory = getHoles(theMemory);


        if(holesInMemory.size() != 0) {

            // Get the smallest entry
            Integer startingIndex = Collections.min(holesInMemory.entrySet(), Map.Entry.comparingByValue()).getKey();
            Integer endingIndex = holesInMemory.get(startingIndex) + startingIndex;

            // TODO make sure this works properly
            // Place the process in the memory with the starting segment
            for(int i = startingIndex; i < endingIndex; i++){
                theMemory.put(startingIndex, itemName);

                startingIndex++;
            }
       }



        // The process doesn't fit if the number of fitting holes
        // is zero.
        else {
            System.out.println("Process doesn't fit in memory");
        }


        return theMemory;
    }










    static TreeMap<Integer, String> worstFit(TreeMap<Integer, String> theMemory, int processSize, String itemName){

        TreeMap<Integer, Integer> holesInMemory = getHoles(theMemory);

        // If there is a place for the process
        if(holesInMemory.size() != 0) {

            // Get the largest entry
            Integer key = Collections.max(holesInMemory.entrySet(), Map.Entry.comparingByValue()).getKey();
            Integer size = holesInMemory.get(key) + key;

            // TODO make sure this works properly
            // Place the process in the memory with the starting segment
            for(int i = key; i < size + 1; i++){
                theMemory.put(key, itemName);
                key++;
            }

            // TODO reset memory method on success?


        }


        // The process doesn't fit if the number of fitting holes
        // is zero.
        else {
            System.out.println("Process doesn't fit in memory");
        }



        return theMemory;
    }







    /**
     * Frees a process from memory
     * @param theMemory the memory
     * @param itemName the name of the process to be freed
     * @return the memory with the new updates
     */
    private static TreeMap<Integer, String> free(TreeMap<Integer, String> theMemory, String itemName) {



        Iterator<Map.Entry<Integer, String>> iterator = theMemory.entrySet().iterator();
        boolean inMem = false;


        // Find Every Key Referencing the itemName
        while(iterator.hasNext()){
            Map.Entry<Integer, String> entry = iterator.next();

            // If you find that process
            // set the process to a free space
            if(entry.getValue().equals(itemName)){
                entry.setValue(FREE_SPACE);
                inMem = true;
            }
        }


        // If the process was not found in memory
        if(!inMem){
            System.out.println("Process Not In Memory");
        }

        return theMemory;
    }










    /**
     * Displays the memory currently
     * @param theMemory the memory
     */
    private static void display(TreeMap<Integer, String> theMemory) {


        // If the First Spot is a free memory spot

        int sizeCal = 0;
        int previousMemSpot = 1;
        Iterator<Map.Entry<Integer, String>> iterator = theMemory.entrySet().iterator();
        Map.Entry<Integer, String> previousEntry = iterator.next();
        Map.Entry<Integer, String> entry;



        // While You have memory slots
        while (iterator.hasNext()){

            entry = iterator.next();

            // If the next memory slot
            // is the same as previous
            if(previousEntry.getValue() == entry.getValue()) {
                // Keep Counter of That spot
                sizeCal++;

            }
            else {

                // Print previousMemSpot '-' (sizeCal - 1)  itemName
                printMemBlock(previousMemSpot, sizeCal + previousMemSpot, previousEntry);

                // Switch isFree
                previousMemSpot = sizeCal +previousMemSpot + 1;
                sizeCal = 0;
            }

            previousEntry = entry;

        }

        printMemBlock(previousMemSpot, sizeCal + previousMemSpot, previousEntry);



    }








    /**
     * This Method prints a Memory Block Segment
     * @param previousMemSpot the previous memory spot
     * @param currentMemSpot the current memory spot
     * @param previousEntry the previous entry of the memory
     */
    private static void printMemBlock(int previousMemSpot, int currentMemSpot, Map.Entry<Integer, String> previousEntry) {

        if(previousMemSpot == currentMemSpot){
            System.out.println(previousMemSpot + " " + previousEntry.getValue());

        }
        else{
            System.out.println(previousMemSpot + "-" + currentMemSpot + " " + previousEntry.getValue());

        }


    }


}
