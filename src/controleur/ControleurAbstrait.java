package controleur;
import modele.*;
import vue.Ihm;

public abstract class ControleurAbstrait {
    protected PartieAbstraite partie;
    protected Ihm ihm = new Ihm();

    public final void jouerPartie() {
        initialiser();
        while (!partie.partieEstFinie()) {
            ihm.afficherPlateau( partie.getPlateau().getTableau());
            Joueur joueur = partie.getJoueurCourant();
            ihm.afficherJoueurCourant(joueur.getPseudo());

            if (partie.peutJouer(joueur)) {
                traiterTour();
            } else {
                ihm.afficherMessage("Aucun coup possible. Tour passé.");
            }

            partie.changerJoueur();
        }

         afficherResultat();
    }

    protected abstract void initialiser();
    protected abstract void traiterTour();
    protected abstract void afficherResultat();
}
