package de.netprojectev.Tests;

import java.awt.Color;

public class Userdir {
    public static void main(String[] args) {
        System.out.println("Working Directory = " +
           System.getProperty("user.dir"));
        
        
        Color col = Color.BLACK;
        System.out.println(col.getRGB());
    }
}