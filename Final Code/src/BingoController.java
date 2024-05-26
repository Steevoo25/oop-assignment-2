import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class BingoController {

    private final String[] mainMenuItems = {"Exit",
            "Play bingo",
            "Set number separator",
            "Create a bingo card",
            "List existing cards",
            "Set bingo card size"};

    private final String OPTION_EXIT = ""; //I could not find a usage for this as it only prints a new line and the goodbymessage
    private final String OPTION_PLAY = "Eyes down, look in!";
    private final String OPTION_SEPARATOR = "Enter the new separator";
    private final String OPTION_CREATE_CARD = "Enter %d numbers for your card (separated by '%s')";
    private final String OPTION_LIST_CARDS = "Card %s%d numbers:";
    private final String OPTION_SIZE = "Enter the number of %s for the card";

    private int currentRowSize = Defaults.DEFAULT_NUMBER_OF_ROWS;
    private int currentColumnSize = Defaults.DEFAULT_NUMBER_OF_COLUMNS;

    private ArrayList<BingoCard> cardList = new ArrayList<>();

    public int getCurrentRowSize() {
        return currentRowSize;
    }

    public void setCurrentRowSize(int currentRowSize) {
        this.currentRowSize = currentRowSize;
    }

    public int getCurrentColumnSize() {
        return currentColumnSize;
    }

    public void setCurrentColumnSize(int currentColumnSize) {
        this.currentColumnSize = currentColumnSize;
    }

    public void addNewCard(BingoCard card) {
        cardList.add(card);
    }

    public void setSize() {
        setCurrentRowSize(parseInt(Toolkit.getInputForMessage(String.format(OPTION_SIZE, "rows"))));

        setCurrentColumnSize(parseInt(Toolkit.getInputForMessage(String.format(OPTION_SIZE, "columns"))));

        System.out.printf("The bingo card size is set to %d rows X %d columns%n",
                getCurrentRowSize(),
                getCurrentColumnSize());
    }

    public void createCard() {

        int numbersRequired = currentColumnSize * currentRowSize; //determines the amount of numbers needed to fill the card
        String[] numbers;
        boolean correctAmountOfNumbersEntered = false;


        do {
            numbers = Toolkit.getInputForMessage(String.format(
                            OPTION_CREATE_CARD, numbersRequired, Defaults.getNumberSeparator()))//gets all numbers to add to a card
                    .trim() //removes white space at the end of the whole string
                    .split(Defaults.getNumberSeparator()); //splits it at the separator and stores in numbers array

            //^^ Turns single string into array of strings seperated by the seperator
            //Eliminate white space from the string

            if (numbers.length == numbersRequired) { //if the correct amount of numbers is entered...
                correctAmountOfNumbersEntered = true;
            } else {
                System.out.printf("Try again: you entered %d numbers instead of %d%n", numbers.length, numbersRequired);
            } //...otherwise print appropriate message and try again
        } while (!correctAmountOfNumbersEntered);

        System.out.println("You entered\n" + Toolkit.printArray(numbers)); //prints the numbers the user has put in

        BingoCard bc = new BingoCard(currentRowSize, currentColumnSize);// creates new card

        bc.setCardNumbers(numbers); //sets card numbers for new card
        addNewCard(bc); //adds card to list of cards
    }

    public void listCards() {
        for (int i = 0; i < cardList.size(); i++) {//loops through list of cards
            if (Integer.toString(i).length() > 1) {//title for each card
                System.out.printf(OPTION_LIST_CARDS, "", i); //leave 1 space if index is double digit
            } else System.out.printf(OPTION_LIST_CARDS, " ", i); //leave 2 spaces in index is single digit

            printCardAsGrid(cardList.get(i).getCardNumbers()); //prints each card in a grid
        }
    }

    public void printCardAsGrid(String numbers) {
        String[] temp = numbers.split(Defaults.getNumberSeparator()); //make array that is split at each separator
        int i = 0; //counter to indicate what item is next

        for (int rows = 0; rows < currentRowSize; rows++) {
            System.out.print("\n"); //prints new line after every row

            for (int cols = 0; cols < currentColumnSize; cols++) {
                if (!(cols == currentColumnSize - 1)) { //if it is not the last entry in a row

                    if (temp[i].length() == 1) { //if it is a single digit
                        System.out.print(" " + temp[i] + Defaults.getNumberSeparator()); //add a space before it and seperator after
                    } else System.out.print(temp[i] + Defaults.getNumberSeparator()); //add only seperator after

                } else { //if it is the last entry in a row

                    if (temp[i].length() == 1) { //if it is a single digit
                        System.out.print(" " + temp[i]); //add a space before it and no separator after
                    } else System.out.print(temp[i]); //print with no separator after
                }
                i++;
            }
        }
        System.out.println();
    }

    public void setSeparator() {
        Defaults.setNumberSeparator(Toolkit.getInputForMessage(OPTION_SEPARATOR));
        System.out.printf("Separator is '%s'%n", Defaults.getNumberSeparator());
    }

    public void resetAllCards() {
        for (BingoCard card : cardList) { //for each card in cardList
            card.resetMarked(); //reset markedOff
        }
    }

    public void markNumbers(int number) {
        for (int i = 0; i < cardList.size(); i++) { //loop through all cards in cardList
            System.out.printf("Checking card %d for %d", i, number); //output what number is being checked on what card
            cardList.get(i).markNumber(number);
        }
    }

    public int getWinnerId() {
        for (int i = 0; i < cardList.size(); i++) { //loop through cards in cardList()
            if (cardList.get(i).isWinner()) return i; //if isWinner() is true return that cards index
        }
        return Defaults.NO_WINNER; //if there is no card that has won then return -1
    }

    public void play() {

        System.out.println(OPTION_PLAY);
        resetAllCards();
        boolean weHaveAWinner;

        do {
            markNumbers(parseInt(Toolkit.getInputForMessage("Enter the next number").trim()));
            //gets the next number to be checked and marks it off all the cards

            int winnerID = getWinnerId(); //gets the winner id

            weHaveAWinner = winnerID != Defaults.NO_WINNER;
            //as long as winnerID doesn't return -1 weHaveAWinner will be true

            if (weHaveAWinner)
                System.out.printf("And the winner is card %d%n", winnerID);

        } while (!weHaveAWinner); //loops until a winner is found
    }

    public String getMenu(String[] menuItems) {
        StringBuilder menuText = new StringBuilder();

        for (int i = 0; i < menuItems.length; i++) {

            String menuLine = " " + i + ": " + menuItems[i] + "\n"; // removes need for multiple append calls
            menuText.append(menuLine);
        }
        return menuText.toString();
    }

    public void run() {
        boolean finished = false;

        do {
            switch (Toolkit.getInputForMessage(getMenu(mainMenuItems))) {

                case "0":
                    finished = true;
                    break;

                case "1":
                    play();
                    break;

                case "2":
                    setSeparator();
                    break;

                case "3":
                    createCard();
                    break;

                case "4":
                    listCards();
                    break;

                case "5":
                    setSize();
                    break;

                default:
                    System.out.printf("Please enter a number between 0 and 5 inclusive%n");
            }
        } while (!finished);
    }
}