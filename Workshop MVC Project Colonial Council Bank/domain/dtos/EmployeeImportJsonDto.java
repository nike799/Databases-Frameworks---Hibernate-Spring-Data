package app.ccb.domain.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeImportJsonDto {
    @Expose
    @SerializedName("full_name")
    private String name;
    @Expose
    private String salary;
    @Expose
    @SerializedName("started_on")
    private String startedOn;
    @Expose
    @SerializedName("branch_name")
    private String branchName;

    public EmployeeImportJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
