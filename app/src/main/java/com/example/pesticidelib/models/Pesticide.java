package com.example.pesticidelib.models;

import java.io.Serializable;

public class Pesticide implements Serializable {
    private Integer id;
    private String ten;
    private String hoatchat;
    private String nhom;
    private String doituongphongtru;
    private String tochucdangky;
    private Integer isSaved;

    public Pesticide(Integer id, String ten, String hoatchat, String nhom, String doituongphongtru, String tochucdangky, Integer isSaved) {
        this.id = id;
        this.ten = ten;
        this.hoatchat = hoatchat;
        this.nhom = nhom;
        this.doituongphongtru = doituongphongtru;
        this.tochucdangky = tochucdangky;
        this.isSaved = isSaved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHoatchat() {
        return hoatchat;
    }

    public void setHoatchat(String hoatchat) {
        this.hoatchat = hoatchat;
    }

    public String getNhom() {
        return nhom;
    }

    public void setNhom(String nhom) {
        this.nhom = nhom;
    }

    public String getDoituongphongtru() {
        return doituongphongtru;
    }

    public void setDoituongphongtru(String doituongphongtru) {
        this.doituongphongtru = doituongphongtru;
    }

    public String getTochucdangky() {
        return tochucdangky;
    }

    public void setTochucdangky(String tochucdangky) {
        this.tochucdangky = tochucdangky;
    }

    public Integer getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(Integer isSaved) {
        this.isSaved = isSaved;
    }

    @Override
    public String toString() {
        return "Pesticide{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", hoatchat='" + hoatchat + '\'' +
                ", nhom='" + nhom + '\'' +
                ", doituongphongtru='" + doituongphongtru + '\'' +
                ", tochucdangky='" + tochucdangky + '\'' +
                ", isSaved=" + isSaved +
                '}';
    }
}
