package com.example.noteclock.model;


public class Note {
    private int id;
    private String TieuDe;
    private String NoiDung;
    private String Ngay;
    private String Gio;

    public Note(){

    }

    public Note(int id, String tieuDe, String noiDung, String ngay, String gio) {
        this.id = id;
        TieuDe = tieuDe;
        NoiDung = noiDung;
        Ngay = ngay;
        Gio = gio;
    }

    public int getId() {
        return id;
    }

    public String getTieuDe() {
        return TieuDe;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public String getNgay() {
        return Ngay;
    }

    public String getGio() {
        return Gio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTieuDe(String tieuDe) {
        TieuDe = tieuDe;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public void setGio(String gio) {
        Gio = gio;
    }
}
