package com.docto.protechdoctolib.logging.token;


import org.springframework.stereotype.Service;

@Service
public class ConnectionTokenService {

    private final ConnectionTokenRepository connectionTokenRepository;

    public ConnectionTokenService(ConnectionTokenRepository connectionTokenRepository) {
        this.connectionTokenRepository = connectionTokenRepository;
    }
}
