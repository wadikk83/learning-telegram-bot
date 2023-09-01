package by.wadikk.telegrambot.repository;

import by.wadikk.telegrambot.entity.MathTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MathTaskRepository extends JpaRepository<MathTask, Long> {

    @Query(nativeQuery = true,
            value = "select * from math_task where id= (select floor((select max(id) from math_task) * random()))")
    MathTask getRandomTask();
}
