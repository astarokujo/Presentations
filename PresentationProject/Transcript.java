import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * @author Ryan Kanwar
 * This class generates a transcript for each student, whose information is in
 * the text file.
 * 
 *
 */

public class Transcript {
	private static ArrayList<Object> grade = new ArrayList<Object>();
	private File inputFile;
	private String outputFile;

	/**
	 * This is the constructor for <code> Transcript </code> class that initializes
	 * its instance variables and call readFie private method to read the file and
	 * construct this.grade.
	 * 
	 * @param inFile  is the name of the input file.
	 * @param outFile is the name of the output file.
	 */
	public Transcript(String inFile, String outFile) {
		inputFile = new File(inFile);
		outputFile = outFile;
		grade = new ArrayList<Object>();
		this.readFile();
	}// end of Transcript constructor

	/**
	 * This method reads a text file and add each line as an entry of grade
	 * ArrayList.
	 * 
	 * @exception It throws FileNotFoundException if the file is not found.
	 */
	private void readFile() {
		Scanner sc = null;
		try {
			sc = new Scanner(inputFile);
			while (sc.hasNextLine()) {
				grade.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	} // end of readFile

	/**
	 * This method creates and returns an <i> ArrayList, </i> whose element is an
	 * object of class <code> Student.</code> The object at each element is created
	 * by aggregating ALL the information, that is found for one student in the
	 * <code> grade </code> Arraylist of class <code> Transcript. </code> (i.e. if
	 * the text file contains information about 9 students, then the array list will
	 * have 9 elements.)
	 * 
	 * @exception it throws InvalidTotalException if a student's final grade is
	 *               above 100 or if the weight of all of their assessments isn't
	 *               100
	 * @return an ArrayList representation of all of the Students' information
	 */
	public ArrayList<Student> buildStudentArray() {
		ArrayList<Student> studentInfo = new ArrayList<Student>();
		ArrayList<Course> courseInfo = new ArrayList<Course>();
		String studentID = "";
		String courseCode = "";
		double creditNum;
		String credit;
		char type;
		String weight;
		String rawGrade;
		for (Object line : grade) {
			ArrayList<Assessment> assessmentGrade = new ArrayList<Assessment>();
			ArrayList<Double> gradeList = new ArrayList<Double>();
			ArrayList<Integer> numWeight = new ArrayList<Integer>();
			// creates all of these values in memory each time the for loop goes over a line
			// from the input text
			String currLine = (String) line;
			String finder;
			int pFinder;
			int eFinder;
			int commaFind = currLine.indexOf(',');
			if (commaFind == -1) {
				continue;
				// whenever a line is empty (doesn't contain a comma) then the for loop skips
				// the line
			}
			courseCode = currLine.substring(0, commaFind);
			// gets the course code
			finder = currLine.substring(commaFind + 1, currLine.length());
			// finder stores the line excluding the course code
			credit = finder.substring(0, finder.indexOf(','));
			// gets the course credit
			String noName = currLine.substring(0, currLine.lastIndexOf(','));
			// takes out the name of a line
			pFinder = noName.indexOf(",P");
			eFinder = noName.indexOf(",E");
			// looks for the activity type
			if (pFinder == -1) {
				finder = noName.substring(0, eFinder);
			} else {
				finder = noName.substring(0, pFinder);
			}
			// if it doesn't find an activity on a line, it will only look for eFinder,
			// which represents exams
			// finder's job is to continuously take out information from the line as the
			// code progresses once information is stored into their respective variables
			studentID = finder.substring(finder.lastIndexOf(',') + 1, finder.length());
			// stores studentID
			creditNum = Double.parseDouble(credit);
			// stores creditNum as a double
			String gradeLine = (currLine.substring(finder.length(), noName.length()));
			// stores all of the assessment grades into this string
			int entryNum = gradeLine.length() - (gradeLine.replace(",", "").length());
			// this represents how many grades there are in the line by finding how many
			// commas are contained in gradeLine
			for (int i = 1; i <= entryNum; i++) {
				// goes over how many grades there are
				String sub = gradeLine.substring(gradeLine.indexOf(',') + 1, gradeLine.indexOf(')') + 1);
				// sub is a substring that stores the current weight and grade
				gradeLine = gradeLine.substring(sub.length() + 1, gradeLine.length());
				// shortens gradeLine by taking the current grade being analyzed out
				type = sub.charAt(0);
				// gets the type of assignment
				weight = sub.substring(1, sub.indexOf('('));
				numWeight.add(Integer.parseInt(weight));
				// gets the weight of the assignment and then adds it to the numWeight array
				assessmentGrade.add(Assessment.getInstance(type, numWeight.get(numWeight.size() - 1)));
				// adds the type and weight of a course into assessmentGrade, of assessment type
				rawGrade = sub.substring(sub.indexOf('(') + 1, sub.indexOf(')'));
				gradeList.add(Double.parseDouble(rawGrade));
				// adds all of the grades of a student's course into gradeList
			}
			String name = currLine.substring(currLine.lastIndexOf(',') + 1, currLine.length());
			// stores student's name
			String trimmer = name.trim();
			// takes out all whitespaces
			Course studentCourse = new Course(courseCode, assessmentGrade, creditNum);
			// instantiates Course storing all student course information
			courseInfo.add(studentCourse);
			// adds the course info for one student in the courseInfo array
			Student stud = new Student(studentID, trimmer, courseInfo);
			// instantiates Student storing all student information
			ArrayList<Course> tempCourse = new ArrayList<Course>();
			ArrayList<Double> tempGrade = new ArrayList<Double>();
			// these arrays are for temporary storage of information
			try {
				stud.addGrade(gradeList, numWeight);
			} catch (InvalidTotalException e) {
				System.out.println(e);
			}
			// sends the student's grades and weights from a course, extracted from a line,
			// to this method
			int index = -1;
			for (int i = 0; i < studentInfo.size(); i++) {
				String compareName = studentInfo.get(i).getName();
				if (trimmer.equals(compareName)) {
					// loop sees if the name on the current line is the same as the student names
					// already stored in studentInfo (from all prior file lines)
					index = i;
					// if ever the same, the index becomes i
				}
			}
			if (index >= 0) {
				// implies that the name from the current line is already stored in the
				// studentInfo arraylist
				Double finalGrades;
				finalGrades = (stud.getFinalGrade().get(stud.getFinalGrade().size() - 1));
				tempGrade = studentInfo.get(index).getFinalGrade();
				tempGrade.add(finalGrades);
				studentInfo.get(index).addCourse(courseInfo.get(courseInfo.size() - 1));
				// adds the latest courseInfo entry to studentInfo via addCourse
				studentInfo.get(index).setFinalGrade(tempGrade);
				// tempGrade's purpose is to be an arraylist holding only all of the student's
				// prior final grades.
				// it then adds a new final grade to the arraylist before setting that as the
				// updated final grade arrayList for the particular student ONLY
			}
			if (index == -1) {
				// implies a new student is getting entered in the studentInfo arraylist
				tempCourse.add(courseInfo.get(courseInfo.size() - 1));
				// gets current course and adds it to tempCourse
				tempGrade.add(stud.getFinalGrade().get(stud.getFinalGrade().size() - 1));
				// gets current final grade and adds it to tempGrade
				stud.setCourseTaken(tempCourse);
				// sets the courseTaken from tempCourse's values
				studentInfo.add(stud);
				// adds current student info to studentInfo
				studentInfo.get(studentInfo.size() - 1).setFinalGrade(tempGrade);
				// sets the student's finalGrade to be what's in the tempGrade arrayList
			}
		}
		return studentInfo;
		// end of buildStudentArray
	}

	/**
	 * This is the method that prints the transcript to the given file (i.e.
	 * <code> outputFile </code> attribute)
	 * 
	 * @param printResult is an ArrayList holding all of the Students' information
	 */
	public void printTranscript(ArrayList<Student> printResult) {
		try {
			File outFile = new File(outputFile);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
			// used to write on file
			// this method calls on values from the various objects in the program to create
			// the transcript
			for (int i = 0; i < printResult.size(); i++) {
				writer.write(printResult.get(i).getName() + "\t" + printResult.get(i).getStudentID());
				writer.newLine();
				writer.write("--------------------");
				writer.newLine();
				for (int j = 0; j < printResult.get(i).getCourseTaken().size(); j++) {
					writer.write(printResult.get(i).getCourseTaken().get(j).getCode() + "\t"
							+ printResult.get(i).getFinalGrade().get(j));
					writer.newLine();
				}
				writer.write("--------------------");
				writer.newLine();
				writer.write("GPA: " + printResult.get(i).weightedGPA());
				writer.newLine();
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	// end of printTranscript

	public static void main(String[] args) {
		ArrayList<Student> studentInfo = new ArrayList<Student>();
		Transcript transcriptObj = new Transcript("wrongInput.txt", "output.txt");
		// Creates new transcript object
		studentInfo = transcriptObj.buildStudentArray();
		// Builds the student array and its information
		transcriptObj.printTranscript(studentInfo);
		// Prints out the transcript
		// I've used the below code to test out my code throughout the entire coding
		// process. I've left it here to show how I access studentInfo's information
		for (int i = 0; i < studentInfo.size(); i++) {
			System.out.println("Hi! My name is: " + studentInfo.get(i).getName() + ". My student id is: "
					+ studentInfo.get(i).getStudentID());
			for (int j = 0; j < studentInfo.get(i).getCourseTaken().size(); j++) {
				System.out.println("Course code is: " + studentInfo.get(i).getCourseTaken().get(j).getCode()
						+ "\nCourse credit is: " + studentInfo.get(i).getCourseTaken().get(j).getCredit()
						+ "\nHere is my final grade for the course: " + studentInfo.get(i).getFinalGrade().get(j));
				System.out.println("A list of my assignments:");
				for (int k = 0; k < studentInfo.get(i).getCourseTaken().get(j).getAssignment().size(); k++) {
					System.out.println("Type and weight: "
							+ studentInfo.get(i).getCourseTaken().get(j).getAssignment().get(k).getType()
							+ studentInfo.get(i).getCourseTaken().get(j).getAssignment().get(k).getWeight());
				}
			}
			System.out.println("Here's my overall GPA: " + studentInfo.get(i).weightedGPA() + "\n");
		}
	}

} // end of Transcript

/**
 * This class holds a Student's assessment information, found from the input.txt
 * file.
 * 
 * @author Ryan Kanwar
 *
 */
class Assessment {
	private char type;
	private int weight;

	private Assessment() {
		// default constructor
		this.type = 0;
		this.weight = 0;
	}

	private Assessment(char type, int weight) {
		// defined constructor
		this.type = type;
		this.weight = weight;
	}

	/**
	 * Returns the type of an assignment
	 * 
	 * @return the type of an assignment
	 */
	public char getType() {
		return this.type;
	}

	/**
	 * Sets the type of an assignment
	 * 
	 * @param type represents the type of assignment as set by user input
	 */
	// encapsulation:
	public void setType(char type) {
		this.type = type;
	}

	/**
	 * Returns the weight of an assignment
	 * 
	 * @return the weight of an assignment
	 */
	// encapsulation:
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Sets the weight of an assignment
	 * 
	 * @param weight represents the type of assignment as set by user input
	 */
	// encapsulation:
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * This is a static factory method for class <code> Assessment. </code>
	 * 
	 * @param userType   is the type of assignment the student took for a particular
	 *                   course (P for an assignment or E for an exam).
	 * @param userWeight is the weight of the assignment the student took for a
	 *                   particular course.
	 * @return an instantiation of the <code> Assessment </code> class. Calls onto
	 *         its defined constructor to create a new <code> Assessment </code>
	 *         object.
	 */
	public static Assessment getInstance(char userType, int userWeight) {
		char type = userType;
		int weight = userWeight;
		return new Assessment(type, weight);
	}

	/**
	 * This is an overridden method for Object’s equals() method that returns true,
	 * if all the instance variables of two objects have the same value.
	 * 
	 * @param obj, an object assumed to be an instance of <code> Assessment </code>.
	 *             It's then compared to the attributes that's already been
	 *             instantiated in <code> Assessment </code>'s constructor.
	 * @return a boolean value - either true or false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Assessment) {
			// checks to see if it's an instance of assessment.
			Assessment check = (Assessment) obj;
			if (this.getType() == (check.getType()) && this.getWeight() == check.getWeight()) {
				return true;
			}
		}
		return false;
	}
}
// end of assessment

/**
 * This is a class that define's a student's course.
 * 
 * @author Ryan555
 *
 */
class Course {
	private String code;
	private ArrayList<Assessment> assignment = new ArrayList<Assessment>();
	private double credit;

	/**
	 * This is the default constructor for <code> Course </code> class that
	 * initializes its instance variables
	 */
	public Course() {
		// default constructor
		this.code = null;
		this.assignment = null;
		this.credit = new Double(0.0);
	}

	/**
	 * This is a constructor for Course class that initializes its instance
	 * variables
	 * 
	 * @param code       is a variable of type <code> String </code> representing
	 *                   the course code for a particular student's course
	 * @param assignment is an ArrayList of type <code> Assessment </code>
	 *                   representing a list of the student's assessments for the
	 *                   student's particular course
	 * @param credit     is a variable of type <code> Double </code> representing
	 *                   the credit for the student's particular course
	 */
	public Course(String code, ArrayList<Assessment> assignment, double credit) {
		// defined constructor that uses composition when invoked.
		String codeCopy = new String(code);
		this.code = codeCopy;
		ArrayList<Assessment> assignmentCopy = new ArrayList<Assessment>(assignment);
		this.assignment = assignmentCopy;
		double creditCopy = new Double(credit);
		this.credit = creditCopy;
	}

	/**
	 * This is a copy constructor for Course class that initializes its instance
	 * variables
	 * 
	 * @param c holds all of the course information for a student (ie. the course
	 *          code, the student's assessments and their information for the course
	 *          and the course's credit)
	 */
	public Course(Course c) {
		// Copy constructor. Composition is already achieved in the getter methods.
		this.code = c.getCode();
		this.assignment = c.getAssignment();
		this.credit = c.getCredit();
	}

	/**
	 * Returns the course code of a course
	 * 
	 * @return the course code of a course
	 */
	// encapsulation
	public String getCode() {
		return new String(this.code);
	}

	/**
	 * Sets the course code by user input
	 * 
	 * @param code is a variable of <code> String </code> type representing the
	 *             course code
	 */
	// encapsulation
	public void setCode(String code) {
		String codeCopy = code;
		this.code = codeCopy;
	}

	/**
	 * Returns an ArrayList of student assignments for a particular course of theirs
	 * 
	 * @return an ArrayList of student assignments for a particular course of theirs
	 */
	// encapsulation
	public ArrayList<Assessment> getAssignment() {
		return new ArrayList<Assessment>(this.assignment);
	}

	/**
	 * Sets an ArrayList of student assignments for a particular course of theirs by
	 * user input
	 * 
	 * @param assignment is an ArrayList of student assignments for a particular
	 *                   course of theirs
	 */
	// encapsulation
	public void setAssignment(ArrayList<Assessment> assignment) {
		ArrayList<Assessment> assignmentCopy = assignment;
		this.assignment = assignmentCopy;
	}

	/**
	 * Returns the course credit of a student's particular course
	 * 
	 * @return a <code> Double </code> that represents the course credit of a
	 *         student's particular course
	 */
	// encapsulation
	public double getCredit() {
		return new Double(this.credit);
	}

	/**
	 * Sets a student's course credit
	 * 
	 * @param credit is a <code> Double </code> representing course credit for a
	 *               student's particular course
	 */
	// encapsulation
	public void setCredit(double credit) {
		double creditCopy = credit;
		this.credit = creditCopy;
	}

	/**
	 * This is an overridden method for Object’s equals() method that returns true,
	 * if all the instance variables of two objects have the same value.
	 * 
	 * @param obj, an object assumed to be an instance of <code> Course </code>.
	 *             It's then compared to the attributes that's already been
	 *             instantiated in <code> Course </code>'s constructor.
	 * @return a boolean value - either true or false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Course) {
			// checks to see if object is an instance of course
			Course check = (Course) obj;
			if (this.getCode().equals(check.getCode()) && this.getAssignment().equals(check.getAssignment())
					&& this.getCredit() == check.getCredit()) {
				return true;
			}
		}
		return false;
	}
}
// end of course

/**
 * Defined the information of a particular student.
 * 
 * @author Ryan555
 *
 */
class Student {
	private String studentID;
	private String name;
	private ArrayList<Course> courseTaken = new ArrayList<Course>();
	private ArrayList<Double> finalGrade = new ArrayList<Double>();

	/**
	 * This is the default constructor for <code> Student </code>
	 */
	public Student() {
		// default constructor
		this.studentID = null;
		this.name = null;
		this.courseTaken = null;
		this.finalGrade = null;
	}

	/**
	 * A constructor for the student class that initializes its instances
	 * 
	 * @param studentID   is the student ID for the student
	 * @param studentName is the name of the student
	 * @param courseTaken is an ArrayList of type <code> Course </code> representing
	 *                    all of the courses the student took
	 */
	public Student(String studentID, String studentName, ArrayList<Course> courseTaken) {
		// defined constructor that uses composition to assign values.
		String studentIDCopy = new String(studentID);
		this.studentID = studentIDCopy;
		String studentNameCopy = new String(studentName);
		this.name = studentNameCopy;
		ArrayList<Course> courseTakenCopy = new ArrayList<Course>(courseTaken);
		this.courseTaken = courseTakenCopy;
	}

	/**
	 * Gets the student's ID
	 * 
	 * @return the student's ID
	 */
	// encapsulation
	public String getStudentID() {
		return new String(this.studentID);
	}

	/**
	 * Sets the student's ID to user input
	 * 
	 * @param studentID is the student's ID as set by user input
	 */
	// encapsulation
	public void setStudentID(String studentID) {
		String studentIDCopy = new String(studentID);
		this.studentID = studentIDCopy;
	}

	/**
	 * Gets the student's name
	 * 
	 * @return a <code> String </code> representation of the student's name
	 */
	// encapsulation
	public String getName() {
		return new String(this.name);
	}

	/**
	 * Sets the student's name to user input
	 * 
	 * @param name is the student's name as set by user input
	 */
	// encapsulation
	public void setName(String name) {
		String nameCopy = new String(name);
		this.name = nameCopy;
	}

	/**
	 * Gets an ArrayList representation of the courses taken by a particular student
	 * 
	 * @return an ArrayList representation of the courses taken by a particular
	 *         student
	 */
	// encapsulation
	public ArrayList<Course> getCourseTaken() {
		return new ArrayList<Course>(this.courseTaken);
	}

	/**
	 * Sets an ArrayList representation of the courses taken by a particular student
	 * from user input
	 * 
	 * @param courseTaken is an ArrayList representation of the courses taken by a
	 *                    particular student from user input
	 */
	// encapsulation
	public void setCourseTaken(ArrayList<Course> courseTaken) {
		ArrayList<Course> courseTakenCopy = new ArrayList<Course>(courseTaken);
		this.courseTaken = courseTakenCopy;
	}

	/**
	 * Gets an ArrayList representation of the final grades from all of the courses
	 * taken by a particular student
	 * 
	 * @return an ArrayList representation of the final grades from all of the
	 *         courses taken by a particular student
	 */
	// encapsulation
	public ArrayList<Double> getFinalGrade() {
		return new ArrayList<Double>(this.finalGrade);
	}

	/**
	 * Sets an ArrayList representation of the final grades from all of the courses
	 * taken by a particular student from user input
	 * 
	 * @param finalGrade is an ArrayList representation of the final grades from all
	 *                   of the courses taken by a particular student from user
	 *                   input
	 */
	// encapsulation
	public void setFinalGrade(ArrayList<Double> finalGrade) {
		ArrayList<Double> finalGradeCopy = new ArrayList<Double>(finalGrade);
		this.finalGrade = finalGradeCopy;
	}

	/**
	 * This method gets an array list of the grades and their weights, computes the
	 * true value of the grade based on its weight and add it to finalGrade
	 * attribute. In case the sum of the weight was not 100, or the sum of grade was
	 * greater 100, it throws <code> InvalidTotalException </code>, which is an
	 * exception that should be defined by you.
	 * 
	 * @exception it throws InvalidTotalException if a student's final grade is
	 *               above 100 or if the weight of all of their assessments aren't
	 *               100
	 * @param grade  is an ArrayList of type <code>Double</code> containing all of a
	 *               particular student's grades for a particular course that they
	 *               took.
	 * @param weight is an ArrayList of type <code>Integer</code> containing all of
	 *               the weights corresponding to each assignment's grade for a
	 *               student's particular course.
	 */
	public void addGrade(ArrayList<Double> grade, ArrayList<Integer> weight) throws InvalidTotalException {
		ArrayList<Double> grader = new ArrayList<Double>();
		int gradeDivider = 100;
		double average = 0.0;
		double value = 0.0;
		int currWeight = 0;
		for (int i = 0; i < grade.size(); i++) {
			currWeight += weight.get(i);
			average += ((grade.get(i) * weight.get(i)) / gradeDivider);
			// calculates the average based on the grade and weight value
		}
		String averageText = String.format("%.1f", average);
		value = Double.parseDouble(averageText);
		// rounds the number to one decimal place. Then converts it back to a double
		// value
		grader.add(value);
		setFinalGrade(grader);
		// sets the finalGrade attribute using grader's values
		// whenever the weight of assignments for a particular course isn't 100, the
		// below exception gets called
		if (currWeight != 100) {
			throw new InvalidTotalException("Uh oh! The weight of assignments/exams in " + getName() + "'s course: "
					+ getCourseTaken().get(getCourseTaken().size() - 1).getCode() + " is: " + currWeight
					+ ". Doesn't add to 100 meaning that it's invalid...");
		}
		if (value > 100) {
			throw new InvalidTotalException(
					"Uh oh! " + getName() + "'s final grade is higher than 100%! It's: " + value + ". Invalid grade.");
		}
		// whenever the grade is above 100%, it throws an exception for invalid total
		// exception
		// NOTE: the only grade that's an issue is Jane's 76.5 grade as on the PDF it's
		// shown to be 76.4.
		// The raw grade I computed for Jane was 76.45 (exactly), which is why I decided
		// to leave it rounded to 76.5
	}

	/**
	 * A method that computes the student's GPA.
	 * 
	 * @return a <code>Double</code> value which represents a student's GPA, rounded
	 *         to one decimal
	 */
	public double weightedGPA() {
		double average = 0.0;
		double creditCount = 0.0;
		double value;
		int gradeSize = getFinalGrade().size();
		for (int i = 0; i < gradeSize; i++) {
			double currGrade = getFinalGrade().get(i);
			creditCount += getCourseTaken().get(i).getCredit();
			// Below are a bunch of conditions to see which grade point a finalGrade falls
			// under
			if (currGrade >= 90 && currGrade <= 100) {
				currGrade = 9.0;

			} else if (currGrade >= 80 && currGrade <= 89.99) {
				currGrade = 8.0;

			} else if (currGrade >= 75 && currGrade <= 79.99) {
				currGrade = 7.0;

			} else if (currGrade >= 70 && currGrade <= 74.99) {
				currGrade = 6.0;

			} else if (currGrade >= 65 && currGrade <= 69.99) {
				currGrade = 5.0;

			} else if (currGrade >= 60 && currGrade <= 64.99) {
				currGrade = 4.0;

			} else if (currGrade >= 55 && currGrade <= 59.99) {
				currGrade = 3.0;

			} else if (currGrade >= 50 && currGrade <= 54.99) {
				currGrade = 2.0;

			} else if (currGrade >= 47 && currGrade <= 49.99) {
				currGrade = 1.0;

			} else {
				currGrade = 0.0;

			}
			average += currGrade * (getCourseTaken().get(i).getCredit());
		}
		average = average / creditCount;
		String averageText = String.format("%.1f", average);
		value = Double.parseDouble(averageText);
		// calculates the GPA average, then rounds the value to one decimal place before
		// converting it to double
		return value;
	}

	/**
	 * This method gets a course object as an input and adds it to
	 * <code> courseTaken </code>
	 * 
	 * @param C is the information about a specific course
	 */
	public void addCourse(Course c) {
		ArrayList<Course> courseTakenCopy = new ArrayList<Course>(getCourseTaken());
		courseTakenCopy.add(c);
		setCourseTaken(courseTakenCopy);
	}
} // end of student class

/**
 * Class invalid total exception is an exception that gets invoked whenever the
 * student's final grade is over 100%, or if all of the assignments for the
 * student's course doesn't add up to 100% in weight
 * 
 * @author Ryan555
 *
 */
class InvalidTotalException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the invalidtotalexception class that inherits from the
	 * Exception class
	 * 
	 * @param output is a message for the exception to output whenever it's invoked
	 */
	public InvalidTotalException(String output) {
		super(output);
	}

}