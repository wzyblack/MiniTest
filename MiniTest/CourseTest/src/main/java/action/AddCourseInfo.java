package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.EpointDateUtil;
import utils.JsonUtil;
import utils.PropertiesUtil;
import bean.CourseInfo;
import bean.CourseModel;
import dao.CourseInfoDao;

/**
 * Servlet implementation class AddCourseInfo
 */
@WebServlet("/addCourse.action")
public class AddCourseInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CourseInfoDao courseInfoDao; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCourseInfo() {
        
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
		String courseJson = request.getParameter("course");
		//System.out.println(courseJson);
		CourseModel course = JsonUtil.jsonToList(courseJson, CourseModel.class).get(0);
		CourseInfo courseInfo = new CourseInfo(course.getId(), course.getCourseName(), EpointDateUtil.convertString2DateAuto(course.getStartTime()), course.getNote(), course.getSuitable(), course.getType(), course.getTotalHouse(), course.getHotLevel(), course.getSelectedCount());
		String msg = "添加失败";
		if(courseInfoDao.addCourse(courseInfo)){
			msg = "添加成功";
			PropertiesUtil.setNum(courseInfo.getId().substring(2),"courseNum");
		}
		response.getWriter().print(msg);
	}

	@Override
	public void init() throws ServletException {
		courseInfoDao = new CourseInfoDao();
		new PropertiesUtil();
	}

	
}
