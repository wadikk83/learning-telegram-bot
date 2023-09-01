package by.wadikk.telegrambot.repository;

import by.wadikk.telegrambot.entity.EnglishTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishTaskRepository extends JpaRepository<EnglishTask, Long> {

    @Query(nativeQuery = true,
            value = "select * from english_task where id= (select floor((select max(id) from english_task) * random()))")
    EnglishTask getRandomTask();
}
