import java.util.Scanner;

class JeuAwale {
    private Joueur joueur1;
    private Joueur joueur2;
    private Plateau plateau;
    private Scanner scanner;

    public JeuAwale() {
        scanner = new Scanner(System.in);
        int option = 0;

        while (option < 1 || option > 3) {
            System.out.println("Options de jeu :");
            System.out.println("1. Joueur contre joueur");
            System.out.println("2. Joueur contre IA");
            System.out.println("3. IA contre IA");
            System.out.print("Choisissez une option (1-3) : ");
            option = scanner.nextInt();
        }

        if (option == 1) {
            System.out.print("Entrez le nom du Joueur 1 : ");
            String nomJoueur1 = scanner.next();
            joueur1 = new Joueur(nomJoueur1);

            System.out.print("Entrez le nom du Joueur 2 : ");
            String nomJoueur2 = scanner.next();
            joueur2 = new Joueur(nomJoueur2);
        } else if (option == 2) {
            System.out.print("Entrez le nom du Joueur : ");
            String nomJoueur = scanner.next();
            joueur1 = new Joueur(nomJoueur);
            joueur2 = new IAJoueur("IA");
        } else if (option == 3) {
            joueur1 = new IAJoueur("IA 1");
            joueur2 = new IAJoueur("IA 2");
        }

        plateau = new Plateau();
    }

    public void jouer() {
        boolean finPartie = false;
        Joueur joueurCourant = joueur1;

        while (!finPartie) {
            plateau.afficherPlateau();
            System.out.println(joueurCourant.getNom() + ", c'est à votre tour.");

            int numCase = -1;
            while (numCase < 1 || numCase > 6 || plateau.getCases()[getIndex(joueurCourant, numCase)] == 0) {
                if (joueurCourant instanceof IAJoueur) {
                    numCase = ((IAJoueur) joueurCourant).choisirMouvement();
                    System.out.println("L'IA choisit la case " + numCase);
                } else {
                    System.out.print("Choisissez un numéro de case (1-6) : ");
                    numCase = scanner.nextInt();
                }
            }

            effectuerMouvement(joueurCourant, numCase);
            finPartie = estPartieTerminee();

            if (!finPartie) {
                joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
            }
        }

        afficherResultat();
    }

    private int getIndex(Joueur joueurCourant, int numCase) {
        if (joueurCourant == joueur1) {
            return numCase - 1;
        } else {
            return 12 - numCase; // Correction de l'indice pour le joueur 2
        }
    }

    private void effectuerMouvement(Joueur joueurCourant, int numCase) {
        int graines = plateau.getCases()[getIndex(joueurCourant, numCase)];
        plateau.getCases()[getIndex(joueurCourant, numCase)] = 0;

        int index = getIndex(joueurCourant, numCase);
        while (graines > 0) {
            index = (index + 1) % 12;

            if (index == 6 && joueurCourant != joueur1) {
                // Ignorer le trou du joueur adverse
                continue;
            }

            plateau.getCases()[index]++;
            graines--;
        }

        // Vérifier si la dernière graine est tombée dans le camp du joueur courant
        int derniereCase = index;
        if (joueurCourant == joueur1 && derniereCase >= 0 && derniereCase <= 5 && plateau.getCases()[derniereCase] == 1) {
            int indexOppose = 11 - derniereCase;
            joueurCourant.incrementerScore(plateau.getCases()[indexOppose] + 1);
            plateau.getCases()[indexOppose] = 0;
            plateau.getCases()[derniereCase] = 0; // Réinitialiser le trou
        } else if (joueurCourant == joueur2 && derniereCase >= 7 && derniereCase <= 11 && plateau.getCases()[derniereCase] == 1) {
            int indexOppose = 11 - derniereCase;
            joueurCourant.incrementerScore(plateau.getCases()[indexOppose] + 1);
            plateau.getCases()[indexOppose] = 0;
            plateau.getCases()[derniereCase] = 0; // Réinitialiser le trou
        }

        // Correction : Incrémenter le trou du joueur 2 lorsque le joueur 1 joue dans la case 5
        if (joueurCourant == joueur1 && numCase == 5) {
            plateau.getCases()[6]++;
        }
    }

    private boolean estPartieTerminee() {
        int casesJoueur1 = 0;
        int casesJoueur2 = 0;

        for (int i = 0; i < 6; i++) {
            casesJoueur1 += plateau.getCases()[i];
            casesJoueur2 += plateau.getCases()[i + 6];
        }

        return casesJoueur1 == 0 || casesJoueur2 == 0;
    }

    private void afficherResultat() {
        System.out.println("Partie terminée !");
        System.out.println("Score final :");
        System.out.println(joueur1.getNom() + " : " + joueur1.getScore());
        System.out.println(joueur2.getNom() + " : " + joueur2.getScore());
        if (joueur1.getScore() > joueur2.getScore()) {
            System.out.println(joueur1.getNom() + " a gagné !");
        } else if (joueur2.getScore() > joueur1.getScore()) {
            System.out.println(joueur2.getNom() + " a gagné !");
        } else {
            System.out.println("Match nul !");
        }
    }
}

