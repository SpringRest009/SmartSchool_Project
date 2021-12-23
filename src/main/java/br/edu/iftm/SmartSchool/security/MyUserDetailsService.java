package br.edu.iftm.SmartSchool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.edu.iftm.SmartSchool.model.Usuario;
import br.edu.iftm.SmartSchool.repository.UsuarioRepository;

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("---------------------------------->" + username);
        Usuario usuario = usuarioRepository.buscaPorLogin(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(usuario);
    }
}
