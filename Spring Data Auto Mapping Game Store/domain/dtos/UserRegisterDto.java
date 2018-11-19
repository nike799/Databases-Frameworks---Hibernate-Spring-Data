package gamestore.domain.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegisterDto {
    private String fullName;
    private String email;
    private String password;
    private String confirmedPassword;
    private boolean isAdmin;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String fullName, String email, String password,
                           String confirmedPassword, boolean isAdmin) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.isAdmin = isAdmin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9.]+[a-zA-Z0-9]@[a-zA-Z][a-zA-Z0-9.]+[a-zA-Z].[a-zA-Z][a-zA-Z]+$",
            message = "Invalid email.")
    @NotNull(message = "Email cannot be null.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = "Password cannot be null.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$", message = "Invalid password.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
