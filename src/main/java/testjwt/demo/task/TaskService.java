/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package testjwt.demo.task;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import testjwt.demo.user.User;
import testjwt.demo.user.UserRepository;

/**
 *
 * @author macbookair
 */
@Service
@RequiredArgsConstructor
public class TaskService {

        final private TaskRepository taskRepository;

        final private UserRepository userRepository;

        private User getUser() {
                // Get authenticated user's email
                String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                                .getUsername();

                // Find user by email
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                return user;
        }

        private Task getTask(Integer id, User user) {
                // Find task by id
                Task task = taskRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

                // permissions check
                if (!task.getUser().getId().equals(user.getId())) {
                        throw new RuntimeException("You are not authorized to view this task");
                }

                return task;
        }

        public TaskDTO create(Task request) {
                User user = getUser();

                // Create Task using builder
                Task newTask = Task.builder()
                                .user(user)
                                .title(request.getTitle())
                                .description(request.getDescription())
                                .status(request.getStatus() != null ? request.getStatus() : Status.TODO)
                                .build();

                // Save task
                taskRepository.save(newTask);

                // Create a return object
                return TaskDTO.builder()
                                .id(newTask.getId())
                                .title(newTask.getTitle())
                                .description(newTask.getDescription())
                                .status(newTask.getStatus())
                                .user_id(newTask.getUser().getId())
                                .user_name(newTask.getUser().getFirst_name() + " " + newTask.getUser().getLast_name())
                                .build();
        }

        public TaskDTO showOne(Integer id) {
                User user = getUser();

                Task task = getTask(id, user);

                return TaskDTO.builder()
                                .id(task.getId())
                                .title(task.getTitle())
                                .description(task.getDescription())
                                .status(task.getStatus())
                                .user_id(task.getUser().getId())
                                .user_name(task.getUser().getFirst_name() + " " + task.getUser().getLast_name())
                                .build();
        }

        public TaskDTO put(Integer id, Task request) {
                User user = getUser();

                Task task = getTask(id, user);

                Task updatedTask = Task.builder()
                                .id(task.getId())
                                .user(task.getUser())
                                .title(request.getTitle())
                                .description(request.getDescription())
                                .status(request.getStatus() != null ? request.getStatus() : Status.TODO)
                                .build();

                // Save task
                taskRepository.save(updatedTask);

                return TaskDTO.builder()
                                .id(updatedTask.getId())
                                .title(updatedTask.getTitle())
                                .description(updatedTask.getDescription())
                                .status(updatedTask.getStatus())
                                .user_id(updatedTask.getUser().getId())
                                .user_name(updatedTask.getUser().getFirst_name() + " "
                                                + updatedTask.getUser().getLast_name())
                                .build();
        }

        public TaskDTO patch(Integer id, Map<String, Object> updates) {
                // Get authenticated user
                User user = getUser();

                // Get task with permissions check
                Task task = getTask(id, user);

                // Apply partial updates
                if (updates.containsKey("title")) {
                        String title = (String) updates.get("title");

                        if (title == null || title.trim().isEmpty()) {
                                throw new IllegalArgumentException("Title cannot be empty");
                        }
                        
                        task.setTitle(title);
                }

                if (updates.containsKey("description")) {
                        task.setDescription((String) updates.get("description")); // Allow null/empty
                }

                if (updates.containsKey("status")) {
                        try {
                                String statusStr = (String) updates.get("status");
                                if (statusStr == null) {
                                        throw new IllegalArgumentException("Status cannot be null");
                                }
                                task.setStatus(Status.valueOf(statusStr));
                        } catch (IllegalArgumentException e) {
                                throw new IllegalArgumentException("Invalid status value: " + updates.get("status"));
                        }
                }

                // Save updated task
                taskRepository.save(task);

                // Build and return TaskDTO with null-safe user_name
                String fullName = (task.getUser().getFirst_name() != null ? task.getUser().getFirst_name() : "") + " " +
                                (task.getUser().getLast_name() != null ? task.getUser().getLast_name() : "");
                return TaskDTO.builder()
                                .id(task.getId())
                                .title(task.getTitle())
                                .description(task.getDescription())
                                .status(task.getStatus())
                                .user_id(task.getUser().getId())
                                .user_name(fullName.trim())
                                .build();
        }

        public void delete(Integer id) {
                User user = getUser();

                Task task = getTask(id, user);

                taskRepository.delete(task);
        }
}
