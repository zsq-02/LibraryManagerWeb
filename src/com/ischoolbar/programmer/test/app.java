//package com.ischoolbar.programmer.test;
//
//import java.util.List;
//
//import com.ischoolbar.programmer.entity.Clazz;
//import com.ischoolbar.programmer.entity.Student;
//import com.ischoolbar.programmer.page.Operator;
//import com.ischoolbar.programmer.page.Page;
//import com.ischoolbar.programmer.page.SearchProperty;
//import com.mysql.jdbc.log.Log;
//
//
///**
// * �����࣬���Ը��ַ���
// * @author llq
// *
// */
//public class app {
//
//	
//	public static void main(String[] args) {
//		ClazzManager clazzManager = new ClazzManager();
////		Clazz clazz = new Clazz();
////		clazz.setClazzName("�������");
////		clazz.setMaxNumber(70);
////		clazz.setId(10);
//		Page<Clazz> clazzPage = new Page<Clazz>(1, 2);
//		clazzPage.getSearchProporties().add(new SearchProperty("id", 10, Operator.EQ));
////		page.getSearchProporties().add(new SearchProperty("clazz_name", "%�߶�%", Operator.LIKE));
////		page.getSearchProporties().add(new SearchProperty("max_number", 30, Operator.GT));
//		List<Clazz> finList = clazzManager.finList(clazzPage);
//		//log(clazzManager.addClazz(clazz));
//		StudentManager studentManager = new StudentManager();
////		Student student = new Student();
////		student.setAge(28);
////		student.setClazz(clazzManager.find(11));
////		student.setName("�ž���");
////		student.setSex("��");
////		student.setId(34);
////		studentManager.updateStudent(student);
//		Page<Student> page = new Page<Student>(1, 4);
////		page.getSearchProporties().add(new SearchProperty("id", 6, Operator.GTE));
////		page.getSearchProporties().add(new SearchProperty("student_name", "%��%", Operator.LIKE));
////		page.getSearchProporties().add(new SearchProperty("student_sex", "��", Operator.EQ));
//		page.getSearchProporties().add(new SearchProperty("student_clazz", finList.get(0), Operator.EQ));
//		//List<Student> finList = studentManager.finList(page);
//		page = studentManager.find(page);
//		log(page.getContent().size() + "---" + page.getTotal() + "---" + page.getTotalPage());
//	}
//	
//	public static void log(Object str){
//		System.out.println(str);
//	}
//}
