# Capstone - Software Development
CODON: It's base-ic science!

## Synopsis
This capstone project is a game called "CODON".  It reinforces concepts about the translation process in cell biology.

A player builds the longest amino acid chain they can in the given amount of time.  Inside a ribosome complex, there is an mRNA strand with nucleotide bases A, C, U, and G.  On the letter grid below, the player earns amino acids by finding the "anti-codon" match to the "codon" on the mRNA strand.  The base "A" always pairs with "U", and the base "C" pairs with "G".  The challenge of finding the matching "anti-codon" requires the player to think in reverse.  If the codon is "AGU", then the player must find the anti-codon "UCA" in the letter grid.  If there are no matches in the current letter grid, the player may use one energy to shuffle the letters.  One energy can be earned for every 5 amino acids the player collects.  

## Motivation
This game encourages a casual and fun learning experience while reinforcing concepts about the translational process in cell biology.  I believe science and educational games can and should be fun and engaging, and such an approach brings better learning outcomes (see contribution section below for download).  

## How to Play
OBJECTIVE: 
Collect as many amino acids to build the longest polypeptide chain as possible in the given amount of time.

START:
When start button is clicked, the game will display a ribosome complex with a mRNA strand of random bases (A, C, G, & U). The 3 bases labeled "CODON" glow to indicate the 3 bases the player needs to pair (i.e., if the codon is AUG, then the player needs to find letters UAC in the letter grid.)  The player starts with 5 energy and 99 seconds.

GAMEPLAY:
The bottom half of the screen displays a letter grid where the player will select their anti-codon in a "bejeweled" or "boggle" type fashion.  When a player selects 3 base letters that pair correctly with the codon, a new tRNA enters with an amino acid.  That amino acid will be added to the polypeptide chain, adding one to the player's chain length.  Below the letter grid is a visual reminder that base "A" pairs with "U" and base "G" pairs with "C". 

CLEAR:
If a player selects an letter by mistake, click the "ANTI-CODON" button to clear their anti-codon letters.  

SHUFFLE:
If a player cannot find a match in the grid, click the "SHUFFLE" button to refresh the letter grid with new letters.  This costs one energy. 

ENERGY:
The player is given 5 energy to start the game.  This energy can be used to shuffle the letter grid when necessary.  A player can earn one energy for every 5 amino acids they collect. 

<figure><figcaption>CODON gameplay</><img src="Game Play example 1.png" alt="CODON gameplay" /> </figure>


## Code Example 1
This method is called when a player selects a correct anti-codon match.  It brings in a new tRNA with the player's anti-codon, adds the amino acid to the polypeptide chain, and moves the previous tRNA out of the ribosome complex.

```
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

```

## Code Example 2
This method creates a new random letter grid. The letters are randomized, but also generated to be about equal in number so that there are not too many of any one letter.

```
@FXML
	private void newLetterGrid() { //** New random buttons on letter gridPane.
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
  
  ```


## Contributors
Below is a link to download and play the game.  
It can be played on a mac or PC computer.  I will be updating and improving this game from time to time, and I am happy to receive feedback.  Since this game is for science education and casual gameplay, please play and share. 

Please do not copy or sell this work. 
