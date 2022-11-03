
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.FileDao;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

/**
 *
 * @author salajrawi
 */
public class FileDaoImpl implements FileDao{

    // Declare and initialize HashMap to store items after unmarshalling
    private final Map<String, Item> items = new HashMap<>();
    private static final String ITEM_FILE = "items.txt";
    private static final String DELIMITER = ",";

    /*
    This process converts XML content to Java object
    Split each line / String from the txt file into tokens at the delimiter
    Return an item
     */
    @Override
    public Item unmarshallItem(String line) {
         //implement
        String[] itemTokens = line.split(DELIMITER);
        // Initialize the item name and cost
        Item item = new Item();
        String itemName = itemTokens[0];

        BigDecimal empt = new BigDecimal(itemTokens[1]);
        item.setCost(empt);

        BigDecimal price = new BigDecimal(item.getCost());
        out.println(price);
        int quantity = Integer.parseInt(itemTokens[2]);
        // Instantiate new Item object

//        itemFromFile.setCost(new BigDecimal(itemTokens[1]).setScale(2, RoundingMode.DOWN));
//
//        itemFromFile.setNumInventoryItems(Integer.parseInt(itemTokens[2]));

        return new Item(itemName, item.getCost(), quantity);

    }

    /*
    This is the process of writing Java objects to XML file
    It returns a String which includes the item's properties separated by the delimiter
     */
    @Override
    public String marshallItem(Item item) {
        return item.getName() + DELIMITER + item.getCost() + DELIMITER + item.getNumInventoryItems() + "\n";
    }

    // Write out the item object to the file
    @Override
    public void writeFile(List<Item> list) throws VendingMachineException{
        PrintWriter out;
         try {
            //implement
            // Initialize   the PrintWriter object, pass the file that item is going to be written to

             out = new PrintWriter(new FileWriter(ITEM_FILE));
        }
        catch(IOException e)
        {
            throw new VendingMachineException("Could not save item data", e);
        }

         String itemAsText;
         // Get the collection of items and iterate over them
        for (Item currentItem : list) {
            // turn the Item object into a String
            itemAsText = marshallItem(currentItem);
            // Print the Item object to the file
            out.println(itemAsText);
            // Force PrintWriter to write Line to the file
            out.flush();
        }
        // Clean up
        out.close();

    }

    /*
    This process is to read data from a file
    For each line in the file: read line by line into a String variable
    Pass the line to unmarshallItem method to parse it into Item object
     */
    @Override
    public Map<String,Item> readFile(String file) throws VendingMachineException{
        // Implement
        Scanner scanner;

        try {
            scanner = new Scanner(
                        new BufferedReader(
                                new FileReader(file)
                        )
            );
        }
        catch(FileNotFoundException e)
        {
            throw new VendingMachineException("File not found", e);
        }
        //  For each line in the file: read line by line into a String variable
        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getName(), currentItem);
        }
        scanner.close();

        return items;
        
    }
    
}
