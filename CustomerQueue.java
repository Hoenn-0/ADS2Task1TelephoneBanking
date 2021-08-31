package TelephoneBanking;

import java.text.DecimalFormat;

public class CustomerQueue {
    DecimalFormat df = new DecimalFormat("###.##");
    public TelephoneBanking.DoublyLinkedList front = null;
    public TelephoneBanking.DoublyLinkedList tail = null;
    // Default Constructor
    CustomerQueue() { }

    public void Push(TelephoneBanking.CustomerRequest NewRequest)
    {
        if (CheckIfEmpty()) // if queue is empty create a new DLL object (a customer queue)
        {
            front = new TelephoneBanking.DoublyLinkedList(NewRequest);
            tail  = front;
        }
        else //
        {
            TelephoneBanking.DoublyLinkedList newNode = new TelephoneBanking.DoublyLinkedList(NewRequest);
            tail.next = newNode;
            tail = newNode;
        }
    }

    public boolean CheckIfEmpty()
    {
        return front == null && tail == null; // return true if customer queue (DLL) is empty  else  return false
    }

    //Customer Queue is FIFO, thus returns the first request
    public TelephoneBanking.CustomerRequest Pop()
    {
        if (CheckIfEmpty())
        {
            System.out.println("The List is empty!");
            return null;
        }

        else if (front == tail) // get final customer in queue while nullifying its connections (resetting queue) and return its data.
        {
            TelephoneBanking.DoublyLinkedList Holder = front; // temporary holder so data is not lost during deletion and can be returned
            front = null;
            tail = null;
            return Holder.data;
        }

        else
        {
            TelephoneBanking.DoublyLinkedList Holder = front; // get the next customer in queue
            front = Holder.next;
            tail = Holder.previous;
            return Holder.data;
        }
    }

    void ListAll() // print all the queueing customers in FIFO order
    {
        if (CheckIfEmpty())
        {
            System.out.println("The List  is empty!");
        }
        else
        {
            TelephoneBanking.DoublyLinkedList Holder = front;
            int count = 1;

            while (Holder.next != null) // while queue is not empty print out a task/request
            {
                System.out.println("No." + count + " request:" + Holder.data.request + " Amount To Change: £" + df.format(Holder.data.amountToChange));
                count++;
                Holder=Holder.next;
            }
        }
    }




}
