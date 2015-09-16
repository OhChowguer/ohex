package br.com.ohexpress.ohex;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RegistrarActivity extends ActionBarActivity implements View.OnClickListener,View.OnFocusChangeListener {


    private Map<String, String> params;
    private TextView nome;
    private TextView rAqui;
    private TextView login;
    private TextView email;
    private TextView senha;
    private TextView resenha;
    private Toolbar ohTopBar;
    private Resources resources;
    private Button btLogin;
    private Drawable warning;
    private AccountHeader accHeaderBuilder;
    private Usuario user;
    private AccountManager mAccountManager;
    private Account[] accs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        warning = (Drawable)getResources().getDrawable(R.drawable.ic_alert_circle);
        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAccountManager = AccountManager.get(RegistrarActivity.this);
        accs = mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE);

        nome = (TextView) findViewById(R.id.et_nome);
        nome.setOnFocusChangeListener(this);
        login = (TextView) findViewById(R.id.et_login);
        email = (TextView) findViewById(R.id.et_email);
        senha = (TextView) findViewById(R.id.et_senha);
        resenha = (TextView) findViewById(R.id.et_resenha);

        rAqui = (TextView) findViewById(R.id.tv_reg_aqui);
        rAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(this);
        resources = getResources();




    }

    private void resetTests() {

        nome.setText("");
        login.setText("");
        email.setText("");
        senha.setText("");
        resenha.setText("");
        rAqui.setText("");
        btLogin.setClickable(true);

    }



    @Override
    public void onClick(View v) {
        btLogin.setClickable(false);
        if (v.getId() == R.id.bt_login) {
            if (validateFields()) {
                /**
                 * Nesse ponto você poderia chamar um serviço de autenticação do usuário.
                 * Por questões de simplicidade e entendimento emitiremos somente um alerta
                 */
                //Toast.makeText(this, "Registrado", Toast.LENGTH_LONG).show();
                add();
            }
        }


    }

    private boolean validateFields() {
        String name = nome.getText().toString().trim();
        String log = login.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = senha.getText().toString().trim();
        String rsenha = resenha.getText().toString().trim();

        return (!isEmptyFields(name, log, mail, pass, rsenha) && hasSizeValid(name, log, mail, pass, rsenha));
    }

    //private boolean validateField(TextView view, ImageView img,String desc) {
     //   String texto = view.getText().toString().trim();

        //return (!isEmptyFields(name,log,mail,pass,rsenha) && hasSizeValid(name,log,mail,pass,rsenha));
    //    return (!isEmptyField(view,img,desc) && hasSizeValids(view,img,desc));
   // }

    private boolean isEmptyField(TextView view, ImageView img, String desc) {

        if (TextUtils.isEmpty(view.getText().toString().trim())) {
            view.requestFocus(); //seta o foco para o campo user
            view.setError("Campo "+desc+" não pode ser vazio", warning);
            btLogin.setClickable(true);
            img.setImageResource(R.drawable.ic_alert_circle);
            //nome.setError(resources.getString(R.string.login_user_required));
            return true;
        }



        return false;
    }

    private boolean hasSizeValids(TextView view, ImageView img, String desc) {
        if (!(view.getText().toString().trim().length() > 5)) {
            view.requestFocus();
            view.setError("Campo " +desc+ " deve ter mais de 5 caracteres", warning);
            btLogin.setClickable(true);
            img.setImageResource(R.drawable.ic_alert_circle);
            //nome.setError(resources.getString(R.string.login_user_size_invalid));
            return false;
        }

        return true;
    }
    private boolean isEmptyFields(String name, String log, String mail, String pass, String rsenha) {

        if (TextUtils.isEmpty(name)) {
            nome.requestFocus(); //seta o foco para o campo user
            nome.setError("Campo nome não pode ser vazio", warning);
            btLogin.setClickable(true);

            //nome.setError(resources.getString(R.string.login_user_required));
            return true;
        }
        else if (TextUtils.isEmpty(log)) {
            login.requestFocus(); //seta o foco para o campo password
            login.setError("Campo login não pode ser vazio", warning);
            btLogin.setClickable(true);

            //login.setError(resources.getString(R.string.login_password_required));
            return true;
        }
        else if (TextUtils.isEmpty(mail)) {
            email.requestFocus(); //seta o foco para o campo password
            email.setError("Campo email não pode ser vazio", warning);
            btLogin.setClickable(true);

            //email.setError(resources.getString(R.string.login_password_required));
            return true;
        }else if (TextUtils.isEmpty(pass)) {
            senha.requestFocus(); //seta o foco para o campo password
            senha.setError("Campo senha não pode ser vazio", warning);
            btLogin.setClickable(true);

            //senha.setError(resources.getString(R.string.login_password_required));
            return true;
        }
        else if (TextUtils.isEmpty(rsenha)) {
            resenha.requestFocus(); //seta o foco para o campo password
            resenha.setError("Campo confirmar senha não pode ser vazio", warning);
            btLogin.setClickable(true);

            //resenha.setError(resources.getString(R.string.login_password_required));
            return true;
        }

        return false;
    }

    private boolean hasSizeValid(String name, String log, String mail, String pass, String rsenha) {

        if (!(name.length() > 5)) {
            nome.requestFocus();
            nome.setError("Campo nome deve ter mais de 5 caracteres", warning);
            btLogin.setClickable(true);
            //nome.setError(resources.getString(R.string.login_user_size_invalid));
            return false;
        }
        else if (!(log.length() > 5)) {
            login.requestFocus(); //seta o foco para o campo password
            login.setError("Campo login deve ter mais de 5 caracteres", warning);
            btLogin.setClickable(true);
            //login.setError(resources.getString(R.string.login_password_required));
            return false;
        }
        else if (!(mail.length() > 5)) {
            email.requestFocus(); //seta o foco para o campo password
            email.setError("Campo email deve ter mais de 5 caracteres", warning);
            btLogin.setClickable(true);
            //email.setError(resources.getString(R.string.login_password_required));
            return false;
        } else if (!(pass.length() > 5)) {
            senha.requestFocus(); //seta o foco para o campo password
            senha.setError("Campo senha deve ter mais de 5 caracteres", warning);
            btLogin.setClickable(true);
            //senha.setError(resources.getString(R.string.login_password_required));
            return false;
        }
        else if (!(rsenha.equals(pass))) {
            resenha.requestFocus(); //seta o foco para o campo password
            resenha.setError("As senhas são diferentes", warning);
            btLogin.setClickable(true);
            //resenha.setError(resources.getString(R.string.login_password_required));
            return false;
        }
        return true;
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {

            finish();
            return true;
        }
        if (id == R.id.action_settings) {

            Toast.makeText(this, "Deu certo SILAAAAA", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_settings2) {

            Toast.makeText(this, "Eh Nois SILAAA", Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void add() {

        btLogin.setClickable(false);
        Usuario usuario = new Usuario(login.getText().toString(),nome.getText().toString(),email.getText().toString());

        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapterPedido.create(UserService.class);

        userService.registrar(senha.getText().toString(),usuario,
                new Callback<Usuario>() {


                    @Override
                    public void success(Usuario usuario, retrofit.client.Response response) {

                        if (usuario.getId() != null){

                            ((MyApplication) getApplication()).setUser(usuario);
                            Toast.makeText(RegistrarActivity.this,"Usuario "+ usuario.getLogin()+" registrado com sucesso!",Toast.LENGTH_LONG).show();
                            getAccounts(null);
                            finish();

                        }else {


                            Toast.makeText(RegistrarActivity.this, "Erro no registro", Toast.LENGTH_SHORT).show();
                            btLogin.setClickable(true);

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        //Toast.makeText(RegistrarActivity.this, "Deu errado =(  " + error, Toast.LENGTH_LONG).show();
                        Toast.makeText(RegistrarActivity.this, "Não foi possivel cadastrar o usuario, erro na conexao!", Toast.LENGTH_SHORT).show();


                    }
                }

        );

    }

    public void getAccounts(View view) {
        mAccountManager.getAuthTokenByFeatures(Constant.ACCOUNT_TYPE,
                Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR,
                null,
                RegistrarActivity.this,
                null,
                null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle bundle = future.getResult();
                            Log.i("Script", "MainActivity.getAccounts()");

                            getUser(bundle.getString(AccountManager.KEY_AUTHTOKEN));
                        } catch (OperationCanceledException e) {
                            e.printStackTrace();
                        } catch (AuthenticatorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                null);
    }

    public void getUser(String token){


        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapterPedido.create(UserService.class);

        userService.getuser(token,
                new Callback<Usuario>() {


                    @Override
                    public void success(Usuario usuario, Response response) {

                        ((MyApplication) getApplication()).setUser(usuario);
                        user = usuario;


                        //Toast.makeText(context, user.getToken(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        //Toast.makeText(context, "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );
    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        int id = v.getId();

       /* if (id == R.id.et_nome){

            if (TextUtils.isEmpty(nome.getText().toString().trim())) {
                nome.requestFocus(); //seta o foco para o campo user
                nome.setError("Campo nome não pode ser vazio", warning);
                btLogin.setClickable(true);
                ivNome.setImageResource(R.drawable.ic_alert_circle);
                //nome.setError(resources.getString(R.string.login_user_required));
            }

        }*/

        //if ((!isEmptyField((TextView) v,ivNome,"nome") && hasSizeValids((TextView) v,ivNome,"nome"))) {
        //    ivNome.setImageResource(R.drawable.ic_checkbox_marked_circle);
        //}


    }
}
