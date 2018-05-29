package com.ran.bmsx.book.service;


import com.ran.bmsx.book.model.BookInfo;
import com.ran.bmsx.core.PageResult;
import java.util.List;

/**
 * BookService
 * 
 */
public interface BookService {

	/**
	 * 查询所有图书
	 */
	public PageResult<BookInfo> getBookInfos(int pageNum, int pageSize, Integer status, String searchKey, String searchValue);

	/**
	 * 根据账号查询图书
	 */
	public List<BookInfo> getBookInfos(BookInfo bookInfo);

	/**
	 * 根据id查询图书
	 */
	public BookInfo getBookInfoById(String bookInfoId);

	/**
	 * 添加图书
	 */
	public boolean addBookInfo(BookInfo bookInfo);

	/**
	 * 修改图书
	 */
	public boolean updateBookInfo(BookInfo bookInfo);

	/**
	 * 删除图书
	 */
	public boolean deleteBookInfo(String bookInfoId);

}
