
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import java.util.StringTokenizer;
import java.util.Set;
import java.util.HashSet;

public class MapTest {

	private static boolean isPathCorrect(String path, int startX, int startY, int destX, int destY) {
		StringTokenizer token = new StringTokenizer(path);
		boolean correct = true;
		String point = null;
		String x = "";
		String y = "";
		while (token.hasMoreTokens()) {
			point = token.nextToken();
			x = point.substring(point.indexOf('(') + 1 , point.indexOf(','));	
			y = point.substring(point.indexOf(',') + 1 , point.indexOf(')'));
			if ((Integer.parseInt(x) - startX == 0 && Math.abs(Integer.parseInt(y) - startY) == 1) ||
				(Math.abs(Integer.parseInt(x) - startX)== 1) && Integer.parseInt(y) - startY == 0 ) {
				startX = Integer.parseInt(x);
				startY = Integer.parseInt(y);
			}
			else {
				correct = false;
				break;
			}
		}
		if (correct) {
			if (startX != destX || startY != destY)
				correct = false;
		}
		return correct;
	}

	@Test
	public void testgoSoutWest1() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 4, 1, "");
		assertTrue(isPathCorrect(actual, 5, 5, 4, 1));		
	}
	@Test
	public void testgoSoutWest2() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 5, 1, "");
		assertTrue(isPathCorrect(actual, 5, 5, 5, 1));		
	}
	@Test
	public void testgoSoutWest3() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,1, 5, 0, "");
		assertTrue(isPathCorrect(actual, 5, 1, 5, 0));		
	}

	@Test
	public void testgoSoutWest4() {
		Map city = new Map (1, 1);
		String actual = city.getPath(0,0, 0, 0, "");
		assertTrue(isPathCorrect(actual, 0, 0, 0, 0));		
	}
	
	@Test
	public void testgoSoutWest5() {
		Map city = new Map (5, 8);
		String actual = city.getPath(3,2, 2, 1, "");
		assertTrue(isPathCorrect(actual, 3, 2, 2, 1));		
	}

	@Test
	public void testgoSoutEast1() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 4, 8, "");
		assertTrue(isPathCorrect(actual, 5, 5, 4, 8));		
	}
	
	@Test
	public void testgoSoutEast2() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 5, 9, "");
		assertTrue(isPathCorrect(actual, 5, 5, 5, 9));		
	}
	@Test
	public void testgoSoutEast3() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 0, 9, "");
		assertTrue(isPathCorrect(actual, 5, 5, 0, 9));		
	}
	@Test
	public void testgoSoutEast4() {
		Map city = new Map (1, 1);
		String actual = city.getPath(0,0, 0, 0, "");
		assertTrue(isPathCorrect(actual, 0, 0, 0, 0));		
	}
	
	@Test
	public void testgoSoutEast5() {
		Map city = new Map (15, 27);
		String actual = city.getPath(10,15, 3, 25, "");
		assertTrue(isPathCorrect(actual, 10,15, 3, 25));		
	}

	@Test
	public void testgoNorthWest1() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 8, 0, "");
		assertTrue(isPathCorrect(actual, 5, 5, 8, 0));		
	}
	
	@Test
	public void testgoNorthWest2() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 5, 0, "");
		assertTrue(isPathCorrect(actual, 5, 5, 5, 0));		
	}
	@Test
	public void testgoNorthWest3() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 8, 0, "");
		assertTrue(isPathCorrect(actual, 5, 5, 8, 0));		
	}
	@Test
	public void testgoNorthWest4() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 5, 5, "");
		assertTrue(isPathCorrect(actual, 5, 5, 5, 5));		
	}
	
	@Test
	public void testgoNorthWest5() {
		Map city = new Map (23, 18);
		String actual = city.getPath(11,13, 18, 5, "");
		assertTrue(isPathCorrect(actual, 11,13, 18, 5));		
	}
	
	@Test
	public void testgoNorthEast1() {
		Map city = new Map (100, 100);
		String actual = city.getPath(50,50, 80, 90, "");
		assertTrue(isPathCorrect(actual, 50, 50, 80, 90));		
	}

	@Test
	public void testgoNorthEast2() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 5, 9, "");
		assertTrue(isPathCorrect(actual, 5, 5, 5, 9));		
	}
	@Test
	public void testgoNorthEast3() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 9, 5, "");
		assertTrue(isPathCorrect(actual, 5, 5, 9, 5));		
	}
	@Test
	public void testgoNorthEast4() {
		Map city = new Map (10, 10);
		String actual = city.getPath(5,5, 5, 5, "");
		assertTrue(isPathCorrect(actual, 5, 5, 5, 5));		
	}
	
	@Test
	public void testgoNorthEast5() {
		Map city = new Map (50, 25);
		String actual = city.getPath(15,24, 49, 24, "");
		assertTrue(isPathCorrect(actual, 15,24, 49, 24));		
	}

	@Test
	public void testgetPathException1() {
		Map city = new Map (10, 10);
		assertThrows(IllegalArgumentException.class, ()->city.getPath(-1, 5, 5, 6, ""));
	}
	@Test
	public void testgetPathException2() {
		Map city = new Map (10, 10);
		assertThrows(IllegalArgumentException.class, ()->city.getPath(1, -5, 5, 6, ""));
	}
	@Test
	public void testgetPathException3() {
		Map city = new Map (10, 10);
		assertThrows(IllegalArgumentException.class, ()->city.getPath(1, 5, -5, 6, ""));
	}
	@Test
	public void testgetPathException4() {
		Map city = new Map (10, 10);
		assertThrows(IllegalArgumentException.class, ()->city.getPath(1, 5, 5, -6, ""));
	}
	@Test
	public void testgetPathException5() {
		Map city = new Map (10, 10);
		assertThrows(IllegalArgumentException.class, ()->city.getPath(11, 5, 5, 6, ""));
	}
	@Test
	public void testgetPathException6() {
		Map city = new Map (10, 10);
		assertThrows(IllegalArgumentException.class, ()->city.getPath(1, 15, 5, 6, ""));
	}
	@Test
	public void testgetPathException7() {
		Map city = new Map (10, 10);
		assertThrows(IllegalArgumentException.class, ()->city.getPath(1, 5, 15, 6, ""));
	}
	@Test
	public void testgetPathException8() {
		Map city = new Map (10, 10);
		assertThrows(IllegalArgumentException.class, ()->city.getPath(1, 5, 5, 16, ""));
	}
	
	private static boolean toBorder(String path, int row, int col) {
		StringTokenizer token = new StringTokenizer(path);
		boolean correct = false;
		String point = null;
		while (token.hasMoreTokens()) {
			point = token.nextToken();
		}
		int x = Integer.parseInt(point.substring(point.indexOf('(')+ 1, point.indexOf(',')));
		int y = Integer.parseInt(point.substring(point.indexOf(',')+ 1, point.indexOf(')')));
		if ( x == 0 || x == row -1 || y == 0 || y == col - 1)
				correct = true;
		return correct;
	}

	private static String findBorder(String path) {
		StringTokenizer token = new StringTokenizer(path);
		String point = null;
		while (token.hasMoreTokens()) {
			point = token.nextToken();
		}
		return point;
	}

	private static boolean findDuplication(String path) {
		StringTokenizer token = new StringTokenizer(path);
		boolean correct = true;
		Set<String> set = new HashSet<String>(); 
		while (token.hasMoreTokens()) {
			if (!set.add(token.nextToken())) {
				correct = false;
				break;
			}
		}
		return correct;
	}

	@Test
	public void testFindPath1() {
		int startX = 5; 
		int startY = 5; 
		int n1 = 10;
		int n2 = 10;
		Map city = new Map (n1, n2);
		String actualPath = city.findPath (startX, startX);
		String point = findBorder(actualPath);
		String actual = (actualPath.substring(actualPath.indexOf(')') + 1)).trim();
		int destX = Integer.parseInt(point.substring(point.indexOf('(') + 1, point.indexOf(','))) ;
		int destY = Integer.parseInt(point.substring(point.indexOf(',') + 1 , point.indexOf(')')));

		boolean correct = toBorder(actualPath, n1, n2) && findDuplication(actualPath) &&
				isPathCorrect(actual, startX, startY, destX, destY);
		assertTrue(correct);
				
	}
	
	@Test
	public void testFindPath2() {
		int startX = 5; 
		int startY = 5; 
		int n1 = 30;
		int n2 = 45;
		Map city = new Map (n1, n2);
		String actualPath = city.findPath (startX, startX);
		String point = findBorder(actualPath);
		String actual = (actualPath.substring(actualPath.indexOf(')') + 1)).trim();
		int destX = Integer.parseInt(point.substring(point.indexOf('(') + 1, point.indexOf(','))) ;
		int destY = Integer.parseInt(point.substring(point.indexOf(',') + 1 , point.indexOf(')')));

		boolean correct = toBorder(actualPath, n1, n2) && findDuplication(actualPath) &&
				isPathCorrect(actual, startX, startY, destX, destY);
		assertTrue(correct);
	}
	@Test
	public void testFindPath3() {
		int startX = 29; 
		int startY = 5; 
		int n1 = 30;
		int n2 = 45;
		Map city = new Map (n1, n2);
		String actualPath = city.findPath (startX, startY);
		String point = findBorder(actualPath);
		String actual = (actualPath.substring(actualPath.indexOf(')') + 1)).trim();
		int destX = Integer.parseInt(point.substring(point.indexOf('(') + 1, point.indexOf(','))) ;
		int destY = Integer.parseInt(point.substring(point.indexOf(',') + 1 , point.indexOf(')')));

		boolean correct = toBorder(actualPath, n1, n2) && findDuplication(actualPath) &&
				isPathCorrect(actual, startX, startY, destX, destY);
		assertTrue(correct);
	}
	@Test
	public void testFindPath4() {
		int startX = 29; 
		int startY = 44; 
		int n1 = 30;
		int n2 = 45;
		Map city = new Map (n1, n2);
		String actualPath = city.findPath (startX, startY);
		String point = findBorder(actualPath);
		String actual = (actualPath.substring(actualPath.indexOf(')') + 1)).trim();
		int destX = Integer.parseInt(point.substring(point.indexOf('(') + 1, point.indexOf(','))) ;
		int destY = Integer.parseInt(point.substring(point.indexOf(',') + 1 , point.indexOf(')')));

		boolean correct = toBorder(actualPath, n1, n2) && findDuplication(actualPath) &&
				isPathCorrect(actual, startX, startY, destX, destY);
		assertTrue(correct);
	}
	@Test
	public void testFindPath5() {
		int startX = 0; 
		int startY = 0; 
		int n1 = 1;
		int n2 = 1;
		Map city = new Map (n1, n2);
		String actualPath = city.findPath (startX, startY);
		String point = findBorder(actualPath);
		String actual = (actualPath.substring(actualPath.indexOf(')') + 1)).trim();
		int destX = Integer.parseInt(point.substring(point.indexOf('(') + 1, point.indexOf(','))) ;
		int destY = Integer.parseInt(point.substring(point.indexOf(',') + 1 , point.indexOf(')')));

		boolean correct = toBorder(actualPath, n1, n2) && findDuplication(actualPath) &&
				isPathCorrect(actual, startX, startY, destX, destY);
		assertTrue(correct);
	}
	@Test
	public void testFindPath6() {
		int startX = 4; 
		int startY = 3; 
		int n1 = 13;
		int n2 = 12;
		Map city = new Map (n1, n2);
		String actualPath = city.findPath (startX, startY);
		String point = findBorder(actualPath);
		String actual = (actualPath.substring(actualPath.indexOf(')') + 1)).trim();
		int destX = Integer.parseInt(point.substring(point.indexOf('(') + 1, point.indexOf(','))) ;
		int destY = Integer.parseInt(point.substring(point.indexOf(',') + 1 , point.indexOf(')')));

		boolean correct = toBorder(actualPath, n1, n2) && findDuplication(actualPath) &&
				isPathCorrect(actual, startX, startY, destX, destY);
		assertTrue(correct);
	}

	@Test
	public void testFindPath7() {
		int startX = 1; 
		int startY = 1; 
		int n1 = 13;
		int n2 = 12;
		Map city = new Map (n1, n2);
		String actualPath = city.findPath (startX, startY);
		String point = findBorder(actualPath);
		String actual = (actualPath.substring(actualPath.indexOf(')') + 1)).trim();
		int destX = Integer.parseInt(point.substring(point.indexOf('(') + 1, point.indexOf(','))) ;
		int destY = Integer.parseInt(point.substring(point.indexOf(',') + 1 , point.indexOf(')')));

		boolean correct = toBorder(actualPath, n1, n2) && findDuplication(actualPath) &&
				isPathCorrect(actual, startX, startY, destX, destY);
		assertTrue(correct);
	}

}
