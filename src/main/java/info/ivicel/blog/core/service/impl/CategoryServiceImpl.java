package info.ivicel.blog.core.service.impl;

import info.ivicel.blog.core.model.domain.Category;
import info.ivicel.blog.core.repository.CategoryRepository;
import info.ivicel.blog.core.service.CategoryService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Category> findAllPagable(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    @Override
    public Set<Category> findByNameIn(Collection<String> names) {
        return categoryRepository.findByNameIn(names);
    }

    @Override
    @Transactional
    public Set<Category> addAll(Collection<Category> categories) {
        // saveAll 默认一个一个的插入数据, 并不是 batch insert, 对于一个博客来说, 这性能损失并没什么
        return new HashSet<>(categoryRepository.saveAll(categories));
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        return save(category);
    }

    @Override
    @Transactional
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public boolean exist(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public Optional<Category>findByCategoryName(String name) {
        return categoryRepository.findByName(name);
    }
}
