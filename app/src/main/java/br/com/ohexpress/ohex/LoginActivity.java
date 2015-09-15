package br.com.ohexpress.ohex;


import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends AccountAuthenticatorActivity  {


    private EditText login;
    private TextView email;
    private TextView cliqueAqui;
    private EditText senha;
    private Toolbar ohTopBar;
    private AccountManager mAccountManager;
    private Usuario user;
    private ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        senha = (EditText) findViewById(R.id.tvDataExpCard);
        login = (EditText) findViewById(R.id.et_login_act);
        //login.color;
        user = ((MyApplication) getApplication()).getUser();
        user.setAccountType(getIntent().getStringExtra(Constant.ARG_ACCOUNT_TYPE));
        user.setAccountName(getIntent().getStringExtra(Constant.ARG_ACCOUNT_NAME));
        user.setAuthTokenType(getIntent().getStringExtra(Constant.ARG_AUTH_TYPE));
        mAccountManager = AccountManager.get(LoginActivity.this);
        cliqueAqui = (TextView) findViewById(R.id.tv_clique_aqui);
        cliqueAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        voltar = (ImageView) findViewById(R.id.iv_voltar_login);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(login.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    public void signIn(View view) {

        final Intent it = new Intent();

        findViewById(R.id.bt_login).setEnabled(false);
        user.setLogin(((EditText) findViewById(R.id.et_login_act)).getText().toString());
        user.setSenha(((EditText) findViewById(R.id.tvDataExpCard)).getText().toString());

        RestAdapter restAdapterUser = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapterUser.create(UserService.class);

        userService.gettoken(user.getLogin(),user.getSenha(),
                new Callback<String>() {


                    @Override
                    public void success(String token, Response response) {

                        //Toast.makeText(LoginActivity.this, "Deu erro na conexao!"+token, Toast.LENGTH_SHORT).show();

                        it.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constant.ACCOUNT_TYPE);
                        it.putExtra(AccountManager.KEY_ACCOUNT_NAME, user.getLogin());
                        it.putExtra(AccountManager.KEY_AUTHTOKEN, token);
                        finish(it);

                        //listaPedido = (ArrayList<Pedido>) pedidos;
                        //Intent itLProx = new Intent(MainActivity.this, EstabelecimentoMainActivity.class);
                        //itLProx.putParcelableArrayListExtra("pedidos", listaPedido);
                        //startActivity(itLProx);

                        //findViewById(R.id.bt_login).setEnabled(true);


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        findViewById(R.id.bt_login).setEnabled(true);

                        Toast.makeText(LoginActivity.this, "Deu erro na conexao!"+error, Toast.LENGTH_SHORT).show();


                    }
                }

        );


    }

    public void finish(Intent it){
        //Log.i("Script", "AuthenticatorActivity.finish()");

        String accountType = it.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        String accountName = it.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String token = it.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        Account account = new Account(accountName, accountType);
        int countAccounts = mAccountManager.getAccountsByType(accountType).length;

        if(token == null){
            //Log.i("Script", "AuthenticatorActivity.finish() : if(token.equalsIgnoreCase(\"null\"))");
            Toast.makeText(LoginActivity.this, "Dados de acesso incorretos!", Toast.LENGTH_SHORT).show();
            findViewById(R.id.bt_login).setEnabled(true);
            return;
        }

        mAccountManager.addAccountExplicitly(account, null, null);
        mAccountManager.setAuthToken(account, user.getAuthTokenType(), token);

        setAccountAuthenticatorResult(it.getExtras());
        finish();

        if(countAccounts == 0){
            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }



}
