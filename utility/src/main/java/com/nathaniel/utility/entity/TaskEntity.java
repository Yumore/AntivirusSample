package com.nathaniel.utility.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 下载信息类
 */
@Entity
public class TaskEntity implements Parcelable {
    public static final Creator<TaskEntity> CREATOR = new Creator<TaskEntity>() {
        @Override
        public TaskEntity createFromParcel(Parcel source) {
            return new TaskEntity(source);
        }

        @Override
        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };
    @Id(autoincrement = true)
    private long id;
    private String url;
    private long start;
    private long end;
    private long progress;

    @Generated(hash = 44187298)
    public TaskEntity(long id, String url, long start, long end, long progress) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.progress = progress;
    }

    @Generated(hash = 397975341)
    public TaskEntity() {
    }

    protected TaskEntity(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.start = in.readLong();
        this.end = in.readLong();
        this.progress = in.readLong();
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

    public long getStart() {
        return this.start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return this.end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getProgress() {
        return this.progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
        dest.writeLong(this.start);
        dest.writeLong(this.end);
        dest.writeLong(this.progress);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.url = source.readString();
        this.start = source.readLong();
        this.end = source.readLong();
        this.progress = source.readLong();
    }
}
