package edu.unimelb.smarts.processor.communication.message;

import edu.unimelb.smarts.traffic.road.GridCell;

public class SerializableGridCell {
	public int row;
	public int column;

	public SerializableGridCell() {

	}

	public SerializableGridCell(final GridCell gridCell) {
		row = gridCell.row;
		column = gridCell.col;
	}
}
