package TelephoneBanking;

public class DoublyLinkedList
{
    public CustomerRequest data;
    public DoublyLinkedList previous;
    public DoublyLinkedList next;

    //Constructor
    public DoublyLinkedList(CustomerRequest value)
    {
        data = value;
        previous = null;
        next = null;
    }

}
