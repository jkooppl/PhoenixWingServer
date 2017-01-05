package com.pizza73.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * ExcelFileConversion.java TODO comment me
 * 
 * @author chris 7-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class ExcelFileConversion
{

    @SuppressWarnings("unchecked")
   public static void main(String[] args) throws Exception
    {
        ExcelOnlineCustomerConversion eocc = new ExcelOnlineCustomerConversion();
        eocc.setUp();
        File file = new File("C:\\Documents and Settings\\chris\\My Documents\\dev\\eddOnline\\metadata\\excel\\oc6-excel.xls");
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        List reports = eocc.importData(wb);
        if (reports != null && !reports.isEmpty())
        {
            Map<Integer, String> notMapped = 
                (Map<Integer, String>) reports.get(0);
            List<Integer> notSaved = (List<Integer>) reports.get(1);

            File f = new File("importReports");
            if (!f.exists())
            {
                f.mkdirs();
            }
            File reportFile = new File(f, "notMapped-"
                    + System.currentTimeMillis());
            PrintWriter writer = null;
            try
            {
                writer = new PrintWriter(reportFile);
                Iterator<Integer> iter = notMapped.keySet().iterator();
                while (iter.hasNext())
                {
                    Integer id = iter.next();
                    writer.println(
                        "online_id: " + id + "\tADRESS: " + notMapped.get(id));
                }
            }
            finally
            {
                writer.close();
            }
            
            reportFile = new File(f, "notSaved-"+ System.currentTimeMillis());
            writer = null;
            try
            {
                writer = new PrintWriter(reportFile);
                Iterator<Integer> iter = notSaved.iterator();
                while (iter.hasNext())
                {
                    Integer id = iter.next();
                    writer.println("online_id: " + id);
                }
            }
            finally
            {
                writer.close();
            }            

        }
    }
}
