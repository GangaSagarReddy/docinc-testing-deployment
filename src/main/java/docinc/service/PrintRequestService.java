package docinc.service;

import docinc.model.PrintRequest;
import docinc.model.UserPreference;

public class PrintRequestService {
    public static final double PRINT_FEE = 2.00;

    private final PaymentGateway paymentGateway;
    private final NotificationSender notificationSender;

    public PrintRequestService(PaymentGateway paymentGateway, NotificationSender notificationSender) {
        this.paymentGateway = paymentGateway;
        this.notificationSender = notificationSender;
    }

    public PrintRequest requestPrintedCopy(String userId, String statementId, UserPreference pref) {
        if (pref.isPaperlessEnabled()) {
            throw new IllegalStateException("Paperless users cannot request printed copy.");
        }

        boolean paid = paymentGateway.charge(userId, PRINT_FEE);
        if (!paid) {
            throw new IllegalStateException("Payment failed.");
        }

        PrintRequest pr = new PrintRequest(statementId, "CREATED");
        notificationSender.send(userId, "Print request created for " + statementId);
        return pr;
    }
}