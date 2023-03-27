package com.memoire.kital.raph.openFeign;

import com.memoire.kital.raph.service.restClient.SalleClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${jhipster.clientApp.name}" , url = "http://localhost:8098")
public interface ISalle {
        @GetMapping("/api/salles/{id}")
        ResponseEntity<SalleClient> getSalle(@PathVariable("id") String salle);
    }
