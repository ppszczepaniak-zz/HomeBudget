package model.models;

import model.enums.FamilyMembers;
import model.enums.IncomeCategory;

import java.time.LocalDateTime;

public class Income {
    private LocalDateTime incomeDate;
    private FamilyMembers byWho;
    private IncomeCategory incomeCategory;
    private double amount;

    public Income(LocalDateTime incomeDate, FamilyMembers byWho, IncomeCategory incomeCategory, double amount) {
        this.incomeDate = incomeDate;
        this.byWho = byWho;
        this.incomeCategory = incomeCategory;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Income {"  + incomeDate +", "+ byWho +
                ", " + incomeCategory +
                ", " + amount +
                '}';
    }

    public LocalDateTime getDate() {
        return incomeDate;
    }

    public double getAmount() {
        return amount;
    }
}
