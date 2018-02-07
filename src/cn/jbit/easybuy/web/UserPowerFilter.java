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

@WebFilter(urlPatterns = {"/manage/Order","/manage/User","/GuestBook"}) 
public class UserPowerFilter implements Filter {
	
	public void destroy() {		
	}
	//�������ϵ�ַ�����δ��¼���޴�Ȩ��
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		HttpSession session = req.getSession();
		User user=(User) session.getAttribute("loginUser");//��ȡ��ǰ�û�
		if(null==user){			
			Validator validator = new Validator(Validator
					.toSingleParameters(req));
			validator.addError("userId", "��δ��¼");
			req.setAttribute("errors", validator.getErrors());
			req.getRequestDispatcher("/login.jsp").forward(req, resp);			
			return;			
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig filterConfig) throws ServletException {		
	}	
}
