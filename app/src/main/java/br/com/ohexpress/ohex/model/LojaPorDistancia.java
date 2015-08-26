package br.com.ohexpress.ohex.model;

/**
 * Created by rafaelchowman on 03/08/15.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Comparator;

public class LojaPorDistancia implements Parcelable{

    private Loja loja;
    private String distText;
    private int distNumber;


    public LojaPorDistancia(Parcel parcel){

        this.distText = parcel.readString();
        this.distNumber = parcel.readInt();
        this.loja = (Loja) parcel.readParcelable(Loja.class.getClassLoader());


    }
    public Loja getLoja() {
        return loja;
    }
    public void setLoja(Loja loja) {
        this.loja = loja;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(distText);
        dest.writeInt(distNumber);
        dest.writeParcelable(loja,flags);

    }

    // Comparator
    public static class CompId implements Comparator<LojaPorDistancia> {

        @Override
        public int compare(LojaPorDistancia o1, LojaPorDistancia o2) {
            return o1.getDistNumber() - o2.getDistNumber();

        }
    }
    public static final Parcelable.Creator<LojaPorDistancia> CREATOR = new Parcelable.Creator<LojaPorDistancia>(){

        @Override
        public LojaPorDistancia createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new LojaPorDistancia(source);
        }

        @Override
        public LojaPorDistancia[] newArray(int size) {
            // TODO Auto-generated method stub
            return new LojaPorDistancia[size];
        }
    };


}
