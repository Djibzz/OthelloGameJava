package modele;

/**
 * Interface définissant une stratégie pour jouer un coup.
 */
public interface StrategieIA {
    /**
     * Choisit un coup à jouer dans la partie.
     *
     * @param partieOthello la partie en cours
     * @param couleur la couleur de l'IA ("⚫" ou "⚪")
     * @return le coup choisi (ex : "3C")
     */
    String choisirCoup(PartieOthello partieOthello, String couleur);
}