package org.todo.todorails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.todo.todorails.model.Task;
import org.todo.todorails.service.TaskService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        List<Task> todayTasks = taskService.getTodayTasksForCurrentUser();
        model.addAttribute("todayTasks",todayTasks);
        List<Task> allTasks  = taskService.getAllTasksForCurrentUser();
        sortTasksByDueDate(allTasks);
        model.addAttribute("allTasks",allTasks);
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("completedCount",taskService.countByCompletedAndUserId(true));
        model.addAttribute("pendingCount",taskService.countByCompletedAndUserId(false));

        return "dashboard";
    }
    private void sortTasksByDueDate(List<Task> tasks){
        Collections.sort(tasks, new Comparator<Task>(){
            @Override
            public int compare(Task task1,Task task2){
                if(task1.getDueDate() == null && task2.getDueDate() == null) {
                    return 0;
                }
                if(task1.getDueDate() == null) {
                    return 1;
                }
                if(task2.getDueDate() == null) {
                    return -1;
                }
                return task1.getDueDate().compareTo(task2.getDueDate());
            }
        });
    }
    @GetMapping("/addtask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task());
        return "addtask";
    }
    @PostMapping("/addtask")
    public String saveTask(@ModelAttribute("task")Task task, Model model, RedirectAttributes redirectAttributes) {
        try {
            taskService.saveTask(task);
            redirectAttributes.addFlashAttribute("successMessage","Task Added Successfully!");
            return "redirect:/dashboard";
        }
        catch(Exception e){
            model.addAttribute("task",model);
            redirectAttributes.addFlashAttribute("errorMessage","Failed to add Task.please Try again later");
            return "redirect:/addtask";
        }

    }
    @PostMapping("/task/markdone")
    public String markTaskAsDone(@RequestParam("taskId")Long taskId,RedirectAttributes redirectAttributes){
        boolean isMarkDone = taskService.markTaskAsDone(taskId);
        if(isMarkDone){
            redirectAttributes.addFlashAttribute("successMessage","Task Marked as done!");
        }
        else{
            redirectAttributes.addFlashAttribute("errorMessage","Failed to mark task as done.You may not have permission");
        }
        return "redirect:/dashboard";
    }
    @PostMapping("task/viewtask")
    public String viewTask(@RequestParam("taskId")Long taskId,Model model){
        if(taskId == null){
            model.addAttribute("errorMessage","Invalid attempt to view task!");
        }
        Task task = taskService.getTaskByIdAny(taskId);
        if(task != null){
            model.addAttribute("task",task);
        }
        else{
            model.addAttribute("error","No task to display!");
        }
        return "viewtask";
    }
    @PostMapping("task/edittask")
    public String editTaskForm(@RequestParam("taskId")Long taskId,Model model){
        Task task = taskService.getTaskById(taskId);
        if(task != null){
            model.addAttribute("task",task);
        }
        else{
            model.addAttribute("error","No Task to edit");
        }
        return "edittask";
    }
    @PostMapping("/updatetask")
    public String updateTask(@ModelAttribute("task")Task task,RedirectAttributes redirectAttributes){
        try{
            taskService.updateTaskForUser(task);
            redirectAttributes.addFlashAttribute("successMessage","Task updated successfully!");
            return "redirect:/dashboard";
        }
        catch(Exception e){
            redirectAttributes.addFlashAttribute("errorMessage","Failed to update the task");
            return "redirect:/edittask";
        }
    }
    @PostMapping("/task/delete")
    public String deleteTask(@RequestParam("taskId")Long taskId,RedirectAttributes redirectAttributes){
        Task task = taskService.getTaskByIdAny(taskId);
        if(task != null){
            if(taskService.deleteTask(task)){
                redirectAttributes.addFlashAttribute("successMessage","Task deleted successfully!");
            }
            else{
                redirectAttributes.addFlashAttribute("errorMessage","Task cannot be deleted by the user");
            }
        }
        else{
            redirectAttributes.addFlashAttribute("errorMessage","Task not found!");
        }
        return "redirect:/dashboard";
    }

}


