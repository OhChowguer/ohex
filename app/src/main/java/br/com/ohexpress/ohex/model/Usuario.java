package br.com.ohexpress.ohex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaelchowman on 15/07/15.
 */
public class Usuario implements Parcelable {


    private Long id;
    private String login;
    private String nome;
    private String snome;
    private String email;
    private String senha;
    private int tipo;
    private String accountType;
    private String authTokenType;
    private String accountName;
    private String token;
    private List<Loja> favorita= new ArrayList<Loja>(0);
    private List<CreditCard> creditCard = new ArrayList<CreditCard>();

    public Usuario(){}
    public Usuario(Parcel parcel){

        this.id = parcel.readLong();
        this.nome = parcel.readString();
        this.login = parcel.readString();
        this.nome = parcel.readString();
        this.snome = parcel.readString();
        this.email = parcel.readString();
        this.senha = parcel.readString();
        this.tipo = parcel.readInt();
        this.accountType = parcel.readString();
        this.authTokenType = parcel.readString();
        this.accountName = parcel.readString();
        this.token = parcel.readString();
        this.favorita = new ArrayList<Loja>();
        parcel.readList(this.favorita,Loja.class.getClassLoader());
        this.creditCard = new ArrayList<CreditCard>();
        parcel.readList(this.creditCard,CreditCard.class.getClassLoader());

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSnome() {
        return snome;
    }

    public void setSnome(String snome) {
        this.snome = snome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAuthTokenType() {
        return authTokenType;
    }

    public void setAuthTokenType(String authTokenType) {
        this.authTokenType = authTokenType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Loja> getFavoritas() {
        return favorita;
    }

    public void setFavoritas(List<Loja> favoritas) {
        this.favorita = favoritas;
    }

    public List<CreditCard> getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(List<CreditCard> creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(nome);
        dest.writeString(login);
        dest.writeString(nome);
        dest.writeString(snome);
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeString(email);
        dest.writeInt(tipo);
        dest.writeString(accountType);
        dest.writeString(authTokenType);
        dest.writeString(accountName);
        dest.writeString(token);
        dest.writeList(favorita);
        dest.writeList(creditCard);

    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>(){

        @Override
        public Usuario createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Usuario[size];
        }
    };
}
