package com.yqx.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yqx.dao.EmployeeDao;
import com.yqx.daoImpl.EmployeeDaoImpl;
import com.yqx.entity.Employee;
import com.yqx.vo.EmployeeVO;

@Controller
public class EmployeeController {
	
	@RequestMapping("/empadd")
	public void add(HttpServletRequest request,HttpServletResponse response,Employee s,Map<String,Object> map) throws Exception {
		EmployeeDao dao = new EmployeeDaoImpl();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		try {
			dao.add(s);
			jo.put("state", 0);
			jo.put("msg", "成功新增记录");
		} catch (Exception e) {
			jo.put("state", -1);
			jo.put("msg", "新增记录失败" + e.getMessage());
		} finally {
			String str = JSON.toJSONString(jo);
			out.write(str);
			out.flush();
			out.close();
		}
		
	}
	@RequestMapping("/empdeleteById")
	public void deleteById(int id) {
		EmployeeDao dao = new EmployeeDaoImpl();
		dao.deleteById(id);
	}
	@RequestMapping("/empdeleteMore")
	@ResponseBody
	public void deleteMore(HttpServletRequest request,HttpServletResponse response,String ids,Map<String,Object> map) throws Exception {
		EmployeeDao dao = new EmployeeDaoImpl();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		try {
			dao.deleteMore(ids);
			jo.put("state", 0);
			jo.put("msg", "成功删除记录");
		} catch (Exception e) {
			jo.put("state", -1);
			jo.put("msg", "删除记录失败" + e.getMessage());
		} finally {
			String str = JSON.toJSONString(jo);
			out.write(str);
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping("/empupdate")
	@ResponseBody
	public void update(HttpServletRequest request,HttpServletResponse response,Employee s,Map<String,Object> map) throws Exception {
		EmployeeDao dao = new EmployeeDaoImpl();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		try {
			dao.update(s);
			jo.put("state", 0);
			jo.put("msg", "成功修改记录");
		} catch (Exception e) {
			jo.put("state", -1);
			jo.put("msg", "修改记录失败" + e.getMessage());
		} finally {
			String str = JSON.toJSONString(jo);
			out.write(str);
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping("/empqueryById")
	@ResponseBody
	public Employee queryById(HttpServletRequest request,HttpServletResponse response,int id,String currentPage,Map<String,Object> map) {
		String qname = request.getParameter("qname");
		String qusername = request.getParameter("qusername");
		String qsex = request.getParameter("qsex");
		
		EmployeeDao dao = new EmployeeDaoImpl();
		Employee s = dao.queryById(id);
		map.put("student", s);
		map.put("currentPage", currentPage);
		map.put("qname", qname);
		map.put("qusername", qusername);
		map.put("qsex", qsex);
		return s;
	}
	/*@RequestMapping("/empqueryByPage")
	@ResponseBody
	public void queryByPage(HttpServletRequest request,HttpServletResponse response,String page,Map<String,Object> map) throws Exception {
		String qname = request.getParameter("qname");
		String qusername = request.getParameter("qusername");
		String qsex = request.getParameter("qsex");
		String qbeginDate = request.getParameter("qbeginDate");
		String qendDate = request.getParameter("qendDate");

		String currentPage = request.getParameter("page");
		String rows = request.getParameter("rows");

		String condition = " where 1=1 ";
		if (qname != null && !qname.equals("") && !qname.equalsIgnoreCase("null")) {
			condition += " and name like '%" + qname + "%'";
		}
		if (qusername != null && !qusername.equals("") && !qname.equalsIgnoreCase("null")) {
			condition += " and username like '%" + qusername + "%'";
		}
		if (qsex != null && !qsex.equals("") && !qsex.equals("-1") && !qname.equalsIgnoreCase("null")) {
			condition += " and sex = " + qsex;
		}
		if (qbeginDate != null && !qbeginDate.equals("")) {
			condition += " and birthday >= '" + qbeginDate + "'";
		}
		if (qendDate != null && !qendDate.equals("")) {
			condition += " and birthday <= '" + qendDate + "'";
		}
		EmployeeDao dao = new EmployeeDaoImpl();

		int sp = 1;

		int totals = dao.getTotals(condition);

		int pageSize = Integer.parseInt(rows);

		int pageCounts = totals / pageSize;
		if (totals % pageSize != 0) {
			pageCounts++;
		}
		try {
			sp = Integer.parseInt(currentPage);
		} catch (Exception e) {
			sp = 1;
		}
		if (sp > pageCounts) {
			sp = pageCounts;
		}
		if (sp < 1) {
			sp = 1;
		}
		List<Employee> list = dao.queryByPage(sp, pageSize, condition);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		jo.put("total", totals);
		jo.put("rows", list);
		String json = JSON.toJSONString(jo);
		out.write(json);
		out.flush();
		out.close();
	}*/
	
	@RequestMapping("/empqueryByPage")
	@ResponseBody
	public void queryByPage(HttpServletRequest request,HttpServletResponse response,String page,Map<String,Object> map) throws Exception {
		String qname = request.getParameter("qname");
		String qusername = request.getParameter("qusername");
		String qsex = request.getParameter("qsex");
		String qbeginDate = request.getParameter("qbeginDate");
		String qendDate = request.getParameter("qendDate");

		String currentPage = request.getParameter("page");
		String rows = request.getParameter("rows");

		String condition = " where 1=1 ";
		if (qname != null && !qname.equals("") && !qname.equalsIgnoreCase("null")) {
			condition += " and name like '%" + qname + "%'";
		}
		if (qusername != null && !qusername.equals("") && !qname.equalsIgnoreCase("null")) {
			condition += " and username like '%" + qusername + "%'";
		}
		if (qsex != null && !qsex.equals("") && !qsex.equals("-1") && !qname.equalsIgnoreCase("null")) {
			condition += " and sex = " + qsex;
		}
		if (qbeginDate != null && !qbeginDate.equals("")) {
			condition += " and birthday >= '" + qbeginDate + "'";
		}
		if (qendDate != null && !qendDate.equals("")) {
			condition += " and birthday <= '" + qendDate + "'";
		}
		EmployeeDao dao = new EmployeeDaoImpl();

		int sp = 1;

		int totals = dao.getTotals();

		int pageSize = Integer.parseInt(rows);

		int pageCounts = totals / pageSize;
		if (totals % pageSize != 0) {
			pageCounts++;
		}
		try {
			sp = Integer.parseInt(currentPage);
		} catch (Exception e) {
			sp = 1;
		}
		if (sp > pageCounts) {
			sp = pageCounts;
		}
		if (sp < 1) {
			sp = 1;
		}
		List<EmployeeVO> list = dao.getByPage(sp, pageSize);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		jo.put("total", totals);
		jo.put("rows", list);
		String json = JSON.toJSONString(jo);
		out.write(json);
		out.flush();
		out.close();
	}
	
	/*
	 * 处理参数为日期格式
	 * */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),
	            true));
	}
}
