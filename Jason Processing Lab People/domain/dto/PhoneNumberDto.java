package app.domain.dto;

import java.io.Serializable;

public class PhoneNumberDto implements Serializable {
    private String number;

    public PhoneNumberDto() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
