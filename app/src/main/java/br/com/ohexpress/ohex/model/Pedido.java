package br.com.ohexpress.ohex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rafaelchowman on 15/07/15.
 */
public class Pedido implements Parcelable{


    private Long id;
    private List<ItemPedido> item = new ArrayList<ItemPedido>(0);
    private Loja loja ;
    private int status;
    private CreditCard card;


   // private Usuario usuario;
    public Pedido(){}
    public Pedido(boolean var){

        List<ItemPedido> myCesta = new ArrayList<ItemPedido>(0);
        item = myCesta;
        loja = new Loja();
    }

    public Pedido(Parcel in){

        readFromParcel(in);

    }
    private void readFromParcel(Parcel in) {
        this.id = in.readLong();
        this.status = in.readInt();
        this.loja = (Loja) in.readParcelable(Loja.class.getClassLoader());
        this.item = new ArrayList<ItemPedido>();
        in.readList(this.item,ItemPedido.class.getClassLoader());

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemPedido> getItem() {
        return item;
    }

    public void setItem(List<ItemPedido> item) {
        this.item = item;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    /*
        public FormaPagamento getFormPag() {
            return formPag;
        }

        public void setFormPag(FormaPagamento formPag) {
            this.formPag = formPag;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }
    */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(status);
        dest.writeParcelable(loja, flags);
        dest.writeList(item);

    }

    public static final Parcelable.Creator<Pedido> CREATOR = new Parcelable.Creator<Pedido>(){

        @Override
        public Pedido createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new Pedido(source);
        }

        @Override
        public Pedido[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Pedido[size];
        }
    };

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public double somaVlrItens() {

        double valor = 0;
         for (ItemPedido it: item){

             valor=valor+(it.getProduto().getPreco()*it.getQuantidade());
         }

        return valor;
    }


}
