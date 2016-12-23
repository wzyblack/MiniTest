package action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.PropertiesUtil;

/**
 * Servlet implementation class GetCourseId
 */
@WebServlet("/getCourseNum.action")
public class GetCourseId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public GetCourseId() {
    }

    public void init(ServletConfig config) throws ServletException {
    	
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = GetCourseId.class.getResource("/").getPath();
		path = path.replaceAll("%20", " ");
		PropertiesUtil.local = path+"num.properties";
		int num = PropertiesUtil.getNum();
		String id = "KC";
		if(num < 10){
			id+="000"+num;
		}
		else if(num >= 10 && num <= 99){
			id+="00"+num;
		}
		else if(num >= 100 && num <= 999){
			id+="0"+num;
		}
		else{
			id+=num;
		}
		response.getWriter().print("{'id':'"+id+"'}");
	}

}
