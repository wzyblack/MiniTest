package action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CourseInfoDao;

/**
 * Servlet implementation class DeleteCourse
 */
@WebServlet("/deleteCourse.action")
public class DeleteCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CourseInfoDao courseInfoDao;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCourse() {
        super();
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
		String id = request.getParameter("courIds");
		String msg = "删除失败";
		String[] ids = null;
		if(id!=null && id.indexOf(",") != -1){
			ids = id.split(",");
		}
		else if(id != null){
			ids = new String[]{id};
		}
		if(courseInfoDao.deleteCourse(ids)){
			msg = "删除成功";
		}
		response.getWriter().print(msg);
	}
}
