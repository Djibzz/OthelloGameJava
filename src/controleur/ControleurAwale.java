package controleur;

import modele.*;
import vue.Ihm;

public class ControleurAwale extends ControleurAbstrait {

    /**
     * Constructeur du contrôleur pour le jeu Awalé.
     * Initialise une nouvelle instance de Ihm.
     */
    public ControleurAwale() {
        this.ihm = new Ihm();
    }

    /**
     * Initialise la partie Awalé : demande le nom des joueurs,
     * refuse l’IA, crée une instance de PartieAwale.
     */
    @Override
    protected void initialiser() {
        String choix;
        do {
            choix = ihm.demanderTypedeJeu(); // "IA" ou "J2"
            if (choix.equalsIgnoreCase("IA")) {
                ihm.afficherMessage("❌ Le mode IA n'est pas encore disponible pour Awalé. Veuillez choisir 'J2'.");
            } else if (!choix.equalsIgnoreCase("J2")) {
                ihm.afficherMessage("❌ Choix invalide. Tapez 'J2' pour jouer à deux.");
            }
        } while (!choix.equalsIgnoreCase("J2"));

        JoueurAbstrait joueur1 = new JoueurAwale(ihm.demanderNom(1));
        JoueurAbstrait joueur2 = new JoueurAwale(ihm.demanderNom(2));

        this.partie = new PartieAwale(joueur1, joueur2);
    }

    /**
     * Gère un tour de jeu pour le joueur courant : demande un coup
     * valide et l'applique.
     */
    @Override
    protected void traiterTour() {
        JoueurAbstrait joueurCourant = partie.getJoueurCourant();
        String coup;
        do {
            coup = ihm.demanderCoup(joueurCourant.getPseudo(), "(1 à 6)");
            if (!partie.coupValide(coup)) {
                ihm.afficherMessage("Coup invalide, réessayez.");
            }
        } while (!partie.coupValide(coup));

        partie.jouerCoup(coup);
    }

    /**
     * Affiche le gagnant à la fin d’une partie Awalé ou une égalité.
     */
    @Override
    public void afficherResultatPartie() {
        JoueurAbstrait gagnant = partie.getGagnant();
        if (gagnant == null)
            ihm.afficherMessage("Égalité !");
        else
            ihm.afficherMessage("Le gagnant de la partie est : " + gagnant.getPseudo());
    }

    /**
     * Affiche les scores finaux et le nombre de victoires.
     */
    @Override
    public void afficherResultat() {
        JoueurAbstrait gagnant = partie.getGagnant();
        if (gagnant != null) {
            JoueurAbstrait perdant = gagnant.equals(partie.getJoueurs()[0])
                    ? partie.getJoueurs()[1]
                    : partie.getJoueurs()[0];
            ihm.afficherMessage("Score final : " + gagnant.getPseudo() + " a gagné " +
                    gagnant.getNbPartiesGagnees() + " partie(s).");
        } else {
            ihm.afficherMessage("Match nul !");
        }
    }

    /**
     * Affiche le plateau de jeu Awalé avec les greniers des joueurs.
     */
    @Override
    protected void afficherPlateau() {
        int[][] tableau = ((PlateauAwale) partie.getPlateau()).getTableau();
        JoueurAwale j1 = (JoueurAwale) partie.getJoueurs()[0];
        JoueurAwale j2 = (JoueurAwale) partie.getJoueurs()[1];
        int[] greniers = { j2.getGrenier(), j1.getGrenier() };
        ihm.afficherPlateauAwale(tableau, greniers);
    }
}