package ru.itmo.web.beans;

import javax.faces.bean.ManagedBean;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.web.InAreaChecker.InAreaChecker;
import ru.itmo.web.model.Point;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

//@Named("pointsBean") //Maybe we can use this. ManageBean is not deprecated.
@Data
@ManagedBean(name = "pointsBean")
@ApplicationScoped
public class PointsBean implements Serializable {

    //Here x,y,r is for creating points from form
    private double x;// -3 -2 -1 0 1 2 3 4 5

    private double y;// [-5,5]

    private double r;// 1 1.5 2 2.5 3

    //Hidden part is for hidden form
    private double hidden_x,hidden_y,hidden_r;

    private List<Point> allPoints;

    public void init() {
/*        FacesContext facesContext = FacesContext.getCurrentInstance();
        dbStorage = facesContext.getApplication()
                .evaluateExpressionGet(facesContext, "#{dao}", DBStorage.class);*/
//        allPoints = dbStorage.getAllPoints();

    }

    public void addPoint(double x,double y,double r) {
        Point newPoint = new Point();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        newPoint.setQueryTime(dateFormat
                .format(new Date(System.currentTimeMillis())));
        newPoint.setX(x);
        newPoint.setR(r);
        newPoint.setY(y);
        newPoint.setInArea(InAreaChecker.getResult(x, y, r));
        allPoints.add(newPoint);

        //We will do database later)
//        dbStorage.addPoint(newPoint);
    }

    public void setX1(double x){
        System.out.println("1234567890");
        setX(x);
    }

    public void clearTable() {
//        for (Point p : dbStorage.getAllPoints()) {
//            dbStorage.removePoint(p);
//        }
        allPoints.clear();
    }

    //I don't understand why here return 'main'
    public String addPointFromHiddenForm(){
        addPoint(hidden_x,hidden_y,hidden_r);
        return "main";
    }

    public void addPointFromFields(){
        addPoint(x,y,r);
    }
}
