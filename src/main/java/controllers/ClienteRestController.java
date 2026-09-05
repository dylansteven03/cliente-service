package com.denkitronik.clienteservice.controllers;

import com.denkitronik.clienteservice.entities.Cliente;
import com.denkitronik.clienteservice.entities.Region;
import com.denkitronik.clienteservice.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/${api.version}/cliente-service")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> buscarCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable Long id, @RequestBody Cliente cliente) {
        if (clienteService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente actualizado = clienteService.update(id, cliente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/clientes")
    public ResponseEntity<Void> eliminarCliente(@RequestBody Cliente cliente) {
        clienteService.delete(cliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes/regiones")
    public ResponseEntity<List<Region>> listarRegiones() {
        return ResponseEntity.ok(clienteService.findAllRegiones());
    }
}