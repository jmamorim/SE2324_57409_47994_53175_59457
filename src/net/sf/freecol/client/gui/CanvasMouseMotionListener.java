/**
 *  Copyright (C) 2002-2022   The FreeCol Team
 *
 *  This file is part of FreeCol.
 *
 *  FreeCol is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FreeCol is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FreeCol.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.freecol.client.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

import net.sf.freecol.client.ClientOptions;
import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.control.FreeColClientHolder;


/**
 * Listens to the mouse being moved at the level of the Canvas.
 */
public final class CanvasMouseMotionListener extends FreeColClientHolder implements MouseMotionListener {

    private static final Logger logger = Logger.getLogger(CanvasMouseMotionListener.class.getName());
    
    private final Scrolling scrolling;

    /**
     * Creates a new listener for mouse movement.
     *
     * @param freeColClient The {@code FreeColClient} for the game.
     */
    public CanvasMouseMotionListener(FreeColClient freeColClient, Scrolling scrolling) {
        super(freeColClient);
        
        this.scrolling = scrolling;
    }


    // Interface MouseMotionListener

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseMoved(MouseEvent me) {
        scrolling.performAutoScrollIfActive(me);

        getGUI().updateGoto(me.getX(), me.getY(), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseDragged(MouseEvent me) {
        // Check if the left mouse button is pressed
        if ((me.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != MouseEvent.BUTTON1_DOWN_MASK) {
            return;
        }
        //mechanism close to the one in MiniMap.java just adapted to use map dimensions
        double sensitivity = 0.1;
        final Point focusPoint = getGUI().getFocusMapPoint();

        //calculate the vector to add to the focus point
        int deltaX = (int) ((me.getX() - getGUI().getMapViewDimension().width/2) * sensitivity);
        int deltaY = (int) ((me.getY() - getGUI().getMapViewDimension().height/2) * sensitivity);

        // Adjust the camera position based on the mouse movement
        final int mapPointX = focusPoint.x + deltaX;
        final int mapPointY = focusPoint.y + deltaY;

        getGUI().setFocusMapPoint(new Point(mapPointX, mapPointY));
    }
}
