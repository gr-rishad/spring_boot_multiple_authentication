package multiple_authentication.multiple_authentication.repo;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ReceiptManager {

    private Set<String> receiptNoList;

    public ReceiptManager() {
        receiptNoList = new HashSet<String>();
    }

    public void add(String receipt) {
        receiptNoList.add(receipt);
    }

    public boolean contains(String receipt) {
        return receiptNoList.contains(receipt);
    }
}
