package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.domain.Department;

import java.util.List;

/**
 * Created by Oana.Mihai on 7/15/2016.
 */
public interface DepartmentService {
    List<Department> findAll();

    Department findOneDepartment(Long departmentId);

    Department insertDepartment(Department department);

    Department updateDepartment(Department department);

    void deleteDepartment(Department department);
}
