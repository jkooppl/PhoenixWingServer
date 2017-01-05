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
public class SQLLoader
{

    @SuppressWarnings("unchecked")
    public void setUp()
    {

    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {
        SQLLoader loader = new SQLLoader();
        loader.setUp();
        File file = new File("~/chris/Pizza/pizza73-eddOnline/metadata/scenePins-2011.TXT");
        
    }
}
