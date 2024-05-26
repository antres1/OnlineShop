package ante.resetar.shoppinglist;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SaleService extends Service {

    private Binder binder = null;

    @Override
    public IBinder onBind(Intent intent) {
        if (binder == null) {
            binder = new Binder();
        }
        return binder;

    }
}