package javaapplication8.service.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javaapplication8.dao.ThongKeDao;
import javaapplication8.model.ThongKeDoanhThu;
import javaapplication8.model.ThongKeDoanhThuNgay;
import javaapplication8.model.Top10SP;
import javaapplication8.service.ThongKeService;

public class ThongKeServiceImpl implements ThongKeService {

    private ThongKeDao dao = new ThongKeDao();

    @Override
    public List<Top10SP> layTop10SP(Date ngayBD, Date ngayKT) {
        return dao.thongKeTop10SanPham(ngayBD, ngayKT);
    }

    @Override
    public ThongKeDoanhThu thongKeDoanhThu(Date ngayBD, Date ngayKT) {
        return dao.thongKeDoanhThu(ngayBD, ngayKT);
    }

    @Override
    public List<ThongKeDoanhThuNgay> getDoanhThuTheoNgay(Date tuNgay, Date denNgay) {
        return dao.getDoanhThuTheoNgay(tuNgay, denNgay);
    }

    @Override
    public List<ThongKeDoanhThuNgay> getDoanhThuTheoThang(int nam) {
        return dao.getDoanhThuTheoThang(nam);
    }

    @Override
    public List<ThongKeDoanhThuNgay> getDoanhThuTheoNam() {
        return dao.getDoanhThuTheoNam();
    }

    @Override
    public List<ThongKeDoanhThuNgay> getDoanhThuTheo5NamGanNhat() {
        return dao.getDoanhThuTheo5NamGanNhat();
    }

    @Override
    public List<ThongKeDoanhThuNgay> getDoanhThuTheoNgayTrongThang(int nam, int thang) {
        return dao.getDoanhThuTheoNgayTrongThang(nam, thang);
    }

}
