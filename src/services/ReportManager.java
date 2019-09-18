package services;

import model.enums.ExpenseCategory;
import model.enums.FamilyMembers;
import model.models.Expense;
import model.models.Income;
import view.UserInterface;

import java.util.ArrayList;
import java.util.List;

import static model.enums.FamilyMembers.*;

public class ReportManager {
    private UserInterface userInterface;

    public ReportManager(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    //  suma wydatkow w poszcz miesiacach dla danej kategorii, a gdy nie ma wynikow to zero
    //  rok | miesiac |  kategoria|  kwota
    //  2019|        1|   jedzenie| 100.00
    //   2019 |1      | osobiste  | 0.00 itd.
    public String[] expensesPerCategory(List<Expense> expensesList, List<ExpenseCategory> categories, int yearNumber) {
        String year = String.valueOf(yearNumber);
        double sumForCategory;
        StringBuilder sb;
        String[] array = new String[(categories.size() * 12) + 1]; //12 mcy razy ilość kategorii + nagłówek
        array[0] = "|YEAR |MONTH |CATEGORY |  AMOUNT|";
        int j = 1;
        for (int i = 1; i <= 12; i++) { //dla kazdego miesiaca
            for (ExpenseCategory category : categories) { // dla kazdej kategorii
                sumForCategory = 0.0;
                for (Expense expense : expensesList) {
                    if (expense.getDate().getYear() == yearNumber && expense.getDate().getMonthValue() == i) { //sprawdza czy expense jest w danym miesiacu
                        if (expense.getExpenseCategory().equals(category)) {
                            sumForCategory += expense.getAmount(); //sumuje wydatki w tej kategorii
                        }
                    }
                }
                sb = new StringBuilder("|").append(String.format("%-5s", year)).append("|")
                        .append(String.format("%-6s", i)).append("|").append(String.format("%-9s", category))
                        .append("|").append(String.format("%8s", sumForCategory)).append("|");
                array[j] = sb.toString();
                j++;
            }
        }
        return array;
    }

    /*sume wydatkow dla danej osoby w poszcz. miesiacach wraz ze średnia kroczacą z ostatnich 2 miesiecy
    rok, mc, osoba, kwota, srednia  - vis. jw. z "|"
    2019 |    1 |     tata |  100.0   | 0.00
    2019 | 1   | mama       |1000.0   | 0.0*/
    public String[] expensesPerPerson(List<Expense> expensesList, int yearNumber) {
        String year = String.valueOf(yearNumber);
        double sumPerMonth;
        double oneMonthBackSum = 0.0;  //srednia nie uwzglednia poprzedniego roku
        double twoMonthBackSum = 0.0;
        StringBuilder sb;
        String[] array = new String[(12 * values().length) + 1]; //12 mcy razy * 2 osoby + nagłówek
        array[0] = "|YEAR |MONTH |PERSON |  AMOUNT|MOVING AVG|";
        int j = 1;
        for (FamilyMembers familyMember : values()) {
            oneMonthBackSum = 0.0;
            twoMonthBackSum = 0.0;
            for (int i = 1; i <= 12; i++) { //dla kazdego miesiaca
                sumPerMonth = 0.0;
                for (Expense expense : expensesList) {
                    if (expense.getDate().getYear() == yearNumber && expense.getByWho().equals(familyMember) && expense.getDate().getMonth().getValue() == i) { //sprawdza czy expense jest danej osoby i z danego miesiaca
                        sumPerMonth += expense.getAmount(); //sumuje wydatki w tej kategorii
                    }
                }
                sb = new StringBuilder("|").append(String.format("%-5s", year)).append("|")
                        .append(String.format("%-6s", i)).append("|").append(String.format("%-7s", familyMember))
                        .append("|").append(String.format("%8s", sumPerMonth)).append("|").append(String.format("%10.2f", movingAverage(oneMonthBackSum, twoMonthBackSum))).append("|");
                array[j] = sb.toString();
                j++;
                twoMonthBackSum = oneMonthBackSum;
                oneMonthBackSum = sumPerMonth;
            }
        }
        return array;
    }

    private double movingAverage(double oneMonthBack, double twoMonthsback) {
        return (oneMonthBack + twoMonthsback) / 2;
    }

    /* sume przychodow w poszcz. miesiacach ze śrenią kroczącą z ost. 2 miesięcy*/
    public String[] incomesSumPerMonth(List<Income> incomesList, int yearNumber) {
        String year = String.valueOf(yearNumber);
        double sumPerMonth;
        double oneMonthBackSum = 0.0;  //srednia nie uwzglednia poprzedniego roku
        double twoMonthBackSum = 0.0;
        StringBuilder sb;
        String[] array = new String[13]; //12 mcy razy + nagłówek
        array[0] = "|YEAR |MONTH |      SUM|MOVING AVG|";
        int j = 1;
        for (int i = 1; i <= 12; i++) { //dla kazdego miesiaca
            sumPerMonth = 0.0;
            for (Income income : incomesList) {
                if (income.getDate().getYear() == yearNumber && income.getDate().getMonth().getValue() == i) {
                    sumPerMonth += income.getAmount();
                }
            }
            sb = new StringBuilder("|").append(String.format("%-5s", year)).append("|")
                    .append(String.format("%-6s", i)).append("|").append(String.format("%9.2f", sumPerMonth)).append("|")
                    .append(String.format("%10.2f", movingAverage(oneMonthBackSum, twoMonthBackSum))).append("|");
            array[j] = sb.toString();
            j++;
            twoMonthBackSum = oneMonthBackSum;
            oneMonthBackSum = sumPerMonth;
        }
        return array;
    }

    /* oblicza oszczędności,ktore powinny zostać po odliczeniu wydatkow
    od dochodów wraz z liczeniem prostej średniej krocz. z ost. 2 mcy*/
    public String[] savingsPerMonth(List<Expense> expensesList, List<Income> incomesList, int yearNumber) {
        String year = String.valueOf(yearNumber);
        double savings;
        double oneMonthBackSum = 0.0;  //srednia nie uwzglednia poprzedniego roku
        double twoMonthBackSum = 0.0;
        StringBuilder sb;
        String[] array = new String[13]; //12 mcy razy + nagłówek
        array[0] = "|YEAR |MONTH | INCOMES|EXPENSES|  SAVINGS|MOVING AVG|";
        String[] incomesPerMonthArray = incomesSumPerMonth(incomesList, yearNumber);
        String[] expensesPerMonthAray = expensesSumPerMonth(expensesList, yearNumber);
        List<String[]> incomesAfterSplit = new ArrayList<>();
        List<String[]> expensesAfterSplit = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            incomesAfterSplit.add(incomesPerMonthArray[i].split("[|]"));
            expensesAfterSplit.add(expensesPerMonthAray[i].split("[|]"));
        }

        double expenseSum;
        double incomeSum;
        int j = 1;
        for (int i = 1; i <= 12; i++) { //dla kazdego miesiaca
            incomeSum = Double.valueOf(incomesAfterSplit.get(i - 1)[3].replace(",", ".")); //suma exp z danego miesiąca
            expenseSum = Double.valueOf(expensesAfterSplit.get(i - 1)[3].replace(",", ".")); //suma inc z danego miesiąca
            savings = incomeSum - expenseSum;
            sb = new StringBuilder("|").append(String.format("%-5s", year)).append("|")
                    .append(String.format("%-6s", i)).append("|")
                    .append(String.format("%8.2f", incomeSum)).append("|")
                    .append(String.format("%8.2f", expenseSum)).append("|")
                    .append(String.format("%9.2f", savings)).append("|")
                    .append(String.format("%10.2f", movingAverage(oneMonthBackSum, twoMonthBackSum))).append("|");
            array[j] = sb.toString();
            j++;
            twoMonthBackSum = oneMonthBackSum;
            oneMonthBackSum = savings;
        }
        return array;
    }

    public String[] expensesSumPerMonth(List<Expense> expenseList, int yearNumber) {
        String year = String.valueOf(yearNumber);
        double sumPerMonth;
        double oneMonthBackSum = 0.0;  //srednia nie uwzglednia poprzedniego roku
        double twoMonthBackSum = 0.0;
        StringBuilder sb;
        String[] array = new String[13]; //12 mcy razy + nagłówek
        array[0] = "|YEAR |MONTH |      SUM|MOVING AVG|";
        int j = 1;
        for (int i = 1; i <= 12; i++) { //dla kazdego miesiaca
            sumPerMonth = 0.0;
            for (Expense expense : expenseList) {
                if (expense.getDate().getYear() == yearNumber && expense.getDate().getMonth().getValue() == i) {
                    sumPerMonth += expense.getAmount();
                }
            }
            sb = new StringBuilder("|").append(String.format("%-5s", year)).append("|")
                    .append(String.format("%-6s", i)).append("|").append(String.format("%9.2f", sumPerMonth)).append("|")
                    .append(String.format("%10.2f", movingAverage(oneMonthBackSum, twoMonthBackSum))).append("|");
            array[j] = sb.toString();
            j++;
            twoMonthBackSum = oneMonthBackSum;
            oneMonthBackSum = sumPerMonth;
        }
        return array;
    }
}
