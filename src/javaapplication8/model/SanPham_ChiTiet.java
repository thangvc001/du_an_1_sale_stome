/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.model;

/**
 *
 * @author phamd
 */
public class SanPham_ChiTiet {

    private int id;
    private String maSp;
    private String tenSp;
    private String kichThuoc;
    private String mauSac;
    private String chatLieu;
    private String donGia;
    private int soLuong;
    private boolean daXoa;
    private String maSPCT;
    private String qrCode;

    public SanPham_ChiTiet() {
    }

    public SanPham_ChiTiet(int id, String maSp, String tenSp, String kichThuoc, String mauSac, String chatLieu, String donGia, int soLuong, boolean daXoa, String maSPCT, String qrCode) {
        this.id = id;
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.kichThuoc = kichThuoc;
        this.mauSac = mauSac;
        this.chatLieu = chatLieu;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.daXoa = daXoa;
        this.maSPCT = maSPCT;
        this.qrCode = qrCode;
    }

    public SanPham_ChiTiet(int id, String maSp, String tenSp, String kichThuoc, String mauSac, String chatLieu, String donGia, int soLuong) {
        this.id = id;
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.kichThuoc = kichThuoc;
        this.mauSac = mauSac;
        this.chatLieu = chatLieu;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

