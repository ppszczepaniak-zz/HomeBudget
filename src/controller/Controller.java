package controller;

import model.DataRepository;
import model.enums.ExpenseCategory;
import model.enums.FamilyMembers;
import model.enums.IncomeCategory;
import model.models.Expense;
import model.models.Income;
import services.ReportManager;
import view.UserInterface;
import view.enums.UserActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private DataRepository dataRepository;
    private ReportManager report;
    private UserInterface userInterface;
    private List<FamilyMembers> familyMembersList = Arrays.asList(FamilyMembers.values());
    private List<ExpenseCategory> expenseCategoryList = Arrays.asList(ExpenseCategory.values());
    private List<IncomeCategory> incomeCategoryList = Arrays.asList(IncomeCategory.values());


    public Controller(DataRepository dataRepository, ReportManager report, UserInterface userInterface) {
        this.dataRepository = dataRepository;
        this.report = report;
        this.userInterface = userInterface;
    }

    public void run() {
        mainMenu();
    }

    private void mainMenu() {
        UserActions actions = userInterface.printMenu();
        switch (actions) {
            case ADD_EXPENSE:
                addExpense();
                break;
            case ADD_INCOME:
                addIncome();
                break;
            case MODIFY_EXPENSE:
                modifyExpense();
                break;
            case MODIFY_INCOME:
                modifyIncome();
                break;
            case GENERATE_REPORT:
                generateReport();
                break;
            case QUIT:
                quit();
                break;
        }
    }

    private void addExpense() {
        LocalDateTime chosenDate = userInterface.chooseDate();

        FamilyMembers chosenPerson = userInterface.choosePerson(familyMembersList);
        ExpenseCategory chosenCategory = userInterface.chooseExpenseCategory(expenseCategoryList);
        String chosenName = userInterface.chooseExpenseName();
        double chosenAmount = userInterface.chooseAmount();

        dataRepository.getExpensesList().add(new Expense(chosenDate, chosenPerson, chosenCategory, chosenName, chosenAmount));
        dataRepository.sortExpenses(); //sortuje liste po dacie
        mainMenu();
    }

    private void addIncome() {
        LocalDateTime chosenDate = userInterface.chooseDate();
        FamilyMembers chosenPerson = userInterface.choosePerson(familyMembersList);
        IncomeCategory chosenCategory = userInterface.chooseIncomeCategory(incomeCategoryList);
        double chosenAmount = userInterface.chooseAmount();

        dataRepository.getIncomesList().add(new Income(chosenDate, chosenPerson, chosenCategory, chosenAmount));
        dataRepository.sortIncomes(); //sortuje liste po dacie
        mainMenu();
    }

    private void modifyExpense() {
        Expense chosenExpense = userInterface.chooseExpense(dataRepository.getExpensesList());         //chosenExpense: UI zwroc expense z listy ktora dostaniesz
        UserActions chosenAction = userInterface.chooseAction();
        switch (chosenAction) {
            case CHANGE_ELEMENT:
                dataRepository.getExpensesList().remove(chosenExpense);
                addExpense();
                break;
            case REMOVE_ELEMENT:
                dataRepository.getExpensesList().remove(chosenExpense);
                mainMenu();
                break;
        }
    }

    private void modifyIncome() {
        Income chosenIncome = userInterface.chooseIncome(dataRepository.getIncomesList());         //chosenExpense: UI zwroc expense z listy ktora dostaniesz
        UserActions chosenAction = userInterface.chooseAction();
        switch (chosenAction) {
            case CHANGE_ELEMENT:
                dataRepository.getIncomesList().remove(chosenIncome);
                addIncome();
                break;
            case REMOVE_ELEMENT:
                dataRepository.getIncomesList().remove(chosenIncome);
                mainMenu();
                break;
        }
    }

    private void generateReport() {
        int yearNumber;
        UserActions chosenAction = userInterface.chooseReportAction();
        switch (chosenAction) {
            case SHOW_ALL_INCOMES:
                userInterface.printAllElements(dataRepository.getIncomesList());
                break;
            case SHOW_ALL_EXPENSES:
                userInterface.printAllElements(dataRepository.getExpensesList());
                break;
            case REPORT_EXPENSE_PER_CATEGORY:
                yearNumber = userInterface.chooseYear();
                String[] ExpeseArrayReport = report.expensesPerCategory(dataRepository.getExpensesList(), expenseCategoryList, yearNumber);
                userInterface.printReport(ExpeseArrayReport);
                break;
            case REPORT_EXPENSE_PER_PERSON:
                yearNumber = userInterface.chooseYear();
                String[] ExpesePerPersonReport = report.expensesPerPerson(dataRepository.getExpensesList(), yearNumber);
                userInterface.printReport(ExpesePerPersonReport);
                break;
            case REPORT_INCOME_PER_MONTH:
                yearNumber = userInterface.chooseYear();
                String[] IncomesPerMonthReport = report.incomesSumPerMonth(dataRepository.getIncomesList(),yearNumber);
                userInterface.printReport(IncomesPerMonthReport);
                break;
            case REPORT_SAVINGS_PER_MONTH:
                yearNumber = userInterface.chooseYear();
                String[] savingsReport = report.savingsPerMonth(dataRepository.getExpensesList(), dataRepository.getIncomesList(), yearNumber);
                userInterface.printReport(savingsReport);
                break;
        }
        mainMenu();
    }


    private void quit() {
        userInterface.quit();
    }

}

