package filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodFilter implements Filter{

	public void destroy() {
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) arg1;
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest req = (HttpServletRequest)arg0;
		String reqType = req.getMethod();
		if(reqType.equals("POST")){
			req.setCharacterEncoding("UTF-8");
		}
		else if(reqType.equals("GET")){
			Enumeration<String> enumeration = req.getParameterNames();
			//遍历所有的键
			while(enumeration.hasMoreElements()){
				String key = enumeration.nextElement();
				//获取键中的所有值
				String[] values = req.getParameterValues(key);
				for(int i = 0; i < values.length; i++){
					values[i] = new String(values[i].getBytes("iso-8859-1"),"UTF-8");
				}
			}
		}
		arg2.doFilter(req, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
