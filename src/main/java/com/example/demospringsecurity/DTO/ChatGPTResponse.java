package com.example.demospringsecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTResponse {

    private List<Choise> choices;
    @Data
    @RequiredArgsConstructor
    public static class Choise {
        private int index;
        private Message message;
    }
}
