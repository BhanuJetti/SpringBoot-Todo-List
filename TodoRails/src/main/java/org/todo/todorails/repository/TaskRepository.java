package org.todo.todorails.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.todo.todorails.model.Task;
import org.todo.todorails.model.User;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserAndDueDate(User user, LocalDate dueDate);

    List<Task> findByUserAndDueDateAndCompleted(User user,LocalDate dueDate,boolean completed);

    Task findByUserAndId(User user,Long Id);

    List<Task> findByUser(User user);

    Task getById(Long Id);

    int countByCompletedAndUserId(boolean completed, Long id);
}
