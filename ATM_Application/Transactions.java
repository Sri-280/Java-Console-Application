package ATM_Application;

import java.util.ArrayList;

public class Transactions {
    private final String type;
    private final String name;
    private final long amount;

    public Transactions(String name, String type, long amount) {
        this.name = name;
        this.type = type;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
}