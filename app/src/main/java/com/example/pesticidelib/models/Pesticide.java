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

    //phuc vu cho tim kiem khong dau
    private String ten_ascii;
    private String doituongphongtru_ascii;
    private String tochucdangky_ascii;


    public Pesticide() {
    }

//    public Pesticide(Integer id, String ten, String hoatchat, String nhom, String doituongphongtru, String tochucdangky, Integer isSaved) {
//        this.id = id;
//        this.ten = ten;
//        this.hoatchat = hoatchat;
//        this.nhom = nhom;
//        this.doituongphongtru = doituongphongtru;
//        this.tochucdangky = tochucdangky;
//        this.isSaved = isSaved;
//    }

    public Pesticide(Integer id, String ten, String ten_ascii, String hoatchat, String nhom, String doituongphongtru, String doituongphongtru_ascii, String tochucdangky, String tochucdangky_ascii, Integer isSaved) {
        this.id = id;
        this.ten = ten;
        this.hoatchat = hoatchat;
        this.nhom = nhom;
        this.doituongphongtru = doituongphongtru;
        this.tochucdangky = tochucdangky;
        this.isSaved = isSaved;

        //.........
        this.ten_ascii = ten_ascii;
        this.doituongphongtru_ascii = doituongphongtru_ascii;
        this.tochucdangky_ascii = tochucdangky_ascii;
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

    //cac ham phuc vu cho tim kiem khong dau


    public String getTen_ascii() {
        return ten_ascii;
    }

    public void setTen_ascii(String ten_ascii) {
        this.ten_ascii = ten_ascii;
    }

    public String getDoituongphongtru_ascii() {
        return doituongphongtru_ascii;
    }

    public void setDoituongphongtru_ascii(String doituongphongtru_ascii) {
        this.doituongphongtru_ascii = doituongphongtru_ascii;
    }

    public String getTochucdangky_ascii() {
        return tochucdangky_ascii;
    }

    public void setTochucdangky_ascii(String tochucdangky_ascii) {
        this.tochucdangky_ascii = tochucdangky_ascii;
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
