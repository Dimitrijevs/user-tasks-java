/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package testjwt.demo.task;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author macbookair
 */
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody Task request) {
        return ResponseEntity.ok(taskService.create(request));
    }

    // show task
    @GetMapping("/show/{id}")
    public ResponseEntity<TaskDTO> show(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.showOne(id));
    }

    // update task - put
    @PutMapping("/update-put/{id}")
    public ResponseEntity<TaskDTO> put(@PathVariable Integer id, @Valid @RequestBody Task request) {
        return ResponseEntity.ok(taskService.put(id, request));
    }

    // update task - patch
    @PatchMapping("/update-patch/{id}")
    public ResponseEntity<TaskDTO> patch(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(taskService.patch(id, updates));
    }

    // delete task
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        taskService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
