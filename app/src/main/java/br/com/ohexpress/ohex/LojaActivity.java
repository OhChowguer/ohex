package br.com.ohexpress.ohex;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
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

    public void getMap(){

        Intent itLProx = new Intent(LojaActivity.this, MapaActivity.class);

        startActivity(itLProx);

        return ;
    }

    public void openMenu(View view){


        CestaMenuAdapter adapter = new CestaMenuAdapter( mContext, loja.getCategoriaProduto());

        final ListPopupWindow listPopupWindow = new ListPopupWindow(mContext);
        listPopupWindow.setAdapter( adapter );
        listPopupWindow.setAnchorView(fab);
        listPopupWindow.setWidth((int) (150 * scale + 0.5f));
        listPopupWindow.setDropDownGravity(ListPopupWindow.MATCH_PARENT);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(mContext, ListaProdutosActivity.class);
                intent.putParcelableArrayListExtra("produtos", (ArrayList<Produto>)loja.getProduto());
                listPopupWindow.dismiss();
                mContext.startActivity(intent);

            }
        });
        listPopupWindow.setModal(true);
        listPopupWindow.show();
    }

}
