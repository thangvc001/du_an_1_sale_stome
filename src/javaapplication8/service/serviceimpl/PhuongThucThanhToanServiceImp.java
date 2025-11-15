package javaapplication8.service.serviceimpl;

import java.util.List;
import javaapplication8.dao.PhuongThucThanhToanDao;
import javaapplication8.model.PhuongThucThanhToan;
import javaapplication8.service.PhuongThucThanhToanService;

public class PhuongThucThanhToanServiceImp implements PhuongThucThanhToanService {

    private PhuongThucThanhToanDao dao = new PhuongThucThanhToanDao();

    @Override
    public List<PhuongThucThanhToan> layPhuongThucThanhToan() {
        return dao.layPhuongThucThanhToan();
    }

    @Override
    public PhuongThucThanhToan layIDTheoTen(String ten) {
        return dao.layPhuongThucThanhToanTheoTen(ten);
    }

}
