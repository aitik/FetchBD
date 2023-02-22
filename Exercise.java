import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Class of the program that processes transactions and tracks the balance of payers that needs to be paid
 */
public class Exercise {
    /**
     * Reads the given CSV file of transactions, sorts the list of transactions by timestamp,
     * and processes all transactions while also tracking the balance of payers that needs to be paid
     *
     * @param args - The command line arguments
     *        args[0] - the amount of points to spend
     *        args[1] - the name of the CSV file with transactions
     */
    public static void main(String[] args) {
        // Take arguments
        int pointsToSpend = Integer.parseInt(args[0]);
        String csvFile = args[1];

        // Call another method to get a list of transactions
        ArrayList<Transaction> transactions = readTransactions(csvFile);
        // Sort transactions by their timestamp
        Collections.sort(transactions);
        // Create a hashtable to track payers' balance, so we won't have duplicate payers
        Hashtable<String, Integer> balances = new Hashtable<>();
        // Process all transactions
        for (Transaction transaction : transactions) {
            // Get information about the transaction
            String payer = transaction.getPayer();
            // Look for the payer's balance if we processed it before, otherwise put the balance as 0
            int balance = balances.getOrDefault(payer, 0);
            int points = transaction.getPoints();

            // If we have enough points to cover transaction, or the amount of points from the transaction is negative
            if (pointsToSpend >= points) {
                // Keep track of the transaction and payer's balance, which should be 0 in this case
                balances.put(payer, balance);
                // Update our amount of points to be spent
                pointsToSpend -= points;
            }
            // If we do not have enough points to cover transaction, but our balance is not empty
            else if (pointsToSpend > 0) {
                balances.put(payer, balance + points - pointsToSpend);
                // Update our amount of points to be spent, which is 0 since we spent all our points
                // to cover at least some part of this transaction
                pointsToSpend = 0;
            }
            // If we do not have any points
            else {
                // Add the amount from the transaction to the balance of the payer
                balances.put(payer, balance + points);
            }
        }
        // Print out the balances of the payers in given format:
        // {
        //     "Payer1": 200,
        //     "Payer2": 132,
        //     ...
        //     "Last Payer": 47
        // }
        System.out.println("{");
        int count = 0;
        for (String payer: balances.keySet()) {
            count++;
            if (count == balances.size()) {
                System.out.println("    " + payer + ": " + balances.get(payer));
            } else {
                System.out.println("    " + payer + ": " + balances.get(payer) + ",");
            }
        }
        System.out.println("}");
    }

    /**
     * Helper method that processes CSV file and returns an array list of transactions
     * @param csvFile - path to the CSV file
     * @return an array list of transactions
     */
    private static ArrayList<Transaction> readTransactions(String csvFile) {
        // Create an array list to return
        ArrayList<Transaction> transactions = new ArrayList<>();
        // Go through each line of the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header of hte CSV file
            String line = br.readLine();
            // Check every line until we meet the end of the file
            while ((line = br.readLine()) != null) {
                // Split the information from this line by "," and store it in an array
                String[] fields = line.split(",");
                // Get information about the name of the payer
                String payer = fields[0];
                // Get information about amount of this transaction
                int points = Integer.parseInt(fields[1]);
                // Get information about timestamp of this transaction
                String timestamp = fields[2];
                // Create a new Transaction object with information about the name of the payer, the amount of this transaction, and timestamp
                Transaction transaction = new Transaction(payer, points, timestamp);
                // Add it to the array list which we will return later
                transactions.add(transaction);
            }
        } catch (Exception e) {
            // If something goes wrong, catch the exception and print it out
            e.printStackTrace();
        }
        // Return array list of transactions
        return transactions;
    }
}


/**
 * Transaction class which stores information about a transaction and implement Comparable
 * so that we will be able to sort the list of transactions by their timestamp
 */
class Transaction implements Comparable<Transaction> {
    // Private fields
    private String payer; // Name of the payer
    private int points; // Amount of this transaction
    private String timestamp; // Timestamp of this transaction

    /**
     * Class constructor to store information about this transaction
     * @param payer - Name of the payer
     * @param points - Amount of this transaction
     * @param timestamp - Timestamp of this transaction
     */
    public Transaction(String payer, int points, String timestamp) {
        // Assign private fields
        this.payer = payer;
        this.points = points;
        this.timestamp = timestamp;
    }

    /**
     * Getter for the name of the payer
     * @return
     */
    public String getPayer() {
        return payer;
    }

    /**
     * Getter for the amount of this transaction
     * @return
     */
    public int getPoints() {
        return points;
    }

    /**
     * Getter for the timestamp of this transaction
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Compares this transaction with another transaction according to their timestamps.
     * Needed so we can sort the list of transaction by their timestamp
     *
     * @param other The other transaction to compare
     * @return A negative number if this transaction's timestamp is earlier than the other's, 0 if they are the same,
     *         and a positive number if this transaction's timestamp is later than the other's
     */
    @Override
    public int compareTo(Transaction other) {
        return this.getTimestamp().compareTo(other.getTimestamp());
    }
}
