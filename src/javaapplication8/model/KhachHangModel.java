package javaapplication8.model;

import java.math.BigDecimal;

public class KhachHangModel {

    private int id;
    private String maKH;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private boolean gioiTinh;
    private String maHD;
    private BigDecimal tongTien;
    private int trangThai;
    private int idHD;
    private String ngayTao;

    public KhachHangModel() {
    }

    public KhachHangModel(int id, String maKH, String hoTen, String sdt, String diaChi, boolean gioiTinh, String maHD, BigDecimal tongTien, int trangThai, int idHD, String ngayTao) {
        this.id = id;
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.maHD = maHD;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.idHD = idHD;
        this.ngayTao = ngayTao;
    }

    public KhachHangModel(String maKH, String hoTen, boolean gioiTinh, String sdt, String diaChi) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
    }

    public KhachHangModel(String maKH, String tenKH, int id) {
        this.maKH = maKH;
        this.hoTen = tenKH;
        this.id = id;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

}
