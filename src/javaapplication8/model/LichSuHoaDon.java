
package javaapplication8.model;

public class LichSuHoaDon {
    
    private int id;
    private String maNV;
    private String gio;
    private String ngay;
    private String hanhDong;
    

    public LichSuHoaDon() {
    }

    public LichSuHoaDon(int id, String maNV, String gio, String ngay, String hanhDong) {
        this.id = id;
        this.maNV = maNV;
        this.gio = gio;
        this.ngay = ngay;
        this.hanhDong = hanhDong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }
    
    
    
}
