package action;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.PropertiesUtil;

/**
 * Servlet implementation class GetSalaryInServlet
 */
@WebServlet("/getSalaryId")
public class GetSalaryInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSalaryInServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		String slalaryId = PropertiesUtil.getKCNum();
		LocalDate localDate = LocalDate.now();
		String dateString = localDate.getYear()+""+localDate.getMonthValue();
		if(!dateString.equals(slalaryId.substring(2, slalaryId.length()-4))){
			slalaryId = "KC"+dateString+"0001";
		}
		else{
			int num = Integer.parseInt(slalaryId.substring(slalaryId.length()-3))+1;
			slalaryId = slalaryId.substring(0, slalaryId.length()-4);
			//System.out.println(slalaryId);
			
			//System.out.println(num);
			if(num < 10){
				slalaryId+="000"+num;
			}
			else if(num >= 10 && num <= 99){
				slalaryId+="00"+num;
			}
			else if(num >= 100 && num <= 999){
				slalaryId+="0"+num;
			}
			else{
				slalaryId+=num;
			}
		}
		response.getWriter().print("{'salaryId':'"+slalaryId+"'}");
	}

}
