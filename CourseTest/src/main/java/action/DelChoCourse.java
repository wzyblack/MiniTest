package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubscribedInfoDao;

/**
 * Servlet implementation class DelChoCourse
 */
@WebServlet("/deleteChooseCourse.action")
public class DelChoCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SubscribedInfoDao sInfoDao;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelChoCourse() {
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
		String salaryIds = request.getParameter("salaryIDs");
		String[] ids = null;
		String msg = "删除失败";
		if (salaryIds != null) {
			if(salaryIds.indexOf(",") != -1){
				ids = salaryIds.split(",");
			}
			else{
				ids = new String[]{salaryIds};
			}
			if(sInfoDao.deleteSub(ids)){
				msg = "删除成功";
			}
		}
		response.getWriter().print(msg);
	}

	@Override
	public void init() throws ServletException {
		sInfoDao = new SubscribedInfoDao();
		super.init();
	}
}
