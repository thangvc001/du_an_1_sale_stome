/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.until;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javaapplication8.model.Top10SP;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author phamd
 */
public class FileExcelTop10SP {
    public static void exportTop10SPToExcel(List<Top10SP> list) {
    // 1. Chọn nơi lưu file
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
    fileChooser.setSelectedFile(new java.io.File("Top10SanPham.xlsx"));

    int result = fileChooser.showSaveDialog(null);
    if (result != JFileChooser.APPROVE_OPTION) {
        return; // Người dùng nhấn Cancel
    }

        File selectedFile = fileChooser.getSelectedFile();

    // 2. Tạo workbook
    try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(selectedFile)) {
        Sheet sheet = workbook.createSheet("Top 10 SP");
        String[] columns = {"STT", "Tên sản phẩm", "Số lượng", "Tổng tiền"};

        // 3. Ghi tiêu đề
        Row header = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        // 4. Ghi dữ liệu
        NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));
        int rowNum = 1;
        for (int i = 0; i < list.size(); i++) {
            Top10SP sp = list.get(i);
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(sp.getTenSP());
            row.createCell(2).setCellValue(sp.getSoLuong());
            row.createCell(3).setCellValue(format.format(sp.getTongTien()) + " VND");
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 5. Lưu file
        workbook.write(fos);
        JOptionPane.showMessageDialog(null, "Xuất Excel thành công:\n" + selectedFile.getAbsolutePath());

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi xuất file: " + e.getMessage());
    }
}

}
