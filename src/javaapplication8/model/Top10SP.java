
package javaapplication8.model;

import java.math.BigDecimal;

public class Top10SP {
    
    private String tenSP;
    private int soLuong;
    private BigDecimal TongTien;

    public Top10SP() {
    }

    public Top10SP(String tenSP, int soLuong, BigDecimal TongTien) {
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.TongTien = TongTien;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getTongTien() {
        return TongTien;
    }

    public void setTongTien(BigDecimal TongTien) {
        this.TongTien = TongTien;
    }
    
     
    
    
}
