package semtd_intranet.semtd_net.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import semtd_intranet.semtd_net.DTO.ArquivosDTO;
import semtd_intranet.semtd_net.model.Arquivos;
import semtd_intranet.semtd_net.service.ArquivosService;

@RestController
@RequestMapping("/arquivos")
public class ArquivosController {

    @Autowired
    private ArquivosService arquivosService;

    @GetMapping("/minha-foto")
    public ResponseEntity<?> obterMinhaFoto() {
        return arquivosService.obterFotoDoUsuarioLogado();
    }

    @PostMapping("/minha-foto")
    public ResponseEntity<?> salvarOuAtualizarFoto(@RequestParam("foto") MultipartFile foto) {
        return arquivosService.salvarOuAtualizarFotoDoUsuarioLogado(foto);
    }

    @DeleteMapping("/minha-foto")
    public ResponseEntity<?> deletarMinhaFoto() {
        return arquivosService.deletarFotoDoUsuarioLogado();
    }
}
