package me.denden.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.domain.Article;
import me.denden.springbootdeveloper.dto.AddArticleRequest;
import me.denden.springbootdeveloper.dto.UpdateArticleRequest;
import me.denden.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById( id )
                .orElseThrow( () -> new IllegalArgumentException( "not found : " + id ) );

        article.update( request.getTitle(), request.getContent() );

        return article;
    }
}
