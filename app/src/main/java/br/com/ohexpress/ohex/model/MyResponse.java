package br.com.ohexpress.ohex.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rafaelchowman on 28/07/15.
 */
public class MyResponse implements Parcelable {

    private String resp;

    public MyResponse(){}


    public MyResponse(Parcel parcel){
        this.resp = parcel.readString();


    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(resp);

    }

    public static final Parcelable.Creator<MyResponse> CREATOR = new Parcelable.Creator<MyResponse>(){

        @Override
        public MyResponse createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new MyResponse(source);
        }

        @Override
        public MyResponse[] newArray(int size) {
            // TODO Auto-generated method stub
            return new MyResponse[size];
        }
    };
}
