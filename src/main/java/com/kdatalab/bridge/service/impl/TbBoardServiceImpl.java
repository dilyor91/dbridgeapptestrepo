package com.kdatalab.bridge.service.impl;

import com.kdatalab.bridge.domain.TbBoard;
import com.kdatalab.bridge.repository.TbBoardRepository;
import com.kdatalab.bridge.service.TbBoardService;
import com.kdatalab.bridge.service.dto.TbBoardDTO;
import com.kdatalab.bridge.service.mapper.TbBoardMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TbBoard}.
 */
@Service
@Transactional
public class TbBoardServiceImpl implements TbBoardService {

    private final Logger log = LoggerFactory.getLogger(TbBoardServiceImpl.class);

    private final TbBoardRepository tbBoardRepository;

    private final TbBoardMapper tbBoardMapper;

    public TbBoardServiceImpl(TbBoardRepository tbBoardRepository, TbBoardMapper tbBoardMapper) {
        this.tbBoardRepository = tbBoardRepository;
        this.tbBoardMapper = tbBoardMapper;
    }

    @Override
    public TbBoardDTO save(TbBoardDTO tbBoardDTO) {
        log.debug("Request to save TbBoard : {}", tbBoardDTO);
        TbBoard tbBoard = tbBoardMapper.toEntity(tbBoardDTO);
        tbBoard = tbBoardRepository.save(tbBoard);
        return tbBoardMapper.toDto(tbBoard);
    }

    @Override
    public TbBoardDTO update(TbBoardDTO tbBoardDTO) {
        log.debug("Request to update TbBoard : {}", tbBoardDTO);
        TbBoard tbBoard = tbBoardMapper.toEntity(tbBoardDTO);
        tbBoard = tbBoardRepository.save(tbBoard);
        return tbBoardMapper.toDto(tbBoard);
    }

    @Override
    public Optional<TbBoardDTO> partialUpdate(TbBoardDTO tbBoardDTO) {
        log.debug("Request to partially update TbBoard : {}", tbBoardDTO);

        return tbBoardRepository
            .findById(tbBoardDTO.getId())
            .map(existingTbBoard -> {
                tbBoardMapper.partialUpdate(existingTbBoard, tbBoardDTO);

                return existingTbBoard;
            })
            .map(tbBoardRepository::save)
            .map(tbBoardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TbBoardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TbBoards");
        return tbBoardRepository.findAll(pageable).map(tbBoardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbBoardDTO> findOne(Long id) {
        log.debug("Request to get TbBoard : {}", id);
        return tbBoardRepository.findById(id).map(tbBoardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TbBoard : {}", id);
        tbBoardRepository.deleteById(id);
    }
}
