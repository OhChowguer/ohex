package br.com.ohexpress.ohex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rafaelchowman on 15/07/15.
 */
public class CategoriaProduto implements Parcelable {


    private Long id;
    private String nome;
    private String descricao;
   // private boolean endImg;
    //private Set<Produto> produto = new HashSet<Produto>(0);
    //private Set<Loja> loja = new HashSet<Loja>(0);

    public CategoriaProduto(Parcel parcel){
        this.id = parcel.readLong();
        this.nome = parcel.readString();
        this.descricao = parcel.readString();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /*  public boolean isEndImg() {
         return endImg;
     }

     public void setEndImg(boolean endImg) {
         this.endImg = endImg;
     }

    public Set<Produto> getProduto() {
         return produto;
     }

     public void setProduto(Set<Produto> produto) {
         this.produto = produto;
     }

     public Set<Loja> getLoja() {
         return loja;
     }

     public void setLoja(Set<Loja> loja) {
         this.loja = loja;
     }
 */

    public static final Parcelable.Creator<CategoriaProduto> CREATOR = new Parcelable.Creator<CategoriaProduto>(){

        @Override
        public CategoriaProduto createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new CategoriaProduto(source);
        }

        @Override
        public CategoriaProduto[] newArray(int size) {
            // TODO Auto-generated method stub
            return new CategoriaProduto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(nome);
        dest.writeString(descricao);

    }
}
