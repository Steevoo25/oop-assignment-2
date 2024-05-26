import java.util.Arrays;

public class BingoCard {
    /*
      The two arrays are private and their structure is NEVER exposed to another
      class, which is why the getCardNumbers returns a String that needs
      further processing.

      While this is not computationally efficient, it is good programming
      practice to hide data structures (information hiding).
     */
    private int[][] numbers;
    private boolean[][] markedOff;

    private int numberOfRows;
    private int numberOfColumns;

    public BingoCard(int numberOfRows, int numberOfColumns) {
        setNumberOfRows(numberOfRows);
        setNumberOfColumns(numberOfColumns);

        numbers = new int[numberOfRows][numberOfColumns];
        markedOff = new boolean[numberOfRows][numberOfColumns];
        resetMarked();
    }

    public void resetMarked() {
        markedOff = new boolean[numberOfRows][numberOfColumns]; //re-initialises boolean array to all equal false
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public String getCardNumbers() { //returns a String of the card numbers separated by the numberSeparator
        StringBuilder sb = new StringBuilder();

        for (int[] number : numbers) { //loop through the rows
            for (int i : number) { //loop through the columns

                sb.append(i).append(Defaults.getNumberSeparator());  //concatenate with the result and add a separator after
            }
        }
        sb.deleteCharAt(sb.length() - 1); //remove the trailing separator
        return sb.toString();
    }

    public void setCardNumbers(String[] numbersAsStrings) { //sets the numbers on the card from an array of string
        int i = 0;
        int[] numbersList = Arrays.stream(numbersAsStrings).mapToInt(Integer::parseInt).toArray();
        //^ sets the array of strings coming into the method to an array of integers to be used

        for (int rows = 0; rows < numberOfRows; rows++) {
            for (int cols = 0; cols < numberOfColumns; cols++) {
                numbers[rows][cols] = numbersList[i]; //loops throug the 2d array and sets the card values in order from top-right to bottom-left
                i++;
            }
        }
    }

    public void markNumber(int number) {

        boolean found = false; //sets default value to false

        for (int rows = 0; rows < numberOfRows; rows++) {
            for (int cols = 0; cols < numberOfColumns; cols++) { //^- iterates through numbers array

                if (number == numbers[rows][cols]) { //if the number to be marked off is in the array
                    markedOff[rows][cols] = true; //mark it off in the mirrored markedOff boolean array
                    System.out.println("\nMarked off " + number);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("\nNumber " + number + " not on this card");
            //if the number hasn't been found, print out appropriate message
        }

    }

    public boolean isWinner() {
        for (int rows = 0; rows < getNumberOfRows(); rows++) {
            for (int cols = 0; cols < getNumberOfColumns(); cols++) {//search through markedOff

                if (!markedOff[rows][cols]) {//if any false is found (the number has not been ticked off)
                    return false;//return false

                }
            }
        }
        return true;//there has been no unmarked numbers
    }
}