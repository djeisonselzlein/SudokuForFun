package com.example.sudokuforfun.inputPuzzle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import com.example.sudokuforfun.gameboard.Board;
import com.example.sudokuforfun.undo.SingletonStack;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * 
 * @author djeisonselzlein
 *
 *         This class populates the board using an input file. Input files can be
 *         provided by players or predefined by difficulty levels.
 *
 */
public class PopulateBoard implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static String REMOTE_INPUT = "remote_input.txt";

	private GridLayout grid;
	private Board board;

	public PopulateBoard(GridLayout layout, Board board) {
		this.grid = layout;
		this.board = board;
	}

	public void populateBoard(String fileName) {

		SingletonStack.clearStack();

		InputStream fis;
		InputStreamReader isr;

		try {
			if (fileName.equals(REMOTE_INPUT)) {
				fis = new FileInputStream(fileName);
			} else {
				fis = getClass().getResourceAsStream(fileName);
			}

			isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String sCurrentLine;

			int row = 0;
			int col = 0;
			while ((sCurrentLine = br.readLine()) != null) {

				String[] field = sCurrentLine.split(" ");

				col = 0;
				for (String s : field) {
					// set to ReadOnly to false to allow for update
					board.setReadOnly(col, row, false);

					board.setValue(col, row, s, s.equals("0") ? false : true);

					col++;
				}
				row++;
			}

			// update the tiles for display
			for (col = 0; col < 9; col++)
				for (row = 0; row < 9; row++) {
					Label label = ((Label) ((DragAndDropWrapper) grid
							.getComponent(col, row)).getData());
					label.setPropertyDataSource(board.getCellElement(col, row));
					if (board.getCellElement(col, row).isReadOnly()) {
						label.removeStyleName("colored bold");
						label.addStyleName("black bold");
						label.addStyleName("dj-background-read-only");
						
					} else {
						label.removeStyleName("dj-background-read-only");
						label.removeStyleName("black bold");
						label.addStyleName("colored bold");
					}
				}

			System.out.println(board);
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
