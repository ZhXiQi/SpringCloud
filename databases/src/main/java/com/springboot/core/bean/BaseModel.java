package com.springboot.core.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基本数据
 */
public class BaseModel implements Serializable {
    //TODO reponse 过滤这些字段
    protected transient int page=1;
    protected transient int pageSize=10;
    protected transient String order;
    protected transient String sort;
    protected transient String keyWord;

    private transient Long fromCreateTime;
    private transient Long toCreateTime;

    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected transient int offset;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getOffset() {
        return (page-1)*pageSize;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getFromCreateTime() {
        if(fromCreateTime==null)
            return null;
        return dateFormat.format(new Date(fromCreateTime));
    }

    public void setFromCreateTime(Long fromCreateTime) {
        this.fromCreateTime = fromCreateTime;
    }

    public String getToCreateTime() {
        if(toCreateTime==null)
            return null;
        return dateFormat.format(new Date(toCreateTime));
    }

    public void setToCreateTime(Long toCreateTime) {
        this.toCreateTime = toCreateTime;
    }
}