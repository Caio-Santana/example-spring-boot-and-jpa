package com.samsoftware.example.repositories;

import com.samsoftware.example.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {
}
