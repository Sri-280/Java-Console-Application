package ATM;
import java.util.ArrayList;

public class Accounts {
    private String id;
    private String pin;
    private final ArrayList<Transactions> transactions = new ArrayList<>();

    public Accounts(String id,String pin){
        this.id=id;
        this.pin=pin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }


    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }
}
