package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.EpointDateUtil;
import utils.JsonUtil;
import bean.CourseInfo;
import bean.CourseModel;
import dao.CourseInfoDao;

/**
 * Servlet implementation class EditCourseInfo
 */
@WebServlet("/editCourse.action")
public class EditCourseInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CourseInfoDao courseInfoDao;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCourseInfo() {
        super();
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
		CourseModel course = JsonUtil.jsonToList(courseJson, CourseModel.class).get(0);
		CourseInfo courseInfo = new CourseInfo(course.getId(), course.getCourseName(), EpointDateUtil.convertString2DateAuto(course.getStartTime()), course.getNote(), course.getSuitable(), course.getType(), course.getTotalHouse(), course.getHotLevel(), course.getSelectedCount());
		String msg = "修改成功";
		if(!courseInfoDao.editCourseInfo(courseInfo)){
			msg = "修改失败";
		}
		response.getWriter().print(msg);
	}

	@Override
	public void init() throws ServletException {
		courseInfoDao = new CourseInfoDao();
	}
}
