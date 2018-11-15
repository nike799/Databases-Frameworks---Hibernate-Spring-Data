package app;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wizzard_deposits")
public class WizzardDeposit {
    private Integer id;
    private String firstName;
    private String lastName;
    private String notes;
    private Integer age;
    private String magicWandCreator;
    private Short magicWandSize;
    private String depositGroup;
    private LocalDateTime depositStartDate;
    private BigDecimal depositAmount;
    private BigDecimal depositInterest;
    private BigDecimal depositCharge;
    private LocalDateTime depositExpirationDate;
    private Boolean isDepositExpired;

    public WizzardDeposit() {
    }

    public WizzardDeposit(String firstName, String lastName, String notes,
                          Integer age, String magicWandCreator, Short magicWandSize,
                          String depositGroup, LocalDateTime depositStartDate,
                          BigDecimal depositAmount, BigDecimal depositInterest,
                          BigDecimal depositCharge, LocalDateTime depositExpirationDate,
                          Boolean isDepositExpired) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setNotes(notes);
        this.setAge(age);
        this.setMagicWandCreator(magicWandCreator);
        this.setMagicWandSize(magicWandSize);
        this.setDepositGroup(depositGroup);
        this.setDepositStartDate(depositStartDate);
        this.setDepositAmount(depositAmount);
        this.setDepositInterest(depositInterest);
        this.setDepositCharge(depositCharge);
        this.setDepositExpirationDate(depositExpirationDate);
        this.setIsDepositExpired(isDepositExpired);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    @Column(name = "last_name", length = 60, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    @Column(name = "notes", length = 1000)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "age", nullable = false)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be positive number!");
        }
        this.age = age;
    }

    @Column(name = "magic_wand_creator", length = 1000)
    public String getMagicWandCreator() {
        return magicWandCreator;
    }

    public void setMagicWandCreator(String magic_wand_creator) {
        this.magicWandCreator = magic_wand_creator;
    }

    @Column(name = "magic_wand_size")
    public Short getMagicWandSize() {
        return magicWandSize;
    }

    public void setMagicWandSize(Short magic_wand_size) {
        this.magicWandSize = magic_wand_size;
    }

    @Column(name = "deposit_group", length = 20)
    public String getDepositGroup() {
        return depositGroup;
    }

    public void setDepositGroup(String deposit_group) {
        this.depositGroup = deposit_group;
    }

    @Column(name = "deposit_start_date")
    public LocalDateTime getDepositStartDate() {
        return depositStartDate;
    }

    public void setDepositStartDate(LocalDateTime deposit_start_date) {
        this.depositStartDate = deposit_start_date;
    }

    @Column(name = "deposit_amount")
    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal deposit_amount) {
        this.depositAmount = deposit_amount;
    }

    @Column(name = "deposit_interest")
    public BigDecimal getDepositInterest() {
        return depositInterest;
    }

    public void setDepositInterest(BigDecimal deposit_interest) {
        this.depositInterest = deposit_interest;
    }

    @Column(name = "deposit_charge")
    public BigDecimal getDepositCharge() {
        return depositCharge;
    }

    public void setDepositCharge(BigDecimal deposit_charge) {
        this.depositCharge = deposit_charge;
    }

    @Column(name = "deposit_expiration_date")
    public LocalDateTime getDepositExpirationDate() {
        return depositExpirationDate;
    }

    public void setDepositExpirationDate(LocalDateTime deposit_expiration_date) {
        this.depositExpirationDate = deposit_expiration_date;
    }

    @Column(name = "is_deposit_expired")
    public Boolean getIsDepositExpired() {
        return isDepositExpired;
    }

    public void setIsDepositExpired(Boolean is_deposit_expired) {
        this.isDepositExpired = is_deposit_expired;
    }
}
