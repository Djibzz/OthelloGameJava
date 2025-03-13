package controleur;

import modele.Joueur;
import modele.Partie;
import modele.Plateau;
import vue.Ihm;

public class Controleur {
    private Ihm ihm;
    private Partie partie;

    public Controleur() {
        this.ihm = new Ihm();
    }

    public void jouerPartie() {
        Joueur joueur1 = new Joueur("Joueur 1");
        Joueur joueur2 = new Joueur("Joueur 2");

        ihm.demanderNom(joueur1, joueur2);

        partie = new Partie(joueur1, joueur2);

        // La boucle continue tant que la partie n'est pas finie.
        while (!partie.partieEstFinie()) {  // Appelle la méthode partieEstFinie() sur l'objet partie
            ihm.afficherPlateau(partie.getPlateau());
            ihm.afficherJoueurCourant(partie.getJoueurCourant());

            if (partie.peutJouer(partie.getJoueurCourant())) {
                String coup;
                boolean coupValide;

                do {
                    coup = ihm.demanderCoup(partie.getJoueurCourant().getPseudo());
                    coupValide = partie.coupValide(coup);
                    if (!coupValide) {
                        if(coup.equalsIgnoreCase("P"))
                            ihm.afficherMessage("Impossible de passer son tour  car vous pouvez encore jouer! Veuillez entrer un coup valide.");
                        else
                            ihm.afficherMessage("Coup invalide ! Veuillez entrer un coup valide.");
                    }
                } while (!coupValide);

                partie.jouerCoup(coup);
            } else {
                ihm.afficherMessage(partie.getJoueurCourant().getPseudo() + " ne peut pas jouer et passe son tour.");
            }
            // Change de joueur après chaque tour
            partie.setJoueurCourant(partie.LejoueurSuivant());
        }

        afficherResultat();  // Afficher le résultat de la partie une fois terminée.
    }

    // Méthode pour afficher le résultat final de la partie.
    public void afficherResultat() {
        Joueur gagnant = partie.gagnantdelaPartie();
        if (gagnant != null) {
            ihm.afficherMessage("Le gagnant de la partie est : " + gagnant.getPseudo());
        } else {
            ihm.afficherMessage("La partie se termine sur un match nul !");
        }
    }
}

