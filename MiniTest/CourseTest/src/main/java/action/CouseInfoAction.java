package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.PerUtils;
import bean.CourseInfo;
import dao.CourseInfoDao;

/**
 * Servlet implementation class CouseInfoAction
 */
@WebServlet("/courseInfos.action")
public class CouseInfoAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CourseInfoDao courseInfoDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CouseInfoAction() {
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
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"))+1;
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String courseName = request.getParameter("courseName");
		String typeString = request.getParameter("type");
		int type = 0;
		if(typeString!=null && !typeString.equals("")){
			type = Integer.parseInt(typeString);
		}
		//System.out.println(courseName);
		List<CourseInfo> courseInfos = courseInfoDao.getCourseInfo(pageSize, pageIndex, courseName,type);
		int total = courseInfoDao.getAllCount(courseName);
		//System.out.println(courseInfos.get(0).getStartTime()+":"+courseInfos.get(0).getNote());
		String infoString = PerUtils.getAuto(courseInfos, total);
		response.getWriter().print(infoString);
	}

	@Override
	public void init() throws ServletException {
		courseInfoDao = new CourseInfoDao();
		super.init();
	}

	
}
