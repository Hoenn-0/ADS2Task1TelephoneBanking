package TelephoneBanking;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class CustomerData
{
    private TelephoneBanking.HashTable[] data; // Array of objects to store the data pairs (key, value)
    DecimalFormat decimalFormat = new DecimalFormat("###.##");

    private int Hash(String key) //hashing function for our index in the array
    {
        int returnValue = 0;

        if (key != null)
        {
            for (int i = 0; i < key.length(); i++)
            {
                returnValue += ((int) key.charAt(i)) * 911.69;
            }
            return returnValue;

        }

        else { return 0;}

    }

    //  Set container size/ store new HashObjects (100 capacity in our case)  in an array
    public void SetHashTableSize(int length)
    { data = new HashTable[length]; }

    CustomerData() throws FileNotFoundException
    {
        try (Scanner scanner = new Scanner(new File("ADS2_Task1_TelephoneBanking/src/BankUserDataset10K.csv"))) //ADS2_Task1_TelephoneBanking/src/BankUserDataset100.csv
        {

            //Initialise your members here. After this point, you should already
            SetHashTableSize(10000); // change with other csv file

            //have a ready-to-use "database" for user account information
            scanner.useDelimiter("\r\n");
            while (scanner.hasNext()) {
                String[] currentLine = scanner.next().split(",");
                String currentID = currentLine[0];
                float currentBalance = Float.parseFloat(currentLine[1]);

                //System.out.println("Loading..."+currentID+"\t£"+currentBalance);
                //Load currentID and currentBalance into your underlying data structure
                //one by one inside the loop. The loop will stop after reading the
                //last data point in the file.

                SetData(currentID, currentBalance); // add key value pairs from file to hashmap
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

 // compares if an account key/hashcode passed in is valid in the hashmap if not return false
    public boolean CheckIfValid(String key)
    {
        int hashCode = Hash(key);
        hashCode = hashCode % data.length;

        if (data[hashCode] != null) {
            while (data[hashCode] != null && !data[hashCode].key.equals(key)) //
            {

                if (data[hashCode].key.equals(key)) {
                    return true;
                } else {
                    //make sure its:  0 <= value  < HashTableSize/length
                    hashCode++;
                    hashCode = hashCode % data.length;
                }
            }

        }
        return false;
    }

    // adds the hashcode/key  and amount to the hashTable
    public void SetData(String key, double amount)
    {
        int hashCode = Hash(key);
        hashCode = hashCode % data.length;

        HashTable newPair = new HashTable(key, amount);

        if (data[hashCode] != null) {
            while (data[hashCode] != null) {
                hashCode++;
                hashCode = hashCode % data.length;
            }
        }
        data[hashCode] = newPair;
    }

    // deletes a requested account`s(hashcode and amount) from the hashmap if its valid
    public void DeleteData(String key)
    {
        int hashCode = Hash(key);
        hashCode = hashCode % data.length;

        while (!data[hashCode].key.equals(key)) {
            hashCode++;
            hashCode = hashCode % data.length;
        }

        if (data[hashCode].key.equals(key)) {
            data[hashCode].key = "0";
            data[hashCode].value = 0D;
        }
    }

    // prints updated balance of the requested(hashCode) account
    public void Display(String key)
    {
        int hashCode = Hash(key);
        hashCode = hashCode % data.length;

        if (data[hashCode] != null)
        {
            System.out.println("Current Balance: £" + decimalFormat.format(data[hashCode].value));
        } else
        {
            System.out.println("The hashmap is empty!");
        }

    }
    // update account`s balance during a request for withdraw or deposit
    public void Update(String key, double amount)
    {
        int hashCode = Hash(key);
        hashCode = hashCode % data.length;

        if (data[hashCode] != null)
        {
            if (CheckIfValid(key))
            {
                while (!data[hashCode].key.equals(key))
                {
                    hashCode++;
                    hashCode = hashCode % data.length;
                }
                data[hashCode].value += data[hashCode].value + amount; // add or decrease the generated amount to the current account
            }
        }
    }



}
