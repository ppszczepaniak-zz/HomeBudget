import controller.Controller;
import dataCreator.Creator;
import model.DataRepository;
import services.ReportManager;
import view.UserInterface;

public class Main {
    public static void main(String[] args) {
        Creator dataCreator = new Creator();
        DataRepository data = new DataRepository(dataCreator.getCreatedIncomesList(), dataCreator.getCreatedExpensesList());
        UserInterface ui = new UserInterface();
        ReportManager reportManager = new ReportManager(ui);

        Controller controller = new Controller(data, reportManager, ui);
        controller.run();
    }
}
