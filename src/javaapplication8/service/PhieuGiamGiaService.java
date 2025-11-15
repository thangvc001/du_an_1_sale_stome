
package javaapplication8.service;

import java.util.List;
import javaapplication8.model.PhieuGiamGiaModel;

public interface PhieuGiamGiaService {
    
    List<PhieuGiamGiaModel> getAll();
    
    List<PhieuGiamGiaModel> getByTrangThai(int trangThai);

    public PhieuGiamGiaModel layPhieuGiamGiaTheoTen(String phieuGiamGia);
    
    boolean capNhatSoLuongPhieuGiamGia(int id);
    
}
