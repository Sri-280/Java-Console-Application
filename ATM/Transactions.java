package ATM;

import java.util.ArrayList;

public class Transactions {
    private String type;
    private String name;
    private long amount= 0;

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
