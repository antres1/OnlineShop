package ante.resetar.shoppinglist;

public class PurchaseHistoryItem {
    private String status;
    private String price;
    private String date;

    public PurchaseHistoryItem(String status, String price, String date) {
        this.status = status;
        this.price = price;
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("DELIVERED") || status.equals("CANCELLED") || status.equals("WAITING FOR DELIVERY")) {
            this.status = status;
        } else {
            this.status = "CANCELLED";
        }
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
