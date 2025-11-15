
package javaapplication8.service;

import java.util.List;
import javaapplication8.model.PhuongThucThanhToan;

public interface PhuongThucThanhToanService {
    List<PhuongThucThanhToan> layPhuongThucThanhToan();

    PhuongThucThanhToan layIDTheoTen(String ten);
}

