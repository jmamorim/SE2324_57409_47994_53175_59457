
package net.sf.freecol.client.gui.panel;

import java.awt.Graphics;
import java.util.logging.Logger;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.SwingConstants;

import net.sf.freecol.client.gui.i18n.Messages;
import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.common.model.Game;
import net.sf.freecol.common.model.Unit;


/**
 * This is the panel that shows more details about the currently selected unit
 * and the tile it stands on. It also shows the amount of gold the player has left
 * and stuff like that.
 */
public final class InfoPanel extends FreeColPanel {
    public static final String  COPYRIGHT = "Copyright (C) 2003-2004 The FreeCol Team";
    public static final String  LICENSE = "http://www.gnu.org/licenses/gpl.html";
    public static final String  REVISION = "$Revision$";

    private static final Logger logger = Logger.getLogger(InfoPanel.class.getName());

    private final FreeColClient freeColClient;
    private final Game          game;
    private final ImageProvider imageProvider;
    private final EndTurnPanel  endTurnPanel = new EndTurnPanel();
    private final UnitInfoPanel unitInfoPanel = new UnitInfoPanel();


    

    /**
    * The constructor that will add the items to this panel.
    * @param game The Game object that has all kinds of useful information
    * that we want to display here.
    * @param imageProvider The ImageProvider that can provide us with images to
    * display on this panel.
    */
    public InfoPanel(FreeColClient freeColClient, Game game, ImageProvider imageProvider) {
        this.freeColClient = freeColClient;
        this.game = game;
        this.imageProvider = imageProvider;

        setSize(256, 128);
        setLayout(null);

        unitInfoPanel.setVisible(false);
        endTurnPanel.setVisible(false);

        unitInfoPanel.setLocation((getWidth()-unitInfoPanel.getWidth())/2, (getHeight()-unitInfoPanel.getHeight())/2);
        endTurnPanel.setLocation((getWidth()-endTurnPanel.getWidth())/2, (getHeight()-endTurnPanel.getHeight())/2);

        add(unitInfoPanel);
        add(endTurnPanel);
    }



    /**
    * Updates this <code>InfoPanel</code>.
    * @param unit The displayed unit (or null if none)
    */
    public void update(Unit unit) {
        unitInfoPanel.update(unit);
    }


    /**
    * Gets the <code>Unit</code> in which this <code>InfoPanel</code> is
    * displaying information about.
    *
    * @return The <code>Unit</code> or <i>null</i> if no <code>Unit</code> applies.
    */
    public Unit getUnit() {
        return unitInfoPanel.getUnit();
    }


    /**
    * Paints this component.
    * @param graphics The Graphics context in which to draw this component.
    */
    public void paintComponent(Graphics graphics) {
        if (unitInfoPanel.getUnit() != null) {
            if (!unitInfoPanel.isVisible()) {
                unitInfoPanel.setVisible(true);
                endTurnPanel.setVisible(false);
            }
        } else if (!freeColClient.getMyPlayer().hasNextActiveUnit()) {
            if (!endTurnPanel.isVisible()) {
                endTurnPanel.setVisible(true);
                unitInfoPanel.setVisible(false);
            }
        }

        super.paintComponent(graphics);
    }


    /**
    * Panel for displaying <code>Unit</code>-information.
    */
    public class UnitInfoPanel extends JPanel {

        private final JLabel    unitLabel,
                                unitNameLabel,
                                unitMovesLabel,
                                unitToolsLabel;
        private Unit unit;

        public UnitInfoPanel() {
            unitLabel = new JLabel();
            unitNameLabel = new JLabel();
            unitMovesLabel = new JLabel();
            unitToolsLabel = new JLabel();

            unitLabel.setSize(118, 96);
            unitNameLabel.setSize(116, 20);
            unitMovesLabel.setSize(116, 20);
            unitToolsLabel.setSize(116, 20);

            unitLabel.setLocation(10, 4);
            unitNameLabel.setLocation(130, 15);
            unitMovesLabel.setLocation(130, 40);
            unitToolsLabel.setLocation(130, 65);

            setLayout(null);

            add(unitLabel);
            add(unitNameLabel);
            add(unitMovesLabel);
            add(unitToolsLabel);

            unitLabel.setFocusable(false);
            unitNameLabel.setFocusable(false);
            unitMovesLabel.setFocusable(false);
            unitToolsLabel.setFocusable(false);

            setSize(256, 128);
            setOpaque(false);
        }
        
        
        /**
        * Paints this component.
        * @param graphics The Graphics context in which to draw this component.
        */
        public void paintComponent(Graphics graphics) {
            //Unit unit = freeColClient.getGUI().getActiveUnit();
            if (unit != null) {
                unitLabel.setIcon(imageProvider.getUnitImageIcon(imageProvider.getUnitGraphicsType(unit)));
                unitNameLabel.setText(unit.getName());
                unitMovesLabel.setText("Moves: " + unit.getMovesAsString());
                if (unit.isPioneer()) {
                    unitToolsLabel.setText("Tools: " + unit.getNumberOfTools());
                } else if (unit.getType() == Unit.TREASURE_TRAIN) {
                    unitToolsLabel.setText("Gold: " + unit.getTreasureAmount());
                } else {
                    unitToolsLabel.setText("");
                }
            } else {
                unitLabel.setIcon(null);
                unitNameLabel.setText("");
                unitMovesLabel.setText("");
                unitToolsLabel.setText("");
            }

            super.paintComponent(graphics);
        }
        

        /**
        * Updates this <code>InfoPanel</code>.
        * @param unit The displayed unit (or null if none)
        */
        public void update(Unit unit) {
            this.unit = unit;
        }


        /**
        * Gets the <code>Unit</code> in which this <code>InfoPanel</code> is
        * displaying information about.
        *
        * @return The <code>Unit</code> or <i>null</i> if no <code>Unit</code> applies.
        */
        public Unit getUnit() {
            return unit;
        }
    }


    /**
    * Panel for ending the turn.
    */
    public class EndTurnPanel extends JPanel {

        private JLabel endTurnLabel = new JLabel(Messages.message("infoPanel.endTurnPanel.text"), JLabel.CENTER);
        private JButton endTurnButton = new JButton(Messages.message("infoPanel.endTurnPanel.endTurnButton"));

        public EndTurnPanel() {
            super(new FlowLayout(FlowLayout.CENTER, 10, 10));

            add(endTurnLabel);
            add(endTurnButton);
            setOpaque(false);
            setSize(250, endTurnLabel.getPreferredSize().height + endTurnButton.getPreferredSize().height + 30);

            /* TODO:
              The action listener does not work, because this button looses it's focus.
              The reason why the focus gets lost should be found, in order to use the actionlistener.
            */
            /*endTurnButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    freeColClient.getInGameController().endTurn();
                }
            });*/

            endTurnButton.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    freeColClient.getInGameController().endTurn();
                }
            });
        }
    }
}
