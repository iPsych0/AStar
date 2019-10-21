public class Main {

    public static void main(String[] args) {
//        new Benchmarker().start(10_000);
        AStarGrid aStarGrid = new AStarGrid(0, 0, 12, 12);
        new Screen(aStarGrid);
    }
}