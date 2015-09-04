package br.com.ohexpress.ohex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by rafaelchowman on 19/08/15.
 */
public class CreditCard implements Parcelable{




    private Long id;
    private String nome;
    private int tipoCard;
    private String descricao;
    private String numeroCard;
    private Date dataExp;
    private String nomeTitular;
    private Endereco enderecoCob;

    public CreditCard(String numeroCard, String nome, int tipoCard, String descricao, Date dataExp, String nomeTitular) {
        this.numeroCard = numeroCard;
        this.nome = nome;
        this.tipoCard = tipoCard;
        this.descricao = descricao;
        this.dataExp = dataExp;
        this.nomeTitular = nomeTitular;
    }

    public CreditCard(Parcel parcel) {
        this.id = parcel.readLong();
        this.nome = parcel.readString();
        this.tipoCard = parcel.readInt();
        this.descricao = parcel.readString();
        //this.dataExp = parcel.readDa();
        this.nomeTitular = parcel.readString();
        this.enderecoCob = (Endereco) parcel.readParcelable(Endereco.class.getClassLoader());

    }
    public int getTipoCard() {
        return tipoCard;
    }
    public void setTipoCard(int tipoCard) {
        this.tipoCard = tipoCard;
    }
    public String getNumeroCard() {
        return numeroCard;
    }
    public void setNumeroCard(String numeroCard) {
        this.numeroCard = numeroCard;
    }
    public Date getDataExp() {
        return dataExp;
    }
    public void setDataExp(Date dataExp) {
        this.dataExp = dataExp;
    }
    public String getNomeTitular() {
        return nomeTitular;
    }
    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }
    public Endereco getEnderecoCob() {
        return enderecoCob;
    }
    public void setEnderecoCob(Endereco enderecoCob) {
        this.enderecoCob = enderecoCob;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeLong(id);
        dest.writeString(nome);
        dest.writeInt(tipoCard);
        dest.writeString(descricao);
        dest.writeString(nomeTitular);
        dest.writeParcelable(enderecoCob,flags);




    }

    public static final Parcelable.Creator<CreditCard> CREATOR = new Parcelable.Creator<CreditCard>(){

        @Override
        public CreditCard createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new CreditCard(source);
        }

        @Override
        public CreditCard[] newArray(int size) {
            // TODO Auto-generated method stub
            return new CreditCard[size];
        }
    };

}