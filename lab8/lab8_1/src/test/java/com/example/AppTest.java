package com.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    App app = new App();

    @Test
    void testSoma() {
        assertEquals(7, app.soma(3, 4));
    }

    @Test
    void testSubtrai() {
        assertEquals(2, app.subtrai(5, 3));
    }

    @Test
    void testMultiplica() {
        assertEquals(12, app.multiplica(3, 4));
    }

    @Test
    void testDivide() {
        assertEquals(2, app.divide(10, 5));
    }

    @Test
    void testDividePorZero() {
        assertThrows(IllegalArgumentException.class, () -> app.divide(5, 0));
    }

    @Test
    void testIsParTrue() {
        assertTrue(app.isPar(6));
    }

    @Test
    void testIsParFalse() {
        assertFalse(app.isPar(7));
    }

    @Test
    void testClassificarNotaInvalida() {
        assertEquals("Inválido", app.classificarNota(25));
    }

    @Test
    void testClassificarNotaReprovado() {
        assertEquals("Reprovado", app.classificarNota(5.5));
    }

    @Test
    void testClassificarNotaAprovado() {
        assertEquals("Aprovado", app.classificarNota(14));
    }

    @Test
    void testClassificarNotaExcelente() {
        assertEquals("Excelente", app.classificarNota(19.5));
    }

    @Test
    void testMaiorValor() {
        List<Integer> numeros = Arrays.asList(10, 25, 3, 99, 42);
        assertEquals(99, app.maiorValor(numeros));
    }

    @Test
    void testMaiorValorListaVazia() {
        assertThrows(Exception.class, () -> app.maiorValor(Arrays.asList()));
    }
}
