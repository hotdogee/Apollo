package org.bbop.apollo.web.tools;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bbop.apollo.web.datastore.JEDatabase;
import org.bbop.apollo.web.datastore.history.JEHistoryDatabase;
import org.bbop.apollo.web.datastore.history.TransactionList;
import org.gmod.gbol.simpleObject.Feature;

import java.util.Iterator;
import java.util.Scanner;


public class JEDatabaseMerger {

    private final static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public static void mergeHistoryDbs(String[] inputDbs, String outputDb, boolean forceOverwrite) {
        Scanner scanner = new Scanner(System.in);
        JEHistoryDatabase out = new JEHistoryDatabase(outputDb);
        for (String inputDb : inputDbs) {
            JEHistoryDatabase in = new JEHistoryDatabase(inputDb, true, 0);
            Iterator<TransactionList> iter = in.getTransactionListIterator();
            while (iter.hasNext()) {
                TransactionList transactionList = iter.next();
                String uniqueName = transactionList.get(0).getFeatureUniqueName();
                if (out.getTransactionListForFeature(uniqueName).size() > 0 && !forceOverwrite) {
                    logger.info(uniqueName + " exists in output database.  Overwrite [Y/N]?: ");
                    String response = scanner.nextLine();
                    if (!response.equalsIgnoreCase("Y")) {
                        continue;
                    }
                }
                out.writeTransactionListForFeature(uniqueName, transactionList);
            }
        }
        out.close();
    }
    
    public static void mergeDbs(String[] inputDbs, String outputDb, boolean forceOverwrite) {
        Scanner scanner = new Scanner(System.in);
        JEDatabase out = new JEDatabase(outputDb, false);
        for (String inputDb : inputDbs) {
            JEDatabase in = new JEDatabase(inputDb, true);
            Iterator<Feature> iter = in.getFeatureIterator();
            while (iter.hasNext()) {
                Feature feature = iter.next();
                if (out.getFeatureByUniqueName(feature.getUniqueName()) != null && !forceOverwrite) {
                    logger.info(feature.getUniqueName() + " exists in output database.  Overwrite [Y/N]?: ");
                    String response = scanner.nextLine();
                    if (!response.equalsIgnoreCase("Y")) {
                        continue;
                    }
                }
                out.writeFeature(feature);
            }
        }
    }
    
    public static CommandLine parseOptions(String[] args) {
        CommandLineParser parser = new GnuParser();
        Options options = new Options();
        options.addOption("i", "input", true, "input JE database (can be called multiple times)");
        options.addOption("o", "output", true, "output JE database");
        options.addOption("h", "help", false, "print help");
        options.addOption("f", "force-overwrite", false, "force overwritting of existing features by id");
        options.addOption("H", "history", false, "input database(s) are history databases");
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
            if (line.hasOption('h')) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("JEDatabaseMerger", options);
                System.exit(1);
            }
            else if (!line.hasOption('i')) {
                logger.error("Missing required input database(s)");
                System.exit(1);
            }
            else if (!line.hasOption('o')) {
                logger.error("Missing required output database");
                System.exit(1);
            }
        }
        catch( ParseException exp ) {
            logger.error("Unexpected exception:" + exp.getMessage());
            System.exit(1);
        }
        return line;
    }
    
    public static void main(String[] args) {
        try {
            CommandLine line = parseOptions(args);
            if (line.hasOption('H')) {
                mergeHistoryDbs(line.getOptionValues('i'), line.getOptionValue('o'), line.hasOption('f'));
            }
            else {
                mergeDbs(line.getOptionValues('i'), line.getOptionValue('o'), line.hasOption('f'));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
    }
    
}
