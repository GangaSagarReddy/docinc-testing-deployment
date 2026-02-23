package docinc.model;

public class PrintRequest {
    private final String statementId;
    private final String status;

    public PrintRequest(String statementId, String status) {
        this.statementId = statementId;
        this.status = status;
    }

    public String getStatementId() {
        return statementId;
    }

    public String getStatus() {
        return status;
    }
}