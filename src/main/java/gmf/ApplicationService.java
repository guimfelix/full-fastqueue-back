package gmf;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gmf.model.EPapel;
import gmf.model.Endereco;
import gmf.model.Espectador;
import gmf.model.Evento;
import gmf.model.Papel;
import gmf.model.Produtor;
import gmf.model.Usuario;
import gmf.repository.EnderecoRepository;
import gmf.repository.EspectadorRepository;
import gmf.repository.EventoRepository;
import gmf.repository.PapelRepository;
import gmf.repository.ProdutorRepository;
import gmf.repository.UsuarioRepository;

@Service
public class ApplicationService implements ApplicationRunner {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    PapelRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutorRepository produtorRepository;
    private final EspectadorRepository espectadorRepository;
    private final PapelRepository papelRepository;
    private final EnderecoRepository enderecoRepository;

    public ApplicationService(EnderecoRepository enderecoRepository, EventoRepository eventoRepository,
            UsuarioRepository usuarioRepository, ProdutorRepository produtorRepository,
            EspectadorRepository espectadorRepository, PapelRepository papelRepository) {
        this.enderecoRepository = enderecoRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtorRepository = produtorRepository;
        this.espectadorRepository = espectadorRepository;
        this.papelRepository = papelRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Papel p1 = new Papel(EPapel.PAPEL_ADMIN);
        Papel p2 = new Papel(EPapel.PAPEL_ESPECTADOR);
        Papel p3 = new Papel(EPapel.PAPEL_PRODUTOR);

        papelRepository.save(p1);
        papelRepository.save(p2);
        papelRepository.save(p3);

        Usuario usuarioAdmin = new Usuario("guiadmin", "gui@puc.com", encoder.encode("123456"), true);

        Set<Papel> listaPapel = Set.of(p1, p2, p3);
        usuarioAdmin.setRoles(listaPapel);
        usuarioRepository.save(usuarioAdmin);

        Usuario usuarioProdutor = new Usuario("janapromoter", "jana@puc.com", encoder.encode("123456"), true);
        usuarioProdutor.setRoles(Set.of(p2, p3));
        usuarioRepository.save(usuarioProdutor);

        Usuario usuarioEspectador = new Usuario("cajaFesteiro", "caja@puc.com", encoder.encode("123456"), true);
        usuarioEspectador.setRoles(Set.of(p2));
        usuarioRepository.save(usuarioEspectador);

        Endereco endereco = new Endereco();
        endereco.nomeRua = "Mario Jose Filho";
        endereco.numero = "10";
        endereco.complemento = "Fundo";
        endereco.bairro = "Jardins";
        endereco.cidade = "Jaboatão";
        endereco.estado = "NI";

        Endereco endereco2 = new Endereco();
        endereco2.nomeRua = "Afonso Pena Jr";
        endereco2.numero = "2300";
        endereco2.complemento = "";
        endereco2.bairro = "Guaratins";
        endereco2.cidade = "Renova";
        endereco2.estado = "PS";

        enderecoRepository.save(endereco);
        enderecoRepository.save(endereco2);

        Espectador espectador = new Espectador();
        espectador.nome = "Cajauino Oliveira";
        espectador.dataNascimento = "10/10/1990";
        espectador.endereco = endereco;
        espectador.usuario = usuarioEspectador;

        Espectador espectador1 = new Espectador();
        espectador1.nome = "Jarbas Silva";
        espectador1.dataNascimento = "10/10/1990";
        espectador1.endereco = endereco;

        Espectador espectador2 = new Espectador();
        espectador2.nome = "Mauro Nunes";
        espectador2.dataNascimento = "10/10/1990";
        espectador2.endereco = endereco;

        Espectador espectador3 = new Espectador();
        espectador3.nome = "Laura Faisao";
        espectador3.dataNascimento = "10/10/1990";
        espectador3.endereco = endereco;

        Espectador espectador4 = new Espectador();
        espectador4.nome = "Gabriela Zardinski";
        espectador4.dataNascimento = "10/10/1990";
        espectador4.endereco = endereco;

        espectadorRepository.save(espectador);

        espectadorRepository.save(espectador1);

        espectadorRepository.save(espectador2);
        espectadorRepository.save(espectador3);
        espectadorRepository.save(espectador4);

        Produtor produtor = new Produtor();
        produtor.nome = "Janailva Munhoz";
        produtor.dataNascimento = "11/11/1991";
        produtor.endereco = endereco;
        produtor.usuario = usuarioProdutor;

        Produtor produtor1 = new Produtor();
        produtor1.nome = "Carolina Cunha";
        produtor1.dataNascimento = "11/11/1991";
        produtor1.endereco = endereco;

        produtorRepository.save(produtor);
        produtorRepository.save(produtor1);

        Evento evento1 = new Evento();
        evento1.nomeEvento = "Festa do Morango";
        evento1.nomeLocalEvento = "Feira de Agricultores coberta";
        evento1.endereco = endereco;
        evento1.dataEvento = "31/10/2022";
        evento1.horarioEvento = "10:00";
        evento1.produtor = produtor;
        evento1.quantidadeEspectadoresEsperada = 1000;

        Evento evento2 = new Evento();
        evento2.nomeEvento = "Festa da Noite do Sinal";
        evento2.nomeLocalEvento = "Boate Juizado Final";
        evento2.endereco = endereco2;
        evento2.dataEvento = "02/01/2022";
        evento2.horarioEvento = "23:50";
        evento2.quantidadeEspectadoresEsperada = 300;

        Evento evento3 = new Evento();
        evento3.nomeEvento = "Bailão Sertanejo";
        evento3.nomeLocalEvento = "Espaço Fenix";
        evento3.endereco = endereco;
        evento3.dataEvento = "09/05/2022";
        evento3.horarioEvento = "22:00";
        evento3.quantidadeEspectadoresEsperada = 8500;

        Evento evento4 = new Evento();
        evento4.nomeEvento = "Jornada ecumênica";
        evento4.nomeLocalEvento = "Sitio Espolio";
        evento4.endereco = endereco;
        evento4.dataEvento = "31/10/2022";
        evento4.horarioEvento = "08:00";
        evento4.quantidadeEspectadoresEsperada = 200;

        Evento evento5 = new Evento();
        evento5.nomeEvento = "Desfile Animal";
        evento5.nomeLocalEvento = "Espaço Clean";
        evento5.endereco = endereco;
        evento5.dataEvento = "26/10/2022";
        evento5.horarioEvento = "13:00";
        evento5.produtor = produtor;
        evento5.espectadores = List.of(espectador4, espectador);
        evento5.quantidadeEspectadoresEsperada = 200;

        // persiste com produtor
        eventoRepository.save(evento1);

        eventoRepository.save(evento2);
        eventoRepository.save(evento3);
        eventoRepository.save(evento4);

        // persiste com produtor e um espectador na lista
        eventoRepository.save(evento5);

        List<Espectador> listaEspectadores = List.of(espectador, espectador1, espectador2, espectador3);
        // falta o espectador 4

        List<Evento> listaEventos = List.of(evento1, evento2, evento3, evento4);
        // falta evento 5

        // atualiza evento2 com lista de espectadores
        evento2.espectadores = listaEspectadores;
        eventoRepository.save(evento2);

        espectador.eventos = listaEventos;
        espectadorRepository.save(espectador);
        espectador3.eventos = listaEventos;
        espectadorRepository.save(espectador3);

        // atualiza produtor evento
        evento4.produtor = produtor1;
        eventoRepository.save(evento4);

        produtor.eventos = listaEventos;
        produtorRepository.save(produtor);

    }
}
