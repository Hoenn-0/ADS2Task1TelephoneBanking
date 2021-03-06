package TelephoneBanking;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The telephone Banking System is an assignment for Algorithms and Data Structure 2
 * @author Jing Wang @ SHU
 * @version  1.1
 */

public class Main {
    //State Machine parameters
    public enum State{START, STOP, WELCOME, LIST_ALL, PUSH, POP, TASK, REMOVE, NEW, SAVE, WITHDRAW, DISPLAY}
    public static State currentState = State.START;

    //Global variables
    public static Scanner inputOrder = new Scanner(System.in);
    public static Scanner inputID = new Scanner(System.in);
    public static CustomerQueue queue;
    public static CustomerData userData;
    public static CustomerRequest currentRequest = new CustomerRequest();

    public static void main(String[] args) throws FileNotFoundException {
        while (currentState != State.STOP){
            switch (currentState){
                default: break;
                case START:     state_start();
                    break;
                case WELCOME:   state_welcome();
                    break;
                case LIST_ALL:  state_Q_listAll();
                    break;
                case PUSH:      state_Q_push();
                    break;
                case POP:       state_Q_pop();
                    break;
                case TASK:      state_task();
                    break;
                case REMOVE:    state_H_remove();
                    break;
                case NEW:       state_H_new();
                    break;
                case SAVE:      state_H_saveMoney();
                    break;
                case WITHDRAW:  state_H_reduceMoney();
                    break;
                case DISPLAY:   state_H_display();
                    break;
            }
        }

        clear();
    }

    private static void state_start() throws FileNotFoundException {
        System.out.println("System Initialization...");
        System.out.println("Load customer database...");
        userData = new CustomerData();
        System.out.println("Initializing queue...");
        queue = new CustomerQueue();
        currentState = State.WELCOME;
    }

    private static void state_welcome() {
        System.out.println("\n\n\n==Telephone Banking Control Centre==");
        System.out.println("""
                Choose the index number from following options:\s
                1. Get the next customer
                2. Queueing a new customer
                3. Check current queue
                4. Deposit
                5. Withdraw
                6. Display Hash
                7. Quit""");
        if (inputOrder.hasNext("1")) currentState = State.POP;
        if (inputOrder.hasNext("2")) currentState = State.PUSH;
        if (inputOrder.hasNext("3")) currentState = State.LIST_ALL;
        if (inputOrder.hasNext("4")) currentState = State.SAVE;
        if (inputOrder.hasNext("5")) currentState = State.WITHDRAW;
        if (inputOrder.hasNext("6")) currentState = State.DISPLAY;
        if (inputOrder.hasNext("7")) currentState = State.STOP;
        inputOrder.next();
    }

    private static void state_Q_listAll()
    {
        if(queue.CheckIfEmpty())
            System.err.println("The Customer Que is empty!");
        else
            queue.ListAll();
        currentState = State.WELCOME;
    }

    private static void state_Q_push() {
        CustomerRequest newRequest = new CustomerRequest();
        newRequest.newRequest();
        //newRequest.newRequest();//replace this statement by your Push function
        queue.Push(newRequest);
        System.out.println("A new customer has entered the queue");

        currentState = State.WELCOME;
    }

    private static void state_Q_pop() {
        if(queue.CheckIfEmpty()){
            System.err.println("The Customer Que is empty!");
            currentState = State.WELCOME;
        }
        else {
            currentRequest = queue.Pop();
            currentState = State.TASK;
        }
    }

    private static void state_task() {
        System.out.print("This customer wants to ");
        switch (currentRequest.request){
            default: break;

            case 0: System.out.println("open a new account");
                System.out.println("Please input a new account ID:");
                currentRequest.id = inputID.nextLine();
                currentState = State.NEW;
                break;
            case 1: System.out.println("close the account");
                System.out.println("Please input the account ID:");
                currentRequest.id = inputID.nextLine();
                currentState = State.REMOVE;
                break;
            case 2: System.out.println("check balance");
                System.out.println("Please input the account ID:");
                currentRequest.id = inputID.nextLine();
                currentState = State.DISPLAY;
                break;
            case 3: System.out.println("save ??"+currentRequest.amountToChange);
                System.out.println("Please input the account ID:");
                currentRequest.id = inputID.nextLine();
                currentState = State.SAVE;
                break;
            case 4: System.out.println("withdraw ??"+currentRequest.amountToChange);
                System.out.println("Please input the account ID:");
                currentRequest.id = inputID.nextLine();
                currentState = State.WITHDRAW;
                break;

        }
    }

    private static void state_H_remove() {
        userData.DeleteData(currentRequest.id);
        System.out.println("The account has been successfully closed.");
        currentState = State.WELCOME;
    }

    private static void state_H_new() {
        while (userData.CheckIfValid(currentRequest.id)) {
            System.out.println("This ID has been used. Please input another one");
            currentRequest.id = inputID.nextLine();
        }
        userData.SetData(currentRequest.id, currentRequest.amountToChange);
        System.out.println("A new account has been created.");
        currentState = State.WELCOME;
    }

    private static void state_H_saveMoney() {
        userData.Update(currentRequest.id, currentRequest.amountToChange);
        System.out.println("The money has been saved.");
        currentState =  State.DISPLAY;
    }

    private static void state_H_reduceMoney() {
        userData.Update(currentRequest.id, currentRequest.amountToChange*(-1));
        System.out.println("The money has been withdrawed.");
        currentState = State.DISPLAY;

    }

    private static void state_H_display() {
        userData.Display(currentRequest.id);
        currentState = State.WELCOME;
    }

    private static void clear() {
        System.out.println("Clearing data...");
        //Deleted by the Garbage collector.
        queue = null;
        userData = null;
        System.out.println("===See you next time===");
    }

}
