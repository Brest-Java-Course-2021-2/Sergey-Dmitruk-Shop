package com.epam.brest;


import com.epam.brest.service.DepartmentDTOService;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.validators.DepartmentValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class DepartmentController {

    private final DepartmentService departmentService;

    private final DepartmentDTOService departmentDTOService;

    private final DepartmentValidator validator;


    public DepartmentController(DepartmentDTOService departmentDTOService, DepartmentService departmentService, DepartmentValidator validator) {
        this.departmentDTOService = departmentDTOService;
        this.departmentService = departmentService;
        this.validator = validator;
    }

    @GetMapping(value = "/departments")
    public String departments(Model model) {
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
    public String addDepartment(Department department, BindingResult bindingResult) {

        validator.validate(department, bindingResult);

        if (bindingResult.hasErrors())
            return "department";

        this.departmentService.create(department);
        return "redirect:/departments";
    }

    @GetMapping(value = "/department/{id}")
    public String editDepartment(@PathVariable Integer id, Model model) {
        model.addAttribute("isNew", false);
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "department";
    }

    @PostMapping(value = "/department/{id}")
    public String updateDepartment(Department department, BindingResult bindingResult) {

        validator.validate(department, bindingResult);

        if (bindingResult.hasErrors())
            return "department";

        this.departmentService.update(department);
        return "redirect:/departments";
    }

    @GetMapping(value = "/department/{id}/delete")
    public String deleteDepartment(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Integer result = departmentService.delete(id);

        if (result == 0) {
            redirectAttributes.addFlashAttribute("errorDelete", true);
        }

        return "redirect:/departments";
    }


}
