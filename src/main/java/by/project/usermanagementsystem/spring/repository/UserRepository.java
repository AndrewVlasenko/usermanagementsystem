package by.project.usermanagementsystem.spring.repository;

import by.project.usermanagementsystem.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<Client, Long> {

    @Modifying
    @Query("update Client c set c.role = :text where c.id = :id")
    void changeRole(@Param("text") String text, @Param("id") Long id);

    @Modifying
    @Query("update Client c set c.lastEntrance = :date where c.name = :name")
    void updateLastEntrance(@Param("date") Date date, @Param("name") String name);

    Client findByName(String name);
}
