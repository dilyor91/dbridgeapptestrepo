package com.kdatalab.bridge.service.impl;

import com.kdatalab.bridge.domain.TbEduMst;
import com.kdatalab.bridge.repository.TbEduMstRepository;
import com.kdatalab.bridge.service.TbEduMstService;
import com.kdatalab.bridge.service.dto.TbEduMstDTO;
import com.kdatalab.bridge.service.mapper.TbEduMstMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TbEduMst}.
 */
@Service
@Transactional
public class TbEduMstServiceImpl implements TbEduMstService {

    private final Logger log = LoggerFactory.getLogger(TbEduMstServiceImpl.class);

    private final TbEduMstRepository tbEduMstRepository;

    private final TbEduMstMapper tbEduMstMapper;

    public TbEduMstServiceImpl(TbEduMstRepository tbEduMstRepository, TbEduMstMapper tbEduMstMapper) {
        this.tbEduMstRepository = tbEduMstRepository;
        this.tbEduMstMapper = tbEduMstMapper;
    }

    @Override
    public TbEduMstDTO save(TbEduMstDTO tbEduMstDTO) {
        log.debug("Request to save TbEduMst : {}", tbEduMstDTO);
        TbEduMst tbEduMst = tbEduMstMapper.toEntity(tbEduMstDTO);
        tbEduMst = tbEduMstRepository.save(tbEduMst);
        return tbEduMstMapper.toDto(tbEduMst);
    }

    @Override
    public TbEduMstDTO update(TbEduMstDTO tbEduMstDTO) {
        log.debug("Request to update TbEduMst : {}", tbEduMstDTO);
        TbEduMst tbEduMst = tbEduMstMapper.toEntity(tbEduMstDTO);
        tbEduMst = tbEduMstRepository.save(tbEduMst);
        return tbEduMstMapper.toDto(tbEduMst);
    }

    @Override
    public Optional<TbEduMstDTO> partialUpdate(TbEduMstDTO tbEduMstDTO) {
        log.debug("Request to partially update TbEduMst : {}", tbEduMstDTO);

        return tbEduMstRepository
            .findById(tbEduMstDTO.getId())
            .map(existingTbEduMst -> {
                tbEduMstMapper.partialUpdate(existingTbEduMst, tbEduMstDTO);

                return existingTbEduMst;
            })
            .map(tbEduMstRepository::save)
            .map(tbEduMstMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbEduMstDTO> findAll() {
        log.debug("Request to get all TbEduMsts");
        return tbEduMstRepository.findAll().stream().map(tbEduMstMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbEduMstDTO> findOne(Long id) {
        log.debug("Request to get TbEduMst : {}", id);
        return tbEduMstRepository.findById(id).map(tbEduMstMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TbEduMst : {}", id);
        tbEduMstRepository.deleteById(id);
    }
}
