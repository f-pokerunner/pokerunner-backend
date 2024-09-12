package com.flab.pokerunner.service.pokemon;

import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import com.flab.pokerunner.domain.entity.UserJpo;
import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import com.flab.pokerunner.repository.UserRepository;
import com.flab.pokerunner.repository.pokemon.UserPokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonStoreTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPokemonRepository userPokemonRepository;

    @Mock
    private Environment env;

    @InjectMocks
    private PokemonStore pokemonStore;


    @Test
    void saveRunningStopped_whenDefaultPokemonIdIsNull() {
        var event = mock(RunningStopped.class);

        var user = mock(UserJpo.class);
        when(user.getDefaultPokemonId()).thenReturn(null);
        when(userRepository.findById(event.getUserId())).thenReturn(user);

        pokemonStore.save(event);
        verify(userPokemonRepository, never()).findByUserIdAndPokemonId(anyInt(), anyInt());
    }

    @Test
    void savePokemonSearched_withValidParameters() {
        var event = mock(PokemonSearched.class);
        var pokemonLocation = mock(PokemonLocationDto.class);

        pokemonStore.save(event, pokemonLocation);

        verify(userPokemonRepository).save(any(UserPokemonJpo.class));
    }
}
