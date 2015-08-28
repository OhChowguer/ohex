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
public class Loja implements Parcelable {


    private Long id;
    private String nome;
    private String descricao;
    private boolean status;
    private String imgLoja;
    private Usuario administrador;
    private Set<Usuario> colaborador;
    private Endereco endereco;
    private List<Categoria> categoria ;
    private List<CategoriaProduto> categoriaProduto;
    private String imgLogoTopo;

    private List<Produto> produto;
    private List<Usuario> usuario ;
    private String distText;
    private int distNumber;

    public Loja(){

    }

    public Loja(String name, String descricao){
        this.nome = nome;
        this.descricao = descricao;

    }
    public Loja(Parcel parcel){

        this.id = parcel.readLong();
        this.nome = parcel.readString();
        this.descricao = parcel.readString();
        this.imgLoja = parcel.readString();
        this.distText = parcel.readString();
        this.distNumber = parcel.readInt();
        this.endereco = (Endereco) parcel.readParcelable(Endereco.class.getClassLoader());
        this.produto = new ArrayList<Produto>();
        parcel.readList(this.produto,Produto.class.getClassLoader());
        this.categoriaProduto = new ArrayList<CategoriaProduto>();
        parcel.readList(this.categoriaProduto,CategoriaProduto.class.getClassLoader());
        this.categoria = new ArrayList<Categoria>();
        parcel.readList(this.categoria,Categoria.class.getClassLoader());
        this.usuario = new ArrayList<Usuario>();
        parcel.readList(this.usuario,Usuario.class.getClassLoader());

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
    public String getImgLoja() {
        return imgLoja;
    }
    public void setImgLoja(String imgLoja) {
        this.imgLoja = imgLoja;
    }

    public boolean isStatus() {return status;}

    public void setStatus(boolean status) {this.status = status;}

     public Usuario getAdministrador() {return administrador;}

    public void setAdministrador(Usuario administrador) {this.administrador = administrador;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeLong(id);
        dest.writeString(nome);
        dest.writeString(descricao);
        dest.writeString(imgLoja);
        dest.writeString(distText);
        dest.writeInt(distNumber);
        dest.writeParcelable(endereco, flags);
        dest.writeList(produto);
        dest.writeList(categoriaProduto);
        dest.writeList(categoria);
        dest.writeList(usuario);

    }
    public static final Parcelable.Creator<Loja> CREATOR = new Parcelable.Creator<Loja>(){

        @Override
        public Loja createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new Loja(source);
        }

        @Override
        public Loja[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Loja[size];
        }
    };

    public Set<Usuario> getColaborador() {
       return colaborador;
    }

    public void setColaborador(Set<Usuario> colaborador) {
        this.colaborador = colaborador;
    }

    public Endereco getEndereco() {
       return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public List<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<Categoria> categoria) {
        this.categoria = categoria;
    }

    public List<CategoriaProduto> getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(List<CategoriaProduto> categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public List<Produto> getProduto() {
       return produto;
    }

     public void setProduto(List<Produto> produto) {
        this.produto = produto;
    }

    public String getDistText() {
        return distText;
    }

    public void setDistText(String distText) {
        this.distText = distText;
    }

    public int getDistNumber() {
        return distNumber;
    }

    public void setDistNumber(int distNumber) {
        this.distNumber = distNumber;
    }


    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }

    public String getImgLogoTopo() {
        return imgLogoTopo;
    }

    public void setImgLogoTopo(String imgLogoTopo) {
        this.imgLogoTopo = imgLogoTopo;
    }

    public List<Produto> filtraProduto(CategoriaProduto categoria){
        List<Produto> filtrados= new ArrayList<Produto>(0);

        for(Produto prod:produto){

            for (CategoriaProduto cat: prod.getCategoriaProduto()){

                if (categoria.getId() == cat.getId()){
                    filtrados.add(prod);
                }
            }
        }

        return filtrados;
    }
}
