package gui;

import com.google.common.primitives.Ints;
import engine.board.Move;
import engine.pieces.Piece;
import gui.Table.MoveLog;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static gui.Table.pieceIconPath;

public class TakenPiecesPanel extends JPanel {

    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);
    private final JPanel northPanel;
    private final JPanel southPanel;
    private Color PANEL_COLOR = Color.decode("0xFDF5E6");
    public TakenPiecesPanel() {
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final MoveLog moveLog){
        this.southPanel.removeAll();
        this.northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();
        for(final Move move : moveLog.getMoves()){
            if(move.isAttackMove()){
                final Piece takenPiece = move.getAttackPiece();
                if(takenPiece.getPieceColor() == engine.Color.WHITE){
                    whiteTakenPieces.add(takenPiece);
                }else if(takenPiece.getPieceColor() == engine.Color.BLACK){
                    blackTakenPieces.add(takenPiece);
                }else{
                    throw new RuntimeException("should not reach here");
                }
            }
            Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
                @Override
                public int compare(Piece o1, Piece o2) {
                    return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
                }
            });
            Collections.sort(blackTakenPieces, new Comparator<Piece>() {
                @Override
                public int compare(Piece o1, Piece o2) {
                    return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
                }
            });

            for(final Piece takenPiece : whiteTakenPieces){
                try {
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath + takenPiece.getPieceColor().toString().substring(0,1) +
                                                                 takenPiece.toString() + ".gif"));
                    final ImageIcon icon = new ImageIcon(image);
                    final JLabel imageLabel = new JLabel();
                    this.southPanel.add(imageLabel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for(final Piece takenPiece : blackTakenPieces){
                try {
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath + takenPiece.getPieceColor().toString().substring(0,1) +
                             takenPiece.toString() + ".gif"));
                    final ImageIcon icon = new ImageIcon(image);
                    final JLabel imageLabel = new JLabel();
                    this.northPanel.add(imageLabel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
