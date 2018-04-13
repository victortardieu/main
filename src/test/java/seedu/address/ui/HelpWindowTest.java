package seedu.address.ui;

import guitests.guihandles.HelpWindowHandle;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.HelpWindow.USERGUIDE_FILE_PATH;

public class HelpWindowTest extends GuiUnitTest {

    private HelpWindow helpWindow;
    private HelpWindowHandle helpWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> helpWindow = new HelpWindow());
        Stage helpWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(helpWindow.getRoot().getScene()));
        FxToolkit.showStage();
        helpWindowHandle = new HelpWindowHandle(helpWindowStage);
    }

    @Test
    public void display() {
        URL expectedHelpPage = HelpWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedHelpPage, helpWindowHandle.getLoadedUrl());
    }
}
