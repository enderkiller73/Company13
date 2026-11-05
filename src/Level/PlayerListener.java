package Level;

// Other classes can use this interface to listen for events from the Player class
public interface PlayerListener {
    default void onLevelCompletionTriggered() {}
    void onLevelCompleted();
    void onDeath();
}
