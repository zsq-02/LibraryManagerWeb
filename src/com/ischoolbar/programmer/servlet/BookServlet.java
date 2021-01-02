package com.ischoolbar.programmer.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.alibaba.fastjson.JSONObject;
import com.ischoolbar.programmer.dao.BookCategoryDao;
import com.ischoolbar.programmer.dao.BookDao;
import com.ischoolbar.programmer.entity.Book;
import com.ischoolbar.programmer.entity.BookCategory;
import com.ischoolbar.programmer.page.Operator;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.page.SearchProperty;
import com.ischoolbar.programmer.util.StringUtil;
import com.lizhou.exception.FileFormatException;
import com.lizhou.exception.NullFileException;
import com.lizhou.exception.ProtocolException;
import com.lizhou.exception.SizeException;
import com.lizhou.fileload.FileUpload;
/**
 * 图书管理控制器
 * @author llq
 *
 */
public class BookServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4386109520796986005L;

	private BookDao bookDao = new BookDao();
	
	private BookCategoryDao bookCategoryDao = new BookCategoryDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Object attribute = request.getParameter("method");
		String method = "";
		if(attribute != null){
			method = attribute.toString();
		}
		if("toBookListView".equals(method)){
			request.getRequestDispatcher("/WEB-INF/views/book.jsp").forward(request, response);
			return;
		}
		if("BookList".equals(method)){
			getBookList(request,response);
			return;
		}
		if("AddBook".equals(method)){
			addBook(request,response);
			return;
		}
		if("EditBook".equals(method)){
			editBook(request,response);
			return;
		}
		if("DeleteBook".equals(method)){
			deleteBook(request,response);
			return;
		}
		if("GetBookCategoryComboxData".equals(method)){
			getBookCategoryComboxData(request,response);
			return;
		}
		if("uploadPhoto".equals(method)){
			uploadPhoto(request,response);
			return;
		}
	}

	/**
	 * 上传图片
	 * @param request
	 * @param response
	 */
	private void uploadPhoto(HttpServletRequest request,
			HttpServletResponse response) {
		
		FileUpload fileUpload = new FileUpload(request);
		fileUpload.setFileFormat("jpg");
		fileUpload.setFileFormat("png");
		fileUpload.setFileFormat("jpeg");
		fileUpload.setFileFormat("gif");
		fileUpload.setFileSize(2048);
		response.setCharacterEncoding("UTF-8");
		Map<String, String> ret = new HashMap<String, String>();
		try {
			InputStream uploadInputStream = fileUpload.getUploadInputStream();
			//此处将文件保存到服务器
			String realPath = request.getRealPath("images");
			System.out.println(realPath);
			// 以当前时间戳为图片命名             
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String strCurrentTime = df.format(new Date());
                        //最终文件存储位置
            String imagePath = realPath + "/" + strCurrentTime + ".jpg";
            // 这里的文件格式可以自行修改，如.jpg
            FileOutputStream fos = new FileOutputStream(imagePath);
            byte[] bbuf = new byte[32];
            int hasRead = 0;
            while ((hasRead = uploadInputStream.read(bbuf)) > 0) {
                fos.write(bbuf, 0, hasRead);
                // 将文件写入服务器的硬盘上
            }
            fos.close();
            uploadInputStream.close();
			
            ret.put("type", "success");
			ret.put("filepath", "images/"+strCurrentTime + ".jpg");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			
            
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			ret.put("type", "error");
			ret.put("msg", "上传协议错误!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}catch (NullFileException e1) {
			
			ret.put("type", "error");
			ret.put("msg", "上传的文件为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		catch (SizeException e2) {
			ret.put("type", "error");
			ret.put("msg", "上传文件大小不能超过"+fileUpload.getFileSize()+"!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		catch (IOException e3) {
			ret.put("type", "error");
			ret.put("msg", "读取文件出错!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		catch (FileFormatException e4) {
			ret.put("type", "error");
			ret.put("msg", "上传文件格式不正确，请上传 "+fileUpload.getFileFormat()+" 格式的文件!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		catch (FileUploadException e5) {
			ret.put("type", "error");
			ret.put("msg", "上传文件失败!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
	}

	private void getBookCategoryComboxData(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Page<BookCategory> page = new Page<BookCategory>(1, 999);
		page = bookCategoryDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		BookCategory bookCategory = new BookCategory();
		bookCategory.setId(0);
		bookCategory.setName("全部");
		page.getContent().add(bookCategory);
		ret.put("type", "success");
		ret.put("values", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void deleteBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		Map<String, String> ret = new HashMap<String, String>();
		if(ids == null || ids.length ==0){
			ret.put("type", "error");
			ret.put("msg", "请选中要删除的数据!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		int[] idArr = new int[ids.length];
		for(int i = 0;i < ids.length; i++){
			idArr[i] = Integer.parseInt(ids[i]);
		}
		if(!bookDao.delete(idArr)){
			ret.put("type", "error");
			ret.put("msg", "删除失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void editBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		int bookCategoryId = Integer.parseInt(request.getParameter("bookCategoryId"));
		int status = Integer.parseInt(request.getParameter("status"));
		int number = Integer.parseInt(request.getParameter("number"));
		String photo = request.getParameter("photo");
		String info = request.getParameter("info");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(name)){
			ret.put("type", "error");
			ret.put("msg", "图书名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		BookCategory bookCategory = bookCategoryDao.find(bookCategoryId);
		if(bookCategory == null){
			ret.put("type", "error");
			ret.put("msg", "图书分类不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		//检查图书总数是否大于借出的数
		Book oldBook = bookDao.find(id);
		//已经借出的数量
		int borrowedNumber = oldBook.getNumber() - oldBook.getFreeNumber();
		if(number < borrowedNumber){
			ret.put("type", "error");
			ret.put("msg", "数量不能小于已经借出的数量!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		
		Book book = new Book();
		book.setId(id);
		book.setBookCategory(bookCategory);
		book.setName(name);
		book.setStatus(status);
		book.setNumber(number);
		book.setFreeNumber(number-borrowedNumber);
		book.setInfo(info);
		book.setPhoto(photo);
		if(book.getFreeNumber() == 0){
			book.setStatus(2);
		}
		
		if(!bookDao.update(book)){
			ret.put("type", "error");
			ret.put("msg", "图书更新失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "图书更新成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void addBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int bookCategoryId = Integer.parseInt(request.getParameter("bookCategoryId"));
		int status = Integer.parseInt(request.getParameter("status"));
		int number = Integer.parseInt(request.getParameter("number"));
		String photo = request.getParameter("photo");
		String info = request.getParameter("info");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(name)){
			ret.put("type", "error");
			ret.put("msg", "图书名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		BookCategory bookCategory = bookCategoryDao.find(bookCategoryId);
		if(bookCategory == null){
			ret.put("type", "error");
			ret.put("msg", "图书分类不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		Book book = new Book();
		book.setBookCategory(bookCategory);
		book.setName(name);
		book.setStatus(status);
		book.setNumber(number);
		book.setFreeNumber(number);
		book.setPhoto(photo);
		book.setInfo(info);
		if(!bookDao.add(book)){
			ret.put("type", "error");
			ret.put("msg", "图书添加失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "图书添加成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void getBookList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String bbcid = request.getParameter("bookCategoryId");
		if(name == null){
			name = "";
		}
		int pageNumber = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Page<Book> page = new Page<Book>(pageNumber, pageSize);
		page.getSearchProporties().add(new SearchProperty("name", "%"+name+"%", Operator.LIKE));
		if(!StringUtil.isEmpty(bbcid) && !"0".equals(bbcid)){
			page.getSearchProporties().add(new SearchProperty("book_category", bookCategoryDao.find(Integer.parseInt(bbcid)), Operator.EQ));
		}
		page = bookDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", page.getTotal());
		ret.put("rows", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}
	
}
