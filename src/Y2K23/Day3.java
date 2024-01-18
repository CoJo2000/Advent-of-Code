package Y2K23;
/*
 * 	
 	--- Day 3: Gear Ratios ---
	You and the Elf eventually reach a gondola lift station; he says the gondola 
	lift will take you up to the water source, but this is as far as he can bring you. 
	You go inside.
	
	It doesn't take long to find the gondolas, but there seems to be a problem: 
	they're not moving.
	
	"Aaah!"
	
	You turn around to see a slightly-greasy Elf with a wrench and a look of surprise. 
	"Sorry, I wasn't expecting anyone! The gondola lift isn't working right now; it'll 
	still be a while before I can fix it." You offer to help.
	
	The engineer explains that an engine part seems to be missing from the engine, but 
	nobody can figure out which one. If you can add up all the part numbers in the engine 
	schematic, it should be easy to work out which part is missing.
	
	The engine schematic (your puzzle input) consists of a visual representation of the 
	engine. There are lots of numbers and symbols you don't really understand, but apparently 
	any number adjacent to a symbol, even diagonally, is a "part number" and should be 
	included in your sum. (Periods (.) do not count as a symbol.)
	
	Here is an example engine schematic:
	
	467..114..
	...*......
	..35..633.
	......#...
	617*......
	.....+.58.
	..592.....
	......755.
	...$.*....
	.664.598..
	In this schematic, two numbers are not part numbers because they are not adjacent 
	to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent 
	to a symbol and so is a part number; their sum is 4361.
	
	Of course, the actual engine schematic is much larger. What is the sum of all of the 
	part numbers in the engine schematic?
	
	--- Part Two ---
	The engineer finds the missing part and installs it in the engine! As the engine 
	springs to life, you jump in the closest gondola, finally ready to ascend to the 
	water source.
	
	You don't seem to be going very fast, though. Maybe something is still wrong? 
	Fortunately, the gondola has a phone labeled "help", so you pick it up and the 
	engineer answers.
	
	Before you can explain the situation, she suggests that you look out the window. 
	There stands the engineer, holding a phone in one hand and waving with the other. 
	You're going so slowly that you haven't even left the station. You exit the gondola.
	
	The missing part wasn't the only issue - one of the gears in the engine is wrong. 
	A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio
	is the result of multiplying those two numbers together.
	
	This time, you need to find the gear ratio of every gear and add them all 
	up so that the engineer can figure out which gear needs to be replaced.
	
	Consider the same engine schematic again:
	
	467..114..
	...*......
	..35..633.
	......#...
	617*......
	.....+.58.
	..592.....
	......755.
	...$.*....
	.664.598..
	In this schematic, there are two gears. The first is in the top left; it has 
	part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the 
	lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear 
	because it is only adjacent to one part number.) Adding up all of the gear ratios 
	produces 467835.
	
	What is the sum of all of the gear ratios in your engine schematic?
	
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Start Day: January 17th, 2023
//End Day  : January 18th, 2023


public class Day3 {
	public static void main (String [] args) {
		System.out.println(part1());
		System.out.println(part2());
	}
	
	public static int part1() {
		try {	
			// Find/Open File
			String fLoc = System.getProperty("user.dir") + "\\src\\Y2k23\\Inputs\\Day3Part1.txt";
			File myObj = new File(fLoc);
			// Scan File
			Scanner myReader = new Scanner(myObj);
			int total = 0;
			List<String[]> data = new ArrayList<String[]>();  
			while (myReader.hasNextLine()) {
				// Read File line by line and convert the text input to a 2d arraylist
				String line = myReader.nextLine();
				data.add(line.split(""));
			}
			// Go through the 2d array and find all the non numerical/period symbols
			for (int x=1; x<data.size()-1; x++) {
				for (int y=1; y<data.get(x).length-2; y++) {
					// Check if the current char is a number or period. If not check for valid numbers
					if(!"1234567890.".contains(data.get(x)[y])) {						
						// This set of If statements first checks for the North/South directions
						// I do this because, if north is found, the horizontal finding 
						// will automatically check the East and West of that North/South
						// This is to ensure no double counting.
						
						// East and West are checked seperatly and without conflicts of double counting.
						
						// North
						if ("1234567890".contains(data.get(x-1)[y])) {
							total += horizontalFind(data, x-1, y);
						} else {
							// North East
							if ("1234567890".contains(data.get(x-1)[y+1])) {
								total += directionalFindRight(data, x-1, y+1);
							}
							// North West
							if ("1234567890".contains(data.get(x-1)[y-1])) {
								total += directionalFindLeft(data, x-1, y-1);
							}
						}
						// South
						if ("1234567890".contains(data.get(x+1)[y])) {
							total += horizontalFind(data, x+1, y);
						} else {
							// South East
							if ("1234567890".contains(data.get(x+1)[y+1])) {
								total += directionalFindRight(data, x+1, y+1);
							}
							// South West
							if ("1234567890".contains(data.get(x+1)[y-1])) {
								total += directionalFindLeft(data, x+1, y-1);
							}
						}
						
						// East
						if ("1234567890".contains(data.get(x)[y+1])) {
							total += directionalFindRight(data, x, y+1);
						}
						// West
						if ("1234567890".contains(data.get(x)[y-1])) {
							total += directionalFindLeft(data, x, y-1);
						}
					}
				}
			}
			
			// Close reader and return total
			myReader.close();
			return total;
			
		} catch (FileNotFoundException e) {
			// Error File
			System.out.println("An error occurred.");
			e.printStackTrace();
			return -1;
		}
	}
	
	// Helper function for finding numbers in a bidirectional pattern.
	private static int horizontalFind(List<String[]> data, int x, int y) {
		// l and r keep track of the left and right sides of the currnt number
		int l = y;
		int r = y;
		
		// While loop ensures that l and r keep iterating till the end of the nubmers are found
		while (true) {
			if (!data.get(x)[l].equals(".")) { l--; }
			if (!data.get(x)[r].equals(".")) { r++; }
			// Break loop exits if l or r are outside the bounds of the data, 
			// or if a period is found for l and r. 
			if ((l==-1 || data.get(x)[l].equals(".")) 
			&& (r==data.get(0).length || data.get(x)[r].equals("."))) {
				break;
			}
		}
		
		// Convert data range from single letter strings to integer and returned
		String val = "";
		for (int i=l+1; i<r; i++) {
			val += data.get(x)[i];
		}
		return Integer.parseInt(val);
	}
	
	// Helper function for finding numbers in a left direction. 
	private static int directionalFindLeft(List<String[]> data, int x, int y) {
		// l and r keep track of the left and right sides of the currnt number
		int l = y;
		int r = y+1;
		
		// While loop ensures that l and r keep iterating till the end of the nubmers are found
		while (true) {
			// Only moving l value as r value is at a known wall. 
			if (!data.get(x)[l].equals(".")) { l--; }
			// Break loop exits if l are outside the bounds of the data, 
			// or if a period is found for l. 
			if (l==-1 || data.get(x)[l].equals(".")) {
				break;
			}
		}
		
		// Convert data range from single letter strings to integer and returned
		String val = "";
		for (int i=l+1; i<r; i++) {
			val += data.get(x)[i];
		}
		return Integer.parseInt(val);
	}
	
	// Helper function for finding numbers in a right direction. 
	private static int directionalFindRight(List<String[]> data, int x, int y) {
		// l and r keep track of the left and right sides of the currnt number
		int l = y;
		int r = y;
		
		// While loop ensures that l and r keep iterating till the end of the nubmers are found
		while (true) {
			// Only moving r value as l value is at a known wall. 
			if (!data.get(x)[r].equals(".")) { r++; }
			// Break loop exits if r are outside the bounds of the data, 
			// or if a period is found for r. 
			if (r==data.get(0).length || data.get(x)[r].equals(".")) {
				break;
			}
		}
		
		// Convert data range from single letter strings to integer and returned
		String val = "";
		for (int i=l; i<r; i++) {
			val += data.get(x)[i];
		}
		return Integer.parseInt(val);
	}
	
	public static int part2() {
		try {	
			// Find/Open File
			String fLoc = System.getProperty("user.dir") + "\\src\\Y2k23\\Inputs\\Day3Part1.txt";
			File myObj = new File(fLoc);
			// Scan File
			Scanner myReader = new Scanner(myObj);
			int total = 0;
			List<String[]> data = new ArrayList<String[]>();  
			while (myReader.hasNextLine()) {
				// Read File line by line and convert the text input to a 2d arraylist
				String line = myReader.nextLine();
				data.add(line.split(""));
			}
			// Go through the 2d array and find all the non numerical/period symbols
			for (int x=1; x<data.size()-1; x++) {
				for (int y=1; y<data.get(x).length-2; y++) {
					// Check if the current char is a gear (*)
					if(data.get(x)[y].equals("*")) {						
						List<Integer> validValues = new ArrayList<Integer>();
						// North
						if ("1234567890".contains(data.get(x-1)[y])) {
							validValues.add(horizontalFind(data, x-1, y));
						} else {
							// North East
							if ("1234567890".contains(data.get(x-1)[y+1])) {
								validValues.add(directionalFindRight(data, x-1, y+1));
							}
							// North West
							if ("1234567890".contains(data.get(x-1)[y-1])) {
								validValues.add(directionalFindLeft(data, x-1, y-1));
							}
						}
						// South
						if ("1234567890".contains(data.get(x+1)[y])) {
							validValues.add(horizontalFind(data, x+1, y));
						} else {
							// South East
							if ("1234567890".contains(data.get(x+1)[y+1])) {
								validValues.add(directionalFindRight(data, x+1, y+1));
							}
							// South West
							if ("1234567890".contains(data.get(x+1)[y-1])) {
								validValues.add(directionalFindLeft(data, x+1, y-1));
							}
						}
						
						// East
						if ("1234567890".contains(data.get(x)[y+1])) {
							validValues.add(directionalFindRight(data, x, y+1));
						}
						// West
						if ("1234567890".contains(data.get(x)[y-1])) {
							validValues.add(directionalFindLeft(data, x, y-1));
						}
						
						// Check if only two values are next to a gear (*)
						if (validValues.size() == 2) {
							// Add the new gear ration (multiply them) to the total.
							total = total + (validValues.get(0) * validValues.get(1));
						}
					}
				}
			}
			
			// Close reader and return total
			myReader.close();
			return total;
			
		} catch (FileNotFoundException e) {
			// Error File
			System.out.println("An error occurred.");
			e.printStackTrace();
			return -1;
		}
	}
}