package com.example.restfulapi.model;


import android.os.Parcel;
import android.os.Parcelable;

public class CityItem implements Parcelable {

    private Integer id;
    private String main;
    private String description;
    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.main);
        dest.writeString(this.description);
        dest.writeString(this.icon);
    }

    public CityItem() {
    }

    protected CityItem(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.main = in.readString();
        this.description = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<CityItem> CREATOR = new Parcelable.Creator<CityItem>() {
        @Override
        public CityItem createFromParcel(Parcel source) {
            return new CityItem(source);
        }

        @Override
        public CityItem[] newArray(int size) {
            return new CityItem[size];
        }
    };
}