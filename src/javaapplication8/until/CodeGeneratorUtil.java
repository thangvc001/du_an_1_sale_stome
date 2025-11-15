/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.until;

import java.security.SecureRandom;

/**
 *
 * @author phamd
 */
public class CodeGeneratorUtil {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 5;
    private static final SecureRandom RANDOM = new SecureRandom();
    
    public static String generateSanPham(){
        StringBuilder code = new StringBuilder("SP-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generateMauSac(){
        StringBuilder code = new StringBuilder("MS-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generateKichThuoc(){
        StringBuilder code = new StringBuilder("KT-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generateChatLieu(){
        StringBuilder code = new StringBuilder("CL-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generateSanPhamChiTiet(){
        StringBuilder code = new StringBuilder("SPCT-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generateNhanVien(){
        StringBuilder code = new StringBuilder("NV-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generateHoaDon(){
        StringBuilder code = new StringBuilder("HD-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generateHoaDonChiTiet(){
        StringBuilder code = new StringBuilder("HDCT-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generateKhachHang(){
        StringBuilder code = new StringBuilder("KH-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generatePhieuGiamGia(){
        StringBuilder code = new StringBuilder("VC-");
        for(int i = 0; i< CODE_LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
}
