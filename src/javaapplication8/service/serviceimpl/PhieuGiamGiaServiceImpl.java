package javaapplication8.service.serviceimpl;

import java.util.List;
import javaapplication8.dao.PhieuGiamGiaDao;
import javaapplication8.model.PhieuGiamGiaModel;
import javaapplication8.service.PhieuGiamGiaService;

public class PhieuGiamGiaServiceImpl implements PhieuGiamGiaService {

    private PhieuGiamGiaDao dao = new PhieuGiamGiaDao();

    @Override
    public List<PhieuGiamGiaModel> getAll() {
        return dao.getAll();
    }

    @Override
    public List<PhieuGiamGiaModel> getByTrangThai(int trangThai) {
        return dao.getByTrangThai(trangThai);
    }

    @Override
    public PhieuGiamGiaModel layPhieuGiamGiaTheoTen(String phieuGiamGia) {
        return dao.layPhieuGiamGiaTheoTen(phieuGiamGia);
    }

    @Override
    public boolean capNhatSoLuongPhieuGiamGia(int id) {
        return dao.capNhatSoLuongPhieuGiamGia(id);
    }

}
