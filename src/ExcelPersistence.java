import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

public class ExcelPersistence implements DataPersistence {
    private static final String FILE = "data.xlsx";


    @Override
    public void save(ArrayList<Customer> customers, ArrayList<Product> products,Administrator admin) {
        try {

                System.out.println(">>> compress实际加载自: "
                        + org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream.class
                        .getProtectionDomain().getCodeSource().getLocation());



            Workbook wb = new XSSFWorkbook();


            Sheet pSheet = wb.createSheet("商品");
            Row pHeader = pSheet.createRow(0);
            String[] pCols = {"商品ID", "名称", "厂家", "生产日期", "型号", "进货价", "零售价", "库存"};
            for (int i = 0; i < pCols.length; i++) {
                pHeader.createCell(i).setCellValue(pCols[i]);
            }
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                Row row = pSheet.createRow(i + 1);
                row.createCell(0).setCellValue(p.getProductID());
                row.createCell(1).setCellValue(p.getName());
                row.createCell(2).setCellValue(p.getManufacture());
                row.createCell(3).setCellValue(p.getProductionDate());
                row.createCell(4).setCellValue(p.getModel());
                row.createCell(5).setCellValue(p.getPrimeCost());
                row.createCell(6).setCellValue(p.getRetailPrice());
                row.createCell(7).setCellValue(p.getStock());
            }


            Sheet cSheet = wb.createSheet("顾客");
            Row cHeader = cSheet.createRow(0);
            String[] cCols = {"顾客ID", "用户名", "密码(加密)", "等级", "注册时间", "消费次数", "手机号", "邮箱"};
            for (int i = 0; i < cCols.length; i++) {
                cHeader.createCell(i).setCellValue(cCols[i]);
            }
            for (int i = 0; i < customers.size(); i++) {
                Customer c = customers.get(i);
                Row row = cSheet.createRow(i + 1);
                row.createCell(0).setCellValue(c.getCustomerID());
                row.createCell(1).setCellValue(c.getUsername());
                row.createCell(2).setCellValue(PasswordUtil.encrypt(c.getPassword()));
                row.createCell(3).setCellValue(c.getLeve());
                row.createCell(4).setCellValue(c.getRegisterTime());
                row.createCell(5).setCellValue(c.getCostCount());
                row.createCell(6).setCellValue(c.getPhonenumber());
                row.createCell(7).setCellValue(c.getMailBox());
            }


            Sheet aSheet = wb.createSheet("管理员");
            Row aHeader = aSheet.createRow(0);
            aHeader.createCell(0).setCellValue("用户名");
            aHeader.createCell(1).setCellValue("密码(加密)");
            aHeader.createCell(2).setCellValue("是否首次登录");
            Row aRow = aSheet.createRow(1);
            aRow.createCell(0).setCellValue(admin.getUsername());
            aRow.createCell(1).setCellValue(PasswordUtil.encrypt(admin.getPassword()));
            aRow.createCell(2).setCellValue(admin.isFirstLogin() ? "true" : "false");


            FileOutputStream out = new FileOutputStream(FILE);
            wb.write(out);
            out.close();
            wb.close();
            System.out.println("数据已保存到 Excel 文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public LoadResult load() {
        LoadResult result = new LoadResult();
        File f = new File(FILE);
        if (!f.exists() || f.length() == 0) return result;


        try {
            Workbook wb = new XSSFWorkbook(new FileInputStream(FILE));


            Sheet pSheet = wb.getSheet("商品");
            if (pSheet != null) {
                for (int i = 1; i <= pSheet.getLastRowNum(); i++) {
                    Row row = pSheet.getRow(i);
                    if (row == null) continue;
                    String id = row.getCell(0).getStringCellValue();
                    String name = row.getCell(1).getStringCellValue();
                    String mf = row.getCell(2).getStringCellValue();
                    String date = row.getCell(3).getStringCellValue();
                    String model = row.getCell(4).getStringCellValue();
                    double pc = row.getCell(5).getNumericCellValue();
                    double rp = row.getCell(6).getNumericCellValue();
                    int stock = (int) row.getCell(7).getNumericCellValue();
                    result.addProduct(new Product(id, name, mf, date, model, pc, rp, stock));
                }
            }


            Sheet cSheet = wb.getSheet("顾客");
            if (cSheet != null) {
                for (int i = 1; i <= cSheet.getLastRowNum(); i++) {
                    Row row = cSheet.getRow(i);
                    if (row == null) continue;
                    String id = row.getCell(0).getStringCellValue();
                    String name = row.getCell(1).getStringCellValue();
                    String pwd = PasswordUtil.decrypt(row.getCell(2).getStringCellValue());
                    String leve = row.getCell(3).getStringCellValue();
                    String time = row.getCell(4).getStringCellValue();
                    int cost = (int) row.getCell(5).getNumericCellValue();
                    String phone = row.getCell(6).getStringCellValue();
                    String mail = row.getCell(7).getStringCellValue();

                    Customer c = new Customer(id, name, pwd,time,phone,mail);
                    c.setLeve(leve);
                    c.setCostCount(cost);
                    result.addCustomer(c);
                }
            }


            Sheet aSheet = wb.getSheet("管理员");
            if (aSheet != null && aSheet.getLastRowNum() >= 1) {
                Row row = aSheet.getRow(1);
                if (row != null) {
                    Administrator a = new Administrator();
                    a.setUsername(row.getCell(0).getStringCellValue());
                    a.setPassword(PasswordUtil.decrypt(row.getCell(1).getStringCellValue()));
                    a.setFirstLogin(row.getCell(2).getStringCellValue().equals("true"));
                    result.setAdmin(a);
                }
            }

            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
