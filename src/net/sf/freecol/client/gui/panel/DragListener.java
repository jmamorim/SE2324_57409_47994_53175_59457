
package net.sf.freecol.client.gui.panel;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;

import net.sf.freecol.client.gui.i18n.Messages;
import net.sf.freecol.common.model.Building;
import net.sf.freecol.common.model.Colony;
import net.sf.freecol.common.model.ColonyTile;
import net.sf.freecol.common.model.Goods;
import net.sf.freecol.common.model.Tile;
import net.sf.freecol.common.model.Unit;


/**
* A DragListener should be attached to Swing components that have a
* TransferHandler attached. The DragListener will make sure that the
* Swing component to which it is attached is draggable (moveable to be
* precise).
*/
public final class DragListener extends MouseAdapter {

    public static final String  COPYRIGHT = "Copyright (C) 2003-2005 The FreeCol Team";
    public static final String  LICENSE = "http://www.gnu.org/licenses/gpl.html";
    public static final String  REVISION = "$Revision$";
    
    private final JLayeredPane parentPanel;

    /**
    * The constructor to use.
    * @param parentPanel The layered pane that contains the components to which a
    * DragListener might be attached.
    */
    public DragListener(JLayeredPane parentPanel) {
        this.parentPanel = parentPanel;
    }
    
    /**
    * Gets called when the mouse was pressed on a Swing component that has this
    * object as a MouseListener.
    * @param e The event that holds the information about the mouse click.
    */
    public void mousePressed(MouseEvent e) {
        JComponent comp = (JComponent)e.getSource();

        //Does not work on some platforms:
        //if (e.isPopupTrigger() && (comp instanceof UnitLabel)) {
        if ((e.getButton() == MouseEvent.BUTTON3 || e.isPopupTrigger())) {
            if (comp instanceof UnitLabel) {
                UnitLabel unitLabel = (UnitLabel) comp;
                Unit tempUnit = unitLabel.getUnit();

                JPopupMenu menu = new JPopupMenu("Unit");
                JMenuItem menuItem;
            
                if (tempUnit.getLocation() instanceof Tile && tempUnit.getTile().getColony() != null) {
                    menuItem = new JMenuItem( Messages.message("activateUnit") );
                    menuItem.setActionCommand(String.valueOf(UnitLabel.ACTIVATE_UNIT));
                    menuItem.addActionListener(unitLabel);
                    menuItem.setEnabled((tempUnit.getMovesLeft() > 0));
                    menu.add(menuItem);
                    menu.addSeparator();
                }
            
                if ((tempUnit.isColonist() || tempUnit.getType() == Unit.INDIAN_CONVERT) && tempUnit.getLocation() instanceof ColonyTile) {
                    ColonyTile colonyTile = (ColonyTile) tempUnit.getLocation();
                    menuItem = new JMenuItem( Messages.message("beAFarmer") + " (" + tempUnit.getFarmedPotential(Goods.FOOD, colonyTile.getWorkTile()) + "/"
                                              + colonyTile.getColony().getVacantColonyTileProductionFor(tempUnit, Goods.FOOD) + ")");
                    menuItem.setActionCommand(String.valueOf(UnitLabel.WORKTYPE_FOOD));
                    menuItem.addActionListener(unitLabel);
                    menu.add(menuItem);
                    menuItem = new JMenuItem( Messages.message("beASugarPlanter") + " (" + tempUnit.getFarmedPotential(Goods.SUGAR, colonyTile.getWorkTile()) + "/"
                                              + colonyTile.getColony().getVacantColonyTileProductionFor(tempUnit, Goods.SUGAR) + ")");
                    menuItem.setActionCommand(String.valueOf(UnitLabel.WORKTYPE_SUGAR));
                    menuItem.addActionListener(unitLabel);
                    menu.add(menuItem);
                    menuItem = new JMenuItem( Messages.message("beATobaccoPlanter") + " (" + tempUnit.getFarmedPotential(Goods.TOBACCO, colonyTile.getWorkTile()) + "/"
                                              + colonyTile.getColony().getVacantColonyTileProductionFor(tempUnit, Goods.TOBACCO) + ")");
                    menuItem.setActionCommand(String.valueOf(UnitLabel.WORKTYPE_TOBACCO));
                    menuItem.addActionListener(unitLabel);
                    menu.add(menuItem);
                    menuItem = new JMenuItem( Messages.message("beAcottonPlanter") + " (" + tempUnit.getFarmedPotential(Goods.COTTON, colonyTile.getWorkTile()) + "/"
                                              + colonyTile.getColony().getVacantColonyTileProductionFor(tempUnit, Goods.COTTON) + ")");
                    menuItem.setActionCommand(String.valueOf(UnitLabel.WORKTYPE_COTTON));
                    menuItem.addActionListener(unitLabel);
                    menu.add(menuItem);
                    menuItem = new JMenuItem( Messages.message("beAFurTrapper") + " (" + tempUnit.getFarmedPotential(Goods.FURS, colonyTile.getWorkTile()) + "/"
                                              + colonyTile.getColony().getVacantColonyTileProductionFor(tempUnit, Goods.FURS) + ")");
                    menuItem.setActionCommand(String.valueOf(UnitLabel.WORKTYPE_FURS));
                    menuItem.addActionListener(unitLabel);
                    menu.add(menuItem);
                    menuItem = new JMenuItem( Messages.message("beALumberjack") + " (" + tempUnit.getFarmedPotential(Goods.LUMBER, colonyTile.getWorkTile()) + "/"
                                              + colonyTile.getColony().getVacantColonyTileProductionFor(tempUnit, Goods.LUMBER) + ")");
                    menuItem.setActionCommand(String.valueOf(UnitLabel.WORKTYPE_LUMBER));
                    menuItem.addActionListener(unitLabel);
                    menu.add(menuItem);
                    menuItem = new JMenuItem( Messages.message("beAnOreMiner") + " (" + tempUnit.getFarmedPotential(Goods.ORE, colonyTile.getWorkTile()) + "/"
                                              + colonyTile.getColony().getVacantColonyTileProductionFor(tempUnit, Goods.ORE) + ")");
                    menuItem.setActionCommand(String.valueOf(UnitLabel.WORKTYPE_ORE));
                    menuItem.addActionListener(unitLabel);
                    menu.add(menuItem);
                    menuItem = new JMenuItem( Messages.message("beASilverMiner") + " (" + tempUnit.getFarmedPotential(Goods.SILVER, colonyTile.getWorkTile()) + "/"
                                              + colonyTile.getColony().getVacantColonyTileProductionFor(tempUnit, Goods.SILVER) + ")");
                    menuItem.setActionCommand(String.valueOf(UnitLabel.WORKTYPE_SILVER));
                    menuItem.addActionListener(unitLabel);
                    menu.add(menuItem);

                    menu.addSeparator();
                }


                if (tempUnit.isColonist()) {
                    if (!tempUnit.isPioneer() && !tempUnit.isMissionary() && tempUnit.canBeArmed()) {
                        if (tempUnit.isArmed()) {
                            menuItem = new JMenuItem( Messages.message("disarm") );
                        } else {
                            if (tempUnit.getTile() == null) { // -> in Europe
                                menuItem = new JMenuItem( Messages.message("arm") + " (" + tempUnit.getGame().getMarket().getBidPrice(Goods.MUSKETS, 50) + " gold)");
                            } else {
                                menuItem = new JMenuItem( Messages.message("arm") );
                            }
                        }
                        menuItem.setActionCommand(String.valueOf(UnitLabel.ARM));
                        menuItem.addActionListener(unitLabel);
                        menu.add(menuItem);
                    }

                    if (!tempUnit.isPioneer() && !tempUnit.isMissionary() && tempUnit.canBeMounted()) {
                        if (tempUnit.isMounted()) {
                            menuItem = new JMenuItem( Messages.message("removeHorses") );
                        } else {
                            if (tempUnit.getTile() == null) { // -> in Europe
                                menuItem = new JMenuItem( Messages.message("mount") + " (" + tempUnit.getGame().getMarket().getBidPrice(Goods.HORSES, 50) + " gold)" );
                            } else {
                                menuItem = new JMenuItem( Messages.message("mount") );
                            }
                        }
                        menuItem.setActionCommand(String.valueOf(UnitLabel.MOUNT));
                        menuItem.addActionListener(unitLabel);
                        menu.add(menuItem);
                    }

                    if (!tempUnit.isArmed() && !tempUnit.isMounted() && !tempUnit.isMissionary() && tempUnit.canBeEquippedWithTools()) {
                        if (tempUnit.isPioneer()) {
                            menuItem = new JMenuItem( Messages.message("removeTools") );
                        } else {
                            if (tempUnit.getTile() == null) { // -> in Europe
                                int amount = 100;
                                int price = tempUnit.getGame().getMarket().getBidPrice(Goods.TOOLS, amount);
                                if (price <= tempUnit.getOwner().getGold()) {
                                    menuItem = new JMenuItem( Messages.message("equipWithTools") + " (" + price + " gold)" );
                                } else {
                                    while (price > tempUnit.getOwner().getGold()) {
                                        amount -= 20;
                                        price = tempUnit.getGame().getMarket().getBidPrice(Goods.TOOLS, amount);
                                    }
                                    menuItem = new JMenuItem(Messages.message("equipWith") + ' ' + amount + " " + Messages.message("model.goods.Tools") + " (" + price + " " + Messages.message("gold") + ")");

                                }
                            } else {
                                menuItem = new JMenuItem( Messages.message("equipWithTools") );
                            }
                        }
                        menuItem.setActionCommand(String.valueOf(UnitLabel.TOOLS));
                        menuItem.addActionListener(unitLabel);
                        menu.add(menuItem);
                    }

                    if (!tempUnit.isArmed() && !tempUnit.isMounted() && !tempUnit.isPioneer() && tempUnit.canBeDressedAsMissionary()) {

                        if (tempUnit.isMissionary()) {
                            menuItem = new JMenuItem( Messages.message("cancelMissionaryStatus") );
                        } else {
                            menuItem = new JMenuItem( Messages.message("blessAsMissionaries") );
                        }
                        menuItem.setActionCommand(String.valueOf(UnitLabel.DRESS));
                        menuItem.addActionListener(unitLabel);
                        menu.add(menuItem);
                    }

                    if (tempUnit.getType() != Unit.INDIAN_CONVERT && tempUnit.getType() != Unit.PETTY_CRIMINAL &&
                        tempUnit.getType() != Unit.INDENTURED_SERVANT && tempUnit.getType() != Unit.FREE_COLONIST) {

                        if (menu.getSubElements().length > 0) {
                            menu.addSeparator();
                        }

                        menuItem = new JMenuItem( Messages.message("clearSpeciality") );
                        menuItem.setActionCommand(String.valueOf(UnitLabel.CLEAR_SPECIALITY));
                        menuItem.addActionListener(unitLabel);
                        menu.add(menuItem);
                    }
                }

                if (menu.getSubElements().length > 0) {
                    menu.show(comp, e.getX(), e.getY());
                }

            } else if (comp instanceof GoodsLabel) {
                if (parentPanel instanceof ColonyPanel) {
                    ColonyPanel colonyPanel = (ColonyPanel) parentPanel;
                    Colony colony = colonyPanel.getColony();
                    if (colony.getBuilding(Building.CUSTOM_HOUSE).isBuilt()) {
                        colonyPanel.toggleExports(((GoodsLabel) comp).getGoods());
                        comp.repaint();
                    }
                }
            }
        } else {
            TransferHandler handler = comp.getTransferHandler();

            if (e.isShiftDown()) {
                if (comp instanceof GoodsLabel) {
                    ((GoodsLabel) comp).setPartialChosen(true);
                } else if (comp instanceof MarketLabel) {
                    ((MarketLabel) comp).setPartialChosen(true);
                }
            } else {
                if (comp instanceof GoodsLabel) {
                    ((GoodsLabel) comp).setPartialChosen(false);
                } else if (comp instanceof MarketLabel) {
                    ((MarketLabel) comp).setPartialChosen(false);
                    ((MarketLabel) comp).setAmount(100);
                }
            }

            if ((comp instanceof UnitLabel) && (((UnitLabel)comp).getUnit().isCarrier())) {
                if (parentPanel instanceof EuropePanel) {
                    Unit u = ((UnitLabel) comp).getUnit();
                    if (u.getState() != Unit.TO_AMERICA && u.getState() != Unit.TO_EUROPE) {
                        ((EuropePanel) parentPanel).setSelectedUnitLabel((UnitLabel)comp);
                    }
                } else if (parentPanel instanceof ColonyPanel) {
                    ((ColonyPanel) parentPanel).setSelectedUnitLabel((UnitLabel)comp);
                }
            }
            
            handler.exportAsDrag(comp, e, TransferHandler.COPY);
        }
    }
}
