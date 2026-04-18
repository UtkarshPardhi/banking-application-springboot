package com.example.Banking_App.controller;

import com.example.Banking_App.dto.AccountDto;
import com.example.Banking_App.dto.TransactionDto;
import com.example.Banking_App.entity.Account;
import com.example.Banking_App.service.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Add Account REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    // Get Account REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    // Deposit REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request){

        Double  amount = request.get("amount");

        AccountDto accountDto = accountService.deposit(id, amount);
        return  ResponseEntity.ok(accountDto);
    }

    // Withdraw REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                               @RequestBody Map<String, Double> request){
        double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    // Get All Accounts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // Delete Account REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted successfully");
    }

    // Transactions REST API
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>>
    getTransactions(@PathVariable Long id) {

        List<TransactionDto> transactions =
                accountService.getTransactionsByAccountId(id);

        return ResponseEntity.ok(transactions);
    }

    // CSV API
    @GetMapping("/{id}/transactions/csv")
    public void downloadCsv(@PathVariable Long id,
                            @RequestParam(required = false) String filter,
                            HttpServletResponse response) throws IOException {

        List<TransactionDto> transactions = accountService.getTransactionsByAccountId(id);
        Account account = accountService.getAccountEntityById(id);
        List<TransactionDto> filteredTransactions = transactions;

        // Filter Logic
        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime startDateFinal;

        if ("7D".equals(filter)) {
            startDateFinal = now.minusDays(7);
        } else if ("30D".equals(filter)) {
            startDateFinal = now.minusDays(30);
        } else {
            startDateFinal = null;
        }

        if (startDateFinal != null) {
            filteredTransactions = transactions.stream()
                    .filter(tx -> tx.getTimestamp().isAfter(startDateFinal))
                    .toList();
        }

        // Sort DESC
        filteredTransactions.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));

        double runningBalance = account.getBalance();

        // Summary
        double totalCredit = 0;
        double totalDebit = 0;

        for (TransactionDto tx : filteredTransactions) {
            if (tx.getType().equals("DEPOSIT")) {
                totalCredit += tx.getAmount();
            } else {
                totalDebit += tx.getAmount();
            }
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.csv");

        PrintWriter writer = response.getWriter();

        // Summary Header
        writer.println("Account ID," + account.getId());
        writer.println("Account Holder," + account.getAccountHolderName());
        writer.println("Generated On," + formatDate(LocalDateTime.now()));
        writer.println("Total Credit," + totalCredit);
        writer.println("Total Debit," + totalDebit);
        writer.println("");

        // TAble Header
        writer.println("Account ID,Account Holder,Date & Time,Type,Amount,Balance");

        for (TransactionDto tx : filteredTransactions) {

            String type;

            if (tx.getType().equals("DEPOSIT")) {
                type = "CREDIT";
                runningBalance -= tx.getAmount(); // reverse
            } else {
                type = "DEBIT";
                runningBalance += tx.getAmount(); // reverse
            }

            writer.println(
                                    account.getId() + "," +
                                    account.getAccountHolderName() + "," +
                                    "\"" + formatDate(tx.getTimestamp()) + "\"," +
                                    type + "," +
                                    tx.getAmount() + "," +
                                    runningBalance
            );

        }

        writer.flush();
    }

    // PDF API
    @GetMapping("/{id}/transactions/pdf")
    public void downloadPdf(@PathVariable Long id,
                            @RequestParam(required = false) String filter,
                            HttpServletResponse response) throws Exception {

        List<TransactionDto> transactions = accountService.getTransactionsByAccountId(id);
        Account account = accountService.getAccountEntityById(id);
        List<TransactionDto> filteredTransactions = transactions;

        // Filter
        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime startDateFinal;

        if ("7D".equals(filter)) {
            startDateFinal = now.minusDays(7);
        } else if ("30D".equals(filter)) {
            startDateFinal = now.minusDays(30);
        } else {
            startDateFinal = null;
        }

        if (startDateFinal != null) {
            filteredTransactions = transactions.stream()
                    .filter(tx -> tx.getTimestamp().isAfter(startDateFinal))
                    .toList();
        }

        // Sort DESC
        filteredTransactions.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));

        double runningBalance = account.getBalance();

        //Summary
        double totalCredit = 0;
        double totalDebit = 0;

        for (TransactionDto tx : filteredTransactions) {
            if (tx.getType().equals("DEPOSIT")) {
                totalCredit += tx.getAmount();
            } else {
                totalDebit += tx.getAmount();
            }
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=statement.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Title
        document.add(new Paragraph("BANKING APP STATEMENT")
                .setBold()
                .setFontSize(18));

        document.add(new Paragraph(" "));

        // Account Info
        document.add(new Paragraph("Account ID: " + account.getId()));
        document.add(new Paragraph("Account Holder: " + account.getAccountHolderName()));
        document.add(new Paragraph("Generated On: " + formatDate(LocalDateTime.now())));
        document.add(new Paragraph(" "));

        //Summary
        document.add(new Paragraph("Total Credit: ₹" + totalCredit));
        document.add(new Paragraph("Total Debit: ₹" + totalDebit));
        document.add(new Paragraph(" "));

        // Table
        float[] columnWidths = {180, 100, 100, 120};
        Table table = new Table(columnWidths);

        table.addHeaderCell(new Paragraph("Date & Time").setBold());
        table.addHeaderCell(new Paragraph("Type").setBold());
        table.addHeaderCell(new Paragraph("Amount").setBold());
        table.addHeaderCell(new Paragraph("Balance").setBold());


        for (TransactionDto tx : filteredTransactions) {

            String type;

            if (tx.getType().equals("DEPOSIT")) {
                type = "CREDIT";
                runningBalance -= tx.getAmount(); // reverse
            } else {
                type = "DEBIT";
                runningBalance += tx.getAmount(); // reverse
            }

            table.addCell(formatDate(tx.getTimestamp()));
            table.addCell(type);
            table.addCell("₹" + tx.getAmount());
            table.addCell("₹" + runningBalance);
        }

        document.add(table);
        document.close();
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        return dateTime.format(formatter);
    }

    @GetMapping("/{id}/transactions/excel")
    public void downloadExcel(@PathVariable Long id,
                              @RequestParam(required = false) String filter,
                              HttpServletResponse response) throws IOException {

        List<TransactionDto> transactions = accountService.getTransactionsByAccountId(id);
        Account account = accountService.getAccountEntityById(id);

        // Filter
        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime startDateFinal;

        if ("7D".equals(filter)) {
            startDateFinal = now.minusDays(7);
        } else if ("30D".equals(filter)) {
            startDateFinal = now.minusDays(30);
        } else {
            startDateFinal = null;
        }

        List<TransactionDto> filteredTransactions = transactions;

        if (startDateFinal != null) {
            filteredTransactions = transactions.stream()
                    .filter(tx -> tx.getTimestamp().isAfter(startDateFinal))
                    .toList();
        }

        // Sort (latest first)
        filteredTransactions.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));

        double runningBalance = account.getBalance();

        // Summary
        double totalCredit = 0;
        double totalDebit = 0;

        for (TransactionDto tx : filteredTransactions) {
            if (tx.getType().equals("DEPOSIT")) totalCredit += tx.getAmount();
            else totalDebit += tx.getAmount();
        }

        // Excel Creation
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Statement");

        int rowNum = 0;

        // Summary
        sheet.createRow(rowNum++).createCell(0).setCellValue("Account ID: " + account.getId());
        sheet.createRow(rowNum++).createCell(0).setCellValue("Account Holder: " + account.getAccountHolderName());
        sheet.createRow(rowNum++).createCell(0).setCellValue("Generated On: " + formatDate(LocalDateTime.now()));
        sheet.createRow(rowNum++).createCell(0).setCellValue("Total Credit: " + totalCredit);
        sheet.createRow(rowNum++).createCell(0).setCellValue("Total Debit: " + totalDebit);

        rowNum++; // empty row

        // Header
        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Date & Time");
        header.createCell(1).setCellValue("Type");
        header.createCell(2).setCellValue("Amount");
        header.createCell(3).setCellValue("Balance");

        // Data
        for (TransactionDto tx : filteredTransactions) {

            String type;

            if (tx.getType().equals("DEPOSIT")) {
                type = "CREDIT";
                runningBalance -= tx.getAmount();
            } else {
                type = "DEBIT";
                runningBalance += tx.getAmount();
            }

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(formatDate(tx.getTimestamp()));
            row.createCell(1).setCellValue(type);
            row.createCell(2).setCellValue(tx.getAmount());
            row.createCell(3).setCellValue(runningBalance);
        }

        // Auto Size
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        // Response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=statement.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    //  Stats API
    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable Long id) {

        List<TransactionDto> transactions = accountService.getTransactionsByAccountId(id);

        double totalCredit = 0;
        double totalDebit = 0;

        for (TransactionDto tx : transactions) {
            if (tx.getType().equals("DEPOSIT")) {
                totalCredit += tx.getAmount();
            } else {
                totalDebit += tx.getAmount();
            }
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTransactions", transactions.size());
        stats.put("totalCredit", totalCredit);
        stats.put("totalDebit", totalDebit);

        return ResponseEntity.ok(stats);
    }
}
