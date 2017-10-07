package com.sjsu.vector;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * CS249 VectorClock Program
 * A skeleton code was provided on which we built-upon
 * Performs all the processor related tasks
 * @author Rashmeet Khanuja, Anusha Vijay, Steven Yen
 * @version 1.0
 *
 */
public class Processor implements Observer {
    Buffer messageBuffer ;
    Integer id ;
    VectorClock vc ; //This is the current vector clock
    ArrayList<int[]> store;

    /**
     * Initializes the processor with id, children and unexplored lists. Adds himself in the observers list.
     * @param id of the processor
     */
    public Processor(int id, int totalProcesors) {
        messageBuffer = new Buffer();
        this.id = id;
        messageBuffer.addObserver(this);
        vc=new VectorClock(totalProcesors);
        store = new ArrayList<int[]>();
    }

    public void getStore(int index) {
        System.out.println(store.get(index)[0]+" "+store.get(index)[1]);
    }

    public void printStore() {
        System.out.print("[");
        for (int i=0;i<this.store.size();i++){
            int[] item = this.store.get(i);
            System.out.print(item[0]);
            System.out.print(" ");
            System.out.print(item[1]);
            System.out.print(",\t");
        }
        System.out.print("]");
    }

    /**
     * Getter for the id of this process
     * @return process id as integer
     */
    public Integer getId(){
        return id;
    }
    /**
     * Overlaoding of above method we added that has sender param
     * @param message
     * @param sender the processor that send message to my (this processor's) buffer
     */
    public void sendMessageToMyBuffer(Message message, Processor sender) throws InterruptedException {
        System.out.printf("P%d send %s message to P%d %n",sender.id, message.messageType, this.id);
        this.messageBuffer.setMessage(message,sender);
    }

    /**
     * Gets called when a node receives a message in its buffer
     * Processes the message received in the buffer
     */
    synchronized public void update(Observable observable, Object arg) {
        try {
            Message text = messageBuffer.getMessage();
            calculateVectorClocks(text);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void calculateVectorClocks(Message recievedMessage) throws InterruptedException {

        this.vc.incrementAt(id);
        if(recievedMessage.messageType==MessageType.RECIEVE){
	        for(int i=0; i< this.vc.vc.length; i++){
	        	if(i != this.getId()){
	        		if(this.vc.vc[i]<recievedMessage.vc.vc[i]){
	        			this.vc.updateAt(i,recievedMessage.vc.vc[i]);
	        		}
	        	}
	        }
        }
        System.out.printf("  VC of P%d updated to: ", this.getId());
        this.store.add(this.vc.vc.clone());
        this.vc.printVC();
    }

    public void calculateMaximalCut(int[] k){
        System.out.print("Starting access from:");
        this.getStore(k[this.id]);
    }

}