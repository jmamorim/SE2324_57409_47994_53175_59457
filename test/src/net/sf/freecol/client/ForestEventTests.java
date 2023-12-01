package net.sf.freecol.client;

import net.sf.freecol.common.model.*;
import net.sf.freecol.server.ServerTestHelper;
import net.sf.freecol.server.model.ServerUnit;
import net.sf.freecol.util.test.FreeColTestCase;

public class ForestEventTests extends FreeColTestCase{


    private static final UnitType pioneerType
            = spec().getUnitType("model.unit.hardyPioneer");

    private static final TileType forest
            = spec().getTileType("model.tile.tropicalForest");



    @Override
    public void tearDown() throws Exception {
        ServerTestHelper.stopServerGame();
        super.tearDown();
    }


    public void testEndTurnForest() {
        Game game = ServerTestHelper.startServerGame(getTestMap(forest));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Tile forest1 = map.getTile(5, 8);
            forest1.setExplored(dutch, true);
            Tile forest2 = map.getTile(5, 7);
            forest2.setExplored(dutch, true);

            Unit hardyPioneer = new ServerUnit(game, forest1, dutch,
                    pioneerType);

            client.getPreGameController().startGameHandler();
            assertEquals(forest1.getNeighbourOrNull(Direction.NE), forest2);
            System.out.println("test1");
            client.getInGameController().moveTiletest(hardyPioneer,
                    Direction.NE, 0.05);
            assertTrue(hardyPioneer.getOwner().gethasendTurn());
            assertEquals(0, hardyPioneer.getOwner().getGold());
            assertEquals(3, hardyPioneer.getMovesLeft());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testGainGoldForest() {
        Game game = ServerTestHelper.startServerGame(getTestMap(forest));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Tile forest1 = map.getTile(5, 8);
            forest1.setExplored(dutch, true);
            Tile forest2 = map.getTile(5, 7);
            forest2.setExplored(dutch, true);

            Unit hardyPioneer = new ServerUnit(game, forest1, dutch,
                    pioneerType);

            client.getPreGameController().startGameHandler();
            assertEquals(forest1.getNeighbourOrNull(Direction.NE), forest2);
            System.out.println("test2");
            client.getInGameController().moveTiletest(hardyPioneer,
                    Direction.NE, 0.3);
            assertFalse(hardyPioneer.getOwner().gethasendTurn());
            assertEquals(100, hardyPioneer.getOwner().getGold());
            assertEquals(3, hardyPioneer.getMovesLeft());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testGainMovementForest() {
        Game game = ServerTestHelper.startServerGame(getTestMap(forest));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Tile forest1 = map.getTile(5, 8);
            forest1.setExplored(dutch, true);
            Tile forest2 = map.getTile(5, 7);
            forest2.setExplored(dutch, true);

            Unit hardyPioneer = new ServerUnit(game, forest1, dutch,
                    pioneerType);

            client.getPreGameController().startGameHandler();
            assertEquals(forest1.getNeighbourOrNull(Direction.NE), forest2);
            System.out.println("test3");
            client.getInGameController().moveTiletest(hardyPioneer,
                    Direction.NE, 0.6);
            assertFalse(hardyPioneer.getOwner().gethasendTurn());
            assertEquals(0, hardyPioneer.getOwner().getGold());
            assertEquals(6, hardyPioneer.getMovesLeft());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }

    public void testNothingHappenForest() {
        Game game = ServerTestHelper.startServerGame(getTestMap(forest));
        Map map = game.getMap();

        FreeColClient client = null;
        try {
            client = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());

            Player dutch = game.getPlayerByNationId("model.nation.dutch");
            Tile forest1 = map.getTile(5, 8);
            forest1.setExplored(dutch, true);
            Tile forest2 = map.getTile(5, 7);
            forest2.setExplored(dutch, true);

            Unit hardyPioneer = new ServerUnit(game, forest1, dutch,
                    pioneerType);

            client.getPreGameController().startGameHandler();
            assertEquals(forest1.getNeighbourOrNull(Direction.NE), forest2);
            System.out.println("test4");
            client.getInGameController().moveTiletest(hardyPioneer,
                    Direction.NE, 0.9);
            assertFalse(hardyPioneer.getOwner().gethasendTurn());
            assertEquals(0, hardyPioneer.getOwner().getGold());
            assertEquals(3, hardyPioneer.getMovesLeft());
        } finally {
            if (client != null) {
                ClientTestHelper.stopClient(client);
            }
        }
    }
}
