package javaapplication8.swing;

import java.awt.Dimension;
import javax.swing.JTable;

public class Table extends JTable{

    public Table() {
        setBorder(null);

        // Xóa đường viền giữa các ô
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));

        // Xóa border của tiêu đề bảng
        getTableHeader().setBorder(null);
    }
    
    
}
