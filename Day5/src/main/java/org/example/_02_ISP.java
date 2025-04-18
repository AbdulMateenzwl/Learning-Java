package org.example;

interface IPrintScanContent {
    boolean printContent(String content);

    boolean scanContent(String content);

    boolean copyContent(String content);
}

interface IFaxContent {
    boolean faxContent(String content);
}

interface IPrintDuplex {
    boolean printDuplexContent(String content);
}

class HPPrinter implements IPrintScanContent, IFaxContent {
    @Override
    public boolean printContent(String content) {
        // Implement print logic here
        return true;
    }

    @Override
    public boolean scanContent(String content) {
        // Implement scan logic here
        return true;
    }

    @Override
    public boolean faxContent(String content) {
        // Implement fax logic here
        return true;
    }

    @Override
    public boolean copyContent(String content) {
        // Implement copy logic here
        return true;
    }
}

class CanonPrinter implements IPrintScanContent, IPrintDuplex {
    @Override
    public boolean printContent(String content) {
        // Implement print logic here
        return true;
    }

    @Override
    public boolean scanContent(String content) {
        // Implement scan logic here
        return true;
    }

    @Override
    public boolean copyContent(String content) {
        // Implement copy logic here
        return true;
    }

    @Override
    public boolean printDuplexContent(String content) {
        // Implement duplex print logic here
        return true;
    }
}

// This is an example of the Interface Segregation Principle (ISP) in Java.
// The ISP states that no client should be forced to depend on methods it does not use.
// In this example, we have separate interfaces for different functionalities of printers.
// The IPrintScanContent interface defines methods for printing, scanning, and copying content.
// The IFaxContent interface defines a method for faxing content.
// The IPrintDuplex interface defines a method for duplex printing.
// This allows for better organization and flexibility in the codebase.
// The main method demonstrates how to use these classes.

public class _02_ISP {
    public static void main(String[] args) {
        IPrintScanContent printer = new HPPrinter();
        printer.printContent("Hello, World!");
        printer.scanContent("Scan this document.");
        printer.copyContent("Copy this document.");

        IFaxContent faxPrinter = new HPPrinter();
        faxPrinter.faxContent("Fax this document.");

        IPrintDuplex duplexPrinter = new CanonPrinter();
        duplexPrinter.printDuplexContent("Print this document in duplex mode.");
    }
}
