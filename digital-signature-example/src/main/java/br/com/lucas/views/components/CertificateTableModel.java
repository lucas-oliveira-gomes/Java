package br.com.lucas.views.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.lucas.views.components.models.TableModelRow;

public class CertificateTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -3675408277583223874L;
	private final String[] HEADERS = new String[] { "Certificate Owner", "Certificate Source", "Source Type" };

	private List<TableModelRow> rows = new ArrayList<TableModelRow>();

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return HEADERS.length;
	}

	@Override
	public String getColumnName(int column) {
		return HEADERS[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TableModelRow row = rows.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return row.getOwner();
		case 1:
			return row.getSource();
		case 2:
			return row.getSourceType();
		default:
			return null;
		}
	}

	public void addAll(List<TableModelRow> rows) {
		for (TableModelRow row : rows) {
			add(row);
		}
	}

	public void add(TableModelRow row) {
		rows.add(row);
		fireTableDataChanged();
	}

	public TableModelRow getRow(int rowIndex) {
		return rows.get(rowIndex);
	}

}
