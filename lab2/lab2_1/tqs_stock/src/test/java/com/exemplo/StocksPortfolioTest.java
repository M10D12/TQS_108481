package com.exemplo;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StocksPortfolioTest {
    @Mock
    private IStockmarketService stockMarketService;
    
    private StocksPortfolio stocksPortfolio;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stocksPortfolio = new StocksPortfolio(stockMarketService);
    }
    
    @Test
    public void testTotalValue(){
        when(stockMarketService.lookUpPrice("FCPorto")).thenReturn(400.0);
        when(stockMarketService.lookUpPrice("Deti")).thenReturn(200.0);

        when(stockMarketService.lookUpPrice("zboritng")).thenReturn(400.0);
        when(stockMarketService.lookUpPrice("ua")).thenReturn(200.0);

        
        stocksPortfolio.addStock(new Stock("FCPorto", 2));
        stocksPortfolio.addStock(new Stock("Deti", 1));
        
        double totalValue = stocksPortfolio.totalValue();
        assertThat(totalValue).isEqualTo(1000.0);

        verify(stockMarketService).lookUpPrice("FCPorto");
        verify(stockMarketService).lookUpPrice("Deti");
    }
    @Test
    public void testMostValuableStocks_TypicalCase() {
        when(stockMarketService.lookUpPrice("StockA")).thenReturn(50.0);  
        when(stockMarketService.lookUpPrice("StockB")).thenReturn(200.0);  
        when(stockMarketService.lookUpPrice("StockC")).thenReturn(300.0); 
        when(stockMarketService.lookUpPrice("StockD")).thenReturn(1000.0);
        when(stockMarketService.lookUpPrice("StockE")).thenReturn(1.0);    

        stocksPortfolio.addStock(new Stock("StockA", 10));
        stocksPortfolio.addStock(new Stock("StockB", 5));
        stocksPortfolio.addStock(new Stock("StockC", 2));
        stocksPortfolio.addStock(new Stock("StockD", 1));
        stocksPortfolio.addStock(new Stock("StockE", 20));

        List<Stock> topStocks = stocksPortfolio.mostValuabStocks(3);
        
        assertThat(topStocks).hasSize(3);
        
        double previousValue = Double.MAX_VALUE;
        for (Stock s : topStocks) {
            double currentValue = stockMarketService.lookUpPrice(s.getLabel()) * s.getQuantity();
            assertThat(currentValue).isLessThanOrEqualTo(previousValue);
            previousValue = currentValue;
        }
        
    
        List<String> labels = topStocks.stream()
                                       .map(Stock::getLabel)
                                       .collect(Collectors.toList());
        assertThat(labels).containsExactlyInAnyOrder("StockB", "StockD", "StockC");
    }
    
}
