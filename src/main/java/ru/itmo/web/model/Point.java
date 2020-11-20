package ru.itmo.web.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Point {
    public Point(Double x, Double y, Double r, boolean inArea, String queryTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inArea = inArea;
        this.queryTime = queryTime;
    }

    // All of these yellow thing is for Database

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private Double r;

    @Column(nullable = false)
    private boolean inArea;

    @Column(nullable = false)
    private String queryTime;
}
