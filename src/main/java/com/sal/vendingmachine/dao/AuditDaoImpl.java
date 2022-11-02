
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.VendingMachineException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author salajrawi
 */
public class AuditDaoImpl implements AuditDao{

    public static final String AUDIT_FILE = "audit.txt";
    
    @Override
    public void writeAuditEntry(String entry) throws VendingMachineException{
       // Declare and initialize PrintWriter object with AUDIT_FILE, write an entry to the that file
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingMachineException("Could not persist audit information.", e);
        }

        // Declare and initialize the timestamp of the current date and time
        LocalDateTime timestamp = LocalDateTime.now();

        // Write the audit log entry to the file
        out.println(timestamp + " : " + entry);
        // Forces output to be written out
        out.flush();

    }
    
}
