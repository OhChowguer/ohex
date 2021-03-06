package br.com.ohexpress.ohex;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.ohexpress.ohex.adapters.CestaDialogAdapter;
import br.com.ohexpress.ohex.fragment.ItensCestaFragment;
import br.com.ohexpress.ohex.interfaces.PedidoService;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.CreditCard;
import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CestaActivity extends ActionBarActivity {

    private Toolbar ohTopBar;
    private TextView tvTotalCesta;
    private TextView tvLabelTotalCesta;
    private TextView tvLabelSifrao;
    private View saparator;
    //private Usuario user;
    private MyApplication mApp;
    private AccountManager mAccountManager;
    private List<ItemPedido> cesta;
    private Pedido pedido;
    private DecimalFormat format;
    private CreditCard card;
    private ItensCestaFragment frag;
    private FragmentTransaction ft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);
        //user = ((MyApplication) getApplication()).getUser();
        pedido = ((MyApplication) getApplication()).getMyPedido();
        mAccountManager = AccountManager.get(CestaActivity.this);
        mApp = ((MyApplication) getApplication());
        //cesta = ((MyApplication) getApplication()).getMyCesta();
        cesta = pedido.getItem();
        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        format = new DecimalFormat("###0.00", otherSymbols);
        tvTotalCesta = (TextView) findViewById(R.id.tv_total_cesta);
        tvTotalCesta.setText(format.format(getTotal()));
        tvTotalCesta = (TextView) findViewById(R.id.tv_total_cesta);
        tvTotalCesta.setText(format.format(getTotal()));
        tvLabelTotalCesta = (TextView) findViewById(R.id.tv_label_total_cesta);
        tvLabelSifrao = (TextView) findViewById(R.id.tv_label_sifrao);
        saparator = (View) findViewById(R.id.item_separator);
        if (cesta.isEmpty()){
            limpaActivity();
        }
        // FRAGMENT
        frag = (ItensCestaFragment) getSupportFragmentManager().findFragmentByTag("mainFragCesta");
        if (frag == null) {
            frag = new ItensCestaFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_cesta, frag, "mainFragCesta");
            ft.commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cesta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();

            return true;
        }
        if (id == R.id.action_limpar_cesta) {

            ((MyApplication) getApplication()).setMyPedido(new Pedido(true));
            limpaActivity();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void EmitirPedido(View view) {
        Account[] accs = mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE);
        final Dialog dialog = new Dialog(this);;
        if (accs.length == 0) {
            getAccounts(null);
        } else if (mApp.getUser().getCreditCard().size() == 0) {
            dialog.setContentView(R.layout.dialog_pedido_add_card);
            dialog.setTitle("Forma de pagamento");
            TextView text = (TextView) dialog.findViewById(R.id.tv_nome_loga_dialog_pedido);
            //text.setText("Voce nao possui uma forma de pagamento, gostaria de cadastrar uma?");
            //ImageView image = (ImageView) dialog.findViewById(R.id.image);
            //image.setImageResource(R.drawable.ic_busca);
            Button dialogButtonSim = (Button) dialog.findViewById(R.id.bt_emite_ped_dialog_sim);
            Button dialogButtonNao = (Button) dialog.findViewById(R.id.bt_emite_ped_dialog_nao);
            //dialogButton.setText("Adicionar");
            // if button is clicked, close the custom dialog
            dialogButtonSim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CestaActivity.this,"Abre tela de cadastro de pedido",Toast.LENGTH_SHORT).show();
                }
            });
            dialogButtonNao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {


            dialog.setContentView(R.layout.dialog_pedido);
            dialog.setTitle("Forma de Pagamento");
            TextView text = (TextView) dialog.findViewById(R.id.tv_nome_loga_dialog_pedido);
            text.setText(pedido.getLoja().getNome());
            TextView total = (TextView) dialog.findViewById(R.id.tv_total_pedido_dialog);
            total.setText(format.format(getTotal()));
            Button dialogButton = (Button) dialog.findViewById(R.id.bt_emite_ped_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddPedido(null);

                    dialog.dismiss();
                }
            });
            //ListView listaCads= (ListView) dialog.findViewById(R.id.listview);

            //CestaDialogAdapter adapter = new CestaDialogAdapter(CestaActivity.this, user.getCreditCard());
            //listaCads.setAdapter(adapter);

            //RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.rg_dialog_pedido);

            List<String> list = new ArrayList<String>();

            for (CreditCard card: mApp.getUser().getCreditCard()){
            list.add(card.getNome()+" - ****.****."+card.getNumeroCard().substring(7));
          }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, list);
            ListView listView = (ListView) dialog.findViewById(R.id.listview);
            listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    card = mApp.getUser().getCreditCard().get(position);

                }
            });
            listView.setAdapter(adapter);


            dialog.show();


            /*listDialog = new Dialog(this);
            listDialog.setTitle("Forma de pagamento");
            LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = li.inflate(R.layout.dialog_pedido, null, false);
            listDialog.setContentView(v);
            listDialog.setCancelable(true);
            ListView list1 = (ListView) listDialog.findViewById(R.id.listview);
            Button dialogButton = (Button) dialog.findViewById(R.id.bt_emite_ped_dialog);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    AddPedido(null);

                    listDialog.dismiss();
                } });
            //list1.setOnItemClickListener(CestaActivity.this);
            list1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, val));
//now that the dialog is set up, it's time to show it
            listDialog.show();*/


        }

        //Intent it = new Intent(CestaActivity.this, EmitePedidoActivity.class);
        //startActivity(it);

        return;
    }

    public double getTotal() {

        double total = 0;

        for (ItemPedido itens : cesta) {

            total = total + itens.getQuantidade() * itens.getProduto().getPreco();


        }

        return total;
    }

    public void getAccounts(View view) {
        mAccountManager.getAuthTokenByFeatures(Constant.ACCOUNT_TYPE,
                Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR,
                null,
                CestaActivity.this,
                null,
                null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle bundle = future.getResult();

                            mApp.getUser().setAccountType(bundle.getString(AccountManager.KEY_ACCOUNT_TYPE));
                            mApp.getUser().setAccountName(bundle.getString(AccountManager.KEY_ACCOUNT_NAME));
                            mApp.getUser().setToken(bundle.getString(AccountManager.KEY_AUTHTOKEN));
                            getUser(mApp.getUser().getToken());


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

    public void AddPedido(View view) {


        pedido.setCard(card);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        PedidoService pedidoService = restAdapter.create(PedidoService.class);

        pedidoService.addPedido(mApp.getUser().getToken(), pedido,
                new Callback<Pedido>() {


                    @Override
                    public void success(Pedido ped, Response response) {


                        if (ped.getId() != null) {

                            Toast.makeText(CestaActivity.this, "Pedido realizado com sucesso!", Toast.LENGTH_LONG).show();
                            pedido.getItem().clear();
                            finish();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(CestaActivity.this, "errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );


        return;
    }

    public void refreshTotal(){

        tvTotalCesta.setText(format.format(getTotal()));


    }

    public void limpaActivity(){

        Button botao = (Button) findViewById(R.id.bt_finaliza_pedido);
        botao.setVisibility(View.GONE);
        tvTotalCesta.setVisibility(View.GONE);
        tvLabelTotalCesta.setVisibility(View.GONE);
        tvLabelSifrao.setVisibility(View.GONE);
        saparator.setVisibility(View.GONE);
        if (frag!=null) {
            frag.getView().setVisibility(View.GONE);

        }
    }

    public void getUser(String token){


        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapterPedido.create(UserService.class);

        userService.getuser(token,
                new Callback<Usuario>() {


                    @Override
                    public void success(Usuario usuario, Response response) {

                        //((MyApplication) getApplication()).setUser(usuario);
                        mApp.setUser(usuario);


                        //Toast.makeText(context, user.getToken(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        //Toast.makeText(context, "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );
    }


}
