package docinc.service;

public interface NotificationSender {
    void send(String userId, String message);
}