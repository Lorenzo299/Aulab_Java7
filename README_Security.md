SPRING SECURITY 
CI PERMETTE DI IMPLEMENTARE FACILMENTE DEI CONTROLLI NEL NOSTRO PROGETTO
CIOE DEFINIRE ALCUNE FUNZIONI DISPONIBILI SOLO AD ALCUNI UTENTI.
VENGONO IMPLEMENTATE GRAZIE AD UN SISTEMA DI AUTENTICAZIONE (REG, LOGIN, LOGOUT)
VENGONO DEFINITE LE AUTORIZZAZIONI PER L ACCESSO ALLE RISORSE DELL APPLICAZIONE

NEL CONTESTO DI SPRING, CON LA DIPENDENZA DI SPRING SECURITY VENGONO IMPLEMENTATE IN AUTOMATICO ALCUNE FUNZIONALITA SUI CONTROLLI CHE SONO SUBITO DISPONIBILI NEL NOSTRO PROGRAMMA. I FILTRI SONO I COMPONENTI DEL SECURITY, QUESTI SI APPLICANO ALLE CHIAMATA, MODIFICANDOLE O AGGIUNGENDO DATI: VENOGNO CREATE RETI/CATENE DI FILTRI CHE APPLICANO DIVERSI CONTROLLI ALLE CHIAMATE. QUESTO AVVIENE GRAZIE AD UN PROXY CHE CI REINVIA VERSO I FILTRI DOVE AVVIENEN TUTTA LA CATENA. UNA VOLTA IDENTIFICATI CON IL LOGIN SI ATTIVA IN AUTOMATICO UN FILTRO CHE CI ASSEGNA UNA STRINGA, CON QUELLA SI VIENE IDENTIFICATI COME UTENTI LOGGATI, 
ATTRAVERSO UNA DIPENDENZA:
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
CI SI CREA IN AUTOMATICO UN LOGIN CON UN USERNAME:USER E PASSWORD DA TERMINALE.

CREO FOLDER SECURITY PER L IMPLEMENTAZIONE DELLA SICUREZZA nel file SECURITYCONFIG
SI CREANO DEI BEAN, 
UNO RELATIVO AL CRIPT DELLA PASSWORD UNA VOLTA CHE L UTENTE SI REGISTRA, l encoder 
@Bean
public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
}
UN METODO CHE GESTIRA GLI UTENTI
SI APPLICA UNA METODOLOGIA PER CUI SI CREA UNA TABELLA NEL DB CON TUTTI I DATI DEL LOGIN DEI CLIENTI, E ATTAVERSO SERVICE E REPO VENGONO DEFINITI I METODI PER LA GESTIONE DEI DATI DEGLI UTENTI.
NOI FAREMO UN METODO PIU VELOCE CREANDO un BEAN NELLA SECURITYCONFIG

QUESTA CLASSE IDENTIFICA I DETTAGLI DELL UTENTE IN RAM RENDENDOLI ACCCESSIBILI A SCOPI DI TEST
CON QUESTO METODO SI CREANO GLI UTENTI, M ASI CREANO GRAZIE AGLI USERBUILDER OSSIA DEI METODI CHE EFFETTIVAMENTE REGISTRANO gli utenti
 @Bean
    public InMemoryUserDetailsManager userManager() {
        UserBuilder user = User.withUsername("user").password(encoder().encode("12345678"));
        UserBuilder admin = User.withUsername("admin").password(encoder().encode("admin12345678"));
        return new InMemoryUserDetailsManager(user.build(), admin.build());
    }

ABBIAMO CREATO 2 UTENTI
BCRIPT PASSECONDER E UN METODO SOLO DI CODIFICA PERO, COSI CHE DOBBIAMO CREARNE UNO DI DECODIFICA PER RITORNARE I DATI DECODIFICATI

INTANTO DOBBIAMO DEFINIRE L AUTORIZZAZIONE A LIVELLO API, LE RICHIESTE web TRAMITE BROWSER VENGONO GESTITE DAL SERVER E QUESTO FORNISCE ACCESSO inserendo dei cookies nell header della richiesta che forniscono i permessi per gli accessi alle risorse, CON LE API DOBBIAMO MANIPOLARE LA SECURITY FILTER CHAIN, CON UN BEAN:
@Bean
    public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.build();
    }
IL METODO HA UN PARAMETRO HTTP, LE CHAIN SONO ASSOCIATE SIA SULLA TRASMISSIONE HTTP CHE SU ALTRE, PERCIO DOBBIAMO GESTIRE IL TIPO DI PROTOCOLLO.
GESTIREMO IL PROTOCOLLO HTTP ATTRAVERSO LA CLASSE HTTPSECURITY, QUESTA ANCHE E UNA BUILD COME QUELLA USATA PER GESTIRE LA CREAZIONE DEGLI UTENTI. 
IL PATTERN BUILDER SONO LE OPERAZIONI CHE ANDREMO A ESEGUIRE ORA E SI BASA SULLA CREAZIONE DEGLI OGGETTI STEPBYSTEP, STILE PROGRAMMAZIONE FUNZIONALE.
LA FUNZIONE BUILD COSTRUISCE REALMENTE L OGGETTO, SECURITY FA LARGO USO DI QUESTI BUILDER
creiamo la filter chain security:

PARTENDO DA UN CONTROLLO GENERALE CHE FA RIFERIMENTO ALLE AUTORIZZAZIONI RISPETTO LE RICHIESTE HTTP, CHI PUO ACCEDERE A DETERMINANTE RISORSE E QUALI REQUISISTI SONO NECESSARI:
http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()); QUESTA DEFINISCE UNA CATENA DI FILTRI PER CUI SE SI VUOLE ACCEEDERE A TUTTE LE RICHIESTE BISOGNA ESSERE AUTENTICATI
QUESTA PER DEFINIRE IL PERMESSO PER ESSERE AUTENTICATI
@Bean
    public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> authorize.requestMatchers("/api/**").permitAll().anyRequest().authenticated());
        return http.build();
    }
    VIENE AGGIUYNTO IL REQUESTMATCHERS PER DEFINIRE L URI DELLE RICHIESTE CHE VOGLIAMO AUTORIZZARRE, SEGUITO DA PERMITALL
    LE RICHIESTE CHE NON HANNO API NELL URI SONO ANCORA BLOCCATE PERCHE CON QUESTO METODO NOI ABBIAMO DATO L AUTENTICAZIONE A TUTTI GLI UTENTI .AUTENTICATED

    AGGIUNGENDO  RIPRISTINIAMO IL FORM DI LOGIN DI DEFAULT PERCHE CON QUETO METODO VIENE ELIMINATO : .formLogin(Customizer.withDefaults())

    PER INSERIRE UNO PERSONALIZZATO .formLogin((formLogin) -> formLogin.loginPage("/login"))

    L INNDIRIZZO DEVE ESSERE MAPPATO DA UN CONTROLLER

        @Controller
        @RequestMapping("/")
        public class AuthController {

            @GetMapping("/login")
            public String login(Model model) {
                model.addAttribute("title", "Login");
                return "login";
            }

        }
    CREIAMO QUESTO HANDEL IN UN AUTHCONTOLLER GRAZIE CON CUI DEFINIAMO UN HANDLER GET DI LOGIN, QUI USIAMO IL MODEL MAPPER PER RITORNARE UNA VISTA LOGIN CON URL /LOGIN
    @Bean
    public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> authorize.requestMatchers("/api/**").permitAll().anyRequest().authenticated())
                .formLogin(
                        (formLogin) -> formLogin.loginPage("/login").defaultSuccessUrl("/authors", true).permitAll())
        return http.build();
    }
    IL METODO DEFAULTSUCCESSURL REINDIRIZZA NEL URL DEFINITO SOLO SE LA LOGIN E ANDATA A BUON FINE GRAZIE A TRUE

    INSERIAMO LA LOGOUT CON LA STESSA LOIGICA DI REINDIRIZZAMENTO:
    @Bean
    public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> authorize.requestMatchers("/api/**").permitAll().anyRequest().authenticated())
                .formLogin(
                        (formLogin) -> formLogin.loginPage("/login").defaultSuccessUrl("/authors", true).permitAll())
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));
        return http.build();
    }
    SENZA PERMITALL PERCHE QUI DOBBIAMO FARE UNA RICHIESTA POST DA UN UTENTE LOGGATO, CREre un pulsante logout a mo di form per richiamare attibuto action e method per applicare una post sull uri logout:
    non serve UN HANDLER POST NEL CONTROLLER per il logout

    SPRING SECURITY ABILITA IL CSRF TOKEN IN AUTOMTICO PER TUTTE LE RICHIESTE SIA WEB CEH API, MA QUESTO CI IMPEDISCE DI LAVORARE SU QUELLE API COSI CHE DOBBIAMO BLOCCARE QUESTO COMPORTAMENTO SOL PER LE RICHIESTE API
    IL CODICE COMPLETO DEL BEAN DEL FILTER CHAIN CHE SEGUE IL PATTERN BUILDER :
    
    @Bean
    public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> authorize.requestMatchers("/api/**").permitAll().anyRequest().authenticated())
                .formLogin(
                        (formLogin) -> formLogin.loginPage("/login").defaultSuccessUrl("/authors", true).permitAll())
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/")).csrf(
                        (csfr) -> csfr.ignoringRequestMatchers("/api/**"));
        return http.build();
    }

    CSP SONO UN INSIME DI REGOLE CHE PROTEGGONO  APP DAGLI ATTACCHI XSS, OSSIA CODE INJECTIO, DEGLI SCRIPT MALEVOLI DA LATO CLIENT, VA AGGIUNTO COL METODO HEADERS DOPO CSRF: E MEGLIO CREARE UN ATTRIBUTO STATICO CON TUTTE LE REGOLE INSERITE DI CSP CHE FPRNISCONO VARIE AUTORIZZAZIONI A SITI TERZI E POI RICHIAMARLO NEL METODO