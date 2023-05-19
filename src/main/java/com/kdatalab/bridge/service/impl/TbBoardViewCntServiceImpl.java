package com.kdatalab.bridge.service.impl;

import com.kdatalab.bridge.domain.TbBoardViewCnt;
import com.kdatalab.bridge.repository.TbBoardViewCntRepository;
import com.kdatalab.bridge.service.TbBoardViewCntService;
import com.kdatalab.bridge.service.dto.TbBoardViewCntDTO;
import com.kdatalab.bridge.service.mapper.TbBoardViewCntMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TbBoardViewCnt}.
 */
@Service
@Transactional
public class TbBoardViewCntServiceImpl implements TbBoardViewCntService {

    private final Logger log = LoggerFactory.getLogger(TbBoardViewCntServiceImpl.class);

    private final TbBoardViewCntRepository tbBoardViewCntRepository;

    private final TbBoardViewCntMapper tbBoardViewCntMapper;

    public TbBoardViewCntServiceImpl(TbBoardViewCntRepository tbBoardViewCntRepository, TbBoardViewCntMapper tbBoardViewCntMapper) {
        this.tbBoardViewCntRepository = tbBoardViewCntRepository;
        this.tbBoardViewCntMapper = tbBoardViewCntMapper;
    }

    @Override
    public TbBoardViewCntDTO save(TbBoardViewCntDTO tbBoardViewCntDTO) {
        log.debug("Request to save TbBoardViewCnt : {}", tbBoardViewCntDTO);
        TbBoardViewCnt tbBoardViewCnt = tbBoardViewCntMapper.toEntity(tbBoardViewCntDTO);
        tbBoardViewCnt = tbBoardViewCntRepository.save(tbBoardViewCnt);
        return tbBoardViewCntMapper.toDto(tbBoardViewCnt);
    }

    @Override
    public TbBoardViewCntDTO update(TbBoardViewCntDTO tbBoardViewCntDTO) {
        log.debug("Request to update TbBoardViewCnt : {}", tbBoardViewCntDTO);
        TbBoardViewCnt tbBoardViewCnt = tbBoardViewCntMapper.toEntity(tbBoardViewCntDTO);
        tbBoardViewCnt = tbBoardViewCntRepository.save(tbBoardViewCnt);
        return tbBoardViewCntMapper.toDto(tbBoardViewCnt);
    }

    @Override
    public Optional<TbBoardViewCntDTO> partialUpdate(TbBoardViewCntDTO tbBoardViewCntDTO) {
        log.debug("Request to partially update TbBoardViewCnt : {}", tbBoardViewCntDTO);

        return tbBoardViewCntRepository
            .findById(tbBoardViewCntDTO.getId())
            .map(existingTbBoardViewCnt -> {
                tbBoardViewCntMapper.partialUpdate(existingTbBoardViewCnt, tbBoardViewCntDTO);

                return existingTbBoardViewCnt;
            })
            .map(tbBoardViewCntRepository::save)
            .map(tbBoardViewCntMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbBoardViewCntDTO> findAll() {
        log.debug("Request to get all TbBoardViewCnts");
        return tbBoardViewCntRepository
            .findAll()
            .stream()
            .map(tbBoardViewCntMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbBoardViewCntDTO> findOne(Long id) {
        log.debug("Request to get TbBoardViewCnt : {}", id);
        return tbBoardViewCntRepository.findById(id).map(tbBoardViewCntMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TbBoardViewCnt : {}", id);
        tbBoardViewCntRepository.deleteById(id);
    }
}
