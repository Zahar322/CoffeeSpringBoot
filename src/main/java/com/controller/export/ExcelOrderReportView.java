package com.controller.export;

import com.controller.entity.OrderItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelOrderReportView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, org.apache.poi.ss.usermodel.Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"order_report.xls\"");
        List<OrderItem> itemList=(List<OrderItem>) map.get("orderItems");
        Sheet sheet=workbook.createSheet("OrderItem list");
        Row header=sheet.createRow(0);
        header.createCell(0).setCellValue("Order id");
        header.createCell(1).setCellValue("Coffee title");
        header.createCell(2).setCellValue("Coffee amount");
        header.createCell(3).setCellValue("Total price");

        int rowNum=1;

        for(OrderItem item:itemList){
            Row row=sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getOrderId());
            row.createCell(1).setCellValue(item.getCoffee().getTitle());
            row.createCell(2).setCellValue(item.getAmount());
            row.createCell(3).setCellValue(item.getCoffee().getPrice()*item.getAmount());
        }



    }
}
