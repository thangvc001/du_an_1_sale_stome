package javaapplication8.model;

public class HoaDonChiTiet_Model {
    private int id;
    private String maHDCT;
    private int idHD;
    private int idSPCT;
    private int soLuong;
    private String donGia;
    private String maSPCT;
    private String tenSP;
    private String mauSac;
    private String chatLieu;
    private String kichThuoc;
    private String thanhTien;
    public HoaDonChiTiet_Model() {
    }

    public HoaDonChiTiet_Model(int id, String maHDCT, int idHD, int idSPCT, int soLuong, String donGia, String maSPCT, String tenSP, String mauSac, String chatLieu, String kichThuoc, String thanhTien) {
        this.id = id;
        this.maHDCT = maHDCT;
        this.idHD = idHD;
        this.idSPCT = idSPCT;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.maSPCT = maSPCT;
        this.tenSP = tenSP;
        this.mauSac = mauSac;
        this.chatLieu = chatLieu;
        this.kichThuoc = kichThuoc;
        this.thanhTien = thanhTien;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
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

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(String maHDCT) {
        this.maHDCT = maHDCT;
    }

    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public int getIdSPCT() {
        return idSPCT;
    }

    public void setIdSPCT(int idSPCT) {
        this.idSPCT = idSPCT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }
    
}
