package action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.JsonUtil;
import bean.CourseInfo;
import dao.CourseInfoDao;

/**
 * Servlet implementation class GetCourseServlet
 */
@WebServlet("/getCourse.action")
public class GetCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CourseInfoDao courseInfoDao;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCourseServlet() {
        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		courseInfoDao = new CourseInfoDao();
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
		String id = request.getParameter("id");
		//System.out.println(id);
		CourseInfo course = courseInfoDao.getCourseInfoById(id);
		//System.out.println(course.getStartTime()+":time");
		String courseJson = JsonUtil.objectToJson(course);
		response.getWriter().print(courseJson);
	}

}
