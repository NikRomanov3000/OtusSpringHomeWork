package ru.otus.hw05jdbc.util.impl;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.otus.hw05jdbc.util.IOService;

@Component
public class ConsoleIOServiceImpl implements IOService {
    private final PrintStream out;
    private final Scanner sc;


    public ConsoleIOServiceImpl(@Value("#{T(java.lang.System).out}") PrintStream out,
                            @Value("#{T(java.lang.System).in}") InputStream in) {
        this.out = out;
        this.sc = new Scanner(in);
    }

    @Override
    public void out(String message) {
        out.println(message);
    }

    @Override
    public String readString() {
        return sc.nextLine();
    }
}
