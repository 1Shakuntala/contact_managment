package com.example.demo1.service;

import com.example.demo1.entity.Contact;
import com.example.demo1.repository.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.example.demo1.constant.constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class ContactService {

//    private  final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Page<Contact> getAllContacts(int page, int size){
        return contactRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }
    public Contact getContact(Integer id){
        return contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
    }
    public Contact createContact(Contact contact){
        return contactRepository.save(contact);
    }
    public void deleteContact(Integer id){
        if (getContact(id) != null) {
            contactRepository.deleteById(id);
        }
    }
    public String uploadPhoto(Integer id, MultipartFile file){
//        log.info("Saving picture for user ID: {}", id );
        Contact contact = getContact(id);
        String photoUrl = photoFunction.apply(id, file);
        contact.setPhotoUrl(photoUrl);
        contactRepository.save(contact);
        return photoUrl;
    }
    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    private final BiFunction<Integer, MultipartFile, String> photoFunction = (id, image) -> {
        String filename = id + fileExtension.apply(image.getOriginalFilename());
        try{
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)){Files.createDirectories(fileStorageLocation);}
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/contacts/image/" + filename).toUriString();
        }
        catch(Exception exception){
            throw new RuntimeException("Unable to save image");
        }
    };
}
