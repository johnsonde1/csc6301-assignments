import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A notification dispatcher that uses composition to remain
 * independent of any specific delivery channel.
 *
 * <p>{@code AlertSystem} holds a reference to a {@link NotificationMedium}
 * rather than extending one. This means the active channel can be swapped
 * at runtime via {@link #setMedium(NotificationMedium)} without touching
 * any other code.</p>
 *
 * <p>Every successfully dispatched message is recorded in an internal
 * {@link ArrayList} log that can be retrieved with {@link #getLog()}.</p>
 *
 * <h2>CLI Quick-Start</h2>
 * <pre>
 *   javac *.java
 *   java  AlertSystem
 * </pre>
 *
 * @author  Derrick Johnson
 * @version 1.0
 * @since June 6, 2026
 * @see     NotificationMedium
 * @see     EmailService
 * @see     SMSService
 */
public class AlertSystem {

    // ------------------------------------------------------------------ fields

    /** The currently active notification channel. */
    private NotificationMedium medium;

    /**
     * Chronological log of every message dispatched during this session.
     * Entries are formatted as {@code "[CHANNEL] message"} exactly as
     * printed by the concrete {@link NotificationMedium} implementation.
     */
    private final ArrayList<String> log;

    // --------------------------------------------------------------- constructor

    /**
     * Constructs an {@code AlertSystem} with an initial delivery channel.
     *
     * @param medium the starting {@link NotificationMedium}; must not be {@code null}
     * @throws IllegalArgumentException if {@code medium} is {@code null}
     */
    public AlertSystem(NotificationMedium medium) {
        if (medium == null) {
            throw new IllegalArgumentException("Initial medium must not be null.");
        }
        this.medium = medium;
        this.log    = new ArrayList<>();
    }

    // --------------------------------------------------------------- public API

    /**
     * Replaces the active notification channel.
     *
     * <p>The swap takes effect immediately; the next call to
     * {@link #notifyUser(String)} will use the new medium.</p>
     *
     * @param medium the replacement {@link NotificationMedium}; must not be {@code null}
     * @throws IllegalArgumentException if {@code medium} is {@code null}
     */
    public void setMedium(NotificationMedium medium) {
        if (medium == null) {
            throw new IllegalArgumentException("Medium must not be null.");
        }
        this.medium = medium;
        System.out.println("[AlertSystem] Medium switched to: "
                + medium.getClass().getSimpleName());
    }

    /**
     * Dispatches {@code message} through the currently active medium and
     * appends an entry to the session log.
     *
     * @param message the notification text to deliver; must not be {@code null} or blank
     * @throws IllegalArgumentException if {@code message} is {@code null} or blank
     */
    public void notifyUser(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message must not be null or blank.");
        }
        medium.send(message);
        log.add("[" + medium.getClass().getSimpleName() + "] " + message);
    }

    /**
     * Returns an unmodifiable view of the session message log.
     *
     * <p>Each entry has the form {@code "[ClassName] message text"}.</p>
     *
     * @return a read-only {@link List} of log entries; never {@code null}
     */
    public List<String> getLog() {
        return Collections.unmodifiableList(log);
    }

    /**
     * Prints the complete session log to {@code System.out}.
     *
     * <p>Prints a header, each log entry on its own line, and a
     * footer showing the total count.</p>
     */
    public void printLog() {
        System.out.println("\n========== Session Log ==========");
        if (log.isEmpty()) {
            System.out.println("  (no messages sent yet)");
        } else {
            for (int i = 0; i < log.size(); i++) {
                System.out.printf("  %2d. %s%n", i + 1, log.get(i));
            }
        }
        System.out.println("  Total messages sent: " + log.size());
        System.out.println("=================================\n");
    }

    // --------------------------------------------------------------- main

    /**
     * Entry point — demonstrates the notification system from the CLI.
     *
     * <p>Run with:</p>
     * <pre>
     *   javac *.java
     *   java  AlertSystem
     * </pre>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        System.out.println("=== Notification System Demo ===\n");

        // --- Start with Email as the active medium --------------------
        AlertSystem alert = new AlertSystem(new EmailService());

        alert.notifyUser("Your account password was changed.");
        alert.notifyUser("Your monthly invoice is ready.");

        // --- Swap to SMS at runtime — no hierarchy changes needed -----
        alert.setMedium(new SMSService());

        alert.notifyUser("Verification code: 482910");
        alert.notifyUser("Your order has been dispatched.");

        // --- Swap to WhatsApp at runtime ------------------------------
        alert.setMedium(new WhatsAppService());

        alert.notifyUser("Thanks for joining WhatsApp!");

        // --- Swap back to Email ---------------------------------------
        alert.setMedium(new EmailService());

        alert.notifyUser("Welcome to our newsletter!");

        // --- Print the session log ------------------------------------
        alert.printLog();
    }
}
