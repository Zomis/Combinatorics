package net.zomis.combinatorics;

import java.util.ArrayList;
import java.util.List;

import net.zomis.minesweeper.analyze.AnalyzeFactory;
import net.zomis.minesweeper.analyze.BoundedFieldRule;
import net.zomis.minesweeper.analyze.FieldRule;

public class NQueens {
	public static AnalyzeFactory<Integer> createQueens(int size) {
		AnalyzeFactory<Integer> analyze = new AnalyzeFactory<Integer>();
		
		for (int x = 0; x < size; x++) {
			// Diagonal from top to bottom-right
			analyze.addRule(new BoundedFieldRule<Integer>(0, IntegerPoints.createDiagonal(x, 0, size, 1, 1), 0, 1));
			if (x != 0) {
				// Diagonals from left to bottom-right
				analyze.addRule(new BoundedFieldRule<Integer>(0, IntegerPoints.createDiagonal(0, x, size, 1, 1), 0, 1));
			}
			
			// Diagonals from top to left-bottom
			analyze.addRule(new BoundedFieldRule<Integer>(0, IntegerPoints.createDiagonal(x, 0, size, -1, 1), 0, 1));
			if (x != 0) {
				// Diagonals from right to left-bottom
				analyze.addRule(new BoundedFieldRule<Integer>(0, IntegerPoints.createDiagonal(size - 1, x, size, -1, 1), 0, 1));
			}
		}
		
		for (int x = 0; x < size; x++) {
			List<Integer> columnFields = new ArrayList<Integer>();
			List<Integer> rowFields = new ArrayList<Integer>();
			for (int y = 0; y < size; y++) {
				columnFields.add(IntegerPoints.pos(x, y, size));
				rowFields.add(IntegerPoints.pos(y, x, size));
			}
			analyze.addRule(new FieldRule<Integer>(0, columnFields, 1));
			analyze.addRule(new FieldRule<Integer>(0, rowFields, 1));
		}
		
		return analyze;
	}

	

}
