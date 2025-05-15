package exercise.exercise.web.mapper;

import exercise.exercise.domain.model.Game;
import exercise.exercise.web.model.GameWeb;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-15T09:57:00+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 18.0.2-ea (Private Build)"
)
public class GameMapperWebImpl implements GameMapperWeb {

    @Override
    public GameWeb toWeb(Game game) {
        if ( game == null ) {
            return null;
        }

        GameWeb gameWeb = new GameWeb();

        gameWeb.setBoard( map( game.getBoard() ) );
        gameWeb.setSinglePlayer( game.isSinglePlayer() );
        gameWeb.setPlayer1( game.getPlayer1() );
        gameWeb.setPlayer2( game.getPlayer2() );
        gameWeb.setCurrentPlayer( game.getCurrentPlayer() );
        gameWeb.setAuthHeader( game.getAuthHeader() );

        gameWeb.setGameId( toUUID(game.getGameId().toString()) );

        return gameWeb;
    }

    @Override
    public Game fromWeb(GameWeb gameWeb) {
        if ( gameWeb == null ) {
            return null;
        }

        Game game = new Game();

        game.setBoard( map( gameWeb.getBoard() ) );
        game.setGameId( gameWeb.getGameId() );
        game.setAuthHeader( gameWeb.getAuthHeader() );
        game.setSinglePlayer( gameWeb.isSinglePlayer() );
        game.setPlayer1( gameWeb.getPlayer1() );
        game.setPlayer2( gameWeb.getPlayer2() );
        game.setCurrentPlayer( gameWeb.getCurrentPlayer() );

        return game;
    }
}
