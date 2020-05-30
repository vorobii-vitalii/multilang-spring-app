package com.multilang.app.Multilangapp;

import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.exceptions.LocateAlreadyExistsException;
import com.multilang.app.Multilangapp.exceptions.LocateNotFoundException;
import com.multilang.app.Multilangapp.mapper.LocateMapper;
import com.multilang.app.Multilangapp.mapper.Mapper;
import com.multilang.app.Multilangapp.repository.LocateRepository;
import com.multilang.app.Multilangapp.service.LocateService;
import com.multilang.app.Multilangapp.service.impl.LocateServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class LocateServiceTest {

    @Mock
    private LocateRepository locateRepository;

    private LocateService locateService;

    private Mapper<Locate, LocateDTO> locateDTOMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.locateDTOMapper = new LocateMapper();
        this.locateService = new LocateServiceImpl(locateRepository, locateDTOMapper);
    }

    @Test
    public void whenMapLocateToItsDTO_thenReturnsRightOne() {
        Long expectedId = 1L;
        String expectedLanguageCode = "ua";
        Locate locate = new Locate(expectedId, expectedLanguageCode);
        LocateDTO locateDTO = locateDTOMapper.to(locate);
        assertEquals(locate.getId(), expectedId);
        assertEquals(locate.getLanguageCode(), expectedLanguageCode);
        assertEquals(locate.getId(), locateDTO.getId());
        assertEquals(locate.getLanguageCode(), locateDTO.getLanguageCode());
    }

    @Test
    public void whenMapLocateDTOtoItsEntity_thenReturnsRightOne() {
        Long expectedId = 1L;
        String expectedLanguageCode = "ua";
        LocateDTO locateDTO = new LocateDTO(expectedId, expectedLanguageCode);
        Locate locate = locateDTOMapper.from(locateDTO);
        assertEquals(locateDTO.getId(), expectedId);
        assertEquals(locateDTO.getLanguageCode(), expectedLanguageCode);
        assertEquals(locateDTO.getId(), locate.getId());
        assertEquals(locateDTO.getLanguageCode(), locate.getLanguageCode());
    }

    @Test
    public void whenFindAll_thenReturnLocateList() {
        Locate locate = new Locate(1L, "ua");
        List<Locate> expectedLocates = Collections.singletonList(locate);
        doReturn(expectedLocates).when(locateRepository).findAll();

        // When
        List<LocateDTO> actualLocates = locateService.getAll();

        // Then
        assertThat(actualLocates)
                .isEqualTo(
                        expectedLocates
                            .stream()
                            .map(locateDTOMapper::to)
                            .collect(Collectors.toList())
                );
    }

    @Test
    public void whenGetById_thenReturnsAppropriateLocate() {
        final Long targetId = 1L;
        Optional<Locate> locate = Optional.of( new Locate(targetId, "ua"));
        doReturn(locate).when(locateRepository).findById(targetId);

        // When
        LocateDTO actualLocate = locateService.getById(targetId);

        // Then
        assertThat(actualLocate)
                .isEqualTo(locateDTOMapper.to(locate.get()));
    }

    @Test
    public void whenGetByLanguageCode_thenReturnsAppropriateOne() {
        final String targetLangCode = "ua";
        Optional<Locate> locate = Optional.of ( new Locate(1L, targetLangCode) );
        doReturn(locate).when(locateRepository).findByLanguageCode(targetLangCode);

        // When
        LocateDTO actualLocate = locateService.getByLanguageCode(targetLangCode);

        // Then
        assertThat(actualLocate)
                .isEqualTo(locateDTOMapper.to(locate.get()));
    }

    @Test(expected = LocateNotFoundException.class)
    public void whenDeleteNotFound_thenThrowsException() {
        final Long targetId = 4L;
        doReturn(false).when(locateRepository).existsById(targetId);
        locateService.deleteById(targetId);
    }

    @Test
    public void whenDeleteExists_thenPerformDelete() {
        final Long targetId = 4L;
        doReturn(true).when(locateRepository).existsById(targetId);
        locateService.deleteById(targetId);
    }

    @Test(expected = LocateAlreadyExistsException.class)
    public void whenTryToAddAlreadyCreatedLocateById_thenThrowException() {
        final Long testId = 4L;
        final LocateDTO locateDTO = new LocateDTO(testId, "ua");
        doReturn(true).when(locateRepository).existsById(testId);
        locateService.add(locateDTO);
    }

    @Test(expected = LocateAlreadyExistsException.class)
    public void whenTryToAddAlreadyCreatedLocateByLangCode_thenThrowException() {
        final String testLanguageCode = "ua";
        final LocateDTO locateDTO = new LocateDTO(1L, testLanguageCode);
        doReturn(true).when(locateRepository).existsByLanguageCode(testLanguageCode);
        locateService.add(locateDTO);
    }

    @Test
    public void whenTryToAddLocateWhichWasntAddedById_thenPerformCreation() {
        final Long definedId = 4L;
        final Long testId = 2L;
        assertNotEquals(definedId, testId);
        LocateDTO locateDTOTest = new LocateDTO(testId, "ua");
        Locate locate = locateDTOMapper.from(locateDTOTest);
        doReturn(true).when(locateRepository).existsById(definedId);
        doReturn(false).when(locateRepository).existsById(testId);
        doReturn(locate).when(locateRepository).save(locate);
        locateService.add(locateDTOTest);
    }

    @Test
    public void whenTryToAddLocateWhichWasntAddedByLangCode_thenPerformCreation() {
        final String definedLangCode = "ua";
        final String testLangCode = "ru";
        assertNotEquals(definedLangCode, testLangCode);
        LocateDTO locateDTOTest = new LocateDTO(1L, testLangCode);
        Locate locate = locateDTOMapper.from(locateDTOTest);
        doReturn(true).when(locateRepository).existsByLanguageCode(definedLangCode);
        doReturn(false).when(locateRepository).existsByLanguageCode(testLangCode);
        doReturn(locate).when(locateRepository).save(locate);
        locateService.add(locateDTOTest);
    }

}
