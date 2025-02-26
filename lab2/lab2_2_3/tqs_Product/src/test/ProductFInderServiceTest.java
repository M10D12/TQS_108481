package test;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class ProductFInderServiceTest {
    private ISImpleHttpClient httpClient;
    private ProductFInderService productFInderService;

    @BeforeEach
    void setup(){
        mockHttpClient = mock(ISImpleHttpClient.class);
        productFInderService = new ProductFInderService(mockHttpClient);
    }

    @Test
    void testFindProductDetails(){
        String fakeResponse = """
            {
                "id": 3,
                "title": "Mens Cotton Jacker",
                "price": 55.99,
                "description": "Great Jacket for winter.",
                "category": "men's clothing",
                "image": "https://example.com/image.jpg"
            }
            """;
        when(mockHttpClient.dotHttpGet(anyString())).thenReturn(fakeResponse);

        Optional  <Product> result = productFInderService.findProductDetails(1);

        assertTrue(result.isPresent(),"Expected a result"); 
        Product product = product.get();
        assertEquals(1, product.getId());
        assertEquals("Test Product", product.getTitle());
        assertEquals(29.99, product.getPrice());
        assertEquals("A sample product for testing.", product.getDescription());
        assertEquals("electronics", product.getCategory());
        assertEquals("https://example.com/image.jpg", product.getIMAGE());

        verify(mockHttpClient).doHttpGet("https://api.com/products/1");

}
@Test
    void testFindProductDetails_ProductNotFound() {
        when(mockHttpClient.doHttpGet(anyString())).thenReturn("");

        Optional<Product> result = productFinderService.findProductDetails(300);

        assertFalse(result.isPresent(), "Expected no product to be returned");

        verify(mockHttpClient).doHttpGet("https://fakestoreapi.com/products/300");
    }
}
