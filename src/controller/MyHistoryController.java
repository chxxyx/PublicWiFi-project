package controller;
import dao.WifiDAO;
import vo.HistoryVO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/insertHistory")
public class MyHistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");



        WifiDAO wifiDAO = new WifiDAO();

        double lat = Double.parseDouble(req.getParameter("lat"));
        double lnt = Double.parseDouble(req.getParameter("lnt"));
        HistoryVO historyVO = new HistoryVO();
        historyVO.setLat(lat);
        historyVO.setLnt(lnt);
        String rs = String.valueOf(wifiDAO.insertLocation(historyVO));
        resp.getWriter().write(rs);
    }
}
