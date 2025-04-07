package controleur;

import modele.*;
import vue.Ihm;

/**
 * La classe {@code ControleurOthello} gère la logique du déroulement d'une partie d'Othello.
 * Elle interagit avec la vue (Ihm) pour récupérer les saisies utilisateurs et afficher les informations,
 * et avec le modèle pour appliquer la logique de jeu.
 *
 * <p>Elle permet de sélectionner le mode de jeu (contre IA ou joueur 2),
 * de jouer les coups et d'afficher les résultats de chaque partie.</p>
 *
 * @author
 */
public class ControleurOthello extends ControleurAbstrait {

    /**
     * Constructeur de la classe {@code ControleurOthello}.
     * Initialise la vue (Ihm).
     */
    public ControleurOthello() {
        this.ihm = new Ihm();
    }

    /**
     * Demande le type de jeu (contre IA ou joueur 2) et initialise les joueurs et la partie.
     * Le nom du joueur 1 est demandé via la vue, et selon le choix, un joueur IA (naïf ou fort)
     * ou un deuxième joueur humain est créé.
     */
    public void initialiser() {
        String choix = ihm.demanderTypedeJeu();
        Joueur joueur1 = new Joueur("Joueur 1");
        Joueur joueur2;

        joueur1.setPseudo(ihm.demanderNom(1));
        if (choix.equalsIgnoreCase("IA")) {
            String choixIA = ihm.demanderTypeIA();
            StrategieIA strategie = choixIA.equalsIgnoreCase("fort")
                    ? new StrategieMinimax(4)
                    : new StrategieNaive();
            joueur2 = new JoueurIA("IA", strategie);
        } else {
            joueur2 = new Joueur("Joueur 2");
            joueur2.setPseudo(ihm.demanderNom(2));
        }

        this.partie = new PartieOthello(joueur1, joueur2);
    }


    /**
     * Lance une partie de jeu et gère le déroulement jusqu'à la fin.
     * Pendant la partie, le contrôleur affiche le plateau, demande et valide les coups,
     * gère les tours et affiche les résultats à la fin de la partie.
     */
    public void traiterTour() {
        Joueur courant = partie.getJoueurCourant();

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

    /**
     * Affiche le résultat de la partie en indiquant le gagnant de la partie ou un match nul.
     */
    public void afficherResultatPartie() {
        Joueur gagnant = ((PartieOthello) partie).gagnantdelaPartie();
        if (gagnant != null) {
            ihm.afficherMessage("Le gagnant de la partie est : " + gagnant.getPseudo());
        } else {
            ihm.afficherMessage("La partie se termine sur un match nul !");
        }
    }

    /**
     * Affiche le résultat final en indiquant le gagnant global et le nombre de parties gagnées pour chaque joueur.
     */
    public void afficherResultat() {
        Joueur gagnant = ((PartieOthello) partie).gagnant();

        if (gagnant != null) {
            Joueur perdant = gagnant.equals(((PartieOthello) partie).getJoueurs()[0])
                    ? ((PartieOthello) partie).getJoueurs()[1]
                    : ((PartieOthello) partie).getJoueurs()[0];

            ihm.afficherMessage("Le gagnant est : " + gagnant.getPseudo() +
                    " avec " + gagnant.getNbPartiesgagnés() +
                    " | Le perdant : " + perdant.getPseudo() +
                    " avec " + perdant.getNbPartiesgagnés());
        } else {
            ihm.afficherMessage("Ex aequo !");
        }
    }
}
