package ru.itmo.web.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Tags;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.*;

@Entity
@Table(name = "points")
@NoArgsConstructor
@Data
@ToString
class PointsBeanTest {

    @Id
    @SequenceGenerator( name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    int id;

    double x;

    double y;

    double r;

    double completion_time;

    boolean result;
}