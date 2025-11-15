
package javaapplication8.model;

import java.math.BigDecimal;

public class ThongKeDoanhThuNgay {
    private String nhomThoiGian;
    private BigDecimal tongTien;

    public ThongKeDoanhThuNgay() {
    }

    public ThongKeDoanhThuNgay(String nhomThoiGian, BigDecimal tongTien) {
        this.nhomThoiGian = nhomThoiGian;
        this.tongTien = tongTien;
    }

    public String getNhomThoiGian() {
        return nhomThoiGian;
    }

    public void setNhomThoiGian(String nhomThoiGian) {
        this.nhomThoiGian = nhomThoiGian;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
    
    
    
}
