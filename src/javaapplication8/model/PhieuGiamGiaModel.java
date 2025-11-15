
package javaapplication8.model;

import java.math.BigDecimal;

public class PhieuGiamGiaModel {
    
    private int id;
    private String maPGG;
    private String tenPGG;
    private String ngayDB;
    private String ngayKT;
    private int soLuong;
    private BigDecimal HDToiThieu;
    private int phantramgiam;
    private BigDecimal giamToiDa;
    private String ngayTao;
    private String maNV;
    private int trangThai;
    private int idNV;


    public PhieuGiamGiaModel() {
    }

    public PhieuGiamGiaModel(int id, String maPGG, String tenPGG, String ngayDB, String ngayKT, int soLuong, BigDecimal HDToiThieu, int phantramgiam, BigDecimal giamToiDa, String ngayTao, String maNV, int trangThai, int idNV) {
        this.id = id;
        this.maPGG = maPGG;
        this.tenPGG = tenPGG;

        this.ngayDB = ngayDB;
        this.ngayKT = ngayKT;
        this.soLuong = soLuong;
        this.HDToiThieu = HDToiThieu;
        this.phantramgiam = phantramgiam;
        this.giamToiDa = giamToiDa;
        this.ngayTao = ngayTao;
        this.maNV = maNV;
        this.trangThai = trangThai;
        this.idNV = idNV;
    }

    
    public int getIdNV() {
        return idNV;
    }

    public void setIdNV(int idNV) {
        this.idNV = idNV;
    }

    

    public String getTenPGG() {
        return tenPGG;
    }

    public void setTenPGG(String tenPGG) {
        this.tenPGG = tenPGG;
    }

    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaPGG() {
        return maPGG;
    }

    public void setMaPGG(String maPGG) {
        this.maPGG = maPGG;
    }

    public String getNgayDB() {
        return ngayDB;
    }

    public void setNgayDB(String ngayDB) {
        this.ngayDB = ngayDB;
    }

    public String getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(String ngayKT) {
        this.ngayKT = ngayKT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getHDToiThieu() {
        return HDToiThieu;
    }

    public void setHDToiThieu(BigDecimal HDToiThieu) {
        this.HDToiThieu = HDToiThieu;
    }

    


    public int getPhantramgiam() {
        return phantramgiam;
    }

    public void setPhantramgiam(int phantramgiam) {
        this.phantramgiam = phantramgiam;
    }

    public BigDecimal getGiamToiDa() {
        return giamToiDa;
    }

    public void setGiamToiDa(BigDecimal giamToiDa) {

        this.giamToiDa = giamToiDa;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    
}
