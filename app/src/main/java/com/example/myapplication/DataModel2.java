package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.List;

public class DataModel2 implements Parcelable {

    List<String> URL;
    List<String> type;
    List<String> Giver;

    protected DataModel2() {

    }

    protected DataModel2(Parcel in) {
        URL = in.createStringArrayList();
        type = in.createStringArrayList();
        Giver = in.createStringArrayList();
    }

    public static final Creator<DataModel2> CREATOR = new Creator<DataModel2>() {
        @Override
        public DataModel2 createFromParcel(Parcel in) {
            return new DataModel2(in);
        }

        @Override
        public DataModel2[] newArray(int size) {
            return new DataModel2[size];
        }
    };

    public void add(List<String> URL, List<String> type, List<String> Giver) {

        this.URL = URL;
        this.type = type;
        this.Giver = Giver;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(URL);
        dest.writeStringList(type);
        dest.writeStringList(Giver);
    }
}
