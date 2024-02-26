package cpt276.demo.controllers;

// import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.DeleteMapping;

import cpt276.demo.models.StudentRepository;
import cpt276.demo.models.Students;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class StudentsController {


    @Autowired
    private StudentRepository studentRepo;


    @GetMapping("/students/view")
    public String getAllUsers(Model model) {
        System.out.println("Getting all users");
        

        List<Students> students = studentRepo.findAll();

        model.addAttribute("us", students);
        return "students/showAll";
    }
    

    

    @PostMapping("/students/add")
    public String addUser(@RequestParam Map<String, String> newstudent, HttpServletResponse response) {
        System.out.println("ADD student");
        String newName = newstudent.get("name");
        String newColor = newstudent.get("hairColor");
        int newWeight = Integer.parseInt(newstudent.get("weight"));
        int newHeight = Integer.parseInt(newstudent.get("height"));
        double newGpa = Double.parseDouble(newstudent.get("gpa"));

        studentRepo.save(new Students(newName, newColor, newGpa, newWeight, newHeight));
        response.setStatus(201);
        return "redirect:/students/view";

    }

    @GetMapping("/students/display")
    public String viewAllStudents(Model model) {
        model.addAttribute("us", studentRepo.findAll());
        return "students/displayStudents"; 
    }
    
    @Transactional
    @PostMapping("/students/delete")
    public String deleteStudentByName(@RequestParam("name") String name) {
        studentRepo.deleteByName(name);
        return "redirect:/students/view";
    }


    @GetMapping("/students/update")
    public String showUpdateForm() {
        return "students/update";
    }

    @PostMapping("/students/update")
    public String updateStudentAttributes(@RequestParam String name,
                                          @RequestParam int weight,
                                          @RequestParam int height,
                                          @RequestParam double gpa,
                                          @RequestParam String hairColor,
                                          Model model) {
    
        List<Students> students = studentRepo.findByName(name);
        
        if (students.isEmpty()) {
            model.addAttribute("error", "Student with name " + name + " not found.");
            return "redirect:/students/update"; 
        }
        Students student = students.get(0);
        student.setWeight(weight);
        student.setHeight(height);
        student.setGpa(gpa);
        student.setHairColor(hairColor);
        studentRepo.save(student);
        return "redirect:/students/view";
    }
}
