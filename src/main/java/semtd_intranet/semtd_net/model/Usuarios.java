package semtd_intranet.semtd_net.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuarios implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome não pode estar vazio")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotEmpty(message = "A senha não pode estar vazia")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;

    @NotEmpty(message = "O e-mail não pode estar vazio")
    @Email(message = "E-mail inválido")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Formato de e-mail inválido")
    private String email;

    private Boolean diretor = false;

    private Boolean secretario = false;

    @NotEmpty(message = "A função não pode estar vazia")
    @Size(min = 2, max = 50, message = "A função deve ter entre 2 e 50 caracteres")
    private String funcao;

    @NotNull(message = "A gerência é obrigatória")
    @ManyToOne
    @JoinColumn(name = "gerencia_id", nullable = false)
    private Gerencia gerencia;

    @OneToMany(mappedBy = "criadoPor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comunicados> comunicadosCriados;

    // SPRING SECURITY MÉTODOS

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}