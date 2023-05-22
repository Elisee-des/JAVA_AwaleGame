class Plateau {
    private int[] cases;

    public Plateau() {
        cases = new int[12];
        for (int i = 0; i < 12; i++) {
            cases[i] = 4;
        }
    }

    public int[] getCases() {
        return cases;
    }

    public void afficherPlateau() {
        System.out.println("Plateau :");
        System.out.println("  1 2 3 4 5 6");
        System.out.print("  ");
        for (int i = 11; i >= 6; i--) {
            System.out.print(cases[i] + " ");
        }
        System.out.println();
        System.out.print(cases[0] + "                " + cases[6]);
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < 6; i++) {
            System.out.print(cases[i] + " ");
        }
        System.out.println();
    }
}
