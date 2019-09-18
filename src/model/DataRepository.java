package model;



import model.models.Expense;
import model.models.Income;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataRepository {

    private List<Income> incomesList;
    private List<Expense> expensesList;

    public DataRepository(List<Income> incomesList, List<Expense> expensesList) {
        this.incomesList = incomesList;
        this.expensesList = expensesList;

        sortIncomes();
        sortExpenses();
    }

    public List<Income> getIncomesList() {
        return incomesList;
    }

    public List<Expense> getExpensesList() {
        return expensesList;
    }

    public void sortIncomes(){ //sortuje przychody po dacie na liście
        Collections.sort(incomesList, new Comparator<Income>() {
            @Override
            public int compare(Income income1, Income income2) {
                if (income1.getDate().isAfter(income2.getDate())) {
                    return 1;
                } else {
                    return -1; //jak bedzie ta sama data to tylko zamieni je miejscami
                }
            }
        });
    }

    public void sortExpenses(){ //sortuje wydatki po dacie na liście
        Collections.sort(expensesList, new Comparator<Expense>() {
            @Override
            public int compare(Expense expense1, Expense expense2) {
                if (expense1.getDate().isAfter(expense2.getDate())) {
                    return 1;
                } else {
                    return -1; //j/w
                }
            }
        });
    }
}
