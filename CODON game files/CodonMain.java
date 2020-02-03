
/* 
 * Author: Renee Linford
 * Date: 1-31-20
 * Capstone: "Codon" game
 * Player builds amino acid chain by matching anti-codon to a codon on the mRNA strand.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;

public class CodonMain extends Application { // Builds GUI with controller & FXML file.

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start (Stage primaryStage) throws Exception {
		
		// Create the FXMLLoader.
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/CodonLayout.v3.fxml"));
		
		// Create the Parent root and load.
		Parent root = loader.load();
		
		// Create the Scene.      
		Scene scene = new Scene(root, 335, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CODON");
		primaryStage.show();
	}
}
