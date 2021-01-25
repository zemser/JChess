package gui;

import engine.Color;
import engine.board.Board;
import engine.board.Move;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static gui.Table.*;

public class GameHistoryPanel extends JPanel {
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 400);
    private final DataModel model;
    final JScrollPane scrollPane;  //scroller item for the table

    GameHistoryPanel(){
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    void redo(final Board board, final MoveLog moveHistory){
        int currentRow = 0;
        this.model.clear();
        for(final Move move : moveHistory.getMoves()){
            final String moveText = move.toString();
            if(move.getMovedPiece().getPieceColor() == Color.WHITE){
                this.model.setValueAt(moveText, currentRow, 0);
            }else if(move.getMovedPiece().getPieceColor() == Color.BLACK){
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }
        if(moveHistory.getMoves().size() > 0){
            final Move lastMove = moveHistory.getMoves().get(moveHistory.size() - 1);
            final String moveText = lastMove.toString();
            if(lastMove.getMovedPiece().getPieceColor() == Color.WHITE){
                this.model.setValueAt(moveText + calculateCheckAndCheckMateHash(board), currentRow, 0);
            }else if(lastMove.getMovedPiece().getPieceColor() == Color.BLACK){
                this.model.setValueAt(moveText + calculateCheckAndCheckMateHash(board), currentRow - 1, 1);
            }
        }
        final  JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private String calculateCheckAndCheckMateHash(Board board) {
        if(board.currentPlayer().isInCheckMate()){
            return "#";
        }else if(board.currentPlayer().isInCheck()){
            return "+";
        }
        return "";
    }

    private static class DataModel extends DefaultTableModel{
        private final List<Row> values;
        private static final String[] NAMES = {"White", "Black"};

        public DataModel() {
            this.values = new ArrayList<>();
        }
        public void clear(){
            this.values.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount() {
            if(this.values == null){
                return 0;
            }
            return this.values.size();
        }

        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        @Override
        public Object getValueAt(final int row, final int column) {
            final Row currentRow = this.values.get(row);
            return column == 0 ? currentRow.getWhiteMove() : currentRow.getBlackMove();
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            final Row currentRow;
            if(this.values.size() <= row){
                currentRow = new Row();
                this.values.add(currentRow);
            }else{
                currentRow = this.values.get(row);
            }
            if(column == 0){
                currentRow.setWhiteMove((String) aValue);
                fireTableRowsInserted(row, row);
            }else if(column == 1){
                currentRow.setBlackMove((String)aValue);
                fireTableCellUpdated(row, column);
            }
        }
        @Override
        public Class<?> getColumnClass(final int col) {
            return Move.class;
        }

        @Override
        public String getColumnName(final int col) {
            return NAMES[col];
        }
    }
    private static class Row{
        private String whiteMove;
        private String blackMove;

        Row(){

        }

        public String getWhiteMove() {
            return whiteMove;
        }

        public void setWhiteMove(final String whiteMove) {
            this.whiteMove = whiteMove;
        }

        public String getBlackMove() {
            return blackMove;
        }

        public void setBlackMove(final String blackMove) {
            this.blackMove = blackMove;
        }
    }
}
