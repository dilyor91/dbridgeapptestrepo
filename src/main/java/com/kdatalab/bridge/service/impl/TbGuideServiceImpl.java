package com.kdatalab.bridge.service.impl;

import com.kdatalab.bridge.domain.TbGuide;
import com.kdatalab.bridge.repository.TbGuideRepository;
import com.kdatalab.bridge.service.TbGuideService;
import com.kdatalab.bridge.service.dto.TbGuideDTO;
import com.kdatalab.bridge.service.mapper.TbGuideMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TbGuide}.
 */
@Service
@Transactional
public class TbGuideServiceImpl implements TbGuideService {

    private final Logger log = LoggerFactory.getLogger(TbGuideServiceImpl.class);

    private final TbGuideRepository tbGuideRepository;

    private final TbGuideMapper tbGuideMapper;

    public TbGuideServiceImpl(TbGuideRepository tbGuideRepository, TbGuideMapper tbGuideMapper) {
        this.tbGuideRepository = tbGuideRepository;
        this.tbGuideMapper = tbGuideMapper;
    }

    @Override
    public TbGuideDTO save(TbGuideDTO tbGuideDTO) {
        log.debug("Request to save TbGuide : {}", tbGuideDTO);
        TbGuide tbGuide = tbGuideMapper.toEntity(tbGuideDTO);
        tbGuide = tbGuideRepository.save(tbGuide);
        return tbGuideMapper.toDto(tbGuide);
    }

    @Override
    public TbGuideDTO update(TbGuideDTO tbGuideDTO) {
        log.debug("Request to update TbGuide : {}", tbGuideDTO);
        TbGuide tbGuide = tbGuideMapper.toEntity(tbGuideDTO);
        tbGuide = tbGuideRepository.save(tbGuide);
        return tbGuideMapper.toDto(tbGuide);
    }

    @Override
    public Optional<TbGuideDTO> partialUpdate(TbGuideDTO tbGuideDTO) {
        log.debug("Request to partially update TbGuide : {}", tbGuideDTO);

        return tbGuideRepository
            .findById(tbGuideDTO.getId())
            .map(existingTbGuide -> {
                tbGuideMapper.partialUpdate(existingTbGuide, tbGuideDTO);

                return existingTbGuide;
            })
            .map(tbGuideRepository::save)
            .map(tbGuideMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TbGuideDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TbGuides");
        return tbGuideRepository.findAll(pageable).map(tbGuideMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbGuideDTO> findOne(Long id) {
        log.debug("Request to get TbGuide : {}", id);
        return tbGuideRepository.findById(id).map(tbGuideMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TbGuide : {}", id);
        tbGuideRepository.deleteById(id);
    }
}
