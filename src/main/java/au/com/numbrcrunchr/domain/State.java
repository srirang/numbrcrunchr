package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class State implements Serializable {
    private static final long serialVersionUID = 1L;

    private State() {

    }

    public static final String VIC = "VIC";
    public static final String NSW = "NSW";
    public static final String QLD = "QLD";
    public static final String TAS = "TAS";
    public static final String ACT = "ACT";
    public static final String NT = "NT";
    public static final String WA = "WA";
    public static final String SA = "SA";

    public static final List<String> STATES;
    static {
        STATES = new ArrayList<String>(7);
        STATES.add(VIC);
        STATES.add(NSW);
        STATES.add(QLD);
        STATES.add(TAS);
        STATES.add(ACT);
        STATES.add(NT);
        STATES.add(WA);
        STATES.add(SA);
    }

    public static String normalise(String state) {
        if (state == null) {
            throw new DataException("Invalid state: null");
        }
        return state.toUpperCase().trim();
    }
}
