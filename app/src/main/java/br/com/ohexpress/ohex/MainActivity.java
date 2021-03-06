package br.com.ohexpress.ohex;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.LojaPorDistancia;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    private Toolbar ohTopBar;
    private Toolbar ohPesqBar;
    private Drawer nDrawerLeft;
    private AccountHeader accHeaderBuilder;
    private AccountManager mAccountManager;
    private Account[] accs;
    private MyApplication mApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        mApp = (MyApplication) getApplication();
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl_main_lay);

        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                return false;

            }
        });

        //List<CreditCard> cards = new ArrayList<CreditCard>(0);

        //CreditCard card = new CreditCard("99999999999","Visa",1,"DEBITO",new Date(),"Diego");
        //cards.add(card);

       //user = ((MyApplication) getApplication()).getUser();
        //user.setCreditCard(cards);
        mAccountManager = AccountManager.get(this);

        /*
        if(mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE).length == 0){
            finish();
        }*/





        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        ohTopBar.setTitle("");
        setSupportActionBar(ohTopBar);


        accs = mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE);

        if(accs.length>0) {
            getAccounts(null);}

        accHeaderBuilder = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bg)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        ohPesqBar = (Toolbar) findViewById(R.id.oh_pesquisa_toolbar);

        ohPesqBar.inflateMenu(R.menu.menu_toolbar_pesquisa);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = ohPesqBar.getMenu().findItem(R.id.action_searchable_activity_tb2);

        item.expandActionView();


        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ){
            searchView = (SearchView) item.getActionView();

        }
        else{
            searchView = (SearchView) MenuItemCompat.getActionView(item);

        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        nDrawerLeft = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(ohTopBar)
                        //.withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withAccountHeader(accHeaderBuilder)
                .withSavedInstance(savedInstanceState)
                .build();



        ohPesqBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            Intent it = null;

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return true;
            }
        });
        final TextView textView = (TextView) searchView.findViewById(R.id.search_src_text);
        textView.setTextColor(getResources().getColor(R.color.accent));
        textView.setHintTextColor(Color.LTGRAY);
        setnDrawerLeft(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ohTopBar.inflateMenu(R.menu.menu_main);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent itConfig = new Intent(MainActivity.this, ConfiguracaoActivity.class);
            startActivity(itConfig);

            return true;
        }

        if (id == R.id.action_settings2) {

            Intent it = new Intent(MainActivity.this, EstabelecimentoMainActivity.class);
            startActivity(it);
            return true;
        }

        if (id == R.id.action_menu_registrar) {

            Intent it = new Intent(MainActivity.this, EstabelecimentoPedidosActivity.class);
            startActivity(it);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public List<Loja> setDistancia(List<LojaPorDistancia> lpd) {

        List<Loja> lojas =  new ArrayList<Loja>();

        for (LojaPorDistancia lj: lpd)
        {
            Loja loja = lj.getLoja();
            loja.setDistNumber(lj.getDistNumber());
            loja.setDistText(lj.getDistText());
            lojas.add(loja);


        }
        return lojas;
    }


    public void myPedidos(View view){
        //getAccounts(null);
        //Toast.makeText(MainActivity.this, "Deu errado", Toast.LENGTH_LONG).show();
        //PedidosUtil pUtil = new PedidosUtil();

        //pUtil.getPedidos(this,user);

        Intent it = new Intent(MainActivity.this, ListaPedidoActivity.class);
        startActivity(it);

    }

    public void myFavoritas(View view){

        Intent itLProx = new Intent(MainActivity.this, LojasFavoritasActivity.class);
        startActivity(itLProx);

    }

    public void setnDrawerLeft(Boolean logout){

        accs = mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE);
        nDrawerLeft.removeAllItems();

        if(accs.length>0 ){
        nDrawerLeft.addItems(
                new SecondaryDrawerItem().withName("Lojas Proximos").withIcon(R.drawable.ic_local_amarelo),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName("Favoritos").withIcon(R.drawable.ic_favorito_amarelo),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName("QR Code").withIcon(R.drawable.ic_qr_amarelo),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName("Cesta de compras").withIcon(R.drawable.ic_cesto_amarelo),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName("Meus Pedidos").withIcon(R.drawable.ic_lista_amarelo),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName("Configurações").withIcon(R.drawable.ic_usuario_amarelo),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName("Sair").withIcon(R.drawable.ic_off_amarelo));
        nDrawerLeft.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(AdapterView<?> adapterView, View view, int position, long l, IDrawerItem iDrawerItem) {
                switch (position) {
                    case 0:
                        myActionButton(null);
                        break;
                    case 2:
                        myFavoritas(null);
                        break;
                    case 4:
                        myFavoritas(null);
                        break;
                    case 6:
                        Intent itLProx = new Intent(MainActivity.this, CestaActivity.class);
                        startActivity(itLProx);
                        break;
                    case 8:
                        Intent itPedido = new Intent(MainActivity.this, ListaPedidoActivity.class);
                        startActivity(itPedido);
                        break;
                    case 10:
                        Intent itConfig = new Intent(MainActivity.this, ConfiguracaoActivity.class);
                        startActivity(itConfig);
                        break;
                    case 12:
                        Account account = accs[0];
                        mAccountManager.removeAccount(account, new AccountManagerCallback<Boolean>() {
                            @Override
                            public void run(AccountManagerFuture<Boolean> future) {
                                accHeaderBuilder.clear();
                                ((MyApplication) getApplication()).setUser(new Usuario());
                                mApp.setUser(new Usuario());
                                setnDrawerLeft(true);
                            }
                        }, null);

                        break;
                }
                return false;
            }
        });
        }else{

            nDrawerLeft.addItems(
                    new SecondaryDrawerItem().withName("Lojas Proximos").withIcon(R.drawable.ic_local_amarelo),
                    new DividerDrawerItem(),
                    new SecondaryDrawerItem().withName("QR Code").withIcon(R.drawable.ic_qr_amarelo),
                    new DividerDrawerItem(),
                    new SecondaryDrawerItem().withName("Cesta de compras").withIcon(R.drawable.ic_cesto_amarelo),
                    new DividerDrawerItem(),
                    new SecondaryDrawerItem().withName("Entrar").withIcon(R.drawable.ic_off_amarelo),
                    new DividerDrawerItem(),
                    new SecondaryDrawerItem().withName("Registrar").withIcon(R.drawable.ic_account_plus_yellow));

            nDrawerLeft.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(AdapterView<?> adapterView, View view, int position, long l, IDrawerItem iDrawerItem) {
                    switch (position) {
                        case 0:
                            myActionButton(null);
                            break;
                        case 2:
                            //QR Code
                            break;
                        case 4:
                            Intent itLProx = new Intent(MainActivity.this, CestaActivity.class);
                            startActivity(itLProx);
                            break;
                        case 6:
                            getAccounts(null);
                            break;
                        case 8:
                            Intent intentReg = new Intent(MainActivity.this, RegistrarActivity.class);
                            startActivity(intentReg);
                            break;

                    }
                    return false;
                }
            });
        }

    }


    public boolean myActionButton (View view) {

        Intent itLProx = new Intent(MainActivity.this, LojasProximasActivity.class);
        startActivity(itLProx);

        return true;
    }

    public void onResume(){
        super.onResume();
        accs = mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE);


        if(accs.length==0){
            accHeaderBuilder.clear();
        }
        if(accs.length>0){
        getAccounts(null);}

        setnDrawerLeft(false);

    }

    public void getAccounts(View view) {
        mAccountManager.getAuthTokenByFeatures(Constant.ACCOUNT_TYPE,
                Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR,
                null,
                MainActivity.this,
                null,
                null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle bundle = future.getResult();
                            Log.i("Script", "MainActivity.getAccounts()");

                            if (accHeaderBuilder.getProfiles().isEmpty() && accs.length > 0) {
                                accHeaderBuilder.addProfile(new ProfileDrawerItem().
                                        withName(bundle.getString(AccountManager.KEY_ACCOUNT_NAME)).withEmail(bundle.getString(AccountManager.KEY_AUTHTOKEN))
                                        .withIcon(getResources().getDrawable(R.drawable.img_avatar)), 0);
                            }

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
