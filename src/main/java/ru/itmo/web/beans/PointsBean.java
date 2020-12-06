package ru.itmo.web.beans;

import javax.faces.bean.ManagedBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.web.DataBase.DataBaseStorage;
import ru.itmo.web.InAreaChecker.InAreaChecker;
import ru.itmo.web.model.Point;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedProperty;
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
@ManagedBean(name = "pointsBean")
@ApplicationScoped
@Setter
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
    private DataSource ds;

    @ManagedProperty("#{dao}")
    private DataBaseStorage dbStorage;

    public void init() {
/*        FacesContext facesContext = FacesContext.getCurrentInstance();
        dbStorage = facesContext.getApplication()
                .evaluateExpressionGet(facesContext, "#{dao}", DBStorage.class);*/
        allPoints = dbStorage.getAllPoints();
    }

    public void clearTable() {
        for (Point p : dbStorage.getAllPoints()) {
            dbStorage.removePoint(p);
        }
        allPoints.clear();
    }

    public void addPoint(double x, double y, double r) {
        Point currentPoint = new Point();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        currentPoint.setQueryTime(dateFormat
                .format(new Date(System.currentTimeMillis())));
        currentPoint.setX(x);
        currentPoint.setR(r);
        currentPoint.setY(y);
        currentPoint.setInArea(InAreaChecker.getResult(x, y, r));
        allPoints.add(currentPoint);
        dbStorage.addPoint(currentPoint);
    }

//    public String addPointsFromField() {
//        if (validateR(r) && validateXAndY()) {
//            addPoint(x, y, r);
//        }
//        return "main";
//    }


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
