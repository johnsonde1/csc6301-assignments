/**
 * A {@link NotificationMedium} implementation that delivers
 * notifications via SMS (Short Message Service).
 *
 * @author  Derrick Johnson
 * @version 1.0
 * @since June 6, 2026
 * @see NotificationMedium
 */
public class SMSService implements NotificationMedium {

    /**
     * Constructs a new {@code SMSService}.
     */
    public SMSService() {}

    /**
     * Simulates sending {@code message} as an SMS text.
     *
     * <p>Prints a formatted confirmation to {@code System.out} so
     * callers can verify the correct medium was invoked.</p>
     *
     * @param message the notification text to deliver; must not be {@code null}
     */
    @Override
    public void send(String message) {
        System.out.println("[SMS] Sending message: " + message);
    }
}
