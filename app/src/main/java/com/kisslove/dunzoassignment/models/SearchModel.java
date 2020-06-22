package com.kisslove.dunzoassignment.models;

public class SearchModel {
    private String text;
    private int pageNo;

    public SearchModel() {
    }

    public SearchModel(String text, int pageNo) {
        this.text = text;
        this.pageNo = pageNo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
