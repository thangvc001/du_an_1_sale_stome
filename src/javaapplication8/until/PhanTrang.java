/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.until;

import java.util.List;

/**
 *
 * @author phamd
 */
public class PhanTrang {

    private int currentPage = 1;
    private int pageSize;
    private int totalItems = 0;

    public PhanTrang(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalItems / pageSize);
    }

    public void firstPage() {
        currentPage = 1;
    }

    public void lastPage() {
        currentPage = getTotalPages();
    }

    public void nextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
        }
    }

    public void prevPage() {
        if (currentPage > 1) {
            currentPage--;
        }
    }

    public boolean isFirstPage() {
        return currentPage == 1;
    }

    public boolean isLastPage() {
        return currentPage == getTotalPages();
    }
}
