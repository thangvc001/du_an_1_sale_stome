/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.until;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phamd
 */
public class STT {
    public static void updateSTT(DefaultTableModel model, int startSTT) {
        // Lưu danh sách listener hiện có
        TableModelListener[] listeners = model.getTableModelListeners();
        
        // Xóa tất cả listener để tránh vòng lặp vô hạn
        for (TableModelListener listener : listeners) {
            model.removeTableModelListener(listener);
        }

        // Cập nhật STT
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(startSTT + i, i, 0);
        }

        // Khôi phục listener sau khi cập nhật xong
        for (TableModelListener listener : listeners) {
            model.addTableModelListener(listener);
        }
    }
}
