package productsshop.domain.dto;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class UsersAndProductsDto {
    @Expose
    private Integer usersCount;
    @Expose
    private List<UserWithNameAndAgeDto> users;

    public UsersAndProductsDto() {
        this.users = new ArrayList<>();
    }
    public UsersAndProductsDto(List<UserWithNameAndAgeDto> users) {
        this.users = users;
        this.usersCount = this.users.size();
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount() {
        this.usersCount = this.users.size();
    }

    public List<UserWithNameAndAgeDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithNameAndAgeDto> users) {
        this.users = users;
    }
}
