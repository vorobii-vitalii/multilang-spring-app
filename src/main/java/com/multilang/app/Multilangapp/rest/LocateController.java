package com.multilang.app.Multilangapp.rest;

import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.service.LocateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locates")
public class LocateController {

    private final LocateService locateService;

    @Autowired
    public LocateController(LocateService locateService) {
        this.locateService = locateService;
    }

    @GetMapping
    public List<LocateDTO> getLocates() {
        return locateService.getAll();
    }

    @GetMapping("/{id:[0-9]+}}")
    public LocateDTO getLocateById(@PathVariable("id") Long id) {
        return locateService.getById(id);
    }

    @GetMapping("/{languageCode:[a-z]+}")
    public LocateDTO getLocateByLanguageCode(@PathVariable("languageCode") String languageCode) {
        return locateService.getByLanguageCode(languageCode);
    }

    @PostMapping
    public LocateDTO addLocate(@RequestBody LocateDTO locateDTO) {
        return locateService.add(locateDTO);
    }

}
