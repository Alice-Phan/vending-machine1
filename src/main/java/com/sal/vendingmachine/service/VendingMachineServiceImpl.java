
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.AuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author salajrawi
 */
public class VendingMachineServiceImpl implements VendingMachineService{

    private VendingMachineDao dao;
    private AuditDao auditDao;

    public VendingMachineServiceImpl() throws VendingMachineException {
    //implement
         dao = new VendingMachineDaoImpl();
         auditDao = new AuditDaoImpl();

    }
    // Constructor passes in 2 arguments dao and auditDap
    public VendingMachineServiceImpl(VendingMachineDao dao, AuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    // Get item by its name from dao object
    @Override
    public Item getItem(String name) throws VendingMachineException, VendingMachineItemInventoryException{
        //implement
        return dao.getItem(name);
    }

    @Override
    public List<Item> listAllItems() throws VendingMachineException{
//        Item item1 = new Item("food", new BigDecimal("5"), 25);
//        Item item2 = new Item("drink", new BigDecimal("2"), 10);
//        List<Item> dummieList = new ArrayList<>();
//
//        dummieList.add(item1);
//        dummieList.add(item2);
//        return dummieList;

        return dao.listAllItems()
                .stream()
                .filter(item->item.getNumInventoryItems()>0)
                .collect(Collectors.toList());
    }

    // Add item to the dao
    @Override
    public Item addItem(Item item) throws VendingMachineException{
     //implement
     // Check if item was already in the vending machine
        if(dao.getItem(item.getName()) != null) {
            throw new VendingMachineException("This item already exists.");
        }
        dao.addItem(item);
        // tell auditDao to write entry
        auditDao.writeAuditEntry("Item " + item.getName() + "added.");
        return item;
        // Courtney's code:
//        auditDao.writeAuditEntry(item.getName() + "added.");
//        return dao.addItem(item);

    }

    @Override
    public Item removeItem(Item item) throws VendingMachineException{
        //implement
        auditDao.writeAuditEntry("Item " + item.getName() + "removed.");
        return dao.removeItem(item);
    }

    // Update inventory of each item in the vending machine
    @Override
    public Item changeInventoryCount(Item item, int newCount) throws VendingMachineException{
         //implement
        auditDao.writeAuditEntry("Item " + item.getName() + "'s inventory count changed to " + newCount + ".");
        return dao.changeInventoryCount(item, newCount);
    }

    // Sell an item
    // Check if the fund is sufficient to make a purchase
    // Check if the item is available in the inventory
    @Override
    public BigDecimal sellItem(BigDecimal totalFunds, Item item) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException{
    //implement
        if (item.getNumInventoryItems() <= 0) {
            throw new VendingMachineItemInventoryException("Error: Item quantity is out of stock. ");
        }

        if (totalFunds.subtract(item.getCost()).compareTo(new BigDecimal(0)) < 0) {
            throw new VendingMachineInsufficientFundsException("Error: Insufficient fund. Please add fund in order to purchase an item.");
        }

        // Buyer can only purchase one item at a time
        // so each time an item is sold, the quantity is subtracted by 1
        // also subtract the item cost from totalFunds
        dao.changeInventoryCount(item, item.getNumInventoryItems() - 1);


        auditDao.writeAuditEntry("Item " + item.getName() + "sold.");

        return totalFunds.subtract(item.getCost());
    }
    
}
