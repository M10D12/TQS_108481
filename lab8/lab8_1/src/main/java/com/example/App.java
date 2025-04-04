package com.example;

import java.util.Arrays;
import java.util.List;

public class App {

    public int soma(int a, int b) {
        return a + b;
    }

    public int subtrai(int a, int b) {
        return a - b;
    }

    public int multiplica(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisão por zero não permitida.");
        }
        return a / b;
    }

    public boolean isPar(int numero) {
        return numero % 2 == 0;
    }

    public String classificarNota(double nota) {
        if (nota < 0 || nota > 20) {
            return "Inválido";
        } else if (nota >= 0 && nota < 10) {
            return "Reprovado";
        } else if (nota >= 10 && nota < 18) {
            return "Aprovado";
        } else {
            return "Excelente";
        }
    }

    public int maiorValor(List<Integer> numeros) {
        return numeros.stream().max(Integer::compare).orElseThrow();
    }

    public static void main(String[] args) {
        System.out.println("App pronta para análise com SonarQube!");
    }
}
