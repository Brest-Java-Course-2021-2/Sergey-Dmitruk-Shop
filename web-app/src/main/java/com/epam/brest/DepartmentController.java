package com.epam.brest;


import com.epam.brest.service.DepartmentDTOService;
import com.epam.brest.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class DepartmentController {
    private DepartmentService departmentService;

    private DepartmentDTOService departmentDTOService;



    public DepartmentController(DepartmentDTOService departmentDTOService,DepartmentService departmentService) {
        this.departmentDTOService = departmentDTOService;
        this.departmentService=departmentService;
    }

    @GetMapping(value = "/departments")
    public String departments( Model model){
    model.addAttribute("departments", departmentDTOService.findAllDepartments());

    return "departments";
}

    @GetMapping(value = "/department")
    public final String gotoAddDepartmentPage(Model model) {
        model.addAttribute("isNew", true);
        model.addAttribute("department", new Department());
        return "department";
    }
    @PostMapping(value = "/department")
    public String addDepartment(Department department) {
        this.departmentService.create(department);
        return "redirect:/departments";
    }
    @GetMapping(value = "/department/{id}")
    public String editDepartment(@PathVariable Integer id, Model model){
        model.addAttribute("isNew", false);
        model.addAttribute("department", departmentService.getDepartmentById(id));
return "department";
    }
    @PostMapping(value = "/department/{id}")
    public String updateDepartment(Department department){
        this.departmentService.update(department);
        return "redirect:/departments";
    }
    @GetMapping(value = "/department/{id}/delete")
    public String deleteDepartment(@PathVariable Integer id, Model model){

        departmentService.delete(id);
        return "redirect:/departments";
    }



}
