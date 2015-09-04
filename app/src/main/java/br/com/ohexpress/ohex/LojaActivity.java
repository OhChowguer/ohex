package br.com.ohexpress.ohex;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
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
import com.software.shell.fab.ActionButton;
import java.util.ArrayList;
import java.util.List;
import br.com.ohexpress.ohex.adapters.CestaMenuAdapter;
import br.com.ohexpress.ohex.fragment.DestaqueFragment;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Produto;
import br.com.ohexpress.ohex.model.Usuario;


public class LojaActivity extends ActionBarActivity {

    private Context mContext;
    private Toolbar ohTopBar;
    private Loja loja;
    private TextView tvLoja;
    private TextView tvCatLoja;
    private TextView tvEndLoja;
    private SimpleDraweeView imageLoja;
    private ActionButton fab;
    private float scale;
    private Usuario user;
    private Drawer nDrawerLeft;
    private AccountHeader accHeaderBuilder;
    private ImageView openDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);
        mContext = this;
        loja = getIntent().getExtras().getParcelable("loja");
        ((MyApplication) getApplication()).getMyLoja().setId(loja.getId());
        scale = mContext.getResources().getDisplayMetrics().density;
        user = ((MyApplication) getApplication()).getUser();
        fab = (ActionButton) findViewById(R.id.fab_menu);
        imageLoja = (SimpleDraweeView) findViewById(R.id.cv_loja);
        Uri uri = Uri.parse(loja.getImgLoja());
        imageLoja.setImageURI(uri);
        tvLoja = (TextView) findViewById(R.id.tv_nomeLoja_loja);
        tvCatLoja = (TextView) findViewById(R.id.tv_label_categoria_loja);
        tvEndLoja = (TextView) findViewById(R.id.tv_lb_bairro_loja);
        tvLoja.setText(loja.getNome());
        tvCatLoja.setText(loja.getCategoria().get(0).getNome());
        tvEndLoja.setText(loja.getEndereco().getBairro()+", "+loja.getEndereco().getCidade());
        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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

        accHeaderBuilder.addProfiles(new ProfileDrawerItem().
                withName(user.getAccountName()).withEmail("chowman@gmail.com")
                .withIcon(getResources().getDrawable(R.drawable.img_avatar)));



        nDrawerLeft = new DrawerBuilder()
                .withActivity(this)
                //.withToolbar(ohTopBar)
                        //.withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withAccountHeader(accHeaderBuilder)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Inicio").withIcon(R.drawable.ic_home_amarelo),
                        new DividerDrawerItem(),
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
                        new SecondaryDrawerItem().withName("Sair").withIcon(R.drawable.ic_off_amarelo))

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int position, long l, IDrawerItem iDrawerItem) {

                        switch (position) {
                            case 0:
                                Intent i = new Intent(LojaActivity.this, MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                break;
                            case 2:

                                break;
                            case 4:

                                break;
                            case 6:

                                break;
                            case 8:

                                break;
                            case 10:

                                break;
                            case 12:

                                break;
                            case 14:

                                break;
                        }


                        return false;
                    }
                })
                .build();

        openDrawer = (ImageView) findViewById(R.id.iv_open_drawer);
        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDrawerLeft.openDrawer();
            }
        });


        // FRAGMENT
        DestaqueFragment frag = (DestaqueFragment) getSupportFragmentManager().findFragmentByTag("mainFragLoja");
        if(frag == null) {
            frag = new DestaqueFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_loja, frag, "mainFragLoja");
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

            finish();
            return true;
        }

        if (id == R.id.action_cesta_loja) {
            Intent it = new Intent(LojaActivity.this, CestaActivity.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Produto> getDestaques(){


        return loja.getProduto();
    }

    public void getMap(View view){

        Intent itLProx = new Intent(LojaActivity.this, MapaActivity.class);

        startActivity(itLProx);

        return ;
    }

    public void openMenu(View view){


        CestaMenuAdapter adapter = new CestaMenuAdapter( mContext, loja.getCategoriaProduto());

        final ListPopupWindow listPopupWindow = new ListPopupWindow(mContext);
        listPopupWindow.setAdapter( adapter );
        listPopupWindow.setAnchorView(fab);
        listPopupWindow.setWidth((int) (175 * scale + 0.5f));
        listPopupWindow.setDropDownGravity(ListPopupWindow.MATCH_PARENT);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(mContext, ListaProdutosActivity.class);
                intent.putParcelableArrayListExtra("produtos", (ArrayList<Produto>)loja.filtraProduto(loja.getCategoriaProduto().get(position)));
                listPopupWindow.dismiss();
                mContext.startActivity(intent);

            }
        });
        listPopupWindow.setModal(true);
        listPopupWindow.show();
    }



}
