package seedu.address;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Catalogue;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Account;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AccountListStorage;
import seedu.address.storage.CatalogueStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.SerialisedAccountListStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlCatalogueStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;


/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Catalogue ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        CatalogueStorage catalogueStorage = new XmlCatalogueStorage(userPrefs.getCatalogueFilePath());
        AccountListStorage accountListStorage = new SerialisedAccountListStorage(userPrefs.getAccountListFilePath());
        storage = new StorageManager(catalogueStorage, userPrefsStorage, accountListStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);


        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s C\catalogue and {@code userPrefs}. <br>
     * The data from the sample Catalogue will be used instead if {@code storage}'s Catalogue is not found,
     * or an empty Catalogue will be used instead if errors occur when reading {@code storage}'s Catalogue.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyCatalogue> catalogueOptional;
        Optional<UniqueAccountList> accountListOptional;
        ReadOnlyCatalogue initialData;
        UniqueAccountList initlaAccountList;
        //@@author QiuHaohao
        try {
            catalogueOptional = storage.readCatalogue();
            if (!catalogueOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Catalogue");
            }
            initialData = catalogueOptional.orElseGet(SampleDataUtil::getSampleCatalogue);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Catalogue");
            initialData = new Catalogue();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Catalogue");
            initialData = new Catalogue();
        }

        try {
            accountListOptional = storage.readAccountList();
            if (!accountListOptional.isPresent()) {
                logger.info("AccountList file not found. Will be starting with an accountList with only admin");
                initlaAccountList = new UniqueAccountList();
            } else {
                initlaAccountList = accountListOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("AccountList file not in the correct format. "
                + "Will be starting with an accountList with only admin");
            initlaAccountList = new UniqueAccountList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the AccountList file. "
                + "Will be starting with an accountList with only admin");
            System.out.print(e.getMessage());
            initlaAccountList = new UniqueAccountList();
        }

        try {
            if (!initlaAccountList.contains(Account.createDefaultAdminAccount())) {
                initlaAccountList.add(Account.createDefaultAdminAccount());
            }
        } catch (DuplicateAccountException e) {
            e.printStackTrace();
        }
        return new ModelManager(initialData, initlaAccountList, userPrefs);
        //@@author
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        String prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Catalogue");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Catalogue " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Catalogue ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }
}
