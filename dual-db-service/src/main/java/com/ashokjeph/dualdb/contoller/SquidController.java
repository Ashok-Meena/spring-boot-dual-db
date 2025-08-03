package com.ashokjeph.dualdb.contoller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ashokjeph.dualdb.squid_admin.SquidService;
import com.ashokjeph.dualdb.squid_admin.model.RefreshTokenRequestModel;

import java.util.Map;


/**
 * Made for squid specific access of api's
 */

@Slf4j
@RestController
@RequestMapping("")
public class SquidController {

    @Autowired
    SquidService squidService;

    // LOGIN
    @PostMapping("/authorization")
    public ResponseEntity<?> squidLogin(@RequestParam Map<String, Object> params) {

        return squidService.squidLogin(params);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestModel requestModel, HttpServletRequest request) {
        return squidService.refreshToken(requestModel, request);
    }

    @PostMapping("/squid/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return squidService.logout(request);
    }

}
