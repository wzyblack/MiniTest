package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.PerUtils;
import bean.SubscribedInfo;
import dao.SubscribedInfoDao;

/**
 * Servlet implementation class ChooseCourseAction
 */
@WebServlet("/ChooseCourse.action")
public class ChooseCourseAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SubscribedInfoDao sInfoDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChooseCourseAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		sInfoDao = new SubscribedInfoDao();
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
		String courseId = request.getParameter("courseId");
		String actualHours = request.getParameter("actualHours");
		int totalHouse = 0;
		if(actualHours != null && !actualHours.trim().equals("")){
			totalHouse = Integer.parseInt(actualHours);
		}
		int total = sInfoDao.getAllSubscribedInfo(courseId, totalHouse);
		List<SubscribedInfo> sInfos = sInfoDao.getSubscribedInfo(pageIndex, pageSize, courseId, totalHouse);
		String sInfosJson = PerUtils.getAuto(sInfos, total);
		response.getWriter().print(sInfosJson);
	}

}
