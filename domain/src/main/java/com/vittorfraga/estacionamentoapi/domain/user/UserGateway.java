package com.vittorfraga.estacionamentoapi.domain.user;

public interface UserGateway {
    User save(User user);

    boolean findByUsername(String username);

    User findById(String id);

    void delete(String id);
}
