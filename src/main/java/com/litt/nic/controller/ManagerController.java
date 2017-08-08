package com.litt.nic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.litt.nic.pojo.manager;
import com.litt.nic.service.IManagerService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private IManagerService managerservice;

	@RequestMapping("/load")
	public String load(HttpServletRequest request) {
		List<manager> record = managerservice.selectAllManager();
		request.setAttribute("record", record);
		return "/WEB-INF/views/manager/managerlist";

	}

	@RequestMapping("/addmanager")
	public String registered(HttpServletRequest request,
			HttpServletResponse response, manager model) {
		String renewpass = request.getParameter("renewpass");
		System.out.println("新密码：" + renewpass);
		managerservice.insert(model);

		return "redirect:/manager/load";
	}

	@RequestMapping("/delete")
	public String delete(HttpServletRequest request) {
		Integer managerId = Integer.valueOf(request.getParameter("managerId"));
		System.out.println(managerId);
		managerservice.deleteByPrimaryKey(managerId);
		return "redirect:/manager/load";

	}

	@RequestMapping("/loadmanagerinfor")
	public String loadmanagerinformation(HttpServletRequest request,
			manager model) {

		Integer managerId = Integer.valueOf(request.getParameter("managerId"));
		manager managernews = managerservice.selectByPrimaryKey(managerId);
		System.out.println("/loadmanagerinfor" + managernews.toString());
		request.setAttribute("managernews", managernews);
		return "/WEB-INF/views/manager/updatemanager";

	}

	@RequestMapping("/update")
	public String update(HttpServletRequest request, manager model) {
		System.out.println("/update" + model.toString());
		managerservice.updateByPrimaryKeySelective(model);
		return "redirect:/manager/load";

	}

	// WEB-INF为安全目录，所以跳转经过Controller再跳转页面
	@RequestMapping("/toadd")
	public String ToRegistered(HttpServletRequest request) {
		return "/WEB-INF/views/manager/addmanager";

	}
}