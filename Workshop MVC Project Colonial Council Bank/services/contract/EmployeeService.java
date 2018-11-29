package app.ccb.services.contract;

import java.io.IOException;

public interface EmployeeService {

    Boolean employeesAreImported();

    String readEmployeesJsonFile() throws IOException;

    String importEmployees(String employees);

    String exportTopEmployees();
}
