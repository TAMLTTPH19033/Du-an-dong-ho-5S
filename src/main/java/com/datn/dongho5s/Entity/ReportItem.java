package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportItem {
    private String identifier;
    private Double grossSales;
    private Double netSales;
    private int ordersCount;

    public ReportItem(String identifier, Double grossSales, Double netSales) {
        this.identifier = identifier;
        this.grossSales = grossSales;
        this.netSales = netSales;
    }

    public ReportItem(Double grossSales) {
        this.grossSales = grossSales;
    }

    public ReportItem(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public ReportItem(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Double getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(Double grossSales) {
        this.grossSales = grossSales;
    }

    public void setGrossSales() {
        this.grossSales = grossSales;
    }
    public Double getNetSales() {
        return netSales;
    }

    public void setNetSales(Double netSales) {
        this.netSales = netSales;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportItem)) return false;
        ReportItem that = (ReportItem) o;
        return getIdentifier().equals(that.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier());
    }

    public void addGrossSales(Double amount) {
        if (this.grossSales == null) {
            this.grossSales = 0.0; // Initialize grossSales to 0 if it's null
        }
        this.grossSales += amount;
    }

    public void addNetSales(Double amount) {
        if (this.netSales == null) {
            this.netSales = 0.0; // Initialize netSales to 0 if it's null
        }
        this.netSales += amount;
    }

    public void increaseOrderCount() {
        this.ordersCount++;
    }
}