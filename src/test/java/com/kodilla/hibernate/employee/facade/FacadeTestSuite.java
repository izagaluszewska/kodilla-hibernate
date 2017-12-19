package com.kodilla.hibernate.employee.facade;

import com.kodilla.hibernate.employee.Company;
import com.kodilla.hibernate.employee.Employee;
import com.kodilla.hibernate.employee.dao.CompanyDao;
import com.kodilla.hibernate.employee.dao.EmployeeDao;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacadeTestSuite {
    @Autowired
    SearchingServiceFacade facade;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    EmployeeDao employeeDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @After
    public void afterTest() {
        companyDao.delete(softwareMachine);
        companyDao.delete(dataMaesters);
        companyDao.delete(greyMatter);
    }

    @Test
    public void testSearchEmployeeByLastnameWithSubstring() throws SearchingException {
        //Given
        //When
        List<Employee> foundedEmployees = facade.searchEmployeeByLastnameWithSubstring("%mit%");
        int foundedEmployeeId = foundedEmployees.iterator().next().getId();
        Employee readFoundedEmployees = employeeDao.findOne(foundedEmployeeId);
        //Then
        Assert.assertEquals(1, foundedEmployees.size());
        Assert.assertEquals(foundedEmployeeId, readFoundedEmployees.getId());
        System.out.println("Founded employee/employees lastname: " + readFoundedEmployees.getLastname());
    }

    @Test
    public void testSearchNoExistingEmployeeByLastnameWithSubstring() throws Exception {
        //Given
        thrown.expect(SearchingException.class);
        //When
        List<Employee> emptyEmployeeList = facade.searchEmployeeByLastnameWithSubstring("%xyz%");
        //Then
        Assert.fail();
        Assert.assertEquals(0, emptyEmployeeList.size());
    }

    @Test
    public void testSearchCompanyBySubstring() throws SearchingException {
        //Given
        //When
        List<Company> foundedCompanies = facade.searchCompanyBySubstring("%ach%");
        int foundedCompanyId = foundedCompanies.iterator().next().getId();
        Company readFoundedCompanies = companyDao.findOne(foundedCompanyId);
        //Then
        Assert.assertEquals(1, foundedCompanies.size());
        Assert.assertEquals(foundedCompanyId, readFoundedCompanies.getId());
        System.out.println("Founded company/companies: " + readFoundedCompanies.getName());
    }

    @Test
    public void testSearchNoExistingCompanyBySubstring() throws Exception {
        //Given
        thrown.expect(SearchingException.class);
        //When
        List<Company> emptyCompanyList = facade.searchCompanyBySubstring("%xyz%");
        //Then
        Assert.fail();
        Assert.assertEquals(0, emptyCompanyList);
    }
}
