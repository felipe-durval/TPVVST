package br.ifsp.demo.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/hello")
@AllArgsConstructor
@Tag(name = "Hello API")
public class TransactionController {


}
