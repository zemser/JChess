package gui;

import com.google.common.collect.Lists;
import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Move;
import engine.board.Tile;
import engine.pieces.Piece;
import engine.players.MoveMaker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String pieceIconPath = "art/basic/";

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board board;  //cant be final because we change the reference to a new board after a successful move

    private Tile srcTile;
    private Tile desTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;
    private boolean highlightLegalMoves;

    private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");

    /**
     * Constructor for the Table object, initialize the gameFrame field and:
     * set gameFrame LayOut
     * set gameFrame's JMenuBar
     * set gameFrame's size
     * set gameFrame's visible to true
     * initialize boardPanel and to gameFrame config
     */
    public Table(){
        this.gameFrame = new JFrame("Jchess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = initializeMenuBar();  // create the menu bar with all the menus in it
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        this.board = Board.createInitialBoard();

        this.boardPanel = new BoardPanel();
        this.boardDirection = BoardDirection.NORMAL;  //show board in normal order (not flipped)
        this.highlightLegalMoves = false;
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setVisible(true);
    }

    /**
     * Creates an object of JMenuBar with added JMenu object(represents the File Menu)
     * @return
     */
    private JMenuBar initializeMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());  //add file menu
        tableMenuBar.add(createPreferencesMenu());  //add presences menu
        return tableMenuBar;
    }

    /**
     * Create Menu entrance in the Menu Bar (named File)
     * @return JMenu object with name File and JMenu Items(load pgn file, exit)
     */
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        // create the "load pgn file" item
        final  JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {    // assign action on click
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up that pgn file");
            }
        });
        fileMenu.add(openPGN); //add the item to the menu
        //create the exit menu item
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {  // assign action on click - exit the frame
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem); // add the item to the menu
        //create opposite side item


        return fileMenu;
    }
    /**
     * Create Menu entrance in the Menu Bar (named Preferences)
     * @return JMenu object with name File and JMenu Items(Flip Board)
     */
    private JMenu createPreferencesMenu(){
        final JMenu preferencesMenu = new JMenu("Preferences");
        //create flip item
        final  JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener() {    // assign action on click
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();  //switch to opposite representationn
                boardPanel.drawBoard(board);                //redraw the board
            }
        });
        preferencesMenu.add(flipBoardMenuItem); //add the item to the menu

        //add separator
        preferencesMenu.addSeparator();

        //create highlight moves item
        final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight legal Moves", false);

        legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
            }
        });

        preferencesMenu.add(legalMoveHighlighterCheckbox);

        return preferencesMenu;
    }

    /**
     * Visual component for the board - 8x8 grid layout with 64 Tiles - keeps track of all the tiles
     */
    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;
        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtils.NUM_TILES; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);  // add tilePanel to the boardTiles list
                add(tilePanel);                  // add tilePanel to the JPanel Object
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        /**
         * redraw the board
         * @param board new board we want to draw
         */
        public void drawBoard(Board board) {
            removeAll(); //remove all the components
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)){
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    /**
     * Visual component for the tile
     */
    private class TilePanel extends JPanel{
        private final int tileId;
        TilePanel(final BoardPanel boardPanel, final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();  //assign color to the tile
            assignTilePieceIcon(board); // if tile is occupied assign a piece to it

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(isRightMouseButton(e)){ //cancel everything out
                        srcTile = null;
                        desTile = null;
                        humanMovedPiece = null;
                    }else if(isLeftMouseButton(e)){
                        if(srcTile == null){ //player picked piece to move(the piece on the clicked title)
                            srcTile = board.getTile(tileId);
                            humanMovedPiece = srcTile.getPiece();
                            if(humanMovedPiece == null){
                                srcTile = null;  // cant pick a tile with no piece on it
                            }
                        }else{ // player picked a tile to move the piece to
                            desTile = board.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(board, srcTile.getTileCoordinate(), desTile.getTileCoordinate());
                            if(!(move instanceof Move.NullMove)){
                                final MoveMaker transition = board.currentPlayer().makeMove(move); // make the move
                                if(transition.getMoveStatus().isDone()){  // the move is legal so we can update the board
                                    board = transition.getBoard();
                                    //TODO ADD MOVE MADE TO A MOVE LOG
                                }
                            }else{
                                System.out.println("illegalm move");
                            }

                            srcTile = null;
                            desTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(board);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            validate();
        }
        /**
         * place the image of the piece on the board - add it to the TilePanel
         * @param board check the corresponding tile to the piece
         */
        private void assignTilePieceIcon(final Board board){
            this.removeAll(); // remove all the components added to the tilePanel
            if(board.getTile(this.tileId).isTileOccupied()){
                try {
                    final BufferedImage image = ImageIO.read(new File(
                            pieceIconPath + board.getTile(this.tileId).getPiece().getPieceColor().toString().substring(0,1)
                            + board.getTile(this.tileId).getPiece().toString() + ".gif")); //reads the corresponding image for the piece on the tile
                    add(new JLabel(new ImageIcon(image)));   //add component to the tilePanel
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * set tile color to light or dark depending on the tile id
         */
        private void assignTileColor() {
            if(BoardUtils.isBetween(this.tileId, 0, 7) || BoardUtils.isBetween(this.tileId, 16, 23)
                || BoardUtils.isBetween(this.tileId, 32, 39) || BoardUtils.isBetween(this.tileId, 48, 55)){
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            }else if(BoardUtils.isBetween(this.tileId, 8, 15) || BoardUtils.isBetween(this.tileId, 24, 31)
                    || BoardUtils.isBetween(this.tileId, 40, 47) || BoardUtils.isBetween(this.tileId, 56, 63)){
                setBackground(this.tileId % 2 == 0 ? darkTileColor : lightTileColor);
            }
        }

        /**
         * redraw the Tile
         * @param board - the new board
         */
        public void drawTile(Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();

        }
        private void highlightLegalMoves(final Board board){
            if(highlightLegalMoves){
                for(final Move move : pieceLegalMoves(board)){
                    if(move.getDestinationCoordinate() == this.tileId){
                        try {
                            add(new JLabel((new ImageIcon(ImageIO.read(new File("art/basic/green_dot.png"))))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        private Collection<Move> pieceLegalMoves(final Board board){
            if(humanMovedPiece != null && humanMovedPiece.getPieceColor() == board.currentPlayer().getColor()){
                return humanMovedPiece.calculateMoves(board);
            }
        return Collections.emptyList();
        }
    }

    /**
     * Enum used for the menu to show the tiles in flipped manner
     */
    enum BoardDirection {
        NORMAL {
            /**
             * @param boardTiles list of TilePanels that represent the tiles
             * @return  boardTiles list as is
             */
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }
            /**
             * @return opposite enum
             */
            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            /**
             * @param boardTiles list of TilePanels that represent the tiles
             * @return  reversed boardTiles list
             */
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            /**
             * @return opposite enum
             */
            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();

    }
}
