package controleur;

import modele.*;
import vue.Ihm;

/**
 * La classe {@code Controleur} gère la logique du déroulement d'une partie d'Othello.
 * Elle interagit avec la vue (Ihm) pour récupérer les saisies utilisateurs et afficher les informations,
 * et avec le modèle pour appliquer la logique de jeu.
 *
 * <p>Elle permet de sélectionner le mode de jeu (contre IA ou joueur 2),
 * de jouer les coups et d'afficher les résultats de chaque partie.</p>
 *
 * @author
 */
public class Controleur {
    private Ihm ihm;
    private Partie partie;

    /**
     * Constructeur de la classe {@code Controleur}.
     * Initialise la vue (Ihm).
     */
    public Controleur() {
        this.ihm = new Ihm();
    }

    /**
     * Demande le type de jeu (contre IA ou joueur 2) et initialise les joueurs et la partie.
     * Le nom du joueur 1 est demandé via la vue, et selon le choix, un joueur IA (naïf ou fort)
     * ou un deuxième joueur humain est créé.
     */
    public void demanderTypedeJeu() {  // True = IA ,False = Joueur 2
        String choix = ihm.demanderTypedeJeu();
        Joueur joueur1 = new Joueur("Joueur 1");
        Joueur joueur2;
        if (choix.equalsIgnoreCase("IA")) {
            String choixIA = ihm.demanderTypeIA();
            StrategieIA strategie;
            if (choixIA.equalsIgnoreCase("fort")) {
                strategie = new StrategieMinimax(4);
            } else {
                strategie = new StrategieNaive();
            }
            joueur2 = new JoueurIA("IA", strategie);
                joueur1.setPseudo(ihm.demanderNom(1));
                partie = new Partie(joueur1, joueur2);

        } else {
            joueur2 = new Joueur("Joueur 2");
            joueur1.setPseudo(ihm.demanderNom(1));
            joueur2.setPseudo(ihm.demanderNom(2));
            partie = new Partie(joueur1, joueur2);
        }
    }

    /**
     * Lance une partie de jeu et gère le déroulement jusqu'à la fin.
     * Pendant la partie, le contrôleur affiche le plateau, demande et valide les coups,
     * gère les tours et affiche les résultats à la fin de la partie.
     */
    public void jouerPartie() {
        boolean rejouer;
        do {
            demanderTypedeJeu();
            while (!partie.partieEstFinie()) {
                ihm.afficherPlateau(partie.getPlateau().getTableau());
                ihm.afficherJoueurCourant(partie.getJoueurCourant().getPseudo());

                if (partie.peutJouer(partie.getJoueurCourant())) {
                    if (partie.getJoueurCourant() instanceof JoueurIA) {
                        JoueurIA ia = (JoueurIA) partie.getJoueurCourant();
                        String coupIA = ia.jouerCoup(partie);
                        ihm.afficherMessage(ia.getPseudo() + " a joué automatiquement sur la case " + coupIA);
                    } else {
                        String coup;
                        boolean coupValide;
                        do {
                            coup = ihm.demanderCoup(partie.getJoueurCourant().getPseudo());
                            try {
                                coupValide = partie.coupValide(coup);
                                if (!coupValide) {
                                    if (coup.equalsIgnoreCase("P"))
                                        ihm.afficherMessage("Impossible de passer son tour car vous pouvez encore jouer! Veuillez entrer un coup valide.");
                                    else
                                        ihm.afficherMessage("Coup invalide ! Veuillez entrer un coup valide.");
                                }
                            } catch (IllegalArgumentException e) {
                                ihm.afficherMessage("Erreur dans la saisie : " + e.getMessage());
                                coupValide = false;
                            }
                        } while (!coupValide);
                        partie.jouerCoup(coup);
                    }
                } else {
                    ihm.afficherMessage(partie.getJoueurCourant().getPseudo() + " ne peut pas jouer et passe son tour.");
                }
                partie.setJoueurCourant(partie.LejoueurSuivant());
            }
            afficherResultatpartie();
            rejouer = ihm.demanderRejouer();
        } while (rejouer);
        afficherResultat();
        ihm.fermerScanner();
    }

    /**
     * Affiche le résultat de la partie en indiquant le gagnant de la partie ou un match nul.
     */
    public void afficherResultatpartie() {
        Joueur gagnant = partie.gagnantdelaPartie();
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
        Joueur gagnant = partie.gagnant();
        Joueur perdant = (gagnant.equals(partie.getJoueurs()[0])) ? partie.getJoueurs()[1] : partie.getJoueurs()[0];
        if (gagnant != null) {
            ihm.afficherMessage("Le gagnant de  est : " + gagnant.getPseudo() + " avec un score de " + gagnant.getNbPartiesgagnés() +
                    " Le perdant est " + perdant.getPseudo() + " avec un score de " + perdant.getNbPartiesgagnés());
        } else {
            ihm.afficherMessage("Ex aequo  !");
        }
    }
}
