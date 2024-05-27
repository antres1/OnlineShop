package ante.resetar.shoppinglist;

public class Binder extends IMyAidlInterface.Stub {
    private boolean isSale;
    private String username;

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public boolean getSale() {
        return this.isSale;
    }

    @Override
    public void setSale(boolean sale){
        this.isSale = sale;
    }
}
