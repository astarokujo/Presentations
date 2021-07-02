import java.util.*;

/**
 * 
 * @author Ryan Kanwar
 *
 */

public class Map {
	// boolean [][] map;
	// the above was included but I didn't need to use it so I commented it out
	private int row;
	private int column;
	private int counter = 1;
	// Counter variable that's used throughout the class.
	private String pathway = "";
	// String pathway will be used as a concatenation of all paths (outputted by
	// getPath) commuted by the program into one string.
	private int startRowCopy = -1;
	private int startColCopy = -1;

	// Copies of the first startRow and startCol values that are used in method
	// findPath.
	// Their purpose is to store the first coordinate values so that when findPath
	// resets,
	// it can go back to the original inputted coordinates from the client.
	// They both = -1 as it involves a condition in findPath. See condition there.
	/**
	 * 
	 * /** This is the constructor that constructs the city map, which is a grid of
	 * row by column.
	 * 
	 * @param row    is the number of east-west streets of the city
	 * @param column is the number of north-south streets of the city
	 */
	public Map(int row, int column) {
		// Constructs the grid size of the map.
		this.row = row;
		this.column = column;
	}

	/**
	 * This method checks the correctness of the input parameters. If the
	 * preconditions are not met an exception is thrown, otherwise depending to the
	 * direction, it calls one of the four recursive functions of goSouthWest,
	 * goSouthEast, goNorthWest and goNorthEast.
	 * 
	 * @param startRow is the starting row of the path
	 * @param startCol is the starting column of the path
	 * @param destRow  is the destination row
	 * @param destCol  is the destination column
	 * @param path     is the path that is constructed while the recursive method is
	 *                 called. In first round, it will be "".
	 * @return returns a string representing the path to the destination. The format
	 *         of the output is (x1,y1) (x2,y2) (x3,y3)...
	 * @pre the integer parameters should be in the range of the city grid.(i.e. [0,
	 *      N) if N is the number of east-west streets and [0, M) if M is the number
	 *      of north-south streets.)
	 * @exception IllegalArgumentException if any of the precondition did not meet.
	 */
	public String getPath(int startRow, int startCol, int destRow, int destCol, String path) {
		// Please complete this method
		// you should decide on what should be returned. This return statement is here
		// to avoid compiler error.
		boolean condition1 = (startRow < 0 || startCol < 0 || destRow < 0 || destCol < 0);
		// Condition that checks to see if the start or destination coordinates are
		// negative.
		boolean condition2 = (startRow > this.row || startCol > this.column || destRow > this.row
				|| destCol > this.column);
		// Second condition that checks to see if the start or destination coordinates
		// are out of the grid's range.
		if (condition1 || condition2) {
			throw new IllegalArgumentException();
		}
		// Below are a series of conditions that check to see which method is
		// appropriate to call upon.
		// I separated the north intersections from the south intersections.
		// First the conditions check to see if the path is north/south based on
		// comparing the start and destination rows.
		// The conditions then check to see if the path is west/east by comparing the
		// start and destination columns.

		if (startRow <= destRow)
		// if the destination row is greater than start row, clearly its going north.
		{
			if (destCol <= startCol) {
				// if the start column is greater than the destination column, clearly its going
				// west.
				path = goNorthWest(startRow, startCol, destRow, destCol, path);
			} else if (destCol >= startCol) {
				// else if the destination column is greater than the destination column,
				// clearly its going east.
				path = goNorthEast(startRow, startCol, destRow, destCol, path);
			}

		} else if (startRow >= destRow)
		// else if the start row is greater than destination row, clearly its going
		// south.
		{
			// The two conditions here are the same as the ones for North.
			if (destCol <= startCol) {
				path = goSouthWest(startRow, startCol, destRow, destCol, path);
			} else if (destCol >= startCol) {
				path = goSouthEast(startRow, startCol, destRow, destCol, path);
			}
		}
		return path;
	}

	/**
	 * This method returns a path from the source (startRow, startCol) to the
	 * destination (destRow, destCol). Please note that the returning path does not
	 * include the starting point.
	 * 
	 * @param startRow is the starting row of the path
	 * @param startCol is the starting column of the path
	 * @param destRow  is the destination row
	 * @param destCol  is the destination column
	 * @param path     is the path that is constructed while the recursive method is
	 *                 called. In first round, it will be "".
	 * @return returns a string representing the path to the destination. The format
	 *         of the output is (x1,y1) (x2,y2) (x3,y3)...
	 * @pre <code> startRow >= destRow </code> and
	 *      <code> startCol >= destCol </code>
	 */

	private String goSouthWest(int startRow, int startCol, int destRow, int destCol, String path) {
		// Please complete this method
		// you should decide on what should be returned. This return statement is here
		// to avoid compiler error.
		// Checks to see if the start row is above the destination row.
		// When the condition complies, it decreases the start row amount using var
		// counter.
		// Var counter makes startRow decrease by one each time.
		// Path becomes the coordinate. The method then calls itself recursively until
		// the condition is fufilled.
		// Likewise with destination column and start column.
		if (destRow < startRow) {
			startRow -= counter;
			path += "(" + startRow + "," + startCol + ")" + " ";
			return goSouthWest(startRow, startCol, destRow, destCol, path);
		}
		if (destCol < startCol) {
			startCol -= counter;
			path += "(" + startRow + "," + startCol + ")" + " ";
			return goSouthWest(startRow, startCol, destRow, destCol, path);
		}
		return path;
		// Once all conditions are fufilled the method then returns all the coordinates
		// the car passed by.
	}

	/**
	 * This method returns a path from the source (startRow, startCol) to the
	 * destination (destRow, destCol). Please note that the returning path does not
	 * include the starting point.
	 * 
	 * @param startRow is the starting row of the path
	 * @param startCol is the starting column of the path
	 * @param destRow  is the destination row
	 * @param destCol  is the destination column
	 * @param path     is the path that is constructed while the recursive method is
	 *                 called. In first round, it will be "".
	 * @return returns a string representing the path to the destination. The format
	 *         of the output is (x1,y1) (x2,y2) (x3,y3)...
	 * @pre <code> startRow >= destRow </code> and
	 *      <code> startCol <= destCol </code>
	 */
	private String goSouthEast(int startRow, int startCol, int destRow, int destCol, String path) {
		// Please complete this method
		// you should decide on what should be returned. This return statement is here
		// to avoid compiler error.
		// This method is similar to goSouthWest. The only difference
		// is that the second condition assumes the startCol number is lower than the
		// destination column number, so it increases start column and calls upon the
		// method
		// until it reaches destination column number to fufill the condition.
		if (destRow < startRow) {
			startRow -= counter;
			path += "(" + startRow + "," + startCol + ")" + " ";
			return goSouthEast(startRow, startCol, destRow, destCol, path);
		}
		if (destCol > startCol) {
			startCol += counter;
			path += "(" + startRow + "," + startCol + ")" + " ";
			return goSouthEast(startRow, startCol, destRow, destCol, path);
		}
		return path;
	}

	/**
	 * This method returns a path from the source (startRow, startCol) to the
	 * destination (destRow, destCol). Please note that the returning path does not
	 * include the starting point.
	 * 
	 * @param startRow is the starting row of the path
	 * @param startCol is the starting column of the path
	 * @param destRow  is the destination row
	 * @param destCol  is the destination column
	 * @param path     is the path that is constructed while the recursive method is
	 *                 called. In first round, it will be "".
	 * @return returns a string representing the path to the destination. The format
	 *         of the output is (x1,y1) (x2,y2) (x3,y3)...
	 * @pre <code> startRow <= destRow </code> and
	 *      <code> startCol >= destCol </code>
	 */
	private String goNorthEast(int startRow, int startCol, int destRow, int destCol, String path) {
		// Please complete this method
		// you should decide on what should be returned. This return statement is here
		// to avoid compiler error.
		// Similar to goNorthEast. The only difference is the first condition - which
		// checks to see if the destination row is bigger than the start row
		// This implies that it's going north, so it increases startRow until it reaches
		// the destination.
		if (destRow > startRow) {
			startRow += counter;
			path += "(" + startRow + "," + startCol + ")" + " ";
			return goNorthEast(startRow, startCol, destRow, destCol, path);
		}
		if (destCol > startCol) {
			startCol += counter;
			path += "(" + startRow + "," + startCol + ")" + " ";
			return goNorthEast(startRow, startCol, destRow, destCol, path);
		}
		return path;
	}

	/**
	 * This method returns a path from the source (startRow, startCol) to the
	 * destination (destRow, destCol). Please note that the returning path does not
	 * include the starting point.
	 * 
	 * @param startRow is the starting row of the path
	 * @param startCol is the starting column of the path
	 * @param destRow  is the destination row
	 * @param destCol  is the destination column
	 * @param path     is the path that is constructed while the recursive method is
	 *                 called. In first round, it will be "".
	 * @return returns a string representing the path to the destination. The format
	 *         of the output is (x1,y1) (x2,y2) (x3,y3)...
	 * @pre <code> startRow >= destRow </code> and
	 *      <code> startCol <= destCol </code>
	 */
	private String goNorthWest(int startRow, int startCol, int destRow, int destCol, String path) {
		// Please complete this method
		// you should decide on what should be returned. This return statement is here
		// to avoid compiler error.
		// Similar to goNorthEast. The only difference is that the second condition
		// checks to see if
		// startCol is above destCol. It then decreases startCol and calls upon the
		// function recursively
		// until the condition is fufilled.
		if (destRow > startRow) {
			startRow += counter;
			path += "(" + startRow + "," + startCol + ")" + " ";
			return goNorthWest(startRow, startCol, destRow, destCol, path);
		}
		if (destCol < startCol) {
			startCol -= counter;
			path += "(" + startRow + "," + startCol + ")" + " ";
			return goNorthWest(startRow, startCol, destRow, destCol, path);
		}
		return path;
	}

	/**
	 * This method find a path from (startRow, startCol) to a border point of the
	 * city. Please note that the starting point should be included in the path.
	 * 
	 * @param startRow is the starting row of the path
	 * @param startCol is the starting column of the path
	 * @return is a path from (starting row, staring col) to a border point of the
	 *         city. The format of the output is (x1,y1) (x2,y2) (x3,y3)...
	 */

	public String findPath(int startRow, int startCol) {
		// Please complete this method
		// you should decide on what should be returned. This return statement is here
		// to avoid compiler error.
		if (pathway.length() == 0) {
			pathway += "(" + startRow + "," + startCol + ")\s";
		}
		// When the pathway length = 0, the var will store the first coordinates
		// inputted by
		// the client.
		if (startRowCopy < 0 || startColCopy < 0) {
			startRowCopy = startRow;
			startColCopy = startCol;
		}
		// When either startRowCopy or startColCopy are -1, they will become startRow
		// and startCol.
		// It's set this way as they are only going to be -1 when initialized.
		// This ensures that startRowCopy and startColCopy store the first coordinate
		// values that were inputted by the client.
		// Their purpose is to retain those values and send them to the function if
		// we ever need to possibly reset the function's values.
		int gridStart = 0;
		int endPoint1;
		int endPoint2;
		int endPoint3;
		int endPoint4;
		String path = "";
		boolean condition;
		int randomCol = ((int) (Math.random() * ((this.column - 1) - gridStart))) + gridStart;
		int randomRow = ((int) (Math.random() * ((this.row - 1) - gridStart))) + gridStart;
		// Random coordinates that are between 0,0 (gridStart, the start of the grid)
		// and the grid's dimensions (this.row and this.column's values)
		pathway += getPath(startRow, startCol, randomRow, randomCol, path);
		// Calls getPath. Gets a path using functions from tasks 2-4 and concatenates it
		// to prior paths. All coordinates are all stored in one string.
		String spaceFree = pathway.replaceAll("\s", "");
		String sub = spaceFree;
		// Removes all unnecessary spaces from the string and stores it into spaceFree.
		// String sub then copies spaceFree. It will be used later on in the function
		// compare.
		// Removing all spaces is necessary to make the process of checking the pathway
		// string easier.
		condition = compare(spaceFree, sub);
		// The two strings are then sent to another function called compare, which
		// compares the two strings. More details explained in compare's method.
		endPoint1 = pathway.indexOf("(" + gridStart);
		endPoint2 = pathway.indexOf("," + gridStart);
		endPoint3 = pathway.indexOf("(" + this.row);
		endPoint4 = pathway.indexOf("," + this.column);
		// These 4 'endPoint' variables store the first instance of:
		// endPoint1: (0
		// endPoint2: ,0
		// endPoint3: the same as endPoint1, but with the grid's row dimension
		// endPoint4: the same as endPoint2, but with the grid's column dimension
		// All of these values are extracted from the string pathway.
		// Note that each of these are coordinate values found at the edge of the grid's
		// dimensions.
		// These occurrences imply that the car found a way out of the path.
		if (condition) {
			startRow = startRowCopy;
			startCol = startColCopy;
			pathway = "";
			return findPath(startRow, startCol);
			// When the condition var (compare's output) is true, the function will call
			// itself again.
			// When it does in this instance, it resets pathway (emptying the string) and
			// reinitializes the startRow and startCol variables to their
			// initial values - the ones the client had inputted orginally.
			// The condition var being true implies that the car has intersected a
			// coordinate twice.
		} else {
			// Implies that the car was able to cross a path without intersecting any prior
			// coordinates again.
			// The next four conditions imply that the car has reached an intersection that
			// leaves the grid's range.
			if (endPoint1 >= 0) {
				// Checks to see if there is an occurrence of "(0" in the string pathway.
				// It's above/= 0 as it outputs where in the string it found the occurrence.
				// Occurrence is between (including) 0 and (including) string's length.
				String part = pathway.substring(endPoint1, pathway.length());
				// Takes a part of the pathway String and stores it into String part.
				// Part extracts pathway from the character right before "(0" to pathway's end.
				pathway = pathway.substring(0, endPoint1);
				part = part.substring(0, ((part.indexOf(")") + 1)));
				pathway += part;
				// pathway then becomes the part of the string before "(0".
				// String part gets cut to the substring: "(0," to the first occurrence of ")".
				// This implies that it ONLY extracts out the coordinate (of the grid's end that
				// the car reached).
				// Finally, part is added onto pathway, so that it can add the part of the path
				// prior to reaching the end of the grid and then adds the end coordinate.
				// endPoint3's condition is the same as this, only that it looks for this.row
				// and not 0. All of this is done due to the fact that there's no way of
				// knowing the length of the other coordinate.
			} else if (endPoint2 >= 0) {
				// Checks to see if there is an occurrence of ",0" in the string pathway
				String part = pathway.substring(0, endPoint2);
				String part2 = pathway.substring(endPoint2, pathway.length());
				pathway = pathway.substring(0, pathway.lastIndexOf("("));
				part = part.substring(part.lastIndexOf("("), part.length());
				part2 = part2.substring(0, (part2.indexOf(")") + 1));
				pathway += part + part2;
				// This condition is mainly the same as endPoint1's. The only difference is that
				// it checks to see if ",0" has occurred in the pathway string
				// as this would be the column point of 0. When it finds it, it splits pathway
				// into two substring parts (part and part2).
				// It then makes pathway = all prior coordinates before the final one. It then
				// takes part to get the first half of the final coordinate.
				// Then part2 = the second half of the final coordinate. Finally, the last
				// coordinates are joined together into pathway to form
				// the string of ALL paths including the last ones.
				// endPoint4's condition is the same as this, only that it looks for this.col
				// and not 0.
			} else if (endPoint3 >= 0) {
				// Checks to see if there is an occurrence of "((this.row)" in the string
				// pathway
				String part = pathway.substring(endPoint3, pathway.length());
				pathway = pathway.substring(0, endPoint3);
				part = part.substring(0, ((part.indexOf(")") + 1)));
				pathway += part;
			} else if (endPoint4 >= 0) {
				// Checks to see if there is an occurrence of "((this.col)" in the string
				// pathway
				String part = pathway.substring(0, endPoint4);
				String part2 = pathway.substring(endPoint4, pathway.length());
				pathway = pathway.substring(0, pathway.lastIndexOf("("));
				part = part.substring(part.lastIndexOf("("), part.length());
				part2 = part2.substring(0, (part2.indexOf(")") + 1));
				pathway += part + part2;
			} else {
				return findPath(randomRow, randomCol);
			}
			// Else, if the car hasn't ever reached an edge point of the grid,
			// but there have been no repeated intersections,
			// the method calls itself again.
			// However, the methods row and col values are changes to the random
			// coordinates; the ones that were the last destination points of the path.
			// This is so that the car could start from the last destination points and
			// reach new random destination points once the function calls itself.
		}
		return pathway;
	}

	private boolean compare(String spaceFree, String sub) {
		// The purpose of this function is to compare all pathway coordinates to one
		// another to see if there are any repeated intersections.
		// It's one of findPath's helper functions.
		// String sub's role is to store the first coordinate within spaceFree
		// as a substring, hence the name "sub".
		if (sub.length() != 0) {
			String number = sub.substring(sub.indexOf('(') + 1, sub.indexOf(')'));
			int occurrence = 0;
			boolean checker = compare2(spaceFree, number, occurrence);
			// When sub's length doesn't = 0 (base case is when it does = 0), String number
			// becomes the first coordinate value that's stored in String sub.
			// It stores numbers within the string, hence the name number.
			// A counter named occurrence is initialized to be 0. Boolean variable checker
			// then calls the second helper function of compare2, sending in pathway's
			// spacefree variable along with
			// a singular coordinate (String number) and occurrence.
			if (checker) {
				return true;
			} else {
				return compare(spaceFree, (sub.substring(sub.indexOf(')') + 1, sub.length())));
			}
			// If checker is true, then an repeated intersection has been found and compare
			// returns true to findPath.
			// Else, the function calls itself by reducing sub to a substring of itself -
			// taking out the coordinate it already compared to the other coordinates.
		}
		return false;
		// If base case is fufilled without any repeated occurrences in intersections,
		// compare returns false.
	}

	private boolean compare2(String spaceFree, String number, int occurrence) {
		// The purpose of this method is to compare one specific coordinate to every
		// other coordinate from the pathway (spaceFree) string.
		if (spaceFree.length() != 0) {
			// As the function's base case is spaceFree's length equaling 0, the function
			// recurs only when the length of spaceFree isn't 0
			if (number.compareTo(spaceFree.substring(spaceFree.indexOf('(') + 1, spaceFree.indexOf(')'))) == 0) {
				occurrence += 1;
			}
			// Whenever String number (a singular coordinate from spaceFree) is compared to
			// String spaceFree and finds that the first coordinate stored in spaceFree is
			// the same to it, occurrence increases by one.
			if (occurrence == 2) {
				return true;
				// Whenever occurrence is 2, that means that there has been a repeated
				// intersection in spaceFree/pathway and compare2 returns true.
				// The occurrence is 2 as the coordinate would have to be stored in spaceFree at
				// least TWO times for it to be a repeated occurrence.
			} else {
				return compare2(spaceFree.substring(spaceFree.indexOf(')') + 1, spaceFree.length()), number,
						occurrence);
				// Else, the function compare2 calls itself when occurrence is below 2. Each
				// time it calls itself it changes spaceFree to be a substring of its former
				// self, cutting off the first coordinate stored in the string. 
				// It sends that to be compared with String number each time the method runs
				// also sending over String number and occurrence each time.
			}
		}
		return false;
	}
} 
