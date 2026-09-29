package com.examcraft.testpaper.repository;

import com.examcraft.testpaper.entity.TestPaper;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TestPaperRepository extends JpaRepository<TestPaper, Long> {}
