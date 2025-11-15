/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.model;

/**
 *
 * @author dungc
 */
public class Model_SanPham {

    private int id;
    private String maSp;
    private String ten;
    private String moTa;
    private int soLuong;
    private boolean daXoa; //đang bán - ngưng bán

    public Model_SanPham() {
    }

    public Model_SanPham(int id, String maSp, String ten, String moTa, int soLuong, boolean daXoa) {
        this.id = id;
        this.maSp = maSp;
        this.ten = ten;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.daXoa = daXoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isDaXoa() {
        return daXoa;
    }

    public void setDaXoa(boolean daXoa) {
        this.daXoa = daXoa;
    }

    }
