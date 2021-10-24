package com.nathaniel.utility.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 文件信息类
 *
 * @author nathaniel
 */
@Entity
public class FileEntity implements Parcelable {
    public static final Creator<FileEntity> CREATOR = new Creator<FileEntity>() {
        @Override
        public FileEntity createFromParcel(Parcel source) {
            return new FileEntity(source);
        }

        @Override
        public FileEntity[] newArray(int size) {
            return new FileEntity[size];
        }
    };
    @Id(autoincrement = true)
    private long id;
    private String url;
    private String fileName;
    private long length;
    private long finish;

    @Generated(hash = 1019123921)
    public FileEntity(long id, String url, String fileName, long length,
                      long finish) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finish = finish;
    }

    @Generated(hash = 1879603201)
    public FileEntity() {
    }

    protected FileEntity(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.fileName = in.readString();
        this.length = in.readLong();
        this.finish = in.readLong();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getFinish() {
        return this.finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
        dest.writeString(this.fileName);
        dest.writeLong(this.length);
        dest.writeLong(this.finish);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.url = source.readString();
        this.fileName = source.readString();
        this.length = source.readLong();
        this.finish = source.readLong();
    }
}
