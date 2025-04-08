package controleur;

import modele.*;
import vue.Ihm;

public class ControleurAwale extends ControleurAbstrait {

    public ControleurAwale() {
        this.ihm = new Ihm();
    }

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

    @Override
    public void afficherResultatPartie() {
        JoueurAbstrait gagnant = partie.getGagnant();
        if (gagnant == null)
            ihm.afficherMessage("Égalité !");
        else
            ihm.afficherMessage("Le gagnant de la partie est : " + gagnant.getPseudo());
    }

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
}
