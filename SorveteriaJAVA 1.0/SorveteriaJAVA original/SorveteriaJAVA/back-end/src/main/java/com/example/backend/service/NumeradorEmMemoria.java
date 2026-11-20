package com.example.backend.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/** Numeração que recomeça em 1 toda vez que a aplicação liga. */
@Component
public class NumeradorEmMemoria  implements  NumeradorPedido{
    private  final  AtomicInteger sequencia = new AtomicInteger();

    @Override
    public int proximo(){
        return sequencia.incrementAndGet();
    }
}
