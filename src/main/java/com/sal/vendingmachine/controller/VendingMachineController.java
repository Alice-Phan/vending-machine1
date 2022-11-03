
package com.sal.vendingmachine.controller;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Coins;
import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Salajrawi
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineService serviceLayer;


    // Alice's added code


    public VendingMachineController() throws VendingMachineException {
       //implement
         UserIO io = new UserIOImpl();
         view = new VendingMachineView(io);
         serviceLayer = new VendingMachineServiceImpl();


    }
    
    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        //implement
        // Initialize View and Service
        this.view = view;
        this.serviceLayer = service;

    }

    public void run() throws VendingMachineException {
        // Initialized the beginning balance is $0.00
        BigDecimal balance = new BigDecimal(0.0);
        boolean start = true;

        // Display welcome banner
        view.displayWelcomeBanner();

        try{
            while (start) {
            view.printMenu();
            view.displayBalance(balance);
            String operation = view.getMenuSelection();
            switch(operation) {
                case "1":  // add funds
                    balance=addFunds(balance);
                    break;
                case "2":  // buy items
                    try{
                        balance=buyItems(balance);
                    }catch(VendingMachineItemInventoryException | VendingMachineInsufficientFundsException e){
                        view.displayBalance(balance);
                        view.displayErrorMessage(e.getMessage());
                    }
                    break;
                case "3": // quit
                    try{
                        quit(balance);
                    }catch(VendingMachineInsufficientFundsException e){
                        view.displayBalance(balance);
                        view.displayErrorMessage(e.getMessage());
                    }
                    start = false;
                    break;
                default:
                    view.displayUnknownCommand();
            }
        }
        }catch(VendingMachineException e){
            //implement
            view.displayErrorMessage(e.getMessage());
        }

        view.displayQuitMessage();
    }
    
    public BigDecimal addFunds(BigDecimal balance){
      //implement
        return balance.add(view.displayAndGetFunds());
    }
    
    public BigDecimal buyItems(BigDecimal balance) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException{
       //implement
        // Get item selection
        ArrayList<String> items = view.printAllItems(serviceLayer.listAllItems());
        int selection = view.getItemSelection();
        Change change = new Change();
        String[] itemAsString = items.get(selection).split(", ");
        Item selectedItem = serviceLayer.getItem(itemAsString[0]);

//        view.allItemsBanner();
//        view.printAllItems(serviceLayer.listAllItems());
        // Sell item
        try {
            serviceLayer.sellItem(balance, selectedItem);
        } catch (VendingMachineException e) {
            view.displayErrorMessage(e.getMessage());
        }
        // Display successful purchase banner
        view.purchaseSucceeded();

        // Calculate the change from the remaining balance after purchasing item
        HashMap<Coins, Integer> changeMap = change.getChange(balance);
        view.printChanges(changeMap);

        return balance.subtract(selectedItem.getCost());

    }
    
    public void quit(BigDecimal balance) throws VendingMachineInsufficientFundsException{
       //implement
        // Make sure balance set to zero before operating another purchase

        view.displayBalance(balance);
        view.displayQuitMessage();
    }
}
