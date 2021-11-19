package com.epam.brest;


import com.epam.brest.service.DepartmentDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DepartmentController {
    private DepartmentDTOService departmentDTOService;


    public DepartmentController(DepartmentDTOService departmentDTOService) {
        this.departmentDTOService = departmentDTOService;
    }

    @GetMapping(value = "/departments")
    public String departments( Model model){
    model.addAttribute("departments", departmentDTOService.findAllDepartments());

    return "departments";
}


}
