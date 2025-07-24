package gianlucamerlo.u5d9.controllers;

import gianlucamerlo.u5d9.entities.Blogpost;
import gianlucamerlo.u5d9.exceptions.NotFoundException;
import gianlucamerlo.u5d9.payloads.NewBlogPostPayload;
import gianlucamerlo.u5d9.services.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogsController {
    @Autowired
    BlogsService blogsService;

    // 1. - POST http://localhost:3001/blogs (+ req.body)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Blogpost saveBlog(@RequestBody NewBlogPostPayload body) {
        return blogsService.save(body);
    }

    // 2. - GET http://localhost:3001/blogs
    @GetMapping("")
    public List<Blogpost> getBlogs(@RequestParam(required = false) Integer authorId) {
        if(authorId != null) return blogsService.findByAuthor(authorId);
        else return blogsService.getBlogs();
    }

    // 3. - GET http://localhost:3001/blogs/{id}
    @GetMapping("/{blogId}")
    public Blogpost findById(@PathVariable int blogId) {
        return blogsService.findById(blogId);
    }

    // 4. - PUT http://localhost:3001/blogs/{id} (+ req.body)
    @PutMapping("/{blogId}")
    public Blogpost findAndUpdate(@PathVariable int blogId, @RequestBody NewBlogPostPayload body) {
        return blogsService.findByIdAndUpdate(blogId, body);
    }

    // 5. - DELETE http://localhost:3001/blogs/{id
    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDelete(@PathVariable int blogId) {
        blogsService.findByIdAndDelete(blogId);
    }
}
