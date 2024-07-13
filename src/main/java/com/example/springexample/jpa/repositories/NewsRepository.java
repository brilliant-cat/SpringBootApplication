package com.example.springexample.jpa.repositories;

import com.example.springexample.jpa.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository <News, Long> {


}
