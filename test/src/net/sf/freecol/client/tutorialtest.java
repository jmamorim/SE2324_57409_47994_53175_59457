/**
 *  Copyright (C) 2002-2022  The FreeCol Team
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

package net.sf.freecol.client;

import net.sf.freecol.common.model.*;
import net.sf.freecol.server.ServerTestHelper;
import net.sf.freecol.server.model.ServerUnit;
import net.sf.freecol.util.test.FreeColTestCase;


public class tutorialtest extends FreeColTestCase {

    private static final TileType plains
            = spec().getTileType("model.tile.plains");

    private static final TileType ocean
            = spec().getTileType("model.tile.ocean");

    private static final UnitType pioneerType
            = spec().getUnitType("model.unit.hardyPioneer");

    private static final UnitType caraveltype
            = spec().getUnitType("model.unit.caravel");


    @Override
    public void tearDown() throws Exception {
        ServerTestHelper.stopServerGame();
        super.tearDown();
    }


    public void testSimpleMove() {
        Game game = ServerTestHelper.startServerGame(getTestMap(plains));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Tile plain1 = map.getTile(5, 8);
            plain1.setExplored(dutch, true);
            Tile plain2 = map.getTile(5, 7);
            plain2.setExplored(dutch, true);

            Unit hardyPioneer = new ServerUnit(game, plain1, dutch,
                    pioneerType);

            client.getPreGameController().startGameHandler();
            assertEquals(plain1.getNeighbourOrNull(Direction.NE), plain2);
            final Unit.MoveType mt = hardyPioneer.getMoveType(Direction.NE);
            System.out.println("typeofmove"+mt);
            client.getInGameController().moveDirection(hardyPioneer,
                    Direction.NE, false);
            assertEquals(true, dutch.gethasMoved());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }


    public void testDisembark() {
        Game game = ServerTestHelper.startServerGame(getTestMap(ocean));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Tile ocean1 = map.getTile(5, 8);
            ocean1.setExplored(dutch, true);
            Tile plain1 = map.getTile(5, 7);
            plain1.setType(plains);
            plain1.setExplored(dutch, true);

            Unit caravel = new ServerUnit(game, ocean1, dutch,
                    caraveltype);

            client.getPreGameController().startGameHandler();
            assertEquals(ocean1.getNeighbourOrNull(Direction.NE), plain1);
            final Unit.MoveType mt = caravel.getMoveType(Direction.NE);
            client.getInGameController().moveDirection(caravel,
                    Direction.NE, false);
            assertEquals(true, dutch.gethasDisembarked());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testEndTurn() {
        Game game = ServerTestHelper.startServerGame(getTestMap(ocean));

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");

            client.getPreGameController().startGameHandler();
            dutch.endTurn();
            assertEquals(true, dutch.gethasendTurn());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testCreateSettlementAndPorts() {
        Game game = ServerTestHelper.startServerGame(getTestMap(ocean));

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Colony colony = getStandardColony(3, 1, 8);
            Player someGuy = colony.getOwner();

            client.getPreGameController().startGameHandler();
            assertFalse(someGuy.getColonyList().isEmpty());
            assertTrue(someGuy.getNumberOfPorts() != 0);
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testlearnskill() {
        Game game = ServerTestHelper.startServerGame(getTestMap(plains));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Tile plain1 = map.getTile(5, 8);
            plain1.setExplored(dutch, true);
            Tile plain2 = map.getTile(5, 7);
            plain2.addLostCityRumour(new LostCityRumour(game, plain2));
            plain2.setExplored(dutch, true);

            Unit hardyPioneer = new ServerUnit(game, plain1, dutch,
                    pioneerType);

            client.getPreGameController().startGameHandler();
            assertEquals(plain1.getNeighbourOrNull(Direction.NE), plain2);
            client.getInGameController().moveLearnSkilltest(hardyPioneer, Direction.NE);
            assertTrue(hardyPioneer.getOwner().gethasLearnSkill());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testfirstcontact() {
        Game game = ServerTestHelper.startServerGame(getTestMap(plains));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Player french = game.getPlayerByNationId("model.nation.french");
            Tile plain1 = map.getTile(5, 8);
            plain1.setExplored(dutch, true);
            Tile plain2 = map.getTile(5, 7);
            plain2.addLostCityRumour(new LostCityRumour(game, plain2));
            plain2.setExplored(dutch, true);


            client.getPreGameController().startGameHandler();
            assertEquals(plain1.getNeighbourOrNull(Direction.NE), plain2);
            client.getInGameController().firstContactHandlertest(dutch, french, plain2 );
            assertTrue(dutch.gethasfirstContact());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testExploreLostCityRumours() {
        Game game = ServerTestHelper.startServerGame(getTestMap(plains));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Tile plain1 = map.getTile(5, 8);
            plain1.setExplored(dutch, true);
            Tile plain2 = map.getTile(5, 7);
            plain2.addLostCityRumour(new LostCityRumour(game, plain2));
            plain2.setExplored(dutch, true);

            Unit hardyPioneer = new ServerUnit(game, plain1, dutch,
                    pioneerType);

            client.getPreGameController().startGameHandler();
            assertEquals(plain1.getNeighbourOrNull(Direction.NE), plain2);
            client.getInGameController().moveExploretest(hardyPioneer, Direction.NE);
            assertTrue(hardyPioneer.getOwner().gethasExpRumours());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testmovetoeurope() {
        Game game = ServerTestHelper.startServerGame(getTestMap(plains));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Tile plain1 = map.getTile(5, 8);
            plain1.setExplored(dutch, true);

            Unit hardyPioneer = new ServerUnit(game, plain1, dutch,
                    pioneerType);

            client.getPreGameController().startGameHandler();
            client.getInGameController().moveTowardEuropetest(hardyPioneer,
                    new Europe(game,"europe"));
            assertTrue(hardyPioneer.getOwner().getHasGoEurope());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    //this part is still in progress
    public void testSellGoods() {
        Game game = ServerTestHelper.startServerGame(getTestMap(plains));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());
            Player dutch = game.getPlayerByNationId("model.nation.dutch");

            client.setMyPlayer(dutch);
            client.getInGameController().sellGoodstest(new Goods(game, "horses"));
            assertTrue(dutch.gethassellGoods());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testemigrate() {
        Game game = ServerTestHelper.startServerGame(getTestMap(plains));

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());
            Player dutch = game.getPlayerByNationId("model.nation.dutch");

            client.getInGameController().emigrationtest(dutch, 1, false);
            assertTrue(dutch.gethasRecruit());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }
}
