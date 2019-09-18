package view;

import model.enums.ExpenseCategory;
import model.enums.FamilyMembers;
import model.enums.IncomeCategory;
import model.models.Expense;
import model.models.Income;
import view.enums.UserActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInterface {
    private int chosenIndex;
    private Scanner input = new Scanner(System.in);

    public UserActions printMenu() {
        do {
            chosenIndex = -1;
            System.out.println("===MAIN MENU===");
            System.out.println("[1] Add expense");
            System.out.println("[2] Add income");
            System.out.println("[3] Modify expenses");
            System.out.println("[4] Modify incomes");
            System.out.println("[5] Generate report");
            System.out.println("[6] Quit");
            userChoiceAndVerification();
        } while (chosenIndex > 6 || chosenIndex < 1);
        System.out.println("You've chosen [" + chosenIndex + "]");
        switch (chosenIndex) {
            case 1:
                return UserActions.ADD_EXPENSE;
            case 2:
                return UserActions.ADD_INCOME;
            case 3:
                return UserActions.MODIFY_EXPENSE;
            case 4:
                return UserActions.MODIFY_INCOME;
            case 5:
                return UserActions.GENERATE_REPORT;
            case 6:
                return UserActions.QUIT;
        }
        return null;  //returns null if switch fails (impossible because of do-while loop)
    }

    public FamilyMembers choosePerson(List<FamilyMembers> familyMembersList) {
        System.out.println("Choose person...");
        return chooseObject(familyMembersList);
    }

    public ExpenseCategory chooseExpenseCategory(List<ExpenseCategory> expenseCategoryList) {
        System.out.println("Choose expense category..");
        return chooseObject(expenseCategoryList);
    }

    public IncomeCategory chooseIncomeCategory(List<IncomeCategory> incomeCategoryList) {
        System.out.println("Choose income category...");
        return chooseObject(incomeCategoryList);
    }

    public String chooseExpenseName() {
        String name;
        do {
            System.out.println("Type the name of the expense...");
            name = null;
            try {
                name = input.next();
            } catch (NoSuchElementException e) {
                System.out.println("Error! Please write something.");
                input.next();
            }
        } while (name.equals(null));
        System.out.println("Expense name: " + name);
        return name;
    }

    public double chooseAmount() {
        double chosenAmount;
        do {
            System.out.println("Choose the amount 0.01 - 100000.00");
            chosenAmount = 0.00;
            try {
                chosenAmount = input.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("ERROR! Please type the number!");
                input.next();
            }
        } while (chosenAmount > 100000.00 || chosenAmount < 0.01);
        System.out.println("You've chosen: " + chosenAmount);
        return chosenAmount;
    }

    public UserActions chooseAction() {
        do {
            chosenIndex = -1;
            System.out.println("Please choose your action:");
            System.out.println("[1] Modify");
            System.out.println("[2] Remove from the list");
            userChoiceAndVerification();
        } while (chosenIndex > 2 || chosenIndex < 1);
        System.out.println("You've chosen [" + chosenIndex + "]");
        switch (chosenIndex) {
            case 1:
                return UserActions.CHANGE_ELEMENT;
            case 2:
                return UserActions.REMOVE_ELEMENT;
        }
        return null;
    }

    public Expense chooseExpense(List<Expense> expensesList) {
        System.out.println("Expense choice.");
        return chooseObject(expensesList);
    }

    public Income chooseIncome(List<Income> incomesList) {
        System.out.println("Income choice.");
        return chooseObject(incomesList);
    }

    public void printReport(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public UserActions chooseReportAction() {
        do {
            chosenIndex = -1;
            System.out.println("Please choose your action:");
            System.out.println("[1] Show all incomes");
            System.out.println("[2] Show all expenses");
            System.out.println("[3] Generate Report: Expenses per month for each category");
            System.out.println("[4] Generate Report: Expenses per person");
            System.out.println("[5] Generate Report: Incomes per month");
            System.out.println("[6] Generate Report: Savings per month");
            userChoiceAndVerification();
        } while (chosenIndex > 6 || chosenIndex < 1);
        System.out.println("You've chosen [" + chosenIndex + "]");
        switch (chosenIndex) {
            case 1:
                return UserActions.SHOW_ALL_INCOMES;
            case 2:
                return UserActions.SHOW_ALL_EXPENSES;
            case 3:
                return UserActions.REPORT_EXPENSE_PER_CATEGORY;
            case 4:
                return UserActions.REPORT_EXPENSE_PER_PERSON;
            case 5:
                return UserActions.REPORT_INCOME_PER_MONTH;
            case 6:
                return UserActions.REPORT_SAVINGS_PER_MONTH;
        }
        return null;
    }

    public LocalDateTime chooseDate() {
        int year;
        int monthNumber;
        int dayNumber;
        LocalDate date = null;
        boolean wasExceptionThrown;

        do {
            wasExceptionThrown = false;
            year = chooseYear(); //choose year
            monthNumber = chooseMonth(); //choose month
            dayNumber = chooseDay(); //choose day
            try {
                date = LocalDate.of(year, monthNumber, dayNumber);
            } catch (java.time.DateTimeException e) {
                System.out.println("There is no such day in " + Month.of(monthNumber) + " in year " + year + "!");
                wasExceptionThrown = true;
            }
        } while (wasExceptionThrown);

        LocalTime time = LocalTime.parse("12:00:00"); //bez opcji wyboru godziny
        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        System.out.println("Element date is: " + localDateTime);
        return localDateTime;
    }

    public int chooseYear() {
        int year = 0;
        do {
            chosenIndex = -1;
            System.out.println("Choose year...");
            System.out.println("[1] 2018");
            System.out.println("[2] 2019");
            System.out.println("[3] 2020");
            userChoiceAndVerification();
        } while (chosenIndex > 3 || chosenIndex < 1);
        switch (chosenIndex) {
            case 1:
                year = 2018;
                break;
            case 2:
                year = 2019;
                break;
            case 3:
                year = 2020;
                break;
        }
        return year;
    }

    public <T> void printAllElements(List<T> theList) {
        System.out.println("============ ALL ELEMENTS=============");
        for (T element : theList) {
            System.out.println(element);
        }
        System.out.println();
    }

    public void quit() {
        System.out.println("Goodbye.");
    }

    //private methods
    private int chooseMonth() {
        do {
            chosenIndex = -1;
            System.out.println("Choose month [1-12]...");
            userChoiceAndVerification();
        } while (chosenIndex > 12 || chosenIndex < 1);
        return chosenIndex;
    }

    private int chooseDay() {
        do {
            chosenIndex = -1;
            System.out.println("Choose day [1-31]...");
            userChoiceAndVerification();
        } while (chosenIndex > 31 || chosenIndex < 1);
        return chosenIndex;
    }

    private <T> T showPagedList(List<T> theList) { //wyswietla cala liste, gdy rozmiar <10. W innym wypadku wyswietla kolejne strony
        int size = 9; //ilość wierszy do wyświetlania na stronę - zakładam 9
        int listIndexAddition = -size; //bedzie dodawane do indeksu listy
        int listsPerPage;
        T chosenElement;

        if (theList.size() % size == 0) { //jeśli rozmiar z listy jest podzielny przez 9 bez reszty
            listsPerPage = theList.size() / size;
        } else {
            listsPerPage = (int) Math.ceil((double) theList.size() / size); //dziele przez 9 i zaokr. w górę, np. 15 elementów to 2 strony
        }

        for (int j = 1; j <= listsPerPage; j++) {
            listIndexAddition += size; //for 1st page 0, for 2nd page 9, etc.
            if (j == listsPerPage && theList.size() % size != 0) { //mniej elementów może być wyświetlanych na ostatniej stronie
                size = theList.size() % size;
            }
            showPage(theList, listIndexAddition, listsPerPage, j, size);
            if (chosenIndex != 0) {
                break; //przerywa pętlę, jeśli user coś wybierze z listy na ekranie
            }
        }
        chosenElement = theList.get(chosenIndex + listIndexAddition - 1);
        return chosenElement;
    }

    private <T> void showPage(List<T> theList, int listIndexAddition, int listsPerPage, int j, int size) {
        int lowerCondition = 0;
        do {
            chosenIndex = -1;
            System.out.println("Please choose the element from the list (page " + j + " of " + listsPerPage + ")...");
            for (int i = 1; i <= size; i++) {
                System.out.println("[" + i + "] " + theList.get(i + listIndexAddition - 1));
            }
            if (j != listsPerPage) {
                System.out.println("[0] => Show next page..."); //wyswietli sie na wszystkich stronach poza ostatnią
            } else {
                lowerCondition = 1; //na ostatniej stronie nie ma opcji [0], więc user nie może tego wybrać
            }
            userChoiceAndVerification();
        } while (chosenIndex > size || chosenIndex < lowerCondition);
    }

    private <T> T chooseObject(List<T> theList) {  //chooses Person, ExpenseCategory or IncomeCategory
        T chosenObject;
        chosenObject = showPagedList(theList);
        System.out.println("You've chosen " + chosenObject);
        return chosenObject;
    }

    private void userChoiceAndVerification() {
        try {
            chosenIndex = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("ERROR! Please type the number from the menu!");
            input.next();  //"eats" the wrong token, so it won't be passed to Scanner again
        }
    }
}
