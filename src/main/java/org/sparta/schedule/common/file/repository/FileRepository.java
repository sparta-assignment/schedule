package org.sparta.schedule.common.file.repository;

import org.sparta.schedule.common.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

}
