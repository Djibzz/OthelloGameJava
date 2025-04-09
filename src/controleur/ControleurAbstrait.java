package controleur;
import modele.*;
import vue.Ihm;

public abstract class ControleurAbstrait {
    protected PartieAbstraite partie;
    protected Ihm ihm = new Ihm();

    /**
     * Joue une partie complète avec initialisation, alternance des joueurs, affichage du plateau,
     * et gestion du résultat. Gère également le choix de rejouer.
     */
    public void jouerPartie() {
        boolean rejouer;
        do {
            initialiser();
            while (!partie.partieEstFinie()) {
                afficherPlateau();
                JoueurAbstrait joueur = partie.getJoueurCourant();
                ihm.afficherJoueurCourant(joueur.getPseudo());

                if (partie.peutJouer(joueur)) {
                    traiterTour();
                } else {
                    ihm.afficherMessage("Aucun coup possible. Tour passé.");
                }

                partie.changerJoueur();
            }
            afficherResultatPartie();

            rejouer = ihm.demanderRejouer();
        } while(rejouer);
        afficherResultat();
        ihm.fermerScanner();
    }

    /**
     * Initialise une nouvelle partie. À redéfinir dans chaque contrôleur spécifique.
     */
    protected abstract void initialiser();

    /**
     * Gère le déroulement d'un tour de jeu pour le joueur courant.
     */
    protected abstract void traiterTour();

    /**
     * Affiche le résultat cumulé des parties après la fin de la session de jeu.
     */
    protected abstract void afficherResultat();

    /**
     * Affiche le résultat d'une partie individuelle à sa fin.
     */
    protected abstract void afficherResultatPartie();

    /**
     * Affiche l'état courant du plateau de jeu.
     */
    protected abstract void afficherPlateau();
}