package com.example.oussama.models;

import java.util.Date;

public class Post {
    private int id ;
    private int username_id;
    private String title;
    private String content;
    private String quote;
    private String image;
    private int rating;
    private String video;
    private Date createdat;
    private int visible;
    private Date Dateupdatedat;
    private  int threadid;




    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThreadid() {
        return threadid;
    }

    public void setThreadid(int threadid) {
        this.threadid = threadid;
    }

    public int getUsername_id() {
        return username_id;
    }

    public void setUsername_id(int username_id) {
        this.username_id = username_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateupdatedat() {
        return Dateupdatedat;
    }

    public void setDateupdatedat(Date dateupdatedat) {
        Dateupdatedat = dateupdatedat;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", username_id=" + username_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", createdat=" + createdat +
                ", visible=" + visible +
                ", image='" + image + '\'' +
                ", Dateupdatedat=" + Dateupdatedat +
                ", video='" + video + '\'' +
                ", quote='" + quote + '\'' +
                '}';
    }

}
