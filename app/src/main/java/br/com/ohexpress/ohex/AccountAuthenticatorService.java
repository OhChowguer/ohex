package br.com.ohexpress.ohex;

/**
 * Created by rafaelchowman on 13/08/15.
 */

import android.content.Intent;
import android.os.IBinder;
import android.app.Service;

import br.com.ohexpress.ohex.util.AccountAuthenticator;

public class AccountAuthenticatorService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        //Log.i("Script", "AccountAuthenticatorService.onBind()");
        AccountAuthenticator mAccountAuthenticator = new AccountAuthenticator(AccountAuthenticatorService.this);
        return(mAccountAuthenticator.getIBinder());
    }

}

