package tests;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by brice on 24/01/17.
 * test for writing in Excel files
 */
public class Xls_test
{
    public static void main(String[] args)
    {
        String filename = "/home/" + System.getProperty("user.name") + "/test.xlsx";
        File myFile = new File(filename);
        try
        {
            Random rand = new Random();

            myFile.createNewFile();
            FileOutputStream os = new FileOutputStream(myFile);
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("coucou");
            ArrayList<String> list = new ArrayList<>();
            list.add("Toto");
            list.add("Titi");
            list.add("Tata");
            list.add("Tutu");
            list.add("Tete");
            Row first_row = sheet.createRow(0);
            Cell name = first_row.createCell(0, CellType.STRING);
            name.setCellValue("Nom");
            Cell age = first_row.createCell(1, CellType.NUMERIC);
            age.setCellValue("Age");

            int row = 1;
            for (String s : list)
            {
                Row r = sheet.createRow(row);
                Cell c1 = r.createCell(0, CellType.STRING);
                c1.setCellValue(s);
                int age_num = rand.nextInt(100);
                Cell c2 = r.createCell(1, CellType.NUMERIC);
                c2.setCellValue(age_num);
                row++;
            }

            sheet = workbook.createSheet("sheet 2");
            first_row = sheet.createRow(0);
            name = first_row.createCell(0, CellType.STRING);
            name.setCellValue("test");

            Sheet s = workbook.getSheetAt(0);
            Row last_row = s.createRow(row);
            Cell moyenne = last_row.createCell(1, CellType.FORMULA);
            moyenne.setCellFormula("AVERAGE(B2:B" + row + ")");

            workbook.write(os);
            System.out.println("XLSX successfully created : " + filename);

            os.close();
            workbook.close();

            FileInputStream fis = new FileInputStream(filename);
            XSSFWorkbook book = new XSSFWorkbook(fis);

            XSSFCell moy = book.getSheetAt(0).getRow(row).getCell(1);
            System.out.println("Average age : " + new XSSFFormulaEvaluator(book).evaluateInCell(moy));

            fis.close();
            book.close();
        }
        catch (FileNotFoundException e)
        {
            e.getMessage();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

