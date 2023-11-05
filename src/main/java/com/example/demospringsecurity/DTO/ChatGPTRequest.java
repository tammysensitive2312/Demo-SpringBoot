package com.example.demospringsecurity.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
public class ChatGPTRequest {
    private String model;
    private List<Message> messages;

    public ChatGPTRequest(String model, String promt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", promt));
    }
}
