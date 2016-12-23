package action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.JsonUtil;
import bean.CourseNameModel;
import dao.CourseInfoDao;

/**
 * Servlet implementation class GetCourse
 */
@WebServlet("/courseNames.action")
public class GetCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CourseInfoDao courseInfoDao;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCourse() {
        
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
		List<CourseNameModel> cNameModels = courseInfoDao.getCourseName();
		//int total = courseInfoDao.getAllCount(null);
		HashMap<String, Object> cMap = new HashMap<String, Object>();
		cMap.put("data", cNameModels);
		String json = JsonUtil.objectToJson(cMap);
		//String courseJson = JsonUtil.listToJson(cNameModels);
		response.getWriter().print(json);
	}

}
