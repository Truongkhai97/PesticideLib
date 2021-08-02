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
//    private String ten_eng;
//    private String hoatchat_eng;
//    private String nhom_eng;
//    private String doituongphongtru_eng;
//    private String tochucdangky_eng;


    public Pesticide() {
    }

    public Pesticide(Integer id, String ten, String hoatchat, String nhom, String doituongphongtru, String tochucdangky, Integer isSaved) {
        this.id = id;
        this.ten = ten;
        this.hoatchat = hoatchat;
        this.nhom = nhom;
        this.doituongphongtru = doituongphongtru;
        this.tochucdangky = tochucdangky;
        this.isSaved = isSaved;

        //chuyen sang khong dau
//        this.ten_eng = convertToEng(ten.toLowerCase());
//        this.hoatchat_eng = convertToEng(hoatchat.toLowerCase());
//        this.nhom_eng = convertToEng(nhom.toLowerCase());
//        this.doituongphongtru_eng = convertToEng(doituongphongtru.toLowerCase());
//        this.tochucdangky_eng = convertToEng(tochucdangky.toLowerCase());
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
//    public String getTen_eng() {
//        return ten_eng;
//    }
//
//    public void setTen_eng(String ten_eng) {
//        this.ten_eng = ten_eng;
//    }
//
//    public String getHoatchat_eng() {
//        return hoatchat_eng;
//    }
//
//    public void setHoatchat_eng(String hoatchat_eng) {
//        this.hoatchat_eng = hoatchat_eng;
//    }
//
//    public String getNhom_eng() {
//        return nhom_eng;
//    }
//
//    public void setNhom_eng(String nhom_eng) {
//        this.nhom_eng = nhom_eng;
//    }
//
//    public String getDoituongphongtru_eng() {
//        return doituongphongtru_eng;
//    }
//
//    public void setDoituongphongtru_eng(String doituongphongtru_eng) {
//        this.doituongphongtru_eng = doituongphongtru_eng;
//    }
//
//    public String getTochucdangky_eng() {
//        return tochucdangky_eng;
//    }
//
//    public void setTochucdangky_eng(String tochucdangky_eng) {
//        this.tochucdangky_eng = tochucdangky_eng;
//    }

//    public String convertToEng(String str) {
//        str = str.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
//        str = str.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
//        str = str.replaceAll("ì|í|ị|ỉ|ĩ", "i");
//        str = str.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
//        str = str.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
//        str = str.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
//        str = str.replaceAll("đ", "d");
//
////        str = str.replaceAll("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A");
////        str = str.replaceAll("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E");
////        str = str.replaceAll("Ì|Í|Ị|Ỉ|Ĩ", "I");
////        str = str.replaceAll("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O");
////        str = str.replaceAll("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U");
////        str = str.replaceAll("Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y");
////        str = str.replaceAll("Đ", "D");
//        return str;
//    }

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
