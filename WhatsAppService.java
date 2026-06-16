/**
 * A {@link NotificationMedium} implementation that delivers
 * notifications via WhatsApp.
 *
 * <p>This class is a drop-in addition to the existing system: because
 * {@code AlertSystem} depends only on the {@link NotificationMedium}
 * interface (composition), this channel can be plugged in via
 * {@link AlertSystem#setMedium(NotificationMedium)} without any changes
 * to {@code AlertSystem} or the interface itself.</p>
 *
 * @author  Derrick Johnson
 * @version 1.0
 * @since June 11, 2026
 * @see NotificationMedium
 */
public class WhatsAppService implements NotificationMedium {

    /**
     * Constructs a new {@code WhatsAppService}.
     */
    public WhatsAppService() {}

    /**
     * Simulates sending {@code message} as a WhatsApp message.
     *
     * <p>Prints a formatted confirmation to {@code System.out} so
     * callers can verify the correct medium was invoked.</p>
     *
     * @param message the notification text to deliver; must not be {@code null}
     */
    @Override
    public void send(String message) {
        System.out.println("[WhatsApp] Sending message: " + message);
    }
}