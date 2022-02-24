package prog2;

import java.util.Date;
import java.util.Random;
import java.util.ArrayList;

public class ManagerMonitor {

    // Maximum time in between fan arrivals
    private static final int MAX_TIME_IN_BETWEEN_ARRIVALS = 3000;

    // Maximum amount of break time in between celebrity photos
    private static final int MAX_BREAK_TIME = 10000;

    // Maximum amount of time a fan spends in the exhibit
    private static final int MAX_EXHIBIT_TIME = 10000;

    // Minimum number of fans for a photo
    private static final int MIN_FANS = 3;

    // Maximum number of fans allowed in queue
    private static final int MAX_ALLOWED_IN_QUEUE = 10;

    // Holds the queue of fans
    private static ArrayList<Fan> line = new ArrayList<Fan>();

    // The current number of fans in line
    private static int numFansInLine = 0;

    // For generating random times
    private Random rndGen = new Random(new Date().getTime());


    /**
     * Celebrity Thread
     * Checks to see if there are enough fans in the
     * queue and takes pictures with fans.
     *
     * The Consumer
     */
    class Celebrity implements Runnable
    {
        @Override
        public void run() {
            while (true)
            {

                takePicutreWithFans();

            }

        }

    }



    public synchronized void takePicutreWithFans(){
        while(numFansInLine < MIN_FANS){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Check to see if celebrity flips out
        checkCelebrityOK();

        // Take picture with fans
        System.out.println("Celebrity takes a picture with fans");

        // Remove the fans from the line
        for (int i = 0; i < MIN_FANS; i++) {
            System.out.println(line.remove(0).getName() + ": OMG! Thank you!");
        }

        // Adjust the numFans variable
        numFansInLine-= MIN_FANS;

        // Take a break
        try {
            Thread.sleep(rndGen
                    .nextInt(MAX_BREAK_TIME));
        } catch (InterruptedException e) {
            System.err.println(e.toString());
            System.exit(1);
        }

        notify();


    }




    /**
     * Fan thread
     *
     * Generate fans to wait in the
     * queue for celebrity pictures
     *
     * The producer
     */
    class Fan implements Runnable
    {
        String name;

        public String getName()
        { return name;}

        @Override
        public void run() {
            // Set the thread name
            name = Thread.currentThread().toString();

            System.out.println(Thread.currentThread() + ": arrives");

            // Look in the exhibit for a little while
            try {
                Thread.sleep(rndGen.nextInt(MAX_EXHIBIT_TIME));
            } catch (InterruptedException e) {
                System.err.println(e.toString());
                System.exit(1);
            }

            // Get in line
            System.out.println(Thread.currentThread() + ": gets in line");
            insertAFan(this);


        }

    }




    public synchronized void insertAFan(Fan fan)  {

        while(numFansInLine > MAX_ALLOWED_IN_QUEUE){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        line.add(0, fan);
        numFansInLine++;



        notify();

    }






    public static void main(String[] args) {
        new ManagerMonitor().go();
    }




    private void go() {
        // Create the celebrity thread
        Celebrity c = new Celebrity();
        new Thread(c, "Celebrity").start();

        // Continually generate new fans
        int i = 0;
        while (true) {
            new Thread(new Fan(), "Fan " + i++).start();
            try {
                Thread.sleep(rndGen.nextInt(MAX_TIME_IN_BETWEEN_ARRIVALS));
            } catch (InterruptedException e) {
                System.err.println(e.toString());
                System.exit(1);
            }
        }

    }



    public void checkCelebrityOK()
    {
        if (numFansInLine > MAX_ALLOWED_IN_QUEUE)
        {
            System.err.println("Celebrity becomes claustrophobic and flips out");
            System.exit(1);
        }

        if (numFansInLine < MIN_FANS)
        {
            System.err.println("Celebrity becomes enraged that he was woken from nap for too few fans");
            System.exit(1);
        }
    }
}
