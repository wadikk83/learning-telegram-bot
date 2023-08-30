package by.wadikk.telegrambot.repository;

import by.wadikk.telegrambot.entity.RussianTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RussianTaskRepository extends JpaRepository<RussianTask, Long> {

    @Query(nativeQuery = true,
            value = "select * from russian_task where id= (select floor((select max(id) from russian_task) * random()))")
    RussianTask getRandomTask();
}
