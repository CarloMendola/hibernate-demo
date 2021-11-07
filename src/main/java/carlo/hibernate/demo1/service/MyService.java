package carlo.hibernate.demo1.service;

import carlo.hibernate.demo1.repository.custom.IAlphaRepository;
import carlo.hibernate.demo1.repository.entities.Alpha;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
@Log4j2
@Service
@Transactional(
    isolation = Isolation.READ_COMMITTED,
    readOnly = false,
    propagation = Propagation.REQUIRED,
    rollbackFor = { Exception.class, RuntimeException.class })
// @formatter:on
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyService {
    @Autowired
    IAlphaRepository alphaRepository;

    public String save(Alpha in) {
        String out="empty";
        try {
            Alpha persisted = alphaRepository.createAndGet(in);
            out = persisted.toString();
        } catch (Exception e) {
            log.error("unexpected exception",e);
        }
        return out;
    }

}
