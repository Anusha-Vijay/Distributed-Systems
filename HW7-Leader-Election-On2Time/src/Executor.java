/**
 * Leader Election in Asyn Ring O(n^2) Algorithm
 * CS 249 Team #2 Rashmeet Khanuja, Anusha Vijay, Steven Yen
 */

public class Executor implements Runnable{

    Processor proc;

    /**
     * Constructor for executor
     * @param proc Processor this executor corresponds to.
     */
    public Executor(Processor proc){
        this.proc = proc;
    }

    /**
     * First step for each Processor is to send its identifier to
     * the left neighbor. After it waits to receive message from the
     * right neighbor (this 2nd part is done by the observer-observable mechanism).
     */
    @Override
    public void run() {
        proc.sendMyIdToLeftNeighbor();
    }

}

