package br.com.technologies.venom.frutastropicais;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("tfvname")
    @Expose
    private String tfvname;
    @SerializedName("botname")
    @Expose
    private String botname;
    @SerializedName("othname")
    @Expose
    private String othname;
    @SerializedName("imageurl")
    @Expose
    private String imageurl;

    /**
     * No args constructor for use in serialization
     *
     */
    public Result() {
    }

    /**
     *
     * @param botname
     * @param imageurl
     * @param tfvname
     * @param othname
     */
    public Result(String tfvname, String botname, String othname, String imageurl) {
        super();
        this.tfvname = tfvname;
        this.botname = botname;
        this.othname = othname;
        this.imageurl = imageurl;
    }

    public String getTfvname() {
        return tfvname;
    }

    public void setTfvname(String tfvname) {
        this.tfvname = tfvname;
    }

    public String getBotname() {
        return botname;
    }

    public void setBotname(String botname) {
        this.botname = botname;
    }

    public String getOthname() {
        return othname;
    }

    public void setOthname(String othname) {
        this.othname = othname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

}