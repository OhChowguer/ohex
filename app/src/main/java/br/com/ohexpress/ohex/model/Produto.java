package br.com.ohexpress.ohex.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rafaelchowman on 15/07/15.
 */
@DatabaseTable(tableName="produto")
public class Produto implements Parcelable{

    @DatabaseField(generatedId=true)
    private Long id;
    @DatabaseField
    private String nome;
    @DatabaseField
    private String descricao;
    //private boolean status;
    @DatabaseField
    private String imgProduto;
    //private Date dataCadastro;
    @DatabaseField
    private double preco;

    private List<CategoriaProduto> categoriaProduto;

    private List<ItemProduto> itemProduto ;


    public Produto(){}
    public Produto(Parcel parcel){
        this.id= parcel.readLong();
        this.nome = parcel.readString();
        this.descricao = parcel.readString();
        this.imgProduto= parcel.readString();
        this.preco = parcel.readDouble();
        this.categoriaProduto = new ArrayList<CategoriaProduto>();
        parcel.readList(this.categoriaProduto,CategoriaProduto.class.getClassLoader());
        this.itemProduto = new ArrayList<ItemProduto>();
        parcel.readList(this.itemProduto,ItemProduto.class.getClassLoader());




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
/*
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
*/
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    @Override
    public String toString() {
        return "Produto{" +
                "preco=" + preco +
                '}';
    }


    public List<CategoriaProduto> getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(List<CategoriaProduto> categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public String getImgProduto() {
        return imgProduto;
    }

    public void setImgProduto(String imgProduto) {
        this.imgProduto = imgProduto;
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
        dest.writeString(imgProduto);
        dest.writeDouble(preco);
        dest.writeList(categoriaProduto);
        dest.writeList(itemProduto);


    }


    public String getNomeItemProdutro() {
        String listaPc = new String();
        int count = 0;

        for (ItemProduto cat : itemProduto) {

            if (count<1) {
                listaPc = cat.getNome();
                count++;
            }else
                listaPc = listaPc + ", " + cat.getNome();{

            }

        }

        if (listaPc.length()>40){

            return listaPc.substring(0,40)+"...";

        }else{

          return listaPc;
        }


    }


    public static final Parcelable.Creator<Produto> CREATOR = new Parcelable.Creator<Produto>(){

        @Override
        public Produto createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new Produto(source);
        }



        @Override
        public Produto[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Produto[size];
        }
    };


    public List<ItemProduto> getItemProduto() {
        return itemProduto;
    }

    public List<String> getAdicionalString() {



        List<String> lista = new ArrayList<String>(0);

        for (ItemProduto item: itemProduto){

            lista.add(item.getNome());
        }
        return lista;
    }

    public void setItemProduto(List<ItemProduto> itemProduto) {
        this.itemProduto = itemProduto;
    }
}
