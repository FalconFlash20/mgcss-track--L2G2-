package com.mgcss.domain;

import java.util.*;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
}
