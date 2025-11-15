package javaapplication8.dao;

import java.sql.SQLException;
import java.util.List;

public interface PhanTrangDao<T> {
    
    int countAll() throws SQLException;
    
    List<T> getPage(int offset, int limit) throws SQLException;
}
