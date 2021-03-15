package com.mzl.exportexcel.entity;

import java.util.Date;

public class News {
    private Integer id;

    private String source;

    private Date time;

    private String title;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", time=" + time +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}