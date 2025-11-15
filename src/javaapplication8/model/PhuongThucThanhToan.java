
package javaapplication8.model;

public class PhuongThucThanhToan {
    
    private int id;
    
    private String tenPT;

    public PhuongThucThanhToan() {
    }

    public PhuongThucThanhToan(int id, String tenPT) {
        this.id = id;
        this.tenPT = tenPT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenPT() {
        return tenPT;
    }

    public void setTenPT(String tenPT) {
        this.tenPT = tenPT;
    }
    
    
    
}
