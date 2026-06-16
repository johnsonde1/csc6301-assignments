/**
 * Interface to define the medium for all notification delivery channels.
 *
 * @author  Derrick Johnson
 * @version 1.0
 * @since June 6, 2026
 */
public interface NotificationMedium {

    /**
     * Sends a notification message through this medium.
     *
     * @param message the text to be delivered; must not be {@code null}
     */
    void send(String message);
}
