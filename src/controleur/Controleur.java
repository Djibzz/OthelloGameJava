package controleur;

import modele.Joueur;
import modele.JoueurIA;
import modele.JoueurIAFort;
import modele.Partie;
import vue.Ihm;

public class Controleur {
    private Ihm ihm;
    private Partie partie;

    public Controleur() {
        this.ihm = new Ihm();
    }

    public void demanderTypedeJeu(){  // True = IA False = Joueur 2
        String choix = ihm.demanderTypedeJeu();
        Joueur joueur1 = new Joueur("Joueur 1");
        Joueur joueur2;
        if(choix.equalsIgnoreCase("IA")) {
            String choixIA = ihm.demanderTypeIA();
            if(choixIA.equalsIgnoreCase("fort")) {
                joueur2 = new JoueurIAFort("Joueur IA intélligent");
                ihm.demanderNomIA(joueur1);
                partie = new Partie(joueur1, joueur2);
            }else {
                joueur2 = new JoueurIA("Joueur IA Naif");
                ihm.demanderNomIA(joueur1);
                partie = new Partie(joueur1, joueur2);
            }
        }else{
            joueur2 = new Joueur("Joueur 2");
            ihm.demanderNom(joueur1, joueur2);
            partie = new Partie(joueur1, joueur2);
        }
    }

    public void jouerPartie() {
        boolean rejouer;
        do {
            demanderTypedeJeu();
            while (!partie.partieEstFinie()) {
                ihm.afficherPlateau(partie.getPlateau());
                ihm.afficherJoueurCourant(partie.getJoueurCourant());

                if (partie.peutJouer(partie.getJoueurCourant())) {
                    if(partie.getJoueurCourant() instanceof JoueurIA ) {
                        JoueurIA ia = (JoueurIA) partie.getJoueurCourant();
                        String coupIA = ia.jouerCoup(partie);
                        ihm.afficherMessage(ia.getPseudo() + " a joué automatiquement sur la case " + coupIA);
                    }
                    else if (partie.getJoueurCourant() instanceof JoueurIAFort) {
                        JoueurIA ia = (JoueurIAFort) partie.getJoueurCourant();
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

    public void afficherResultatpartie() {
        Joueur gagnant = partie.gagnantdelaPartie();
        if (gagnant != null) {
            ihm.afficherMessage("Le gagnant de la partie est : " + gagnant.getPseudo());
        } else {
            ihm.afficherMessage("La partie se termine sur un match nul !");
        }
    }
    public void afficherResultat() {
        Joueur gagnant = partie.gagnant();
        Joueur perdant =(gagnant.equals(partie.getJoueurs()[0])) ? partie.getJoueurs()[1]: partie.getJoueurs()[0];
        if (gagnant != null) {
            ihm.afficherMessage("Le gagnant de  est : " + gagnant.getPseudo()+" avec un score de "+ gagnant.getNbPartiesgagnés()+" Le perdant est "+
                    perdant.getPseudo()+" avec un score de "+ perdant.getNbPartiesgagnés());
        } else {
            ihm.afficherMessage("Ex aequo  !");
        }
    }
}

