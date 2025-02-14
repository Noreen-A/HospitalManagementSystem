import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Billing {
    private static final Logger LOGGER = Logger.getLogger(Billing.class.getName());
    private static final String CURRENCY = "EGP";

    private final int patientID;
    private BigDecimal outstandingBalance;
    private final List<PaymentRecord> paymentHistory;
    private BigDecimal overpaidAmount = BigDecimal.ZERO;

    public Billing(int patientID) {
        this.patientID = patientID;
        this.outstandingBalance = BigDecimal.ZERO;
        this.paymentHistory = new ArrayList<>();
    }

    public void generateBill(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.log(Level.SEVERE, "Invalid bill amount.");
            return;
        }
        outstandingBalance = outstandingBalance.add(amount);
        paymentHistory.add(new PaymentRecord("Bill Added", amount));
        LOGGER.log(Level.INFO, "Bill generated: Patient ID {0}, Amount: {1} {2}", new Object[]{patientID, amount, CURRENCY});
    }

    public BigDecimal addPayment(BigDecimal payment) {
        if (payment == null || payment.compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.log(Level.SEVERE, "Invalid payment amount.");
            return outstandingBalance;
        }

        if (payment.compareTo(outstandingBalance) > 0) {
            overpaidAmount = payment.subtract(outstandingBalance);
            outstandingBalance = BigDecimal.ZERO;
            LOGGER.log(Level.INFO, "Overpayment received! Change due: {0} {1}", new Object[]{overpaidAmount, CURRENCY});
        } else {
            outstandingBalance = outstandingBalance.subtract(payment);
        }

        paymentHistory.add(new PaymentRecord("Payment Received", payment));
        LOGGER.log(Level.INFO, "Payment received: {0} {1}, Outstanding balance: {2} {3}",
                new Object[]{payment, CURRENCY, outstandingBalance, CURRENCY});

        return overpaidAmount;
    }

    public String getPaymentStatus() {
        return outstandingBalance.compareTo(BigDecimal.ZERO) > 0
                ? "Outstanding Balance: " + outstandingBalance + " " + CURRENCY
                : "All dues are cleared.";
    }

    public List<PaymentRecord> getPaymentHistory() {
        return Collections.unmodifiableList(paymentHistory);
    }

    public int getPatientID() {
        return patientID;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public BigDecimal getOverpaidAmount() {
        return overpaidAmount;
    }

    public static class PaymentRecord {
        private final String type;
        private final BigDecimal amount;
        private final String timestamp;

        public PaymentRecord(String type, BigDecimal amount) {
            this.type = type;
            this.amount = amount;
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        @Override
        public String toString() {
            return "[" + timestamp + "] " + type + ": " + amount + " " + CURRENCY;
        }
    }
}
