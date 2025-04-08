package modele;

public interface PlateauJeu {
    /**
     * Initialise les éléments du plateau au début de la partie.
     */
    void InitialisationTableau();

    /**
     * Retourne une représentation du plateau (tableau 2D ou autre).
     * Utile pour l'affichage.
     */
    Object getTableau();
}
