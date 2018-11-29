package app.ccb.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class BranchImportJsonDto {
    @Expose
    @NotNull
    private String name;

    public BranchImportJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
