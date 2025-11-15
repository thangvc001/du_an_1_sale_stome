package javaapplication8.model;

public class Model_NhanVien {
    
    private int id;
    private String tenNV;
    private boolean gioiTinh;
    private String ngaySinh;
    private String sodienThoai;
    private String email;
    private String diaChi;
    private String tenDN;
    private String mk;
    private boolean vaiTro;
    private boolean trangThai;
    private String maNV;

    public Model_NhanVien() {
    }

    public Model_NhanVien(int id, String tenNV, boolean gioiTinh, String ngaySinh, String sodienThoai, String email, String diaChi, String tenDN, String mk, boolean vaiTro, boolean trangThai, String maNV) {
        this.id = id;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sodienThoai = sodienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.tenDN = tenDN;
        this.mk = mk;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
        this.maNV = maNV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSodienThoai() {
        return sodienThoai;
    }

    public void setSodienThoai(String sodienThoai) {
        this.sodienThoai = sodienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    
    
    
}
