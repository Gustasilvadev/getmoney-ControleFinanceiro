package com.getmoney.service;

import com.getmoney.dto.request.MetaRequestDTO;
import com.getmoney.dto.request.MetaRequestUpdateDTO;
import com.getmoney.dto.response.MetaResponseDTO;
import com.getmoney.dto.response.TransacaoBasicaResponseDTO;
import com.getmoney.dto.response.TransacaoResponseDTO;
import com.getmoney.entity.Meta;
import com.getmoney.entity.Usuario;
import com.getmoney.repository.MetaRepository;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MetaService(MetaRepository metaRepository, UsuarioRepository usuarioRepository) {
        this.metaRepository = metaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<MetaResponseDTO> listarMetas() {
        List<Meta> metas = metaRepository.listarMetasAtivas();

        return metas.stream()
                .map(meta -> {
                    MetaResponseDTO metaResponseDTO = modelMapper.map(meta, MetaResponseDTO.class);
                    metaResponseDTO.setUsuarioId(meta.getUsuario().getId());

                    if (meta.getTransacoes() != null) {
                        List<TransacaoBasicaResponseDTO> transacoesDTO = meta.getTransacoes().stream()
                                .map(transacao -> {
                                    TransacaoBasicaResponseDTO dto = modelMapper.map(transacao, TransacaoBasicaResponseDTO.class);

                                    if (transacao.getCategoria() != null) {
                                        dto.setCategoriaId(transacao.getCategoria().getId());
                                        dto.setCategoriaNome(transacao.getCategoria().getNome());
                                        dto.setCategoriaTipo(transacao.getCategoria().getTipo().toString());
                                    }
                                    return dto;
                                })
                                .collect(Collectors.toList());
                        metaResponseDTO.setTransacoes(transacoesDTO);
                    }

                    return metaResponseDTO;
                })
                .collect(Collectors.toList());
    }


    public MetaResponseDTO listarPorMetaId(Integer metaId) {
        Meta meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new EntityNotFoundException("Meta não encontrada com id: " + metaId));

        return new MetaResponseDTO(meta);
    }

    public MetaResponseDTO criarMeta(MetaRequestDTO metaRequestDTO) {
        Meta meta = new Meta();
        meta.setNome(metaRequestDTO.getNome());
        meta.setValorAlvo(metaRequestDTO.getValorAlvo());
        meta.setStatus(metaRequestDTO.getStatus());
        meta.setData(metaRequestDTO.getData());


        Usuario usuario = usuarioRepository.findById(metaRequestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        meta.setUsuario(usuario);


        Meta metaSalva = metaRepository.save(meta);
        return new MetaResponseDTO(metaSalva);
    }

    public MetaResponseDTO editarPorMetaId(Integer metaId, MetaRequestUpdateDTO metaRequestUpdateDTO) {
        Meta metaExistente = metaRepository.findById(metaId)
                .orElseThrow(() -> new EntityNotFoundException("Meta não encontrada com id " + metaId));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper.map(metaRequestUpdateDTO, metaExistente);

        Meta metaAtualizada = metaRepository.save(metaExistente);

        return modelMapper.map(metaAtualizada, MetaResponseDTO.class);
    }

    public void deletarPorMetaId(Integer metaId) {
        boolean metaExistente = metaRepository.existsById(metaId);
        if(metaExistente){
            metaRepository.deleteById(metaId);
        }
    }
}
