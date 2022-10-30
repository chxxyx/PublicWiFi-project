package vo;

import java.sql.Date;
import java.sql.Timestamp;

public class HistoryVO {
    // id, x(위도 lat), y(경도 lnt), 조회일자
    private int id;
    private Double lat;
    private Double lnt;
    private Timestamp date;

    public HistoryVO() {}

    public HistoryVO(int id, Double lat, Double lnt, Timestamp date) {
        this.id = id;
        this.lat = lat;
        this.lnt = lnt;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLnt() {
        return lnt;
    }

    public void setLnt(Double lnt) {
        this.lnt = lnt;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
