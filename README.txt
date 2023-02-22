# FetchBD

This code is for Fetch Rewards take home exercise for a back end developer intern role. It reads a CSV file of transactions, processes each transaction and tracks remaining balance of payers.

## Usage

Compile the code by running the following command in your terminal.
```
javac Exercise.java
```
Run the program by typing java Exercise <points-to-spend> <csv-file> in the terminal, where <points> the amount of points to spend, and <csv-file> name of the CSV file, assuming it's in the same folder with the Exercise.java file. For example, if you want to spend 5000 points, it would look like this:
```
java Exercise 5000 transactions.csv
```
The program will output remaining balances of each payer in the following format:

```
{
    "DANNON": 1000,
    "MILLER COORS": 5300,
    "UNILEVER": 0
}
```
