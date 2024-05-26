public class BingoRunner {
    public static void main(String[] args) {
        BingoController bc = new BingoController();
        bc.run();
        System.out.print(Toolkit.GOODBYE_MESSAGE);
        System.out.println();
    }
}