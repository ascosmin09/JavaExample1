/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trainstation;

/**
 *
 * @author ascos
 */
public class PassengerQueue {

    private Passenger[] queueArray= new Passenger[TrainStation.WAITING_ROOM_CAPACITY];
    private int first=0;
    private int last=0;
    private int maxLength=0;
    
    public void add(Passenger next){ // with this method i add a passenger to the queue
        if(this.isFull()==false)
            {
                queueArray[last]=next;
            last++;
            }
        else System.out.println("The queue is full");
        if(getLength()>maxLength) maxLength=getLength();
    }
    
    public void remove(){ //method to remove a passenger from the queue and keep it circular, all other element move a position back;
        if(this.isEmpty()==false){
        System.out.println("Removed from the queue: "+ queueArray[first].getName());
        for(int i=0;i<last;i++){
            queueArray[i]=queueArray[i+1];
        } last--;
        }
        else {System.out.println("The queue is empty");}
    }
    
    public void display(){ // checking if the queue is empty, if not i display it
        if(this.isEmpty()==true)System.out.println("The queue is empty");
        else{
            for(int i=first;i<last;i++)
        {
            queueArray[i].display();
        }
        }
        
    }
    
    public String writeInFile(int first, int last){ //writing in file returning to the buffer writer all the details in the queue
        if(first<last) return queueArray[first].getName()+" "+queueArray[first].getSeconds()+"\n"+writeInFile(first+1, last); //Isn't this just a beautiful line of code using recursivity?
        else return " ";
    }
    
    public int first(){//returns first element of the queue
        return first;
    }
    
    public int last(){//return last element of the queue
        return last;
    }
    
    public boolean isEmpty(){ //checks if the queue is empty
        return first==last;
    }
    
    public boolean isFull(){ // checks if the queue is full
        int j=0;
        for(int i=0;i<queueArray.length;i++)
            if (queueArray[i]!= null) j++;
        return j==queueArray.length;
    }
    
    public int getLength(){ // gets the length of the queue
        return last-first;
    }
    
    public int getMaxStay(){//returns the time in the queue for the last element of the queue only, when all the others were removed. the last element will always have spent the maximum time in the queue//
        if(first==last-1) return queueArray[first].getSeconds();
        else return 0;
    }
    
    public int getStayInQueue(){//returns the current time spent in queue by a passenger
        return queueArray[first].getSeconds();
    }
    
    public void addDelay(int delay){ //iterrates through all elements and adds the delay to the time spent in queue
        int a=first, b=last;
        while(a<b){
            int secondsInQueue=queueArray[a].getSeconds();
            queueArray[a].setSecondsInQueue(secondsInQueue+delay);
            a++;
        }
    }
    public void resetQueue(){ //resets the queue
        first=0;
        last=0;
        maxLength=0;
    }
}