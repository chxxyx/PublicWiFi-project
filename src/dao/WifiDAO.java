package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import data.GetJsonData;

public class WifiDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public WifiDAO() {
        try {
            String dbURL = "jdbc:mariadb://localhost:3306/testdb1";
            String dbID = "root";
            String dbPassword = "1234";
            Class.forName("org.mariadb.jdbc.Driver");

            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public int getData() {
        String SQL = "INSERT INTO TbPublicWifiInfo VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            GetJsonData jsonData = new GetJsonData();

            String Data = jsonData.getData();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(Data); // 전체를json으
            JSONObject TbPublicWifiInfo = (JSONObject) jsonObject.get("TbPublicWifiInfo");

            JSONArray rows = (JSONArray) TbPublicWifiInfo.get("row");

            for (int i = 0; i > rows.size(); i++) {

                JSONObject row = (JSONObject) rows.get(i);

                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, (String) row.get("X_SWIFI_MGR_NO"));
                pstmt.setString(2, (String) row.get("X_SWIFI_WRDOFC"));
                pstmt.setString(3, (String) row.get("X_SWIFI_MAIN_NM"));
                pstmt.setString(4, (String) row.get("X_SWIFI_ADRES1"));
                pstmt.setString(5, (String) row.get("X_SWIFI_ADRES2"));
                pstmt.setString(6, (String) row.get("X_SWIFI_INSTL_FLOOR"));
                pstmt.setString(7, (String) row.get("X_SWIFI_INSTL_TY"));
                pstmt.setString(8, (String) row.get("X_SWIFI_INSTL_MBY"));
                pstmt.setString(9, (String) row.get("X_SWIFI_SVC_SE"));
                pstmt.setString(10, (String) row.get("X_SWIFI_CMCWR"));
                pstmt.setString(11, (String) row.get("X_SWIFI_CNSTC_YEAR"));
                pstmt.setString(12, (String) row.get("X_SWIFI_INOUT_DOOR"));
                pstmt.setString(13, (String) row.get("X_SWIFI_REMARS3"));
                pstmt.setString(14, (String) row.get("LAT"));
                pstmt.setString(15, (String) row.get("LNT"));
                pstmt.setString(16, (String) row.get("WORK_DTTM"));

            }

            return pstmt.executeUpdate();
        } catch (Exception e) {

        }
        return -1;

    }
}