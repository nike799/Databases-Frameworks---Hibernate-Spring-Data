package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount extends BillingDetail {
    private String bankName;
    private String swiftCode;

    public BankAccount() {
    }

    public BankAccount(User user, String number, String bankName, String swiftCode) {
        super(user, number);
        this.bankName = bankName;
        this.swiftCode = swiftCode;
    }

    @Column(name = "bank_name")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "swift_code")
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
