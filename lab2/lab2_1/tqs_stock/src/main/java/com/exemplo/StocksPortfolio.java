package com.exemplo;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private List<Stock> stocks;
    private IStockmarketService stockMarketService;

    public StocksPortfolio(IStockmarketService stockMarketService){
        this.stockMarketService = stockMarketService;
        this.stocks = new ArrayList<>();
    }

    public double totalValue(){
        double total = 0.0;
        for(Stock s : stocks){
            total += stockMarketService.lookUpPrice(s.getLabel()) * s.getQuantity();
        }
        return total;
    }

    public void addStock(Stock stock){
        stocks.add(stock);
    }

    public List<Stock> mostValuabStocks(int topN){
        List<Stock> mostValuable = new ArrayList<>();
        stocks.sort((s1, s2) -> {
            double s1Value = stockMarketService.lookUpPrice(s1.getLabel()) * s1.getQuantity();
            double s2Value = stockMarketService.lookUpPrice(s2.getLabel()) * s2.getQuantity();
            return Double.compare(s2Value, s1Value);
        });
        for(int i = 0; i < topN; i++){
            mostValuable.add(stocks.get(i));
        }
        return mostValuable;
    }
}
