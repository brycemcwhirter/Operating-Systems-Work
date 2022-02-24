package scratch;

import java.io.File;

public class JavaMonitor {

    static final int MAX_RESOURCES = 5;

    int available_resources = MAX_RESOURCES;


    //When a process wishes to obtain a number of resources, it invokes the decrease_count() function:
    /* decrease available_resources by count resources */
    /* return 0 if sufficient resources available, */
    /* otherwise return -1 */
    int decrease_count(int count) {
        if (available_resources < count)
            return -1;
        else {
            available_resources -= count;
            return 0;
        }
    }

    int increase_count(int count) {

        available_resources += count;

        return 0;
    }


}
