package com.controller.export;

import com.controller.entity.OrderItem;
import com.lowagie.text.Table;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class PdfOrderReportView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> map, com.lowagie.text.Document document, com.lowagie.text.pdf.PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"order_report.pdf\"");

        List<OrderItem> itemList=(List<OrderItem>) map.get("orderItems");

        Table table=new Table(4);
        table.addCell("Order id");
        table.addCell("Coffee title");
        table.addCell("Coffee amount");
        table.addCell("Total price");

        for(OrderItem item:itemList){
            table.addCell(String.valueOf(item.getId()));
            table.addCell(item.getCoffee().getTitle()+httpServletRequest.getHeader("user-agent"));
            table.addCell(String.valueOf(item.getAmount()));
            table.addCell(String.valueOf(item.getCoffee().getPrice()*item.getAmount()));
        }
        document.add(table);

    }
}
