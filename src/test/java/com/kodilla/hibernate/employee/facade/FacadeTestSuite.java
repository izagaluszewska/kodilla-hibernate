package com.kodilla.hibernate.employee.facade;

import com.kodilla.hibernate.employee.Company;
import com.kodilla.hibernate.employee.Employee;
import com.kodilla.hibernate.employee.dao.CompanyDao;
import com.kodilla.hibernate.employee.dao.EmployeeDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacadeTestSuite {
    @Autowired
    Facade facade;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    EmployeeDao employeeDao;

    Employee johnSmith = new Employee("John", "Smith");
    Employee stephanieClarkson = new Employee("Stephanie", "Clarkson");
    Employee lindaKovalsky = new Employee("Linda", "Kovalsky");

    Company softwareMachine = new Company("Software Machine");
    Company dataMaesters = new Company("Data Maesters");
    Company greyMatter = new Company("Grey Matter");

    @Before
    public void beforeTest() {
        softwareMachine.getEmployees().add(johnSmith);
        dataMaesters.getEmployees().add(stephanieClarkson);
        dataMaesters.getEmployees().add(lindaKovalsky);
        greyMatter.getEmployees().add(johnSmith);
        greyMatter.getEmployees().add(lindaKovalsky);

        johnSmith.getCompanies().add(softwareMachine);
        johnSmith.getCompanies().add(greyMatter);
        stephanieClarkson.getCompanies().add(dataMaesters);
        lindaKovalsky.getCompanies().add(dataMaesters);
        lindaKovalsky.getCompanies().add(greyMatter);

        companyDao.save(softwareMachine);
        companyDao.save(dataMaesters);
        companyDao.save(greyMatter);
    }

    @Test
    public void testSearchEmployeeByLastnameWithSubstring() throws SearchingException {
        //Given
        //When
        List<Employee> foundedEmployees = facade.searchEmployeeByLastnameWithSubstring("mit");
        int foundedEmployeeId = foundedEmployees.iterator().next().getId();
        Employee readFoundedEmployees = employeeDao.findOne(foundedEmployeeId);
        //Then
        try {
            Assert.assertEquals(1, foundedEmployees.size());
            Assert.assertEquals(foundedEmployeeId, readFoundedEmployees.getId());
            System.out.println("Founded employee/employees lastname: " + readFoundedEmployees.getLastname());
        } catch (Exception e) {
        } finally {
            //CleanUp
            employeeDao.delete(foundedEmployeeId);
        }
    }

    @Test
    public void testSearchCompanyBySubstring() throws SearchingException {
        //Given
        //When
        List<Company> foundedCompanies = facade.searchCompanyBySubstring("ach");
        int foundedCompanyId = foundedCompanies.iterator().next().getId();
        Company readFoundedCompanies = companyDao.findOne(foundedCompanyId);
        //Then
        try {
            Assert.assertEquals(1, foundedCompanies.size());
            Assert.assertEquals(foundedCompanyId, readFoundedCompanies.getId());
            System.out.println("Founded company/companies: " + readFoundedCompanies.getName());
        } catch (Exception e) {
        } finally {
            //CleanUp
            companyDao.delete(foundedCompanyId);
        }
    }
}
