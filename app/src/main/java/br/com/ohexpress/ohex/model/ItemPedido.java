package br.com.ohexpress.ohex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ItemPedido implements Parcelable{


    private Long id;
    private Produto produto;
    private int quantidade;
    private List<ItemProduto> adicionais;
    private List<ItemProduto> opcionais;

    public ItemPedido(){}

    public ItemPedido(Produto produto,int qtd){

        this.produto = produto;
        this.quantidade=qtd;
    }
    public ItemPedido(Parcel parcel){
        this.id = parcel.readLong();
        this.quantidade = parcel.readInt();
        this.produto = (Produto) parcel.readParcelable(br.com.ohexpress.ohex.model.Produto.class.getClassLoader());
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(quantidade);
        dest.writeParcelable(produto,flags);

    }
    public static final Parcelable.Creator<ItemPedido> CREATOR = new Parcelable.Creator<ItemPedido>(){

        @Override
        public ItemPedido createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new ItemPedido(source);
        }

        @Override
        public ItemPedido[] newArray(int size) {
            // TODO Auto-generated method stub
            return new ItemPedido[size];
        }
    };

    public List<ItemProduto> getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(List<ItemProduto> adicionais) {
        this.adicionais = adicionais;
    }

    public List<ItemProduto> getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(List<ItemProduto> opcionais) {
        this.opcionais = opcionais;
    }

    public void addQtd(){


        if (quantidade<9) {
            quantidade = quantidade + 1;
        }
    }

    public void removeQtd(){

        if (quantidade > 1){

        quantidade = quantidade-1;}
    }
}
