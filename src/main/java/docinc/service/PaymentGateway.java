package docinc.service;

public interface PaymentGateway {
    boolean charge(String userId, double amount);
}