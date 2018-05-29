package com.ran.bmsx.book.service.impl;

import com.ran.bmsx.book.model.BookInfo;
import com.ran.bmsx.book.service.BookService;
import com.ran.bmsx.core.PageResult;
import java.util.List;

public class BookServiceImpl implements BookService {


    @Override
    public PageResult<BookInfo> getBookInfos(int pageNum, int pageSize, Integer status, String searchKey, String searchValue) {
        return null;
    }

    @Override
    public List<BookInfo> getBookInfos(BookInfo bookInfo) {
        return null;
    }

    @Override
    public BookInfo getBookInfoById(String bookInfoId) {
        return null;
    }

    @Override
    public boolean addBookInfo(BookInfo bookInfo) {
        return false;
    }

    @Override
    public boolean updateBookInfo(BookInfo bookInfo) {
        return false;
    }

    @Override
    public boolean deleteBookInfo(String bookInfoId) {
        return false;
    }
}
