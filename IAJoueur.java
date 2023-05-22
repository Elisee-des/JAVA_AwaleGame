class IAJoueur extends Joueur {
    public IAJoueur(String nom) {
        super(nom);
    }

    public int choisirMouvement() {
        // Logique de l'IA pour choisir un coup aléatoire entre 1 et 6
        return (int) (Math.random() * 6) + 1;
    }
}

