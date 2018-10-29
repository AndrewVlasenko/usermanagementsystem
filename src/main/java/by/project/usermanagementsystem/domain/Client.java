package by.project.usermanagementsystem.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(catalog = "user_db")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
public class Client{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    private String password;

    private byte enabled;

    @Column(name = "date_сreation")
    private Date dateCreation;

    @Column(name = "last_entrance")
    private Date lastEntrance;

    private String role;

    @Override
    public String toString() {
        return name;
    }
}
