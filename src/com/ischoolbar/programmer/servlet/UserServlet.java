package com.ischoolbar.programmer.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ischoolbar.programmer.dao.UserDao;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.page.Operator;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.page.SearchProperty;
import com.ischoolbar.programmer.util.StringUtil;
/**
 * �û����������
 * @author llq
 *
 */
public class UserServlet extends HttpServlet {

	private UserDao userDao = new UserDao();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1594262014744822081L;

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
		if("toUserListView".equals(method)){
			request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
			return;
		}
		if("UserList".equals(method)){
			getUserList(request,response);
			return;
		}
		if("AddUser".equals(method)){
			addUser(request,response);
			return;
		}
		if("EditUser".equals(method)){
			editUser(request,response);
			return;
		}
		if("DeleteUser".equals(method)){
			deleteUser(request,response);
			return;
		}
	}

	private void deleteUser(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		Map<String, String> ret = new HashMap<String, String>();
		if(ids == null || ids.length ==0){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��Ҫɾ��������!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		int[] idArr = new int[ids.length];
		for(int i = 0;i < ids.length; i++){
			idArr[i] = Integer.parseInt(ids[i]);
		}
		if(!userDao.delete(idArr)){
			ret.put("type", "error");
			ret.put("msg", "ɾ��ʧ�ܣ�����ϵ����Ա!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "ɾ���ɹ�!");
		writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void editUser(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String, String> ret = new HashMap<String, String>();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int id = Integer.parseInt(request.getParameter("id"));
		int type = Integer.parseInt(request.getParameter("type"));
		int status = Integer.parseInt(request.getParameter("status"));
		if(StringUtil.isEmpty(username)){
			ret.put("type", "error");
			ret.put("msg", "�û�������Ϊ��!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(StringUtil.isEmpty(password)){
			ret.put("type", "error");
			ret.put("msg", "���벻��Ϊ��!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(isExistUser(username, id)){
			ret.put("type", "error");
			ret.put("msg", "���û����Ѿ�����!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		user.setStatus(status);
		user.setType(type);
		if(!userDao.update(user)){
			ret.put("type", "error");
			ret.put("msg", "����ʧ�ܣ�����ϵ����Ա!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "���³ɹ�!");
		writrToPage(response, JSONObject.toJSONString(ret));
		return;
	}

	private void addUser(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String, String> ret = new HashMap<String, String>();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int type = Integer.parseInt(request.getParameter("type"));
		int status = Integer.parseInt(request.getParameter("status"));
		if(StringUtil.isEmpty(username)){
			ret.put("type", "error");
			ret.put("msg", "�û�������Ϊ��!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(StringUtil.isEmpty(password)){
			ret.put("type", "error");
			ret.put("msg", "���벻��Ϊ��!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(isExistUser(username, 0)){
			ret.put("type", "error");
			ret.put("msg", "���û����Ѿ�����!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setStatus(status);
		user.setType(type);
		if(!userDao.add(user)){
			ret.put("type", "error");
			ret.put("msg", "���ʧ�ܣ�����ϵ����Ա!");
			writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "��ӳɹ�!");
		writrToPage(response, JSONObject.toJSONString(ret));
		return;
	}

	private void getUserList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		if(username == null){
			username = "";
		}
		int pageNumber = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Page<User> page = new Page<User>(pageNumber, pageSize);
		page.getSearchProporties().add(new SearchProperty("username", "%"+username+"%", Operator.LIKE));
		
		//�жϵ�ǰ�û��Ƿ��ǹ���Ա����������ֻ�ܲ鿴���Լ�����Ϣ
		User loginedUser = (User)request.getSession().getAttribute("user");
		if(loginedUser.getType() == 2){
			//������ͨ�û�
			page.getSearchProporties().add(new SearchProperty("id", loginedUser.getId(), Operator.EQ));
		}
		
		page = userDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", page.getTotal());
		ret.put("rows", page.getContent());
		try {
			response.getWriter().write(JSONObject.toJSONString(ret));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void writrToPage(HttpServletResponse response,String content){
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean isExistUser(String username,int id){
		Page<User> page = new Page<User>(1, 10);
		page.getSearchProporties().add(new SearchProperty("username", username, Operator.EQ));
		page = userDao.findList(page);
		if(page.getContent().size() > 0){
			User user = page.getContent().get(0);
			if(user.getId() != id)return true;
		}
		return false;
	}
}
