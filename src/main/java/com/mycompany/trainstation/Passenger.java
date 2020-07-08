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
public class Passenger {
private String firstName, surname;
private int secondsInQueue;
    
    Passenger(String firstName, String surname){ //contructor for passenger class//
        this.firstName= firstName;
        this.surname=surname;
        secondsInQueue=0;
        
    }
    public String getName(){ //returns the name of the passenger
        return firstName + " "+ surname;
    }
    
    public void setFirstName(String firstName){ //sets the first name 
        this.firstName= firstName;
    }
    
    public void setLastName(String surname){ //sets last name
        this.surname= surname;
    }
    
    public void setName(String firstName,String surname){ //sets first and last name at once
        setFirstName(firstName);
        setLastName(surname);
    }
    
    public void display(){ //dispalys the info about a desired passenger
        System.out.println(firstName+ " " + surname +" "+ secondsInQueue);
    }
    
    public String displayForSimulation(){ //returns the name for writing in file
        return(firstName+ " " + surname +" ");
    }
    
    public int getSeconds(){ //returns the seconds spent in  queue by a passenger
        return this.secondsInQueue;
    }
    
    public void setSecondsInQueue( int n){ //sets the seconds that a passenger spent in the queue
        this.secondsInQueue=n;
    }
}
