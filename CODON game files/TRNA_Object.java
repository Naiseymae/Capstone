
/* 
 * Author: Renee Linford
 * Date: 1-31-20
 * Capstone: "Codon" game
 * tRNA class builds and returns a tRNA object in a VBox.
 */

import java.util.*;

import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class TRNA_Object extends VBox {

	private double width = 56;
	private double height = 24;
	private Color green = Color.rgb(17, 151, 12);
	private Color red = Color.rgb(148, 6, 6);
	private Color yellow = Color.rgb(185, 148, 0);
	private Color orange = Color.rgb(174, 81, 0);
	private ArrayList<String> baseList = new ArrayList<String>();
	private String b1; 
	private String b2; 
	private String b3; 
	
	public TRNA_Object() {  // No-args constructor.
	}
	
	public TRNA_Object(Rectangle base1, Rectangle base2, Rectangle base3) { // Constructor using given codon.

		this.b1 = getBaseLetter(base1.getFill().toString());
		this.b2 = getBaseLetter(base2.getFill().toString());
		this.b3 = getBaseLetter(base3.getFill().toString());
		
		// Add to base arrayList
		this.baseList.add(b1);
		this.baseList.add(b2);
		this.baseList.add(b3);

		this.draw();
	}

	private String getBaseLetter(String base) { // Use rectangle color to get base letter.
		String letter = "";
		
		// Assign base letter to codon depending on base color.
		if (base.equals("0x11970cff")) { // If green (A), assign red (U).
			letter = letter.concat("U");
		}
		else if (base.equals("0x940606ff")) { // If red (U), assign green (A).
			letter = letter.concat("A");
		}
		else if (base.equals("0xb99400ff")) { // If yellow (C), assign orange (G).
			letter = letter.concat("G");
		}
		else if (base.equals("0xae5100ff")) { // If orange (G), assign yellow (C).
			letter = letter.concat("C");
		}
		return letter;
	}

	private Pane draw() { // Draw tRNA
		
		// HBox to hold tRNA & bases.
		HBox box = new HBox();
		Rectangle tRNA = new Rectangle(width, height);
		tRNA.setStrokeWidth(0.5);
		tRNA.setSmooth(true);
		tRNA.setArcWidth(5);
		tRNA.setArcHeight(5);
		
		// Create random rectangle color.
		int randomInt = (int) Math.random() * 4;
		if (randomInt == 0) { // Green
			tRNA.setFill(Color.rgb(122, 171, 120));
			tRNA.setStroke(Color.rgb(68,127, 63));
		}
		else if (randomInt == 1) { // Red
			tRNA.setFill(Color.rgb(224, 156, 156));
			tRNA.setStroke(Color.rgb(140, 95, 95));
		}
		else if (randomInt == 2) { // Orange
			tRNA.setFill(Color.rgb(248, 188, 139));
			tRNA.setStroke(Color.rgb(177, 131, 95));
		}
		else if (randomInt == 3) { // Yellow
			tRNA.setFill(Color.rgb(221, 200, 115));
			tRNA.setStroke(Color.rgb(121, 111, 70));
		}
		
		// Add 3 bases w/spacers to HBox (anticodon).
		Rectangle spacer1 = new Rectangle(8, 35, Color.TRANSPARENT);
		spacer1.setStroke(Color.TRANSPARENT);
		box.getChildren().add(spacer1); // Add spacer to HBox.
		
		// Loop to add bases & spacers.
		for (int i = 0; i < 3; i++) {
			String b = baseList.get(i); // Get base letter.
			Rectangle base = new Rectangle(9, 35); // Make base rectangle.
			base.setStroke(Color.TRANSPARENT);
			base.setStrokeWidth(0);
			base.setStrokeType(StrokeType.OUTSIDE);
			base.setArcWidth(10);
			base.setArcHeight(10);
			base.setSmooth(true);
			
			Rectangle spacer2 = new Rectangle(5, 35, Color.TRANSPARENT); // Make base spacer.
			spacer2.setStroke(Color.TRANSPARENT);
			
			if (b.equals("A")) { // If codon base == A, anticodon base = U.
				base.setFill(green);
			}
			else if (b.equals("U")) { // If codon base == U, anticodon base = A.
				base.setFill(red);
			}
			else if (b.equals("G")) { // If codon base == G, anticodon base = C.
				base.setFill(orange);
			}
			else if (b.equals("C")) { // If codon base == C, anticodon base = G.
				base.setFill(yellow);
			}
			box.getChildren().add(base); // Add anticodon base to HBox.
			box.getChildren().add(spacer2); // Add spacer to HBox.
		}	
		// Add tRNA & base box to Pane.
		this.getChildren().add(tRNA);
		this.getChildren().add(box);
		return this;
	}
}
