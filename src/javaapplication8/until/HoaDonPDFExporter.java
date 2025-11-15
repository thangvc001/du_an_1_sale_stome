package javaapplication8.until;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javaapplication8.model.HoaDon;
import javaapplication8.model.HoaDonChiTiet_Model;
import javaapplication8.model.HoaDon_Model;
import javaapplication8.model.Model_SanPham;
import javaapplication8.model.SanPham_ChiTiet;
import javax.imageio.ImageIO;

public class HoaDonPDFExporter {

    public static boolean inHoaDonPDF(HoaDon_Model hd, List<HoaDonChiTiet_Model> list, String outputPath) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(outputPath));
            doc.open();

            BaseFont bf = BaseFont.createFont("resources/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(bf, 12);
            Font fontBold = new Font(bf, 13, Font.BOLD);

            Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", new Font(bf, 16, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph(" "));

            doc.add(new Paragraph("Mã hóa đơn: " + hd.getMaHD(), font));
            doc.add(new Paragraph("Ngày tạo: " + hd.getNgayTao(), font));
            doc.add(new Paragraph("Khách hàng: " + hd.getTenKhachHang(), font));
            doc.add(new Paragraph("Địa chỉ: " + hd.getDiaChiKhachHang(), font));
            doc.add(new Paragraph("SDT: " + hd.getSdtKH(), font));
            doc.add(new Paragraph("Nhân viên: " + hd.getMaNV(), font));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{30, 15, 25, 30});

            table.addCell(new PdfPCell(new Phrase("Tên sản phẩm", fontBold)));
            table.addCell(new PdfPCell(new Phrase("SL", fontBold)));
            table.addCell(new PdfPCell(new Phrase("Đơn giá", fontBold)));
            table.addCell(new PdfPCell(new Phrase("Thành tiền", fontBold)));

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            currencyFormat.setMaximumFractionDigits(0); // Không có phần thập phân
            currencyFormat.setCurrency(java.util.Currency.getInstance("VND")); // Đặt đơn vị VND

            for (HoaDonChiTiet_Model ct : list) {
                BigDecimal donGia = new BigDecimal(ct.getDonGia().replace(",", "").replace("VND", "").trim());
                BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(ct.getSoLuong()));

                table.addCell(new Phrase(ct.getTenSP(), font));
                table.addCell(new Phrase(String.valueOf(ct.getSoLuong()), font));
                table.addCell(new Phrase(currencyFormat.format(donGia), font));
                table.addCell(new Phrase(currencyFormat.format(thanhTien), font));
            }

            doc.add(table);
            doc.add(new Paragraph(" "));

            // 6. Tổng tiền
            Paragraph total = new Paragraph("Tổng tiền sau giảm: " + hd.getTongTienKhiGiam(), fontBold);
            total.setAlignment(Element.ALIGN_RIGHT);
            doc.add(total);
            doc.add(new Paragraph(" "));

            String qrHoaDonPath = hd.getQrHoaDon();  // Đường dẫn QR Code

            // Kiểm tra sự tồn tại của tệp
            File file = new File(qrHoaDonPath);
            if (!file.exists()) {
                System.out.println("Tệp hình ảnh không tồn tại: " + qrHoaDonPath);
            }
            // 7. Thêm mã QR
            Image qrImg = Image.getInstance(hd.getQrHoaDon());

            qrImg.scaleToFit(120, 120);
            qrImg.setAlignment(Image.ALIGN_RIGHT);
            doc.add(qrImg);

            doc.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
