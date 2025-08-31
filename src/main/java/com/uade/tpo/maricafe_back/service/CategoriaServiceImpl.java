package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Categoria;
import com.uade.tpo.maricafe_back.entity.dto.CategoriaDTO;
import com.uade.tpo.maricafe_back.entity.dto.CreateCategoriaDTO;
import com.uade.tpo.maricafe_back.exceptions.CategoryDuplicateException;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoriaServiceImpl implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CategoriaDTO> getCategories(Pageable pageable) {
        return categoriaRepository.findAll(pageable)
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class));
    }

    @Override
    public Optional<CategoriaDTO> getCategoryById(Integer id) {
        return categoriaRepository.findById(id)
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class));
    }

    @Override
    @Transactional
    public CategoriaDTO createCategory(CreateCategoriaDTO dto) {
        boolean exists = categoriaRepository.existsByNombre(dto.getNombre());
        if (exists) {
            throw new CategoryDuplicateException(dto.getNombre());
        }

        Categoria categoria = modelMapper.map(dto, Categoria.class);
        Categoria created = categoriaRepository.save(categoria);
        return modelMapper.map(created, CategoriaDTO.class);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría con id " + id + " no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CategoriaDTO updateCategory(Integer id, CreateCategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con id " + id + " no encontrada"));

        // Verificar duplicados
        boolean exists = categoriaRepository.existsByNombre(dto.getNombre());
        if (exists && !categoria.getNombre().equals(dto.getNombre())) {
            throw new CategoryDuplicateException(dto.getNombre());
        }

        categoria.setNombre(dto.getNombre());
        Categoria updated = categoriaRepository.save(categoria);

        return modelMapper.map(updated, CategoriaDTO.class);
    }
}
