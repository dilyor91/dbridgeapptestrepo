package com.kdatalab.bridge.service.impl;

import com.kdatalab.bridge.domain.TbAttachment;
import com.kdatalab.bridge.repository.TbAttachmentRepository;
import com.kdatalab.bridge.service.TbAttachmentService;
import com.kdatalab.bridge.service.dto.TbAttachmentDTO;
import com.kdatalab.bridge.service.mapper.TbAttachmentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TbAttachment}.
 */
@Service
@Transactional
public class TbAttachmentServiceImpl implements TbAttachmentService {

    private final Logger log = LoggerFactory.getLogger(TbAttachmentServiceImpl.class);

    private final TbAttachmentRepository tbAttachmentRepository;

    private final TbAttachmentMapper tbAttachmentMapper;

    public TbAttachmentServiceImpl(TbAttachmentRepository tbAttachmentRepository, TbAttachmentMapper tbAttachmentMapper) {
        this.tbAttachmentRepository = tbAttachmentRepository;
        this.tbAttachmentMapper = tbAttachmentMapper;
    }

    @Override
    public TbAttachmentDTO save(TbAttachmentDTO tbAttachmentDTO) {
        log.debug("Request to save TbAttachment : {}", tbAttachmentDTO);
        TbAttachment tbAttachment = tbAttachmentMapper.toEntity(tbAttachmentDTO);
        tbAttachment = tbAttachmentRepository.save(tbAttachment);
        return tbAttachmentMapper.toDto(tbAttachment);
    }

    @Override
    public TbAttachmentDTO update(TbAttachmentDTO tbAttachmentDTO) {
        log.debug("Request to update TbAttachment : {}", tbAttachmentDTO);
        TbAttachment tbAttachment = tbAttachmentMapper.toEntity(tbAttachmentDTO);
        tbAttachment = tbAttachmentRepository.save(tbAttachment);
        return tbAttachmentMapper.toDto(tbAttachment);
    }

    @Override
    public Optional<TbAttachmentDTO> partialUpdate(TbAttachmentDTO tbAttachmentDTO) {
        log.debug("Request to partially update TbAttachment : {}", tbAttachmentDTO);

        return tbAttachmentRepository
            .findById(tbAttachmentDTO.getId())
            .map(existingTbAttachment -> {
                tbAttachmentMapper.partialUpdate(existingTbAttachment, tbAttachmentDTO);

                return existingTbAttachment;
            })
            .map(tbAttachmentRepository::save)
            .map(tbAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbAttachmentDTO> findAll() {
        log.debug("Request to get all TbAttachments");
        return tbAttachmentRepository.findAll().stream().map(tbAttachmentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbAttachmentDTO> findOne(Long id) {
        log.debug("Request to get TbAttachment : {}", id);
        return tbAttachmentRepository.findById(id).map(tbAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TbAttachment : {}", id);
        tbAttachmentRepository.deleteById(id);
    }
}
