package pro.sky.DBJava.repository;

import pro.sky.DBJava.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Page<Avatar> findAll(Pageable pageable);
}