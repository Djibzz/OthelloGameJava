package controleur;

import modele.*;
import vue.Ihm;

public class ControleurOthello extends ControleurAbstrait {

    /**
     * Constructeur du contrôleur pour le jeu Othello.
     * Initialise une nouvelle instance de Ihm.
     */
    public ControleurOthello() {
        this.ihm = new Ihm();
    }

    /**
     * Initialise la partie Othello : demande le nom des joueurs,
     * choix du mode IA ou humain, instancie la stratégie IA si nécessaire.
     */
    @Override
    public void initialiser() {
        String choix = ihm.demanderTypedeJeu();

        JoueurAbstrait joueur1 = new JoueurOthello(ihm.demanderNom(1));
        JoueurAbstrait joueur2;

        if (choix.equalsIgnoreCase("IA")) {
            String choixIA = ihm.demanderTypeIA();
            StrategieIA strategie = choixIA.equalsIgnoreCase("fort")
                    ? new StrategieMinimax(4)
                    : new StrategieNaive();
            joueur2 = new JoueurIA("IA", strategie);
        } else {
            joueur2 = new JoueurOthello(ihm.demanderNom(2));
        }

        this.partie = new PartieOthello(joueur1, joueur2);
    }

    /**
     * Gère un tour de jeu pour Othello. Si l’IA joue, elle choisit automatiquement
     * un coup. Sinon, le joueur humain saisit un coup validé.
     */
    @Override
    public void traiterTour() {
        JoueurAbstrait courant = partie.getJoueurCourant();

        if (courant instanceof JoueurIA ia) {
            String coup = ia.jouerCoup((PartieOthello) partie);
            ihm.afficherMessage(ia.getPseudo() + " a joué automatiquement sur la case " + coup);
            partie.jouerCoup(coup);
        } else {
            String coup;
            boolean coupValide;
            do {
                coup = ihm.demanderCoup(courant.getPseudo(), "(ex: 3C) ou 'P' pour passer");
                try {
                    coupValide = partie.coupValide(coup);
                    if (!coupValide) {
                        if (coup.equalsIgnoreCase("P")) {
                            ihm.afficherMessage("Impossible de passer son tour car vous pouvez encore jouer !");
                        } else {
                            ihm.afficherMessage("Coup invalide !");
                        }
                    }
                } catch (IllegalArgumentException e) {
                    ihm.afficherMessage("Erreur : " + e.getMessage());
                    coupValide = false;
                }
            } while (!coupValide);
            partie.jouerCoup(coup);
        }
    }

    /**
     * Affiche le gagnant de la partie Othello ou une égalité.
     */
    @Override
    public void afficherResultatPartie() {
        JoueurOthello gagnant = ((PartieOthello) partie).gagnantDeLaPartie();
        if (gagnant != null) {
            ihm.afficherMessage("Le gagnant de la partie est : " + gagnant.getPseudo());
        } else {
            ihm.afficherMessage("La partie se termine sur un match nul !");
        }
    }

    /**
     * Affiche les scores finaux pour l'ensemble des parties Othello jouées.
     */
    @Override
    public void afficherResultat() {
        JoueurAbstrait gagnant = partie.getGagnant();
        if (gagnant != null) {
            JoueurAbstrait[] joueurs = partie.getJoueurs();
            JoueurAbstrait perdant = gagnant.equals(joueurs[0]) ? joueurs[1] : joueurs[0];
            ihm.afficherMessage("Le gagnant est : " + gagnant.getPseudo() +
                    " avec " + gagnant.getNbPartiesGagnees() +
                    " | Le perdant : " + perdant.getPseudo() +
                    " avec " + perdant.getNbPartiesGagnees());
        } else {
            ihm.afficherMessage("Match nul !");
        }
    }

    /**
     * Affiche le plateau de jeu Othello avec l'état actuel des pions.
     */
    @Override
    protected void afficherPlateau() {
        ihm.afficherPlateauOthello(((PlateauOthello) partie.getPlateau()).getTableau());
    }
}