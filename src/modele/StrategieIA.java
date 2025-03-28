package modele;

/**
 * Interface définissant une stratégie pour jouer un coup.
 */
public interface StrategieIA {
    /**
     * Choisit un coup à jouer dans la partie.
     *
     * @param partie la partie en cours
     * @param couleur la couleur de l'IA ("⚫" ou "⚪")
     * @return le coup choisi (ex : "3C")
     */
    String choisirCoup(Partie partie, String couleur);
}