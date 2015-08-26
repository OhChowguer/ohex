package br.com.ohexpress.ohex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rafaelchowman on 15/07/15.
 */
public class ItemProduto implements Parcelable{


    private Long id;
    private String nome;
    private String descricao;
    private boolean status;


    public ItemProduto(Parcel parcel){
        this.id= parcel.readLong();
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


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


    public static final Parcelable.Creator<ItemProduto> CREATOR = new Parcelable.Creator<ItemProduto>(){

        @Override
        public ItemProduto createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new ItemProduto(source);
        }

        @Override
        public ItemProduto[] newArray(int size) {
            // TODO Auto-generated method stub
            return new ItemProduto[size];
        }
    };

}
