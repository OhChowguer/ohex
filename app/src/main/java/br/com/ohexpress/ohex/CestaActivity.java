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
    private Usuario user;
    private AccountManager mAccountManager;
    private List<ItemPedido> cesta;
    private Pedido pedido;
    private DecimalFormat format;
    private CreditCard card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);


        user = ((MyApplication) getApplication()).getUser();
        pedido = ((MyApplication) getApplication()).getMyPedido();
        mAccountManager = AccountManager.get(CestaActivity.this);
        //cesta = ((MyApplication) getApplication()).getMyCesta();
        cesta = pedido.getItem();
        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        format = new DecimalFormat("###0.00",otherSymbols);



        tvTotalCesta = (TextView) findViewById(R.id.tv_total_cesta);
        tvTotalCesta.setText(format.format(getTotal()));


        // FRAGMENT
        ItensCestaFragment frag = (ItensCestaFragment) getSupportFragmentManager().findFragmentByTag("mainFragCesta");
        if(frag == null) {
            frag = new ItensCestaFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_cesta, frag, "mainFragCesta");
            ft.commit();
        }


    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_loja, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            if (card!=null){
                Toast.makeText(CestaActivity.this,card.getNomeTitular(),Toast.LENGTH_LONG).show();
            }

            finish();

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public void EmitirPedido(View view){
        Account[] accs = mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE);
        final Dialog dialog = new Dialog(this);
        //Toast.makeText(CestaActivity.this,user.getCreditCard().get(0).getNomeTitular(),Toast.LENGTH_SHORT).show();

        if(accs.length==0) {
            getAccounts(null);}
        else if(user.getCreditCard().size() == 0){


        dialog.setContentView(R.layout.dialog_pedido);
        dialog.setTitle("Forma de pagamento");
        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.tv_nome_loga_dialog_pedido);
        text.setText("Voce nao possui uma forma de pagamento, gostaria de cadastrar uma?");
        //ImageView image = (ImageView) dialog.findViewById(R.id.image);
        //image.setImageResource(R.drawable.ic_busca);
        Button dialogButton = (Button) dialog.findViewById(R.id.bt_emite_ped_dialog);
            dialogButton.setText("Adicionar");
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
            } });
        dialog.show();}
        else {


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
            list.add("VISA CREDITO ****.****.****.9090");
            list.add("MASTER CARD  ****.*****");


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, list);
            ListView listView = (ListView) dialog.findViewById(R.id.listview);
            listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    card = user.getCreditCard().get(position);

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

    public double getTotal(){

        double total=0;

       for(ItemPedido itens : cesta){

           total = total+itens.getQuantidade()*itens.getProduto().getPreco();


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

    public void AddPedido(View view){


        pedido.setCard(card);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://10.0.3.2:8080/ohexpress/phone").build();

        PedidoService pedidoService = restAdapter.create(PedidoService.class);

        pedidoService.addPedido(user.getToken(),"cvc",pedido,
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


}
