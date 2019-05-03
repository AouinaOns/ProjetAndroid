package com.example.espaceetudiant;

import android.os.Parcel;
import android.os.Parcelable;


public class Process  implements Parcelable {


    String tas_uid;


    String pro_title;


    String   pro_uid;

    public Process(String tas_uid, String pro_title, String pro_uid) {

        this.tas_uid = tas_uid;
        this.pro_title = pro_title;
        this.pro_uid = pro_uid;
    }

    public Process(Parcel in) {
        tas_uid = in.readString();
        pro_title = in.readString();
        pro_uid = in.readString();
    }

    public String getTas_uid() {
        return tas_uid;
    }

    public void setTas_uid(String tas_uid) {
        this.tas_uid = tas_uid;
    }

    public String getPro_title() {
        return pro_title;
    }

    public void setPro_title(String pro_title) {
        this.pro_title = pro_title;
    }

    public String getPro_uid() {
        return pro_uid;
    }

    public void setPro_uid(String pro_uid) {
        this.pro_uid = pro_uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tas_uid);
        parcel.writeString(pro_title );
        parcel.writeString(pro_uid );


    }

    public static final Parcelable.Creator<Process> CREATOR = new Parcelable.Creator<Process>()
    {
        public Process createFromParcel(Parcel in)
        {
            return new Process(in);
        }
        public Process[] newArray(int size)
        {
            return new Process[size];
        }
    };
}
