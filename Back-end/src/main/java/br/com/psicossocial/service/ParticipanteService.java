
package br.com.psicossocial.service;

import br.com.psicossocial.dto.ParticipanteCadastroDTO;
import br.com.psicossocial.dto.ParticipanteEdicaoDTO;
import br.com.psicossocial.dto.ParticipanteResponseDTO;
import br.com.psicossocial.entity.Participante;
import br.com.psicossocial.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    // Converte a entidade para o DTO de resposta.
    private ParticipanteResponseDTO converterParaDTO(Participante participante) {
        return new ParticipanteResponseDTO(
                participante.getId(),
                participante.getCpf(),
                participante.getNome(),
                participante.getCargo(),
                participante.getSetor()
        );
    }

    // Cadastra um novo participante.
    @Transactional
    public ParticipanteResponseDTO cadastrar(ParticipanteCadastroDTO dados) {

        if (participanteRepository.findByCpf(dados.cpf()).isPresent()) {
            throw new RuntimeException("Já existe um participante cadastrado com esse CPF.");
        }

        Participante participante = new Participante();
        participante.setCpf(dados.cpf());
        participante.setNome(dados.nome());
        participante.setCargo(dados.cargo());
        participante.setSetor(dados.setor());

        return converterParaDTO(participanteRepository.save(participante));
    }

    // Lista todos os participantes.
    public List<ParticipanteResponseDTO> listarTodos() {
        return participanteRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // Busca um participante pelo ID.
    public ParticipanteResponseDTO buscarPorId(Integer id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado."));

        return converterParaDTO(participante);
    }

    // Atualiza os dados de um participante.
    @Transactional
    public ParticipanteResponseDTO atualizar(Integer id, ParticipanteEdicaoDTO dados) {

        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado."));

        participanteRepository.findByCpf(dados.cpf()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new RuntimeException("Esse CPF já está cadastrado para outro participante.");
            }
        });

        participante.setCpf(dados.cpf());
        participante.setNome(dados.nome());
        participante.setCargo(dados.cargo());
        participante.setSetor(dados.setor());

        return converterParaDTO(participanteRepository.save(participante));
    }

    // Exclui um participante.
    @Transactional
    public void excluir(Integer id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado."));

        participanteRepository.delete(participante);
    }
}
