package dataCreator;

import model.enums.ExpenseCategory;
import model.enums.FamilyMembers;
import model.enums.IncomeCategory;
import model.models.Expense;
import model.models.Income;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Creator {
    private List<Income> createdIncomesList;
    private List<Expense> createdExpensesList;

    public Creator() {
        createdIncomesList = createIncomes();
        createdExpensesList = createExpenses();
    }

    private List<Income> createIncomes() {
        createdIncomesList = new ArrayList<>();
        LocalDateTime dadContractMoneyTransferTime = LocalDateTime.parse("2018-01-20T08:00:00");

        for (int i = 1; i <=18 ; i++) { //pensja taty przez 18 miesiecy
            createdIncomesList.add(new Income(dadContractMoneyTransferTime, FamilyMembers.DAD, IncomeCategory.CONTRACT,8000.00));
            dadContractMoneyTransferTime = dadContractMoneyTransferTime.plusMonths(1);
        }
        //pozostale Taty
        createdIncomesList.add(new Income(LocalDateTime.parse("2019-01-13T15:00:00"),FamilyMembers.DAD,IncomeCategory.B2B,5300.25)); // b2b Taty
        createdIncomesList.add(new Income(LocalDateTime.parse("2019-05-10T11:00:00"),FamilyMembers.DAD,IncomeCategory.B2B,4302.80)); // b2b Taty
        createdIncomesList.add(new Income(LocalDateTime.parse("2019-06-12T13:00:00"),FamilyMembers.DAD,IncomeCategory.B2B,2450.00)); // b2b Taty
        //pozostale Mamy
        createdIncomesList.add(new Income(LocalDateTime.parse("2019-02-12T12:00:00"),FamilyMembers.MUM,IncomeCategory.COMMISION,1800.00)); //zlecenie mamy
        createdIncomesList.add(new Income(LocalDateTime.parse("2019-03-21T12:00:00"),FamilyMembers.MUM,IncomeCategory.COMMISION,4200.00)); //zlecenie mamy
        createdIncomesList.add(new Income(LocalDateTime.parse("2019-05-05T14:00:00"),FamilyMembers.MUM,IncomeCategory.OTHER,9240.99)); //inne mamy

        return createdIncomesList;
    }


    private List<Expense> createExpenses() {
        createdExpensesList = new ArrayList<>();
        LocalDateTime rentTime = LocalDateTime.parse("2018-01-22T14:00:00");

        for (int i = 1; i <=18 ; i++) { //czynsz co miesiac do czerwca 2019
            createdExpensesList.add(new Expense(rentTime,FamilyMembers.DAD, ExpenseCategory.BILLS,"Czynsz",1440.00));
            rentTime = rentTime.plusMonths(1);
        }
        // paliwo + naprawa
        createdExpensesList.add(new Expense(LocalDateTime.parse("2019-01-14T22:15:42"),FamilyMembers.DAD, ExpenseCategory.CAR,"Paliwo",245.20));
        createdExpensesList.add(new Expense(LocalDateTime.parse("2019-02-23T21:00:42"),FamilyMembers.DAD, ExpenseCategory.CAR,"Paliwo",212.90));
        createdExpensesList.add(new Expense(LocalDateTime.parse("2019-04-12T09:40:12"),FamilyMembers.DAD, ExpenseCategory.CAR,"Paliwo",198.50));
        createdExpensesList.add(new Expense(LocalDateTime.parse("2019-06-02T12:00:42"),FamilyMembers.DAD, ExpenseCategory.CAR,"Blacharz",1245.00));

        //inne
        createdExpensesList.add(new Expense(LocalDateTime.parse("2019-01-19T17:00:42"),FamilyMembers.MUM, ExpenseCategory.FOOD,"Zakupy",320.02));
        createdExpensesList.add(new Expense(LocalDateTime.parse("2019-03-14T17:00:42"),FamilyMembers.MUM, ExpenseCategory.CLOTHES,"Ubrania dla dzieci",199.00));
        createdExpensesList.add(new Expense(LocalDateTime.parse("2019-05-19T20:00:42"),FamilyMembers.MUM, ExpenseCategory.PLEASURE,"Kino",68.00));
        createdExpensesList.add(new Expense(LocalDateTime.parse("2019-01-19T17:00:42"),FamilyMembers.MUM, ExpenseCategory.PERSONAL,"Lekarz",150.00));

        return createdExpensesList;
    }


    public List<Income> getCreatedIncomesList() {
        return createdIncomesList;
    }

    public List<Expense> getCreatedExpensesList() {
        return createdExpensesList;
    }


}
