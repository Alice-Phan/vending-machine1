
package com.sal.vendingmachine;

import com.sal.vendingmachine.controller.VendingMachineController;
import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author salajrawi
 */
public class App {
    public static void main(String[] args) throws VendingMachineException{
       //implement
       // Instantiate the UserIO, VendingMachineDao, VendingMachineServiceLayer, VendingMachineView, VendingMachineController, and VendingMachineAuditDao
        UserIO myIo = new UserIOImpl();
        VendingMachineDao myDao = new VendingMachineDaoImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        AuditDao myAuditDao = new AuditDaoImpl();
        VendingMachineService myServiceLayer = new VendingMachineServiceImpl(myDao, myAuditDao);
        VendingMachineController controller = new VendingMachineController(myView, myServiceLayer);
        controller.run();
    }
}
