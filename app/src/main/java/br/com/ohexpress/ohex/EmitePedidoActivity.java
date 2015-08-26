package br.com.ohexpress.ohex;


import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.dao.DatabaseHelper;
import br.com.ohexpress.ohex.dao.ItemPedidoDao;
import br.com.ohexpress.ohex.dao.ProdutoDao;
import br.com.ohexpress.ohex.interfaces.PedidoService;
import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EmitePedidoActivity extends ActionBarActivity {

    private Toolbar ohTopBar;
    private Pedido pedido;
    private TextView tvNomeProduto;
    public SimpleDraweeView imageProduto;
    private List<ItemPedido> cesta;
    private Usuario user;
    private AccountManager mAccountManager;
    private Spinner mSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emite_pedido);

        cesta = ((MyApplication) getApplication()).getMyCesta();
        pedido = new Pedido();
        pedido.setItem(cesta);

        //dh = new DatabaseHelper(EmitePedidoActivity.this);

        //pedido =  getIntent().getExtras().getParcelable("pedido");
        //itens = pedido.getItem();
        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = ((MyApplication) getApplication()).getUser();
        mAccountManager = AccountManager.get(EmitePedidoActivity.this);
        getAccounts(null);
        if(mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE).length == 0){
            //finish();
        }

        mSpinner = (Spinner) findViewById(R.id.sp_cad_form_pag);
        List<String> list = new ArrayList<String>();
        list.add("VISA CREDITO");
        list.add("VISA DEBITO");
        list.add("MASTERCARD MAESTRO");
        list.add("MASTERCARD CREDITO");
        list.add("ELLO CREDITO");
        list.add("ELLO DEBITO");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);




    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
            return true;
        }


        if (id == R.id.action_settings) {

            //Toast.makeText(this, loja.getImgLoja(), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_settings2) {


            return true;
        }

        if (id == R.id.action_menu_registrar) {

            Intent it = new Intent(EmitePedidoActivity.this, RegistrarActivity.class);
            startActivity(it);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void AddPedido(View view){



        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://10.0.3.2:8080/ohexpress/phone").build();

        PedidoService pedidoService = restAdapter.create(PedidoService.class);

        pedidoService.addPedido(user.getToken(), "cvc",pedido,
                new Callback<Pedido>(){


                    @Override
                    public void success(Pedido pedido, Response response) {


                        if (pedido.getId()!= null){

                            Toast.makeText(EmitePedidoActivity.this, "Pedido realizado com sucesso!", Toast.LENGTH_LONG).show();
                            cesta.clear();
                            finish();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(EmitePedidoActivity.this, "errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );


        return;
    }



    public void getAccounts(View view) {
        mAccountManager.getAuthTokenByFeatures(Constant.ACCOUNT_TYPE,
                Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR,
                null,
                EmitePedidoActivity.this,
                null,
                null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle bundle = future.getResult();

                            user.setAccountType(bundle.getString(AccountManager.KEY_ACCOUNT_TYPE));
                            user.setAccountName(bundle.getString(AccountManager.KEY_ACCOUNT_NAME));
                            user.setToken(bundle.getString(AccountManager.KEY_AUTHTOKEN));

                            //setUserDataFromServer();
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




}



