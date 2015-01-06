package net.zomis.combinatorics;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.zomis.minesweeper.analyze.AnalyzeFactory;
import net.zomis.minesweeper.analyze.AnalyzeResult;
import net.zomis.minesweeper.analyze.FieldGroup;
import net.zomis.minesweeper.analyze.GroupValues;
import net.zomis.minesweeper.analyze.SimplifyResult;
import net.zomis.minesweeper.analyze.Solution;

import org.junit.Test;

public class BineroTest {
	
	@Test
	public void uniqueSequence() {
		FieldGroup<String> a = new FieldGroup<String>(Arrays.asList("a"));
		FieldGroup<String> b = new FieldGroup<String>(Arrays.asList("b"));
		FieldGroup<String> c = new FieldGroup<String>(Arrays.asList("c"));
		FieldGroup<String> d = new FieldGroup<String>(Arrays.asList("d"));
		List<List<String>> lists = new ArrayList<List<String>>();
		lists.add(Arrays.asList("a", "b"));
		lists.add(Arrays.asList("c", "d"));
		
		GroupValues<String> failValues = new GroupValues<String>();
		failValues.put(a, 0);
		failValues.put(b, 1);
		failValues.put(c, 0);
		failValues.put(d, 1);
		UniqueSequence<String> sequence = new UniqueSequence<String>(null, lists);
		assertEquals(SimplifyResult.FAILED_TOO_BIG_RESULT, sequence.simplify(failValues));
	}
	
	@Test
	public void hard() {
		solve("simple");
	}
	
	@Test
	public void veryHard() {
		solve("veryhard");
	}
	
	@Test
	public void veryHard14() {
		solve("veryhard14");
	}
	
	private void solve(String string) {
		AnalyzeFactory<Integer> puzzle;
		AtomicInteger sizev = new AtomicInteger();
		try {
			puzzle = Binero.binero(getClass().getResourceAsStream(string), sizev);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		AnalyzeResult<Integer> solved = puzzle.solve();
		int size = sizev.get();
		System.out.println(string + " " + size + "x" + size);
		System.out.println(solved.getTotal());
		for (Solution<Integer> ee : solved.getSolutions()) {
			System.out.println(ee);
			System.out.println(IntegerPoints.map(ee.getSetGroupValues(), size));
			System.out.println("---");
		}
		
		assertEquals(1.0, solved.getTotal(), 0.001);
	}
	
}
