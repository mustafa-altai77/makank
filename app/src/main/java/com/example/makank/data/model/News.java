package com.example.makank.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {
    int id;
    String title;
    String text;
    String image;
    String created_at;

    public News(int id, String title, String text, String image,String create_at) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
        this.created_at=create_at;
    }

    protected News(Parcel in) {
        id = in.readInt();
        title = in.readString();
        text = in.readString();
        image = in.readString();
        created_at=in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getCreated_at() {
        return created_at;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(image);
        dest.writeString(created_at);
    }
}
