package net.zomis.combinatorics;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import net.zomis.minesweeper.analyze.AnalyzeFactory;
import net.zomis.minesweeper.analyze.AnalyzeResult;
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
		AnalyzeFactory<Integer> queens = NQueens.createQueens(size);
		AnalyzeResult<Integer> solve = queens.solve();
		System.out.println("Solving N-queens " + size + " resulted in " + solve.getTotal() + " solutions");
		if (expectedSolutions < 30) {
			for (Solution<Integer> sol : solve.getSolutions()) {
				System.out.println(sol);
				System.out.println(IntegerPoints.map(sol.getSetGroupValues(), size));
			}
		}
		assertEquals(expectedSolutions, (int) solve.getTotal());
		System.out.println();
	}
	
}
