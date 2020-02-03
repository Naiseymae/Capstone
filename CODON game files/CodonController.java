package codonPackage;

/* 
 * Author: Renee Linford
 * Date: 1-31-20
 * Capstone: "Codon" game
 * Controller class
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.ConditionalFeature;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.util.Duration;

public class CodonController { // Controller class.

	//** Add a public no-args constructor.
	public CodonController() {
	}

	//** Location and resources will be automatically injected by the FXML loader. 
	@FXML
	private URL location;
	@FXML
	private ResourceBundle resources;
	@FXML
	private Button btStart;
	@FXML 
	private Button btShuffle;
	@FXML
	private Button btAnticodon;
	@FXML 
	private ArrayList<String> buttonsPressed = new ArrayList<String>();
	@FXML
	private ArrayList<Button> buttonList = new ArrayList<Button>();
	@FXML
	private ArrayList<Integer> indexListX = new ArrayList<Integer>();
	@FXML
	private ArrayList<Integer> indexListY = new ArrayList<Integer>();
	@FXML
	private GridPane letterGrid;
	@FXML
	private HBox mRNAbox;
	@FXML
	private LinkedList<Rectangle> mRNAbases = new LinkedList<Rectangle>();
	@FXML
	private VBox tRNA1;
	@FXML
	private VBox tRNANew;
	@FXML
	private VBox tRNAOld;
	@FXML
	private CubicCurve lineIn;
	@FXML
	private CubicCurve lineOut;
	@FXML 
	private CubicCurve lineAA;
	@FXML
	private VBox chainBox;
	@FXML
	private ArrayList<Circle> aaList = new ArrayList<Circle>();
	@FXML 
	private AnchorPane bottomPane;
	@FXML
	private AnchorPane topPane;
	@FXML
	private String codonToAA;
	@FXML
	private String codonToAC;
	@FXML
	private Ellipse ribozyme1;
	@FXML
	private Ellipse ribozyme2;
	@FXML
	private Ellipse ribozyme3;
	@FXML
	private Ellipse ribozyme4;
	@FXML
	private Ellipse ribozyme5;
	@FXML
	private Rectangle mRNAbackbone;
	@FXML
	private Label lblMRNA;
	@FXML
	private Label lblCodon;
	@FXML
	private Text txtInstruction;
	@FXML
	private HBox energyGrid;
	@FXML
	private Label lblLength;
	@FXML
	private Label lblEnergy;
	@FXML
	private Label lblAnticodon;
	@FXML
	private Label lblRibosome;
	@FXML
	private Label lblEnd1;
	@FXML
	private Label lblEnd2;
	@FXML
	private Label lblEndLength;
	@FXML
	private Circle circleEnd;
	@FXML
	private Text timer = new Text();

	private Map<String, String> aminoAcidMap = new HashMap<String, String>();
	private Map<String, String> antiCodonMap = new HashMap<String, String>();

	//** Create an animation for the countdown.
	Timeline animation = new Timeline(
			// Create keyframe.
			new KeyFrame(Duration.millis(1000), e-> { // Handler for TimeLine.
				String s = timer.getText(); // Convert text to string.
				timer.setText(countdown(s)); // Call countdown method after new text "second" is set.
			})); 

	//** Add accessible methods. 
	@FXML
	private void initialize() {
	}

	@FXML 
	private void start(ActionEvent event) { //** Start button pressed.

		// Remove Start button & end of game labels.
		bottomPane.getChildren().remove(btStart);
		lblEnd1.setOpacity(0.0);
		lblEnd2.setOpacity(0.0);
		lblEndLength.setOpacity(0.0);
		circleEnd.setOpacity(0.0);
		chainBox.getChildren().clear();
		aaList.clear();
		topPane.getChildren().remove(tRNA1);
		timer.setText("99"); // Reset timer

		// Fill energy bar & starting length & energy numbers.
		lblEnergy.setText("5"); // Starting energy 5.
		lblLength.setText("0"); // Starting length 0.
		for (int l = 0; l < 5; l++) {
			Rectangle e = new Rectangle(61, 20);
			e.setFill(Color.web("0xe1d557ff"));
			e.setStroke(Color.web("0x908736ff"));
			energyGrid.getChildren().add(e);
		}

		// Add shuffle button, anticodon label, & letterGrid.
		btAnticodon.setOpacity(1.0);
		lblAnticodon.setOpacity(1.0);;
		btShuffle.setOpacity(1.0);
		newLetterGrid(); // Method places random but equal letters into grid.
		int randomY = (int) (Math.random() * 5) + 1; // Random columm index.
		int randomX = (int) (Math.random() * 3) + 1 ; // Random row index.

		// Place buttons into letterGrid.
		for (int i = 0; i < 3; i++) { // Ensure start anticodon (UAC) for MET is in grid.
			if (i == 0 && randomY <= 3) { // Placement for U for column 1-3.
				letterGrid.add(newGridButtonU(), randomY, randomX);
			}
			else if (i == 1 && randomY <= 3) { // Placement for A for column 1-3.
				letterGrid.add(newGridButtonA(), randomY + 1, randomX);
			}
			else if (i == 2 && randomY <= 3) { // Placement for C for column 1-3.
				letterGrid.add(newGridButtonC(), randomY + 2, randomX);
			}
			else if (i == 0 && randomY >= 4) { // Placement for U for column 4-5.
				letterGrid.add(newGridButtonU(), randomY, randomX);
			}
			else if (i == 1 && randomY >= 4) { // Placement for A for column 4-5.
				letterGrid.add(newGridButtonA(), randomY, randomX + 1);
			}
			else if (i == 2 && randomY >= 4) { // Placement for C for column 4-5.
				letterGrid.add(newGridButtonC(), randomY, randomX + 2);
			}
		}

		// Add ribozyme circles & mRNA backbone.
		mRNAbackbone.setOpacity(1.0);
		lblMRNA.setOpacity(1.0);
		lblCodon.setOpacity(1.0);

		// Add bases to mRNAbox.
		for (int k = 0; k < 28; k++) {
			Rectangle b = newBase(); // All other bases are random.
			if (k == 15 || k == 16 || k == 17) { // Start codon is MET (AUG).
				if (k == 15) {
					b.setFill(Color.rgb(17, 151, 12));
				}
				if (k == 16) {
					b.setFill(Color.rgb(148, 6, 6));
				}
				if (k == 17) {
					b.setFill(Color.rgb(174, 81, 0));
				}
			}
			if (k == 15 || k == 16 || k == 17) {
				Glow g = new Glow();
				g.setLevel(0.8);
				b.setEffect(g);
			}
			mRNAbox.getChildren().add(b); // Add to Hbox.
			mRNAbases.add(b); // Add to linkedList.
		}
		// Start Timer.
		animation.setCycleCount(Timeline.INDEFINITE); 
		animation.setDelay(Duration.seconds(1));
		animation.play();
	}

	@FXML 
	private void tRNAtransition() { //** Move tRNAs on path transitions.

		// Stop glow of old codons.
		for (int k = 14; k < 21; k++) {
			Glow g = new Glow();
			Rectangle r = mRNAbases.get(k);
			if (k == 14 || k == 15 || k == 16 || k == 17) { // Stop glow for old codon.
				g.setLevel(0.0);
				r.setEffect(g);
				mRNAbases.set(k, r);
			}
			if (k == 18 || k == 19 || k == 20) {
				g.setLevel(0.8);
				r.setEffect(g);
				mRNAbases.set(k, r);
			}
		}

		// Create amino acid circle.
		StringBuilder s = new StringBuilder(); // Build codon string to pass to new amino acid.
		for (int j = 15; j < 18; j++) {
			Rectangle b = mRNAbases.get(j);

			if (b.getFill().equals(Color.web("0x11970cff"))) { // If green, assign A.
				s.append("A");
			}
			else if (b.getFill().equals(Color.web("0x940606ff"))) { // If red, assign U.
				s.append("U");
			}
			else if (b.getFill().equals(Color.web("0xb99400ff"))) { // If yellow, assign C.
				s.append("C");
			}
			else if (b.getFill().equals(Color.web("0xae5100ff"))) { // If orange, assign G.
				s.append("G");
			}
		}
		String codon = s.toString(); 
		Circle aaCircle = newAminoAcid(codon); // Build new amino acid.

		// Create path for amino acid.
		Path aaPath = new Path();
		MoveTo moveToAA = new MoveTo();
		moveToAA.setX(lineAA.getStartX());
		moveToAA.setY(lineAA.getStartY());
		aaPath.getElements().add(moveToAA);
		aaPath.getElements().add(new CubicCurveTo(
				lineAA.getControlX1(), lineAA.getControlY1(), 
				lineAA.getControlX2(), lineAA.getControlY2(), 
				lineAA.getEndX(), lineAA.getEndY()));
		PathTransition transitionAA = new PathTransition();
		transitionAA.setPath(aaPath);
		transitionAA.setNode(aaCircle);
		transitionAA.setDuration(Duration.millis(1500));
		transitionAA.setCycleCount(1);

		// Add new tRNA object to Transition path.
		tRNANew = new TRNA_Object(mRNAbases.get(15), mRNAbases.get(16), mRNAbases.get(17));
		topPane.getChildren().addAll(aaCircle, tRNANew); // Add to topPane

		// Enter tRNA: Move tRNA into Ribozyme.
		Path tRNAPath1 = new Path();
		MoveTo moveTo1 = new MoveTo();
		moveTo1.setX(lineIn.getStartX());
		moveTo1.setY(lineIn.getStartY());
		tRNAPath1.getElements().add(moveTo1);
		tRNAPath1.getElements().add(new CubicCurveTo(
				lineIn.getControlX1(), lineIn.getControlY1(), 
				lineIn.getControlX2(), lineIn.getControlY2(), 
				lineIn.getEndX(), lineIn.getEndY()));
		PathTransition transition1 = new PathTransition();
		transition1.setPath(tRNAPath1);
		transition1.setNode(tRNANew);
		transition1.setDuration(Duration.millis(1500));
		transition1.setCycleCount(1);

		// Play tRNA & aa paths.
		transitionAA.play();
		transition1.play();

		// After transition add to amino acid chain.
		transitionAA.setOnFinished(event -> {
			topPane.setLeftAnchor(chainBox, -12.5);
			chainBox.setTranslateY(0 + (aaList.size() * -8));
			addToChain(aaCircle); // Call addToChain() method.
		});

		// Exit tRNA: First tRNA becomes old tRNA & exits.
		tRNAOld = tRNA1; // Set tRNA1 to become old tRNA.
		Path tRNAPath2 = new Path();
		MoveTo moveTo2 = new MoveTo();
		moveTo2.setX(lineOut.getStartX());
		moveTo2.setY(lineOut.getStartY());
		tRNAPath2.getElements().add(moveTo2);
		tRNAPath2.getElements().add(new CubicCurveTo(
				lineOut.getControlX1(), lineOut.getControlY1(), 
				lineOut.getControlX2(), lineOut.getControlY2(), 
				lineOut.getEndX(), lineOut.getEndY()));
		PathTransition transition2 = new PathTransition();
		transition2.setPath(tRNAPath2);
		transition2.setNode(tRNAOld);
		transition2.setDelay(Duration.seconds(1));
		transition2.setDuration(Duration.millis(2500));
		transition2.setCycleCount(1);
		transition2.play();
		tRNA1 = tRNANew; // Assign new tRNA to tRNA to be removed next round.
	}

	@FXML
	private void menuPressed() { //** Pause game and go to menu.
	}

	@FXML
	private void closePressed() { //** Close game. 
	}

	@FXML
	private void gridPressed(ActionEvent event) { //** Letter grid button pressed.

		// Reset Ribosome color.
		normalRibosome();

		// If ArrayList buttonsPressed contains 3 or more items, clear arrayLists & labels.
		if (buttonsPressed.size() > 2) {
			buttonsPressed.clear();
			buttonList.clear();
			indexListX.clear();
			indexListY.clear();
			btAnticodon.setText(" ");
		}

		// Create instance of button to get text.
		Node node = (Node)event.getSource();
		Button b = (Button)node;

		// Add new button pressed to ArrayList buttonsPressed.
		buttonsPressed.add(b.getText());
		buttonList.add(b);

		// Highlight button pressed.
		Glow g = new Glow();
		g.setLevel(0.3);
		b.setEffect(g);
		if (buttonList.size() > 2) { // If more than 3 buttons pressed, clear glow.
			g.setLevel(0.0);
			InnerShadow innerShadow = new InnerShadow();
			innerShadow.setColor(Color.rgb(184, 178, 89));
			for (int i = 0; i < buttonList.size(); i++) {
				Button bt = buttonList.get(i);
				bt.setEffect(g); // Clear glow.
				bt.setEffect(innerShadow); // Reset innerShadow effect.
			}
		}

		// Get grid locations
		indexListX.add(letterGrid.getRowIndex(node)); 
		indexListY.add(letterGrid.getColumnIndex(node));

		// Display base button pressed.
		if (buttonsPressed.size() > 0) {
			btAnticodon.setText(" ");
			btAnticodon.setText(buttonsPressed.get(0).toString());
			if (buttonsPressed.size() > 1) { // Set new base letter.
				btAnticodon.setText(buttonsPressed.get(0).toString() + buttonsPressed.get(1).toString()); 
				if (buttonsPressed.size() > 2) { // Set new base letter.
					btAnticodon.setText(buttonsPressed.get(0).toString() + buttonsPressed.get(1).toString() + buttonsPressed.get(2).toString()); 
				}
			}
		}

		//** If next button pressed not adjacent to first button, clear ArrayList and make next button the first button in list.

		// When 3 buttons in ArrayList, check if order matches the anti-codon.
		if (buttonsPressed.size() == 3) {

			// Concat player anticodon from buttonsPressed.
			String b1 = buttonsPressed.get(0);
			String b2 = buttonsPressed.get(1);
			String b3 = buttonsPressed.get(2);
			String playerAntiCodon = b1 + b2 + b3;

			if (!checkMatch(playerAntiCodon)) { // If false:
				wrongAnticodon(); // Change ribosome color to red.
			}
			if (checkMatch(playerAntiCodon)) { // If true:
				// Change ribosome color to green.
				correctAnticodon();

				// Move in/out tRNAs.
				tRNAtransition();

				// Add to length number.
				int n = Integer.parseInt(lblLength.getText()); // Get length number.
				n++; // Add to length
				lblLength.setText(Integer.toString(n)); // Set new number.

				// Remove Highlighted buttons.
				letterGrid.getChildren().remove(buttonList.get(0));
				letterGrid.getChildren().remove(buttonList.get(1));
				letterGrid.getChildren().remove(buttonList.get(2));

				// Add new buttons to removed button locations.
				letterGrid.add(newGridButton(), indexListY.get(0), indexListX.get(0));
				letterGrid.add(newGridButton(), indexListY.get(1), indexListX.get(1));
				letterGrid.add(newGridButton(), indexListY.get(2), indexListX.get(2));

				// Add & remove bases on mRNA.
				for (int j = 0; j < 3; j++) {
					Rectangle base = newBase();
					mRNAbases.add(base); // Add to beginning of mRNA.
					mRNAbox.getChildren().add(base); 
					removeBase(); // Remove from end.
				}

				// Every +5 to length, add 1 energy.
				int length = Integer.parseInt(lblLength.getText());
				if (length % 5 == 0) {
					addEnergy();
				}

				// Clear arrayLists for next call.
				buttonsPressed.clear();
				buttonList.clear();
				indexListX.clear();
				indexListY.clear();
			}
		}
	}

	private String getAminoAcid(String codon) { //** Match the anti-codon to amino acid.

		// Create map entries (K, V)
		aminoAcidMap.put("UUU", "PHE");	aminoAcidMap.put("UUC", "PHE");	aminoAcidMap.put("UUA", "LEU"); aminoAcidMap.put("UUG", "LEU");
		aminoAcidMap.put("CUU", "LEU");	aminoAcidMap.put("CUC", "LEU");	aminoAcidMap.put("CUA", "LEU"); aminoAcidMap.put("CUG", "LEU");
		aminoAcidMap.put("AUU", "ILE");	aminoAcidMap.put("AUC", "ILE");	aminoAcidMap.put("AUA", "ILE"); aminoAcidMap.put("AUG", "MET");
		aminoAcidMap.put("GUU", "VAL");	aminoAcidMap.put("GUC", "VAL");	aminoAcidMap.put("GUA", "VAL"); aminoAcidMap.put("GUG", "VAL");
		aminoAcidMap.put("UCU", "SER");	aminoAcidMap.put("UCC", "SER");	aminoAcidMap.put("UCA", "SER"); aminoAcidMap.put("UCG", "SER");	
		aminoAcidMap.put("CCU", "PRO");	aminoAcidMap.put("CCC", "PRO");	aminoAcidMap.put("CCA", "PRO"); aminoAcidMap.put("CCG", "PRO");
		aminoAcidMap.put("ACU", "THR");	aminoAcidMap.put("ACC", "THR");	aminoAcidMap.put("ACA", "THR"); aminoAcidMap.put("ACG", "THR");	
		aminoAcidMap.put("GCU", "ALA");	aminoAcidMap.put("GCC", "ALA");	aminoAcidMap.put("GCA", "ALA"); aminoAcidMap.put("GCG", "ALA");	
		aminoAcidMap.put("UAU", "TYR");	aminoAcidMap.put("UAC", "TYR");	aminoAcidMap.put("UAA", "STOP"); aminoAcidMap.put("UAG", "STOP");
		aminoAcidMap.put("CAU", "HIS");	aminoAcidMap.put("CAC", "HIS");	aminoAcidMap.put("CAA", "GLN"); aminoAcidMap.put("CAG", "GLN");
		aminoAcidMap.put("AAU", "ASN");	aminoAcidMap.put("AAC", "ASN");	aminoAcidMap.put("AAA", "LYS"); aminoAcidMap.put("AAG", "LYS");
		aminoAcidMap.put("GAU", "ASP");	aminoAcidMap.put("GAC", "ASP");	aminoAcidMap.put("GAA", "GLU"); aminoAcidMap.put("GAG", "GLU");
		aminoAcidMap.put("UGU", "CYS");	aminoAcidMap.put("UGC", "CYS");	aminoAcidMap.put("UGA", "STOP"); aminoAcidMap.put("UGG", "TRP");
		aminoAcidMap.put("CGU", "ARG");	aminoAcidMap.put("CGC", "ARG");	aminoAcidMap.put("CGA", "ARG"); aminoAcidMap.put("CGG", "ARG");
		aminoAcidMap.put("AGU", "SER");	aminoAcidMap.put("AGC", "SER");	aminoAcidMap.put("AGA", "ARG"); aminoAcidMap.put("AGG", "ARG");
		aminoAcidMap.put("GGU", "GLY");	aminoAcidMap.put("GGC", "GLY");	aminoAcidMap.put("GGA", "GLY"); aminoAcidMap.put("GGG", "GLY");

		// Find and return aminoAcid.
		String aminoAcid = (String) aminoAcidMap.get(codon);
		return aminoAcid;	
	}

	private String getAntiCodon(String codon) { //** Match the anti-codon to codon.

		// Create map entries (K, V)
		antiCodonMap.put("UUU", "AAA");	antiCodonMap.put("UUC", "AAG");	antiCodonMap.put("UUA", "AAU"); antiCodonMap.put("UUG", "AAC");
		antiCodonMap.put("CUU", "GAA");	antiCodonMap.put("CUC", "GAG");	antiCodonMap.put("CUA", "GAU"); antiCodonMap.put("CUG", "GAC");
		antiCodonMap.put("AUU", "UAA");	antiCodonMap.put("AUC", "UAG");	antiCodonMap.put("AUA", "UAU"); antiCodonMap.put("AUG", "UAC");
		antiCodonMap.put("GUU", "CAA");	antiCodonMap.put("GUC", "CAG");	antiCodonMap.put("GUA", "CAU"); antiCodonMap.put("GUG", "CAC");
		antiCodonMap.put("UCU", "AGA");	antiCodonMap.put("UCC", "AGG");	antiCodonMap.put("UCA", "AGU"); antiCodonMap.put("UCG", "AGC");	
		antiCodonMap.put("CCU", "GGA");	antiCodonMap.put("CCC", "GGG");	antiCodonMap.put("CCA", "GGU"); antiCodonMap.put("CCG", "GGC");
		antiCodonMap.put("ACU", "UGA");	antiCodonMap.put("ACC", "UGG");	antiCodonMap.put("ACA", "UGU"); antiCodonMap.put("ACG", "UGC");	
		antiCodonMap.put("GCU", "CGA");	antiCodonMap.put("GCC", "CGG");	antiCodonMap.put("GCA", "CGU"); antiCodonMap.put("GCG", "CGC");	
		antiCodonMap.put("UAU", "AUA");	antiCodonMap.put("UAC", "AUG");	antiCodonMap.put("UAA", "AUU"); antiCodonMap.put("UAG", "AUC");
		antiCodonMap.put("CAU", "GUA");	antiCodonMap.put("CAC", "GUG");	antiCodonMap.put("CAA", "GUU"); antiCodonMap.put("CAG", "GUC");
		antiCodonMap.put("AAU", "UUA");	antiCodonMap.put("AAC", "UUG");	antiCodonMap.put("AAA", "UUU"); antiCodonMap.put("AAG", "UUC");
		antiCodonMap.put("GAU", "CUA");	antiCodonMap.put("GAC", "CUG");	antiCodonMap.put("GAA", "CUU"); antiCodonMap.put("GAG", "CUC");
		antiCodonMap.put("UGU", "ACA");	antiCodonMap.put("UGC", "ACG");	antiCodonMap.put("UGA", "ACU"); antiCodonMap.put("UGG", "ACC");
		antiCodonMap.put("CGU", "GCA");	antiCodonMap.put("CGC", "GCG");	antiCodonMap.put("CGA", "GCU"); antiCodonMap.put("CGG", "GCC");
		antiCodonMap.put("AGU", "UCA");	antiCodonMap.put("AGC", "UCG");	antiCodonMap.put("AGA", "UCU"); antiCodonMap.put("AGG", "UCC");
		antiCodonMap.put("GGU", "CCA");	antiCodonMap.put("GGC", "CCG");	antiCodonMap.put("GGA", "CCU"); antiCodonMap.put("GGG", "CCC");

		// Find and return aminoAcid.
		String antiCodon = (String) antiCodonMap.get(codon);
		return antiCodon;
	}

	@FXML
	private void newLetterGrid() { //* New random buttons on letter gridPane.
		if ((Integer.parseInt(lblEnergy.getText())) > 0) {
			letterGrid.getChildren().clear(); // Remove old buttons.

			// Initialize counts to be relatively equal. (7+6+6+6 = 25)
			int countA = 0, countU = 0, countG = 0, countC = 0; 
			int n = (int) (Math.random() * 4);
			if (n == 0) { // Case: A = 7
				countA = 7; countU = 6; countG = 6; countC = 6;
			}
			if (n == 1) { // Case: U = 7
				countA = 6; countU = 7; countG = 6; countC = 6;
			}
			if (n == 2) { // Case: G = 7
				countA = 6; countU = 6; countG = 7; countC = 6;
			}
			if (n == 3) { // Case: C = 7
				countA = 6; countU = 6; countG = 6; countC = 7;
			}

			// Assign new buttons to grid.
			for (int i = 1; i < 6; i++) {
				for (int j = 1; j < 6; j++) {
					int randomInt = (int) (Math.random() * 4);
					if (randomInt == 0 && countA > 0) { 
						letterGrid.add(newGridButtonA(), i, j);
						countA--;
					}
					else if (randomInt == 1 && countU > 0) {
						letterGrid.add(newGridButtonU(), i, j);
						countU--;
					}
					else if (randomInt == 2 && countG > 0) {
						letterGrid.add(newGridButtonG(), i, j);
						countG--;
					}
					else if (randomInt == 3 && countC > 0) {
						letterGrid.add(newGridButtonC(), i, j);
						countC--;
					}
					else {
						if (countA > 0) {
							letterGrid.add(newGridButtonA(), i, j);
							countA--;
						}
						else if (countU > 0) {
							letterGrid.add(newGridButtonU(), i, j);
							countU--;
						}
						else if (countG > 0) {
							letterGrid.add(newGridButtonG(), i, j);
							countG--;
						}
						else if (countC > 0) {
							letterGrid.add(newGridButtonC(), i, j);
							countC--;
						}
					}
				}
			}
		}
	}

	@FXML
	private void shufflePressed() { //** Shuffle new buttons on letter gridPane.
		// New letterGrid.
		newLetterGrid();

		// Remove one energy.
		if (!(lblEnergy.getText().equals(""))) {
			removeEnergy();
		}
		// Clear anticodon buttons & labels.
		acPressed();	
	}

	@FXML
	private void acPressed() { //** Reset anticodon buttons & labels.

		// Reset button glow to innershadow.
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setColor(Color.rgb(184, 178, 89));
		if (buttonList.size() > 0) { // Reset button at index 0.
			Button button1 = buttonList.get(0); 
			button1.setEffect(innerShadow);
			if (buttonList.size() > 1) { // Reset button at index 1.
				Button button2 = buttonList.get(1); 
				button2.setEffect(innerShadow);
				if (buttonList.size() > 2) {  // Reset button at index 2.
					Button button3 = buttonList.get(2);
					button3.setEffect(innerShadow);
				}
			}
		}
		// Reset ribosome color.
		normalRibosome();

		// Clear lists.
		buttonsPressed.clear();
		buttonList.clear();	
		indexListX.clear();
		indexListY.clear();
		btAnticodon.setText(" ");
	}

	@FXML
	private boolean checkMatch(String playerAntiCodon) { //** If match found, return true.

		// Create default boolean & codon string.
		boolean match = false;
		StringBuilder s = new StringBuilder();

		// Get codon from arrayList (indices 15, 16, 17).
		for (int i = 15; i < 18; i++) {
			Rectangle b = mRNAbases.get(i); // Get bases from mRNA list.

			// Assign base letter to codon depending on base color.
			if (b.getFill().equals(Color.web("0x11970cff"))) { // If green, assign A.
				s.append("A");
			}
			if (b.getFill().equals(Color.web("0x940606ff"))) { // If red, assign U.
				s.append("U");
			}
			if (b.getFill().equals(Color.web("0xb99400ff"))) { // If yellow, assign C.
				s.append("C");
			}
			if (b.getFill().equals(Color.web("0xae5100ff"))) { // If orange, assign G.
				s.append("G");
			}
		}
		// Get antiCodon from codon.
		String codon = s.toString();
		String antiCodon = getAntiCodon(codon);

		// Compare playerAntiCodon vs antiCodon.
		if (playerAntiCodon.equals(antiCodon)) {
			match = true; // If same, it's a match.
		}
		return match; 
	}

	@FXML
	private Node newGridButton() { //** Create new letterGrid button (Random).
		// New button.
		Button button = new Button(); 

		// Set button look and feel.
		button.setPrefWidth(35);
		button.setPrefHeight(35);
		button.setMinSize(35, 35);
		button.setStyle("-fx-background-color: #fffce1");
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setColor(Color.rgb(184, 178, 89));
		button.setEffect(innerShadow);
		button.setFont(Font.font ("Ayuthaya", 28));
		button.setTextAlignment(TextAlignment.CENTER);
		button.setWrapText(true);
		button.setAlignment(Pos.CENTER);
		button.setContentDisplay(ContentDisplay.CENTER);
		button.setPadding(new Insets(0, 2, 0, 2)); // (top/right/bottom/left)

		// Set up onAction event.
		button.setOnAction(e -> gridPressed(e)); 

		// Assign base letter & color.
		int randomInt = (int) (Math.random() * 4);
		if (randomInt == 0) {
			button.setText("A");
			button.setTextFill(Color.rgb(4, 121, 0));
		}
		else if (randomInt == 1) {
			button.setText("U");
			button.setTextFill(Color.rgb(124, 0, 0));
		}
		else if (randomInt == 2) {
			button.setText("G");
			button.setTextFill(Color.rgb(174, 81, 0));
		}
		else if (randomInt == 3) {
			button.setText("C");
			button.setTextFill(Color.rgb(185, 148, 0));
		}
		return button;
	}

	@FXML
	private Node newGridButtonA() { //** Create new letterGrid button (A).
		// New button.
		Button button = new Button(); 

		// Set button look and feel.
		button.setPrefWidth(35);
		button.setPrefHeight(35);
		button.setMinSize(35, 35);
		button.setStyle("-fx-background-color: #fffce1");
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setColor(Color.rgb(184, 178, 89));
		button.setEffect(innerShadow);
		button.setFont(Font.font ("Ayuthaya", 28));
		button.setTextAlignment(TextAlignment.CENTER);
		button.setWrapText(true);
		button.setAlignment(Pos.CENTER);
		button.setContentDisplay(ContentDisplay.CENTER);
		button.setPadding(new Insets(0, 2, 0, 2)); // (top/right/bottom/left)

		// Set up onAction event.
		button.setOnAction(e -> gridPressed(e)); 

		// Assign base letter & color.
		//int randomInt = (int) (Math.random() * 4);
		button.setText("A");
		button.setTextFill(Color.rgb(4, 121, 0));

		return button;
	}

	@FXML
	private Node newGridButtonU() { //** Create new letterGrid button (U).
		// New button.
		Button button = new Button(); 

		// Set button look and feel.
		button.setPrefWidth(35);
		button.setPrefHeight(35);
		button.setMinSize(35, 35);
		button.setStyle("-fx-background-color: #fffce1");
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setColor(Color.rgb(184, 178, 89));
		button.setEffect(innerShadow);
		button.setFont(Font.font ("Ayuthaya", 28));
		button.setTextAlignment(TextAlignment.CENTER);
		button.setWrapText(true);
		button.setAlignment(Pos.CENTER);
		button.setContentDisplay(ContentDisplay.CENTER);
		button.setPadding(new Insets(0, 2, 0, 2)); // (top/right/bottom/left)

		// Set up onAction event.
		button.setOnAction(e -> gridPressed(e)); 

		// Assign base letter & color.
		button.setText("U");
		button.setTextFill(Color.rgb(124, 0, 0));

		return button;
	}

	@FXML
	private Node newGridButtonG() { //** Create new letterGrid button (G).
		// New button.
		Button button = new Button(); 

		// Set button look and feel.
		button.setPrefWidth(35);
		button.setPrefHeight(35);
		button.setMinSize(35, 35);
		button.setStyle("-fx-background-color: #fffce1");
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setColor(Color.rgb(184, 178, 89));
		button.setEffect(innerShadow);
		button.setFont(Font.font ("Ayuthaya", 28));
		button.setTextAlignment(TextAlignment.CENTER);
		button.setWrapText(true);
		button.setAlignment(Pos.CENTER);
		button.setContentDisplay(ContentDisplay.CENTER);
		button.setPadding(new Insets(0, 2, 0, 2)); // (top/right/bottom/left)

		// Set up onAction event.
		button.setOnAction(e -> gridPressed(e)); 

		// Assign base letter & color .
		button.setText("G");
		button.setTextFill(Color.rgb(174, 81, 0));

		return button;
	}

	@FXML
	private Node newGridButtonC() { //** Create new letterGrid button (C).
		// New button.
		Button button = new Button(); 

		// Set button look and feel.
		button.setPrefWidth(35);
		button.setPrefHeight(35);
		button.setMinSize(35, 35);
		button.setStyle("-fx-background-color: #fffce1");
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setColor(Color.rgb(184, 178, 89));
		button.setEffect(innerShadow);
		button.setFont(Font.font ("Ayuthaya", 28));
		button.setTextAlignment(TextAlignment.CENTER);
		button.setWrapText(true);
		button.setAlignment(Pos.CENTER);
		button.setContentDisplay(ContentDisplay.CENTER);
		button.setPadding(new Insets(0, 2, 0, 2)); // (top/right/bottom/left)

		// Set up onAction event.
		button.setOnAction(e -> gridPressed(e)); 

		// Assign base letter & color.
		button.setText("C");
		button.setTextFill(Color.rgb(185, 148, 0));

		return button;
	}

	@FXML
	private Rectangle newBase() { //** Add new base to mRNA.
		Rectangle base = new Rectangle(9, 35); // New rectangle.
		base.setStroke(Color.TRANSPARENT);
		base.setStrokeWidth(3);
		base.setStrokeType(StrokeType.OUTSIDE);
		base.setArcWidth(10);
		base.setArcHeight(10);
		base.setSmooth(true);

		// Random base color assigned.
		int randomInt = (int) (Math.random() * 4);

		if (randomInt == 0) { // Green
			base.setFill(Color.rgb(17, 151, 12));
		}
		else if (randomInt == 1) { // Red
			base.setFill(Color.rgb(148, 6, 6));
		}
		else if (randomInt == 2) { // Orange
			base.setFill(Color.rgb(174, 81, 0));
		}
		else if (randomInt == 3) { // Yellow
			base.setFill(Color.rgb(185, 148, 0));
		}

		// Return new base.
		return base;
	}

	@FXML 
	private Rectangle newTrna() { //** Create new tRNA with anticodon to correspond with new codon.

		// New tRNA object: assign random color.
		Rectangle rectangle = new Rectangle(55, 24);
		int randomInt = (int) Math.random() * 4;
		if (randomInt == 0) { // Green
			rectangle.setFill(Color.rgb(4, 121, 0));
			rectangle.setStroke(Color.rgb(159, 211, 159));
		}
		else if (randomInt == 1) { // Red
			rectangle.setFill(Color.rgb(255, 139, 139));
			rectangle.setStroke(Color.rgb(124, 0, 0));
		}
		else if (randomInt == 2) { // Orange
			rectangle.setFill(Color.rgb(238, 161, 98));
			rectangle.setStroke(Color.rgb(159, 211, 159));
		}
		else if (randomInt == 3) { // Yellow
			rectangle.setFill(Color.rgb(231, 206, 105));
			rectangle.setStroke(Color.rgb(159, 211, 159));
		}
		// Return new tRNA.
		return rectangle;
	}

	@FXML
	private Circle newAminoAcid(String codon) { //** Return circle for amino acid.
		Circle aaCircle = new Circle(7);
		String aaName = getAminoAcid(codon); // Get correct amino acid name.

		// Set circle look.
		aaCircle.setStroke(Color.TRANSPARENT);
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setColor(Color.rgb(137, 137, 137));
		aaCircle.setEffect(innerShadow);

		if (aaName.equals("PHE")){ // PHENYLALANINE
			aaCircle.setFill(Color.rgb(180, 134, 194));
		}
		if (aaName.equals("LEU")) { // LEUCINE 
			aaCircle.setFill(Color.rgb(147, 134, 194));
		}
		if (aaName.equals("ILE")) { // ISOLEUCINE 
			aaCircle.setFill(Color.rgb(134, 149, 194));
		}
		if (aaName.equals("MET")) { // METHIONINE 
			aaCircle.setFill(Color.rgb(134, 174, 194));
		}
		if (aaName.equals("VAL")) { // VALINE 
			aaCircle.setFill(Color.rgb(134, 194, 192));
		}
		if (aaName.equals("SER")) { // SERINE 
			aaCircle.setFill(Color.rgb(158, 79, 120));
		}
		if (aaName.equals("PRO")) { // PROLINE 
			aaCircle.setFill(Color.rgb(124, 90, 184));
		}
		if (aaName.equals("THR")) { // THREONINE 
			aaCircle.setFill(Color.rgb(71, 114, 173));
		}
		if (aaName.equals("ALA")) { // ALANINE 
			aaCircle.setFill(Color.rgb(134, 194, 162));
		}
		if (aaName.equals("TYR")) { // TYROSINE 
			aaCircle.setFill(Color.rgb(237, 157, 181));
		}
		if (aaName.equals("HIS")) { // HISTIDINE 
			aaCircle.setFill(Color.rgb(189, 119, 126));
		}
		if (aaName.equals("GLN")) { // GLUTAMINE 
			aaCircle.setFill(Color.rgb(166, 73, 73));
		}
		if (aaName.equals("ASN")) { // ASPARAGINE 
			aaCircle.setFill(Color.rgb(204, 141, 114));
		}
		if (aaName.equals("LYS")) { // LYSINE 
			aaCircle.setFill(Color.rgb(194, 165, 103));
		}
		if (aaName.equals("ASP")) { // ASPARTIC ACID 
			aaCircle.setFill(Color.rgb(181, 181, 74));
		}
		if (aaName.equals("GLU")) { // GLUTAMIC ACID 	
			aaCircle.setFill(Color.rgb(196, 214, 114));
		}
		if (aaName.equals("CYS")) { // CYSTEINE 
			aaCircle.setFill(Color.rgb(222, 112, 100));
		}
		if (aaName.equals("TRP")) { // TRYPTOPHAN 
			aaCircle.setFill(Color.rgb(232, 138, 97));
		}
		if (aaName.equals("ARG")) { // ARGININE 
			aaCircle.setFill(Color.rgb(179, 114, 77));
		}
		if (aaName.equals("GLY")) { // GLYCINE 
			aaCircle.setFill(Color.rgb(237, 233, 119));
		}
		if (aaName.equals("STOP")) {
			aaCircle.setFill(Color.WHITE);
		}
		return aaCircle;
	}

	@FXML 
	private void removeBase() {  //** Remove base from mRNA.
		mRNAbases.remove(0);
		mRNAbox.getChildren().remove(0);
	}

	@FXML
	private Glow glow(boolean glow) { //** When mRNA base are in certain indices, make them glow.
		// Instantiating the Glow class. 
		Glow g = new Glow();

		if (glow) { // Set to glow.
			g.setLevel(0.9);
		}
		if (!glow){ // Set to not glow.
			g.setLevel(0.0);
		}
		return g;
	}

	@FXML
	private void addEnergy() { //** Add one to Energy.
		Rectangle e = new Rectangle(61, 20);
		e.setFill(Color.web("0xe1d557ff"));
		e.setStroke(Color.web("0x908736ff"));

		int n = Integer.parseInt(lblEnergy.getText()); // Get Energy number.

		if (n < 5) { // If energy bar not full, add 1.
			energyGrid.getChildren().add(e); // Add energy.
			n++;
			lblEnergy.setText(Integer.toString(n)); // Set new text.
		}
	}

	@FXML 
	private void removeEnergy() { //** Remove one to Energy.
		int n = Integer.parseInt(lblEnergy.getText()); // Get Energy number.

		if (n > 0) { // If at least one energy in grid, remove 1.
			energyGrid.getChildren().remove(n-1);
			n--;
			lblEnergy.setText(Integer.toString(n)); // Set new text.
		}
	}

	@FXML 
	private void addLength() { //** Add one to Length number.
		int n = (int) Integer.parseInt(lblLength.getText());
		n++;
		lblLength.setText(Integer.toString(n)); // Set new number.
	}

	@FXML
	private void wrongAnticodon() { //** Change Ribosome color to red.
		ribozyme1.setFill(Color.valueOf("#a74848"));
		ribozyme1.setStroke(Color.valueOf("#7c0000"));
		ribozyme2.setFill(Color.valueOf("#e16262"));
		ribozyme2.setStroke(Color.valueOf("#7c0000"));
		ribozyme3.setFill(Color.valueOf("#e16262"));
		ribozyme3.setStroke(Color.valueOf("#7c0000"));
		ribozyme4.setFill(Color.valueOf("#e16262"));
		ribozyme4.setStroke(Color.valueOf("#7c0000"));
		ribozyme5.setFill(Color.valueOf("#e16262"));
		ribozyme5.setStroke(Color.valueOf("#7c0000"));
		lblRibosome.setOpacity(0.0);
	}

	@FXML
	private void correctAnticodon() { //** Change Ribosome color to green.
		ribozyme1.setFill(Color.valueOf("#a4d3a1"));
		ribozyme1.setStroke(Color.valueOf("#657864"));
		ribozyme2.setFill(Color.valueOf("#d4ffd0"));
		ribozyme2.setStroke(Color.valueOf("#657864"));
		ribozyme3.setFill(Color.valueOf("#d4ffd0"));
		ribozyme3.setStroke(Color.valueOf("#657864"));
		ribozyme4.setFill(Color.valueOf("#d4ffd0"));
		ribozyme4.setStroke(Color.valueOf("#657864"));
		ribozyme5.setFill(Color.valueOf("#d4ffd0"));
		ribozyme5.setStroke(Color.valueOf("#657864"));
		lblRibosome.setOpacity(0.0);
	}

	@FXML
	private void normalRibosome() { //** Reset ribosome color to normal.
		ribozyme1.setFill(Color.valueOf("#e1dc9e"));
		ribozyme1.setStroke(Color.valueOf("#97946e"));
		ribozyme2.setFill(Color.valueOf("#fffcc7"));
		ribozyme2.setStroke(Color.valueOf("#97946e"));
		ribozyme3.setFill(Color.valueOf("#fffcc7"));
		ribozyme3.setStroke(Color.valueOf("#97946e"));
		ribozyme4.setFill(Color.valueOf("#fffcc7"));
		ribozyme4.setStroke(Color.valueOf("#97946e"));
		ribozyme5.setFill(Color.valueOf("#fffcc7"));
		ribozyme5.setStroke(Color.valueOf("#97946e"));
		lblRibosome.setOpacity(1.0);
	}

	@FXML
	private void addToChain(Circle aaCircle) { //** Add amino acid to chain Vbox.
		// Put aaCircle into aaList.
		aaList.add(aaCircle);
		chainBox.getChildren().add(aaCircle);
	}

	@FXML
	private String countdown(String s) { //** Countdown in seconds. Returns new second string.
		// Convert string to int.
		int second = (Integer.parseInt(s)); 

		if (second == 0) { // Stop gameplay.
			gameEnd();
		}
		else { // Continue countdown by removing a second.
			second--;
		}
		// Convert second int back to string & return.
		String newSecond = Integer.toString(second);
		return newSecond;
	}

	@FXML
	private void gameEnd() { //** Events at end of game.
		// Stop animation.
		animation.stop();

		// Clear letterGrid & show end pop-up.
		letterGrid.getChildren().clear();
		circleEnd.setOpacity(1.0);
		lblEnd1.setOpacity(1.0);
		lblEnd2.setOpacity(1.0);
		lblEndLength.setOpacity(1.0);
		String length = " " + lblLength.getText();
		lblEndLength.setText(length); // Show final length.

		// Reset game to start.
		lblLength.setText(" ");
		lblEnergy.setText(" ");
		mRNAbackbone.setOpacity(0.0);
		lblMRNA.setOpacity(0.0);
		lblCodon.setOpacity(0.0);
		normalRibosome();
		energyGrid.getChildren().clear();
		btAnticodon.setOpacity(0.0);
		btAnticodon.setText(" ");
		lblAnticodon.setOpacity(0.0);
		btShuffle.setOpacity(0.0);
		mRNAbox.getChildren().clear();
		mRNAbases.clear();

		// Add new start button.
		Button newStart = new Button("START");
		newStart.setFont(Font.font("Ayuthaya", 27));
		newStart.setTextFill(Color.valueOf("#074004"));
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setColor(Color.valueOf("#044300"));
		newStart.setEffect(innerShadow);
		newStart.setStyle("-fx-background-color: #289622");
		newStart.setPrefWidth(120);
		newStart.setPrefHeight(80);
		newStart.setLayoutX(104.0);
		newStart.setLayoutY(55.0);
		bottomPane.getChildren().add(newStart);
		bottomPane.setLeftAnchor(newStart, 105.0);
		bottomPane.setRightAnchor(newStart, 105.0);

		// If game started, remove new start button & restart game.
		newStart.setOnAction(e -> {
			bottomPane.getChildren().remove(newStart);
			start(e);
		});
	}

}


