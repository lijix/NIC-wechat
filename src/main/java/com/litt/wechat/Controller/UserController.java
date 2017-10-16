package com.litt.wechat.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.litt.nic.pojo.department;
import com.litt.nic.pojo.user;
import com.litt.nic.service.IDepartmentService;
import com.litt.nic.service.IUserService;
import com.litt.wechat.Util.Token.WeixinUtil;
import com.litt.wechat.Util.User.GetUserInfo;

/**
 * 用户身份信息的完善
 * 
 * @author litt
 * 
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IDepartmentService departmentService;

	private user user;

	@RequestMapping(value = "/load")
	public String loadWorkJsp(String code) {
		System.out.println("code------>" + code);
		String openid = WeixinUtil.getOpenid(code);
		System.out.println("--------");

		// request.setAttribute("openid", openid);
		return "redirect:/user/loadInfo?openid=" + openid;
	}

	/**
	 * 加载完善用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/loadInfo")
	public String loadInfo(HttpServletRequest request,
			HttpServletResponse response, String openid) throws IOException {
		System.out.println("--------" + openid);
		request.setAttribute("openid", openid);
		// 加载页面
		List<department> listDepartment = departmentService.findAllInfo();
		for (department department : listDepartment) {
			System.out.println(department.getDepartmentName());
		}
		request.setAttribute("listDepartment", listDepartment);
		user DataBaseUser = userService.findByOpenid(openid);
		// 数据库已存在此人
		if (DataBaseUser != null) {
			System.out.println(DataBaseUser.getUserName());
			response.setContentType("text/html; charset=UTF-8"); // 转码
			PrintWriter out = response.getWriter();
			out.flush();
			out.println("<script>");
			out.println("alert('此用户名已存在,请不要重复绑定！');");
			out.println("history.back();");
			out.println("</script>");
			System.out.println("要返回值了");
			request.setAttribute("dbuser", DataBaseUser);
			return "/jsp/user_info";
		}
		request.setAttribute("openid", openid);
		return "/jsp/user_info";
	}

	/**
	 * 搜索全部信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping(value = "/adduser")
	public String addUser(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, IOException {

		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String depart = request.getParameter("department");
		System.out.println("depart----" + depart);
		if ("0".equals(depart) || "".equals(name) || "".equals(telephone)) {

			if ("0".equals(depart)) {
				response.setContentType("text/html; charset=UTF-8"); // 转码
				PrintWriter out = response.getWriter();
				out.flush();
				out.println("<script>");
				out.println("alert('请选择所属部门！');");
				out.println("history.back();");
				out.println("</script>");
			} else if ("".equals(name)) {
				response.setContentType("text/html; charset=UTF-8"); // 转码
				PrintWriter out = response.getWriter();
				out.flush();
				out.println("<script>");
				out.println("alert('请输入您的姓名！');");
				out.println("history.back();");
				out.println("</script>");
			} else if ("".equals(telephone)) {
				response.setContentType("text/html; charset=UTF-8"); // 转码
				PrintWriter out = response.getWriter();
				out.flush();
				out.println("<script>");
				out.println("alert('请输入您的联系电话！');");
				out.println("history.back();");
				out.println("</script>");
			}

		} else {
			String openid = request.getParameter("openid");
			System.out.println("openid=" + openid);
			user DataBaseUser = userService.findByOpenid(openid);
			// 数据库已存在此人
			if (DataBaseUser != null) {
				System.out.println(DataBaseUser.getUserName());
				response.setContentType("text/html; charset=UTF-8"); // 转码
				PrintWriter out = response.getWriter();
				out.flush();
				out.println("<script>");
				out.println("alert('此用户名已存在,请不要重复绑定！');");
				out.println("history.back();");
				out.println("</script>");
			} else {
				user = GetUserInfo.getUserInfo(WeixinUtil.getAccessToken()
						.getAccessToken(), openid);
				user.setUserName(request.getParameter("name"));
				user.setUserTelephone(request.getParameter("telephone"));
				user.setUserDepartment(request.getParameter("department"));

				System.out.println("OpenID：" + user.getUserOpenid());
				System.out.println("关注状态：" + user.getUserSubscribe());
				System.out.println("关注时间：" + user.getUserSubscribetime());
				System.out.println("昵称：" + user.getUserNickname());
				System.out.println("性别：" + user.getUserSex());
				System.out.println("国家：" + user.getUserCountry());
				System.out.println("省份：" + user.getUserProvince());
				System.out.println("城市：" + user.getUserCity());
				System.out.println("语言：" + user.getUserLanguage());
				System.out.println("头像：" + user.getUserHeadimgurl());

				// 添加微信用户到数据库
				userService.addUser(user);
				response.setContentType("text/html; charset=UTF-8"); // 转码
				PrintWriter out = response.getWriter();
				out.flush();
				out.println("<script>");
				out.println("alert('添加成功！');");
				// out.println("history.back();");
				out.println("</script>");
				request.setAttribute("openid", openid);
				return "/jsp/work_info";
			}

		}
		System.out.println("name=" + name + "tele" + telephone);
		return null;
	}

	public static String getOpenId(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx6f7d35bce110c2e3&secret=162b3c93a3c265dbba32e5c9ddbff023&code="
				+ code + "&grant_type=authorization_code";
		String openId = "";
		try {
			URL getUrl = new URL(url);
			HttpURLConnection http = (HttpURLConnection) getUrl
					.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);

			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] b = new byte[size];
			is.read(b);
			String message = new String(b, "UTF-8");

			JSONObject json = JSONObject.fromObject(message);
			openId = json.getString("openid");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("此时的openid=" + openId);
		return openId;
	}

}
