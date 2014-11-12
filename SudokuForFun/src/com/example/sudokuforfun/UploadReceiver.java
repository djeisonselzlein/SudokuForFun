package com.example.sudokuforfun;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.example.sudokuforfun.level.PopulateBoard;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.Receiver;

public class UploadReceiver implements Receiver, FinishedListener {

	private static final long serialVersionUID = 1L;
	private FileOutputStream fos = null;
	// private FileInputStream fis = null;
	// private InputStreamReader isr = null;

	// private GridLayout grid;
	// private Board board;
	private PopulateBoard populateBoard;
	private final TabSheet tabSheet;

	public UploadReceiver(final TabSheet tab, PopulateBoard inputFile) {
		// this.grid = grid;
		// this.board = board;
		this.populateBoard = inputFile;
		this.tabSheet = tab;
	}

	// The receiveUpload() method is called when the user clicks the submit
	// button.
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {

		try {
			fos = new FileOutputStream(PopulateBoard.REMOTE_INPUT);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				fos.write(b);
			}

		};
	}

	@Override
	public void uploadFinished(FinishedEvent event) {
		populateBoard.populateBoard(PopulateBoard.REMOTE_INPUT);
		// advance to the next tab, so the game start
		// tabSheet.setEnabled(false);
		tabSheet.setSelectedTab(tabSheet.getTabIndex() + 1);
		// tabSheet.getSelectedTab().setEnabled(true);
	}
}
