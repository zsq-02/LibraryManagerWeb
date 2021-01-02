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
 * ͼ����������
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
	 * �ϴ�ͼƬ
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
			//�˴����ļ����浽������
			String realPath = request.getRealPath("images");
			System.out.println(realPath);
			// �Ե�ǰʱ���ΪͼƬ����             
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String strCurrentTime = df.format(new Date());
                        //�����ļ��洢λ��
            String imagePath = realPath + "/" + strCurrentTime + ".jpg";
            // ������ļ���ʽ���������޸ģ���.jpg
            FileOutputStream fos = new FileOutputStream(imagePath);
            byte[] bbuf = new byte[32];
            int hasRead = 0;
            while ((hasRead = uploadInputStream.read(bbuf)) > 0) {
                fos.write(bbuf, 0, hasRead);
                // ���ļ�д���������Ӳ����
            }
            fos.close();
            uploadInputStream.close();
			
            ret.put("type", "success");
			ret.put("filepath", "images/"+strCurrentTime + ".jpg");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			
            
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			ret.put("type", "error");
			ret.put("msg", "�ϴ�Э�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}catch (NullFileException e1) {
			
			ret.put("type", "error");
			ret.put("msg", "�ϴ����ļ�Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		catch (SizeException e2) {
			ret.put("type", "error");
			ret.put("msg", "�ϴ��ļ���С���ܳ���"+fileUpload.getFileSize()+"!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		catch (IOException e3) {
			ret.put("type", "error");
			ret.put("msg", "��ȡ�ļ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		catch (FileFormatException e4) {
			ret.put("type", "error");
			ret.put("msg", "�ϴ��ļ���ʽ����ȷ�����ϴ� "+fileUpload.getFileFormat()+" ��ʽ���ļ�!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		catch (FileUploadException e5) {
			ret.put("type", "error");
			ret.put("msg", "�ϴ��ļ�ʧ��!");
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
		bookCategory.setName("ȫ��");
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
			ret.put("msg", "��ѡ��Ҫɾ��������!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		int[] idArr = new int[ids.length];
		for(int i = 0;i < ids.length; i++){
			idArr[i] = Integer.parseInt(ids[i]);
		}
		if(!bookDao.delete(idArr)){
			ret.put("type", "error");
			ret.put("msg", "ɾ��ʧ�ܣ�����ϵ����Ա!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "ɾ���ɹ�!");
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
			ret.put("msg", "ͼ�����Ʋ���Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		BookCategory bookCategory = bookCategoryDao.find(bookCategoryId);
		if(bookCategory == null){
			ret.put("type", "error");
			ret.put("msg", "ͼ����಻��Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		//���ͼ�������Ƿ���ڽ������
		Book oldBook = bookDao.find(id);
		//�Ѿ����������
		int borrowedNumber = oldBook.getNumber() - oldBook.getFreeNumber();
		if(number < borrowedNumber){
			ret.put("type", "error");
			ret.put("msg", "��������С���Ѿ����������!");
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
			ret.put("msg", "ͼ�����ʧ�ܣ�����ϵ����Ա!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "ͼ����³ɹ�!");
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
			ret.put("msg", "ͼ�����Ʋ���Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		BookCategory bookCategory = bookCategoryDao.find(bookCategoryId);
		if(bookCategory == null){
			ret.put("type", "error");
			ret.put("msg", "ͼ����಻��Ϊ��!");
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
			ret.put("msg", "ͼ�����ʧ�ܣ�����ϵ����Ա!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "ͼ����ӳɹ�!");
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
