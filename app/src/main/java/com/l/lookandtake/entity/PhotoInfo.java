package com.l.lookandtake.entity;

/**
 * Created by L on 2018/4/10.
 * Description:
 */
public class PhotoInfo {

    /**
     * id : ADjcacMLblo
     * created_at : 2018-03-27T20:58:33-04:00
     * updated_at : 2018-04-10T02:31:25-04:00
     * width : 6000
     * height : 4000
     * color : #EFF0F2
     * description : null
     * categories : []
     * user : {"id":"b_9ibdOaHks","updated_at":"2018-04-10T04:12:24-04:00","username":"andrewtneel","name":"Andrew Neel","first_name":"Andrew","last_name":"Neel","twitter_username":"andrewtneel","portfolio_url":"https://www.instagram.com/andrewtneel/","bio":"Unsplash Community Manager | Support + encourage the people you believe in ✌️","location":"North Carolina","links":{"self":"https://api.unsplash.com/users/andrewtneel","html":"https://unsplash.com/@andrewtneel","photos":"https://api.unsplash.com/users/andrewtneel/photos","likes":"https://api.unsplash.com/users/andrewtneel/likes","portfolio":"https://api.unsplash.com/users/andrewtneel/portfolio","following":"https://api.unsplash.com/users/andrewtneel/following","followers":"https://api.unsplash.com/users/andrewtneel/followers"},"profile_image":{"small":"https://images.unsplash.com/profile-1493155049978-dadd9d03e2be?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=31799b4b2f75f9c530e8c63a02def1a0","medium":"https://images.unsplash.com/profile-1493155049978-dadd9d03e2be?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=0420b6dfd56e8f2b18ac2e6e319b666f","large":"https://images.unsplash.com/profile-1493155049978-dadd9d03e2be?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=dbf2d0e72ede4f51210b6d9ba8a3df91"},"total_collections":60,"instagram_username":"andrewtneel","total_likes":7055,"total_photos":63}
     * urls : {"raw":"https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=cc5aa14e4bc68e8de56fc6bd35bea54e","full":"https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=36242aec7192ef16f5703e5d14dba2ba","regular":"https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=441061ee0cf3688feb38e72d0501c942","small":"https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=4a4d151c515a77b856ba8bdeea97daae","thumb":"https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=e75f3ecbd7cccbdcfc2d1111b6c486e9"}
     * links : {"self":"https://api.unsplash.com/photos/ADjcacMLblo","html":"https://unsplash.com/photos/ADjcacMLblo","download":"https://unsplash.com/photos/ADjcacMLblo/download","download_location":"https://api.unsplash.com/photos/ADjcacMLblo/download"}
     * liked_by_user : false
     * sponsored : true
     * likes : 13
     * current_user_collections : []
     */

    private String id;
    private String created_at;
    private String updated_at;
    private int width;
    private int height;
    private String color;
    private String description;
    private UserInfo user;
    private Urls urls;
    private Links links;
    private boolean liked_by_user;
    private boolean sponsored;
    private int likes;

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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
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
}
