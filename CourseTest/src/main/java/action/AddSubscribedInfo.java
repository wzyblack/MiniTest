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
import bean.SubscribedInfo;
import bean.SubscribedModel;
import dao.CourseInfoDao;
import dao.SubscribedInfoDao;
@WebServlet("/subscribedInfo")
public class AddSubscribedInfo extends HttpServlet{

	private SubscribedInfoDao sInfoDao;
	private CourseInfoDao cInfoDao;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5804705013718726489L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String subscribedInfo = req.getParameter("subscribedInfo");
		SubscribedModel sModel = JsonUtil.jsonToObject(subscribedInfo, SubscribedModel.class);
		SubscribedInfo sInfo = new SubscribedInfo(sModel.getSalaryId(), sModel.getCourseId(), EpointDateUtil.convertString2DateAuto(sModel.getStartTime()), 
				EpointDateUtil.convertString2DateAuto(sModel.getEndTime()), 0, sModel.getNote());
		sInfo.setActualHours((int)((sInfo.getEndTime().getTime()-sInfo.getStartTime().getTime())/(60*60*1000)));
		String msg = "添加失败";
		if(sInfoDao.addSubscribed(sInfo) && cInfoDao.updateCourseInfo(sInfo.getActualHours(), 1, sInfo.getCourseId())){
			PropertiesUtil.setNum(sInfo.getSalaryId(), "id");
			msg = "添加成功";
		}
		resp.getWriter().print(msg);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		sInfoDao = new SubscribedInfoDao();
		cInfoDao = new CourseInfoDao();
	}
}
