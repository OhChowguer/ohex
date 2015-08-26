package br.com.ohexpress.ohex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by rafaelchowman on 15/07/15.
 */
public class Endereco implements Parcelable {

    private Long id;
    private String cep;
    private String cidade;
    private String estado;
    private String logradouro;
    private String pais;
    private String bairro;
    private String numero;
    private String latitude;
    private String longitude;

    public Endereco(Parcel parcel){
        this.id = parcel.readLong();
        this.cep = parcel.readString();
        this.cidade = parcel.readString();
        this.estado = parcel.readString();
        this.logradouro = parcel.readString();
        this.pais = parcel.readString();
        this.bairro = parcel.readString();
        this.numero = parcel.readString();
        this.latitude = parcel.readString();
        this.longitude = parcel.readString();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(cep);
        dest.writeString(cidade);
        dest.writeString(estado);
        dest.writeString(logradouro);
        dest.writeString(pais);
        dest.writeString(bairro);
        dest.writeString(numero);
        dest.writeString(latitude);
        dest.writeString(longitude);


    }
    public static final Parcelable.Creator<Endereco> CREATOR = new Parcelable.Creator<Endereco>(){

        @Override
        public Endereco createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new Endereco(source);
        }

        @Override
        public Endereco[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Endereco[size];
        }
    };
}
