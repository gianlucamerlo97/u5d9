package gianlucamerlo.u5d9.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gianlucamerlo.u5d9.entities.Author;
import gianlucamerlo.u5d9.exceptions.BadRequestException;
import gianlucamerlo.u5d9.exceptions.NotFoundException;
import gianlucamerlo.u5d9.payloads.NewAuthorsDTO;
import gianlucamerlo.u5d9.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class AuthorsService {

    @Autowired
    private AuthorsRepository authorsRepository;
    @Autowired
    private Cloudinary imgUploader;

    public Author save(NewAuthorsDTO payload) {
        this.authorsRepository.findByEmail(payload.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + author.getEmail() + " è già stata utilizzata");
        });
        Author newAuthor = new Author(payload.name(),payload.surname(),payload.email(),payload.dateOfBirth());
        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" + payload.name().charAt(0) + "+" + payload.surname().charAt(0));
        return authorsRepository.save(newAuthor);
    }

    public Page<Author> getAuthors(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return authorsRepository.findAll(pageable);
    }

    public Author findById(int id) {
        return authorsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Author found = this.findById(id);
        authorsRepository.delete(found);
    }

    public Author findByIdAndUpdate(int id, Author body) {

        Author found = this.findById(id);
        found.setEmail(body.getEmail());
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setDateOfBirth(body.getDateOfBirth());
        found.setAvatar(body.getAvatar());
        return authorsRepository.save(found);
    }

    public String uploadAvatar(MultipartFile file) {
        try {
            Map result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageURL = (String) result.get("url"); // Dalla risposta di Cloudinary leggo l'url.
            // ... Qua dovreste aggiornare l'avatar dell'utente a DB!
            return imageURL;
        } catch (Exception e) {
            throw new BadRequestException("Ci sono stati problemi nel salvataggio del file!");
        }
    }
}
