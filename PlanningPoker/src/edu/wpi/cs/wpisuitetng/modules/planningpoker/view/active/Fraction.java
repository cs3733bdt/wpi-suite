/*******************************************************************************
* Copyright (c) 2012-2014 -- WPI Suite
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

/**
* Represents a fraction with a numerator and denominator.
*
* @author Team BobbyDropTables
* Inspiration from Team Romulus
*/
public class Fraction {
    private final int numerator;
    private final int denominator;
    
    /**
*
* Description
*
* @param numerator
* @param denominator
*/
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    /**
*
* Description goes here.
*
* @return
*/
    public int getNumerator() {
        return numerator;
    }
    
    /**
*
* Description goes here.
*
* @return
*/
    public int getDenominator() {
        return denominator;
    }
    
    /**
*
* Description goes here.
*
* @return
*/
    public double getValue() {
        return (double) numerator / denominator;
    }
}