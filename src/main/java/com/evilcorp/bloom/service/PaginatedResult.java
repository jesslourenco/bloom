package com.evilcorp.bloom.service;

import java.util.List;

public class PaginatedResult<T> {
  private List<T> data;
  private long totalRecords;
  private int totalPages;
  private int currentPage;
  private int pageSize;

  public PaginatedResult(List<T> data, long totalRecords, int totalPages, int currentPage, int pageSize) {
    this.data = data;
    this.totalRecords = totalRecords;
    this.totalPages = totalPages;
    this.currentPage = currentPage;
    this.pageSize = pageSize;
  }

  public List<T> getdata() {
    return data;
  }

  public void setdata(List<T> data) {
    this.data = data;
  }

  public long getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(long totalRecords) {
    this.totalRecords = totalRecords;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
