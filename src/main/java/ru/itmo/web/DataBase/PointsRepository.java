package ru.itmo.web.DataBase;

import ru.itmo.web.model.Point;

import java.util.List;

public interface PointsRepository {
    void addPoint(Point point);
    void removePoint(Point point);
    void updatePoint(Point point);

    List<Point> getAllPoints();
}
