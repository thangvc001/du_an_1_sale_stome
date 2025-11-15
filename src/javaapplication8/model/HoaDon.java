/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.model;

import java.util.List;

/**
 *
 * @author phamd
 */
public class HoaDon {

    private String maHD, tenSP, khuyenMai, phuongThucTT, tenKH, sdtKH, diaChi, tenNV;
    private int soLuong;
    private double tongTien, tienKhachDua, tienThua;
    private List<SanPham_ChiTiet> danhSachSanPham;

    public HoaDon() {
    }

    public HoaDon(String maHD, String tenSP, String khuyenMai, String phuongThucTT, String tenKH, String sdtKH, String diaChi, String tenNV, int soLuong, double tongTien, double tienKhachDua, double tienThua, List<SanPham_ChiTiet> danhSachSanPham) {
        this.maHD = maHD;
        this.tenSP = tenSP;
        this.khuyenMai = khuyenMai;
        this.phuongThucTT = phuongThucTT;
        this.tenKH = tenKH;
        this.sdtKH = sdtKH;
        this.diaChi = diaChi;
        this.tenNV = tenNV;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.tienKhachDua = tienKhachDua;
        this.tienThua = tienThua;
        this.danhSachSanPham = danhSachSanPham;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    

    public List<SanPham_ChiTiet> getDanhSachSanPham() {
        return danhSachSanPham;
    }

    public void setDanhSachSanPham(List<SanPham_ChiTiet> danhSachSanPham) {
        this.danhSachSanPham = danhSachSanPham;
    }

    

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(String khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public String getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(String phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdtKH() {
        return sdtKH;
    }

    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public double getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(double tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    public double getTienThua() {
        return tienThua;
    }

    public void setTienThua(double tienThua) {
        this.tienThua = tienThua;
    }

}
