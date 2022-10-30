package dao;

import data.GetJsonData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import vo.HistoryVO;
import vo.WifiVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Statement stmt;

    // db 연결
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

    // 공공와이파이 정보 데이터 가져오기
    public int getData() {
        String SQL = "INSERT INTO TbPublicWifiInfo VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            GetJsonData jsonData = new GetJsonData();

            String Data = jsonData.getData();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(Data); // 전체를json으
            JSONObject TbPublicWifiInfo = (JSONObject) jsonObject.get("TbPublicWifiInfo");

            JSONArray rows = (JSONArray) TbPublicWifiInfo.get("row");

            for (int i = 0; i < rows.size(); i++) {

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
                pstmt.setString(14, (String) row.get("LAT")); //
                pstmt.setString(15, (String) row.get("LNT")); //
                pstmt.setString(16, (String) row.get("WORK_DTTM"));

                pstmt.executeUpdate();
            }
            return 200;

        } catch (Exception e) {

        }
        return -1;

    }

    // 현재 위치 정보 삽입
    public int insertLocation(HistoryVO vo) {

        String SQL = "INSERT INTO wifi_History (lat, lnt, date) VALUES (?, ?, now())";
        int ret = -1;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setDouble(1, vo.getLat());
            pstmt.setDouble(2, vo.getLnt());

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close(); // 풀에 반환
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close(); // 풀에 반환
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }


    // 위치 히스토리 목록 조회
    public List<HistoryVO> selectLocation() {

        String SQL = "SELECT * FROM wifi_History ORDER BY id desc";
        ArrayList<HistoryVO> ls = new ArrayList<HistoryVO>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                HistoryVO vo = new HistoryVO(
                        rs.getInt(1),
                        rs.getDouble(2),
                        rs.getDouble(3),
                        rs.getTimestamp(4));
                    ls.add(vo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close(); // 풀에 반환
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    try {
                        conn.close(); // 풀에 반환
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return ls;
        }
    // 위치 히스토리 삭제하기
    public int deleteLocation (int id) {
        String SQL = "DELETE FROM wifi_History WHERE id = ?";
        int ret = -1;

        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, id);
            ret = pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close(); // 풀에 반환
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close(); // 풀에 반환
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    // 거리 계산해서 와이파이 정보 가져오기
    public List<WifiVO> selectwifi(Double lat,Double lnt) {

        String SQL = "SELECT * FROM TbPublicWifiInfo";
        List<WifiVO> wifiList = new ArrayList<WifiVO>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                double lat2 = Double.parseDouble(rs.getString("LAT"));
                double lnt2 = Double.parseDouble(rs.getString("LNT"));
                String distance = String
                                    .valueOf(getDistance(lat,lnt ,lat2,lnt2))
                                    .substring(0,6)
                        ;


                WifiVO vo = new WifiVO(
                        distance,
                        rs.getString("X_SWIFI_MGR_NO"),
                        rs.getString("X_SWIFI_WRDOFC"),
                        rs.getString("X_SWIFI_MAIN_NM"),
                        rs.getString("X_SWIFI_ADRES1"),
                        rs.getString("X_SWIFI_ADRES2"),
                        rs.getString("X_SWIFI_INSTL_FLOOR"),
                        rs.getString("X_SWIFI_INSTL_TY"),
                        rs.getString("X_SWIFI_INSTL_MBY"),
                        rs.getString("X_SWIFI_SVC_SE"),
                        rs.getString("X_SWIFI_CMCWR"),
                        rs.getString("X_SWIFI_CNSTC_YEAR"),
                        rs.getString("X_SWIFI_INOUT_DOOR"),
                        rs.getString("X_SWIFI_REMARS3"),
                        rs.getString("LAT"),
                        rs.getString("LNT"),
                        rs.getString("WORK_DTTM")
                        );


                wifiList.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close(); // 풀에 반환
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close(); // 풀에 반환
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return wifiList;
    }


    /*
         공공 데이터와 내 위치 거리 계산을 위해 필요한 메소드
     */

    // 1. km 기준
    private Double getDistance(Double lat, Double lnt, Double lat2, Double lnt2) {
        double theta = lnt - lnt2;
        double dist = Math.sin(deg2rad(lat))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;
        return dist / 1000;
    }

    // 2. 10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg){
        return (deg * Math.PI/180.0);

    }
    // 3. radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }
}