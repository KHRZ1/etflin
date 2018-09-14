package com.etflin.etflin;

public class RowItem {
    private String member_name, status, statusWaktu, userLevel, jumlahSuka, jumlahKomen, idBer, totalnamasuka, totaltipe, userId;
    private String profile_pic_id, picutama;

    public RowItem(String member_name, String profile_pic_id, String status, String statusWaktu, String userLevel, String jumlahSuka, String jumlahKomen, String idBer, String totalnamasuka, String totaltipe, String picutama, String userId){
        this.member_name = member_name;
        this.profile_pic_id = profile_pic_id;
        this.status = status;
        this.statusWaktu = statusWaktu;
        this.userLevel = userLevel;
        this.jumlahSuka = jumlahSuka;
        this.jumlahKomen = jumlahKomen;
        this.idBer = idBer;
        this.totalnamasuka = totalnamasuka;
        this.totaltipe = totaltipe;
        this.picutama = picutama;
        this.userId = userId;
    }

    public String getTotaltipe() { return totaltipe; }

    public void setTotaltipe(String totaltipe) { this.totaltipe = totaltipe; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getTotalnamasuka() { return totalnamasuka; }

    public void setTotalnamasuka (String totalnamasuka) { this.totalnamasuka = totalnamasuka; }

    public String getIdBer() { return idBer; }

    public void setIdBer (String idBer) { this.idBer = idBer; }

    public String getMember_name() { return member_name; }

    public void setMember_name(String member_name) { this.member_name = member_name; }

    public String getUserLevel() { return userLevel; }

    public void setUserLevel (String userLevel) { this.userLevel = userLevel; }

    public String getProfile_pic_id() {
        return profile_pic_id;
    }

    public void setProfile_pic_id(String profile_pic_id) {
        this.profile_pic_id = profile_pic_id;
    }

    public String getPicutama() {
        return picutama;
    }

    public void setPicutama(String picutama) {
        this.picutama = picutama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusWaktu() {
        return statusWaktu;
    }

    public void setStatusWaktu(String statusWaktu) {
        this.statusWaktu = statusWaktu;
    }

    public String getJumlahSuka() { return jumlahSuka; }

    public void setJumlahSuka(String jumlahSuka) { this.jumlahSuka = jumlahSuka; }

    public String getJumlahKomen() { return jumlahKomen; }

    public void setJumlahKomen(String jumlahKomen) { this.jumlahKomen = jumlahKomen; }

}
