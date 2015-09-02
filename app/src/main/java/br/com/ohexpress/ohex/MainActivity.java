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
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.fragment.MainFragment;
import br.com.ohexpress.ohex.interfaces.LojaService;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.LojaPorDistancia;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.PedidosUtil;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    private Toolbar ohTopBar;
    private Toolbar ohPesqBar;
    private Toolbar ohBaixoBar;
    private ArrayList<Loja> lista;
    private ArrayList<LojaPorDistancia> listaDist;
    private ArrayList<Pedido> listaPedido;
    private Drawer nDrawerLeft;
    private Drawer nDrawerRight;
    private AccountHeader accHeaderBuilder;
    private Usuario user;
    private AccountManager mAccountManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        user = ((MyApplication) getApplication()).getUser();
        mAccountManager = AccountManager.get(MainActivity.this);

        /*
        if(mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE).length == 0){
            finish();
        }*/



        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);


        Account[] accs = mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE);

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



        nDrawerLeft = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(ohTopBar)
                //.withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withAccountHeader(accHeaderBuilder)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Menu"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Lugares Proximos").withIcon(R.drawable.ic_map_gray_25),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Favoritos").withIcon(R.drawable.ic_map_gray_25),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("QR Code").withIcon(R.drawable.ic_map_gray_25),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Cesta de compras").withIcon(R.drawable.ic_map_gray_25),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Meus Pedidos").withIcon(R.drawable.ic_map_gray_25),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Configurações").withIcon(R.drawable.ic_map_gray_25),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Sair").withIcon(R.drawable.ic_map_gray_25))

                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(AdapterView<?> adapterView, View view, int position, long l, IDrawerItem iDrawerItem) {

                                       switch (position) {
                                           case 1:

                                               break;
                                           case 2:
                                               myActionButton(null);
                                               break;
                                           case 3:

                                               break;
                                           case 4:
                                               myFavoritas(null);
                                               break;
                                           case 10:
                                               myPedidos(null);
                                               break;
                                       }


                                        return false;
                                    }
                                })
                                .build();

        ohPesqBar = (Toolbar) findViewById(R.id.oh_pesquisa_toolbar);

        ohPesqBar.inflateMenu(R.menu.menu_toolbar_pesquisa);



        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = ohPesqBar.getMenu().findItem(R.id.action_searchable_activity_tb2);
        item.setIcon(R.drawable.ic_busca_xx_amarelo);

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ){
            searchView = (SearchView) item.getActionView();
        }
        else{
            searchView = (SearchView) MenuItemCompat.getActionView(item);
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));


        ohPesqBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            Intent it = null;

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return true;
            }
        });
        TextView textView = (TextView) searchView.findViewById(R.id.search_src_text);
        textView.setTextColor(Color.BLUE);
        textView.setHintTextColor(Color.GREEN);






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

            if (user.getId()==null) {

                RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

                UserService userService = restAdapterPedido.create(UserService.class);

                userService.getuser(user.getToken(),
                        new Callback<Usuario>() {


                            @Override
                            public void success(Usuario usuario, Response response) {

                                ((MyApplication) getApplication()).setUser(usuario);
                                //listaPedido = (ArrayList<Pedido>) pedidos;
                                //Intent itLProx = new Intent(MainActivity.this, EstabelecimentoMainActivity.class);
                                //itLProx.putParcelableArrayListExtra("pedidos", listaPedido);
                                //tartActivity(itLProx);
                                Toast.makeText(MainActivity.this, user.getToken(), Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void failure(RetrofitError error) {

                                Toast.makeText(MainActivity.this, "Deu errado" + error, Toast.LENGTH_LONG).show();


                            }
                        }

                );
            }
            else{
                Toast.makeText(MainActivity.this, user.getCreditCard().get(0).getNomeTitular(), Toast.LENGTH_SHORT).show();
            }

            //PedidosUtil pUtil = new PedidosUtil();

            //pUtil.getPedidos(this,user);

            return true;
        }

        if (id == R.id.action_settings2) {

            Intent it = new Intent(MainActivity.this, CadFormaDePagamentoActivity.class);
            startActivity(it);
            return true;
        }

        if (id == R.id.action_menu_registrar) {

            Intent it = new Intent(MainActivity.this, RegistrarActivity.class);
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

        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();
        UserService userService = restAdapterPedido.create(UserService.class);

        userService.listarfavoritas(user.getToken(),
                new Callback<Usuario>() {


                    @Override
                    public void success(Usuario usuario, Response response) {


                        lista = (ArrayList<Loja>) usuario.getFavoritas();
                        Intent itLProx = new Intent(MainActivity.this, LojasProximasActivity.class);
                        itLProx.putParcelableArrayListExtra("lojas", lista);
                        startActivity(itLProx);


                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(MainActivity.this, "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );


    }


    public boolean myActionButton (View view) {


                RestAdapter restAdapterDist = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

                LojaService lojaServiceDist = restAdapterDist.create(LojaService.class);

                lojaServiceDist.listarLojasDist(
                        new Callback<List<LojaPorDistancia>>() {


                            @Override
                            public void success(List<LojaPorDistancia> lojas, Response response) {

                                lista = (ArrayList<Loja>) setDistancia(lojas);
                                Intent itLProx = new Intent(MainActivity.this, LojasProximasActivity.class);
                                itLProx.putParcelableArrayListExtra("lojas", lista);
                                startActivity(itLProx);


                            }

                            @Override
                            public void failure(RetrofitError error) {

                                Toast.makeText(MainActivity.this, "Deu errado" + error, Toast.LENGTH_LONG).show();


                            }
                        }

                );




        return true;
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
                            //Log.i("Script", "MainActivity.getAccounts() : AccountType = "+bundle.getString(AccountManager.KEY_ACCOUNT_TYPE));
                            //Log.i("Script", "MainActivity.getAccounts() : AccountName = "+bundle.getString(AccountManager.KEY_ACCOUNT_NAME));
                            //Log.i("Script", "MainActivity.getAccounts() : Token = " + bundle.getString(AccountManager.KEY_AUTHTOKEN));

                            user.setAccountType(bundle.getString(AccountManager.KEY_ACCOUNT_TYPE));
                            user.setAccountName(bundle.getString(AccountManager.KEY_ACCOUNT_NAME));
                            user.setToken(bundle.getString(AccountManager.KEY_AUTHTOKEN));


                            accHeaderBuilder.addProfiles(new ProfileDrawerItem().
                                    withName(user.getAccountName()).withEmail("chowman@gmail.com")
                                    .withIcon(getResources().getDrawable(R.drawable.img_avatar)));



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




                /*

                 switch (item.getItemId()) {


                    case R.id.item_tb_prox:

                    RestAdapter restAdapterDist = new RestAdapter.Builder().setEndpoint("http://10.0.3.2:8080/ohexpress/phone/listalojaproxima").build();

                    LojaService lojaServiceDist = restAdapterDist.create(LojaService.class);

                    lojaServiceDist.listarLojasDist(
                            new Callback<List<LojaPorDistancia>>() {


                                @Override
                                public void success(List<LojaPorDistancia> lojas, Response response) {

                                    lista = (ArrayList<Loja>) setDistancia(lojas);
                                    Intent itLProx = new Intent(MainActivity.this, LojasProximasActivity.class);
                                    itLProx.putParcelableArrayListExtra("lojas", lista);
                                    startActivity(itLProx);


                                }

                                @Override
                                public void failure(RetrofitError error) {

                                    Toast.makeText(MainActivity.this, "Deu errado" + error, Toast.LENGTH_LONG).show();


                                }
                            }

                    );
                        break;

                    case R.id.item_tb_pedido:
                        //nDrawerLeft.openDrawer();
                        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint("http://10.0.3.2:8080/ohexpress/phone").build();

                        PedidoService pedidoService = restAdapterPedido.create(PedidoService.class);

                        pedidoService.listarPedido(
                                new Callback<List<Pedido>>() {


                                    @Override
                                    public void success(List<Pedido> pedidos, Response response) {

                                        listaPedido = (ArrayList<Pedido>) pedidos;
                                        Intent itLProx = new Intent(MainActivity.this, ListaPedidoActivity.class);
                                        itLProx.putParcelableArrayListExtra("pedidos", listaPedido);
                                        startActivity(itLProx);


                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                        //Toast.makeText(getActivity(), "Deu errado" + error, Toast.LENGTH_LONG).show();


                                    }
                                }

                        );




                        break;

                    case R.id.item_tb_favoritos:



                        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://10.0.3.2:8080/ohexpress/phone/lojas").build();

                        LojaService lojaService = restAdapter.create(LojaService.class);

                        lojaService.listarLojas(
                                new Callback<List<Loja>>() {


                                    @Override
                                    public void success(List<Loja> lojas, Response response) {

                                        lista = (ArrayList<Loja>) lojas;
                                        Intent itLProx = new Intent(MainActivity.this, LojasProximasActivity.class);
                                        itLProx.putParcelableArrayListExtra("lojas", lista);
                                        startActivity(itLProx);


                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                        //Toast.makeText(getActivity(), "Deu errado" + error, Toast.LENGTH_LONG).show();


                                    }
                                }

                        );


                        break;

                    case R.id.item_tb_qrcode:




                        break;
                    case R.id.item_tb_config:

                        Intent itLProx = new Intent(MainActivity.this, MapaActivity.class);

                        startActivity(itLProx);

                        break;

                }

                return true;
            }
        });


*/

