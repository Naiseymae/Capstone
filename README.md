# Capstone - Software Development
CODON: It's base-ic science!

## Synopsis
This capstone project is a game called "CODON". A player tries to build the longest amino acid chain they can in the given amount of time.  Inside a ribosome complex, there is an mRNA strand with nucleotide bases A, C, U, and G.  On the letter grid below, the player earns amino acids by finding the "anti-codon" match to the "codon" on the mRNA strand.  The base "A" always pairs with "U", and the base "C" pairs with "G".  The challenge of finding the matching "anti-codon" requires the player to think in reverse.  If the codon is "AGU", then the player must find the anti-codon "UCA" in the letter grid.  If there are no matches in the current letter grid, the player may use one energy to shuffle the letters.  One energy can be earned with every 5 amino acids the player collects.  

## Motivation
This game encourages a casual and fun learning experience while reinforcing concepts about the translational process in cell biology.  I believe science and educational games can and should be fun and engaging, as such an approach bring better learning outcomes.  

## How to Play
OBJECTIVE: 
Collect as many amino acids to build the longest polypeptide chain as possible in the given time.

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

[CODON gameplay] <img src="Game Play example 1.png"/>

## Code Example
Show a small snippet of the code you are proud of and why.
```
Insert Code Here
```

## Contributors
Let people know how they can dive into the project, include important links to things like issue trackers, irc, twitter accounts if applicable.
Also list any one who has already contributed to the project.
