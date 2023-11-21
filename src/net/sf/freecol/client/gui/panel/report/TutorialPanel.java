package net.sf.freecol.client.gui.panel.report;

import java.awt.*;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.gui.FontLibrary;
import net.sf.freecol.client.gui.panel.BuildingPanel;
import net.sf.freecol.client.gui.panel.FreeColProgressBar;
import net.sf.freecol.client.gui.panel.Utility;
import net.sf.freecol.common.model.Building;
import net.sf.freecol.common.model.Colony;
import net.sf.freecol.common.model.GoodsType;
import net.sf.freecol.common.model.Player;
import net.sf.freecol.common.model.Specification;
import net.sf.freecol.common.model.WorkLocation;

import static net.sf.freecol.common.util.CollectionUtils.*;

public class TutorialPanel extends ReportPanel {
    public TutorialPanel(FreeColClient freeColClient) {
        super(freeColClient, "TutorialMissions");

        final Font font = FontLibrary.getScaledFont("normal-bold-smaller");
        final Player player = getMyPlayer();
        final Specification spec = getSpecification();

        reportPanel.setLayout(new MigLayout("wrap 6, fill", "center"));

        // Add text for settling a new colony mission
        //change in move action
        JLabel settleMissionLabel = new JLabel("Mission 1:");
        settleMissionLabel.setFont(font);
        reportPanel.add(settleMissionLabel, SPAN_SPLIT_2);

        JLabel settleMissionDescription = new JLabel("Move an unit.");
        settleMissionDescription.setForeground(Color.RED);
        settleMissionDescription.setFont(font);
        reportPanel.add(settleMissionDescription, "span");

        // Add text for settling a new colony mission
        //change in Disembark action
        JLabel settleMission2 = new JLabel("Mission 2:");
        settleMission2.setFont(font);
        reportPanel.add(settleMission2, SPAN_SPLIT_2);

        JLabel settleMission2Description = new JLabel("Disembark an unit.");
        settleMission2Description.setFont(font);
        reportPanel.add(settleMission2Description, "span");


        // Add text for settling a new colony mission
        //change in end turn action
        JLabel settleMission3 = new JLabel("Mission 3:");
        settleMission3.setFont(font);
        reportPanel.add(settleMission3, SPAN_SPLIT_2);

        JLabel settleMission3Description = new JLabel("End a turn.");
        settleMission3Description.setFont(font);
        reportPanel.add(settleMission2Description, "span");

        // Add text for settling a new colony mission
        //change in build colony action
        JLabel settleMission4 = new JLabel("Mission 4:");
        settleMission4.setFont(font);
        reportPanel.add(settleMission3, SPAN_SPLIT_2);

        JLabel settleMission4Description = new JLabel("Explore and find a suitable spot for you colony.");
        settleMission4Description.setFont(font);
        reportPanel.add(settleMission3Description, "span");

        // Add text for settling a new colony mission

        JLabel settleMission5 = new JLabel("Mission 5:");
        settleMission5.setFont(font);
        reportPanel.add(settleMission4, SPAN_SPLIT_2);

        JLabel settleMission5Description = new JLabel("Explore a Lost Cities rumour.");
        settleMission5Description.setFont(font);
        reportPanel.add(settleMission5Description, "span");

        // Add text for settling a new colony mission
        JLabel settleMission6 = new JLabel("Mission 6:");
        settleMission6.setFont(font);
        reportPanel.add(settleMission5, SPAN_SPLIT_2);

        JLabel settleMission6Description = new JLabel("Put a unit living with natives.");
        settleMission6Description.setFont(font);
        reportPanel.add(settleMission5Description, "span");

        // Add text for settling a new colony mission
        JLabel settleMission7 = new JLabel("Mission 7:");
        settleMission7.setFont(font);
        reportPanel.add(settleMission6, SPAN_SPLIT_2);

        JLabel settleMission7Description = new JLabel("Recruit a unit from Europe.");
        settleMission7Description.setFont(font);
        reportPanel.add(settleMission6Description, "span");

        // Add text for settling a new colony mission
        JLabel settleMission8 = new JLabel("Mission 8:");
        settleMission8.setFont(font);
        reportPanel.add(settleMission6, SPAN_SPLIT_2);

        JLabel settleMission8Description = new JLabel("Sell products to Europe.");
        settleMission8Description.setFont(font);
        reportPanel.add(settleMission6Description, "span");

        // Add text for settling a new colony mission
        JLabel settleMission9 = new JLabel("Mission 9:");
        settleMission9.setFont(font);
        reportPanel.add(settleMission6, SPAN_SPLIT_2);

        JLabel settleMission9Description = new JLabel("Have a first contact with a native.");
        settleMission9Description.setFont(font);
        reportPanel.add(settleMission6Description, "span");

        // Iterate through immigration goods types
        for (GoodsType gt : spec.getImmigrationGoodsTypeList()) {
            JLabel crosses = Utility.localizedLabel(gt);
            crosses.setFont(font);
            reportPanel.add(crosses, SPAN_SPLIT_2);
            FreeColProgressBar progressBar
                    = new FreeColProgressBar(freeColClient, gt, 0,
                    player.getImmigrationRequired(), player.getImmigration(),
                    player.getTotalImmigrationProduction());
            reportPanel.add(progressBar, "span");
        }
    }
}

