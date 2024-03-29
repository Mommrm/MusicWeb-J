package com.example.demo.DTO;

import com.example.demo.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Songs {
    private int songCount;
    private List<Song> songs;
}
