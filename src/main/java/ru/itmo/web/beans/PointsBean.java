package ru.itmo.web.beans;

import javax.faces.bean.ManagedBean;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.web.model.Point;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "pointsBean")
@Named("pointsBean") //Maybe we can use this. ManageBean is not deprecated.
@Getter
@Setter
@ApplicationScoped
public class PointsBean implements Serializable {

    //Here x,y,r is for creating points
    private double x;// -3 -2 -1 0 1 2 3 4 5

    private double y;// [-5,5]

    private double r;// 1 1.5 2 2.5 3

    private List<Point> allPoints;

}
