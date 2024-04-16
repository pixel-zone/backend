package br.com.pixelzone.pixelzone.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.pixelzone.pixelzone.dtos.jogos.Matchmaking;

@Configuration
public class MatchmakingConfig {

    @Bean
    public Matchmaking matchmaking(){

        return new Matchmaking(new ArrayList<>());

    }
    
}
