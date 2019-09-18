package model.models;

import model.enums.ExpenseCategory;
import model.enums.FamilyMembers;

import java.time.LocalDateTime;

public class Expense {
    private LocalDateTime expenseDate;
    private FamilyMembers byWho;
    private ExpenseCategory expenseCategory;
    private String expenseName;
    private double amount;

    public Expense(LocalDateTime expenseDate, FamilyMembers byWho, ExpenseCategory expenseCategory, String expenseName, double amount) {
        this.expenseDate = expenseDate;
        this.byWho = byWho;
        this.expenseCategory = expenseCategory;
        this.expenseName = expenseName;
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return expenseDate;
    }

    @Override
    public String toString() {
        return "Expense {" + expenseDate +
                ", " + byWho +
                ", " + expenseCategory +
                ", '" + expenseName +
                ", " + amount +
                '}';
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public double getAmount() {
        return amount;
    }

    public FamilyMembers getByWho() {
        return byWho;
    }
}
