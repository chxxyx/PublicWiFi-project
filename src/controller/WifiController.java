package controller;

import com.google.gson.Gson;
import dao.WifiDAO;
import vo.WifiVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getList")
public class WifiController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        Double lat = Double.parseDouble(req.getParameter("lat"));
        Double lnt = Double.parseDouble(req.getParameter("lnt"));

        WifiDAO dao = new WifiDAO();
        List<WifiVO> list = dao.selectwifi(lat,lnt);
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(list));
    }
}
