package controleur;

import modele.*;
import vue.Ihm;

public class ControleurOthello extends ControleurAbstrait {

    public ControleurOthello() {
        this.ihm = new Ihm();
    }

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
        } else
            joueur2 = new JoueurOthello(ihm.demanderNom(2));


        this.partie = new PartieOthello((JoueurOthello) joueur1, (JoueurOthello) joueur2);
    }

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
                coup = ihm.demanderCoup(courant.getPseudo());
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

    public void afficherResultatPartie() {
        JoueurOthello gagnant = ((PartieOthello) partie).gagnantDeLaPartie();
        if (gagnant != null) {
            ihm.afficherMessage("Le gagnant de la partie est : " + gagnant.getPseudo());
        } else {
            ihm.afficherMessage("La partie se termine sur un match nul !");
        }
    }

    @Override
    public void afficherResultat() {
        PartieOthello partieOthello = (PartieOthello) partie;
        JoueurOthello gagnant = partieOthello.gagnant();

        if (gagnant != null) {
            JoueurOthello perdant = gagnant.equals(partieOthello.getJoueurs()[0])
                    ? partieOthello.getJoueurs()[1]
                    : partieOthello.getJoueurs()[0];

            ihm.afficherMessage("Le gagnant est : " + gagnant.getPseudo() +
                    " avec " + gagnant.getNbPartiesGagnees() +
                    " | Le perdant : " + perdant.getPseudo() +
                    " avec " + perdant.getNbPartiesGagnees());
        } else {
            ihm.afficherMessage("Ex aequo !");
        }
    }
}
