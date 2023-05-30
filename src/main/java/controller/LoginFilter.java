package controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@WebFilter("/*")
public class LoginFilter extends HttpFilter implements Filter {
       
    
	private static final long serialVersionUID = 1L;


	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		HttpSession session = httpServletRequest.getSession(false);
		
		boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);
		
		String loginURI = httpServletRequest.getContextPath() + "/login";
		String cadastroURI = httpServletRequest.getContextPath() + "/cadastrarUsuario.jsp";
		String insertSummonerURI = httpServletRequest.getContextPath() + "/insert-summoner";
		String insertUsuarioURI = httpServletRequest.getContextPath() + "/insert-usuario";
        boolean isLoginRequest = httpServletRequest.getRequestURI().equals(loginURI);
        boolean isCadastroRequest = httpServletRequest.getRequestURI().equals(cadastroURI);
        boolean isInsertSummonerRequest = httpServletRequest.getRequestURI().equals(insertSummonerURI);
        boolean isInsertUsuarioRequest = httpServletRequest.getRequestURI().equals(insertUsuarioURI);
        boolean isLoginPage = httpServletRequest.getRequestURI().endsWith("login.jsp");
        boolean isCSSResource = httpServletRequest.getRequestURI().startsWith("/PlayerStats/css/");
        boolean isJSResource = httpServletRequest.getRequestURI().startsWith("/PlayerStats/js/");
        
		
		if(isLoggedIn && (isLoginRequest || isLoginPage)) {
			
			RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("main");
			dispatcher.forward(request, response);
			
		} else if(!isLoggedIn && (isLoginRequest || isCadastroRequest || isInsertSummonerRequest || isInsertUsuarioRequest
				|| isCSSResource || isJSResource)){
			
			chain.doFilter(request, response);	
			
		}else if(!isLoggedIn){
			RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			
		} else {
			
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public LoginFilter() {
		super();
	}
	
	
	public void destroy() {
	}
}
