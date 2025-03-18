package modele;

import java.util.ArrayList;
import java.util.List;

public class JoueurIAFort  extends JoueurIA{
    public JoueurIAFort(String pseudo) {
        super(pseudo);
    }
    public List<String> getTousLesCoupsValidesPour(Partie partie ,String couleur) {
        List<String> coupsValides = new ArrayList<>();
        for (int ligne = 1; ligne <= 8; ligne++) {
            for (char colonne = 'A'; colonne <= 'H'; colonne++) {
                String coup = ligne + "" + colonne;
                if (partie.coupValidePour(coup, couleur)) {
                    coupsValides.add(coup);
                }
            }
        }
        return coupsValides;
    }
    public int minimax( Partie partie, boolean estMax, String couleurIA) {
        if (partie.getPlateau().partieEstFinie()) {
            return partie.getPlateau().evaluerPlateau(couleurIA); // Fonction d'évaluation
        }
        String couleurAdverse = couleurIA.equals("⚫") ? "⚪" : "⚫";

        if(estMax){
            int meilleurScore = Integer.MIN_VALUE;
            for (String coup : getTousLesCoupsValidesPour(partie,couleurIA)){
                Partie copiePartie = partie.copier();
                copiePartie.jouerCoup(coup);
                copiePartie.setJoueurCourant(copiePartie.LejoueurSuivant());
                int score = minimax(copiePartie,false,couleurIA);
                meilleurScore= Math.max(meilleurScore,score);
            }
            return  meilleurScore;
        }else{
            int pirescore = Integer.MAX_VALUE;
            for (String coup : getTousLesCoupsValidesPour(partie,couleurAdverse)){
                Partie copiePartie = partie.copier();
                copiePartie.jouerCoup(coup);
                copiePartie.setJoueurCourant(copiePartie.LejoueurSuivant());
                int score = minimax(copiePartie,true,couleurIA);
                pirescore= Math.min(pirescore,score);
            }
            return  pirescore;
        }


    }
    public String jouerCoup(Partie partie) {
        String meilleurCoup = null;
        int meilleurScore = Integer.MIN_VALUE;
        String couleurIA = partie.getJoueurCourant().equals(partie.getJoueurs()[0]) ? "⚫" : "⚪";

        for (String coup : getTousLesCoupsValidesPour(partie, couleurIA)) {
            Partie copiePartie = partie.copier();
            copiePartie.jouerCoup(coup);
            copiePartie.setJoueurCourant(copiePartie.LejoueurSuivant());
            int score = minimax(copiePartie, false, couleurIA);
            if (score > meilleurScore) {
                meilleurScore = score;
                meilleurCoup = coup;
            }
        }

        if (meilleurCoup != null) {
            partie.jouerCoup(meilleurCoup);
        } else {
            partie.jouerCoup("P"); 
        }
        return  meilleurCoup;
    }



}
