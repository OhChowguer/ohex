package br.com.ohexpress.ohex;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;

/**
 * Created by rafaelchowman on 13/08/15.
 */
public class MyApplication extends Application {

    private Usuario user;
    private List<ItemPedido> myCesta;
    private Pedido myPedido;
    private Loja myLoja;



    @Override
    public void onCreate(){
        super.onCreate();
        user = new Usuario();
        myCesta = new ArrayList<ItemPedido>(0);
        myPedido = new Pedido();
        myPedido.setLoja(new Loja());
        myLoja = new Loja();






    }



    public Usuario getUser() {
        return user;
    }
    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<ItemPedido> getMyCesta() {
        return myCesta;
    }

    public void setMyCesta(List<ItemPedido> myCesta) {
        this.myCesta = myCesta;
    }

    public Pedido getMyPedido() {
        return myPedido;
    }

    public void setMyPedido(Pedido myPedido) {
        this.myPedido = myPedido;
    }

    public Loja getMyLoja() {
        return myLoja;
    }

    public void setMyLoja(Loja myLoja) {
        this.myLoja = myLoja;
    }
}