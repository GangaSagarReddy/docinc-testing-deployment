package docinc.model;

public class UserPreference {
    private final boolean paperlessEnabled;

    public UserPreference(boolean paperlessEnabled) {
        this.paperlessEnabled = paperlessEnabled;
    }

    public boolean isPaperlessEnabled() {
        return paperlessEnabled;
    }
}