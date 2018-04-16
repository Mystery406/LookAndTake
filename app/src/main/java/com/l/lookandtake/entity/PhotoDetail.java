package com.l.lookandtake.entity;

/**
 * Created by L on 2018/4/13.
 * Description:
 */
public class PhotoDetail {

    /**
     * id : cqOZcjOOmRw
     * created_at : 2018-03-27T22:35:44-04:00
     * updated_at : 2018-04-13T02:31:56-04:00
     * width : 5684
     * height : 3789
     * color : #040404
     * description : null
     * categories : []
     * user : {"id":"pVJLqUK0Dh4","updated_at":"2018-04-12T21:41:55-04:00","username":"brookelark","name":"Brooke Lark","first_name":"Brooke","last_name":"Lark","twitter_username":null,"portfolio_url":"http://www.brookelark.com","bio":"Real Food fanatic. Bike rider. People lover. Running around with a camera.","location":"SLC, UT","links":{"self":"https://api.unsplash.com/users/brookelark","html":"https://unsplash.com/@brookelark","photos":"https://api.unsplash.com/users/brookelark/photos","likes":"https://api.unsplash.com/users/brookelark/likes","portfolio":"https://api.unsplash.com/users/brookelark/portfolio","following":"https://api.unsplash.com/users/brookelark/following","followers":"https://api.unsplash.com/users/brookelark/followers"},"profile_image":{"small":"https://images.unsplash.com/profile-1496175100457-27a8e68786eb?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=f1b64c7cf2d8c9e799adcfb543c2dc11","medium":"https://images.unsplash.com/profile-1496175100457-27a8e68786eb?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=f77869683f0a1001871e6b661782ef89","large":"https://images.unsplash.com/profile-1496175100457-27a8e68786eb?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=d282cc1f4be5fa5614bfff9b4290d7fd"},"total_collections":0,"instagram_username":"brookelark","total_likes":3,"total_photos":108}
     * urls : {"raw":"https://images.unsplash.com/photo-1522204538344-922f76ecc041?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=fa0fb05e579bd4c3280d90da7c0e3e25","full":"https://images.unsplash.com/photo-1522204538344-922f76ecc041?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=d69ffef37fab49fdb83a5b0d8fe4a08a","regular":"https://images.unsplash.com/photo-1522204538344-922f76ecc041?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=31976aaf6fe2a0d346041cbb62a67b84","small":"https://images.unsplash.com/photo-1522204538344-922f76ecc041?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=59324f9b62cbe7764130789a498d88d0","thumb":"https://images.unsplash.com/photo-1522204538344-922f76ecc041?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=b145b63b85f6734a716062a5e6175e82"}
     * links : {"self":"https://api.unsplash.com/photos/cqOZcjOOmRw","html":"https://unsplash.com/photos/cqOZcjOOmRw","download":"https://unsplash.com/photos/cqOZcjOOmRw/download","download_location":"https://api.unsplash.com/photos/cqOZcjOOmRw/download"}
     * liked_by_user : false
     * sponsored : true
     * likes : 133
     * current_user_collections : []
     * slug : null
     * exif : {"make":"Canon","model":"Canon EOS 5D Mark III","exposure_time":"1/25","aperture":"5.0","focal_length":"35","iso":800}
     * views : 443715
     * downloads : 1900
     */

    private String id;
    private String created_at;
    private String updated_at;
    private int width;
    private int height;
    private String color;
    private String description;
    private boolean liked_by_user;
    private boolean sponsored;
    private int likes;
    private Object slug;
    private Exif exif;
    private int views;
    private int downloads;
    private UserInfo user;
    private Location location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public boolean isSponsored() {
        return sponsored;
    }

    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Object getSlug() {
        return slug;
    }

    public void setSlug(Object slug) {
        this.slug = slug;
    }

    public Exif getExif() {
        return exif;
    }

    public void setExif(Exif exif) {
        this.exif = exif;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
