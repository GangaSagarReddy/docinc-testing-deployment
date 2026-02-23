package docinc.service;

import docinc.model.PrintRequest;
import docinc.model.UserPreference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrintRequestServiceTest {

    
    @Test
    void paperlessUserCannotRequestPrint() {
        PaymentGateway stubPay = (userId, amount) -> true; 
        NotificationSender stubNotify = (userId, msg) -> {
        }; 

        PrintRequestService service = new PrintRequestService(stubPay, stubNotify);

        assertThrows(IllegalStateException.class,
                () -> service.requestPrintedCopy("U1", "S1", new UserPreference(true)));
    }

    
    @Test
    void paymentSuccessCreatesPrintRequestAndSendsNotification() {
        PaymentGateway payMock = mock(PaymentGateway.class);
        NotificationSender notifyMock = mock(NotificationSender.class);

        when(payMock.charge("U1", 2.00)).thenReturn(true);

        PrintRequestService service = new PrintRequestService(payMock, notifyMock);

        PrintRequest pr = service.requestPrintedCopy("U1", "S100", new UserPreference(false));

        assertEquals("S100", pr.getStatementId());
        assertEquals("CREATED", pr.getStatus());

        verify(notifyMock).send(eq("U1"), contains("S100"));
    }

    
    @Test
    void paymentFailureStopsRequest() {
        PaymentGateway failingStub = (userId, amount) -> false; 
        NotificationSender notifyMock = mock(NotificationSender.class);

        PrintRequestService service = new PrintRequestService(failingStub, notifyMock);

        assertThrows(IllegalStateException.class,
                () -> service.requestPrintedCopy("U2", "S200", new UserPreference(false)));

        verify(notifyMock, never()).send(anyString(), anyString());
    }
}
