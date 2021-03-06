package com.multilang.app.Multilangapp.service.impl;

import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.exceptions.LocateAlreadyExistsException;
import com.multilang.app.Multilangapp.exceptions.LocateNotFoundException;
import com.multilang.app.Multilangapp.mapper.Mapper;
import com.multilang.app.Multilangapp.repository.LocateRepository;
import com.multilang.app.Multilangapp.service.LocateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocateServiceImpl implements LocateService {

    private final LocateRepository locateRepository;
    private final Mapper<Locate, LocateDTO> locateDTOMapper;

    @Autowired
    public LocateServiceImpl(LocateRepository locateRepository, Mapper<Locate, LocateDTO> locateDTOMapper) {
        this.locateRepository = locateRepository;
        this.locateDTOMapper = locateDTOMapper;
    }

    @Override
    public LocateDTO add(LocateDTO locateDTO) {

        if ( locateRepository.existsById(locateDTO.getId()) || locateRepository.existsByLanguageCode(locateDTO.getLanguageCode()) )
                throw new LocateAlreadyExistsException();

        Locate addedLocate = locateRepository.save(locateDTOMapper.from(locateDTO));
        return locateDTOMapper.to(addedLocate);
    }

    @Override
    public LocateDTO getById(Long id) {
        Locate locate = locateRepository.findById(id)
                .orElseThrow(LocateNotFoundException::new);
        return locateDTOMapper.to(locate);
    }

    @Override
    public List<LocateDTO> getAll() {
        return locateRepository.findAll().stream()
                .map(locateDTOMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByLanguageCode(String languageCode) {
        return locateRepository.existsByLanguageCode(languageCode);
    }

    @Override
    public LocateDTO getByLanguageCode(String languageCode) {
        Locate locate = locateRepository
                .findByLanguageCode(languageCode)
                .orElseThrow(LocateNotFoundException::new);
        return locateDTOMapper.to(locate);
    }

    @Override
    public void deleteById(Long id) {
        if (!locateRepository.existsById(id))
            throw new LocateNotFoundException();
        locateRepository.deleteById(id);
    }

}
