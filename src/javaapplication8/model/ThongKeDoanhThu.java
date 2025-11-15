
package javaapplication8.model;

import java.math.BigDecimal;

public class ThongKeDoanhThu {
    
    private BigDecimal tongTien;
    private int tongSP;
    private int tongHD;

    public ThongKeDoanhThu() {
    }

    public ThongKeDoanhThu(BigDecimal tongTien, int tongSP, int tongHD) {
        this.tongTien = tongTien;
        this.tongSP = tongSP;
        this.tongHD = tongHD;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public int getTongSP() {
        return tongSP;
    }

    public void setTongSP(int tongSP) {
        this.tongSP = tongSP;
    }

    public int getTongHD() {
        return tongHD;
    }

    public void setTongHD(int tongHD) {
        this.tongHD = tongHD;
    }
    
    
    
}
