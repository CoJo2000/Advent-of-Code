package Y2K23;
/*
 *
	--- Day 1: Trebuchet?! ---
	Something is wrong with global snow production, and you've been 
	selected to take a look. The Elves have even given you a map; on it, 
	they've used stars to mark the top fifty locations that are likely 
	to be having problems.
	
	You've been doing this long enough to know that to restore snow 
	operations, you need to check all fifty stars by December 25th.
	
	Collect stars by solving puzzles. Two puzzles will be made 
	available on each day in the Advent calendar; the second puzzle is 
	unlocked when you complete the first. Each puzzle grants one star. 
	Good luck!
	
	You try to ask why they can't just use a weather machine 
	("not powerful enough") and where they're even sending you ("the sky") 
	and why your map looks mostly blank ("you sure ask a lot of questions") 
	and hang on did you just say the sky ("of course, where do you think snow 
	comes from") when you realize that the Elves are already loading you into 
	a trebuchet ("please hold still, we need to strap you in").
	
	As they're making the final adjustments, they discover that their 
	calibration document (your puzzle input) has been amended by a very young 
	Elf who was apparently just excited to show off her art skills. 
	Consequently, the Elves are having trouble reading the values on the document.
	
	The newly-improved calibration document consists of lines of text; 
	each line originally contained a specific calibration value that the 
	Elves now need to recover. On each line, the calibration value can be 
	found by combining the first digit and the last digit (in that order) 
	to form a single two-digit number.
	
	For example:
	
	1abc2
	pqr3stu8vwx
	a1b2c3d4e5f
	treb7uchet
	In this example, the calibration values of these four lines 
	are 12, 38, 15, and 77. Adding these together produces 142.
	
	Consider your entire calibration document. 
	What is the sum of all of the calibration values?

	Your puzzle answer was 55017.

	The first half of this puzzle is complete! It provides one gold star: *

	--- Part Two ---
	Your calculation isn't quite right. It looks like some of the digits are 
	actually spelled out with letters: one, two, three, four, five, six, seven, 
	eight, and nine also count as valid "digits".

	Equipped with this new information, you now need to find the real first and 
	last digit on each line. For example:
	
	two1nine
	eightwothree
	abcone2threexyz
	xtwone3four
	4nineeightseven2
	zoneight234
	7pqrstsixteen
	In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. 
	Adding these together produces 281.
	
	What is the sum of all of the calibration values?
	
 */

// Start Day: January 16st, 2023
// End Day	: January ##st, 2023


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 
import java.util.regex.*;

public class Day1 {
	public static void main (String [] args) {
		System.out.println(part1());
		System.out.println(part2());
	}

	public static int part1() {
		try {	
			// Find/Open File
			String fLoc = System.getProperty("user.dir") + "\\src\\Y2k23\\Inputs\\Day1Part1.txt";
			File myObj = new File(fLoc);
			// Scan File
			Scanner myReader = new Scanner(myObj);
			int total = 0;
			while (myReader.hasNextLine()) {
				// Read File line by line
				String data = myReader.nextLine();
				
				// Find the first instance of a number and the last instance of a number
				Pattern pattern = Pattern.compile("[0-9]");
				Matcher matcher = pattern.matcher(data);
				List<String> result = new ArrayList<>();
				while (matcher.find()) {
				    result.add(matcher.group());
				}
				
				int first = Integer.parseInt(result.get(0));
				int last  = Integer.parseInt(result.get(result.size()-1));
				
				// Amend the ints together and add them to the total
				total += Integer.parseInt(first + "" + last);
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
	
	public static int part2() {
		try {	
			// Find/Open File
			String fLoc = System.getProperty("user.dir") + "\\src\\Y2k23\\Inputs\\Day1Part2.txt";
			File myObj = new File(fLoc);
			// Scan File
			Scanner myReader = new Scanner(myObj);
			int total = 0;
			while (myReader.hasNextLine()) {
				// Read File line by line
				String data = myReader.nextLine();
				
				// Find the first instance of a number and the last instance of a number
				Pattern pattern = Pattern.compile("(oneight|fiveight|twone|eightwo|eighthree|threeight|sevenine|nineight)|(one|two|three|four|five|six|seven|eight|nine)|([1-9])");
				Matcher matcher = pattern.matcher(data);
				List<String> result = new ArrayList<String>();
				while (matcher.find()) {
				    result.add(matcher.group());
				}
				// Parse First and last numbers into integers		
				int first;
				int last;
				
				try {
					first = Integer.parseInt(result.get(0));
				} catch (NumberFormatException e) {
					first = StringToInt(result.get(0), true);
				}
				try {
					last  = Integer.parseInt(result.get(result.size()-1));
				} catch (NumberFormatException e) {
					last = StringToInt(result.get(result.size()-1), false);
				}

				// Amend the ints together and add them to the total
				total += Integer.parseInt(first + "" + last);
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
	
	public static int StringToInt(String num, boolean front) {
		// Returns the String number as the integer number
		if (num.equals("one")) 	{ return 1; }
		if (num.equals("two")) 	{ return 2; }
		if (num.equals("three")){ return 3; }
		if (num.equals("four")) { return 4; }
		if (num.equals("five")) { return 5; }
		if (num.equals("six")) 	{ return 6; }
		if (num.equals("seven")){ return 7; }
		if (num.equals("eight")){ return 8; }
		if (num.equals("nine")) { return 9; }
		
		// Odd double cases
		if (front) {
			if (num.equals("twone")) 	{ return 2; }
			if (num.equals("eightwo")) 	{ return 8; }
			if (num.equals("eighthree")){ return 8; }
			if (num.equals("threeight")){ return 3; }
			if (num.equals("sevenine")) { return 7; }
			if (num.equals("nineight")) { return 9; }
			if (num.equals("oneight"))   { return 1; }
			if (num.equals("fiveight")) { return 5; }
		} else {
			if (num.equals("twone")) 	{ return 1; }
			if (num.equals("eightwo")) 	{ return 2; }
			if (num.equals("eighthree")){ return 3; }
			if (num.equals("threeight")){ return 8; }
			if (num.equals("sevenine")) { return 9; }
			if (num.equals("nineight")) { return 8; }
			if (num.equals("oneight"))   { return 8; }
			if (num.equals("fiveight")) { return 8; }
		}
		
		// Should never return this
		return 0;
	}
}
