package salvo.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

    //this is needed for spring to run the application
	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

	}

	// with beans we create instances for the according class. Here Player or Game
	@Bean
	public CommandLineRunner initData(PlayerRepository playerrepository, GameRepository gamerepository, GamePlayerRepository gameplayerrepository,
                                      ShipRepository shiprepository, SalvoRepository salvorepository, ScoreRepository scoreRepository) {
		//Arrow function
	    return (args) -> {

			// save a couple of Players (like creating instances in  OOP in JavaScript for the Database)

            Player jack = new Player("j.bauer@ctu.gov", "24");
            Player conan = new Player("c.obrian@ctu.gov", "42");
            Player kim = new Player("kim_bauer@gmail.com", "kb");
            Player tony = new Player("t.almeida@ctu.gov", "mole");
            Player david = new Player("d.palmer@whitehouse.gov", "pres");


            playerrepository.save(jack);
            playerrepository.save(conan);
            playerrepository.save(kim);
            playerrepository.save(tony);
            playerrepository.save(david);



            Game game1 = new Game();
            Game game2 = new Game();
            Game game3 = new Game();
            Game game4 = new Game();
            Game game5 = new Game();
            Game game6 = new Game();


            //Determine the creation Date next game starts one hour after the last. plusSeconds(3600)
            Date date1 = new Date();
            Date date2 = Date.from(date1.toInstant().plusSeconds(3600));
            Date date3 = Date.from(date2.toInstant().plusSeconds(3600));
            Date date4 = Date.from(date3.toInstant().plusSeconds(3600));
            Date date5 = Date.from(date4.toInstant().plusSeconds(3600));
            Date date6 = Date.from(date5.toInstant().plusSeconds(3600));

            game1.setCreationDate(date1);
            game2.setCreationDate(date2);
            game3.setCreationDate(date3);
            game4.setCreationDate(date4);
            game5.setCreationDate(date5);
            game6.setCreationDate(date6);

            gamerepository.save(game1);
            gamerepository.save(game2);
            gamerepository.save(game3);
            gamerepository.save(game4);
            gamerepository.save(game5);
            gamerepository.save(game6);



            Ship ship1 = new Ship(Ship.Type.Carrier, Arrays.asList("A9", "B9", "C9", "D9", "E9"));
            Ship ship2 = new Ship(Ship.Type.Battleship, Arrays.asList("G1", "G2", "G3", "G4"));
            Ship ship3 = new Ship(Ship.Type.Submarine, Arrays.asList("E2", "E3", "E4"));
            Ship ship4 = new Ship(Ship.Type.Destroyer, Arrays.asList("H5", "H6", "H7"));
            Ship ship5 = new Ship(Ship.Type.PatrolBoat, Arrays.asList("F4", "F5"));
            Ship ship6 = new Ship(Ship.Type.Carrier, Arrays.asList("A7", "A8", "A9", "A10"));
            Ship ship7 = new Ship(Ship.Type.Battleship, Arrays.asList("H1", "H2", "H3", "H4"));
            Ship ship8 = new Ship(Ship.Type.Submarine, Arrays.asList("E2", "E3", "E4"));
            Ship ship9 = new Ship(Ship.Type.Destroyer, Arrays.asList());
            Ship ship10 = new Ship(Ship.Type.PatrolBoat, Arrays.asList());

            shiprepository.save(ship1);
            shiprepository.save(ship2);
            shiprepository.save(ship3);
            shiprepository.save(ship4);
            shiprepository.save(ship5);
            shiprepository.save(ship6);
            shiprepository.save(ship7);
            shiprepository.save(ship8);
            shiprepository.save(ship9);
            shiprepository.save(ship10);




                //Always watch out for the Reihenfolge determined in the GamePlayer class: Public Game Player(Date etc)
                GamePlayer gameplayer1 = new GamePlayer(new Date(), game1, jack);
                GamePlayer gameplayer2 = new GamePlayer(new Date(), game1, kim);
                GamePlayer gameplayer3 = new GamePlayer(new Date(), game2, conan);

                GamePlayer gameplayer4 = new GamePlayer(new Date(), game2, kim);
                GamePlayer gameplayer5 = new GamePlayer(new Date(), game3, conan);
                GamePlayer gameplayer6 = new GamePlayer(new Date(), game3, jack);

                GamePlayer gameplayer7 = new GamePlayer(new Date(), game4, jack);
                GamePlayer gameplayer8 = new GamePlayer(new Date(), game4, kim);
                GamePlayer gameplayer9 = new GamePlayer(new Date(), game5, conan);

                GamePlayer gameplayer10 = new GamePlayer(new Date(), game5, kim);
                GamePlayer gameplayer11 = new GamePlayer(new Date(), game6, david);


                gameplayerrepository.save(gameplayer1);
                gameplayerrepository.save(gameplayer2);
                gameplayerrepository.save(gameplayer3);
                gameplayerrepository.save(gameplayer4);
                gameplayerrepository.save(gameplayer5);
                gameplayerrepository.save(gameplayer6);

                gameplayerrepository.save(gameplayer7);
                gameplayerrepository.save(gameplayer8);
                gameplayerrepository.save(gameplayer9);
                gameplayerrepository.save(gameplayer10);
                gameplayerrepository.save(gameplayer11);

            Salvo salvo1 = new Salvo(gameplayer1, Arrays.asList("B2", "C6"), 1);
            Salvo salvo2 = new Salvo(gameplayer2, Arrays.asList("D2", "E6"), 1);
            Salvo salvo3 = new Salvo(gameplayer1, Arrays.asList("B6", "A9"), 2);
            Salvo salvo4 = new Salvo(gameplayer2, Arrays.asList("B3", "C6"), 2);

            salvorepository.save(salvo1);
            salvorepository.save(salvo2);
            salvorepository.save(salvo3);
            salvorepository.save(salvo4);


                gameplayer1.addShip(ship1);
                gameplayer1.addShip(ship2);
                gameplayer1.addShip(ship3);
                gameplayer1.addShip(ship4);
                gameplayer2.addShip(ship5);
                gameplayer2.addShip(ship6);
                gameplayer2.addShip(ship7);
                gameplayer2.addShip(ship8);
                /*gameplayer4.addShip(ship9);
                gameplayer5.addShip(ship1);
                gameplayer5.addShip(ship2);
                gameplayer5.addShip(ship3);
                gameplayer6.addShip(ship4);
                gameplayer6.addShip(ship5);
                gameplayer6.addShip(ship6);*/

                shiprepository.save(ship1);
                shiprepository.save(ship2);
                shiprepository.save(ship3);
                shiprepository.save(ship4);
                shiprepository.save(ship5);
                shiprepository.save(ship6);
                shiprepository.save(ship7);
                shiprepository.save(ship8);



                Score score1 = new Score(jack, game1, 1, new Date());
                Score score2 = new Score(kim, game1, 0, new Date());
                Score score3 = new Score(conan, game2, 0, new Date());
                Score score4 = new Score(kim, game2, 1, new Date());
                Score score6 = new Score(jack, game3, 1, new Date());
                Score score7 = new Score(conan, game3, 0, new Date());

                scoreRepository.save(score1);
                scoreRepository.save(score2);
                scoreRepository.save(score3);
                scoreRepository.save(score4);
                scoreRepository.save(score6);
                scoreRepository.save(score7);

        };


	}

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Player player = playerRepository.findByUserName(username);
                if (player != null) {
                    return new User(player.getUserName(), player.getPassword(), AuthorityUtils.createAuthorityList("USER"));
                } else {
                    throw new UsernameNotFoundException("Unknown user: " + username);
                }
            }
        };
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/players").permitAll()
                .antMatchers("/api/leaderboard").permitAll()
                .antMatchers("/api/games").permitAll()
                .antMatchers("/api/games/new").hasAuthority("USER")
                .antMatchers("/api/games/join").hasAuthority("USER")
                .antMatchers("/api/games/join/**").hasAuthority("USER")
                .antMatchers("/web/**").permitAll()
                .antMatchers("/**").hasAuthority("USER")
                .and()
                .formLogin();

        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");


        // turn off checking for CSRF tokens
        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        });

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

    }
}