package br.com.ohexpress.ohex.util;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import br.com.ohexpress.ohex.LoginActivity;
import br.com.ohexpress.ohex.util.Constant;

/**
 * Created by rafaelchowman on 13/08/15.
 */
public class AccountAuthenticator extends AbstractAccountAuthenticator {
    public Context mContext;


    public AccountAuthenticator(Context context) {
        super(context);
        mContext = context;
    }



    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response,
                                 String accountType) {
        //Log.i("Script", "AccountAuthenticator.editProperties()");
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
                             String accountType, String authTokenType,
                             String[] requiredFeatures, Bundle options)
            throws NetworkErrorException {
        //Log.i("Script", "AccountAuthenticator.addAccount()");

        Intent it = new Intent(mContext,LoginActivity.class);
        it.putExtra(Constant.ARG_ACCOUNT_TYPE, accountType);
        it.putExtra(Constant.ARG_AUTH_TYPE, authTokenType != null ? authTokenType : Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR);
        it.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, it);

        return(bundle);
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response,
                                     Account account, Bundle options) throws NetworkErrorException {
        //Log.i("Script", "AccountAuthenticator.confirmCredentials()");

        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response,
                               Account account, String authTokenType, Bundle options)
            throws NetworkErrorException {
        //Log.i("Script", "AccountAuthenticator.getAuthToken()");

        AccountManager mAccountManager = AccountManager.get(mContext);
        String token = mAccountManager.peekAuthToken(account, authTokenType);

        if(!TextUtils.isEmpty(token)){
            //Log.i("Script", "AccountAuthenticator.getAuthToken() : if(!TextUtils.isEmpty(token))");
            Bundle bundle = new Bundle();
            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            bundle.putString(AccountManager.KEY_AUTHTOKEN, token);
            return(bundle);
        }

        Intent it = new Intent(mContext, LoginActivity.class);
        it.putExtra(Constant.ARG_ACCOUNT_TYPE, account.type);
        it.putExtra(Constant.ARG_ACCOUNT_NAME, account.name);
        it.putExtra(Constant.ARG_AUTH_TYPE, authTokenType);
        it.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, it);

        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        //Log.i("Script", "AccountAuthenticator.getAuthTokenLabel()");
        if(authTokenType.equals(Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR)){
            return("Acesso de comprador");
        }
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response,
                                    Account account, String authTokenType, Bundle options)
            throws NetworkErrorException {
        /*Log.i("Script", "AccountAuthenticator.updateCredentials()");

        Intent it = new Intent(mContext, PasswordActivity.class);
        it.putExtra(Constant.ARG_ACCOUNT_TYPE, account.type);
        it.putExtra(Constant.ARG_ACCOUNT_NAME, account.name);
        it.putExtra(Constant.ARG_AUTH_TYPE, authTokenType);
        it.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, it);*/

        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response,
                              Account account, String[] features) throws NetworkErrorException {
        //Log.i("Script", "AccountAuthenticator.hasFeatures()");
        return null;
    }

}

