package com.bayzdelivery.utilites;

public class Formulae {
    public static Long deliverCommissionFormula(Long orderPrice, Long distance){
        return (long) (orderPrice *0.05 + distance * 0.5);
    }
}
