/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trainstation;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ascos
 */
public class TrainStation {
    
    static int WAITING_ROOM_CAPACITY=30,passengerIndex=0;
    private static Passenger[] waitingRoom= new Passenger[WAITING_ROOM_CAPACITY];
    private static PassengerQueue trainQueue= new PassengerQueue();
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        String x; //the variable where I save the chosen option
        displayMenu(); //displaying the menu for the user
        x= input.next(); //saving the selected option
        while(x.equals("Q")==false) //looping until user inputs "Q"
        {   if("A".equals(x)){ try { //if user selected to add somebody to the waiting list, i load passengers.dat into waitingRoom (so is no need to introduce manually for testing) and it will add passenger waitingRoom[passengerIndex(which is 0 and it increments)]//
            read(waitingRoom);
            } catch (IOException ex) {
                Logger.getLogger(TrainStation.class.getName()).log(Level.SEVERE, null, ex);
            }
                addPassengerToQueue(waitingRoom[passengerIndex]);
                passengerIndex++;}
        
            if("V".equals(x)) displayQueue(); // if passenger selects "V" will view the queue//
            if("D".equals(x)) removePassengerFromQueue(); // "D" will call the coresponding method and remove a passenger from the queue//
            if("S".equals(x)) try { //will print the name of the passenger and current amount of seconds spent in queue in a file name saves.txt"
                write(trainQueue);
        } catch (IOException ex) {
            Logger.getLogger(TrainStation.class.getName()).log(Level.SEVERE, null, ex);
        }
            if("L".equals(x)) try { // will load the name of the passenger and the seconds spent in a queue in the queue array//
                read(trainQueue);
        } catch (IOException ex) {
            Logger.getLogger(TrainStation.class.getName()).log(Level.SEVERE, null, ex);
        }
            if("R".equals(x)) runSimulation(waitingRoom,trainQueue);//running the simulation and printing the data it was asked for, as well as printing it into a file called report.dat//
            displayMenu();    
            x= input.next(); //reading the next letter that the user inputs
            }
            
        
        }
        
    
        private static void displayMenu(){ //simplet method to display the menu 
            System.out.println("Please enter one of the following options: \n"
                    +"‘A’ to add a passenger to the trainQueue \n"+ 
                    "‘V’ to view the trainQueue \n"+
                    "‘D’: Delete passenger from the trainQueue \n"+
                    "‘S’: Store trainQueue data into a plain text file \n"+
                    "‘L’: Load data back from the file into the trainQueue \n"+
                    "‘R’ : Run the simulation and produce report \n"+
                    "'Q' : Quit the program");
        }
        
        private static void addPassengerToQueue(Passenger passenger){ //method for option "a"//
            trainQueue.add(passenger);
        }
        
        private static void removePassengerFromQueue(){ //method for option "D"//
            trainQueue.remove();
        }
        
        private static void displayQueue(){ //method for option "V"//
            trainQueue.display();
        }
        
        private static void write(PassengerQueue trainQueue)throws IOException { //method used for option "S", which prints the current queue in the file saves.txt"//
            try { 
                BufferedWriter writer = new BufferedWriter(new FileWriter("saves.txt"));
                if(trainQueue.isEmpty()==false)
                    writer.write(trainQueue.writeInFile(trainQueue.first(), trainQueue.last()));
                writer.flush();
                writer.close();
                }
            catch (IOException e) {System.out.println("Error IOException is: " + e);}
        }
        
        private static void read(PassengerQueue trainQueue) throws IOException { //method used for option "L", which loads the queue info from the file load.txt//
            try { Scanner rf = new Scanner(new BufferedReader(new FileReader("load.txt")));
            String firstName, surname;
            int secondsInQueue;
            while ( rf.hasNext()) {
                firstName= rf.next();
                surname=rf.next();
                secondsInQueue=Integer.valueOf(rf.next());
                Passenger p= new Passenger(firstName, surname);
                p.setSecondsInQueue(secondsInQueue);
                trainQueue.add(p);
            }
            rf.close();
            } 
            catch (IOException e) {
            System.out.println("Error IOException is: " + e);
            }
        }
        
        private static void read(Passenger[] waitingRoom) throws IOException { // i use this method for option "A". Instead of creating new instances of passengers to see if options "A, V, D, S, L" work, i just populate  waitingRoom with the data given//
            int lineCount=0;
            try { Scanner rf = new Scanner(new BufferedReader(new FileReader("passengers.dat")));
            String firstName, surname;
            while ( rf.hasNext()) {
                firstName= rf.next();
                surname=rf.next();
                waitingRoom[lineCount]= new Passenger(firstName, surname);
                lineCount++;
            }
            rf.close();
            } 
            catch (IOException e) {
            System.out.println("Error IOException is: " + e);
            }
        }
        
        private static int readForSimulation(Passenger[] waitingRoom) throws IOException {//method used as a part of option "R", with this method i read the content of passengers.dat and count the passengers that i will have in the waiting room//
            int lineCount=0;
            try { Scanner rf = new Scanner(new BufferedReader(new FileReader("passengers.dat")));
            String firstName, surname;
            while ( rf.hasNext()) {
                firstName= rf.next();
                surname=rf.next();
                waitingRoom[lineCount]= new Passenger(firstName, surname);
                lineCount++;
            }
            
            rf.close();
            } 
            catch (IOException e) {
            System.out.println("Error IOException is: " + e);
            }
            return lineCount;
        }
        
        private static int rollDice(int nrOfDices){//simple method to generate a random side of a dice. I use this method inside the method runSimulation(Option "R")//
            int sides=6,total=0;
            while(nrOfDices>0){
                int roll=(int)(Math.random()*sides)+1;
                total+=roll;
                nrOfDices--;}
            return total;
        }
        
        private static int min(int[] array){// simple method to take the smallest value out of an array. I use this method inside the method runSimulation(Option "R"),i.e when i need to determine the minimum stay in the queue//
            int i,j;
            j=array[0];
            for(i=1;i<array.length;i++)
                if(j>array[i]&&array[i]!=0)j=array[i];//array[i]!=0 because for what i use this method the elements shouldn't be 0, I declared my array of size 30 because I don't know how many queues i'll have, minimum is WAITING_ROOM_CAPACITY divided by 6 and maximum is 30// 
            return j;
        }
        
        private static int max(int[] array){
            int i,j;
            j=array[0];
            for(i=1;i<array.length;i++)
                if(j<array[i]&&array[i]!=0)j=array[i];//array[i]!=0 because for what i use this method the elements shouldn't be 0, I declared my array of size 30 because I don't know how many queues i'll have, minimum is WAITING_ROOM_CAPACITY divided by 6 and maximum is 30// 
            return j;
        }
        
        private static double average(int[] array){ // simple average method, i use it inside method runSimulation, option "R"
            int i;
            int sum=0;
            for(i=0;i<array.length;i++)
                sum+=array[i];
            return (double)(sum/array.length);
        }
        
        private static void write(Passenger[] waitingRoom,int numberOfPassengers,int[] maximumIndividualStayInQueue, int length, int maximum, int minimum, double average)throws IOException { //method used for option "R", which prints the passenger info and the report into report.dat"
            try { 
                BufferedWriter writer = new BufferedWriter(new FileWriter("report.dat"));
                for(int i=0;i<numberOfPassengers;i++)
                    writer.write(waitingRoom[i].displayForSimulation()+"spent "+maximumIndividualStayInQueue[i]+" seconds in queue.\n");
                writer.write("\n");
                writer.write("The maximum lenght of the queue was: "+length+".\n");
                writer.write("The maximum time spent by a person in the queue was: "+maximum+".\n");
                writer.write("The minimum time spent by a person in the queue was: "+minimum+".\n");
                writer.write("The average time spent by a person in the queue was: "+average+".\n");
                writer.flush();
                writer.close();
                }
            catch (IOException e) {System.out.println("Error IOException is: " + e);}
        }
        
        private static void runSimulation(Passenger[] waitingRoom, PassengerQueue trainQueue){ //this is the method for option "R"//
            trainQueue.resetQueue(); //reseting the queue for the simulation
            int nrOfPassengersInTheWaitingRoom=0; //variable to store the number of the passengers in the waiting room//
            try {
                nrOfPassengersInTheWaitingRoom=readForSimulation(waitingRoom); //populating the waiting room and counting the passengers//
            } catch (IOException ex) {
                Logger.getLogger(TrainStation.class.getName()).log(Level.SEVERE, null, ex);
            }
            int i=0, batchCounter=0,nrOfPassengersMovedSoFar=0,counter, personCounter=0,indexOfTheFirstPassengerOfTheBatch=0;
            int[] maximumIndividualStayInQueue= new int[nrOfPassengersInTheWaitingRoom]; //declaring the array where i will store the waiting time before bording the train of each passenger//
            int[] minimumStayInQueue= new int[nrOfPassengersInTheWaitingRoom]; //declaring the array where i will store the minimum stay in queue for each batch//
            int[] maximumStayInQueue= new int[nrOfPassengersInTheWaitingRoom];// same as above only that in this array i store the maximum stay in queue//
            int[] maximumLength= new int[nrOfPassengersInTheWaitingRoom];// same as above only this time i store the lenghs of each batch/queue//
            
            while (i!=-1){ // "i" is my counter/index for the passengers in waitingRoom and as well is my boolean/killer switch for the simulation
                int nrOfPassengerToBeMoved=rollDice(1); //generating the number of passengers to be moved//
                
                // below i will check if i have enough passengers in the waiting room that i need to move , in other words, if the random number generated by the dice is not bigger than the number of the passengers that are left to board the train. If it is, i ignore that number and update it to the number of remaining passengers//
                if(nrOfPassengersInTheWaitingRoom-nrOfPassengersMovedSoFar>nrOfPassengerToBeMoved){
                    counter=nrOfPassengerToBeMoved;
                    nrOfPassengersMovedSoFar+=nrOfPassengerToBeMoved;
                }
                else {
                    counter=nrOfPassengersInTheWaitingRoom-nrOfPassengersMovedSoFar;
                    nrOfPassengersMovedSoFar=nrOfPassengersInTheWaitingRoom;
                }
                
                //moving the passengers to the queue//
                while(counter>0){
                        trainQueue.add(waitingRoom[i]);
                        i++;
                        counter--;
                }
                
                //boarding each batch of passengers and gather the required data//
                while(trainQueue.isEmpty()==false){
                    int delay=rollDice(3); //generating the delay//
                    trainQueue.addDelay(delay); //adding the delay to each passenger//
                    if(counter==0)minimumStayInQueue[batchCounter]=trainQueue.getStayInQueue(); //getting the time spent in queue for the first passenger("counter" is always 0 for the first one) for this batch (it will always the the smallest ammount of time) and storing it into an array that will contain the minimum times for each batch)//
                    maximumStayInQueue[batchCounter]=trainQueue.getMaxStay();//storing the maximum time spent by last passenger of each batch (it will always be the maximum time of the batch)//
                    if(counter==0)maximumLength[batchCounter]=trainQueue.getLength();//storing the length of each queue before removing any passengers//
                    counter++; //increasing the counter to make sure i save the time spent in the queue for the first passenger of this batch only//
                    maximumIndividualStayInQueue[personCounter]=trainQueue.getStayInQueue(); //saving the time spent in queue by each passenger before boarding//
                    personCounter++; 
                    trainQueue.remove(); //removing a passenger//
                }
                batchCounter++; //increasing the counter for the next batch//
                //indexOfTheFirstPassengerOfTheBatch+=nrOfPassengerToBeMoved; //updating the index for the first passenger of the next batch, to be able to save his boarding time// 
                //below i will check if i finished boarding all passengers, if i did i kill the while, in other words, if i had x persons in the waiting room and i moved x persons, than my job is finished //
                if(nrOfPassengersMovedSoFar==nrOfPassengersInTheWaitingRoom){
                    i=-1;
                }
            }   
                for(int k=0;k<nrOfPassengersInTheWaitingRoom;k++)
                System.out.println(waitingRoom[k].displayForSimulation()+"spent "+maximumIndividualStayInQueue[k]+" seconds in queue.\n");    
                System.out.println("The maximum lenght of the queue was: "+max(maximumLength));
                System.out.println("The maximum time spent by a person in the queue was: "+max(maximumStayInQueue));
                System.out.println("The minimum time spent by a person in the queue was: "+min(minimumStayInQueue));
                System.out.println("The average time spent by a person in the queue was: "+average(maximumIndividualStayInQueue));
        try {
            write(waitingRoom,nrOfPassengersInTheWaitingRoom,maximumIndividualStayInQueue,max(maximumLength),max(maximumStayInQueue),min(minimumStayInQueue),average(maximumIndividualStayInQueue) );
        } catch (IOException ex) {
            Logger.getLogger(TrainStation.class.getName()).log(Level.SEVERE, null, ex);
        }
        trainQueue.resetQueue();//reseting the queue when the simulation is finished to use it normally for "A V D S L" option//
        }
}
