package com.globant.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping({"", "/"})
@Slf4j
public class Index {

    @GetMapping
    public ResponseEntity<Map<String, String>> index() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Header01", "Valor01");
        httpHeaders.add("Header02", "Valor02");
        httpHeaders.add("Header03", "Valor03");

        int duracion = (int) (Math.random() * 10000 + 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss.SSS");
        long horaInicio, horaFinal;
        horaInicio = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - horaInicio < duracion) {
                if (System.currentTimeMillis() % 100 == 0) {
                    Thread.sleep((long) Math.floor((1 - 0.90) * 100));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        horaFinal = System.currentTimeMillis();

        log.info("inicio: {} | final: {} | duracion {} segundos", simpleDateFormat.format(horaInicio), simpleDateFormat.format(horaFinal), duracion / 1000);
        String mensaje = "CPU stress 90% for " + duracion / 1000 + " secs "
                + "( from " + simpleDateFormat.format(horaInicio)
                + " to " + simpleDateFormat.format(horaFinal)
                + " )";

        String host = null;
        String ip = null;
        try {
            host = InetAddress.getLocalHost().getHostName();
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new LinkedHashMap<>();
        map.put("Título", "CI/CD Concepts");
        map.put("version", "1.0.0");
        map.put("mensaje", mensaje);
        map.put("hostname pod", host);
        map.put("ip pod", ip);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(map);
    }
}