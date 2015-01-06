package net.zomis.combinatorics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.zomis.minesweeper.analyze.AnalyzeFactory;
import net.zomis.minesweeper.analyze.AnalyzeResult;
import net.zomis.minesweeper.analyze.BoundedFieldRule;
import net.zomis.minesweeper.analyze.FieldRule;
import net.zomis.minesweeper.analyze.Solution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class NQueensTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{ 1, 1 }, { 2, 0 }, { 3, 0 }, { 4, 2 }, { 5, 10 }, { 6, 4 },
			{ 7, 40 }, { 8, 92 }, { 9, 352 }, { 10, 724 },
//	takes much longer time: { 11, 2680 }, { 12, 14200 }, { 13, 73712 }, { 14, 365596 }
		});
	}
	
	@Parameter(0)
	public int size;
	
	@Parameter(1)
	public int expectedSolutions;
	
	@Test
	public void solve() {
		AnalyzeFactory<Integer> queens = createQueens(size);
		AnalyzeResult<Integer> solve = queens.solve();
		System.out.println("Solving N-queens " + size + " resulted in " + solve.getTotal() + " solutions");
		if (expectedSolutions < 30) {
			for (Solution<Integer> sol : solve.getSolutions()) {
				System.out.println(sol);
			}
		}
		assertEquals(expectedSolutions, (int) solve.getTotal());
	}
	
	public static AnalyzeFactory<Integer> createQueens(int size) {
		AnalyzeFactory<Integer> analyze = new AnalyzeFactory<Integer>();
		
		for (int x = 0; x < size; x++) {
			// Diagonal from top to bottom-right
			analyze.addRule(new BoundedFieldRule<Integer>(0, createDiagonal(x, 0, size, 1, 1), 0, 1));
			if (x != 0) {
				// Diagonals from left to bottom-right
				analyze.addRule(new BoundedFieldRule<Integer>(0, createDiagonal(0, x, size, 1, 1), 0, 1));
			}
			
			// Diagonals from top to left-bottom
			analyze.addRule(new BoundedFieldRule<Integer>(0, createDiagonal(x, 0, size, -1, 1), 0, 1));
			if (x != 0) {
				// Diagonals from right to left-bottom
				analyze.addRule(new BoundedFieldRule<Integer>(0, createDiagonal(size - 1, x, size, -1, 1), 0, 1));
			}
		}
		
		for (int x = 0; x < size; x++) {
			List<Integer> columnFields = new ArrayList<Integer>();
			List<Integer> rowFields = new ArrayList<Integer>();
			for (int y = 0; y < size; y++) {
				columnFields.add(pos(x, y, size));
				rowFields.add(pos(y, x, size));
			}
			analyze.addRule(new FieldRule<Integer>(0, columnFields, 1));
			analyze.addRule(new FieldRule<Integer>(0, rowFields, 1));
		}
		
		return analyze;
	}

	private static Collection<Integer> createDiagonal(int x, int y, int size, int offsetX, int offsetY) {
		List<Integer> fields = new ArrayList<Integer>();
		while (x < size && y < size && x >= 0 && y >= 0) {
			fields.add(pos(x, y, size));
			y += offsetY;
			x += offsetX;
		}
		return fields;
	}

	private static Integer pos(int x, int y, int size) {
		return y * size + x;
	}
	
}
