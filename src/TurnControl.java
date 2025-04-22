/**
 * Egységes interfész a játékban szereplő entitások számára,
 * amelyek reagálnak az idő múlására vagy el tudnak pusztulni.
 */
public interface TurnControl {
    /**
     * Egy időegység elteltével végrehajtandó művelet.
     */
    void timeElapsed();

    /**
     * Az objektum megsemmisítését végző metódus.
     */
    void die();

    /**
     * A kiíratásért felelős.
     */
    String toString();
}
