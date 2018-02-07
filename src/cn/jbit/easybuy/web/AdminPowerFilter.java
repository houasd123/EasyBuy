package cn.jbit.easybuy.web;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.jbit.easybuy.entity.User;
import cn.jbit.easybuy.util.Validator;

@WebFilter(urlPatterns = {"/manage/Category","/manage/Product","/manage/GuestBook","/manage/News"}) 
public class AdminPowerFilter implements Filter {	
	public void destroy() {		
	}
	//�����������ڹ���Ա�Ĳ�����������ͨ�û���δ��¼����ת����¼ҳ
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		HttpSession session = req.getSession();
		User user=(User) session.getAttribute("loginUser");//��ȡ��ǰ��¼�û�
		if(null==user || !(user.isAdministrator())){//����ͨ�û���δ��¼����ת����¼ҳ
			Validator validator = new Validator(Validator
					.toSingleParameters(req));
			validator.addError("userId", "�û��޴�Ȩ��");
			req.setAttribute("errors", validator.getErrors());
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
			return;
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig filterConfig) throws ServletException {		
	}	
}
