package com.kazuki_turismo.api_kazukiTurismo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.kazuki_turismo.api_kazukiTurismo.Service.UsuarioServiceImpl;
import com.kazuki_turismo.api_kazukiTurismo.dao.UsuarioDAO;
import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UsuarioServiceTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    public void testListarUsuarios() throws SQLException {
        // 1. ARRANGE
        List<Usuario> listaFicticia = new ArrayList<>();
        Usuario u = new Usuario();
        u.setNombre("Julissa");
        listaFicticia.add(u);

        when(usuarioDAO.listar()).thenReturn(listaFicticia);

        // 2. ACT
        List<Usuario> resultado = usuarioService.listar();

        // 3. ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Julissa", resultado.get(0).getNombre());
        
        System.out.println("¡PRUEBA ACEPTADA!");
    }
}
