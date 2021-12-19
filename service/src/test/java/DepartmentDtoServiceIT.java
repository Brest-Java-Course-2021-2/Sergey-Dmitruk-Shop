import com.epam.brest.dto.DepartmentDTO;
import com.epam.brest.service.DepartmentDTOService;
import com.epam.brest.service.config.ServiceTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfiguration.class})
@PropertySource({"classpath:sql-dao.properties"})
@Transactional
public class DepartmentDtoServiceIT {

    @Autowired
    DepartmentDTOService departmentDtoService;

    @Test
    public void shouldFindAllDepartments() {
        List<DepartmentDTO> departments = departmentDtoService.findAllDepartments();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);

    }

}
