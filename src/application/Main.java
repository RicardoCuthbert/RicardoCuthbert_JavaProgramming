package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.Page1View;
import view.Page2;

@SuppressWarnings("unused")
public class Main extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		new Page1View(stage);
		stage.setTitle("Bootcamp_BNCC_Kiw_Kiw");
		stage.show();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	
}
