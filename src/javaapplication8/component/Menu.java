
package javaapplication8.component;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javaapplication8.event.EventMenuSelected;
import javaapplication8.model.Model_Menu;
import javax.swing.JFrame;

public class Menu extends javax.swing.JPanel {
    
    private EventMenuSelected event;
    
    public void addEventMenuSelected(EventMenuSelected event){
        listMenu2.addEventMenuSelected(event);
    }

    public Menu() {
        initComponents();
        setOpaque(false);
        init();
        
    }
    private void init(){
        listMenu2.addItem(new Model_Menu("1", "Thống kê", Model_Menu.MenuType.MENU));
        listMenu2.addItem(new Model_Menu("2", "Sản phẩm", Model_Menu.MenuType.MENU));
        listMenu2.addItem(new Model_Menu("3", "Nhân viên", Model_Menu.MenuType.MENU));      
        listMenu2.addItem(new Model_Menu("4", "Bán hàng", Model_Menu.MenuType.MENU));
        listMenu2.addItem(new Model_Menu("5", "Khách hàng", Model_Menu.MenuType.MENU));
        listMenu2.addItem(new Model_Menu("6", "Hóa đơn", Model_Menu.MenuType.MENU));        
        listMenu2.addItem(new Model_Menu("7", "Khuyến mãi", Model_Menu.MenuType.MENU));
        listMenu2.addItem(new Model_Menu("8", "Đổi mật khẩu", Model_Menu.MenuType.MENU));  
        listMenu2.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY)); 
        
        listMenu2.addItem(new Model_Menu("9", "Đăng xuất", Model_Menu.MenuType.MENU));
        
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listMenu1 = new javaapplication8.swing.ListMenu();
        header1 = new javaapplication8.component.Header();
        panelMoving = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        listMenu2 = new javaapplication8.swing.ListMenu<>();

        panelMoving.setOpaque(false);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/logo.png"))); // NOI18N
        jLabel2.setText("SALES STORM");

        javax.swing.GroupLayout panelMovingLayout = new javax.swing.GroupLayout(panelMoving);
        panelMoving.setLayout(panelMovingLayout);
        panelMovingLayout.setHorizontalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelMovingLayout.setVerticalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        listMenu2.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(listMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(565, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 87, Short.MAX_VALUE)
                    .addComponent(listMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint g1 = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0,getHeight(), Color.decode("#000046"));
        g2.setPaint(g1);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    private int x;
    private int y;
    
    public void initMoving(JFrame fram) {
        panelMoving.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
             }           
        });
        
        panelMoving.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
            fram.setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
            }
           
        });
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapplication8.component.Header header1;
    private javax.swing.JLabel jLabel2;
    private javaapplication8.swing.ListMenu listMenu1;
    private javaapplication8.swing.ListMenu<String> listMenu2;
    private javax.swing.JPanel panelMoving;
    // End of variables declaration//GEN-END:variables
}
