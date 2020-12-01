package ru.itmo.web.beans;

import javax.faces.bean.ManagedBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.web.InAreaChecker.InAreaChecker;
import ru.itmo.web.model.Point;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HUANG SIYUAN
 */
@Setter
@ManagedBean(name = "pointsBean")
@ApplicationScoped
public class PointsBean implements Serializable {

    //Here x,y,r is for creating points from form
    // -3 -2 -1 0 1 2 3 4 5

    /**
     * x,y,r are from the main form.
     *
     * @param x int {-3.-2,-1,0,1,2,3,4,5}
     * @param y double (-5,5)
     * @param r int {1,1.5,2,2.5,3}
     */
    @Getter
    private double x;
    @Getter
    private double y;
    @Getter
    private double r;

    /**
     * hiddenX,hiddenY,hiddenR are from the hidden form. Click the svg can set them.
     *
     * @param hiddenX double
     * @param hiddenY double
     * @param hiddenR {1,1.5,2,2.5,3} R has to be the value in the variant.
     */
    @Getter
    private double hiddenX;
    @Getter
    private double hiddenY;
    @Getter
    private double hiddenR;

    @Getter
    private List<Point> allPoints;
    @Getter
    private List<Point> pointsFromDB;
    @Getter
    private DataSource ds;

    {
        try {
            Context context = new InitialContext();
            ds = (DataSource) context.lookup("java:jboss/datasources/postgresqlDS");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        allPoints = new ArrayList<Point>();

    }

    public void addPoint(double x, double y, double r) throws SQLException {
        try {
            Point newPoint = new Point();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
            newPoint.setQueryTime(dateFormat.format(new Date(System.currentTimeMillis())));
            newPoint.setX(x);
            newPoint.setY(y);
            newPoint.setR(r);
            newPoint.setInArea(InAreaChecker.getResult(x, y, r));
            allPoints.add(newPoint);

            if (ds == null) {
                throw new SQLException("No data source");
            }
            Connection conn = ds.getConnection();
            if (conn == null) {
                throw new SQLException("No connection");
            }
            try {
                conn.setAutoCommit(false);
                boolean committed = false;
                try {
                    PreparedStatement newpoint = conn.prepareStatement("INSERT INTO POINTS VALUES (?,?,?,?,?)");
                    newpoint.setDouble(1, newPoint.getX());
                    newpoint.setDouble(2, newPoint.getY());
                    newpoint.setDouble(3, newPoint.getR());
                    newpoint.setBoolean(4, newPoint.isInArea());
                    newpoint.setString(5, newPoint.getQueryTime());
                    newpoint.executeUpdate();
                    conn.commit();
                    committed = true;
                } finally {
                    if (!committed) {
                        conn.rollback();
                    }
                }
            } finally {
                conn.close();

                //We will do database later)
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public List<Point> getPointsFromDB() throws SQLException {
        System.out.println("gettingAllPoints----------");
        pointsFromDB = new ArrayList<>();
        Connection conn = ds.getConnection();
        if (conn == null) {
            throw new SQLException("No connection");
        }

        try {
            conn.setAutoCommit(false);
            boolean committed = false;
            try {
                PreparedStatement newpoint = conn.prepareStatement("SELECT * FROM POINTS");
                ResultSet result2 = newpoint.executeQuery();
                while (result2.next()) {
                    pointsFromDB.add(new Point(result2.getDouble(1), result2.getDouble(2), result2.getDouble(3), result2.getBoolean(4), result2.getString(5)

                    ));
                }
                conn.commit();
                committed = true;
            } finally {
                if (!committed) {
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return pointsFromDB;
    }

    public void clearTable() throws SQLException {

        Connection conn = ds.getConnection();
        if (conn == null) {
            throw new SQLException("No connection");
        }

        try {
            conn.setAutoCommit(false);
            boolean committed = false;
            try {
                PreparedStatement newpoint = conn.prepareStatement("TRUNCATE TABLE POINTS");
                newpoint.executeUpdate();
                conn.commit();
                committed = true;
            } finally {
                if (!committed) {
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        allPoints.clear();
    }

    //I don't understand why here return 'main'

    public String addPointFromHiddenForm() throws SQLException {
        if (validateR(hiddenR)) {
            addPoint(hiddenX, hiddenY, hiddenR);
        }
        return "main";
    }

    public void addPointFromFields() throws SQLException {
        if (validateR(r) && validateXAndY()) {
            addPoint(x, y, r);
        }
    }

    public void toggle(ActionEvent event) {
        UIComponent component = event.getComponent();
        String value = (String) component.getAttributes().get("value");
        x = Double.parseDouble(value.replace(',', '.'));
    }

    public boolean validateXAndY() {
        boolean xIsCorrect = (x == -3 || x == -2 || x == -1 || x == 0 || x == 1 || x == 2 || x == 3 || x == 4 || x == 5);
        boolean yIsCorrect = y >= -5 && y <= 5;

        return xIsCorrect && yIsCorrect;
    }

    public boolean validateR(double r) {

        return r == 1 || r == 1.5 || r == 2 || r == 2.5 || r == 3;
    }
}
