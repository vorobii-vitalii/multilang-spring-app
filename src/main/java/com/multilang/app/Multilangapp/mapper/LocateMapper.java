package com.multilang.app.Multilangapp.mapper;

import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.entity.Locate;
import org.springframework.stereotype.Component;

@Component
public class LocateMapper implements Mapper<Locate, LocateDTO> {

    @Override
    public Locate from(LocateDTO locateDTO) {
        Locate locate = new Locate();
        locate.setId(locateDTO.getId());
        locate.setLanguageCode(locateDTO.getLanguageCode());
        return locate;
    }

    @Override
    public LocateDTO to(Locate locate) {
        LocateDTO locateDTO = new LocateDTO();
        locateDTO.setId(locate.getId());
        locateDTO.setLanguageCode(locate.getLanguageCode());
        return locateDTO;
    }

}
