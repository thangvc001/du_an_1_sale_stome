/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.model;

/**
 *
 * @author dungc
 */
public class SanPham_ThuocTinh {

    private int id;
    private String ma;
    private String ten;
    private boolean daXoa;

    public SanPham_ThuocTinh() {
    }

    public SanPham_ThuocTinh(int id, String ma, String ten, boolean daXoa) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.daXoa = daXoa;
    }

    public SanPham_ThuocTinh(int id, String ma, String ten) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isDaXoa() {
        return daXoa;
    }

    public void setDaXoa(boolean daXoa) {
        this.daXoa = daXoa;
    }

}

