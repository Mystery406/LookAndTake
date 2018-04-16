package com.l.lookandtake.entity;

/**
 * Created by L on 2018/4/10.
 * Description:
 */
public class Urls {

    /**
     * raw : https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=cc5aa14e4bc68e8de56fc6bd35bea54e
     * full : https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=36242aec7192ef16f5703e5d14dba2ba
     * regular : https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=441061ee0cf3688feb38e72d0501c942
     * small : https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=4a4d151c515a77b856ba8bdeea97daae
     * thumb : https://images.unsplash.com/photo-1522198684868-88edd8463fc9?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjI0MzU5fQ&s=e75f3ecbd7cccbdcfc2d1111b6c486e9
     */

    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
